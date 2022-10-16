package info.cs4518.bitcoinblitz.ui.shop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.bitcoinstoremodule.ShopItem
import com.example.bitcoinstoremodule.ShopItemAdapter
import info.cs4518.bitcoinblitz.PlayerViewModel
import info.cs4518.bitcoinblitz.R
import info.cs4518.bitcoinblitz.databinding.FragmentHomeScreenBinding
import info.cs4518.bitcoinblitz.databinding.FragmentStoreScreenListBinding
import info.cs4518.bitcoinblitz.placeholder.PlaceholderContent
import info.cs4518.bitcoinblitz.ui.MainActivity
import info.cs4518.bitcoinblitz.upgrades.Upgrade

/**
 * A fragment representing a list of Items.
 */
class StoreScreen : Fragment() {

	private val TAG = "STORE_SCREEN"
	private lateinit var currencyText: TextView
	private lateinit var binding: FragmentStoreScreenListBinding
	lateinit var viewModel: PlayerViewModel

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Init viewmodel and viewbinding
		viewModel = ViewModelProvider(activity as MainActivity)[PlayerViewModel::class.java]
		binding = FragmentStoreScreenListBinding.inflate(inflater, container, false)

		// Set the adapter
		binding.list.layoutManager = LinearLayoutManager(activity as MainActivity)
		binding.list.adapter = ShopItemAdapter(activity as MainActivity, this, getShopItemList())

		currencyText = binding.bitcoinCount
		currencyText.text = getString(R.string.Wallet_View, viewModel.wallet.value)

		Log.d(TAG, "currencyText.text = ${currencyText.text}")

		// Bind observer to livedata
		val bitcoinObserver = Observer<Long> { newCount ->
			currencyText.text = getString(R.string.Wallet_View, newCount)
		}
		viewModel.wallet.observe(activity as MainActivity, bitcoinObserver)

		return binding.root
	}

	override fun onDestroyView() {
		super.onDestroyView()

		viewModel.wallet.removeObservers(activity as MainActivity)
	}

	private fun getShopItemList(): List<Upgrade> {

		val upgrades = viewModel.upgrades.allUpgrades.sortedBy { it.cost }

		return upgrades
	}

	companion object {
		@JvmStatic
		fun newInstance() = StoreScreen().apply {}
	}
}