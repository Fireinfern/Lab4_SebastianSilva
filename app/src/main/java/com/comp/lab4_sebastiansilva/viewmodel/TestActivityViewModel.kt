package com.comp.lab4_sebastiansilva.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.comp.lab4_sebastiansilva.AppDatabase
import com.comp.lab4_sebastiansilva.models.Nurse
import com.comp.lab4_sebastiansilva.models.Patient
import com.comp.lab4_sebastiansilva.models.Test
import com.comp.lab4_sebastiansilva.repo.NurseRepo
import com.comp.lab4_sebastiansilva.repo.PatientRepo
import com.comp.lab4_sebastiansilva.repo.TestRepo
import kotlinx.coroutines.launch

class TestActivityViewModel: ViewModel()  {
    private lateinit var database: AppDatabase
    private lateinit var testRepo: TestRepo
    private lateinit var patientRepo: PatientRepo
    private lateinit var nurseRepo: NurseRepo
    private val _nurseList = MutableLiveData<List<Nurse>>()
    private val _patientsList = MutableLiveData<List<Patient>>()
    private val _testList = MutableLiveData<List<Test>>()
    val nurseList = _nurseList
    val patientsList = _patientsList
    fun insertTest(test: Test){
        viewModelScope.launch {
            testRepo.insertTest(test)
            var tests: MutableList<Test>? = _testList.value?.toMutableList()
            if (tests.isNullOrEmpty()) {
                tests = mutableListOf(test)
            }
            else {
                tests += test
            }
            _testList.postValue(tests.toList())
        }
    }
    fun getNurses(){
        viewModelScope.launch {
            val nurses = nurseRepo.getNurses()
            val arrayList: ArrayList<Nurse> = ArrayList()
            if (nurses == null) {
                return@launch
            }
            arrayList.addAll(nurses)
            _nurseList.postValue(arrayList)
        }
    }
    fun getPatient(){
        viewModelScope.launch {
            val patient = patientRepo.getPatients()
            val arrayList: ArrayList<Patient> = ArrayList()
            if (patient == null) {
                return@launch
            }
            arrayList.addAll(patient)
            _patientsList.postValue(arrayList)
        }
    }
    fun getTestByNurseId(nurseId: Int) {
        viewModelScope.launch {
            val tests = testRepo.getTestByNurseId(nurseId)
            if (tests != null) {
                _testList.postValue(tests!!)
            }
        }
    }
    fun getTestByPatientId(patientId: Int) {
        viewModelScope.launch {
            val tests = testRepo.getTestByNurseId(patientId)
            if (tests != null) {
                _testList.postValue(tests!!)
            }
        }
    }
    fun initDatabase(db: AppDatabase, nurseId: Int) {
        database = db
        nurseRepo= NurseRepo(db)
        patientRepo= PatientRepo(db)
        testRepo = TestRepo(db)
    }
}