package com.example.quanlysinhvien

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class UpdateStudentActivity : AppCompatActivity() {
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        // Nhận đối tượng Student an toàn
        student = intent.getSerializableExtra("student") as? Student ?: run {
            finish()
            return
        }

        // Gán dữ liệu ban đầu lên form
        findViewById<EditText>(R.id.editName).setText(student.name)
        findViewById<EditText>(R.id.editMSSV).setText(student.mssv)
        findViewById<EditText>(R.id.editEmail).setText(student.email)
        findViewById<EditText>(R.id.editPhone).setText(student.phone)

        // Lưu lại khi bấm nút
        findViewById<Button>(R.id.btnSave).setOnClickListener {
            student.name = findViewById<EditText>(R.id.editName).text.toString()
            student.mssv = findViewById<EditText>(R.id.editMSSV).text.toString()
            student.email = findViewById<EditText>(R.id.editEmail).text.toString()
            student.phone = findViewById<EditText>(R.id.editPhone).text.toString()

            val resultIntent = Intent().putExtra("student", student)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
