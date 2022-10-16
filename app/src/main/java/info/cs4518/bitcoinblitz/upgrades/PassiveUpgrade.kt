package info.cs4518.bitcoinblitz.upgrades

data class PassiveUpgrade (
	override val id: Int,
	override val name: String,
	override var cost: Long,
	override val description: String,
	val passiveIncome: Float
): Upgrade(id, name, cost, description)