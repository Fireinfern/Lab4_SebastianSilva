package com.comp.lab4_sebastiansilva.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.comp.lab4_sebastiansilva.models.Nurse

@Dao
interface NurseDao {
    @Query("SELECT * FROM nurse")
    fun getAll(): List<Nurse>

    @Query("SELECT * FROM nurse WHERE nurseId = :id")
    fun getNurseById(id: Int): List<Nurse>

    @Query("SELECT * FROM nurse WHERE nurseId = :id AND password = :password")
    fun getNurseWithIdAndPassword(id: Int, password: String): List<Nurse>

    @Insert
    fun insertAll(vararg nurses: Nurse)

    @Delete
    fun delete(nurse: Nurse)
}