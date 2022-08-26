package appbox.room.vasili.simpletodolist.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import appbox.room.vasili.simpletodo.TaskAdapter
import appbox.room.vasili.simpletodolist.SimpleToDoListApplication
import appbox.room.vasili.simpletodolist.adapters.TaskLongAdapter
import appbox.room.vasili.simpletodolist.databinding.FragmentHomeBinding
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.ui.addEditTask.AddEditTask
import appbox.room.vasili.simpletodolist.ui.task.TaskActivity

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels {
        HomeViewModelFactory((activity!!.application as SimpleToDoListApplication).taskRepository)
    }
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val layoutManager_newTasks = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        _binding!!.recycleviewNewTasks.setLayoutManager(layoutManager_newTasks)
        _binding!!.recycleviewNewTasks.setHasFixedSize(true)
        val newTasksAdapter= TaskAdapter()
        newTasksAdapter.setTaskClickListener(object:TaskAdapter.onTaskClickListener{
            override fun onTaskClick(task: Task, position: Int) {
                val intent: Intent = Intent( activity, TaskActivity::class.java)
                intent.putExtra("task", task)
                 startActivity(intent)
            }
        })
        _binding!!.recycleviewNewTasks.adapter=newTasksAdapter
        homeViewModel.getAllTasksForToday().observe(viewLifecycleOwner, object : Observer<List<Task>> {
            override fun onChanged(taskList: List<Task>) {
                newTasksAdapter.setListOfTasks(taskList)
                if(taskList.size==0){ binding.textviewNoNewTasks.visibility=View.VISIBLE}
                else{ binding.textviewNoNewTasks.visibility=View.GONE}
            }
        })

        val layoutManager_ongoingTasks = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _binding!!.recyclerviewOngoingTasks.setLayoutManager(layoutManager_ongoingTasks)
        _binding!!.recyclerviewOngoingTasks.setHasFixedSize(true)
        val ongoingTasksAdapter= TaskLongAdapter()
        ongoingTasksAdapter.setTaskClickListener(object:TaskLongAdapter.onTaskClickListener{
            override fun onTaskClick(task: Task, position: Int) {
                val intent: Intent = Intent( activity, TaskActivity::class.java)
                intent.putExtra("task", task)
                startActivity(intent)
            }
        })
        _binding!!.recyclerviewOngoingTasks.adapter=ongoingTasksAdapter
        homeViewModel.getActiveTasks().observe(viewLifecycleOwner, object : Observer<List<Task>> {
            override fun onChanged(taskList: List<Task>) {
                ongoingTasksAdapter.setListOfTasks(taskList)
                if(taskList.size==0){ binding.textviewNoTasks.visibility=View.VISIBLE}
                else{ binding.textviewNoTasks.visibility=View.GONE}
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}