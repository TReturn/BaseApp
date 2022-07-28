package com.example.lib_main.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.AppUtils
import com.example.lib_base.constant.RouterUrls
import com.example.lib_main.R

/**
 * @CreateDate: 2022/7/19 11:41
 * @Author: 青柠
 * @Description:
 */
@Route(path = RouterUrls.ROUTER_URL_ABOUT_COMPOSE)
class AboutComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TitleBar("关于我们")
            MessageCard()
        }
    }

    @Composable
    fun TitleBar(title: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,

        ) {

            Image(painter = painterResource(id = R.drawable.ic_finish),
                contentDescription = "finish",
                modifier = Modifier
                    .size(24.dp)
                    .clickable { finish() })

            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
            )
        }
    }

    @Composable
    fun MessageCard() {
        //垂直布局
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {
            Image(
                painter = painterResource(R.drawable.ic_fruit_lemon),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(104.dp)
            )

            Spacer(modifier = Modifier.height(18.dp))
            Text(
                text = AppUtils.getAppName(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "当前版本：V${AppUtils.getAppVersionName()}",
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }

    @Preview
    @Composable
    fun PreviewMessageCard() {
        TitleBar("关于我们")
        //MessageCard()
    }
}