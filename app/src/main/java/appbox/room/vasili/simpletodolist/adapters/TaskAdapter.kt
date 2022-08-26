package appbox.room.vasili.simpletodo

import androidx.core.content.ContextCompat
import java.text.SimpleDateFormat
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import appbox.room.vasili.simpletodolist.R
import appbox.room.vasili.simpletodolist.models.task.Task
import java.util.*
import kotlin.collections.ArrayList


class TaskAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var taskClickListener: onTaskClickListener? = null

    var taskList: List<Task> = ArrayList()

    lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.getContext())
        val itemView = inflater.inflate(R.layout.layout_task, parent, false)
        val viewHolder = TaskHolder(itemView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val taskHolder: TaskHolder = holder as TaskHolder
        val currentTask: Task = taskList.get(position)

        taskHolder.textviewTaskTitle.text=currentTask.taskTitle

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        if(currentTask.taskDueDate!=null) {
            val formattedDate: String = sdf.format(currentTask.taskDueDate)
            taskHolder.textviewTaskDueDate.setText("$formattedDate")
        }else{
            taskHolder.textviewTaskDueDate.setText("No due date")
        }
        if(currentTask.taskNotes.equals("")){
            taskHolder.textviewTaskNotes.text = "No notes"
        }
        else {
            taskHolder.textviewTaskNotes.text = currentTask.taskNotes
        }

        taskHolder.textviewTaskStatus.text=currentTask.taskStatusIndicator

        if (currentTask.taskColor.equals("red")) {
                taskHolder.taskLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.palette_red))

        } else if (currentTask.taskColor.equals("blue")) {
                taskHolder.taskLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.palette_blue))

        } else if (currentTask.taskColor.equals("green")) {
                taskHolder.taskLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.palette_green))

        } else if (currentTask.taskColor.equals("yellow")) {
                taskHolder.taskLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.palette_yellow))

        } else if (currentTask.taskColor.equals("orange")) {
                taskHolder.taskLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.palette_orange))
        }

        val date=Calendar.getInstance().time
        taskHolder.imageviewTaskWarning.visibility=View.GONE
        currentTask.taskDueDate?.let {
            if(date>currentTask.taskDueDate){taskHolder.imageviewTaskWarning.visibility=View.VISIBLE}
            else{taskHolder.imageviewTaskWarning.visibility=View.GONE}
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.getContext()
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun setListOfTasks(list: List<Task>) {
        taskList = list
        notifyDataSetChanged()
    }

    interface onTaskClickListener {
        fun onTaskClick(task: Task, position: Int)
    }

    fun setTaskClickListener(listener: onTaskClickListener) {
        this.taskClickListener = listener
    }

    inner class TaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textviewTaskTitle: TextView = itemView.findViewById(R.id.textview_taskTitle)
        val textviewTaskDueDate: TextView = itemView.findViewById(R.id.textview_taskDueDate)
        val textviewTaskNotes: TextView = itemView.findViewById(R.id.textview_taskNotes)
        val textviewTaskStatus: TextView = itemView.findViewById(R.id.textview_taskStatus)
        val taskLayout: LinearLayout = itemView.findViewById(R.id.layout_task)
        val imageviewTaskWarning:ImageView =itemView.findViewById(R.id.imageview_pastDueDateWarning)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (taskClickListener != null && position != RecyclerView.NO_POSITION) {
                    taskClickListener!!.onTaskClick(taskList.get(position), position)
                }
            }
        }
    }
}