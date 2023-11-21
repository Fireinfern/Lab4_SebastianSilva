package com.comp.lab4_sebastiansilva.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlin.reflect.KClass

@Entity(foreignKeys = [
    ForeignKey(entity = Nurse::class, parentColumns = ["nurseId"], childColumns = ["nurseId"])
])
data class Patient(
    @PrimaryKey val patientId: Int,
    @ColumnInfo(name = "firstname") val firstName: String,
    @ColumnInfo(name = "lastname") val lastName: String,
    @ColumnInfo(name = "department") val department: String?,
    @ColumnInfo(name = "nurseId") val nurseId: String,
    @ColumnInfo(name = "room") val room: Int
)
