package com.dsds11s.saa.ui.awards

/**
 * In-memory award repository. Provides the 6 SAA 2025 awards in dropdown display order.
 *
 * All text content extracted verbatim from MoMorph specs:
 *   - Signature 2025 - Creator  → screen O98TwiHaJe
 *   - MVP                       → screen b2BuS8HYIt
 *   - Top Talent                → screen c-QM3_zjkG
 *   - Top Project               → screen FQoJZLkG_d
 *   - Top Project Leader        → screen QQvsfK3yaK
 *   - Best Manager              → screen 7y195PPTxQ
 */
object LocalAwardRepository {
    fun all(): List<Award> = awards

    private val awards =
        listOf(
            Award(
                id = "signature-creator",
                name = "Signature 2025 - Creator",
                description =
                    "Giải thưởng Signature vinh danh cá nhân hoặc tập thể thể hiện tinh thần đặc trưng mà " +
                        "Sun* hướng tới trong từng thời kỳ. Trong năm 2025, giải thưởng Signature vinh danh Creator - " +
                        "cá nhân/tập thể mang tư duy chủ động và nhạy bén, luôn nhìn thấy cơ hội trong thách thức " +
                        "và tiên phong trong hành động.",
                quantity = "01",
                quantityUnit = "Cá nhân hoặc tập thể",
                individualPrize = "5.000.000 VNĐ",
                teamPrize = "8.000.000 VNĐ",
            ),
            Award(
                id = "mvp",
                name = "MVP (Most Valuable Person)",
                description =
                    "Giải thưởng MVP vinh danh cá nhân xuất sắc nhất năm – gương mặt tiêu biểu đại diện cho " +
                        "toàn bộ tập thể Sun*. Họ là người đã thể hiện năng lực vượt trội, tinh thần cống hiến bền bỉ, " +
                        "và tầm ảnh hưởng sâu rộng, để lại dấu ấn mạnh mẽ trong hành trình của Sun* suốt năm qua. " +
                        "Không chỉ nổi bật bởi hiệu suất và kết quả công việc, họ còn là nguồn cảm hứng lan tỏa – " +
                        "thông qua suy nghĩ, hành động và ảnh hưởng tích cực của mình đối với tập thể. MVP là người " +
                        "hội tụ đầy đủ phẩm chất của người Sun* ưu tú, đồng thời mang trên mình trọng trách lớn lao: " +
                        "trở thành hình mẫu đại diện cho con người và tinh thần Sun*, góp phần dẫn dắt tập thể vươn " +
                        "tới những đỉnh cao mới.",
                quantity = "01",
                quantityUnit = "Cá nhân",
                individualPrize = "15.000.000 VNĐ",
                teamPrize = null,
            ),
            Award(
                id = "top-talent",
                name = "Top Talent",
                description =
                    "Giải thưởng Top Talent vinh danh những cá nhân xuất sắc toàn diện trên mọi phương diện " +
                        "– từ năng lực chuyên môn vượt trội, đóng góp kinh doanh rõ ràng, đến khả năng truyền cảm " +
                        "hứng và ảnh hưởng tích cực tới tập thể. Đây là những Sun* đã chứng minh bản thân không chỉ " +
                        "bằng kết quả công việc, mà còn bằng tư duy, thái độ và tinh thần cống hiến bền bỉ suốt " +
                        "năm qua.",
                quantity = "10",
                quantityUnit = "Cá nhân",
                individualPrize = "7.000.000 VNĐ",
                teamPrize = null,
            ),
            Award(
                id = "top-project",
                name = "Top Project",
                description =
                    "Giải thưởng Top Project vinh danh các tập thể dự án xuất sắc với kết quả kinh doanh " +
                        "vượt kỳ vọng, hiệu quả vận hành tối ưu và tinh thần làm việc tận tâm. Đây là những dự án " +
                        "tiêu biểu cho sự kết hợp giữa chất lượng chuyên môn cao và giá trị mang lại cho khách hàng " +
                        "cũng như tổ chức, phản ánh đúng tinh thần \"Aim High – Be Agile\" của Sun*.",
                quantity = "02",
                quantityUnit = "Tập thể",
                individualPrize = null,
                teamPrize = "15.000.000 VNĐ",
            ),
            Award(
                id = "top-project-leader",
                name = "Top Project Leader",
                description =
                    "Giải thưởng Top Project Leader vinh danh những nhà quản lý dự án xuất sắc – những " +
                        "người hội tụ năng lực quản lý vững vàng, khả năng truyền cảm hứng mạnh mẽ, và tư duy " +
                        "\"Aim High – Be Agile\" trong mọi bài toán và bối cảnh. Họ là người không chỉ đảm bảo " +
                        "dự án về đích đúng hạn và chất lượng, mà còn kiến tạo môi trường làm việc tích cực, " +
                        "nơi mỗi thành viên trong nhóm có cơ hội phát triển và đóng góp tối đa.",
                quantity = "03",
                quantityUnit = "Cá nhân",
                individualPrize = "7.000.000 VNĐ",
                teamPrize = null,
            ),
            Award(
                id = "best-manager",
                name = "Best Manager",
                description =
                    "Giải thưởng Best Manager vinh danh những nhà lãnh đạo tiêu biểu – người đã dẫn dắt " +
                        "đội ngũ của mình tạo ra kết quả vượt kỳ vọng, tác động nổi bật đến hiệu quả kinh doanh " +
                        "và sự phát triển bền vững của tổ chức. Không chỉ giỏi về chuyên môn, họ còn là người " +
                        "truyền lửa, đồng hành và tạo ra môi trường để mỗi cá nhân trong nhóm tỏa sáng – " +
                        "xứng đáng là tấm gương lãnh đạo của Sun*.",
                quantity = "01",
                quantityUnit = "Cá nhân",
                individualPrize = "10.000.000 VNĐ",
                teamPrize = null,
            ),
        )
}
