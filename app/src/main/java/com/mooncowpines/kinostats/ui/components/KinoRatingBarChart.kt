package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import com.mooncowpines.kinostats.domain.model.StatItem
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoBlack

@Composable
fun KinoRatingBarChart(ratings: List<StatItem<Float, Int>>) {
    val ratingSteps = remember { (0..20).map { it * 0.25f } }

    val barDataList = ratingSteps.mapIndexed { index, step ->
        val count = ratings.find { it.label == step }?.value ?: 0
        BarData(
            point = Point(index.toFloat(), count.toFloat()),
            color = KinoYellow,
            label = step.toString()
        )
    }

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(barDataList.size - 1)
        .bottomPadding(10.dp)
        .labelData { i -> if (i % 4 == 0) ratingSteps[i].toInt().toString() else "" } // Solo mostramos 0, 1, 2...
        .axisLabelColor(KinoWhite)
        .axisLineColor(Color.DarkGray)
        .build()

    val maxCount = barDataList.maxOfOrNull { it.point.y } ?: 0f
    val ySteps = 5

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .labelAndAxisLinePadding(15.dp)
        .labelData { i ->
            val value = (maxCount / ySteps) * i
            value.toInt().toString()
        }
        .axisLabelColor(KinoWhite)
        .axisLineColor(Color.DarkGray)
        .build()

    val barChartData = BarChartData(
        chartData = barDataList,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = KinoBlack
    )

    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        BarChart(
            modifier = Modifier.height(250.dp).fillMaxWidth(),
            barChartData = barChartData
        )
    }
}