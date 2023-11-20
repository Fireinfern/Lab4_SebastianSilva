package com.comp.lab4_sebastiansilva.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlin.reflect.KClass

@Entity
data class Patient(
    @PrimaryKey val patientId: Int,
    @ColumnInfo(name = "firstname") val firstName: String,
    @ColumnInfo(name = "lastname") val lastName: String,
    @ColumnInfo(name = "department") val department: String?
)
