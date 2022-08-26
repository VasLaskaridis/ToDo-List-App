package appbox.room.vasili.simpletodolist.models.subtask

import androidx.room.*
import kotlinx.coroutines.flow.Flow


@Dao
interface SubtaskDao {
    @Insert
    suspend fun insert(subtask: Subtask?)

    @Update
    suspend fun update(subtask: Subtask)

    @Delete
    suspend fun delete(subtask: Subtask?)

    @Query("SELECT * FROM subtask_table WHERE taskId =:taskID ORDER BY subtaskTitle")
    fun getAllSubtasksOfTask(taskID: Int): Flow<List<Subtask>>

    @Query("DELETE  FROM subtask_table WHERE taskId =:taskID")
    suspend fun deleteAllSubtasksOfTask(taskID: Int)
}
