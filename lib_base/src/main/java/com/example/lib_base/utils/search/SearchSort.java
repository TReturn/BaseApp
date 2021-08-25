package com.example.lib_base.utils.search;


import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.List;

/**
 * @CreateDate : 2021/8/6
 * @Author : 青柠
 * @Description :
 */
public class SearchSort {
    /**
     * 排序
     *
     * @param stringList
     * @return
     */
    public static List<String> Sort(List<String> stringList) {

        String[] arr = Stream.of(stringList).toArray(String[]::new);
        // 外层循环控制排序趟数
        for (int i = 0; i < arr.length - 1; i++) {
            // 内层循环控制每一趟排序多少次
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j].length() > arr[j + 1].length()) {
                    String temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        List<String> succlist = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            String string = arr[i];
            succlist.add(i, string);
        }
        return succlist;
    }
}
