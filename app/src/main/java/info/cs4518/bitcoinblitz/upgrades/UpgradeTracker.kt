package info.cs4518.bitcoinblitz.upgrades

import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase

class UpgradeTracker {

	val db = Firebase.firestore


	private fun setUp(){
		val dbSettings = firestoreSettings { isPersistenceEnabled = true }
		db.firestoreSettings = dbSettings
		val cSettings = firestoreSettings {
			cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
		}
		db.firestoreSettings = cSettings
		docReference()
		setDocument()
	}

	private fun docReference(){
		val allUpgradesRef = db.collection("Upgrades").document()
	}

	private fun setDocument(){
		val upgrade1 = PassiveUpgrade(1, "intern,", 0, 10f,
			"Someone to do you're busy work.", 1f)
		val upgrade2 = PassiveUpgrade(2, "Associate,", 0, 300f,
			"Clocking in from 9-5", 10f)
		val upgrade3 =
			PassiveUpgrade(3, "Manager,", 0, 6000f,
				"Going to really miss the hard work you do.", 100f)
		val upgrade4 =
			PassiveUpgrade(4, "Director,", 0, 11000f,
				"Submitting your ideas tomorrow", 1000f)
		val upgrade5 =
			PassiveUpgrade(5, "Vice President,", 0, 190000f,
				"Arriving late and leaving early.", 10000f)
		val upgrade6 = PassiveUpgrade(6, "President,", 0, 3200000f,
			"World's best boss!", 100000f)
		val upgrade7 = PassiveUpgrade(7, "CEO,", 0, 53000000f,
			"Money moves", 1000000f)

		//All Active Upgrades
		val upgrade8 = ActiveUpgrade(8, "UTX 5050,", 0, 10f,
			"Potato powered", 1)
		val upgrade9 = ActiveUpgrade(9, "UTX 5060,", 0, 900f,
			"Handles 720p", 2)
		val upgrade10 = ActiveUpgrade(10, "UTX 5070,", 0, 2500f,
			"Mine diamonds", 3)
		val upgrade11 = ActiveUpgrade(11, "UTX 5080,", 0, 7300f,
			"Shaders support", 4)
		val upgrade12 = ActiveUpgrade(12, "UTX 5090,", 0, 28000f,
			"Raytracing support", 5)
		val upgrade13 = ActiveUpgrade(13, "UTX 6050,", 0, 96000f,
			"Diamond hands", 10)
		val upgrade14 = ActiveUpgrade(14, "UTX 6060,", 0, 530000f,
			"Holding on tight", 25)
		val upgrade15 = ActiveUpgrade(15, "UTX 6070,", 0, 1870000f,
			"Astronomical", 100)
		val upgrade16 = ActiveUpgrade(16, "UTX 6080,", 0, 10000000f,
			"To the moon!", 250)
		val upgrade17 = ActiveUpgrade(17, "UTX 6090,", 0, 420420420f,
			"To infinity and beyond!", 1000)

		//ALL Overclock Upgrades
		val upgrade18 = OverclockUpgrade(18, "Fan Cooling", 0, 1000f,
			"LED fans", 2)
		val upgrade19 = OverclockUpgrade(19, "Water Cooling", 0, 100000f,
			"LED fans", 3)
		val upgrade20 = OverclockUpgrade(20, "Liquid Nitrogen Cooling", 0, 10000000f,
			"LED fans", 4)

		db.collection("Upgrades").add(upgrade1)
		db.collection("Upgrades").add(upgrade2)
		db.collection("Upgrades").add(upgrade3)
		db.collection("Upgrades").add(upgrade4)
		db.collection("Upgrades").add(upgrade5)
		db.collection("Upgrades").add(upgrade6)
		db.collection("Upgrades").add(upgrade7)
		db.collection("Upgrades").add(upgrade8)
		db.collection("Upgrades").add(upgrade9)
		db.collection("Upgrades").add(upgrade10)
		db.collection("Upgrades").add(upgrade11)
		db.collection("Upgrades").add(upgrade12)
		db.collection("Upgrades").add(upgrade13)
		db.collection("Upgrades").add(upgrade14)
		db.collection("Upgrades").add(upgrade15)
		db.collection("Upgrades").add(upgrade16)
		db.collection("Upgrades").add(upgrade17)
		db.collection("Upgrades").add(upgrade18)
		db.collection("Upgrades").add(upgrade19)
		db.collection("Upgrades").add(upgrade20)
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