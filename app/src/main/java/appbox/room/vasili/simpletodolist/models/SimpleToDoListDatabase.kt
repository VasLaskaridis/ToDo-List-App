package appbox.room.vasili.simpletodolist.models

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import appbox.room.vasili.simpletodolist.models.subtask.Subtask
import appbox.room.vasili.simpletodolist.models.subtask.SubtaskDao
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.models.task.TaskDao

@Database(entities = [Task::class, Subtask::class], version = 1, exportSchema = true)
@TypeConverters(
    DateConverter::class
)
abstract class SimpleToDoListDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun subTaskDao(): SubtaskDao

    companion object {
        private var instance: SimpleToDoListDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SimpleToDoListDatabase? {
            if (instance == null) {

                instance = Room.databaseBuilder(
                    context.applicationContext,
                    SimpleToDoListDatabase::class.java, "simple_todolist_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance
        }
    }
}
