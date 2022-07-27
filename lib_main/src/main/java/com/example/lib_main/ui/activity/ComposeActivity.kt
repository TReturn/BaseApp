package com.example.lib_main.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.lib_base.constant.RouterUrls
import com.example.lib_main.R

/**
 * @CreateDate: 2022/7/19 11:41
 * @Author: 青柠
 * @Description:
 */
@Route(path = RouterUrls.ROUTER_URL_COMPOSE)
class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard("Android")
        }
    }

    @Composable
    fun MessageCard(name: String) {
        //水平布局
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Image(
                painter = painterResource(R.drawable.ic_fruit_lemon),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    // Set image size to 40 dp
                    .size(40.dp)
                    // Clip image to be shaped as a circle
                    .clip(CircleShape)
            )

            // Add a horizontal space between the image and the column
            Spacer(modifier = Modifier.width(8.dp))

            //垂直布局
            Column {
                Text(text = "Hello $name")
                Text(text = "Jetpack")
            }
        }
    }

    @Preview
    @Composable
    fun PreviewMessageCard() {
        MessageCard("Android")
    }
}