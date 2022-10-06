package appbox.room.vasili.simpletodolist.ui.calendar


import androidx.lifecycle.*
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.models.task.TaskRepository
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel(val taskRepository: TaskRepository) : ViewModel() {

    lateinit var taskList: LiveData<List<Task>>
    private var date: MutableLiveData<String>

    init{
        val calendar= Calendar.getInstance()
        date=MutableLiveData()
        setDate(calendar.time)
        getTasksOfDate()
    }

    fun getTasksOfDate(): MutableLiveData<List<Task>> {
        taskList= Transformations.switchMap(
            date,
            { taskRepository.getTasksOfDate(date.value.toString()).asLiveData()}
        )
        return taskList as MutableLiveData
    }

    fun setDate(d:Date){
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate: String = sdf.format(d)
        date.value=formattedDate
    }
}

class CalendarViewModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}