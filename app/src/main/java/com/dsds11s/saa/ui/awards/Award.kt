package com.dsds11s.saa.ui.awards

/**
 * Award data model. Represents one SAA 2025 award entry.
 *
 * @param id            Stable key used for navigation (e.g. "signature-creator")
 * @param name          Display name shown in dropdown and as award title
 * @param description   Full description paragraph shown in detail content block
 * @param quantity      Number of awards given (e.g. "01", "10")
 * @param quantityUnit  Unit label (e.g. "Cá nhân hoặc tập thể", "Cá nhân", "Tập thể")
 * @param individualPrize  Prize amount for individual award; null = row not shown
 * @param teamPrize        Prize amount for team award; null = row not shown
 */
data class Award(
    val id: String,
    val name: String,
    val description: String,
    val quantity: String,
    val quantityUnit: String,
    val individualPrize: String? = null,
    val teamPrize: String? = null,
)
