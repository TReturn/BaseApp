package com.example.lib_main.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.blankj.utilcode.util.AppUtils
import com.example.lib_main.R

/**
 * @CreateDate: 2022/7/19 11:41
 * @Author: 青柠
 * @Description:
 */
class AboutComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetSystemBarsColor()
            MyTopAppBar("关于我们") {
                finish()
            }

            MessageCard(AppUtils.getAppName(),"当前版本：V${AppUtils.getAppVersionName()}")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyTopAppBar(title: String, onBackClick: () -> Unit) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = title,
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            },
            navigationIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowBackIosNew,
                    contentDescription = "Back",
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable { onBackClick() }
                )
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                // 设置背景颜色
                containerColor = Color.White
            )
        )
    }


    @Preview
    @Composable
    fun MessageCard(appName:String ="BaseApp",appVersion:String ="当前版本：V1.0.0") {

        //垂直布局
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_fruit_lemon),
                contentDescription = "Contact profile picture",
                modifier = Modifier
                    .size(100.dp)
            )

            Text(
                text = appName,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 18.dp)
            )

            Text(
                text = appVersion,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 6.dp)
            )
        }
    }

    @Composable
    fun SetSystemBarsColor() {
        val view = LocalView.current
        SideEffect {
            val window = window ?: return@SideEffect
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }


    @Preview
    @Composable
    fun PreviewMessageCard() {
        MyTopAppBar(title = "关于我们") {}
    }
}