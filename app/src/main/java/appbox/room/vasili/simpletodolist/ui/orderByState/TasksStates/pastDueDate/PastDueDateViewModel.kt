package appbox.room.vasili.simpletodolist.ui.orderByState.TasksStates.pastDueDate

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.models.task.TaskRepository


class PastDueDateViewModel(val taskRepository: TaskRepository) : ViewModel() {

    private lateinit var taskList: LiveData<List<Task>>

    fun getAllPastDueDateTasks(): LiveData<List<Task>> {
        taskList=taskRepository.getPastDueDateTasks().asLiveData()
        return taskList
    }
}

class PastDueDateViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PastDueDateViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PastDueDateViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}