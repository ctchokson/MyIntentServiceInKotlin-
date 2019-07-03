package toolbar.rispit.myintentserviceinkotlin

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.SystemClock
import android.util.Log
import android.widget.Chronometer

class BoundService: Service() {

    private val myBinder = MyBinder()
    private lateinit var myChrono: Chronometer
    private val LOG_TAG = "BoundService"


    override fun onCreate() {
        super.onCreate()
        Log.v(LOG_TAG,"in onCreate")
        myChrono = Chronometer(this).apply {
            base = SystemClock.elapsedRealtime()
            start()

        }

    }

    override fun onUnbind(intent: Intent?)= true
    override fun onBind(p0: Intent?) = myBinder
    override fun onRebind(intent: Intent?) {

        Log.v(LOG_TAG,"in onRebind")
        super.onRebind(intent)
    }


    override fun onDestroy() {
        super.onDestroy()
        myChrono.stop()
    }

    fun getRispitString() = "Rispit BoundService ${SystemClock.elapsedRealtime()- myChrono.base}"

     inner class MyBinder:Binder()
    {
        fun getService(): BoundService
        {
            return this@BoundService
        }
    }
}