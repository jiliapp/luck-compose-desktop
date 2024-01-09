import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.json.JSONException
import org.skyscreamer.jsonassert.JSONCompare
import org.skyscreamer.jsonassert.JSONCompareMode

@Composable
@Preview
fun App() {
    var result by remember { mutableStateOf("") }
    var json1 by remember { mutableStateOf("") }
    var json2 by remember { mutableStateOf("") }

    MaterialTheme {
        Column (modifier = Modifier.fillMaxWidth()){
            Row (modifier = Modifier.padding(16.dp)){
                OutlinedTextField(
                    value = json1,
                    onValueChange = { json1 = it },
                    label = { Text("JSON 1") },
                    modifier = Modifier.fillMaxWidth(0.5F).fillMaxHeight(0.75F).padding(end = 8.dp)
                )
                OutlinedTextField(
                    value = json2,
                    onValueChange = { json2 = it },
                    label = { Text("JSON 2") },
                    modifier = Modifier.fillMaxWidth().fillMaxHeight(0.75F).padding(end = 8.dp)
                )
            }
            Row (modifier = Modifier.fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                ) {
                Image(painter = BitmapPainter(useResource("launcher/icon.png", ::loadImageBitmap)),"Test")

                    Button(onClick = {
                        result = compareJSON(json1, json2)
                    },modifier = Modifier.wrapContentSize()
                    ) {
                        Text("对比")
                    }
            }
            if (result.isNotBlank()) {
                Row {
                    OutlinedTextField(
                        value = result,
                        readOnly = true,
                        onValueChange = {},
                        label = { Text("结果：") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

fun compareJSON(json1: String, json2: String): String {
    return try {
        val comparison = JSONCompare.compareJSON(json1, json2, JSONCompareMode.STRICT)
        if (comparison.passed()) "✅ Same JSON" else "❗ Differences :\n ${comparison.message}"
    } catch (e: JSONException) {
        "❌ Format Error"
    }
}



fun main() = application {
    Window(onCloseRequest = ::exitApplication,
        title = "luck-desktop",
    ) {
        App()
    }
}
