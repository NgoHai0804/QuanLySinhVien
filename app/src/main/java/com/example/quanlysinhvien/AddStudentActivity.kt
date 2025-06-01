package com.example.quanlysinhvien

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val db = StudentDatabase(this)

        val editName = findViewById<EditText>(R.id.editName)
        val editMSSV = findViewById<EditText>(R.id.editMSSV)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPhone = findViewById<EditText>(R.id.editPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val student = Student(
                name = editName.text.toString(),
                mssv = editMSSV.text.toString(),
                email = editEmail.text.toString(),
                phone = editPhone.text.toString()
            )
            db.insertStudent(student)
            finish()
        }
    }
}
