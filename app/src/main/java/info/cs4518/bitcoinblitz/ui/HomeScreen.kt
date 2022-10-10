package info.cs4518.bitcoinblitz.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import info.cs4518.bitcoinblitz.PlayerViewModel
import info.cs4518.bitcoinblitz.R
import info.cs4518.bitcoinblitz.databinding.FragmentHomeScreenBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [HomeScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeScreen : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null

	private val TAG = "HOME_FRAGMENT";

	private lateinit var binding: FragmentHomeScreenBinding

	lateinit var viewModel: PlayerViewModel

	private fun tapBitcoin() {
		viewModel.wallet += viewModel.clickPotency
		updateBitcoinValues()
	}

	private fun updateBitcoinValues() {
		binding.bitcoinPerSecondView.text = resources.getString(R.string.BPS_View, viewModel.bitcoinPerSecond)
		binding.walletView.text = resources.getString(R.string.Wallet_View, viewModel.wallet)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Init viewmodel and viewbinding
		viewModel = ViewModelProvider(this)[PlayerViewModel::class.java]
		binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

		// Set initial values for bitcoin wallet
		updateBitcoinValues()

		// Clickevent for bitcoin button
		binding.bitcoinButton.setOnClickListener { tapBitcoin()	}

		// Inflate the layout for this fragment
		return binding.root
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment HomeScreen.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance() = HomeScreen().apply {}
	}
}