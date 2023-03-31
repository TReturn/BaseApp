package com.example.lib_base.room.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * @CreateDate: 2022/3/15 3:28 下午
 * @Author: 青柠
 * @Description:
 */

@Entity(tableName = "wallpaper")
class WallpaperEntity() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var time: Long = 0

    //壁纸ID
    var wallpaperId: String = ""


    @Ignore
    constructor(wallpaperId: String) : this() {
        time = System.currentTimeMillis()
        this.wallpaperId = wallpaperId
    }
}