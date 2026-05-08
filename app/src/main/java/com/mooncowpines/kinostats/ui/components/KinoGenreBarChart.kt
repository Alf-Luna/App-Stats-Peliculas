package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mooncowpines.kinostats.domain.model.StatItem
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun KinoGenreBarChart(genres: List<StatItem<String, Int>>, maxMinutes: Int) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
        Text("Top Genres", color = KinoWhite, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))

        genres.forEach { genre ->
            val percentage = if (maxMinutes > 0) genre.value.toFloat() / maxMinutes.toFloat() else 0f

            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(text = genre.label, color = Color.LightGray, modifier = Modifier.width(80.dp), fontSize = 14.sp)

                Box(modifier = Modifier.weight(1f).height(24.dp)) {
                    Box(modifier = Modifier
                        .fillMaxWidth(percentage)
                        .fillMaxHeight()
                        .background(KinoYellow, RoundedCornerShape(4.dp))
                    )
                }
                Text(text = "${genre.value} min", color = KinoWhite, modifier = Modifier.padding(start = 8.dp), fontSize = 12.sp)
            }
        }
    }
}