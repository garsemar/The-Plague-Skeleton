package theplague.ui.gui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import theplague.interfaces.*

@Composable
fun TerritoryDisplay(territory: ITerritory, onClick: ()->Unit){
    Box(modifier = Modifier.width(80.dp).height(80.dp).border(1.dp, Color.Black).clickable {
        onClick()
    }) {
        Text(
            territory.iconList().map { it.icon }.joinToString(" "),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,

            )
    }
}