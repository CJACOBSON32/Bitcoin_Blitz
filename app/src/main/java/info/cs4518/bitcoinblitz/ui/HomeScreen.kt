package info.cs4518.bitcoinblitz.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import info.cs4518.bitcoinblitz.BigNumbers
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
	private val TAG = "HOME_FRAGMENT";

	private lateinit var binding: FragmentHomeScreenBinding

	lateinit var viewModel: PlayerViewModel

	private fun updateBitcoinValues(newAmount: Long?) {
		binding.bitcoinPerSecondView.text = resources.getString(
			R.string.BPS_View, BigNumbers.numToStr(viewModel.bitcoinPerSecond)
		)
		binding.walletView.text = resources.getString(
			R.string.Wallet_View, BigNumbers.numToStr(viewModel.wallet.value!!)
		)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Init viewmodel and viewbinding
		viewModel = ViewModelProvider(activity as MainActivity)[PlayerViewModel::class.java]
		binding = FragmentHomeScreenBinding.inflate(inflater, container, false)

		// Set initial values for bitcoin wallet
		updateBitcoinValues(viewModel.wallet.value)

		val bitcoinObserver = Observer<Long> { newCount -> updateBitcoinValues(newCount) }
		viewModel.wallet.observe(viewLifecycleOwner, bitcoinObserver)

		// Clickevent for bitcoin button
		binding.bitcoinButton.setOnClickListener {
			viewModel.wallet.value = viewModel.wallet.value?.plus(viewModel.clickPotency)
		}

		// Inflate the layout for this fragment
		return binding.root
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @return A new instance of fragment HomeScreen.
		 */
		@JvmStatic
		fun newInstance() = HomeScreen().apply {}
	}
}