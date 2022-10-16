package com.example.bitcoinstoremodule

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import info.cs4518.bitcoinblitz.PlayerViewModel
import info.cs4518.bitcoinblitz.R
import info.cs4518.bitcoinblitz.upgrades.ActiveUpgrade
import info.cs4518.bitcoinblitz.upgrades.OverclockUpgrade
import info.cs4518.bitcoinblitz.upgrades.PassiveUpgrade
import info.cs4518.bitcoinblitz.upgrades.Upgrade

private const val TAG = "ShopItemAdapter"

// got rid of RecycleView comments because we're building on top of it

class ShopItemAdapter(val activity: AppCompatActivity, val fragment: Fragment, private val shopItems: List<Upgrade>) :
	RecyclerView.Adapter<ShopItemAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.fragment_store_item, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val currentShopItem = shopItems[position]
		holder.bind(currentShopItem)
	}

	override fun getItemCount(): Int {
		return shopItems.size
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		private val titleText: TextView
		private val descText: TextView
		private val priceText: TextView
		private val statText: TextView
		private val ownedCounter: TextView
		private val cardView: CardView
		private lateinit var viewModel: PlayerViewModel

		fun bind(cUpgrade: Upgrade) {
			titleText.text = cUpgrade.name
			descText.text = cUpgrade.description
			priceText.text = activity.getString(R.string.Wallet_View, cUpgrade.cost)
			ownedCounter.text = activity.getString(R.string.Owned_Count, cUpgrade.numOwned)
			viewModel = ViewModelProvider(activity)[PlayerViewModel::class.java]

			// Set upgrade stats text
			when (cUpgrade) {
				is ActiveUpgrade -> statText.text = activity.getString(R.string.Active_Stats, cUpgrade.clickPotencyAdditive)
				is PassiveUpgrade -> statText.text = activity.getString(R.string.Passive_Stats, cUpgrade.passiveIncome)
				is OverclockUpgrade -> {
					if (cUpgrade.boostAdditive > 0)
						statText.text = activity.getString(
							R.string.Overclock_Stats_Boost_Additive,
							cUpgrade.boostAdditive
						)
					else if (cUpgrade.boostMultiplier > 1)
						statText.text = activity.getString(
							R.string.Overclock_Stats_Boost_Multiplier,
							(100*(cUpgrade.boostMultiplier - 1)).toInt()
						)
					else if (cUpgrade.cooldownMultiplier > 1)
						statText.text = activity.getString(
							R.string.Overclock_Stats_Boost_Cooldown,
							(100*(1-1/cUpgrade.cooldownMultiplier)).toInt()
						)
				}
			}

			// Make the gpu text red if the user cannot afford the GPU and green if they can
			val bitcoinObserver = Observer<Long> { newCount ->
				if (newCount >= cUpgrade.cost)
					priceText.setTextColor(activity.getColor(R.color.green_700))
				else
					priceText.setTextColor(activity.getColor(R.color.red_700))
			}
			viewModel.wallet.observe(fragment, bitcoinObserver)

			// Buy a GPU when buy button is pressed
			cardView.setOnClickListener {
				Log.d(TAG, "bought for ${cUpgrade.cost}")
				// Check if the user has enough bitcoin and subtract if they do
				if (viewModel.wallet.value!! >= cUpgrade.cost) {
					viewModel.wallet.value = viewModel.wallet.value!! - cUpgrade.cost
					viewModel.upgrades.buy(cUpgrade)
					ownedCounter.text = activity.getString(R.string.Owned_Count, cUpgrade.numOwned)
				}
				else
					Toast.makeText(activity,"You cannot afford this item", Toast.LENGTH_SHORT).show()
			}

		}

		init {
			titleText = itemView.findViewById(R.id.item_title)
			descText = itemView.findViewById(R.id.item_description)
			priceText = itemView.findViewById(R.id.item_price)
			statText = itemView.findViewById(R.id.item_stats)
			ownedCounter = itemView.findViewById(R.id.owned_counter)
			cardView = itemView.findViewById(R.id.cardview)
		}
	}
}