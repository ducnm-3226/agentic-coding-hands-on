package com.dsds11s.saa.ui.kudos

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dsds11s.saa.ui.theme.Montserrat
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * HIGHLIGHT KUDOS section — Figma mms_B_Highlight (6885:9084).
 * Contains: section header, filter row, HorizontalPager carousel, pagination dots.
 */
@Composable
fun KudosHighlightSection(
    posts: List<KudosPost>,
    likedIds: Set<String>,
    selectedHashtag: String?,
    selectedDepartment: String?,
    hashtagOptions: List<String>,
    departmentOptions: List<String>,
    onHashtagSelected: (String?) -> Unit,
    onDepartmentSelected: (String?) -> Unit,
    onLikeToggle: (String) -> Unit,
    onCopyLink: (String) -> Unit,
    onDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    onPersonClick: ((String) -> Unit)? = null,
) {
    val pagerState = rememberPagerState(pageCount = { maxOf(posts.size, 1) })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        // Section header block — node mms_B.1_header (6885:9085).
        HighlightSectionHeader(
            selectedHashtag = selectedHashtag,
            selectedDepartment = selectedDepartment,
            hashtagOptions = hashtagOptions,
            departmentOptions = departmentOptions,
            onHashtagSelected = onHashtagSelected,
            onDepartmentSelected = onDepartmentSelected,
            modifier = Modifier.padding(horizontal = 20.dp),
        )

        // Carousel + navigation controls
        if (posts.isEmpty()) {
            KudosEmptyState(modifier = Modifier.padding(horizontal = 20.dp))
        } else {
            // HorizontalPager — node mms_B.2_HIGHLIGHT KUDOS (6885:9090).
            HorizontalPager(
                state = pagerState,
                contentPadding = PaddingValues(horizontal = 44.dp),
                pageSpacing = 8.dp,
                modifier = Modifier.fillMaxWidth(),
            ) { page ->
                val pageOffset =
                    (pagerState.currentPage - page) +
                        pagerState.currentPageOffsetFraction
                val alpha = 1f - (abs(pageOffset) * 0.4f).coerceIn(0f, 0.4f)
                val post = posts[page]
                KudosCard(
                    post = post,
                    isLiked = likedIds.contains(post.id),
                    onLikeToggle = { onLikeToggle(post.id) },
                    onCopyLink = { onCopyLink(post.id) },
                    onDetail = { onDetail(post.id) },
                    modifier = Modifier.fillMaxWidth().alpha(alpha),
                    onPersonClick = onPersonClick,
                )
            }

            // Navigation controls — node mms_B.5_slide (6885:9098).
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                NavArrow(
                    pointsLeft = true,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                (pagerState.currentPage - 1).coerceAtLeast(0),
                            )
                        }
                    },
                )
                Text(
                    text = "${pagerState.currentPage + 1} / ${posts.size}",
                    color = HighlightEyebrowColor,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    fontFamily = Montserrat,
                )
                NavArrow(
                    pointsLeft = false,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                (pagerState.currentPage + 1).coerceAtMost(posts.size - 1),
                            )
                        }
                    },
                )
            }
        }
    }
}
