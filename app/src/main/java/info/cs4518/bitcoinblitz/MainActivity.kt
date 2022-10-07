package info.cs4518.bitcoinblitz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
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
		setContentView(R.layout.activity_main)

		binding = ActivityMainBinding.inflate(layoutInflater)
		navigationBar = findViewById(R.id.bottom_navigation)
		fragmentView = binding.currentView

		navigationBar.selectedItemId = R.id.home_button

		navigationBar.setOnItemSelectedListener { item ->
			when (item.itemId) {
				R.id.home_button -> {
					supportFragmentManager.beginTransaction()
						.replace(R.id.currentView, HomeScreen.newInstance())
						.commit()
					true
				}
				R.id.stats_button -> {
					supportFragmentManager.beginTransaction()
						.replace(R.id.currentView, StatScreen.newInstance(1))
						.commit()
					true
				}
				R.id.store_button -> {
					supportFragmentManager.beginTransaction()
						.replace(R.id.currentView, StoreScreen.newInstance(1))
						.commit()
					true
				}
				else -> false
			}


		}

//		clickCounterTextview = findViewById(R.id.player_click_counter)
		// TODO: Move over to Home Fragment
	}

/*
	override fun onDestroy() {
		super.onDestroy()
	}
*/

	fun calculateBPS(view: View?){

	}
}