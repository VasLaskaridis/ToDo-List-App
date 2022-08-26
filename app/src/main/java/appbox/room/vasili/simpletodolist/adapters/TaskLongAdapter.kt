package appbox.room.vasili.simpletodolist.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import appbox.room.vasili.simpletodolist.R
import appbox.room.vasili.simpletodolist.models.task.Task
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TaskLongAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var taskClickListener: onTaskClickListener? = null

    var taskList: List<Task> = ArrayList()

    lateinit var context:Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.getContext())
        val itemView = inflater.inflate(R.layout.layout_task_long, parent, false)
        val viewHolder = TaskHolder(itemView)
        return viewHolder
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.getContext()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val taskHolder: TaskHolder = holder as TaskHolder
        val currentTask: Task = taskList.get(position)

        val date=Calendar.getInstance().time

        taskHolder.textviewTaskTitle.text=currentTask.taskTitle

        taskHolder.imageviewTaskWarning.visibility=View.GONE

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        var formattedDateStart: String?=null
        var formattedDateEnd: String?=null
        var textHolder=""

        if(currentTask.taskStartDate!=null){
            Log.e("test ","1")
            formattedDateStart = sdf.format(currentTask.taskStartDate)
            textHolder="Start date: $formattedDateStart"
        }
        if(currentTask.taskDueDate!=null){
            Log.e("test ","2")
            formattedDateEnd = sdf.format(currentTask.taskDueDate)
            if(textHolder.equals("")){
                textHolder="Due date: $formattedDateEnd"
            }else{
                textHolder="$formattedDateStart - $formattedDateEnd"
            }
            if(date >= currentTask.taskDueDate){
                taskHolder.imageviewTaskWarning.visibility=View.VISIBLE
            }
        }
        if(textHolder.equals("")){
            textHolder="No date"
        }
        taskHolder.textviewTaskDueDate.setText(textHolder)

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