package info.cs4518.bitcoinblitz;
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.cs4518.bitcoinblitz.upgrades.UpgradeTracker

class PlayerViewModel : ViewModel() {
	var clickCounter: Int = 0

	var bitcoinPerSecond: Int = 0

	var clickPotency: Int = 1

	var overclockMeter: Float = 0f

	val wallet: MutableLiveData<Long> by lazy {
		MutableLiveData<Long>()
	}

	val upgrades = UpgradeTracker()
}


