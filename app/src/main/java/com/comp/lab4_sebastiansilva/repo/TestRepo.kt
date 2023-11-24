package com.comp.lab4_sebastiansilva.repo

import android.util.Log
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.models.Test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TestRepo (private val database: AppDatabase){

    suspend fun getTestByNurseId(nurseId: Int): List<Test>? {
        return withContext(Dispatchers.IO) {
            try {
                database.testDao().getAllByNurseId(nurseId)
            }
            catch (e : Exception) {
                Log.e("PatientRepo", e.message.toString())
                return@withContext null
            }
        }
    }
    suspend fun getTestByPatientId(patientId: Int): List<Test>? {
        return withContext(Dispatchers.IO) {
            try {
                database.testDao().getAllByPatient(patientId)
            }
            catch (e : Exception) {
                Log.e("PatientRepo", e.message.toString())
                return@withContext null
            }
        }
    }
    /*suspend fun getNurses(): List<Nurse>? {
        return withContext(Dispatchers.IO) {
            try {
                database.nurseDao().getAll()
            }
            catch (e: Exception) {
                Log.e("NurseRepo", e.message.toString())
                return@withContext null
            }
        }
    }
    suspend fun getPatient(): List<Nurse>? {
        return withContext(Dispatchers.IO) {
            try {
                database.nurseDao().getAll()
            }
            catch (e: Exception) {
                Log.e("NurseRepo", e.message.toString())
                return@withContext null
            }
        }
    }*/
    suspend fun getTest(): List<Test>? {
        return withContext(Dispatchers.IO) {
            try {
                database.testDao().getAll()
            }
            catch (e: Exception) {
                Log.e("TestRepo", e.message.toString())
                return@withContext null
            }
        }
    }
    suspend fun insertTest(test: Test) {
        withContext(Dispatchers.IO) {
            try {
                database.testDao().insertAll(test)
            }
            catch (e: Exception) {
                Log.e("TestRepo", e.message.toString())
            }
        }
    }
}