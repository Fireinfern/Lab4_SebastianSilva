package com.comp.lab4_sebastiansilva.repo

import android.util.Log
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.models.Nurse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.UnknownHostException

class NurseRepo(private val database: AppDatabase) {
    suspend fun getNurses(): List<Nurse>? {
        return withContext(Dispatchers.IO) {
            try {
                database.nurseDao().getAll()
            }
            catch (ex: UnknownHostException) {
                return@withContext null
            }
            catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun validateNurse(id: Int, password: String): Nurse? {
        return withContext(Dispatchers.IO) {
            try {
                val nurses: List<Nurse> = database.nurseDao().getNurseWithIdAndPassword(id, password)
                nurses[0]
            }
            catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
                return@withContext null
            }
        }
    }

    suspend fun insertNurse(nurse: Nurse) {
        withContext(Dispatchers.IO){
            try {
                database.nurseDao().insertAll(nurse)
            }
            catch (ex: Exception) {
                Log.e("Repo", ex.message.toString())
            }
        }
    }
}