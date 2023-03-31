package com.example.lib_base.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lib_base.room.dao.WallpaperDao
import com.example.lib_base.room.entity.WallpaperEntity

/**
 * @CreateDate: 2022/3/15 3:29 下午
 * @Author: 青柠
 * @Description:
 */

@Database(version = 1, exportSchema = false, entities = [WallpaperEntity::class])
abstract class WallpaperDatabase : RoomDatabase() {
    val wallpaperDao: WallpaperDao by lazy { createWallpaperDao() }
    abstract fun createWallpaperDao(): WallpaperDao
}