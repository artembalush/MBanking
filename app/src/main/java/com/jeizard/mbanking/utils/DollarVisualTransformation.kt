package com.jeizard.mbanking.utils

import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText

class DollarVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = AnnotatedString("\$${text.text}")

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return offset + 1
            }

            override fun transformedToOriginal(offset: Int): Int {
                return if (offset > 0) offset - 1 else 0
            }
        }

        return TransformedText(transformedText, offsetMapping)
    }
}