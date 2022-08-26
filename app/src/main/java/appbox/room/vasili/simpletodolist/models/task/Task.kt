package appbox.room.vasili.simpletodolist.models.task

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "task_table")
class Task : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var taskId = 0

    var taskTitle: String?
        private set

    var taskStartDate: Date?
        private set

    var taskDueDate: Date?
        private set

    var taskNotes: String?
        private set

    var taskColor: String?
        private set

    var taskStatusIndicator: String?

    constructor(
        taskTitle: String?,
        taskStartDate:Date?,
        taskDueDate: Date?,
        taskNotes: String?,
        taskColor: String?,
        taskStatusIndicator: String
    ) {
        this.taskTitle = taskTitle
        this.taskStartDate=taskStartDate
        this.taskDueDate = taskDueDate
        this.taskNotes = taskNotes
        this.taskColor = taskColor
        this.taskStatusIndicator=taskStatusIndicator
    }

    protected constructor(`in`: Parcel) {
        taskId = `in`.readInt()
        taskTitle = `in`.readString()
        taskStartDate = `in`.readSerializable() as Date?
        taskDueDate = `in`.readSerializable() as Date?
        taskNotes = `in`.readString()
        taskColor = `in`.readString()
        taskStatusIndicator=`in`.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(taskId)
        dest.writeString(taskTitle)
        dest.writeSerializable(taskStartDate)
        dest.writeSerializable(taskDueDate)
        dest.writeString(taskNotes)
        dest.writeString(taskColor)
        dest.writeString(taskStatusIndicator)
    }

    companion object CREATOR : Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }


}