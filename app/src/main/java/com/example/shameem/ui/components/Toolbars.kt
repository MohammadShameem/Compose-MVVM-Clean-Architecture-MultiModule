package com.example.shameem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shameem.ui.theme.colorPrimary

@Composable
fun ToolbarWithBackButton(title: String, onBackPressed: () -> Unit) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            IconButton(onClick = onBackPressed) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}

@Composable
fun ToolbarWithButtonLarge(
    toolbarTitle: String,
    toolbarIcon: ImageVector,
    onButtonPressed: () -> Unit
) {
    Box(Modifier.background(color = colorPrimary)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 16.dp),
            //verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.clickable(onClick = onButtonPressed),
                imageVector = toolbarIcon,
                tint = Color.White,
                contentDescription = "Back"
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = toolbarTitle,
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = Color.White
            )
        }
    }
}

@Composable
fun ToolbarWithButtonLargeWithMenu(
    toolbarTitle: String,
    toolbarIcon: ImageVector,
    onBackButtonPressed: () -> Unit,
    onMenuDashboardClicked: () -> Unit,
    onMenuAboutClicked: () -> Unit,
) {
    Box(Modifier.background(color = colorPrimary)) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            //verticalArrangement = Arrangement.Center
        ) {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackButtonPressed) {
                        Icon(
                            toolbarIcon,
                            tint = Color.White, contentDescription = null
                        )
                    }
                },
                actions = {
                    TopAppBarDropdownMenu(onMenuDashboardClicked, onMenuAboutClicked)
                },
                backgroundColor = colorPrimary,
                elevation = 0.dp
            )
            Text(
                modifier = Modifier.padding(start = 16.dp, bottom = 4.dp),
                text = toolbarTitle,
                fontSize = MaterialTheme.typography.h5.fontSize,
                color = Color.White
            )
        }
    }
}


@Composable
fun TopAppBarDropdownMenu(
    onMenuDashboardClicked: () -> Unit,
    onMenuAboutClicked: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded = true
        }) {
            Icon(
                Icons.Filled.MoreVert,
                contentDescription = "More Menu"
            )
        }
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
    ) {
        DropdownMenuItem(onClick = {
            expanded = false
            onMenuDashboardClicked.invoke()

        }) {
            Text(
                text = "Configure",
                modifier = Modifier
                    .fillMaxWidth()

            )
        }
        DropdownMenuItem(onClick = {
            expanded = false
            onMenuAboutClicked.invoke()

        }) {
            Text(
                text = "About",
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ToolbarPre() {
    ToolbarWithButtonLargeWithMenu(
        "Sea Maritime Service",
        Icons.Filled.ArrowBack, {}, {}
    ) {
    }
}