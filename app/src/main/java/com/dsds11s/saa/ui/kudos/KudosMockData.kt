package com.dsds11s.saa.ui.kudos

// Mock data extracted from Figma content — screens fO0Kt19sZZ, V5GRjAdJyb, 76k69LQPfj
// Posts and feed/highlight slices live in KudosMockPosts.kt (same package).

/** Hashtag values from Figma dropdown (screen V5GRjAdJyb / node 6891:20453) */
val kudosHashtagOptions =
    listOf(
        "#Dedicated",
        "#Inspiring",
        "#TeamPlayer",
        "#Creative",
        "#Leadership",
    )

/**
 * Department values from Figma dropdown (screen 76k69LQPfj / node 6891:21234).
 * Values: CEVC2, CEVC3, CEVC4, CEVC1, OPD, Infra
 */
val kudosDepartmentOptions =
    listOf(
        "CEVC1",
        "CEVC2",
        "CEVC3",
        "CEVC4",
        "OPD",
        "Infra",
    )

/**
 * Top-10 gift recipients — Figma mms_D.3.2 (6885:9259).
 * Names from Figma node text: "Huỳnh Dương Xuân", gift "Nhận được 1 áo phông SAA".
 */
val kudosGiftRecipients =
    listOf(
        GiftRecipient("Huỳnh Dương Xuân", "CEVC2", "Nhận được 1 áo phông SAA"),
        GiftRecipient("Đỗ hoàng Hiệp", "CEVC2", "Nhận được 1 áo phông SAA"),
        GiftRecipient("Dương thúy An", "CEVC3", "Nhận được 1 cốc SAA 2025"),
        GiftRecipient("Nguyễn Văn Quy", "OPD", "Nhận được 1 áo phông SAA"),
        GiftRecipient("Mai phương Thúy", "CEVC2", "Nhận được 1 sticker pack"),
        GiftRecipient("Nguyễn Bá Chức", "CEVC4", "Nhận được 1 cốc SAA 2025"),
        GiftRecipient("Lê Kiều Trang", "Infra", "Nhận được 1 áo phông SAA"),
        GiftRecipient("Nguyễn Hoàng Linh", "CEVC1", "Nhận được 1 sticker pack"),
        GiftRecipient("Trần Thị Minh Châu", "CEVC3", "Nhận được 1 áo phông SAA"),
        GiftRecipient("Phạm Văn Đức", "OPD", "Nhận được 1 cốc SAA 2025"),
    )

/** Personal stats — Figma mms_D.1 (6885:9223), all values 25 per Figma. */
val kudosMockStats =
    KudosStats(
        heartsReceived = 25,
        heartsGiven = 25,
        kudosReceived = 25,
        kudosGiven = 25,
        secretBoxOpened = 25,
        secretBoxUnopened = 25,
    )
