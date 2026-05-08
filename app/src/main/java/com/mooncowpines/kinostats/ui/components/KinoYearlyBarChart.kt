package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import co.yml.charts.ui.barchart.models.BarStyle
import co.yml.charts.common.model.Point
import com.mooncowpines.kinostats.ui.theme.KinoLighterGray
import com.mooncowpines.kinostats.ui.theme.KinoSpacing
import com.mooncowpines.kinostats.ui.theme.KinoWhite

@Composable
fun KinoYearlyBarChart(
    data: List<Pair<String, Float>>,
    barColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(KinoLighterGray)
    ) {
        val chartData = data.mapIndexed { index, pair ->
            BarData(
                point = Point(index.toFloat(), pair.second),
                color = barColor
            )
        }

        val xAxisData = AxisData.Builder()
            .axisStepSize(KinoSpacing.jumbo)
            .steps(chartData.size)
            .bottomPadding(KinoSpacing.extraLarge)
            .startDrawPadding(KinoSpacing.large)
            .axisLabelColor(KinoWhite)
            .labelData { index -> data[index].first }
            .build()

        val yAxisData = AxisData.Builder()
            .steps(4)
            .topPadding(KinoSpacing.extraLarge)
            .bottomPadding(KinoSpacing.extraLarge)
            .labelData { i -> "${(i * 10)} hr" }
            .axisLabelColor(Color.White)
            .build()

        val barChartData = BarChartData(
            chartData = chartData,
            xAxisData = xAxisData,
            yAxisData = yAxisData,

            barStyle = BarStyle(barWidth = 20.dp),
            backgroundColor = Color.Transparent
        )

        BarChart(modifier = modifier.height(300.dp), barChartData = barChartData)
    }
}


