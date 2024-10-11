package com.example.lib_main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lib_main.model.CategoryTypeData
import com.example.lib_main.viewmodel.ProjectsMainViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.lib_base.web.WebActivity
import com.example.lib_main.R
import me.hgj.jetpackmvvm.ext.nav
import me.hgj.jetpackmvvm.ext.navigateAction

/**
 * @CreateDate: 2024/6/19 15:53
 * @Author: 青柠
 * @Description:
 */
class ProjectsMainCompose : Fragment() {

    private val requestViewModel by viewModels<ProjectsMainViewModel>()

    private val tabsName = mutableStateListOf("")
    private val tabsID: MutableList<String> = arrayListOf()

    private val dataDetail = mutableStateListOf<CategoryTypeData>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        onCreateObserve()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 直接返回一个 ComposeView
        return ComposeView(requireContext()).apply {
            setContent {
                TopTabs()
            }
        }
    }

    private fun initData() {
        requestViewModel.getCategoryData()
    }

    private fun onCreateObserve() {
        requestViewModel.categoryDataState.observe(viewLifecycleOwner) {
            tabsName.clear()
            tabsID.clear()
            for (i in it.indices) {
                tabsName.add(it[i].name)
                tabsID.add(it[i].id.toString())
            }
        }

        requestViewModel.categoryTypeDataState.observe(viewLifecycleOwner) {
            dataDetail.clear()
            dataDetail.addAll(it)
        }
    }

    @Composable
    @Preview
    fun TopTabs() {
        var selectedTabIndex by remember { mutableIntStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTabIndex,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.Start),
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex])
                    )
                }
            ) {
                tabsName.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = { Text(text = title) }
                    )
                }
            }
        }


        if (tabsID.isNotEmpty()) {
            // 根据 selectedTabIndex 显示不同的内容
            TabContent(tabsID[selectedTabIndex])
        }

    }

    @Composable
    fun TabContent(id: String) {

        requestViewModel.getCategoryTypeData(false, id)
        MyColumnList()
    }

    @Composable
    fun MyColumnList() {

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 150.dp)
        ) {
            items(dataDetail) { item ->
                // 使用自定义的 ListItem Composable
                ColumnListItem(item)
            }
        }
    }

    @Composable
    fun ColumnListItem(item: CategoryTypeData) {
        // 在这里自定义列表项的外观和行为
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    WebActivity().start(requireContext(), item.title, item.link)
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.envelopePic,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 10.dp)
            )

            Text(
                text = item.title,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp, end = 10.dp),
            )
        }

    }


}