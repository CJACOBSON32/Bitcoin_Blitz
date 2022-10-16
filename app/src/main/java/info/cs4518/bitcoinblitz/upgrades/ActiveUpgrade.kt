package info.cs4518.bitcoinblitz.upgrades

data class ActiveUpgrade(
    override val id: Int,
    override val name: String,
    override var cost: Long,
    override val description: String,
    val clickPotencyAdditive: Int
): Upgrade(id, name, cost, description)
