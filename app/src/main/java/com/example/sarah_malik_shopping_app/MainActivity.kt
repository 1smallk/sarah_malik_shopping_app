package com.example.sarah_malik_shopping_app

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiPaneShoppingApp()
        }
    }
}

data class Product(val name: String, val price: String, val description: String)

val products = listOf(
    Product("Product A", "$100", "This is a great product A."),
    Product("Product B", "$150", "This is product B with more features."),
    Product("Product C", "$200", "Premium product C."),
    Product("Product D", "$250", "Top-of-the-line product D."),
    Product("Product E", "$300", "Exclusive product E with superior quality.")
)

@Composable
fun ProductList(onProductSelected: (Product) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(products) { product ->
            TextButton(
                onClick = { onProductSelected(product) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = product.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Divider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

@Composable
fun ProductDetails(selectedProduct: Product?, onBackClicked: () -> Unit) {
    if (selectedProduct != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = selectedProduct.name,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Price: ${selectedProduct.price}",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = selectedProduct.description,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(onClick = onBackClicked) {
                Text("Back to Product List")
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Select a product to view details.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun MultiPaneShoppingApp() {
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // Product List Pane
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    ProductList { product ->
                        selectedProduct = product
                    }
                }

                // Product Details Pane
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    ProductDetails(selectedProduct) {
                        selectedProduct = null // Reset selection
                    }
                }
            }
        }

        else -> {
            if (selectedProduct != null) {
                // Display Product Details in Portrait Mode with Back Button
                ProductDetails(selectedProduct) {
                    selectedProduct = null // Reset selection
                }
            } else {
                // Display Product List in Portrait Mode
                ProductList { product ->
                    selectedProduct = product
                }
            }
        }
    }
}