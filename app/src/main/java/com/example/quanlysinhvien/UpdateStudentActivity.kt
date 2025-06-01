package com.example.quanlysinhvien

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class UpdateStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val db = StudentDatabase(this)

        val id = intent.getLongExtra("id", -1)
        val student = intent.getSerializableExtra("student") as? Student ?: return

        val editName = findViewById<EditText>(R.id.editName)
        val editMSSV = findViewById<EditText>(R.id.editMSSV)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPhone = findViewById<EditText>(R.id.editPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)

        editName.setText(student.name)
        editMSSV.setText(student.mssv)
        editEmail.setText(student.email)
        editPhone.setText(student.phone)

        btnSave.setOnClickListener {
            student.name = editName.text.toString()
            student.mssv = editMSSV.text.toString()
            student.email = editEmail.text.toString()
            student.phone = editPhone.text.toString()
            db.updateStudent(student, id)
            finish()
        }
    }
}
