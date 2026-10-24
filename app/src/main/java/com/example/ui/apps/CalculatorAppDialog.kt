package com.example.ui.apps

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorAppDialog(
    onDismiss: () -> Unit
) {
    var display by remember { mutableStateOf("0") }
    var operand1 by remember { mutableStateOf<Double?>(null) }
    var pendingOp by remember { mutableStateOf<String?>(null) }
    var shouldClearOnNext by remember { mutableStateOf(false) }

    fun onNumber(digit: String) {
        if (display == "0" || shouldClearOnNext) {
            display = digit
            shouldClearOnNext = false
        } else {
            display += digit
        }
    }

    fun onOperator(op: String) {
        operand1 = display.toDoubleOrNull()
        pendingOp = op
        shouldClearOnNext = true
    }

    fun onEquals() {
        val op1 = operand1 ?: return
        val op2 = display.toDoubleOrNull() ?: return
        val result = when (pendingOp) {
            "+" -> op1 + op2
            "-" -> op1 - op2
            "×" -> op1 * op2
            "÷" -> if (op2 != 0.0) op1 / op2 else Double.NaN
            else -> op2
        }
        display = if (result.isNaN()) "Hata" else if (result % 1.0 == 0.0) result.toLong().toString() else result.toString()
        operand1 = null
        pendingOp = null
        shouldClearOnNext = true
    }

    fun onClear() {
        display = "0"
        operand1 = null
        pendingOp = null
        shouldClearOnNext = false
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E293B))
            .statusBarsPadding()
            .testTag("calculator_app_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismiss) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Geri",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Hesap Makinesi",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 24.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = display,
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }

            // Keypad
            val buttons = listOf(
                listOf("C", "±", "%", "÷"),
                listOf("7", "8", "9", "×"),
                listOf("4", "5", "6", "-"),
                listOf("1", "2", "3", "+"),
                listOf("0", ".", "=")
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                buttons.forEach { row ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        row.forEach { btn ->
                            val isOp = btn in listOf("÷", "×", "-", "+", "=")
                            val isAction = btn in listOf("C", "±", "%")
                            val weight = if (btn == "0") 2f else 1f

                            Box(
                                modifier = Modifier
                                    .weight(weight)
                                    .height(64.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isOp -> Color(0xFFF59E0B)
                                            isAction -> Color(0xFF475569)
                                            else -> Color(0xFF334155)
                                        }
                                    )
                                    .clickable {
                                        when (btn) {
                                            "C" -> onClear()
                                            "±" -> display = (display.toDoubleOrNull()?.let { -it } ?: 0.0).toString()
                                            "%" -> display = ((display.toDoubleOrNull() ?: 0.0) / 100).toString()
                                            "÷", "×", "-", "+" -> onOperator(btn)
                                            "=" -> onEquals()
                                            "." -> if (!display.contains(".")) display += "."
                                            else -> onNumber(btn)
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = btn,
                                    color = Color.White,
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
