package com.comp.lab4_sebastiansilva.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.comp.lab4_sebastiansilva.models.Test

@Dao
interface TestDao {
    @Query("SELECT * FROM test")
    fun getAll(): List<Test>
    @Insert
    fun insertAll(vararg test: Test)

    @Delete
    fun delete(test: Test)

}