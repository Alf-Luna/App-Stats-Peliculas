package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun KinoSummaryCards(
    movies: Int,
    hours: Int,
    minutes: Int,
    modifier: Modifier = Modifier
) {
    KinoFrame {
        val statsList = listOf(
            Pair("Movies\nwatched", movies.toString()),
            Pair("Minutes\nwatched", minutes.toString()),
            Pair("Hours\nwatched", hours.toString())
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(KinoBlack.copy(alpha = 0.5f))
                .padding(vertical = 16.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            statsList.forEach { (label, value) ->
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = value,
                        color = KinoYellow,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = label,
                        color = KinoWhite.copy(alpha = 0.7f),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                }
            }
        }
    }
}