package com.example.core.rules

/**
 * Canonical Cyberpunk RED Price Categories (Eurodollars).
 */
enum class PriceCategory(
    val labelPt: String,
    val referenceCostEb: Int
) {
    CHEAP("Barato", 10),
    EVERYDAY("Comum", 20),
    COSTLY("Custoso", 50),
    PREMIUM("Premium", 100),
    EXPENSIVE("Caro", 500),
    VERY_EXPENSIVE("Muito Caro", 1000),
    LUXURY("Luxo", 5000),
    SUPER_LUXURY("Superluxo", 10000),
    DIRT_CHEAP("Mixaria", 5)
}

sealed interface PurchaseOutcome {
    data class Approved(val remainingEurodollars: Int) : PurchaseOutcome
    data class InsufficientFunds(val currentEurodollars: Int, val requiredEurodollars: Int) : PurchaseOutcome
}

object EconomyEngine {
    fun attemptPurchase(
        currentEurodollars: Int,
        itemCostEb: Int
    ): PurchaseOutcome {
        val cost = itemCostEb.coerceAtLeast(0)
        return if (currentEurodollars >= cost) {
            PurchaseOutcome.Approved(currentEurodollars - cost)
        } else {
            PurchaseOutcome.InsufficientFunds(currentEurodollars, cost)
        }
    }
}
