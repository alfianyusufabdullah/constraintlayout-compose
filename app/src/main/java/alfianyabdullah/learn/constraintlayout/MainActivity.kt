package alfianyabdullah.learn.constraintlayout

import alfianyabdullah.learn.constraintlayout.ui.theme.ConstraintLayoutJetpackComposeTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.*
import androidx.core.view.WindowCompat
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.statusBarsPadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController

const val data = "Yosemite National Park is in California’s Sierra Nevada mountains. It’s famed for its giant, ancient sequoia trees, and for Tunnel View, the iconic vista of towering Bridalveil Fall and the granite cliffs of El Capitan and Half Dome. In Yosemite Village are shops, restaurants, lodging, the Yosemite Museum and the Ansel Adams Gallery, with prints of the photographer’s renowned black-and-white landscapes of the area"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setSystemBarsColor(Color.Transparent, darkIcons = false)
                systemUiController.setNavigationBarColor(Color.Transparent, darkIcons = true)
            }
            ProvideWindowInsets {
                ConstraintLayoutJetpackComposeTheme {
                    ConstraintView()
                }
            }
        }
    }
}

@Composable
fun ConstraintLayoutScope.TopNavIcon(
    int: Int,
    reference: ConstrainedLayoutReference,
    constraint: ConstrainScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .statusBarsPadding()
            .height(45.dp)
            .width(45.dp)
            .clip(shape = RoundedCornerShape(30))
            .background(Color.White)
            .constrainAs(reference, constraint),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = int),
            contentDescription = null
        )
    }
}

@Composable
fun ConstraintLayoutScope.RatingBar(
    reference: ConstrainedLayoutReference,
    constraint: ConstrainScope.() -> Unit
) {
    Row(modifier = Modifier.constrainAs(reference, constraint)) {
        repeat(5) {
            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                painter = painterResource(id = R.drawable.ic_star),
                contentDescription = null
            )
        }
    }
}

@Composable
fun ConstraintLayoutScope.ContentOverview(
    iconRes: Int,
    text: String,
    referenceIcon: ConstrainedLayoutReference,
    referenceText: ConstrainedLayoutReference,
    constraint: ConstrainScope.() -> Unit
) {
    Image(
        modifier = Modifier
            .width(50.dp)
            .height(50.dp)
            .constrainAs(referenceIcon, constraint),
        painter = painterResource(id = iconRes),
        contentDescription = null
    )

    Text(text = text,
        fontFamily = FontFamily(Font(R.font.poppins_medium)),
        modifier = Modifier.constrainAs(referenceText) {
            top.linkTo(referenceIcon.bottom, 8.dp)
            start.linkTo(referenceIcon.start, 16.dp)
            end.linkTo(referenceIcon.end, 16.dp)
        })
}

@Composable
fun ConstraintView() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = PaddingValues(bottom = 62.dp))
            .verticalScroll(state = rememberScrollState())
    ) {
        val (placeName, headerImage, topLeftNav, topRightNav, ratingBar, ratingInfo) = createRefs()
        val (contentBackground, save, map, mile, textSave, textMap, textMile, aboutText, aboutContent) = createRefs()
        createHorizontalChain(save, map, mile, chainStyle = ChainStyle.Spread)

        Image(
            painter = rememberImagePainter(
                builder = {
                    crossfade(true)
                    scale(Scale.FILL)
                },
                data = "https://images.unsplash.com/photo-1498429089284-41f8cf3ffd39?auto=format&fit=crop&w=1050&q=80"
            ),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier
                .height(400.dp)
                .fillMaxWidth()
                .constrainAs(headerImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        TopNavIcon(int = R.drawable.ic_top_back, reference = topLeftNav) {
            top.linkTo(headerImage.top, 20.dp)
            start.linkTo(headerImage.start, 32.dp)
        }

        TopNavIcon(int = R.drawable.ic_top_share, reference = topRightNav) {
            top.linkTo(headerImage.top, 20.dp)
            end.linkTo(headerImage.end, 32.dp)
        }

        RatingBar(reference = ratingBar) {
            bottom.linkTo(placeName.top, 8.dp)
            start.linkTo(placeName.start)
        }

        Text(text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.poppins_bold))
                )
            ) {
                append("4.9 ")
            }
            append("(20221)")
        }, modifier = Modifier.constrainAs(ratingInfo) {
            start.linkTo(ratingBar.end, 16.dp)
            top.linkTo(ratingBar.top)
            bottom.linkTo(ratingBar.bottom)
        }, color = Color.White, fontSize = 14.sp)

        Text(
            text = "Yosemite National Park",
            fontSize = 30.sp,
            color = Color.White,
            lineHeight = 40.sp,
            overflow = TextOverflow.Visible,
            modifier = Modifier.constrainAs(placeName) {
                bottom.linkTo(headerImage.bottom, margin = 16.dp)
                start.linkTo(topLeftNav.start)
                end.linkTo(topRightNav.end, margin = 40.dp)
                width = Dimension.fillToConstraints
            }, fontFamily = FontFamily(Font(R.font.poppins_bold))
        )

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10))
                .background(Color(247,247,247))
                .constrainAs(contentBackground) {
                    top.linkTo(map.top, (-16).dp)
                    start.linkTo(parent.start, 32.dp)
                    end.linkTo(parent.end, 32.dp)
                    bottom.linkTo(textMap.bottom, (-16).dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        ContentOverview(
            iconRes = R.drawable.ic_content_save,
            text = "Save",
            referenceIcon = save,
            referenceText = textSave
        ) {
            top.linkTo(map.top)
        }

        ContentOverview(
            iconRes = R.drawable.ic_content_map,
            text = "Map",
            referenceIcon = map,
            referenceText = textMap
        ) {
            top.linkTo(headerImage.bottom, 42.dp)
        }

        ContentOverview(
            iconRes = R.drawable.ic_content_mile,
            text = "13 Mile",
            referenceIcon = mile,
            referenceText = textMile
        ) {
            top.linkTo(map.top)
        }

        Text(
            text = "About Place",
            fontSize = 20.sp,
            color = Color.DarkGray,
            modifier = Modifier.constrainAs(aboutText) {
                top.linkTo(contentBackground.bottom, margin = 24.dp)
                start.linkTo(topLeftNav.start)
            }, fontFamily = FontFamily(Font(R.font.poppins_bold))
        )

        Text(
            text = data,
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.constrainAs(aboutContent) {
                top.linkTo(aboutText.bottom, 8.dp)
                start.linkTo(topLeftNav.start)
                end.linkTo(topRightNav.end)
                width = Dimension.fillToConstraints
            }, fontFamily = FontFamily(Font(R.font.poppins_medium))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProvideWindowInsets {
        ConstraintLayoutJetpackComposeTheme {
            ConstraintView()
        }
    }
}