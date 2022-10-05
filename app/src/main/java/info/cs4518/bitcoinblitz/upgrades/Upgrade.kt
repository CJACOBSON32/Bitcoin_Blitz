package info.cs4518.bitcoinblitz.upgrades

abstract class Upgrade() {
    abstract var id: Int
    abstract var name: String
    abstract var numOwned: Int
    abstract var cost: Float
    abstract var description: String
}