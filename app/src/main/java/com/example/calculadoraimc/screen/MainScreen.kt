package com.example.calculadoraimc.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var pesoTexto by remember { mutableStateOf("") }
    var alturaTexto by remember { mutableStateOf("") }
    var resultadoIMC by remember { mutableStateOf("") }
    var categoriaIMC by remember { mutableStateOf("") }
    var mostrarDetalles by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF2F6FF)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "📋 Calculadora de IMC",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A237E),
                    textAlign = TextAlign.Center
                )

                Text(
                    "Índice de Masa Corporal",
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                OutlinedTextField(
                    value = pesoTexto,
                    onValueChange = { pesoTexto = it },
                    label = { Text("Peso (kg)") },
                    placeholder = { Text("Ej: 70") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = alturaTexto,
                    onValueChange = { alturaTexto = it },
                    label = { Text("Altura (cm o m)") },
                    placeholder = { Text("Ej: 170 o 1.70") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            val peso = pesoTexto.toFloatOrNull()
                            val alturaBruto = alturaTexto.toFloatOrNull()

                            if (peso == null || alturaBruto == null) {
                                resultadoIMC = "⚠️ Los datos no son validos ⚠️"
                                categoriaIMC = ""
                                mostrarDetalles = false
                                return@Button
                            }

                            val alturaM = if (alturaBruto > 3) alturaBruto / 100 else alturaBruto
                            val imc = peso / (alturaM * alturaM)
                            resultadoIMC = "%.1f".format(imc)

                            categoriaIMC = when {
                                imc < 18.5 -> "Bajo peso"
                                imc < 24.9 -> "Peso normal"
                                imc < 29.9 -> "Sobrepeso"
                                else -> "Obesidad"
                            }

                            mostrarDetalles = true
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2962FF))
                    ) {
                        Text("Calcular IMC", color = Color.White)
                    }

                    OutlinedButton(
                        onClick = {
                            pesoTexto = ""
                            alturaTexto = ""
                            resultadoIMC = ""
                            categoriaIMC = ""
                            mostrarDetalles = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Limpiar")
                    }
                }

                if (resultadoIMC.isNotEmpty()) {
                    // Detectamos si el mensaje es el de aviso de datos inválidos
                    val esError = resultadoIMC.startsWith("⚠️")

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF3F4F6),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Si quieres ocultar "Tu IMC es:" en caso de error, podrías envolverlo en un if (!esError)
                            Text("Tu IMC es:", color = Color.Gray)

                            Text(
                                text = resultadoIMC,
                                // ← CAMBIO: fontSize condicional: 16.sp para el error, 28.sp para el IMC normal
                                fontSize = if (esError) 16.sp else 28.sp,
                                fontWeight = if (esError) FontWeight.Normal else FontWeight.Bold, // opcional: cambiar peso
                                color = if (esError) Color.Red else Color.Black // opcional: color distinto en error
                            )

                            // El texto de categoría (se queda vacío cuando es error)
                            if (!esError) {
                                Text(
                                    text = categoriaIMC,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = when (categoriaIMC) {
                                        "Bajo peso" -> Color.Blue
                                        "Peso normal" -> Color(0xFF2E7D32)
                                        "Sobrepeso" -> Color(0xFFFFA000)
                                        "Obesidad" -> Color.Red
                                        else -> Color.Gray
                                    }
                                )
                            }
                        }
                    }
                }

                Divider(modifier = Modifier.padding(top = 8.dp))

                Column(horizontalAlignment = Alignment.Start) {
                    Text("Categorías del IMC:", fontWeight = FontWeight.SemiBold)
                    Text("• Bajo peso: < 18.5")
                    Text("• Peso normal: 18.5 - 24.9")
                    Text("• Sobrepeso: 25.0 - 29.9")
                    Text("• Obesidad: ≥ 30.0")
                }
            }
        }
    }
}
