package tabacowang.me.moviego.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun HeaderWidget(
    title: String,
    showPlaceHolder: Boolean = false,
    onClickListener: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.placeholder(visible = showPlaceHolder, highlight = PlaceholderHighlight.fade())
        )
        if (onClickListener != null) {
            Button(
                onClick = onClickListener,
                modifier = Modifier.placeholder(visible = showPlaceHolder, highlight = PlaceholderHighlight.fade())
            ) {
                Text(text = "See All")
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HeaderWidgetPreview() {
    HeaderWidget(title = "MovieGo") {
        println("OnClick")
    }
}