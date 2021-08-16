package alfianyabdullah.learn.constraintlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import alfianyabdullah.learn.constraintlayout.ui.theme.ConstraintLayoutJetpackComposeTheme
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConstraintLayoutJetpackComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    ConstraintView("Android")
                }
            }
        }
    }
}

@Composable
fun ConstraintView(name: String) {
    val applicationContext = LocalContext.current.applicationContext
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "ConstraintLayout Compose") })
    }) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (welcomeMessage, texting, called) = createRefs()
            createHorizontalChain(texting, called, chainStyle = ChainStyle.Spread)

            Text(
                text = "Hai $name! Welcome to the Club",
                modifier = Modifier.constrainAs(welcomeMessage) {
                    top.linkTo(parent.top, margin = 32.dp)
                    centerHorizontallyTo(parent)
                })

            Button(
                onClick = {
                    Toast.makeText(applicationContext, "Message", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.constrainAs(texting) {
                    top.linkTo(welcomeMessage.bottom, margin = 32.dp)
                }) {
                Text(text = "Message")
            }

            Button(
                onClick = {
                    Toast.makeText(applicationContext, "Called", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.constrainAs(called) {
                    top.linkTo(welcomeMessage.bottom, margin = 32.dp)
                }) {
                Text(text = "Call")
            }


        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ConstraintLayoutJetpackComposeTheme {
        ConstraintView("Android")
    }
}