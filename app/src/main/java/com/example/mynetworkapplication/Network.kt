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
            HttpUrl.parse(URL + "gifs/search").newBuilder()
        urlBuilder.addQueryParameter("api_key", API_KEY)
        urlBuilder.addQueryParameter("q", "Horse")

        val url = urlBuilder.build().toString()
        val request: Request = Request.Builder()
            .url("https://api.giphy.com/v1/gifs/search?api_key=7gapkk4T9HUCWF2R1XANh8VnnAPifZ2k&q=Horse")
            .build()
        Log.d("AsyncGetRequest url=", url)

        val call: Call = client.newCall(request)
        call.enqueue(object : Callback {
            //            @Throws(IOException::class)
            override fun onResponse(call: Call?, response: Response?) {
                Log.d("AsyncGetRequest", "body ${response?.body()?.string()}  ")
                Log.d("AsyncGetRequest", "message ${response?.message().toString()}  ")
                Log.d("AsyncGetRequest", "isSuccessful ${response?.isSuccessful.toString()}  ")
                Log.d("AsyncGetRequest", "headers ${response?.headers().toString()}  ")
                Log.d("AsyncGetRequest", "cacheResponse ${response?.cacheResponse().toString()}  ")

                Log.d("AsyncGetRequest", "call ${call.toString()}  ")

                val request: Request = Request.Builder()
                    .url("https://api.giphy.com/v1/gifs/search?api_key=7gapkk4T9HUCWF2R1XANh8VnnAPifZ2k&q=Horse")
                    .build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onResponse(call: Call?, response: Response?) {
                        Log.d("AsyncGetRequest 2", response.toString())
                        act.runOnUiThread {
                            val responseText = act.findViewById<TextView>(R.id.responseTxt)
                            val resp = response?.body()?.string()
                            Log.d("AsyncGetRequest  RESP", "body ${resp}  ")
                            responseText.text = resp
                        }
                    }

                    override fun onFailure(call: Call?, e: IOException?) {
                        Log.d("AsyncGetRequest onFailure", e.toString())
                    }
                })

                act.runOnUiThread {
                    val responseText = act.findViewById<TextView>(R.id.responseTxt)
                    responseText.text = response?.toString()
                    responseText.textSize = 26F
                    Log.d("AsyncGetRequest", response?.toString())
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                Log.d("AsyncGetRequest onFailure", call?.toString())
                Log.d("AsyncGetRequest e", e.toString())
            }
        })
    }

}