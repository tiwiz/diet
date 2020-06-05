package io.rob.diet.meal

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class UiData(
    val definition: String? = null,
    @StringRes val definitionRes: Int? = null,
    val weight: Int? = null,
    val unit: String? = null,
    @DrawableRes val iconRes: Int? = null,
    val typeDetail: String? = null)