package info.cs4518.bitcoinblitz.ui.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import info.cs4518.bitcoinblitz.R
import info.cs4518.bitcoinblitz.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class StatScreen : Fragment() {

	private var columnCount = 1

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		arguments?.let {
			columnCount = it.getInt(ARG_COLUMN_COUNT)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_stat_screen_list, container, false)

		// Set the adapter
		if (view is RecyclerView) {
			with(view) {
				layoutManager = when {
					columnCount <= 1 -> LinearLayoutManager(context)
					else -> GridLayoutManager(context, columnCount)
				}
				adapter = StatRecyclerViewAdapter(PlaceholderContent.ITEMS)
			}
		}
		return view
	}

	companion object {

		// TODO: Customize parameter argument names
		const val ARG_COLUMN_COUNT = "column-count"

		// TODO: Customize parameter initialization
		@JvmStatic
		fun newInstance(columnCount: Int) =
			StatScreen().apply {
				arguments = Bundle().apply {
					putInt(ARG_COLUMN_COUNT, columnCount)
				}
			}
	}
}