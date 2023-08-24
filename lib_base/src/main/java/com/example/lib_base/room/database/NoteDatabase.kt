package com.example.lib_base.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lib_base.room.dao.NoteDao
import com.example.lib_base.room.entity.NoteEntity

/**
 * @CreateDate: 2022/3/15 3:29 下午
 * @Author: 青柠
 * @Description:
 */

@Database(version = 1, exportSchema = false, entities = [NoteEntity::class])
abstract class NoteDatabase : RoomDatabase() {
    val noteDao: NoteDao by lazy { createNoteDao() }
    abstract fun createNoteDao(): NoteDao
}