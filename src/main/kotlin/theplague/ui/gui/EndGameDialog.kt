package theplague.ui.gui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theplague.interfaces.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EndGameScreen(player: IPlayer){
    Column(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        Text(text = "Game Ended", fontSize = 30.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp))
        Text("Game ended. You have lived ${player.turns} turns")

    }
}