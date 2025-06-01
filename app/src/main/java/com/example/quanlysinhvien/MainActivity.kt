package com.example.quanlysinhvien

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: StudentAdapter
    private lateinit var db: StudentDatabase
    private var studentList = mutableListOf<StudentWithId>()
    private var selectedId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = StudentDatabase(this)
        studentList = db.getAllStudents()

        adapter = StudentAdapter(studentList, this::showPopupMenu)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showPopupMenu(view: View, position: Int) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_student, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            val studentWithId = studentList[position]
            selectedId = studentWithId.id
            when (item.itemId) {
                R.id.menu_update -> {
                    val intent = Intent(this, UpdateStudentActivity::class.java)
                    intent.putExtra("id", selectedId)
                    intent.putExtra("student", studentWithId.student)
                    startActivity(intent)
                }
                R.id.menu_delete -> showConfirmDeleteDialog(selectedId)
                R.id.menu_call -> startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${studentWithId.student.phone}")))
                R.id.menu_email -> startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${studentWithId.student.email}")))
            }
            true
        }
        popup.show()
    }

    private fun showConfirmDeleteDialog(id: Long) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận")
            .setMessage("Bạn có chắc muốn xóa sinh viên này?")
            .setPositiveButton("Xóa") { _, _ ->
                db.deleteStudent(id)
                reloadData()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        reloadData()
    }

    private fun reloadData() {
        studentList.clear()
        studentList.addAll(db.getAllStudents())
        adapter.notifyDataSetChanged()
    }
}