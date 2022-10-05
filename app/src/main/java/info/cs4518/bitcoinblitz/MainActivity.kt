package info.cs4518.bitcoinblitz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

	private val overclockMeter = 0
	private val wallet = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)



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