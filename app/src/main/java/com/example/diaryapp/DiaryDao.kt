package com.example.diaryapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DiaryDao {
    @Insert
    suspend fun insert(diary: Diary)

    @Query("SELECT * FROM diary_table ORDER BY date DESC")
    suspend fun getAll(): List<Diary>

}