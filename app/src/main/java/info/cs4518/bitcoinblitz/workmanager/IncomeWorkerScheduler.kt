package info.cs4518.bitcoinblitz.workmanager

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object IncomeWorkerScheduler{
    fun refreshPeriodicWork(context: Context){
        val myConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val refreshWork = PeriodicWorkRequest.Builder(IncomeWorker::class.java,
        15,TimeUnit.MINUTES)
            .setConstraints(myConstraints)
            .addTag("IncomeWorkManager")
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork("myWorkManager",
            ExistingPeriodicWorkPolicy.REPLACE, refreshWork)
    }
}