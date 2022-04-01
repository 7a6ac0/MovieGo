package tabacowang.me.moviego.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import tabacowang.me.moviego.R

private val FiraSans = FontFamily(
    Font(R.font.firasans_regular),
    Font(R.font.firasans_medium, FontWeight.W500),
    Font(R.font.firasans_semibold, FontWeight.W600)
)

private val defaultTypography = Typography()
val Typography = Typography(
    h1 = defaultTypography.h1.copy(fontFamily = FiraSans),
    h2 = defaultTypography.h2.copy(fontFamily = FiraSans),
    h3 = defaultTypography.h3.copy(fontFamily = FiraSans),
    h4 = defaultTypography.h4.copy(fontFamily = FiraSans),
    h5 = defaultTypography.h5.copy(fontFamily = FiraSans),
    h6 = defaultTypography.h6.copy(fontFamily = FiraSans),
    subtitle1 = defaultTypography.subtitle1.copy(fontFamily = FiraSans),
    subtitle2 = defaultTypography.subtitle2.copy(fontFamily = FiraSans),
    body1 = defaultTypography.body1.copy(fontFamily = FiraSans),
    body2 = defaultTypography.body2.copy(fontFamily = FiraSans),
    button = defaultTypography.button.copy(fontFamily = FiraSans),
    caption = defaultTypography.caption.copy(fontFamily = FiraSans),
    overline = defaultTypography.overline.copy(fontFamily = FiraSans)
)