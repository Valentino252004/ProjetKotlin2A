package com.example.projetkotlin.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null
) {
    Text(
        text = text,
        modifier = modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp),
        fontWeight = fontWeight
    )
}