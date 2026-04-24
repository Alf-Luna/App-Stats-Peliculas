package com.mooncowpines.kinostats.ui.screens.log

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.data.Movie
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.foundation.layout.padding




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieLogScreen(
    movie: Movie,
    onNavigateBack: () -> Unit,
    onSaveLog: (Float, String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var rating by remember { mutableStateOf(0f) }
    var watchDate by remember { mutableStateOf("23 abr 2026") }
    var reviewText by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = KinoBlack,
        topBar = {
            // 1. BARRA SUPERIOR (Igual a la captura)
            CenterAlignedTopAppBar(
                title = {
                    Text("I watched...", color = Color.Gray, fontSize = 16.sp)
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = KinoWhite)
                    }
                },
                actions = {
                    // Botón de guardar como texto en la esquina superior derecha
                    TextButton(onClick = { onSaveLog(rating, watchDate, reviewText) }) {
                        Text("Save", color = KinoWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = KinoBlack)
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp) // Márgenes laterales globales
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // --- 2. CABECERA: PÓSTER + TÍTULO + AÑO ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Miniatura del póster
                Box(
                    modifier = Modifier
                        .width(64.dp)
                        .height(96.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.DarkGray)
                ) {
                    Image(
                        painter = painterResource(id = movie.thumbnailResId),
                        contentDescription = "Poster",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Título y Año
                Column {
                    Text(
                        text = movie.title,
                        color = KinoWhite,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = movie.year,
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.DarkGray.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))



            // --- 3. SECCIÓN DE FECHA ---
            Text(text = "Specify the date you watched it", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = watchDate,
                color = KinoWhite,
                fontSize = 18.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* TODO: DatePicker */ }
                    .padding(vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.DarkGray.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(16.dp))

            // --- 4. SECCIÓN DE PUNTUACIÓN (DROPDOWN) ---
            Text(text = "Rating", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(12.dp))

            // Reemplazamos el componente viejo por el nuevo selector
            RatingDropdownSelector(
                rating = rating,
                onRatingChange = { rating = it }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.DarkGray.copy(alpha = 0.5f), thickness = 1.dp)

            Text(text = "Review", color = Color.Gray, fontSize = 14.sp)

            TextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 150.dp), // Altura mínima para que parezca un área de texto
                placeholder = {
                    Text("Write a review...", color = Color.DarkGray)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedTextColor = KinoWhite,
                    unfocusedTextColor = KinoWhite,
                    cursorColor = KinoYellow,
                    focusedIndicatorColor = Color.Transparent, // Eliminamos la línea de abajo
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun RatingDropdownSelector(
    rating: Float,
    onRatingChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    // El contenedor principal que actúa como botón
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray.copy(alpha = 0.2f))
            .clickable { expanded = true }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Lo que se muestra cuando está cerrado
            if (rating == 0f) {
                Text("Tap to rate...", color = Color.Gray, fontSize = 16.sp)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = rating.toString(),
                        color = KinoYellow,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(45.dp)
                    )
                    StaticStars(rating = rating) // Muestra las estrellas del valor seleccionado
                }
            }

            // Icono de flecha hacia abajo
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Abrir menú",
                tint = Color.Gray
            )
        }

        // El menú flotante que aparece al hacer clic
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color(0xFF1F252B)) // Color oscuro para el menú
        ) {
            // Lista de opciones (De 0 a 5 en saltos de 0.5)
            val options = listOf(
                0f, 0.25f, 0.5f, 0.75f,
                1f, 1.25f, 1.5f, 1.75f,
                2f, 2.25f, 2.5f, 2.75f,
                3f, 3.25f, 3.5f, 3.75f,
                4f, 4.25f, 4.5f, 4.75f,
                5f
            )

            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (option == 0f) "No rating" else option.toString(),
                                color = if (option == rating) KinoYellow else KinoWhite,
                                modifier = Modifier.width(70.dp)
                            )
                            if (option > 0f) {
                                StaticStars(rating = option)
                            }
                        }
                    },
                    onClick = {
                        onRatingChange(option)
                        expanded = false // Cierra el menú al seleccionar
                    }
                )
            }
        }
    }
}
@Composable
fun StaticStars(rating: Float) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        for (i in 1..5) {
            val fillAmount = (rating - (i - 1)).coerceIn(0f, 1f)
            Box(modifier = Modifier.size(20.dp), contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.fillMaxSize()
                )
                if (fillAmount > 0f) {
                    Box(modifier = Modifier.fillMaxSize().clip(FractionalClip(fillAmount))) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = KinoYellow,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
class FractionalClip(val fraction: Float) : androidx.compose.ui.graphics.Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: androidx.compose.ui.unit.LayoutDirection,
        density: androidx.compose.ui.unit.Density
    ): androidx.compose.ui.graphics.Outline {
        return androidx.compose.ui.graphics.Outline.Rectangle(
            androidx.compose.ui.geometry.Rect(0f, 0f, size.width * fraction, size.height)
        )
    }
}