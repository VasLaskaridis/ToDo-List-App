 package appbox.room.vasili.simpletodolist.ui.addEditTask

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.models.task.TaskRepository
import kotlinx.coroutines.launch
import java.util.*


class AddEditTaskViewModel(val taskRepository: TaskRepository) : ViewModel(), AdapterView.OnItemSelectedListener {


    private var taskTitle:String=""
    private var taskStartDate: Date?=null
    private var taskDueDate: Date?=null
    private  var taskNotes:String=""
    private var taskColor:String="red"
    private var taskStatus:String="Scheduled"

    fun setTaskTitle(title:String){taskTitle=title}
    fun getTaskTitle():String{return taskTitle}
    fun setTaskNotes(notes:String){taskNotes=notes}
    fun setTaskColor(color:String){taskColor=color}
    fun setTaskStatus(status:String){taskStatus=status}
    fun setTaskStartDate(date: Date){
        taskStartDate =date
    }

    fun setTaskDueDate(date: Date){
        taskDueDate = date
    }

     fun insert():Boolean{
         val task: Task = Task(taskTitle,taskStartDate,taskDueDate,taskNotes,taskColor,taskStatus)
         viewModelScope.launch {
             taskRepository.insert(task)
         }
         return true
    }

     fun update(taskToBeUpdated:Task):Boolean{
         val task: Task = Task(taskTitle,taskStartDate,taskDueDate,taskNotes,taskColor,taskStatus)
         task.taskId=taskToBeUpdated.taskId
         viewModelScope.launch {
             taskRepository.update(task)
         }
         return true
    }


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        setTaskStatus(parent!!.getItemAtPosition(position).toString())
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}

 class AddEditTaskViewModelFactory(private val taskRepository: TaskRepository) : ViewModelProvider.Factory {
     override fun <T : ViewModel> create(modelClass: Class<T>): T {
         if (modelClass.isAssignableFrom(AddEditTaskViewModel::class.java)) {
             @Suppress("UNCHECKED_CAST")
             return AddEditTaskViewModel(taskRepository) as T
         }
         throw IllegalArgumentException("Unknown ViewModel class")
     }
 }

