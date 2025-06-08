package com.example.quanlysinhvien

import androidx.room.*

@Dao
interface StudentDao {
    @Insert
    fun insert(student: Student): Long

    @Update
    fun update(student: Student)

    @Delete
    fun delete(student: Student)

    @Query("SELECT * FROM students")
    fun getAll(): List<Student>

    @Query("SELECT * FROM students WHERE id = :id")
    fun getById(id: Long): Student?
}