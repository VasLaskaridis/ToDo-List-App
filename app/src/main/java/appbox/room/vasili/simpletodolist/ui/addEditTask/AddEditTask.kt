package appbox.room.vasili.simpletodolist.ui.addEditTask

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import appbox.room.vasili.simpletodolist.R
import appbox.room.vasili.simpletodolist.SimpleToDoListApplication
import appbox.room.vasili.simpletodolist.databinding.ActivityAddEditTaskBinding
import appbox.room.vasili.simpletodolist.models.task.Task
import java.text.SimpleDateFormat
import java.util.*


class AddEditTask : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var binding:ActivityAddEditTaskBinding?=null

    private lateinit var REQUEST:String

    private lateinit var datePickerCaller:String

    private lateinit var taskToBeEdited: Task

    private val addEditTaskViewModel: AddEditTaskViewModel  by viewModels {
        AddEditTaskViewModelFactory((application as SimpleToDoListApplication).taskRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityAddEditTaskBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        supportActionBar?.title="Create new task"

        REQUEST= intent.getStringExtra("Request").toString()

        val spinnerAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this, R.array.task_states, android.R.layout.simple_spinner_item)
        spinnerAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item)
        binding!!.spinnerSetTaskStatus.adapter=spinnerAdapter
        binding!!.spinnerSetTaskStatus.onItemSelectedListener=addEditTaskViewModel

        binding!!.imageButtonPickbackgroundRed.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                imageBackroundResourseHelper("red")
            }
        })
        binding!!.imageButtonPickbackgroundGreen.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                imageBackroundResourseHelper("green")
            }
        })
        binding!!.imageButtonPickbackgroundBlue.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                imageBackroundResourseHelper("blue")
            }
        })
        binding!!.imageButtonPickbackgroundOrange.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                imageBackroundResourseHelper("orange")
            }
        })
        binding!!.imageButtonPickbackgroundYellow.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                imageBackroundResourseHelper("yellow")
            }
        })

        binding!!.layoutPickStartDate.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                datePickerCaller="start_date"
                createDatePickerDialog().show()
            }
        })

        binding!!.layoutPickDueDate.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                datePickerCaller="due_date"
                createDatePickerDialog().show()
            }
        })

        if(REQUEST.equals("edit")){
            taskToBeEdited= intent.getParcelableExtra("task")!!

            supportActionBar?.setTitle("Edit "+taskToBeEdited.taskTitle)

            binding!!.edittextSetTaskTitle.setText(taskToBeEdited.taskTitle)
            taskToBeEdited.taskTitle?.let { addEditTaskViewModel.setTaskTitle(it) }

            binding!!.edittextSetTaskNotes.setText(taskToBeEdited.taskNotes)
            taskToBeEdited.taskNotes?.let { addEditTaskViewModel.setTaskTitle(it) }

            val sdf = SimpleDateFormat("yyyy-MM-dd")
            var formattedDateStart: String =""
            taskToBeEdited.taskStartDate?.let{
                formattedDateStart = sdf.format(it)
                addEditTaskViewModel.setTaskStartDate(it)
            }
            binding!!.textViewStartDate.setText(formattedDateStart)

            var formattedDateEnd=""
            taskToBeEdited.taskDueDate?.let {
                formattedDateEnd = sdf.format(taskToBeEdited.taskDueDate)
                taskToBeEdited.taskDueDate?.let { addEditTaskViewModel.setTaskDueDate(it) }
            }
            binding!!.textViewDueDate.setText(formattedDateEnd)
            
            taskToBeEdited.taskColor?.let { imageBackroundResourseHelper(it) }

            val status=taskToBeEdited.taskStatusIndicator
            when (status){
                "Scheduled" ->binding!!.spinnerSetTaskStatus.setSelection(0)
                "Ongoing"->binding!!.spinnerSetTaskStatus.setSelection(1)
                "Pending" ->binding!!.spinnerSetTaskStatus.setSelection(2)
                "Completed"->binding!!.spinnerSetTaskStatus.setSelection(3)
            }
        }


        binding!!.flbtnSaveTask.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                if(REQUEST.equals("add")){
                    if(insert()){finish()}
                }else{
                    if(update(taskToBeEdited)){finish()}
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
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
        if( datePickerCaller.equals("start_date")){
            binding!!.textViewStartDate.text=formattedDate
            addEditTaskViewModel.setTaskStartDate(date)
        }
        else if(datePickerCaller.equals("due_date")){
            binding!!.textViewDueDate.text=formattedDate
            addEditTaskViewModel.setTaskDueDate(date)
        }
    }

    fun createDatePickerDialog():DatePickerDialog{
        val calendar=Calendar.getInstance()
        val year=calendar.get(Calendar.YEAR);
        val month=calendar.get(Calendar.MONTH);
        val day=calendar.get(Calendar.DAY_OF_MONTH);
        val datePickerDialog:DatePickerDialog=DatePickerDialog(this,this, year, month, day)
        return datePickerDialog
    }

    fun imageBackroundResourseHelper(color:String){
        binding!!.imageButtonPickbackgroundRed.setBackgroundResource(R.drawable.palette_red_unselected)
        binding!!.imageButtonPickbackgroundBlue.setBackgroundResource(R.drawable.palette_blue_unselected)
        binding!!.imageButtonPickbackgroundGreen.setBackgroundResource(R.drawable.palette_green_unselected)
        binding!!.imageButtonPickbackgroundYellow.setBackgroundResource(R.drawable.palette_yellow_unselected)
        binding!!.imageButtonPickbackgroundOrange.setBackgroundResource(R.drawable.palette_orange_unselected)
        when(color){
            "red"-> {
                binding!!.imageButtonPickbackgroundRed.setBackgroundResource(R.drawable.palette_red_selected)
                addEditTaskViewModel.setTaskColor("red")
            }
            "blue"-> {
                binding!!.imageButtonPickbackgroundBlue.setBackgroundResource(R.drawable.palette_blue_selected)
                addEditTaskViewModel.setTaskColor("blue")
            }
            "green"-> {
                binding!!.imageButtonPickbackgroundGreen.setBackgroundResource(R.drawable.palette_green_selected)
                addEditTaskViewModel.setTaskColor("green")
            }
            "yellow"-> {
                binding!!.imageButtonPickbackgroundYellow.setBackgroundResource(R.drawable.palette_yellow_selected)
                addEditTaskViewModel.setTaskColor("yellow")
            }
            "orange"-> {
                binding!!.imageButtonPickbackgroundOrange.setBackgroundResource(R.drawable.palette_orange_selected)
                addEditTaskViewModel.setTaskColor("orange")
            }
        }
    }

     fun insert():Boolean{
        addEditTaskViewModel.setTaskTitle(binding!!.edittextSetTaskTitle.text.toString())
        addEditTaskViewModel.setTaskNotes(binding!!.edittextSetTaskNotes.text.toString())
         if(addEditTaskViewModel.getTaskTitle().trim().isEmpty()){
             Toast.makeText(applicationContext,"Please make sure you give a title",Toast.LENGTH_LONG).show()
             return false
         }
         else{
            return addEditTaskViewModel.insert()
         }
    }

     fun update(task:Task):Boolean{
        addEditTaskViewModel.setTaskTitle(binding!!.edittextSetTaskTitle.text.toString())
        addEditTaskViewModel.setTaskNotes(binding!!.edittextSetTaskNotes.text.toString())
         if(addEditTaskViewModel.getTaskTitle().trim().isEmpty()){
             Toast.makeText(applicationContext,"Please make sure you give a title",Toast.LENGTH_LONG).show()
             return false
         }
         else{
             return addEditTaskViewModel.update(task)
         }
    }

}