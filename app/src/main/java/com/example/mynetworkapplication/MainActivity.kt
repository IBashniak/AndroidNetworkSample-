package com.example.mynetworkapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class MainActivity : AppCompatActivity() {
    private val restApi: RestApi = RestApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val responseText = findViewById<TextView>(R.id.responseTxt)
        findViewById<Button>(R.id.requestButton).setOnClickListener {

            restApi.AsyncGetRequest(this)

            runOnUiThread {
                responseText.text = " Response"
                responseText.textSize = 26F
            }
        }
    }
}
