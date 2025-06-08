package com.example.quanlysinhvien

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val mssv: String,
    val email: String,
    val phone: String
) : Serializable
