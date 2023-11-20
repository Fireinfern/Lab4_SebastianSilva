package com.comp.lab4_sebastiansilva.dao

import androidx.room.Dao
import androidx.room.Query
import com.comp.lab4_sebastiansilva.models.Test

@Dao
interface TestDao {
    @Query("SELECT * FROM test")
    fun getAll(): List<Test>

}