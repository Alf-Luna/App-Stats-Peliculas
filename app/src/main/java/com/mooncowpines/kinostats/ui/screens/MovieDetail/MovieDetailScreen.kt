package com.mooncowpines.kinostats.ui.screens.MovieDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mooncowpines.kinostats.R
import com.mooncowpines.kinostats.ui.theme.KinoBlack
import com.mooncowpines.kinostats.ui.theme.KinoWhite
import com.mooncowpines.kinostats.ui.theme.KinoYellow
import com.mooncowpines.kinostats.data.Movie
import com.mooncowpines.kinostats.ui.components.KinoButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
@Composable
fun MovieDetailScreen(
    movie: Movie,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(KinoBlack)
            .verticalScroll(scrollState)
    ) {
        // --- 1. Top Banner / Poster ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        ) {
            // Placeholder for the main banner image (e.g., from an API later)
            Image(
                painter = painterResource(id = movie.bannerResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            // Overlay gradient or darkening could go here
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f))
            )
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.padding(top = 32.dp, start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = KinoWhite
                )
            }
        }

        // --- 2. Title, Details, and Thumbnail ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movie.title,
                    color = KinoWhite,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = movie.year, color = Color.Gray, fontSize = 14.sp)
                    Text(text = "  ·  DIRECTED BY", color = Color.Gray, fontSize = 12.sp)
                }
                Text(text = movie.director, color = KinoWhite, fontSize = 16.sp)

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    KinoButton(text = "▶ TRAILER", onClick = { /* TODO */ })
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(text = movie.duration, color = Color.Gray, fontSize = 14.sp)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Small Thumbnail Poster
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Gray) // Placeholder color
            ) {
                // You would load the thumbnail here with Coil/Glide
                Image(
                    painter = painterResource(id = movie.thumbnailResId),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // --- 3. Synopsis / Description ---
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = movie.tagLine,
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movie.synopsis,
                color = Color.LightGray,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }

        Divider(color = Color.DarkGray, thickness = 1.dp)

        // --- 4. Ratings Section ---
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "RATINGS",
                color = Color.Gray,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Placeholder for the bar chart
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    val heights = listOf(2, 5, 8, 15, 25, 40, 30, 15, 5, 10)
                    heights.forEach { height ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(height.dp)
                                .background(Color.DarkGray)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "3.8",
                        color = Color.LightGray,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light
                    )
                    Row {
                        repeat(5) {
                            Text("★", color = KinoYellow, fontSize = 12.sp) // Using your KinoYellow
                        }
                    }
                }
            }
        }

        Divider(color = Color.DarkGray, thickness = 1.dp)

        // --- 5. Where to Watch ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Where to watch",
                color = Color.LightGray,
                fontSize = 16.sp
            )
            Text(
                text = ">", // Placeholder for icon
                color = Color.Gray,
                fontSize = 16.sp
            )
        }

        Divider(color = Color.DarkGray, thickness = 1.dp)

        // --- 6. Stats Cards (Members, Reviews, Lists) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatCard(modifier = Modifier.weight(1f), title = "Members", value = movie.membersCount, color = Color(0xFF00C853))
            StatCard(modifier = Modifier.weight(1f), title = "Reviews", value = movie.reviewsCount, color = Color(0xFF78909C))
            StatCard(modifier = Modifier.weight(1f), title = "Lists", value = movie.listsCount, color = Color(0xFF29B6F6))
        }

        Divider(color = Color.DarkGray, thickness = 1.dp)

        // --- 7. Tabs (Cast, Crew, Details, etc.) ---
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Cast", color = KinoWhite, fontWeight = FontWeight.Bold)
            Text("Crew", color = Color.Gray)
            Text("Details", color = Color.Gray)
            Text("Genre", color = Color.Gray)
            Text("Releases", color = Color.Gray)
        }
        // Indicator for the active tab
        Box(modifier = Modifier.padding(horizontal = 16.dp).height(2.dp).width(30.dp).background(KinoYellow))

        Spacer(modifier = Modifier.height(30.dp)) // Extra space at bottom

    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, title: String, value: String, color: Color) {
    Box(
        modifier = modifier
            .height(80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(text = title, color = KinoWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(text = value, color = KinoWhite, fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview() {
    val dummyMovie = Movie(
        id = 1,
        title = "Hoppers",
        year = "2026",
        director = "Daniel Chong",
        duration = "105 mins",
        tagLine = "ACT NATURAL.",
        synopsis = "Scientists have discovered how to 'hop' human consciousness into lifelike robotic animals...",
        rating = 3.8f,
        membersCount = "523k",
        reviewsCount = "228k",
        listsCount = "95k",
        bannerResId = R.drawable.kinostats_banner,
        thumbnailResId = R.drawable.kinostats_logo
    )
    MovieDetailScreen(
        movie = dummyMovie,
        onNavigateBack = {}
    )
}