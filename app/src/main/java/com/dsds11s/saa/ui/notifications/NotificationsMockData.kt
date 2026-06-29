package com.dsds11s.saa.ui.notifications

// Mock data extracted EXACTLY from Figma [iOS] Notifications (_b68CBWKl5)
// Text content, names, and timestamps are verbatim from the design nodes.

val notificationsMockList =
    listOf(
        NotificationItem(
            id = "n001",
            type = NotificationType.KUDOS_RECEIVED,
            content = "Sunner Huỳnh Dương Xuân Nhật vừa gửi đến bạn lời ghi nhận đầy yêu thương!",
            relativeTime = "15 phút trước",
            isRead = false,
            postId = "k001",
        ),
        NotificationItem(
            id = "n002",
            type = NotificationType.HEART_RECEIVED,
            content = "Wow! Lời nhắn gửi của bạn cho Sunner <tên Sunner> vừa nhận thêm lượt tim!",
            relativeTime = "1 giờ trước",
            isRead = true,
            postId = "k002",
        ),
        NotificationItem(
            id = "n003",
            type = NotificationType.SECRET_BOX_UNLOCK,
            content = "Chúc mừng! Bạn vừa nhận được lượt mở Secret Box mới! Click vào đây để mở ngay nhé!",
            relativeTime = "1 ngày trước",
            isRead = true,
        ),
        NotificationItem(
            id = "n004",
            type = NotificationType.LEVEL_UP,
            content =
                "Bạn nhận được <X> lời nhắn gửi từ đồng nghiệp và thăng hạng <tên level>!\n" +
                    "Tiếp tục lan tỏa năng lượng tích cực đến đồng nghiệp nhé!",
            relativeTime = "1 ngày trước",
            isRead = true,
        ),
        NotificationItem(
            id = "n005",
            type = NotificationType.CONTENT_HIDDEN,
            content =
                "Tiếc quá! Bạn có một lời nhắn bị tạm ẩn vì \"vướng\" một số tiêu chuẩn! " +
                    "Hãy xem các tiêu chuẩn và gửi lại cho đồng đội nhé!",
            relativeTime = "1 tháng trước",
            isRead = true,
            communityLink = "Tiêu chuẩn cộng đồng",
        ),
        NotificationItem(
            id = "n006",
            type = NotificationType.BADGE_COLLECTED,
            content =
                "Chúc mừng bạn đã thu thập đủ 6 huy hiệu của SAA. Bạn đã nhận được phần quà từ " +
                    "BTC chính là <X>. BTC sẽ liên hệ để gửi quà đến bạn vào cuối sự kiện.",
            relativeTime = "1 tháng trước",
            isRead = true,
        ),
        NotificationItem(
            id = "n007",
            type = NotificationType.REVIEW_REQUEST,
            content =
                "\"Có <x> lời nhắn cần bạn xem xét!\"\n" +
                    "Một lời nhắn vừa bị hệ thống gắn cờ nghi ngờ vi phạm tiêu chuẩn. Vui lòng kiểm tra " +
                    "và xác nhận trạng thái: Hợp lệ / Tạm ẩn / Reject.",
            relativeTime = "1 tháng trước",
            isRead = true,
        ),
    )
