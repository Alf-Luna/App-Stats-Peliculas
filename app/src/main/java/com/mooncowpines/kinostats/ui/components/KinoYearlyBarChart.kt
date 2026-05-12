package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.common.model.Point
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray
import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.ui.theme.KinoWhite

@Composable
fun KinoYearlyBarChart(
    data: List<Pair<String, Float>>,
    barColor: Color,
    modifier: Modifier = Modifier
) {

    Text("Time Watched This Year", color = KinoWhite, fontWeight = FontWeight.Bold, fontSize = 20.sp)

    val chartData = data.mapIndexed { index, pair ->
        BarData(
            point = Point(index.toFloat(), pair.second),
            color = barColor,
            label = "Month: ${pair.first}\nHours: ${pair.second}"
        )
    }

    val xAxisData = AxisData.Builder()
        .axisStepSize(KinoSpacing.jumbo)
        .steps(chartData.size - 1)
        .bottomPadding(10.dp)
        .startDrawPadding(KinoSpacing.large)
        .axisLabelColor(KinoWhite)
        .axisLineColor(Color.DarkGray)
        .labelData { index -> data[index].first }
        .build()

    val maxHours = chartData.maxOfOrNull { it.point.y } ?: 1f
    val ySteps = 4

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .labelAndAxisLinePadding(15.dp)
        .labelData { i ->
            val value = (maxHours / ySteps) * i
            "${value.toInt()} hr"
        }
        .axisLabelColor(KinoWhite)
        .axisLineColor(Color.DarkGray)
        .build()

    val barChartData = BarChartData(
        chartData = chartData,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        barStyle = BarStyle(barWidth = 20.dp),
        backgroundColor = KinoBlack
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.width(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Hours Watched",
                    color = KinoWhite.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    maxLines = 1,
                    modifier = Modifier
                        .rotate(-90f)
                        .requiredWidth(120.dp)
                )
            }

            BarChart(
                modifier = Modifier
                    .height(400.dp)
                    .weight(1f),
                barChartData = barChartData
            )
        }

        Text(
            text = "Months",
            color = KinoWhite.copy(alpha = 0.5f),
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}


