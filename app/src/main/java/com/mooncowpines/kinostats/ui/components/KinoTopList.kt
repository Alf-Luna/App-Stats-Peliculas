package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.domain.model.StatItem
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun KinoTopList(title: String, items: List<StatItem<String, Int>>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(title, color = KinoYellow, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        HorizontalDivider(color = Color.DarkGray, thickness = 1.dp, modifier = Modifier.padding(vertical = 4.dp))
        items.forEach { item ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(item.label, color = KinoWhite, fontSize = 14.sp)
                Text(item.value.toString(), color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}