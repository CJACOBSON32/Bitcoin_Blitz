package info.cs4518.bitcoinblitz.upgrades

data class ActiveUpgrade(
    val id: Int? = null,
    val name: String? = null,
    val numOwned: Int? = null,
    val cost: Float? = null,
    val description: String? = null,
    val clickPotencyModifier: Int? = null
)
