package info.cs4518.bitcoinblitz.upgrades

import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class UpgradeTracker {

	val db = Firebase.firestore

	val activeUpgrades: MutableList<ActiveUpgrade> = ArrayList()
	val passiveUpgrades: MutableList<PassiveUpgrade> = ArrayList()
	val overclockUpgrades: MutableList<OverclockUpgrade> = ArrayList()

	val allUpgrades get() = activeUpgrades + passiveUpgrades + overclockUpgrades

	private fun setUp(){
		val dbSettings = firestoreSettings { isPersistenceEnabled = true }
		db.firestoreSettings = dbSettings
		val cSettings = firestoreSettings {
			cacheSizeBytes = FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED
		}
		db.firestoreSettings = cSettings
		docReference()
	}

	/**
	 * Loads upgrades from a json file
	 *
	 * @param stream Inputstream for json file containing upgrades
	 */
	fun loadUpgrades(stream: InputStream): List<Upgrade> {
		// Get JSON object from InputStream
		val JSONString: String = convertStreamToString(stream)
		val jsonObject = JsonParser.parseString(JSONString).asJsonObject

		// Parse upgrades from JSON
		val g = Gson()

		// Active Upgrades
		val active = jsonObject.get("Active").asJsonArray
		val activeListType = object : TypeToken<List<ActiveUpgrade>>() {}.type
		activeUpgrades.addAll(g.fromJson(active.toString(), activeListType))

		// Passive Upgrades
		val passive = jsonObject.get("Passive").asJsonArray
		val passiveListType = object : TypeToken<List<ActiveUpgrade>>() {}.type
		passiveUpgrades.addAll(g.fromJson(passive.toString(), passiveListType))

		// Overclock Upgrades
		val overclock = jsonObject.get("Overclock").asJsonArray
		val overclockListType = object : TypeToken<List<ActiveUpgrade>>() {}.type
		overclockUpgrades.addAll(g.fromJson(overclock.toString(), overclockListType))

		return allUpgrades
	}

	@Throws(Exception::class)
	fun convertStreamToString(stream: InputStream): String {
		val reader = BufferedReader(InputStreamReader(stream))
		val sb = StringBuilder()
		var line: String? = null
		while (reader.readLine().also { line = it } != null) {
			sb.append(line).append("\n")
		}
		reader.close()
		return sb.toString()
	}

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