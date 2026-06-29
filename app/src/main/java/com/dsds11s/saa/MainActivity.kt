package com.dsds11s.saa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.dsds11s.saa.ui.awards.AwardDetailScreen
import com.dsds11s.saa.ui.awards.LocalAwardRepository
import com.dsds11s.saa.ui.home.HomeScreen
import com.dsds11s.saa.ui.kudos.AllKudosScreen
import com.dsds11s.saa.ui.kudos.CommunityStandardsScreen
import com.dsds11s.saa.ui.kudos.KudosDetailScreen
import com.dsds11s.saa.ui.kudos.KudosScreen
import com.dsds11s.saa.ui.kudos.NewKudoScreen
import com.dsds11s.saa.ui.locale.LocalizedApp
import com.dsds11s.saa.ui.login.LoginScreen
import com.dsds11s.saa.ui.notifications.NotificationItem
import com.dsds11s.saa.ui.notifications.NotificationType
import com.dsds11s.saa.ui.notifications.NotificationsScreen
import com.dsds11s.saa.ui.notifications.notificationsMockList
import com.dsds11s.saa.ui.theme.SAATheme

/** Top-level destinations — simple state-based navigation (KISS, no nav library needed). */
private enum class AppScreen {
    LOGIN,
    HOME,
    AWARDS,
    KUDOS,
    NEW_KUDO,
    ALL_KUDOS,
    KUDO_DETAIL,
    COMMUNITY_STANDARDS,
    NOTIFICATIONS,
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SAATheme {
                // Wraps the whole nav graph with a locale-overridden context so language
                // switching (header/login selector) recomposes all strings instantly.
                LocalizedApp {
                    val allAwards = remember { LocalAwardRepository.all() }
                    var screen by rememberSaveable { mutableStateOf(AppScreen.LOGIN) }
                    // Currently-selected award shown on the Awards detail screen.
                    var awardId by rememberSaveable { mutableStateOf(allAwards.first().id) }
                    // Currently-viewed Kudo (detail screen) + the screen to return to on back.
                    var kudoId by rememberSaveable { mutableStateOf("") }
                    var kudoDetailFrom by rememberSaveable { mutableStateOf(AppScreen.KUDOS) }
                    // Opens the Kudo detail, remembering the origin screen for back navigation.
                    val openKudoDetail: (String) -> Unit = { id ->
                        kudoId = id
                        kudoDetailFrom = screen
                        screen = AppScreen.KUDO_DETAIL
                    }
                    // Other-user profile: tapped person's name + the screen to return to on back.
                    // ── Notifications: shared unread state drives the header bell badge ──
                    var notifications by remember { mutableStateOf(notificationsMockList) }
                    val unreadCount = notifications.count { !it.isRead }
                    // Screen to return to when leaving Notifications (opened from Home or Kudos bell).
                    var notificationsFrom by rememberSaveable { mutableStateOf(AppScreen.HOME) }
                    // Community Standards is reachable from the New Kudo editor AND the Notifications
                    // "content hidden" link — remember the origin so back returns to the right screen.
                    var communityStandardsFrom by rememberSaveable { mutableStateOf(AppScreen.NEW_KUDO) }
                    // Profile is reachable from the bottom nav (back → Home) AND a Notifications tap
                    // (back → Notifications) — track the origin so back returns correctly.
                    var profileFrom by rememberSaveable { mutableStateOf(AppScreen.HOME) }
                    // ── Search: live query + session recent-searches (in-memory) + origin ──
                    val markNotificationRead: (String) -> Unit = { id ->
                        notifications = notifications.map { if (it.id == id) it.copy(isRead = true) else it }
                    }
                    // Tap a notification: mark it read, then route per type. Secret Box & Admin Review
                    // have no destination screen yet → mark-read only. Content-hidden routes via its
                    // inline "Tiêu chuẩn cộng đồng" link instead (onCommunityLinkClick below).
                    val onNotificationTap: (NotificationItem) -> Unit = { item ->
                        markNotificationRead(item.id)
                        when (item.type) {
                            NotificationType.KUDOS_RECEIVED,
                            NotificationType.HEART_RECEIVED,
                            -> openKudoDetail(item.postId ?: item.id)
                            NotificationType.LEVEL_UP,
                            NotificationType.BADGE_COLLECTED,
                            -> {
                                profileFrom = AppScreen.NOTIFICATIONS
                            }
                            NotificationType.SECRET_BOX_UNLOCK,
                            NotificationType.CONTENT_HIDDEN,
                            NotificationType.REVIEW_REQUEST,
                            -> { /* mark-read only: no destination */ }
                        }
                    }
                    // Opens the Awards detail at the given award index (clamped).
                    val openAward: (Int) -> Unit = { index ->
                        awardId = (allAwards.getOrNull(index) ?: allAwards.first()).id
                        screen = AppScreen.AWARDS
                    }
                    // Bottom-nav routing shared by the inner screens (0=Home,1=Awards,2=Kudos,3=Profile).
                    val onTab: (Int) -> Unit = { tab ->
                        when (tab) {
                            0 -> screen = AppScreen.HOME
                            1 -> openAward(0)
                            2 -> screen = AppScreen.KUDOS
                        }
                    }
                    // Back: NEW_KUDO/ALL_KUDOS → KUDOS, KUDO_DETAIL → origin,
                    // COMMUNITY_STANDARDS → NEW_KUDO, AWARDS/KUDOS → HOME, HOME → LOGIN (no app exit).
                    BackHandler(enabled = screen != AppScreen.LOGIN) {
                        screen =
                            when (screen) {
                                AppScreen.NEW_KUDO -> AppScreen.KUDOS
                                AppScreen.ALL_KUDOS -> AppScreen.KUDOS
                                AppScreen.KUDO_DETAIL -> kudoDetailFrom
                                AppScreen.COMMUNITY_STANDARDS -> communityStandardsFrom
                                AppScreen.NOTIFICATIONS -> notificationsFrom
                                AppScreen.HOME -> AppScreen.LOGIN
                                else -> AppScreen.HOME
                            }
                    }
                    when (screen) {
                        AppScreen.LOGIN -> LoginScreen(onLoginClick = { screen = AppScreen.HOME })
                        AppScreen.HOME ->
                            HomeScreen(
                                onOpenAward = openAward,
                                onOpenKudos = { screen = AppScreen.KUDOS },
                                // Hero "ABOUT AWARD" → Awards detail; "ABOUT KUDOS" + Kudos "Chi tiết" → Kudos screen.
                                onAboutAwardClick = { openAward(0) },
                                onAboutKudosClick = { screen = AppScreen.KUDOS },
                                onKudosDetailClick = { screen = AppScreen.KUDOS },
                                // Home FAB (write-kudos pill) → New Kudo composer.
                                onWriteKudosClick = { screen = AppScreen.NEW_KUDO },
                                onNotificationClick = {
                                    notificationsFrom = AppScreen.HOME
                                    screen = AppScreen.NOTIFICATIONS
                                },
                                onSearchClick = {},
                                hasNotificationBadge = unreadCount > 0,
                            )
                        AppScreen.AWARDS ->
                            AwardDetailScreen(
                                award = allAwards.firstOrNull { it.id == awardId } ?: allAwards.first(),
                                awards = allAwards,
                                onAwardSelected = { awardId = it },
                                onTabSelected = onTab,
                                // Award screen's Kudos "Chi tiết" CTA → Kudos screen.
                                onKudosDetailClick = { screen = AppScreen.KUDOS },
                                onNotificationClick = {
                                    notificationsFrom = AppScreen.AWARDS
                                    screen = AppScreen.NOTIFICATIONS
                                },
                                onSearchClick = {},
                                hasNotificationBadge = unreadCount > 0,
                            )
                        AppScreen.KUDOS ->
                            KudosScreen(
                                onSendKudos = { screen = AppScreen.NEW_KUDO },
                                onKudosDetail = openKudoDetail,
                                onViewAllKudos = { screen = AppScreen.ALL_KUDOS },
                                onTabSelected = onTab,
                                onPersonClick = {},
                                onNotificationClick = {
                                    notificationsFrom = AppScreen.KUDOS
                                    screen = AppScreen.NOTIFICATIONS
                                },
                                onSearchClick = {},
                                hasNotificationBadge = unreadCount > 0,
                            )
                        AppScreen.NEW_KUDO ->
                            NewKudoScreen(
                                onCancel = { screen = AppScreen.KUDOS },
                                onSend = { screen = AppScreen.KUDOS },
                                onTabSelected = onTab,
                                onCommunityStandards = {
                                    communityStandardsFrom = AppScreen.NEW_KUDO
                                    screen = AppScreen.COMMUNITY_STANDARDS
                                },
                            )
                        AppScreen.ALL_KUDOS ->
                            AllKudosScreen(
                                onBack = { screen = AppScreen.KUDOS },
                                onKudosDetail = openKudoDetail,
                                onTabSelected = onTab,
                                onPersonClick = {},
                            )
                        AppScreen.KUDO_DETAIL ->
                            KudosDetailScreen(
                                postId = kudoId,
                                onBack = { screen = kudoDetailFrom },
                                onTabSelected = onTab,
                            )
                        AppScreen.COMMUNITY_STANDARDS ->
                            CommunityStandardsScreen(
                                onBack = { screen = communityStandardsFrom },
                            )
                        AppScreen.NOTIFICATIONS ->
                            NotificationsScreen(
                                notifications = notifications,
                                onBack = { screen = notificationsFrom },
                                onMarkAllRead = {
                                    notifications = notifications.map { it.copy(isRead = true) }
                                },
                                onNotificationClick = onNotificationTap,
                                onCommunityLinkClick = { item ->
                                    markNotificationRead(item.id)
                                    communityStandardsFrom = AppScreen.NOTIFICATIONS
                                    screen = AppScreen.COMMUNITY_STANDARDS
                                },
                            )
                    }
                }
            }
        }
    }
}
