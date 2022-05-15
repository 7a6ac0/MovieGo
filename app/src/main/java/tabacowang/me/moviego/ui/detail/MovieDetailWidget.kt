package tabacowang.me.moviego.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.gowtham.ratingbar.RatingBar
import tabacowang.me.moviego.BuildConfig
import tabacowang.me.moviego.R
import tabacowang.me.moviego.data.remote.model.AuthorDetail
import tabacowang.me.moviego.data.remote.model.Cast
import tabacowang.me.moviego.data.remote.model.MovieData
import tabacowang.me.moviego.data.remote.model.Review
import tabacowang.me.moviego.ui.widget.BuildMovieGenre
import tabacowang.me.moviego.util.Formatter.DATE_TIME_FORMAT
import tabacowang.me.moviego.util.format
import tabacowang.me.moviego.util.parseHtml
import java.util.*

@Composable
fun MovieDetailInfoWidget(movieData: MovieData) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = "${BuildConfig.IMAGE_API_ROOT}w500${movieData.backdropPath}"),
            contentDescription = null,
            modifier = Modifier.aspectRatio(16f.div(9)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = movieData.title ?: stringResource(id = R.string.string_unknown),
            style = MaterialTheme.typography.h5,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = "    ∟${movieData.originalTitle ?: stringResource(id = R.string.string_unknown)}",
            style = MaterialTheme.typography.subtitle1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Text(
            text = movieData.releaseDate?.format() ?: stringResource(id = R.string.string_unknown),
            style = MaterialTheme.typography.h6,
            maxLines = 1,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Row(
            modifier = Modifier.padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val realVoteAverage = movieData.voteAverage ?: 0f
            RatingBar(
                value = realVoteAverage / 2,
                onValueChange = {},
                onRatingChanged = {},
            )
            Text(
                text = "${movieData.voteCount ?: 0} votes ($realVoteAverage star)",
                style = MaterialTheme.typography.subtitle2,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.align(alignment = Alignment.CenterVertically)
            )
        }
        if (!movieData.genreList.isNullOrEmpty()) {
            BuildMovieGenre(modifier = Modifier.padding(horizontal = 8.dp), genreList = movieData.genreList!!)
        }
        if (!movieData.overview.isNullOrEmpty()) {
            Text(
                text = movieData.overview,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun MovieCastWidget(cast: Cast) {
    Box(modifier = Modifier
        .width(100.dp)
        .wrapContentHeight()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Image(
                painter = rememberImagePainter(data = "${BuildConfig.IMAGE_API_ROOT}w500${cast.profilePath}"),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )
            Text(
                text = cast.name ?: stringResource(id = R.string.string_unknown),
                style = MaterialTheme.typography.caption,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun MovieReviewWidget(
    review: Review,
    itemClickListener: ((Review) -> Unit)? = null
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 5.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .clickable { itemClickListener?.invoke(review) }
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                var avatarUrl = review.authorDetail?.avatarPath
                if (avatarUrl?.startsWith("/http") == true) {
                    avatarUrl = avatarUrl.replaceFirst("/", "")
                } else if (avatarUrl?.startsWith("/") == true) {
                    avatarUrl = "https://www.gravatar.com/avatar$avatarUrl"
                }
                Image(
                    painter = rememberImagePainter(data = avatarUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .aspectRatio(1f)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = review.author ?: stringResource(id = R.string.string_unknown),
                        style = MaterialTheme.typography.h6
                    )
                    RatingBar(
                        value = (review.authorDetail?.rating ?: 0f).div(2),
                        onValueChange = {},
                        onRatingChanged = {},
                    )
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(
                            text = review.updatedAt?.format(format = DATE_TIME_FORMAT)
                                ?: stringResource(id = R.string.string_unknown),
                            style = MaterialTheme.typography.body2,
                            textAlign = TextAlign.Right,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            if (!review.content.isNullOrBlank()) {
                Text(
                    text = review.content.parseHtml(),
                    style = MaterialTheme.typography.body1,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MovieReviewPreview() {
    MovieReviewWidget(
        Review(
            author = "SpotaMovie.com",
            authorDetail = AuthorDetail(
                name = "SpotaMovie.com",
                username = "bastag",
                avatarPath = "/h9FkucYmSN3YH5OmrNRWO5rdquS.jpg",
                rating = 9.0f
            ),
            content = "Full Analysis at Spotamovie.com - **Intro** - Pixar’s movies usually have great insights, and even with Turning Red, they provided us with meaningful messages to help our children and us grow our personalities and mindset. - **The Story** - Meilin is an energetic teenager ready to walk into the grown-up world. However, she needs to wear a mask when she is with her parents and another one when she is at school and with her friends. The risk to disappoint her family is too big for Meilin. But something unexpected will happen. Meilin’s life will change drastically because of a secret that lives within her family. When Meilin can’t control her emotions, a new version of herself will appear. But can you hold your feelings? - **Full Analysis and Explanation at** https://www.spotamovie.com/turning-red-review-and-explanation-critic-post-movie-disney-movie-2022",
            updatedAt = Calendar.getInstance(),
            url = "https://www.themoviedb.org/review/623796c71d820f0044cef423"
        )
    )
}
