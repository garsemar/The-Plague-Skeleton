package theplague.ui.gui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import theplague.interfaces.*
import kotlinx.coroutines.channels.Channel

@ExperimentalMaterialApi
@Composable
@Preview
fun MainScreen(world: IWorld) {
    val scope = rememberCoroutineScope()
    var world by remember{mutableStateOf<IWorld?>(world)}
    val snackbarHostState = remember { SnackbarHostState() }
    var pickItemDialog by remember { mutableStateOf<Iconizable?>(null)}
    val pickItemChannel = Channel<Boolean>()

//    val roboto = FontFamily(
//        androidx.compose.ui.text.platform.Font(
//            resource = "Roboto-Regular.ttf",
//            weight = FontWeight.Normal,
//            style = FontStyle.Normal
//        )
//    )
//    val typography = Typography(defaultFontFamily = roboto)


    MaterialTheme() {
        if (pickItemDialog!=null) {
            PickItemDialog(pickItemDialog!!){
                pickItemDialog = null
                scope.launch { pickItemChannel.send(it) }
            }
        }
        val currentWorld = world
        if(currentWorld!=null) {
            if(currentWorld.gameFinished()){
                EndGameScreen(currentWorld.player)
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    UserDataDisplay(currentWorld.player)


                    currentWorld.territories.forEachIndexed { y, row ->
                        Row() {
                            row.forEachIndexed { x, cell ->
                                TerritoryDisplay(cell) {
                                    scope.launch {
                                        val position = Position(x, y)
                                        if (!currentWorld.canMoveTo(position)) {
                                            snackbarHostState.showSnackbar("You can't go to this position with the current vehicle")
                                            return@launch
                                        }

                                        currentWorld.moveTo(position)
                                        val item = currentWorld.takeableItem()
                                        if (item != null) {
                                            pickItemDialog = item
                                            val take = pickItemChannel.receive()
                                            if (take)
                                                currentWorld.takeItem()
                                        }
                                        currentWorld.exterminate()
                                        currentWorld.nextTurn()
                                        world = null
                                        world = currentWorld
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        SnackbarHost(hostState = snackbarHostState)
    }
}