package theplague.ui.gui

import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import theplague.interfaces.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PickItemDialog(item: Iconizable, onResponse: (Boolean)->Unit){
    AlertDialog(
        modifier = Modifier.width(300.dp),
        onDismissRequest = {

        },
        title = { Text(text = "Item found") },
        text = { Text("Do you want to pick the ${item.icon} ") },
        confirmButton = {
            Button(
                onClick = { onResponse(true) }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(
                onClick = { onResponse(false) }
            ) {
                Text("No")
            }
        }
    )
}