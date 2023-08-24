package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lib_base.room.entity.NoteEntity
import com.example.lib_base.room.manager.DatabaseManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate: 2023/8/24 23:35
 * @Author: 青柠
 * @Description:
 */
class NoteListViewModel : BaseViewModel() {

    var noteListData = MutableLiveData<List<NoteEntity>>()

    /**
     * 子线程获取数据库备忘录列表
     */
    fun getDataBase() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val noteList = DatabaseManager.noteDB.noteDao.getNoteList()
                noteListData.postValue(noteList)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

}