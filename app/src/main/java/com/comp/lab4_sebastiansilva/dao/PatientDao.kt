package com.comp.lab4_sebastiansilva.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.comp.lab4_sebastiansilva.models.Patient

@Dao
interface PatientDao {
    @Query("SELECT * FROM patient")
    fun getAll(): List<Patient>

    @Insert
    fun insertAll(vararg patient: Patient)

    @Delete
    fun delete(patient: Patient)
}