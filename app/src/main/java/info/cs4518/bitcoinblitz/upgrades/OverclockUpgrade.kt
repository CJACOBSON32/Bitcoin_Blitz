package info.cs4518.bitcoinblitz.upgrades

data class OverclockUpgrade(
	override val id: Int,
	override val name: String,
	override var cost: Long,
	override val description: String,
	val cooldownMultiplier: Float,
	val boostMultiplier: Float,
	val boostAdditive: Int
): Upgrade(id, name, cost, description)