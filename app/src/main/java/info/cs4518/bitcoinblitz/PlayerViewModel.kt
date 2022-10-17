package info.cs4518.bitcoinblitz;
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import info.cs4518.bitcoinblitz.ui.Database
import info.cs4518.bitcoinblitz.upgrades.UpgradeTracker

class PlayerViewModel(application: Application): AndroidViewModel(application) {
	var clickCounter: Int = 0

	var bitcoinPerSecond: Long = 0

	var clickPotency: Int = 1

	var overclockMeter: Float = 0f

	var bitcoinCount: Long = 0

	val wallet: MutableLiveData<Long> by lazy {
		MutableLiveData<Long>()
	}

	val upgrades = UpgradeTracker(this, application)

	val database = Database(this)
}


