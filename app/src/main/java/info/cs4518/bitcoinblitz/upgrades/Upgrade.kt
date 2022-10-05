package info.cs4518.bitcoinblitz.upgrades

abstract class Upgrade() {
    abstract var id: Int
    abstract var numOwned: Int
}