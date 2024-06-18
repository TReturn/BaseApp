package com.example.lib_base.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.lib_base.room.entity.NoteEntity


/**
 * @CreateDate: 2022/3/15 3:28 下午
 * @Author: 青柠
 * @Description: 数据库增删改查语句
 */

@Dao
interface NoteDao {
    @Insert
    fun save(vararg logs: NoteEntity): List<Long>

    @Delete
    fun delete(vararg logs: NoteEntity): Int

    @Update
    fun update(vararg logs: NoteEntity)

    @Query("select time from note order by time asc limit 1")
    fun getFirstLogTime(): Long

    @Query("select time from note order by time desc limit 1")
    fun getLastLogTime(): Long

    @Query("select * from note where time>=:startTime and time <=:endTime")
    fun getLogByFilter(startTime: Long, endTime: Long): List<NoteEntity>

    fun getNoteList(startTime: Long = 0, endTime: Long = 0): List<NoteEntity> {
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

    @Query("select * from note where id = :noteID")
    fun getDataByID(noteID: Int): NoteEntity

    @Query("DELETE FROM note")
    fun deleteAll(): Int

}