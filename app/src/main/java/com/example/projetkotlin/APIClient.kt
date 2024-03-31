package com.example.projetkotlin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.BufferedReader
import java.lang.Exception
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.net.ssl.HttpsURLConnection


class APIClient : ViewModel() {


    private val baseUrl = "https://dev-sae301grp5.users.info.unicaen.fr/api"

    private val response: MutableLiveData<String?> = MutableLiveData<String?>()

    fun getResult(): LiveData<String?> = response

    fun resetResponse() {
        response.postValue(null)
    }

    fun getMembers(): LiveData<String?> {
        Thread {
            val url = URL("$baseUrl/members")
            Log.d("tag", "CallGetMembers")
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


    fun postMember(
        nom: String,
        prenom: String,
        numLicence: String,
        motDePasse: String,
        dateCertificat: String,
        pricing: String
    ): LiveData<String?> {
        val oldFormat = SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE)
        val date = oldFormat.parse(dateCertificat)
        val newFormat = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
        val dateCertificatFormatted = newFormat.format(date!!)
        val now = newFormat.format(Date())
        Thread {
            val url =
                URL("$baseUrl/members?name=$nom&surname=$prenom&licence=$numLicence" +
                        "&password=$motDePasse&date_certification=$dateCertificatFormatted" +
                        "&pricing=$pricing&subdate=$now")
            try {
                val conn = url.openConnection() as HttpsURLConnection
                conn.requestMethod = "POST"
                conn.connect()
                if (conn.responseCode == 201) {
                    response.postValue("Adhérent créé !")
                } else {
                    response.postValue("Erreur de connexion au serveur : ${conn.responseMessage}")
                }
            } catch (e: Exception) {
                Log.d("tag", e.message.toString())
                response.postValue("Error")
            }
        }.start()
        return response
    }
}