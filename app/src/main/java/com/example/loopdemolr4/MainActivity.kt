package com.example.triangledemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loopdemolr4.ui.theme.LoopDemoLR4Theme
import kotlin.math.abs

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoopDemoLR4Theme() {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

fun calculateLoop(n: Int, a: Double): Double {
    var term = 1.0/a
    var sum = term

    for (k in 1..n) {
        term /= (a+k)
        sum += term
    }
    return sum
}
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var inputError by remember { mutableStateOf<String?>(null) }
    var valueN by remember { mutableStateOf("") }
    var valueA by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Double?>(null) }
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)

    ) {
        Text("Введите натуральное число n и действительное число a",
            style = MaterialTheme.typography.bodyLarge)
        OutlinedTextField(
            value = valueN,
            placeholder = { Text("Числа 1, 2, 3, ...")},
            onValueChange = {
                valueN = it
            },
            singleLine = true
        )
        OutlinedTextField(
            value = valueA,
            placeholder = { Text("Любые вещественные числа, например, 0.5")},
            onValueChange = {
                valueA = it
            },
            singleLine = true
        )
        Button(
            onClick = {
                inputError = null
                result = null
                val n = valueN.toIntOrNull()
                val a = valueA.replace(',', '.').toDoubleOrNull()
                if (a == null) {
                    inputError = "Введите корректное число а!"
                } else if (abs(a) < 1e-12) {
                    inputError = "Число a не может быть равно 0!"
                }
                else if (n == null || n < 1) {
                    inputError = "Введите корректное число n!"
                } else {
                    result = calculateLoop(n, a)
                }
            }
        ) {
            Text("Вычислить")
        }

        inputError?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }
        result?.let { r ->
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("Результат: ${"%.4f".format(r)}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoopPreview () {
    MainScreen()
}