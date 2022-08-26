package appbox.room.vasili.simpletodolist.ui.task

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import appbox.room.vasili.simpletodolist.R
import appbox.room.vasili.simpletodolist.SimpleToDoListApplication
import appbox.room.vasili.simpletodolist.adapters.SubtaskAdapter
import appbox.room.vasili.simpletodolist.databinding.ActivityTaskBinding
import appbox.room.vasili.simpletodolist.models.subtask.Subtask
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.ui.addEditTask.AddEditTask
import java.text.SimpleDateFormat


class TaskActivity : AppCompatActivity() {

    private lateinit var binding:ActivityTaskBinding

    private lateinit var task: Task

    private val taskViewModel: TaskViewModel by viewModels {
        TaskViewModel.TaskViewModelFactory((application as SimpleToDoListApplication).taskRepository,(application as SimpleToDoListApplication).subtaskRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        task= intent.getParcelableExtra("task")!!

        supportActionBar?.setTitle(task.taskTitle)

        val sdf = SimpleDateFormat("yyyy-MM-dd")
        if(task.taskDueDate!=null) {
            val formattedDate: String = sdf.format(task.taskDueDate)
            binding.textViewDueDate.text = formattedDate
        }else{
            binding.textViewDueDate.text = "No date"
        }
        if(!task.taskNotes.equals("")){
            binding.textviewNotes.text=task.taskNotes
        }

        val subtaskRecyclerView=binding.recycleviewSubtasks
        subtaskRecyclerView.layoutManager=LinearLayoutManager(this)
        subtaskRecyclerView.setHasFixedSize(true)
        val subtaskAdapter=SubtaskAdapter()
        subtaskAdapter.setSubtaskDeleteClickListener(object:SubtaskAdapter.onSubtaskDeleteClickListener{
            override fun onSubtaskDeleteClick(subtask: Subtask) {
                showDeleteDialog(subtask)
            }

        })
        subtaskAdapter.setSubtaskCheckboxClickListener(object : SubtaskAdapter.onSubtaskCheckboxClickListener {
            override fun onSubtaskCheckboxClick(subtask: Subtask, isChecked:Boolean) {
                taskViewModel.update(subtask, isChecked)
            }

        })
        subtaskRecyclerView.adapter=subtaskAdapter

        taskViewModel.getAllSubtasksOfTask(task.taskId).observe(this, object : Observer<List<Subtask>> {
            override fun onChanged(subtaskList: List<Subtask>) {
                subtaskAdapter.setListOfSubtasks(subtaskList)
                if (subtaskAdapter.itemCount>0){
                    binding.textviewSubtaskHint.visibility=View.GONE
                }
                else{
                    binding.textviewSubtaskHint.visibility=View.VISIBLE
                }
            }
        })

        binding.buttonAddSubtask.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                addSubtask()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean =
        when (item.itemId) {
            R.id.item_edit -> {
                goToEditTask()
                true
            }
            R.id.item_delete -> {
                showDeleteDialog(task)
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_menu,menu)
        return true
    }

    fun goToEditTask(){
        val intent: Intent = Intent( this, AddEditTask::class.java)
        intent.putExtra("Request", "edit")
        intent.putExtra("task", task)
        startActivity(intent)
        finish()
    }

    fun addSubtask(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.layout_add_subtask_dialog)
        dialog.setCancelable(true)
        val button_save =dialog.findViewById<Button>(R.id.button_saveSubtask)
        button_save.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                if(dialog.findViewById<EditText>(R.id.edittext_subtaskTitle).text.toString().length>0){
                    taskViewModel.insert(task.taskId,dialog.findViewById<EditText>(R.id.edittext_subtaskTitle).text.toString())
                    dialog.cancel()
                }else{
                    Toast.makeText(applicationContext,"Please make sure you give a title",Toast.LENGTH_LONG).show()
                }
            }

        })
        val button_cancel =dialog.findViewById<Button>(R.id.button_cancel)
        button_cancel.setOnClickListener(object :View.OnClickListener{
            override fun onClick(v: View?) {
                dialog.cancel()
            }
        })
        dialog.show()
    }

    fun showDeleteDialog(task: Task) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Do you want to delete "+task.taskTitle+"?")
        dialog.setPositiveButton("YES", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                taskViewModel.delete(task)
                finish()
            }
        })
        dialog.setNegativeButton("No",object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {}
        })
        dialog.setCancelable(true)
        val alert = dialog.create()
        alert.show()
    }

     fun showDeleteDialog(subtask: Subtask) {
        val dialog = AlertDialog.Builder(this)
        dialog.setMessage("Do you want to delete "+subtask.subtaskTitle+"?")
        dialog.setPositiveButton("YES", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                taskViewModel.delete(subtask)
            }
        })
        dialog.setNegativeButton("No",object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {}
        })
        dialog.setCancelable(true)
        val alert = dialog.create()
        alert.show()
    }

}