package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import com.mooncowpines.kinostats.domain.model.StatItem
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.theme.KinoWhite

@Composable
fun KinoRatingBarChart(ratings: List<StatItem<Float, Int>>) {
    Text("Ratings Distribution", color = KinoWhite, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    val ratingSteps = remember { (1..10).map { it * 0.5f } }

    val realBarDataList = ratingSteps.mapIndexed { index, step ->
        val count = ratings.find { it.label == step }?.value ?: 0
        BarData(
            point = Point(index.toFloat(), count.toFloat()),
            color = KinoYellow,
            label = "Rating: $step \nMovies: $count"
        )
    }

    val barDataList = realBarDataList + BarData(
        point = Point(realBarDataList.size.toFloat(), 0f),
        color = Color.Transparent,
        label = ""
    )

    val maxCount = realBarDataList.maxOfOrNull { it.point.y }?.toInt() ?: 0
    val ySteps = if (maxCount < 5) maxCount.coerceAtLeast(1) else 5

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .labelAndAxisLinePadding(15.dp)
        .labelData { i ->
            val value = (maxCount.toFloat() / ySteps) * i
            value.toInt().toString()
        }
        .axisLabelColor(KinoWhite)
        .axisLineColor(Color.DarkGray)
        .backgroundColor(KinoBlack)
        .build()

    Column(
        modifier = Modifier
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
                    text = "Movies Count",
                    color = KinoWhite.copy(alpha = 0.5f),
                    fontSize = 12.sp,
                    maxLines = 1,
                    modifier = Modifier
                        .rotate(-90f)
                        .requiredWidth(120.dp)
                )
            }

            BoxWithConstraints(
                modifier = Modifier.weight(5f)
            ) {
                val minStepSize = 28.dp
                val startPadding = 17.dp

                val availableWidth = maxWidth - startPadding - 16.dp
                val dynamicStepSize = availableWidth / barDataList.size.toFloat()

                val finalStepSize = maxOf(minStepSize, dynamicStepSize)

                val calculatedWidth = (finalStepSize * barDataList.size.toFloat()) + startPadding + 16.dp

                val xAxisData = AxisData.Builder()
                    .axisStepSize(finalStepSize)
                    .steps(barDataList.size - 1)
                    .bottomPadding(10.dp)
                    .labelData { i ->
                        if (i < ratingSteps.size && i % 2 != 0) ratingSteps[i].toInt().toString() else ""
                    }
                    .axisLabelColor(KinoWhite)
                    .axisLineColor(Color.DarkGray)
                    .startDrawPadding(startPadding)
                    .shouldDrawAxisLineTillEnd(false)
                    .backgroundColor(KinoBlack)
                    .build()

                val barChartData = BarChartData(
                    chartData = barDataList,
                    xAxisData = xAxisData,
                    yAxisData = yAxisData,
                    backgroundColor = KinoBlack,
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                ) {
                    BarChart(
                        modifier = Modifier
                            .height(400.dp)
                            .width(calculatedWidth),
                        barChartData = barChartData
                    )
                }
            }
        }

        Text(
            text = "Rating (0.5 - 5)",
            color = KinoWhite.copy(alpha = 0.5f),
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}