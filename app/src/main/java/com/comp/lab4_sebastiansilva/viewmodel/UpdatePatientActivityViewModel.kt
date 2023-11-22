package com.comp.lab4_sebastiansilva.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.models.Patient
import com.comp.lab4_sebastiansilva.repo.PatientRepo
import kotlinx.coroutines.launch

class UpdatePatientActivityViewModel : ViewModel() {

    private lateinit var database: AppDatabase

    private lateinit var patientRepo: PatientRepo

    private val _patientsList = MutableLiveData<ArrayList<Patient>>()

    val patientsList = _patientsList

    fun getPatients() {
        viewModelScope.launch {
            val patients = patientRepo.getPatients()
            val arrayList: ArrayList<Patient> = ArrayList()
            if (patients == null) {
                return@launch
            }
            arrayList.addAll(patients)
            _patientsList.postValue(arrayList)
        }
    }

    fun update(patient: Patient) {
        viewModelScope.launch {
            patientRepo.update(patient)
        }
    }

    fun initDatabase(db: AppDatabase) {
        database = db
        patientRepo = PatientRepo(database)
    }

}