package com.example.bitcoinstoremodule

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import info.cs4518.bitcoinblitz.PlayerViewModel
import info.cs4518.bitcoinblitz.R
import info.cs4518.bitcoinblitz.upgrades.Upgrade

private const val TAG = "ShopItemAdapter"

// got rid of RecycleView comments because we're building on top of it

class ShopItemAdapter(val activity: AppCompatActivity, private val shopItems: List<ShopItem<Upgrade>>) :
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
		private val launchButton: Button
		private val cardView: CardView
		private lateinit var viewModel: PlayerViewModel

		fun bind(cShopItem: ShopItem<Upgrade>) {
			titleText.text = cShopItem.title
			descText.text = cShopItem.description
			priceText.text = activity.getString(R.string.Wallet_View, cShopItem.price)
			viewModel = ViewModelProvider(activity)[PlayerViewModel::class.java]

			// Make the gpu text red if the user cannot afford the GPU and green if they can
			val bitcoinObserver = Observer<Long> { newCount ->
				if (newCount >= cShopItem.price)
					priceText.setTextColor(activity.getColor(R.color.green_700))
				else
					priceText.setTextColor(activity.getColor(R.color.red_700))
			}
			viewModel.wallet.observe(activity, bitcoinObserver)

			// Buy a GPU when buy button is pressed
			launchButton.setOnClickListener {
				Log.d(TAG, "bought for ${cShopItem.price}")
				// Check if the user has enough bitcoin and subtract if they do
				if (viewModel.wallet.value!! >= cShopItem.price) {
					viewModel.wallet.value = viewModel.wallet.value!! - cShopItem.price
					viewModel.upgrades.buy(cShopItem.upgrade)
				}
				else
					Toast.makeText(activity,"You cannot afford this item", Toast.LENGTH_SHORT).show()
			}

			cardView.setOnLongClickListener {
				Toast.makeText(activity,"Click 'buy' to purchase this item", Toast.LENGTH_SHORT).show()
				true
			}

		}

		init {
			titleText = itemView.findViewById(R.id.item_title)
			descText = itemView.findViewById(R.id.item_description)
			priceText = itemView.findViewById(R.id.item_price)
			launchButton = itemView.findViewById(R.id.item_launch_button)
			cardView = itemView.findViewById(R.id.cardview)
		}
	}
}