package com.jeizard.mbanking.data.room.bases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface BaseDao<T> {
    @Insert
    fun insert(entity: T)

    @Update
    fun update(entity: T)

    @Delete
    fun delete(entity: T)

    @Query("")
    fun deleteAll()

    @Query("")
    fun getAll(): List<T>
}
