package com.comp.lab4_sebastiansilva.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.comp.lab4_sebastiansilva.models.Nurse

@Dao
interface NurseDao {
    @Query("SELECT * FROM nurse")
    fun getAll(): List<Nurse>

    @Insert
    fun insertAll(vararg nurses: Nurse)

    @Delete
    fun delete(nurse: Nurse)
}