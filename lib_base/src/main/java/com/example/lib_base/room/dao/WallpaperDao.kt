package com.example.lib_base.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lib_base.room.entity.WallpaperEntity


/**
 * @CreateDate: 2022/3/15 3:28 下午
 * @Author: 青柠
 * @Description:
 */

@Dao
interface WallpaperDao {
    @Insert
    fun save(vararg logs: WallpaperEntity): List<Long>

    @Update
    fun update(vararg logs: WallpaperEntity)

    @Query("select time from wallpaper order by time asc limit 1")
    fun getFirstLogTime(): Long

    @Query("select time from wallpaper order by time desc limit 1")
    fun getLastLogTime(): Long


    @Query("select * from wallpaper where time>=:startTime and time <=:endTime")
    fun getLogByFilter(startTime: Long, endTime: Long): List<WallpaperEntity>

    fun getWallpaperList(startTime: Long = 0, endTime: Long = 0): List<WallpaperEntity> {
        val start = if (startTime == 0L) {
            getFirstLogTime()
        } else {
            startTime
        }
        val end = if (endTime == 0L) {
            getLastLogTime()
        } else {
            endTime
        }
        return getLogByFilter(start, end)
    }
    
}