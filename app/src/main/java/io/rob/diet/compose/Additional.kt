package io.rob.diet.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*

@Composable
fun DietButton(
    label: String,
    borderColor: Color = Color.LightGray,
    onClick: () -> Unit = {}
) {

    Button(
        onClick = { onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = borderColor),
                shape = RoundedCornerShape(2.dp)
            ),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = borderColor)
    ) {
        Text(
            text = label.toUpperCase(Locale.getDefault()),
            style = MaterialTheme.typography.body2,
            color = borderColor
        )
    }
}

@Composable
fun DietTitle(
    @StringRes titleRes: Int,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null
) {
    DietTitleInternal(
        titleRes = titleRes,
        modifier = modifier,
        fontWeight = fontWeight
    )
}

@Composable
private fun DietTitleInternal(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int? = null,
    title: String? = null,
    fontWeight: FontWeight? = null
) {

    if (titleRes == null && title == null) {
        error("Both title and titleRes are null!")
    }

    Text(
        text = titleRes?.let { stringResource(id = titleRes) } ?: title!!,
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h1,
        fontWeight = fontWeight
    )
}

@Composable
fun DietTitle(
    title: String,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null
) {
    DietTitleInternal(
        title = title,
        modifier = modifier,
        fontWeight = fontWeight
    )
}

@Composable
fun DietSubtitle(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        label,
        modifier = modifier,
        color = MaterialTheme.colors.primary,
        style = MaterialTheme.typography.h2
    )
}

@Composable
fun LinkifiedText(
    text: String,
    modifier: Modifier = Modifier,
    link: String,
    start: Int = 0,
    end: Int = text.length - 1
) {

    val linkifiedText = buildAnnotatedString {
        append(text)
        addStringAnnotation(
            tag = "URL",
            annotation = link,
            start = start,
            end = end
        )
    }

    Text(
        text = linkifiedText,
        modifier = modifier,
        style = MaterialTheme.typography.body1
    )
}

@Composable
fun AppTitle(color: Color) {
    val title = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = color,
                fontWeight = FontWeight.Bold
            )
        ) {
            append("Diet")
        }
        withStyle(
            style = SpanStyle(
                color = color,
                fontWeight = FontWeight.Light
            )
        ) {
            append("Tracker")
        }
    }

    Text(
        text = title,
        modifier = Modifier.padding(all = 16.dp),
        fontSize = 36.sp
    )
}