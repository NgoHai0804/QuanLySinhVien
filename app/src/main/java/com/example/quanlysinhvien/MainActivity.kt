package com.example.quanlysinhvien

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var db: StudentDatabase
    private lateinit var studentDao: StudentDao
    private lateinit var adapter: StudentAdapter
    private var students = mutableListOf<Student>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = StudentDatabase.getInstance(this)
        studentDao = db.studentDao()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = StudentAdapter(students, this::showPopupMenu)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        findViewById<View>(R.id.menu_add).setOnClickListener {
            startActivity(Intent(this, AddStudentActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadStudents()
    }

    private fun loadStudents() {
        students.clear()
        students.addAll(studentDao.getAll())
        adapter.notifyDataSetChanged()
    }

    private fun showPopupMenu(view: View, position: Int) {
        val student = students[position]
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_student, popup.menu)

        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_update -> {
                    val intent = Intent(this, UpdateStudentActivity::class.java)
                    intent.putExtra("student", student)
                    startActivity(intent)
                }
                R.id.menu_delete -> confirmDelete(student)
            }
            true
        }
        popup.show()
    }

    private fun confirmDelete(student: Student) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận")
            .setMessage("Bạn có chắc muốn xoá sinh viên này?")
            .setPositiveButton("Xoá") { _, _ ->
                studentDao.delete(student)
                loadStudents()
            }
            .setNegativeButton("Huỷ", null)
            .show()
    }
}
