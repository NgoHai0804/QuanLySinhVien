package com.example.quanlysinhvien

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class UpdateStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val db = StudentDatabase.getInstance(this)
        val dao = db.studentDao()

        val student = intent.getSerializableExtra("student") as? Student ?: return

        val name = findViewById<EditText>(R.id.editName)
        val mssv = findViewById<EditText>(R.id.editMSSV)
        val email = findViewById<EditText>(R.id.editEmail)
        val phone = findViewById<EditText>(R.id.editPhone)
        val saveBtn = findViewById<Button>(R.id.btnSave)

        name.setText(student.name)
        mssv.setText(student.mssv)
        email.setText(student.email)
        phone.setText(student.phone)

        saveBtn.setOnClickListener {
            val updated = student.copy(
                name = name.text.toString(),
                mssv = mssv.text.toString(),
                email = email.text.toString(),
                phone = phone.text.toString()
            )
            dao.update(updated)
            finish()
        }
    }
}