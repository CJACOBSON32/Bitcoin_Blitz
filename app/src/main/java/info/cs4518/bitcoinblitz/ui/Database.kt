package info.cs4518.bitcoinblitz.ui

import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.ktx.Firebase
import info.cs4518.bitcoinblitz.PlayerViewModel
import info.cs4518.bitcoinblitz.upgrades.Upgrade

class Database(val viewModel: PlayerViewModel) {
	init {
		val dbSettings = firestoreSettings { isPersistenceEnabled = true }
		db.firestoreSettings = dbSettings
		val cSettings = firestoreSettings {
			cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
		}
		db.firestoreSettings = cSettings

		FirebaseInstallations.getInstance().id.addOnSuccessListener { result ->
			installId = result

			db.collection("UserProgress").document(installId!!).get()
				.addOnSuccessListener { result ->
					if (result.exists()) {
						viewModel.wallet.value = result["bitcoinCount"] as Long
						for (map in result["upgrades"] as List<HashMap<String, Any>>) {
							val id = (map["id"] as Long).toInt()
							val numOwned = (map["numOwned"] as Long).toInt()

							viewModel.upgrades.upgradeMap[id]?.numOwned = numOwned
						}
						viewModel.upgrades.recalculateAll()
					} else {
						backupData()
					}
				}
		}
	}

	fun backupData() {
		// Create a list of upgrades to push to the database
		val upgradeMaps: MutableList<HashMap<String, Any>> = ArrayList()
		for (upgrade in viewModel.upgrades.allUpgrades)
			upgradeMaps.add(upgradeToMap(upgrade))

		// Create new userdata
		val newUserProgress = hashMapOf(
			"bitcoinCount" to viewModel.bitcoinCount,
			"upgrades" to upgradeMaps
		)

		db.collection("UserProgress").document(installId!!).set(newUserProgress)
	}

	fun upgradeToMap(upgrade: Upgrade): HashMap<String, Any> {
		return hashMapOf(
			"id" to upgrade.id,
			"numOwned" to upgrade.numOwned
		)
	}

	val db get() = Firebase.firestore
	var installId: String? = null

	private fun docReference(){
		val allUpgradesRef = db.collection("Upgrades").document()
	}

	fun incrementUpgrade(upgrade: Upgrade){

	}

	fun getPassiveIncome(): Float {
		val allPassiveUpgrades = db.collection("Upgrades")
//		allPassiveUpgrades.whereArrayContains()

		var sum = 0f
		return sum
	}
}