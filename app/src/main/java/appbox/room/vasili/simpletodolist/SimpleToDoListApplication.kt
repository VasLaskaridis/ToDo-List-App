package appbox.room.vasili.simpletodolist

import android.app.Application
import appbox.room.vasili.simpletodolist.models.SimpleToDoListDatabase
import appbox.room.vasili.simpletodolist.models.subtask.SubtaskRepository
import appbox.room.vasili.simpletodolist.models.task.TaskRepository

class SimpleToDoListApplication:Application() {

    val database by lazy { SimpleToDoListDatabase.getInstance(this) }
    val taskRepository by lazy { TaskRepository(database!!.taskDao()) }
    val subtaskRepository by lazy{SubtaskRepository(database!!.subTaskDao())}
}