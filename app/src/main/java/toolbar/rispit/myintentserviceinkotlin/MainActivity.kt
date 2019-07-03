package toolbar.rispit.myintentserviceinkotlin

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var boundService: BoundService
    private var isBoundToServie = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        printTimestamp.setOnClickListener {
            if(isBoundToServie)
            {

                displayText.text = boundService.getRispitString()
            }
        }
        stopService.setOnClickListener {
            if(isBoundToServie)
            {
                isBoundToServie = false
                unbindService(serviceConnection)
            }

            Intent(this@MainActivity, BoundService::class.java).apply { stopService(this) }
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this,BoundService::class.java)
        //startService(intent)
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE)


        }

    override fun onStop() {
        super.onStop()
        if(isBoundToServie)
            isBoundToServie= false
    }

    private val serviceConnection = object: ServiceConnection {
        override fun onServiceDisconnected(p0: ComponentName?) {
            isBoundToServie = false
        }

        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Toast.makeText(this@MainActivity,"Connected",Toast.LENGTH_LONG).show()
            val myIBinder = (p1 as BoundService.MyBinder)
            boundService = myIBinder.getService()
            isBoundToServie = true
        }

    }
}

