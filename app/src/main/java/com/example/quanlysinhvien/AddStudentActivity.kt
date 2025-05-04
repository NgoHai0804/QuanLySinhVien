package com.example.quanlysinhvien

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val editName = findViewById<EditText>(R.id.editName)
        val editMSSV = findViewById<EditText>(R.id.editMSSV)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPhone = findViewById<EditText>(R.id.editPhone)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val name = editName.text.toString().trim()
            val mssv = editMSSV.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val phone = editPhone.text.toString().trim()

            // Kiểm tra rỗng
            if (name.isEmpty() || mssv.isEmpty()) {
                editName.error = "Không được để trống"
                editMSSV.error = "Không được để trống"
                return@setOnClickListener
            }

            val student = Student(name, mssv, email, phone)
            val intent = Intent().putExtra("student", student)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}



