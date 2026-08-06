package com.anantdesai.calculatorclientapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.calculator.ICalculator

class MainActivity : ComponentActivity() {

    private var calculator: ICalculator? = null

    private var result by mutableStateOf("Result will appear here")

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            calculator = ICalculator.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            calculator = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DisposableEffect(Unit) {
                val intent = Intent()
                intent.component = ComponentName(
                    "com.anantdesai.calculatorserviceapp",
                    "com.anantdesai.calculatorserviceapp.CalculatorService"
                )

//                val intent = Intent("com.example.calculator.CALCULATOR_SERVICE")
//                intent.setPackage("com.anantdesai.calculatorserviceapp")

                val bound = bindService(intent, connection, BIND_AUTO_CREATE)
                Log.d("AIDL_Service", "bound = $bound")

                onDispose {
                    unbindService(connection)
                }
            }

            CalculatorScreen(
                result = result,
                onAddClicked = {
                    val sum = calculator?.add(10, 20)
                    result = "Result = $sum"
                }
            )
        }
    }
}
