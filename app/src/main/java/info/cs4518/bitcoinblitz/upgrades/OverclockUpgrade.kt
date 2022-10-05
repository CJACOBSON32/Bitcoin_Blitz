package info.cs4518.bitcoinblitz.upgrades

class OverclockUpgrade : Upgrade {
    override var id: Int
    override var numOwned: Int

    constructor(id: Int, numOwned: Int) {
        this.id = id
        this.numOwned = numOwned
    }
}