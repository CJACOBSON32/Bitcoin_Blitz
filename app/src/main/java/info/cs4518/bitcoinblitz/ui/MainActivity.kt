package info.cs4518.bitcoinblitz.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import info.cs4518.bitcoinblitz.workmanager.IncomeWorkerScheduler
import java.util.*

val TAG = "MAINACTIVITY"

class MainActivity : AppCompatActivity() {

	private val overclockMeter = 0
	private val wallet = 0

	private lateinit var binding: ActivityMainBinding
	private lateinit var navigationBar: BottomNavigationView
	private lateinit var fragmentView: FragmentContainerView
	private lateinit var viewModel: PlayerViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		val view = binding.root
		setContentView(view)

		startWorkManager()

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

		// Initialize upgrades
		viewModel.upgrades.loadUpgrades(resources.openRawResource(R.raw.upgrades))
	}


	private fun switchScreen(fragment: Fragment) {
		supportFragmentManager.beginTransaction()
			.replace(R.id.currentView, fragment)
			.commit()
	}

	private fun startWorkManager(){
		IncomeWorkerScheduler.refreshPeriodicWork(this)
	}
}