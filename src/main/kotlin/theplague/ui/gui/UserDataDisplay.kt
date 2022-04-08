package theplague.ui.gui
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import theplague.interfaces.*


@Composable
fun UserDataDisplay(player : IPlayer){
    val weight = 0.25f
    Row(modifier = Modifier.padding(10.dp)){
        Text("Vehicle: ${player.currentVehicle.icon}", modifier = Modifier.weight(weight), textAlign = TextAlign.Center,)
        Text("Weapon: ${player.currentWeapon.icon}", modifier = Modifier.weight(weight), textAlign = TextAlign.Center,)
        Text("${player.livesLeft}❤", modifier = Modifier.weight(weight), textAlign = TextAlign.Center,)
        Text("Turns: ${player.turns}", modifier = Modifier.weight(weight), textAlign = TextAlign.Center,)
    }
}