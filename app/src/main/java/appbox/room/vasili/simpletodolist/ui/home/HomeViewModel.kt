package appbox.room.vasili.simpletodolist.ui.home

import androidx.lifecycle.*
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.models.task.TaskRepository


class HomeViewModel(val taskRepository: TaskRepository) : ViewModel() {

    private lateinit var taskList: LiveData<List<Task>>

    fun getAllTasksForToday(): LiveData<List<Task>> {
        taskList=taskRepository.getAllTasksForToday().asLiveData()
        return taskList
    }

    fun getActiveTasks(): LiveData<List<Task>> {
        taskList=taskRepository.getActiveTasks().asLiveData()
        return taskList
    }

    fun getPastDueDateTasks(): LiveData<List<Task>> {
        taskList=taskRepository.getOnGoingTasks().asLiveData()
        return taskList
    }
}

class HomeViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}