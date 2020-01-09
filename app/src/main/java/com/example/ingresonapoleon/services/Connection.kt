package com.example.ingresonapoleon.services

import android.util.Log
import com.example.ingresonapoleon.App
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object Connection {

    fun send(endpoint: Endpoint, params : Int = 0, completion: (ConnectionResponse) -> Unit) {
        val url = if (params != 0) {
            "${endpoint.base}${endpoint.path}${params}"
        } else {
            "${endpoint.base}${endpoint.path}"
        }

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        val call = App.client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                Log.i(Connection::class.java.name, "$url OnFailure ${e?.printStackTrace().toString()}")
                e?.printStackTrace().toString()
                completion(ConnectionResponse.OnFailure)
            }

            override fun onResponse(call: Call?, response: Response?) {
                completion(ConnectionResponse.OnSuccess(response?.body()!!.string()))
            }
        })
    }
}