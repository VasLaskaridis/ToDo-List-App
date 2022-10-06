package appbox.room.vasili.simpletodolist.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import appbox.room.vasili.simpletodolist.R
import appbox.room.vasili.simpletodolist.models.subtask.Subtask


class SubtaskAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var subtaskDeleteClickListener: SubtaskAdapter.onSubtaskDeleteClickListener? = null

    private var subtaskCheckboxClickListener: SubtaskAdapter.onSubtaskCheckboxClickListener? = null

    var subtaskList: List<Subtask> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.getContext())
        val itemView = inflater.inflate(R.layout.layout_subtask, parent, false)
        val viewHolder = SubtaskHolder(itemView)
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val taskHolder: SubtaskAdapter.SubtaskHolder = holder as SubtaskAdapter.SubtaskHolder
        val currentSubtask: Subtask = subtaskList.get(position)

        taskHolder.checkboxSubtask.isChecked=currentSubtask.subtaskCompletionIndicator

        taskHolder.textviewSubtaskTitle.text=currentSubtask.subtaskTitle
        if(taskHolder.checkboxSubtask.isChecked){
            taskHolder.textviewSubtaskTitle.setPaintFlags(taskHolder.textviewSubtaskTitle.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        }else{
            taskHolder.textviewSubtaskTitle.setPaintFlags(taskHolder.textviewSubtaskTitle.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())
        }
    }

    override fun getItemCount(): Int {
        return subtaskList.size
    }

    fun setListOfSubtasks(list: List<Subtask>) {
        subtaskList = list
        notifyDataSetChanged()
    }

    interface onSubtaskDeleteClickListener {
        fun onSubtaskDeleteClick(subtask: Subtask)
    }

    fun setSubtaskDeleteClickListener(listener: onSubtaskDeleteClickListener) {
        subtaskDeleteClickListener = listener
    }

    interface onSubtaskCheckboxClickListener {
        fun onSubtaskCheckboxClick(subtask: Subtask, isChecked:Boolean)
    }

    fun setSubtaskCheckboxClickListener(listener: onSubtaskCheckboxClickListener) {
        subtaskCheckboxClickListener = listener
    }

    inner class SubtaskHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textviewSubtaskTitle: TextView = itemView.findViewById(R.id.textview_subtaskTitle)
        val checkboxSubtask: CheckBox = itemView.findViewById(R.id.checkbox_completed)
        val subtaskDeleteButton: LinearLayout = itemView.findViewById(R.id.layout_subtaskDelete)

        init {
            subtaskDeleteButton.setOnClickListener(object:View.OnClickListener{
                override fun onClick(v: View?) {
                    val position = adapterPosition
                    if (subtaskDeleteClickListener != null && position != RecyclerView.NO_POSITION) {
                        subtaskDeleteClickListener!!.onSubtaskDeleteClick(subtaskList.get(position))

                    }
                }
            })
            checkboxSubtask.setOnClickListener(object:View.OnClickListener{
                override fun onClick(v: View?) {
                    val position = adapterPosition
                    if (subtaskCheckboxClickListener != null && position != RecyclerView.NO_POSITION) {
                        subtaskCheckboxClickListener!!.onSubtaskCheckboxClick(subtaskList.get(position),checkboxSubtask.isChecked)
                    }
                }
            })
        }
    }
}