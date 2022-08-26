package appbox.room.vasili.simpletodolist.models.task

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
interface TaskDao {
    @Insert
     suspend fun insert(task:Task)

    @Update
    suspend fun update(task:Task)

    @Delete
    suspend fun delete(task:Task)

    @Query("SELECT * FROM task_table WHERE strftime('%Y-%m-%d', 'now')=strftime('%Y-%m-%d', taskStartDate/1000 ,'unixepoch','localtime')  ORDER BY taskTitle")
    fun allTasksForToday(): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE strftime('%Y-%m-%d', taskStartDate/1000 ,'unixepoch','localtime')=strftime('%Y-%m-%d', :date) ORDER BY taskTitle")
    fun getTasksOfDate(date: String): Flow<List<Task>>

    @Query(
        "SELECT * FROM task_table WHERE taskStatusIndicator!='Scheduled' AND taskStatusIndicator!='Completed' ORDER BY taskDueDate")
    fun getActiveTasks(): Flow<List<Task>>

    @Query(
        "SELECT * FROM task_table WHERE strftime('%Y-%m-%d', 'now')>strftime('%Y-%m-%d', taskDueDate/1000 ,'unixepoch')" +
                 "and taskStatusIndicator!='Completed' ORDER BY taskDueDate")
    fun getPastDueDateTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE taskStatusIndicator='Pending' ORDER BY taskDueDate")
    fun getPendingTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE taskStatusIndicator='Scheduled' ORDER BY taskDueDate")
    fun getScheduledTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE taskStatusIndicator='Ongoing' ORDER BY taskDueDate")
    fun getOnGoingTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task_table WHERE taskStatusIndicator='Completed' ORDER BY taskDueDate")
    fun getCompletedTasks(): Flow<List<Task>>
}