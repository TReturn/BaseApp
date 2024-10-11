package com.example.lib_main.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
import com.example.lib_main.R
import com.example.lib_main.model.FruitModel

/**
 * @CreateDate: 2024/6/17 18:06
 * @Author: 青柠
 * @Description:
 */
class ListComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SetSystemBarsColor()
            MyTopAppBar("关于我们") {
                finish()
            }

            MyRowList()
            MyColumnList()
        }
    }

    @Preview
    @Composable
    fun MyRowList() {

        val items: MutableList<FruitModel> = arrayListOf(
            FruitModel("柠檬", R.drawable.ic_fruit_lemon),
            FruitModel("苹果", R.drawable.ic_fruit_apple),
            FruitModel("青柠", R.drawable.ic_fruit_lime),
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp,top = 50.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(items) { item ->
                // 使用自定义的 ListItem Composable
                RowListItem(item)
            }
        }
    }

    @Composable
    fun RowListItem(item: FruitModel) {
        // 在这里自定义列表项的外观和行为
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Toast
                        .makeText(this, "click ${item.name}", Toast.LENGTH_SHORT)
                        .show()
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = item.picture),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
            )

            Text(
                text = item.name,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 10.dp),
            )
        }

    }

    @Preview
    @Composable
    fun MyColumnList() {

        val items: MutableList<FruitModel> = arrayListOf(
            FruitModel("柠檬", R.drawable.ic_fruit_lemon),
            FruitModel("苹果", R.drawable.ic_fruit_apple),
            FruitModel("青柠", R.drawable.ic_fruit_lime),
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
                .padding(top = 150.dp)
        ) {
            items(items) { item ->
                // 使用自定义的 ListItem Composable
                ColumnListItem(item)
            }
        }
    }

    @Composable
    fun ColumnListItem(item: FruitModel) {
        // 在这里自定义列表项的外观和行为
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    Toast
                        .makeText(this, "click ${item.name}", Toast.LENGTH_SHORT)
                        .show()
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.picture),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 10.dp)
            )

            Text(
                text = item.name,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, end = 10.dp),
            )
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


    @Composable
    fun SetSystemBarsColor() {
        val view = LocalView.current
        SideEffect {
            val window = window ?: return@SideEffect
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

}