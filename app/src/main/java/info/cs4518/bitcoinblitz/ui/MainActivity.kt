package info.cs4518.bitcoinblitz.ui

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import info.cs4518.bitcoinblitz.PlayerViewModel
import info.cs4518.bitcoinblitz.R
import info.cs4518.bitcoinblitz.databinding.ActivityMainBinding
import info.cs4518.bitcoinblitz.ui.shop.StoreScreen
import info.cs4518.bitcoinblitz.ui.stats.StatScreen
import java.util.*
import kotlin.math.sqrt

val TAG = "MAINACTIVITY"

class MainActivity : AppCompatActivity() {

	private val overclockMeter = 0
	private val wallet = 0

	private lateinit var binding: ActivityMainBinding
	private lateinit var navigationBar: BottomNavigationView
	private lateinit var fragmentView: FragmentContainerView
	private lateinit var viewModel: PlayerViewModel

	// Sensor stuff
	private var sensorManager: SensorManager? = null
	private var acceleration = 0f
	private var currentAcceleration = 0f
	private var lastAcceleration = 0f

	private var overclockReady = true

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

		// Get Viewmodel
		viewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

		// Sync LiveData with viewmodel
		viewModel.wallet.value = viewModel.bitcoinCount
		val bitcoinObserver = Observer<Long> { newCount -> viewModel.bitcoinCount = newCount }
		viewModel.wallet.observe(this, bitcoinObserver)

		// Get navigation view
		navigationBar = binding.bottomNavigation
		fragmentView = binding.currentView

		// Setup bottom navigation bar links
		navigationBar.selectedItemId = R.id.home_button
		navigationBar.setOnItemSelectedListener { item ->
			when (item.itemId) {
				R.id.home_button -> switchScreen(HomeScreen.newInstance())
				R.id.stats_button -> switchScreen(StatScreen.newInstance(1))
				R.id.store_button -> switchScreen(StoreScreen.newInstance())
				else -> false
			}

			true
		}

		// Getting the Sensor Manager instance
		sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
		Objects.requireNonNull(sensorManager)!!
			.registerListener(sensorListener, sensorManager!!
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
		acceleration = 10f
		currentAcceleration = SensorManager.GRAVITY_EARTH
		lastAcceleration = SensorManager.GRAVITY_EARTH

		viewModel.upgrades.recalculateAll()

		// Set bitcoin to increment in the background every second
		val backgroundBitcoin = Timer()
		backgroundBitcoin.scheduleAtFixedRate(
			object : TimerTask() {
				override fun run() {
					viewModel.wallet.postValue(
						viewModel.wallet.value!! + viewModel.bitcoinPerSecond
					)
				}
			}, 0, 1000
		)
	}

	fun triggerOverclock() {
		overclockReady = false
		Toast.makeText(applicationContext, "Overclock activated", Toast.LENGTH_SHORT).show()

		val clockStats = viewModel.upgrades.getOverclockStats()

		val oldPotency = viewModel.clickPotency
		viewModel.clickPotency = (viewModel.clickPotency * clockStats.boostMultiplier).toInt()
		viewModel.clickPotency += clockStats.boostAdditive

		val oldBPS = viewModel.bitcoinPerSecond
		viewModel.bitcoinPerSecond = (viewModel.bitcoinPerSecond * clockStats.boostMultiplier).toLong()
		viewModel.bitcoinPerSecond += clockStats.boostAdditive

		runAtDelay({
			viewModel.clickPotency = oldPotency
			viewModel.bitcoinPerSecond = oldBPS
			Toast.makeText(applicationContext, "Overclock Finished", Toast.LENGTH_SHORT).show()

			val rechargeTime = (10000 * clockStats.cooldownMultiplier).toLong()
			Toast.makeText(applicationContext,
				"Overclock will recharge in ${rechargeTime/1000}s", Toast.LENGTH_SHORT).show()

			runAtDelay({
				Toast.makeText(applicationContext, "Overclock charged", Toast.LENGTH_SHORT).show()
				overclockReady = true
			}, rechargeTime)
		}, 10000)
	}

	private fun runAtDelay(task: () -> Unit, delay: Long): Timer {
		val timer = Timer()
		timer.schedule(
			object : TimerTask() {
				override fun run() {
					task()
				}
			}, delay
		)

		return timer
	}

	private val sensorListener: SensorEventListener = object : SensorEventListener {
		override fun onSensorChanged(event: SensorEvent) {

			// Fetching x,y,z values
			val x = event.values[0]
			val y = event.values[1]
			val z = event.values[2]
			lastAcceleration = currentAcceleration

			// Getting current accelerations
			// with the help of fetched x,y,z values
			currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
			val delta: Float = currentAcceleration - lastAcceleration
			acceleration = acceleration * 0.9f + delta

			// Display a Toast message if
			// acceleration value is over 12
			if (acceleration > 12 && overclockReady) {
				triggerOverclock()
			}
		}
		override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
	}

	override fun onPause() {
		super.onPause()

		viewModel.database.backupData()
	}

	private fun switchScreen(fragment: Fragment) {
		supportFragmentManager.beginTransaction()
			.replace(R.id.currentView, fragment)
			.commit()
	}
}