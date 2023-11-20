package com.comp.lab4_sebastiansilva

import androidx.room.Database
import androidx.room.RoomDatabase
import com.comp.lab4_sebastiansilva.dao.NurseDao
import com.comp.lab4_sebastiansilva.dao.PatientDao
import com.comp.lab4_sebastiansilva.dao.TestDao
import com.comp.lab4_sebastiansilva.models.Nurse
import com.comp.lab4_sebastiansilva.models.Patient
import com.comp.lab4_sebastiansilva.models.Test

@Database(entities = [Nurse::class, Patient::class, Test::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun nurseDao(): NurseDao

    abstract fun patientDao(): PatientDao

    abstract fun testDao(): TestDao
}