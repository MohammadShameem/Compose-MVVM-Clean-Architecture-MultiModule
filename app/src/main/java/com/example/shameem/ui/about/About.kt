package com.example.shameem.ui.about

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shameem.R
import com.example.shameem.ui.theme.OfflineCounterTicketingTheme

@Composable
fun About(
    companyName: String,
    version: String,
    type: String
) {
    Column(Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp)) {
        Text(buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
            ) {
                append("${stringResource(R.string.company_name)}: ")
            }

            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
            ) {
                append(companyName)
            }
        })

        Text(buildAnnotatedString {

            withStyle(
                style = SpanStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
            ) {
                append("${stringResource(R.string.version)}: ")
            }

            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
            ) {
                append(version)
            }
        })
        Text(buildAnnotatedString {

            withStyle(
                style = SpanStyle(
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
            ) {
                append("${stringResource(R.string.type)}: ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.h6.fontSize
                )
            ) {
                append(type)
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    OfflineCounterTicketingTheme {
        About("Android", "1.0.0", "Dev/Live")
    }
}
