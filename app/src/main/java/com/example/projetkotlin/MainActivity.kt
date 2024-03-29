package com.example.projetkotlin

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.projetkotlin.retrofit.Member
import com.example.projetkotlin.ui.theme.ProjetKotlinTheme
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val etat = mutableStateOf(State.INCONNU)
        val permissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            etat.value = if (isGranted) {
                State.AUTORISE
            } else {
                State.REFUSE
            }
        }
        if (this.checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            etat.value = State.AUTORISE
        } else if (this.shouldShowRequestPermissionRationale(android.Manifest.permission.INTERNET)) {
            etat.value = State.EXPLICATION
        } else {
            permissionRequest.launch(android.Manifest.permission.INTERNET)
        }


        setContent {
            ProjetKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (etat.value) {
                        State.INCONNU -> Text(text = "Unknown Permission State")
                        State.AUTORISE ->
                            Column(modifier = Modifier.fillMaxSize()) {
                                Accueil()
                                Test()
                            }

                        State.REFUSE -> Text(text = "Vous avez refusé les permissions.")
                        State.EXPLICATION -> ExplicationPermission(
                            explication = "Nous avons besoin des permissions internet pour le bon fonctionnement de l'application (requêtes à l'api)",
                            permission = android.Manifest.permission.INTERNET,
                            permissionRequest
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ExplicationPermission(
    explication: String,
    permission: String,
    permissionRequest: ActivityResultLauncher<String>
) {
    Column {
        Text(text = explication)
        Button(onClick = { permissionRequest.launch(permission) }) {
            Text(text = "Redemander l'autorisation")
        }
    }
}



@Composable
fun Accueil() {
    val context = LocalContext.current;
    Column() {
        Button(onClick = {
            context.startActivity(Intent(context, ViewActivity::class.java))
        }) {
            Text(text = "View")
        }
    }
}


private fun getMembers(members: MutableLiveData<Member>) = runBlocking {
    val response = ApiClient.apiService.getMembers()
    members.postValue(response.body())
}


@Composable
fun Test() {
    val state = remember { mutableStateOf(true) }
    if (state.value) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { state.value = false }) {
                Text("Ajouter un membre")
            }
            Text(text = "Membres")
        }
        val members = MutableLiveData<Member>()
        getMembers(members);
        Column {
            Text(members.value.toString())
        }
    } else {
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

        Column {
            Button(onClick = { state.value = true }) {
                Text(text = "Retour")
            }
            Text(text = "Ajout d'un membre")
            InputField("Nom", nom, setNom)
            InputField("Prenom", prenom, setPrenom)
            InputField("N° de license", numLicence, setNumLicense)
            InputField("Mot de passe", motDePasse, setMotDePasse)
            InputField("Date de cerificat", dateCertificat, setDateCertificat)
            DropdownField("Type d'abonnement", forfait, selectedForfait, setForfait)
            DropdownField("Prérogative", prerogative, selectedProregative, setPrerogative)
            Button(onClick = { }) {
                Text(text = "Ajouter")
            }
        }
    }

    Column {

    }

}

@Composable
fun InputField(
    text: String,
    value: String,
    setValue: (String) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        Text(text = "$text : ", modifier = Modifier.fillMaxWidth(0.4f))
        TextField(value = value, onValueChange = setValue)
    }
}

@Composable
fun DropdownField(
    text: String,
    liste: List<String>,
    value: Int,
    setValue: (Int) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(75.dp)
    ) {
        Text(text = "$text : ")
        MyDropDownMenu(liste, value, setValue)
    }
}

@Composable
fun MyDropDownMenu(
    liste: List<String>,
    selectedIndex: Int,
    setForfait: (Int) -> Unit,
) {
    val shown = remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .clickable { shown.value = !shown.value }
    ) {
        Row {
            Text(text = liste[selectedIndex])
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "more")
        }
    }
    Box {
        DropdownMenu(expanded = shown.value, onDismissRequest = { shown.value = false }) {
            liste.forEachIndexed() { index, item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        setForfait(index)
                        shown.value = false
                    })
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProjetKotlinTheme {
        Column {
            Test()
            Accueil()
        }
    }
}