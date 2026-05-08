package com.mooncowpines.kinostats.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import co.yml.charts.common.model.PlotType
import com.mooncowpines.kinostats.domain.model.StatItem
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite


@Composable
fun KinoCountryPieChart(countries: List<StatItem<String, Int>>) {
    val dynamicColors = countries.mapIndexed { index, _ ->
        val hue = (360f / countries.size) * index
        Color.hsv(hue = hue, saturation = 0.6f, value = 0.9f)
    }

    val pieSlices = countries.mapIndexed { index, country ->
        PieChartData.Slice(
            label = country.label,
            value = country.value.toFloat(),
            color = dynamicColors[index]
        )
    }

    val pieChartData = PieChartData(
        slices = pieSlices,
        plotType = PlotType.Pie
    )

    val pieChartConfig = PieChartConfig(
        isAnimationEnable = true,
        showSliceLabels = false,
        activeSliceAlpha = 0.9f,
        backgroundColor = KinoBlack
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PieChart(
            modifier = Modifier.size(200.dp),
            pieChartData = pieChartData,
            pieChartConfig = pieChartConfig
        )

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
        countries.forEachIndexed { index, country ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(12.dp).background(dynamicColors[index], CircleShape))
                Spacer(modifier = Modifier.width(8.dp))
                Text("${country.label}: ${country.value}", color = KinoWhite)
            }
        } }
    }
}