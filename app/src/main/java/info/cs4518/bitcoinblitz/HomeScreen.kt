package info.cs4518.bitcoinblitz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeScreen : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var mainView: ConstraintLayout
    //private lateinit var clickCounterTextview: TextView
    private lateinit var bitcoinPerSecondTextview: TextView
    private lateinit var walletTextView: TextView

    lateinit var viewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

		bitcoinPerSecondTextview = requireView().findViewById(R.id.bitcoin_per_second_view)
		walletTextView = requireView().findViewById(R.id.wallet_view)

        viewModel = ViewModelProvider(this)[PlayerViewModel::class.java]

        //clickCounterTextview.text = viewModel.clickCounter.toString()
		bitcoinPerSecondTextview.text = viewModel.bitcoinPerSecond.toString()
		walletTextView.text = viewModel.wallet.toString()
    }


    fun tapBitcoin(view: View?) {
        viewModel.wallet += viewModel.clickPotency
        walletTextView.text = viewModel.wallet.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_screen, container, false)
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
        fun newInstance(param1: String, param2: String) =
            HomeScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}