package com.example.mynetworkapplication

import android.app.Activity
import android.util.Log
import android.widget.TextView
import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit


class RestApi {
    companion object {
        private const val URL = "https://api.giphy.com/v1/"
        private const val API_KEY = "7gapkk4T9HUCWF2R1XANh8VnnAPifZ2k"
        private const val TIMEOUT_IN_SECONDS = 2
    }

    lateinit var client: OkHttpClient

    init {
        client =
            OkHttpClient.Builder().connectTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_IN_SECONDS.toLong(), TimeUnit.SECONDS)
                .build()
    }

    fun AsyncGetRequest(act: Activity) {
        val urlBuilder =
            HttpUrl.parse(URL+"gifs/search").newBuilder()
        urlBuilder.addQueryParameter("api_key", API_KEY)

        val url = urlBuilder.build().toString()
        val request: Request = Request.Builder()
            .url(url)
            .build()
        Log.d("AsyncGetRequest url=", url)

        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            //            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response?) {
                Log.d("AsyncGetRequest", "onResponse")
                call?.toString()
                act.runOnUiThread {
                    val responseText = act.findViewById<TextView>(R.id.responseTxt)
                    responseText.text = call?.toString()
                    responseText.textSize = 26F
                    Log.d("AsyncGetRequest", call?.toString())
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("AsyncGetRequest onFailure", call?.toString())
                Log.d("AsyncGetRequest e", e.toString())
            }
        })
    }

}