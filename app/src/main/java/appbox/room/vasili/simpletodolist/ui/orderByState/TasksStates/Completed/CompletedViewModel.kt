package appbox.room.vasili.simpletodolist.ui.orderByState.TasksStates.Completed

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.models.task.TaskRepository


class CompletedViewModel(val taskRepository: TaskRepository) : ViewModel() {

    private lateinit var taskList: LiveData<List<Task>>

    fun getCompletedTasks(): LiveData<List<Task>> {
        taskList=taskRepository.getCompletedTasks().asLiveData()
        return taskList
    }
}

class CompletedViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CompletedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CompletedViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}