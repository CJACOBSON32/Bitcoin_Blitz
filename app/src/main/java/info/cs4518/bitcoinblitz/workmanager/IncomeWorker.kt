package info.cs4518.bitcoinblitz.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.lang.Exception

class IncomeWorker(context: Context, val workerParams: WorkerParameters) : Worker(
    context,
    workerParams) {

    override fun doWork(): Result {
        return try {
            try {
                Log.d("MyWorker", "Run work manager")
                doTask()
                Result.success()
            } catch (e: Exception) {
                Log.d("MyWorker", "exception in doWork ${e.message}")
                Result.failure()
            }
        } catch (e: Exception) {
            Log.d("MyWorker", "exception in doWork ${e.message}")
            Result.failure()
        }
    }

    private fun doTask(){
        //interact with database here
    }
}

