package appbox.room.vasili.simpletodolist.ui.orderByState.TasksStates.scheduled

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.models.task.TaskRepository


class ScheduledViewModel(val taskRepository: TaskRepository) : ViewModel() {

    private lateinit var taskList: LiveData<List<Task>>

    fun getAllScheduledTasks(): LiveData<List<Task>> {
        taskList=taskRepository.getScheduledTasks().asLiveData()
        return taskList
    }
}

class ScheduledViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduledViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduledViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}