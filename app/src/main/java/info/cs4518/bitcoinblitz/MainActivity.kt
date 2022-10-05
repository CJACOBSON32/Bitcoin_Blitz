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

	private lateinit var mainView: ConstraintLayout
	//private lateinit var clickCounterTextview: TextView
	private lateinit var bitcoinPerSecondTextview: TextView
	private lateinit var walletTextView: TextView


	lateinit var viewModel: PlayerViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

//		clickCounterTextview = findViewById(R.id.player_click_counter)
		mainView = findViewById(R.id.main_view)
//		bitcoinPerSecondTextview = findViewById(R.id.bitcoin_per_second_view)
//		walletTextView = findViewById(R.id.wallet_view)

		viewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

		//clickCounterTextview.text = viewModel.clickCounter.toString()
//		bitcoinPerSecondTextview.text = viewModel.bitcoinPerSecond.toString()
//		walletTextView.text = viewModel.wallet.toString()
	}

/*
	override fun onDestroy() {
		super.onDestroy()
	}
*/

	fun tapBitcoin(view: View?){
		viewModel.wallet += viewModel.clickPotency
		walletTextView.text = viewModel.wallet.toString()

	}

	fun calculateBPS(view: View?){

	}
}