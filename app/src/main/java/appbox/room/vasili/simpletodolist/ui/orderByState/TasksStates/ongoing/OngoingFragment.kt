package appbox.room.vasili.simpletodolist.ui.orderByState.TasksStates.ongoing

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import appbox.room.vasili.simpletodolist.R
import appbox.room.vasili.simpletodolist.SimpleToDoListApplication
import appbox.room.vasili.simpletodolist.adapters.TaskLongAdapter
import appbox.room.vasili.simpletodolist.databinding.FragmentCompletedBinding
import appbox.room.vasili.simpletodolist.databinding.FragmentOngoingBinding
import appbox.room.vasili.simpletodolist.models.task.Task
import appbox.room.vasili.simpletodolist.ui.task.TaskActivity

class OngoingFragment : Fragment() {

    private val onGoingViewModel: OnGoingViewModel by viewModels {
        OnGoingViewModelFactory((activity!!.application as SimpleToDoListApplication).taskRepository)
    }

    private var _binding: FragmentOngoingBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOngoingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val recyclerView: RecyclerView =root.findViewById(R.id.recyclerview_ongoing)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)
        recyclerView.setHasFixedSize(true)
        val ongoingTasksAdapter= TaskLongAdapter()
        ongoingTasksAdapter.setTaskClickListener(object: TaskLongAdapter.onTaskClickListener{
            override fun onTaskClick(task: Task, position: Int) {
                val intent: Intent = Intent( activity, TaskActivity::class.java)
                intent.putExtra("task", task)
                startActivity(intent)
            }
        })
        recyclerView.adapter=ongoingTasksAdapter
        onGoingViewModel.getOnGoingTasks().observe(viewLifecycleOwner, object : Observer<List<Task>> {
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