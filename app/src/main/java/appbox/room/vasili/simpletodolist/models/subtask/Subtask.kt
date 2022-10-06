package appbox.room.vasili.simpletodolist.models.subtask


import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "subtask_table")
class Subtask {

    @PrimaryKey(autoGenerate = true)
    var subtaskId = 0

    var taskId: Int

    var subtaskTitle: String?

    var subtaskCompletionIndicator: Boolean

    constructor(
        taskId: Int,
        subtaskTitle: String?,
        subtaskCompletionIndicator: Boolean,
    ) {
        this.taskId = taskId
        this.subtaskTitle = subtaskTitle
        this.subtaskCompletionIndicator = subtaskCompletionIndicator
    }

}
