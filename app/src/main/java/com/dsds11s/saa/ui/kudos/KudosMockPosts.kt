package com.dsds11s.saa.ui.kudos

// Mock post data extracted from Figma content — screens fO0Kt19sZZ, V5GRjAdJyb
// Figma node mms_B.3_KUDO (6885:8424)

/**
 * 10 posts spanning >= 3 departments with varied hashtags.
 * Names from Figma Spotlight board: Đỗ hoàng Hiệp, Dương thúy An, Mai phương Thúy,
 * Nguyễn Văn Quy, Nguyễn Bá Chức, Nguyễn Hoàng Linh, Lê Kiều Trang.
 */
val kudosMockPosts =
    listOf(
        KudosPost(
            id = "k001",
            senderName = "Nguyễn Hoàng Linh",
            receiverName = "Đỗ hoàng Hiệp",
            department = "CEVC2",
            badgeLevel = 5,
            message =
                "Cảm ơn bạn đã luôn nhiệt tình hỗ trợ team trong dự án vừa rồi. " +
                    "Bạn thực sự là nguồn cảm hứng lớn cho mọi người!",
            hashtags = listOf("#Dedicated", "#Inspiring"),
            heartCount = 12,
            liked = false,
            timestamp = "10:00 - 10/30/2025",
            badgeLabel = "IDOL GIỚI TRẺ",
            title = "NGƯỜI HÙNG CỦA LÒNG EM",
            galleryImageCount = 5,
        ),
        KudosPost(
            id = "k002",
            senderName = "Lê Kiều Trang",
            receiverName = "Dương thúy An",
            department = "CEVC3",
            badgeLevel = 4,
            message =
                "Dương thúy An đã thực sự nỗ lực không ngừng để hoàn thành sprint này đúng deadline. " +
                    "Rất tự hào khi được làm việc cùng bạn!",
            hashtags = listOf("#TeamPlayer", "#Dedicated"),
            heartCount = 8,
            liked = true,
            timestamp = "09:15 - 10/29/2025",
            badgeLabel = "SAO SÁNG TẠO",
            title = "CHIẾN BINH SPRINT",
            galleryImageCount = 3,
        ),
        KudosPost(
            id = "k003",
            senderName = "Đỗ hoàng Hiệp",
            receiverName = "Nguyễn Văn Quy",
            department = "OPD",
            badgeLevel = 3,
            message =
                "Bạn đã giải quyết vấn đề infrastructure một cách xuất sắc, giúp cả team tiết kiệm " +
                    "rất nhiều thời gian. Cảm ơn sự chuyên nghiệp của bạn.",
            hashtags = listOf("#Creative", "#Leadership"),
            heartCount = 15,
            liked = false,
            timestamp = "14:30 - 10/28/2025",
            badgeLabel = "CHUYÊN GIA KỸ THUẬT",
            title = "BẬC THẦY HẠ TẦNG",
        ),
        KudosPost(
            id = "k004",
            senderName = "Mai phương Thúy",
            receiverName = "Nguyễn Bá Chức",
            department = "CEVC4",
            badgeLevel = 5,
            message =
                "Cảm ơn bạn đã luôn là người lắng nghe và đưa ra những phản hồi xây dựng trong " +
                    "các buổi review. Bạn là cột trụ tinh thần của team.",
            hashtags = listOf("#Inspiring", "#TeamPlayer", "#Leadership"),
            heartCount = 20,
            liked = true,
            timestamp = "08:00 - 10/28/2025",
            badgeLabel = "IDOL GIỚI TRẺ",
        ),
        KudosPost(
            id = "k005",
            senderName = "Nguyễn Văn Quy",
            receiverName = "Lê Kiều Trang",
            department = "Infra",
            badgeLevel = 4,
            message =
                "Lê Kiều Trang đã xây dựng CI/CD pipeline mới giúp team deploy nhanh hơn 3 lần. " +
                    "Công sức của bạn xứng đáng được ghi nhận!",
            hashtags = listOf("#Dedicated", "#Creative"),
            heartCount = 18,
            liked = false,
            timestamp = "11:45 - 10/27/2025",
            badgeLabel = "SAO SÁNG TẠO",
        ),
        KudosPost(
            id = "k006",
            senderName = "Nguyễn Bá Chức",
            receiverName = "Nguyễn Hoàng Linh",
            department = "CEVC1",
            badgeLevel = 3,
            message =
                "Cảm ơn bạn đã onboard member mới cực kỳ tận tâm, giúp họ hòa nhập team chỉ trong " +
                    "vài ngày. Đây là kỹ năng leader thực sự!",
            hashtags = listOf("#TeamPlayer", "#Leadership"),
            heartCount = 7,
            liked = false,
            timestamp = "16:00 - 10/26/2025",
            badgeLabel = "NHÀ LÃNH ĐẠO",
        ),
        KudosPost(
            id = "k007",
            senderName = "Dương thúy An",
            receiverName = "Mai phương Thúy",
            department = "CEVC2",
            badgeLevel = 5,
            message =
                "Bạn đã thiết kế UI/UX hoàn hảo cho tính năng mới — mọi user feedback đều rất tích cực. " +
                    "Tài năng thiết kế của bạn thật xuất sắc!",
            hashtags = listOf("#Creative", "#Inspiring"),
            heartCount = 25,
            liked = true,
            timestamp = "13:20 - 10/25/2025",
            badgeLabel = "IDOL GIỚI TRẺ",
        ),
        KudosPost(
            id = "k008",
            senderName = "Đỗ hoàng Hiệp",
            receiverName = "Dương thúy An",
            department = "CEVC3",
            badgeLevel = 4,
            message =
                "Sự tận tụy của Dương thúy An trong việc viết test case chi tiết đã giúp team " +
                    "tránh được rất nhiều bug production. Cảm ơn bạn rất nhiều.",
            hashtags = listOf("#Dedicated"),
            heartCount = 10,
            liked = false,
            timestamp = "10:10 - 10/24/2025",
            badgeLabel = "CHUYÊN GIA KỸ THUẬT",
        ),
        KudosPost(
            id = "k009",
            senderName = "Lê Kiều Trang",
            receiverName = "Nguyễn Văn Quy",
            department = "OPD",
            badgeLevel = 3,
            message =
                "Cảm ơn Nguyễn Văn Quy đã xử lý incident production lúc 2 giờ sáng một cách " +
                    "bình tĩnh và chuyên nghiệp. Bạn là người hùng thầm lặng!",
            hashtags = listOf("#Dedicated", "#TeamPlayer", "#Leadership"),
            heartCount = 30,
            liked = false,
            timestamp = "07:30 - 10/23/2025",
            badgeLabel = "NHÀ LÃNH ĐẠO",
        ),
        KudosPost(
            id = "k010",
            senderName = "Nguyễn Hoàng Linh",
            receiverName = "Nguyễn Bá Chức",
            department = "CEVC4",
            badgeLevel = 5,
            message =
                "Nguyễn Bá Chức đã dẫn dắt workshop về clean code rất hay, " +
                    "giúp cả team nâng cao kỹ năng đáng kể trong tháng vừa rồi.",
            hashtags = listOf("#Inspiring", "#Creative", "#Leadership"),
            heartCount = 22,
            liked = true,
            timestamp = "15:00 - 10/22/2025",
            badgeLabel = "CHUYÊN GIA KỸ THUẬT",
        ),
    )

/** Highlight carousel posts (first 5 from mock list). */
val kudosHighlightPosts: List<KudosPost> = kudosMockPosts.take(5)

/** Feed posts (all 10). */
val kudosFeedPosts: List<KudosPost> = kudosMockPosts
