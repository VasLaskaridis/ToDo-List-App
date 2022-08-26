package appbox.room.vasili.simpletodolist.ui.orderByState.TasksStates.ongoing

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.models.task.TaskRepository

class OnGoingViewModel(val taskRepository: TaskRepository) : ViewModel() {

    private lateinit var taskList: LiveData<List<Task>>

    fun getOnGoingTasks(): LiveData<List<Task>> {
        taskList=taskRepository.getOnGoingTasks().asLiveData()
        return taskList
    }

}

class OnGoingViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnGoingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OnGoingViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}