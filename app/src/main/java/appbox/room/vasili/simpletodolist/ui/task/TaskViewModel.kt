 package appbox.room.vasili.simpletodolist.ui.task

import android.util.Log
import androidx.lifecycle.*
import appbox.room.vasili.simpletodolist.models.subtask.Subtask
import appbox.room.vasili.simpletodolist.models.subtask.SubtaskRepository
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.models.task.TaskRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext

 class TaskViewModel(val taskRepository: TaskRepository, val subtaskRepository: SubtaskRepository) : ViewModel() {


    private lateinit var subtaskList: LiveData<List<Subtask>>


    fun getAllSubtasksOfTask(taskId:Int): LiveData<List<Subtask>>{
        subtaskList = subtaskRepository.getAllSubtasksOfTask(taskId).asLiveData()
        return subtaskList
    }

     fun insert(taskId:Int, title:String):Boolean{
         val subtask=Subtask(taskId,title,false)
         viewModelScope.launch {
            subtaskRepository.insert(subtask)
         }
        return true
    }
     fun update(subtask:Subtask, isChecked:Boolean):Boolean{
        val updatedSubtask=Subtask(subtask.taskId,subtask.subtaskTitle,isChecked)
        updatedSubtask.subtaskId=subtask.subtaskId
        viewModelScope.launch {
            subtaskRepository.update(updatedSubtask)
        }
        return true
    }

     fun delete(subtask:Subtask):Boolean{
         viewModelScope.launch {
            subtaskRepository.delete(subtask)
         }
        return true
    }

     fun delete(taskToBeDeleted: Task):Boolean{
        viewModelScope.launch {
            subtaskRepository.deleteAllSubtasksOfTask(taskToBeDeleted.taskId)
            taskRepository.delete(taskToBeDeleted)
        }
        return true
    }

    class TaskViewModelFactory(private val taskRepository: TaskRepository,private val subtaskRepository: SubtaskRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TaskViewModel(taskRepository,subtaskRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}
