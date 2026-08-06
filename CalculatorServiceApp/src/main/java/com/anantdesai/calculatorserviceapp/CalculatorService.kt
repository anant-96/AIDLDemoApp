package com.anantdesai.calculatorserviceapp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.calculator.ICalculator

class CalculatorService : Service() {

    private val binder = object : ICalculator.Stub() {
        override fun add(a: Int, b: Int): Int {
            return a + b
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d("AIDL_Service", "onBind")
        return binder
    }

    override fun onDestroy() {
        Log.d("AIDL_Service", "onDestroy")
        super.onDestroy()
    }
}

//Notice ICalculator.Stub()
//This is generated from the AIDL.
//We're simply implementing its methods
