package com.example.lib_main.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.lib_base.room.entity.NoteEntity
import com.example.lib_base.room.manager.NoteManager
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel

/**
 * @CreateDate: 2023/8/24 23:35
 * @Author: 青柠
 * @Description:
 */
class NoteDetailViewModel : BaseViewModel() {

    //是否显示删除按钮
    val isShowDelete = MutableLiveData(false)

    val isShowSave = MutableLiveData(true)

    val noteID = MutableLiveData(-1)
    val title = MutableLiveData("")
    val content = MutableLiveData("")

    fun saveDataBase() {
        if (title.value.isNullOrEmpty() && content.value.isNullOrEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val noteEntity = NoteEntity(title.value.toString(), content.value.toString())
                NoteManager.noteDB.noteDao.save(noteEntity)
            } catch (exception: Exception) {
                exception.printStackTrace()
                Logger.d(exception.printStackTrace())
            }
        }
    }

    fun updateDataBase() {
        if (noteID.value == -1) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val noteList = NoteManager.noteDB.noteDao.getDataByID(noteID.value!!)
                noteList.title = title.value.toString()
                noteList.content = content.value.toString()
                NoteManager.noteDB.noteDao.update(noteList)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

    fun deleteDataBase(){
        if (noteID.value == -1) return

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val noteList = NoteManager.noteDB.noteDao.getDataByID(noteID.value!!)
                NoteManager.noteDB.noteDao.delete(noteList)
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
    }

}