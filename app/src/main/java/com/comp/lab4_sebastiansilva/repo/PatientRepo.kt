package com.comp.lab4_sebastiansilva.repo

import android.util.Log
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.models.Patient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PatientRepo(private val database: AppDatabase) {

    suspend fun getPatientsByNurseId(nurseId: Int): List<Patient>? {
        return withContext(Dispatchers.IO) {
            try {
                database.patientDao().getAllByNurse(nurseId)
            }
            catch (e : Exception) {
                Log.e("PatientRepo", e.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun getPatients(): List<Patient>? {
        return withContext(Dispatchers.IO) {
            try {
                database.patientDao().getAll()
            }
            catch (e: Exception) {
                Log.e("PatientRepo", e.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun update(patient: Patient) {
        withContext(Dispatchers.IO) {
            try {
                database.patientDao().update(patient)
            }
            catch (e: Exception) {
                Log.e("PatientRepo", e.message.toString())
            }
        }
    }

    suspend fun insertPatient(patient: Patient) {
        withContext(Dispatchers.IO) {
            try {
                database.patientDao().insertAll(patient)
            }
            catch (e: Exception) {
                Log.e("PatientRepo", e.message.toString())
            }
        }
    }

}