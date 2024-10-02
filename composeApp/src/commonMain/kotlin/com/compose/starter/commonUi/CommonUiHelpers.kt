package com.compose.starter.commonUi

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.compose.starter.spacingsAndBorders.circleBorder
import com.compose.starter.spacingsAndBorders.sizing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.less
import composestarter.composeapp.generated.resources.more
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


fun LazyListScope.listItemVisibility(
    key: Any? = null,
    value: List<Any> = emptyList(),
    content: @Composable () -> Unit,
) {
    if (value.isNotEmpty()) {
        item(key) {
            content()
        }
    }
}


fun LazyListScope.stringItemVisibility(
    key: Any? = null,
    value: String? = null,
    content: @Composable (String) -> Unit,
) {
    if (value.isNullOrEmpty().not()) {
        item(key) {
            content(value ?: "")
        }
    }
}

fun LazyListScope.pairItemVisibility(
    value: Pair<String, String>,
    content: @Composable (Pair<String, String>) -> Unit,
) {
    item {
        content(value)
    }
}


@Composable
fun CircleIcon(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    contentDesc: String = "",
    tint: Color = Color.White,
    onIconClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .then(modifier)
            .size(MaterialTheme.sizing.giant)
            .background(
                Color.Black.copy(
                    alpha = 0.7f
                ),
                shape = MaterialTheme.circleBorder.extraLarge
            ),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = onIconClick,
            modifier = Modifier
                .size(MaterialTheme.sizing.large)
        ) {
            Icon(
                painter = painterResource(icon),
                modifier = Modifier.size(MaterialTheme.sizing.large),
                contentDescription = contentDesc,
                tint = tint
            )
        }
    }
}

@Composable
fun ExpandableText(
    value: String,
    style: TextStyle,
    maxLines: Int = 3,
    expandedText: String = stringResource(Res.string.more),
    unExpandedText: String = stringResource(Res.string.less),
    expandedColor: Color = MaterialTheme.colorScheme.primary,
    unExpandedColor: Color = MaterialTheme.colorScheme.primary,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var visibleText by remember { mutableStateOf(value) }
    var shouldTriggerOnce by remember { mutableStateOf(false) }
    var isExpansionNeeded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .animateContentSize()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = if (isExpansionNeeded) null else LocalIndication.current
            ) {
                isExpanded = !isExpanded
                shouldTriggerOnce = false
            }

    ) {
        Text(
            buildAnnotatedString {
                if (isExpansionNeeded) {
                    append(visibleText)
                    if (isExpanded) {
                        withStyle(style = SpanStyle(color = unExpandedColor)) {
                            append(unExpandedText)
                        }
                    } else {
                        withStyle(style = SpanStyle(color = expandedColor)) {
                            append(expandedText)
                        }
                    }
                } else {
                    append(visibleText)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            style = style,
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            onTextLayout = {
                if (shouldTriggerOnce.not()) {
                    if (it.lineCount == maxLines) {
                        val lastCharIndex = it.getLineEnd(maxLines - 1, true)
                        val subStr = value.subSequence(0..<lastCharIndex)
                        isExpansionNeeded = subStr != value
                        if (isExpansionNeeded) {
                            visibleText =
                                if (isExpanded) value else subStr.substring(0..(lastCharIndex - 1) - expandedText.length)
                        }
                    } else {
                        visibleText = value
                    }
                    shouldTriggerOnce = true
                }
            }
        )
    }
}