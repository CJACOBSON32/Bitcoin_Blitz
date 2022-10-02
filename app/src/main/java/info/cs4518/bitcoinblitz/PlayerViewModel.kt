package info.cs4518.bitcoinblitz;
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerViewModel : ViewModel() {
    var clickCounter: Int = 0

    var bitcoinPerSecond: Int = 0

    var clickPotency: Int = 1

    var overclockMeter: Int = 0

    var wallet: Int = 0

    //live mutable data
}


