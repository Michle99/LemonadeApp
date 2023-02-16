package com.maridev.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.maridev.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeApp()
            }
        }
    }
}


@Preview
@Composable
fun LemonadeApp() {
    // The current step the app is displaying (remember allows
    // the state to be retained across recompositions.

    var currentStep by remember { mutableStateOf(1) }

    // Number of times the lemon needs to be squeezed to turn into a glass of lemonade
    var squeezeCount by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        when(currentStep) {
            1 -> {
                // Display lemon tree image & ask user to pick a lemon from the tree
                LemonadeImageAndTextSqueezer(
                    textLabelResourceId = R.string.lemon_select,
                    drawableResourceId = R.drawable.lemon_tree,
                    contentDescriptionResourceId = R.string.lemon_tree_content_description,
                    onClickImage = {
                        // update next step
                        currentStep = 2
                        // Each time a lemon is picked from the tree, get a new random number
                        // between 2 and 4 (inclusive) for the number of times the lemon needs
                        // to be squeezed to turn into lemonade
                        squeezeCount = (2..4).random()
                    }
                )
            }
            2 -> {
                // display lemon image and ask use to squeeze the lemon
                LemonadeImageAndTextSqueezer(
                    textLabelResourceId = R.string.lemon_squeeze,
                    drawableResourceId = R.drawable.lemon_squeeze,
                    contentDescriptionResourceId = R.string.lemon_content_description,
                    onClickImage = {
                        // decrease the squeeze count by for each click the user performs
                        squeezeCount--
                        // move to the next step when done squeezing
                        if(squeezeCount == 0) {
                            currentStep = 3
                        }
                    }
                )
            }
            3-> {
                // display the glass of lemonade and ask user to drink
                LemonadeImageAndTextSqueezer(
                    textLabelResourceId = R.string.lemon_drink,
                    drawableResourceId = R.drawable.lemon_drink,
                    contentDescriptionResourceId = R.string.lemonade_content_description,
                    onClickImage = {
                        // update to next step
                        currentStep = 4
                    }
                )
            }
            4 -> {
                LemonadeImageAndTextSqueezer(
                    textLabelResourceId = R.string.lemon_empty_glass,
                    drawableResourceId = R.drawable.lemon_restart,
                    contentDescriptionResourceId = R.string.empty_glass_content_description,
                    onClickImage = {
                        // back to step 1
                        currentStep = 1
                    }
                )
            }
        }
    }
}



/**
 * Composable that displays a text label above an image that is clickable.
 *
 * @param textLabelResourceId is the resource ID for the text string to display
 * @param drawableResourceId is the resource ID for the image drawable to display below the text
 * @param contentDescriptionResourceId is the resource ID for the string to use as the content
 * description for the image
 * @param onClickImage will be called when the user clicks the image
 * @param modifier modifiers to set to this composable
 */
@Composable
fun LemonadeImageAndTextSqueezer(
    modifier: Modifier = Modifier,
    textLabelResourceId: Int,
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onClickImage: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(textLabelResourceId),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(drawableResourceId),
            contentDescription = stringResource(contentDescriptionResourceId),
            modifier = Modifier
                .wrapContentSize()
                .clickable(
                    onClick = onClickImage
                )
                .border(
                    BorderStroke(2.dp, Color(105, 205, 216)),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(16.dp)
        )


    }
}