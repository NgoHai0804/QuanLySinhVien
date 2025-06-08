import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quanlysinhvien.Student
import com.example.quanlysinhvien.StudentDao

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        private var INSTANCE: StudentDatabase? = null
        fun getInstance(context: Context): StudentDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "students.db"
                ).allowMainThreadQueries().build()
            }
            return INSTANCE!!
        }
    }
}
