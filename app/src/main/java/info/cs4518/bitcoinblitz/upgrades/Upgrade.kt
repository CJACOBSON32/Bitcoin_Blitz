package info.cs4518.bitcoinblitz.upgrades

abstract class Upgrade(
	val id: Int? = null,
	val name: String? = null,
	val numOwned: Int? = null,
	val cost: Float? = null,
	val description: String? = null
)