package com.vitrini.app.ui.feed

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vitrini.app.ui.theme.VitriniCream
import com.vitrini.app.ui.theme.VitriniFieldBackground
import com.vitrini.app.ui.theme.VitriniMauve
import com.vitrini.app.ui.theme.VitriniText

@Composable
fun FeedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VitriniCream)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Me",
                fontSize = 26.sp,
                color = VitriniText
            )

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = VitriniMauve
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(430.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(VitriniFieldBackground),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Product Image",
                color = VitriniMauve,
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Description",
            color = VitriniText,
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "B",
                    color = VitriniMauve,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.size(12.dp))

            Text(
                text = "Business.name",
                color = VitriniText,
                fontSize = 18.sp
            )
        }
    }
}