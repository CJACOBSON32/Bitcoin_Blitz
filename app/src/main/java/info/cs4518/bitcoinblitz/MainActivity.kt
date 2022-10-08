package info.cs4518.bitcoinblitz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import info.cs4518.bitcoinblitz.databinding.ActivityMainBinding

val TAG = "MAINACTIVITY"

class MainActivity : AppCompatActivity() {

	private val overclockMeter = 0
	private val wallet = 0

	private lateinit var binding: ActivityMainBinding
	private lateinit var navigationBar: BottomNavigationView
	private lateinit var fragmentView: FragmentContainerView

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)

		val view = binding.root
		setContentView(view)

//		navigationBar = findViewById(R.id.bottom_navigation)
		navigationBar = binding.bottomNavigation
		fragmentView = binding.currentView

		navigationBar.selectedItemId = R.id.home_button

		navigationBar.setOnItemSelectedListener { item ->

			when (item.itemId) {
				R.id.home_button -> switchScreen(HomeScreen.newInstance())
				R.id.stats_button -> switchScreen(StatScreen.newInstance(1))
				R.id.store_button -> switchScreen(StoreScreen.newInstance(1))
				else -> false
			}

			true
		}
	}

	private fun switchScreen(fragment: Fragment) {
		supportFragmentManager.beginTransaction()
			.replace(R.id.currentView, fragment)
			.commit()
	}

/*
	override fun onDestroy() {
		super.onDestroy()
	}
*/

	fun calculateBPS(view: View?){

	}
}