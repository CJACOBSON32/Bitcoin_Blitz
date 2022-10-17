package info.cs4518.bitcoinblitz.upgrades

import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import info.cs4518.bitcoinblitz.PlayerViewModel
import info.cs4518.bitcoinblitz.R
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class UpgradeTracker(val viewModel: PlayerViewModel, val application: Application) {

	val activeUpgrades: MutableList<ActiveUpgrade> = ArrayList()
	val passiveUpgrades: MutableList<PassiveUpgrade> = ArrayList()
	val overclockUpgrades: MutableList<OverclockUpgrade> = ArrayList()

	val allUpgrades get() = activeUpgrades + passiveUpgrades + overclockUpgrades
	val upgradeMap = HashMap<Int, Upgrade>()

	init {
		loadUpgrades(application.resources.openRawResource(R.raw.upgrades))
	}

	fun buy(upgrade: Upgrade) {
		upgrade.numOwned++
		if (upgrade is ActiveUpgrade)
			viewModel.clickPotency += upgrade.clickPotencyAdditive
		else if (upgrade is PassiveUpgrade)
			viewModel.bitcoinPerSecond += upgrade.passiveIncome
	}

	private fun recalculateActive() {
		viewModel.clickPotency = 1 + activeUpgrades.sumOf { it.clickPotencyAdditive*it.numOwned }
	}

	private fun recalculatePassive() {
		viewModel.bitcoinPerSecond = passiveUpgrades.sumOf { it.passiveIncome*it.numOwned }
	}

	fun getOverclockStats(): OverclockStats {
		val stats = OverclockStats(1f, 1f, 2)

		for (upgrade in overclockUpgrades) {
			if (upgrade.numOwned > 0) {
				stats.cooldownMultiplier *= 1/(upgrade.cooldownMultiplier*upgrade.numOwned)
				stats.boostMultiplier += upgrade.boostMultiplier*upgrade.numOwned - 1
				stats.boostAdditive += upgrade.boostAdditive*upgrade.numOwned
			}
		}

		return stats
	}

	fun recalculateAll() {
		recalculateActive()
		recalculatePassive()
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
		val passiveListType = object : TypeToken<List<PassiveUpgrade>>() {}.type
		passiveUpgrades.addAll(g.fromJson(passive.toString(), passiveListType))

		// Overclock Upgrades
		val overclock = jsonObject.get("Overclock").asJsonArray
		val overclockListType = object : TypeToken<List<OverclockUpgrade>>() {}.type
		overclockUpgrades.addAll(g.fromJson(overclock.toString(), overclockListType))

		// Build upgrade hashmap
		for (upgrade in allUpgrades)
			upgradeMap[upgrade.id] = upgrade

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

	data class OverclockStats(
		var cooldownMultiplier: Float,
		var boostMultiplier: Float,
		var boostAdditive: Int
	)
}