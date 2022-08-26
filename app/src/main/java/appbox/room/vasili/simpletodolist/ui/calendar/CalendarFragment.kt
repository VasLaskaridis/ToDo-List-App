package appbox.room.vasili.simpletodolist.ui.calendar

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import appbox.room.vasili.simpletodolist.SimpleToDoListApplication
import appbox.room.vasili.simpletodolist.adapters.TaskLongAdapter
import appbox.room.vasili.simpletodolist.databinding.FragmentCalendarBinding
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.ui.task.TaskActivity
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private val calendarViewModel: CalendarViewModel by viewModels {
        CalendarViewModelFactory((activity!!.application as SimpleToDoListApplication).taskRepository)
    }
    private  var binding: FragmentCalendarBinding?=null

    private lateinit var tasksAdapter:TaskLongAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding!!.root

        binding!!.buttonPickDate.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                createDatePickerDialog().show()
            }
        })

        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.recycleviewTasks.setLayoutManager(layoutManager)
        binding!!.recycleviewTasks.setHasFixedSize(true)
        tasksAdapter= TaskLongAdapter()
        tasksAdapter.setTaskClickListener(object:TaskLongAdapter.onTaskClickListener{
            override fun onTaskClick(task: Task, position: Int) {
                val intent: Intent = Intent( activity, TaskActivity::class.java)
                intent.putExtra("task", task)
                startActivity(intent)
            }
        })
        binding!!.recycleviewTasks.adapter=tasksAdapter

        calendarViewModel.getTasksOfDate().observe(viewLifecycleOwner, object : Observer<List<Task>> {
            override fun onChanged(taskList: List<Task>) {
                tasksAdapter.setListOfTasks(taskList)
                if(taskList.size==0){ binding!!.textviewNoTasks.visibility=View.VISIBLE}
                else{ binding!!.textviewNoTasks.visibility=View.GONE}
            }
        })

        val calendar= Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate: String = sdf.format(calendar.time)
        binding!!.textviewDate.text=formattedDate
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = year
        calendar[Calendar.MONTH] = month
        calendar[Calendar.DAY_OF_MONTH] = dayOfMonth
        val date = calendar.time
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate: String = sdf.format(date)
        binding!!.textviewDate.text=formattedDate
        calendarViewModel.setDate(date)
        calendarViewModel.getTasksOfDate()
    }

    fun createDatePickerDialog():DatePickerDialog{
        val calendar= Calendar.getInstance()
        val year=calendar.get(Calendar.YEAR);
        val month=calendar.get(Calendar.MONTH);
        val day=calendar.get(Calendar.DAY_OF_MONTH);
        val datePickerDialog:DatePickerDialog=DatePickerDialog(context!!,this, year, month, day)
        return datePickerDialog
    }

}