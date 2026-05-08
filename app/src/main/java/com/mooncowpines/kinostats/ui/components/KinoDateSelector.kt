package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import java.time.Month


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KinoDateSelector(
    selectedYear: Int?,
    selectedMonth: Int?,
    onFilterChange: (Int, Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val years = (1900..2026).reversed().toList()
    val months = Month.entries.toList()

    var yearExpanded by remember { mutableStateOf(false) }
    var monthExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = yearExpanded,
            onExpandedChange = { yearExpanded = !yearExpanded },
            modifier = Modifier.weight(1f)
        ) {
                OutlinedTextField(
                    value = selectedYear?.toString() ?: "Select Year",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Year", color = KinoYellow) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = yearExpanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedTextColor = KinoWhite,
                        unfocusedTextColor = KinoWhite,
                        focusedBorderColor = KinoYellow,
                        unfocusedBorderColor = Color.DarkGray
                    ),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = yearExpanded,
                    onDismissRequest = { yearExpanded = false }
                ) {
                    years.forEach { year ->
                        DropdownMenuItem(
                            text = { Text(year.toString()) },
                            onClick = {
                                onFilterChange(year, selectedMonth)
                                yearExpanded = false
                            }
                        )
                    }
                }
            }

        if (selectedYear != null) {
            ExposedDropdownMenuBox(
                expanded = monthExpanded,
                onExpandedChange = { monthExpanded = !monthExpanded },
                modifier = Modifier.weight(1f)
            ) {
                val monthLabel = selectedMonth?.let {
                    Month.of(it).getDisplayName(TextStyle.FULL, Locale.getDefault()).replaceFirstChar { it.uppercase() }
                } ?: "All months"

                OutlinedTextField(
                    value = monthLabel,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Month", color = KinoYellow) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = monthExpanded) },
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                        focusedTextColor = KinoWhite,
                        unfocusedTextColor = KinoWhite,
                        focusedBorderColor = KinoYellow,
                        unfocusedBorderColor = Color.DarkGray
                    ),
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = monthExpanded,
                    onDismissRequest = { monthExpanded = false }
                ) {

                    DropdownMenuItem(
                        text = { Text("All months", fontWeight = FontWeight.Bold) },
                        onClick = {
                            onFilterChange(selectedYear, null)
                            monthExpanded = false
                        }
                    )

                    months.forEach { month ->
                        DropdownMenuItem(
                            text = { Text(month.getDisplayName(TextStyle.FULL, Locale.getDefault()).replaceFirstChar { it.uppercase() }) },
                            onClick = {
                                onFilterChange(selectedYear, month.value)
                                monthExpanded = false
                            }
                        )
                    }
                }
            }
        } else {
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}