package appbox.room.vasili.simpletodolist.models.subtask

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

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
