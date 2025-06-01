package com.example.quanlysinhvien

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StudentDatabase(context: Context) : SQLiteOpenHelper(context, "students.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE students (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                mssv TEXT,
                email TEXT,
                phone TEXT
            )
        """.trimIndent())
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS students")
        onCreate(db)
    }

    fun insertStudent(student: Student): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.name)
            put("mssv", student.mssv)
            put("email", student.email)
            put("phone", student.phone)
        }
        return db.insert("students", null, values)
    }

    fun updateStudent(student: Student, id: Long) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", student.name)
            put("mssv", student.mssv)
            put("email", student.email)
            put("phone", student.phone)
        }
        db.update("students", values, "id=?", arrayOf(id.toString()))
    }

    fun deleteStudent(id: Long) {
        writableDatabase.delete("students", "id=?", arrayOf(id.toString()))
    }

    fun getAllStudents(): MutableList<StudentWithId> {
        val list = mutableListOf<StudentWithId>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM students", null)
        while (cursor.moveToNext()) {
            list.add(
                StudentWithId(
                    id = cursor.getLong(0),
                    student = Student(
                        name = cursor.getString(1),
                        mssv = cursor.getString(2),
                        email = cursor.getString(3),
                        phone = cursor.getString(4)
                    )
                )
            )
        }
        cursor.close()
        return list
    }
}