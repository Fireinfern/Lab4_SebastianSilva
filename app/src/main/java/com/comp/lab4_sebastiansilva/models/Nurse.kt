package com.comp.lab4_sebastiansilva.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Nurse(
    @PrimaryKey val nurseId: Int,
    @ColumnInfo(name = "firstname") val firstName: String,
    @ColumnInfo(name = "lastname") val lastName: String,
    @ColumnInfo(name = "department") val department: String,
    @ColumnInfo(name = "password") val password: String?
)
