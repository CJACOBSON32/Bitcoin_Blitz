package info.cs4518.bitcoinblitz

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import info.cs4518.bitcoinblitz.placeholder.PlaceholderContent.PlaceholderItem
import info.cs4518.bitcoinblitz.databinding.FragmentStatScreenBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class StatRecyclerViewAdapter(
	private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<StatRecyclerViewAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

		return ViewHolder(
			FragmentStatScreenBinding.inflate(
				LayoutInflater.from(parent.context),
				parent,
				false
			)
		)

	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val item = values[position]
		holder.idView.text = item.id
		holder.contentView.text = item.content
	}

	override fun getItemCount(): Int = values.size

	inner class ViewHolder(binding: FragmentStatScreenBinding) :
		RecyclerView.ViewHolder(binding.root) {
		val idView: TextView = binding.itemNumber
		val contentView: TextView = binding.content

		override fun toString(): String {
			return super.toString() + " '" + contentView.text + "'"
		}
	}

}