package com.example.sarah_malik_shopping_app

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sarah_malik_shopping_app.ui.theme.Sarah_malik_shopping_appTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Sarah_malik_shopping_appTheme {
                    Main2()
                }
            }
        }
    }


class Product(val letter: String, val price: String, val description: String)
val products = listOf(
    Product("Product A", "$100", "This is a great product A."),
    Product("Product B", "$150", "This is product B with more features."),
    Product("Product C", "$200", "Premium product C.")
)

@Composable
fun ProductList(selectionChange: (Product) -> Unit){
    LazyColumn {
        item() { Spacer(Modifier.height(50.dp)) }
        items(products) { TextButton(onClick = {selectionChange(it)}){ Text(it.letter)} }
    }
}

@Composable
fun SelectedItem(thing: Product){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(50.dp))
        Text(thing.letter)
        Text(thing.price)
        Text(thing.description)
    }
}

@Composable
fun Main2 () {
    var clicked by remember { mutableStateOf(false)}
    var selected by remember { mutableStateOf(Product("No product selected","N/A", "N/A"))}
    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProductList({selected = it})
                SelectedItem(selected)
            }
        }
        else -> {
            if (clicked) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SelectedItem(selected)
                    TextButton(onClick = { clicked = false }) { Text("Back") }
                }
            } else {
                ProductList({
                    selected = it
                    clicked = true
                })
            }
        }
    }
}