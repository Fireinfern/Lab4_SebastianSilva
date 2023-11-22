package com.comp.lab4_sebastiansilva.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.models.Patient
import com.comp.lab4_sebastiansilva.repo.PatientRepo
import kotlinx.coroutines.launch

class PatientActivityViewModel: ViewModel() {
    private lateinit var database: AppDatabase

    private lateinit var patientRepo: PatientRepo

    private var nurseId: Int = 0

    private val _patientsList = MutableLiveData<List<Patient>>()

    val patientsList = _patientsList

    fun insertData(patient: Patient){
        viewModelScope.launch {
            patientRepo.insertPatient(patient)
            var patients: MutableList<Patient>? = _patientsList.value?.toMutableList()
            if (patients.isNullOrEmpty()) {
                patients = mutableListOf(patient)
            }
            else {
                patients += patient
            }
            _patientsList.postValue(patients.toList())
        }
    }

    fun getPatientsByNurseId(nurseId: Int) {
        viewModelScope.launch {
            val patients = patientRepo.getPatientsByNurseId(nurseId)
            if (patients != null) {
                _patientsList.postValue(patients!!)
            }
        }
    }

    fun initDatabase(db: AppDatabase, newNurseId: Int) {
        database = db
        patientRepo = PatientRepo(db)
        nurseId = newNurseId
    }
}