package appbox.room.vasili.simpletodolist.ui.orderByState

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import appbox.room.vasili.simpletodolist.adapters.ViewPagerAdapter
import appbox.room.vasili.simpletodolist.databinding.FragmentStatusBinding
import com.google.android.material.tabs.TabLayoutMediator


class TasksByStateFragment : Fragment() {

    private var _binding: FragmentStatusBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStatusBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPager = binding.pager
        viewPager.adapter = viewPagerAdapter
        val tabLayout =binding.tablayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position==0){tab.text = "Past due date"}
            else if(position==1){tab.text = "Ongoing"}
            else if(position==2){tab.text = "Scheduled"}
            else if(position==3){tab.text = "Pending"}
            else {tab.text = "Completed"}
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}