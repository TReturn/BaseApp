package com.example.lib_main.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.lib_main.R

/**
 * @CreateDate: 2024/6/17 18:06
 * @Author: 青柠
 * @Description:
 */
class RewardTipsViewActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           RewardTipsView (){

           }
        }
    }

    @Composable
    fun RewardTipsView(onCloseClick: () -> Unit) { // Add a callback for the close button
        ConstraintLayout(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = Color.Gray, // Replace with your actual background color or drawableshape = RoundedCornerShape(8.dp) // Adjust corner radius as needed
                )
        ) {
            val (giftIcon, textContent, closeIcon) = createRefs()

            Image(
                painter = painterResource(id = R.drawable.ic_reward_gift),
                contentDescription = "Reward Icon",
                modifier = Modifier
                    .size(24.dp)
                    .padding(start = 16.dp)
                    .constrainAs(giftIcon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )

            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(start = 12.dp, top = 8.dp, end = 10.dp, bottom = 8.dp)
                    .constrainAs(textContent) {
                        start.linkTo(giftIcon.end)
                        end.linkTo(closeIcon.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            ) {
                Text(
                    text = "观看完整视频",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "即可领取奖励",
                    color = Color(0xFFFFB82C), // Replace with your actual color
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
            }

            Image(
                painter = painterResource(id = R.drawable.ic_ad_reward_close),
                contentDescription = "Close Icon",
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
                    .clickable { onCloseClick() } // Add click listener
                    .constrainAs(closeIcon) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }

}