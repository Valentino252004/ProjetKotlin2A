package com.example.projetkotlin.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InputField(
    text: String,
    value: String,
    setValue: (String) -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(75.dp),
        verticalAlignment = Alignment.CenterVertically

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
            .height(75.dp),
        verticalAlignment = Alignment.CenterVertically
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