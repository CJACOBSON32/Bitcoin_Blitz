package info.cs4518.bitcoinblitz.upgrades

class PassiveUpgrade(override var id: Int,
					 override var name: String,
					 override var numOwned: Int,
					 override var cost: Float,
					 override var description: String) : Upgrade() {

}