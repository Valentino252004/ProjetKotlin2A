package com.example.projetkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.projetkotlin.components.DropdownField
import com.example.projetkotlin.components.InputField
import com.example.projetkotlin.components.TableCell
import com.example.projetkotlin.ui.theme.ProjetKotlinTheme
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjetKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Test()
                        val apiRequest: APIClient by viewModels()
                        Accueil(apiRequest)
                    }
                }
            }
        }
    }
}


@Composable
fun Test() {
    val context = LocalContext.current
    Column {
        Button(onClick = {
            context.startActivity(Intent(context, ViewActivity::class.java))
        }) {
            Text(text = "View")
        }
    }
}

@Composable
fun Accueil(apiRequest: APIClient) {
    val (state, setState) = remember { mutableStateOf(false) }
    if (state) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { setState(false) }) {
                Text("Ajouter un membre")
            }
        }
        val data = apiRequest.getResult().observeAsState()
        apiRequest.getMembers()
        Column {
            if (data.value == null) {
                CircularProgressIndicator()
            } else {
                MembersDisplay(JSONObject(data.value!!))
            }
        }
    } else {
        AddMember(setState)
    }
}

@Composable
fun MembersDisplay(members: JSONObject) {
    Log.d("tag", members.toString())
    val data = members.getJSONArray("data")
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Text(text = "Cliquer sur un membre pour le modifier", fontSize = 20.sp)
    }
    Row(Modifier.padding(3.dp, 0.dp)) {
        TableCell(text = "N° Licence", weight = 1f / 3f, fontWeight = FontWeight.Bold)
        TableCell(text = "Nom", weight = 1f / 3f, fontWeight = FontWeight.Bold)
        TableCell(text = "Prénom", weight = 1f / 3f, fontWeight = FontWeight.Bold)
    }
    for (i in 0..<data.length()) {
        val member = data.get(i) as JSONObject
        val attributes = member.get("attributes") as JSONObject
        Row(Modifier.padding(3.dp, 0.dp)) {
            TableCell(text = attributes.getString("licence"), weight = 1f / 3f)
            TableCell(text = attributes.getString("name"), weight = 1f / 3f)
            TableCell(text = attributes.getString("surname"), weight = 1f / 3f)
        }
    }
}

@Composable
fun AddMember(setState: (Boolean) -> Unit) {
    val forfait = listOf("enfant", "adulte")
    val prerogative = listOf(
        "PB", "PA", "PO-12", "PO-20", "N1", "PA-12", "PE-40", "N2",
        "PA-40", "PE-60", "N3", "N4GP", "E1", "E2", "E3", "E4"
    )
    val (nom, setNom) = remember {
        mutableStateOf("")
    }
    val (prenom, setPrenom) = remember {
        mutableStateOf("")
    }
    val (numLicence, setNumLicense) = remember {
        mutableStateOf("")
    }
    val (motDePasse, setMotDePasse) = remember {
        mutableStateOf("")
    }
    val (dateCertificat, setDateCertificat) = remember {
        mutableStateOf("")
    }
    val (selectedForfait, setForfait) = remember {
        mutableIntStateOf(0)
    }
    val (selectedProregative, setPrerogative) = remember {
        mutableIntStateOf(0)
    }

    Column(modifier = Modifier.padding(3.dp, 0.dp)) {
        Button(onClick = { setState(true) }) {
            Text(text = "Retour")
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 6.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Ajout d'un membre", fontSize = 20.sp)
        }
        InputField("Nom", nom, setNom)
        InputField("Prenom", prenom, setPrenom)
        InputField("N° de license", numLicence, setNumLicense)
        InputField("Mot de passe", motDePasse, setMotDePasse)
        InputField("Date de cerificat", dateCertificat, setDateCertificat)
        DropdownField("Type d'abonnement", forfait, selectedForfait, setForfait)
        DropdownField("Prérogative", prerogative, selectedProregative, setPrerogative)
        Row (Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = { }, modifier = Modifier.fillMaxWidth(0.4f)) {
                Text(text = "Ajouter")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjetKotlinTheme {
        Column {
        }
    }
}