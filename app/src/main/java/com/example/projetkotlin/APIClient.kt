package com.example.projetkotlin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class APIClient : ViewModel() {

    private val baseUrl = "https://dev-sae301grp5.users.info.unicaen.fr/api"

    private val response: MutableLiveData<String> = MutableLiveData<String>()

    fun getResult() : LiveData<String> = response

    fun getMembers(): LiveData<String> {
        Thread {
            val url = URL("$baseUrl/members")
            Log.d("tag", "Call")
            try {
                val conn = url.openConnection() as HttpsURLConnection
                conn.connect()
                if (conn.responseCode == 200) {
                    val buffer: BufferedReader = conn.inputStream.bufferedReader()
                    response.postValue(buffer.readText())
                } else {
                    Log.d("tag", conn.responseMessage)
                    response.postValue("Erreur de connexion au serveur")
                }
            } catch (e: Exception) {
                Log.d("tag", e.message.toString())
                response.postValue("Error")
            }
        }.start()
        return response
    }
}