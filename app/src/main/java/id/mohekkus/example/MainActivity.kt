package id.mohekkus.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import id.mohekkus.enumstore.EnumStoreExtension.asStateFlow
import id.mohekkus.enumstore.EnumStoreExtension.set
import id.mohekkus.enumstore.EnumStoreType
import id.mohekkus.example.storage.Variable
import id.mohekkus.example.ui.theme.ExampleTheme
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = Variable.GREETING.asStateFlow(EnumStoreType.TypeString),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: StateFlow<String>, modifier: Modifier = Modifier) {
    val stateName = name.collectAsState()
    Column {
        Text(
            text = "Hello ${stateName.value}!",
            modifier = modifier
        )
        Button(
            onClick = {
                Variable.GREETING.set("")
            }
        ) {
            Text(text = "Change Greeting")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExampleTheme {
//        Greeting()
    }
}