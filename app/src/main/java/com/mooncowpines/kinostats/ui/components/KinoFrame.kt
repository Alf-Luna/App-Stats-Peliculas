package com.mooncowpines.kinostats.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mooncowpines.kinostats.ui.theme.KinoYellow

@Composable
fun KinoFrame(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = KinoYellow,
                shape = CutCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .padding(24.dp)
    ) {
        content()

    }

}