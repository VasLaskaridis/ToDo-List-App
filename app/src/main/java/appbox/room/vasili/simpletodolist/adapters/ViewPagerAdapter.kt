package appbox.room.vasili.simpletodolist.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import appbox.room.vasili.simpletodolist.ui.orderByState.TasksStates.Completed.CompletedFragment
import appbox.room.vasili.simpletodolist.ui.orderByState.TasksStates.ongoing.OngoingFragment
import appbox.room.vasili.simpletodolist.ui.orderByState.TasksStates.pastDueDate.PastDueDateFragment
import appbox.room.vasili.simpletodolist.ui.orderByState.TasksStates.pending.PendingFragment
import appbox.room.vasili.simpletodolist.ui.orderByState.TasksStates.scheduled.ScheduledFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        var visiblefragment:Fragment
        when (position){
           0 -> visiblefragment= PastDueDateFragment()
            1 -> visiblefragment= OngoingFragment()
            2 -> visiblefragment= ScheduledFragment()
            3 -> visiblefragment= PendingFragment()
            else -> visiblefragment= CompletedFragment()
        }
        return visiblefragment
    }

}