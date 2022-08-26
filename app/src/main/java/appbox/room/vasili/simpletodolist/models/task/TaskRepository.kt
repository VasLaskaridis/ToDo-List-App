 package appbox.room.vasili.simpletodolist.models.task

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow


 class TaskRepository(private val taskDao: TaskDao) {

    private lateinit var taskList: Flow<List<Task>>

     @Suppress("RedundantSuspendModifier")
     @WorkerThread
     suspend fun insert(task: Task) {
         taskDao.insert(task)
     }

     @Suppress("RedundantSuspendModifier")
     @WorkerThread
     suspend fun update(task: Task){
         taskDao.update(task)
     }

     @Suppress("RedundantSuspendModifier")
     @WorkerThread
     suspend fun delete(task: Task){
         taskDao.delete(task)
     }

     fun getAllTasksForToday(): Flow<List<Task>> {
         taskList = taskDao.allTasksForToday()
         return taskList
     }

     fun getActiveTasks():Flow<List<Task>> {
         taskList = taskDao.getActiveTasks()
         return taskList
     }

     fun getPastDueDateTasks(): Flow<List<Task>> {
         taskList = taskDao.getPastDueDateTasks()
         return taskList
     }

     fun getPendingTasks(): Flow<List<Task>> {
         taskList = taskDao.getPendingTasks()
         return taskList
     }

     fun getScheduledTasks(): Flow<List<Task>> {
         taskList = taskDao.getScheduledTasks()
         return taskList
     }

     fun getOnGoingTasks(): Flow<List<Task>> {
         taskList = taskDao.getOnGoingTasks()
         return taskList
     }

     fun getTasksOfDate(date: String): Flow<List<Task>> {
         taskList = taskDao.getTasksOfDate(date)
         return taskList
     }

     fun getCompletedTasks(): Flow<List<Task>> {
         taskList = taskDao.getCompletedTasks()
         return taskList
     }
}