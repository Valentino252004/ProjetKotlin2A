package com.example.projetkotlin

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.projetkotlin.bdd.Site
import com.example.projetkotlin.bdd.SiteView
import com.example.projetkotlin.ui.theme.ProjetKotlinTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {

    private val siteView: SiteView by viewModels()
    private val apiRequest: APIClient by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Thread {
            siteView.dao.deleteSites()
        }
        setContent {
            ProjetKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        SetupApplication(apiRequest, siteView)
                    }
                }
            }
        }
    }
}

@Composable
fun SetupApplication(apiRequest: APIClient, siteView: SiteView) {
    val data = apiRequest.getResult().observeAsState()
    apiRequest.getSites()
    Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        if (data.value == null) {
            Text(text = "Mise en place de l'application")
            CircularProgressIndicator()
        } else {
            val jsonSites = JSONObject(data.value!!)
            val sites = jsonSites.getJSONArray("data")
            for (i in 0..<sites.length()) {
                val site = sites[i] as JSONObject
                val id = site.getString("id")
                val name = (site.get("attributes") as JSONObject).getString("name")
                Thread {
                    try {
                        siteView.dao.insert(Site(id = id.toInt(), name = name))
                    } catch (_: android.database.sqlite.SQLiteConstraintException) {}
                }.start()
            }
            Accueil()
        }
    }
}

@Composable
fun Accueil() {
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            context.startActivity(Intent(context, ComposeActivity::class.java))
        }) {
            Text(text = "Vue Compose")
        }
        Button(onClick = {
            context.startActivity(Intent(context, ViewActivity::class.java))
        }) {
            Text(text = "Vue View")
        }
    }
}