package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.domain.model.StatItem
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun KinoDecadeLineChart(decades: List<StatItem<Int, Int>>) {
    Text("Movies By Decade", color = KinoWhite, fontWeight = FontWeight.Bold, fontSize = 20.sp)
    val sortedDecades = decades.sortedBy { it.label }

    val points = sortedDecades.mapIndexed { index, item ->
        Point(index.toFloat(), item.value.toFloat())
    }

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .steps(points.size - 1)
        .labelData { i -> "${sortedDecades[i].label}s" }
        .axisLabelColor(KinoWhite)
        .axisLineColor(Color.DarkGray)
        .build()

    val maxY = points.maxOfOrNull { it.y }?.toInt() ?: 0
    val ySteps = if (maxY < 5) maxY.coerceAtLeast(1) else 5

    val yAxisData = AxisData.Builder()
        .steps(ySteps)
        .labelAndAxisLinePadding(15.dp)
        .labelData { i ->
            val value = (maxY.toFloat() / ySteps) * i
            value.toInt().toString()
        }
        .axisLabelColor(KinoWhite)
        .axisLineColor(Color.DarkGray)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = points,
                    lineStyle = LineStyle(
                        color = KinoYellow,
                        style = Stroke(width = 3f)),
                    intersectionPoint = IntersectionPoint(color = KinoWhite, radius = 4.dp),
                    selectionHighlightPoint = SelectionHighlightPoint(color = KinoYellow),
                    shadowUnderLine = ShadowUnderLine(
                        alpha = 0.3f,
                        brush = Brush.verticalGradient(
                            colors = listOf(KinoYellow, Color.Transparent)
                        )
                    ),
                    selectionHighlightPopUp = SelectionHighlightPopUp()
                )
            )
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        backgroundColor = KinoBlack
    )

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

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
            LineChart(
                modifier = Modifier.height(400.dp).fillMaxWidth(),
                lineChartData = lineChartData
            )
        }

        Text(
            text = "Decades",
            color = KinoWhite.copy(alpha = 0.5f),
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        ) }
}