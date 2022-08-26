package appbox.room.vasili.simpletodolist.models.subtask

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class SubtaskRepository(val subtaskDao: SubtaskDao) {

    private lateinit var allSubtasks: Flow<List<Subtask>>

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(subtask: Subtask) {
        subtaskDao.insert(subtask)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(subtask: Subtask) {
        subtaskDao.update(subtask)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(subtask: Subtask) {
        subtaskDao.delete(subtask)
    }

    fun getAllSubtasksOfTask(taskId: Int): Flow<List<Subtask>> {
        allSubtasks = subtaskDao.getAllSubtasksOfTask(taskId)
        return allSubtasks
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllSubtasksOfTask(taskId: Int){
        subtaskDao.deleteAllSubtasksOfTask(taskId)
    }

}