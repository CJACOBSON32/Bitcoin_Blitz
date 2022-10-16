package info.cs4518.bitcoinblitz.upgrades

abstract class Upgrade(
	@Transient
	open val id: Int,
	@Transient
	open val name: String,
	@Transient
	open var cost: Long,
	@Transient
	open val description: String,
	var numOwned: Int = 0
)

