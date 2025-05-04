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

    // Khởi tạo danh sách mẫu ban đầu
    private var studentList = mutableListOf(
        Student("Nguyễn Văn A", "20210001", "vana@example.com", "0123456789"),
        Student("Trần Thị B", "20210002", "thib@example.com", "0987654321")
    )

    private lateinit var adapter: StudentAdapter

    private val ADD_STUDENT_REQUEST = 1
    private val UPDATE_STUDENT_REQUEST = 2
    private var selectedStudentIndex = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Gán adapter và thiết lập RecyclerView
        adapter = StudentAdapter(studentList, this::showPopupMenu)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    // Option menu: thêm sinh viên
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivityForResult(intent, ADD_STUDENT_REQUEST)
        }
        return super.onOptionsItemSelected(item)
    }

    // Hiển thị popup menu cho từng sinh viên
    private fun showPopupMenu(view: View, position: Int) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.menu_student, popup.menu)

        popup.setOnMenuItemClickListener { item ->
            val student = studentList[position]
            selectedStudentIndex = position
            when (item.itemId) {
                R.id.menu_update -> {
                    val intent = Intent(this, UpdateStudentActivity::class.java).apply {
                        putExtra("student", student)
                    }
                    startActivityForResult(intent, UPDATE_STUDENT_REQUEST)
                }
                R.id.menu_delete -> showConfirmDeleteDialog(position)
                R.id.menu_call -> {
                    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${student.phone}"))
                    startActivity(intent)
                }
                R.id.menu_email -> {
                    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:${student.email}"))
                    startActivity(Intent.createChooser(intent, "Send Email"))
                }
            }
            true
        }

        popup.show()
    }

    // Hộp thoại xác nhận xóa sinh viên
    private fun showConfirmDeleteDialog(index: Int) {
        AlertDialog.Builder(this)
            .setTitle("Xác nhận")
            .setMessage("Bạn có chắc muốn xóa sinh viên này?")
            .setPositiveButton("Xóa") { _, _ ->
                studentList.removeAt(index)
                adapter.notifyItemRemoved(index)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    // Nhận dữ liệu từ AddStudentActivity / UpdateStudentActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && data != null) {
            val student = data.getSerializableExtra("student") as? Student ?: return
            when (requestCode) {
                ADD_STUDENT_REQUEST -> {
                    studentList.add(student)
                    adapter.notifyItemInserted(studentList.size - 1)
                }
                UPDATE_STUDENT_REQUEST -> {
                    if (selectedStudentIndex >= 0) {
                        studentList[selectedStudentIndex] = student
                        adapter.notifyItemChanged(selectedStudentIndex)
                    }
                }
            }
        }
    }

}
