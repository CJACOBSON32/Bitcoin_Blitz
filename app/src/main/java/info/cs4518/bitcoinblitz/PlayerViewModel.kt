package info.cs4518.bitcoinblitz;
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerViewModel : ViewModel() {
	var clickCounter: Int = 0

	var bitcoinPerSecond: Int = 0

	var clickPotency: Int = 20

	var overclockMeter: Int = 0

	val wallet: MutableLiveData<Int> by lazy {
		MutableLiveData<Int>()
	}



	//live mutable data
}


