package info.cs4518.bitcoinblitz.upgrades

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UpgradeTracker {
	val db = Firebase.firestore


	fun addUpgrade(upgrade: Upgrade){
		db.collection("Upgrades")
			.add(upgrade)
	}

	fun getPassiveIncome():Int {
		val allPassiveUpgrades = db.collection("Upgrades")
		allPassiveUpgrades.whereArrayContains()
	}
}