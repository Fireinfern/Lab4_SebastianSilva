package com.comp.lab4_sebastiansilva.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.models.Nurse
import com.comp.lab4_sebastiansilva.repo.NurseRepo
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {

    private lateinit var database: AppDatabase

    private lateinit var nurseRepo: NurseRepo

    private val _validatedNurse = MutableLiveData<Nurse>()

    val validatedNurse: LiveData<Nurse> = _validatedNurse

    fun insertData(nurse: Nurse) {
        viewModelScope.launch {
            nurseRepo.insertNurse(nurse)
        }
    }

    fun validate(id: Int, password: String) {
        viewModelScope.launch {
            val nurse = nurseRepo.validateNurse(id, password)
            if (nurse != null) {
                _validatedNurse.postValue(nurse!!)
            }
        }
    }

    fun initDatabase(appDatabase: AppDatabase) {
        database = appDatabase
        nurseRepo = NurseRepo(database)
    }

}