package teeu.android.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "CrimeListFragment"

// Fragment가 소멸될 때 ViewModel도 같이 소멸되는데
// Activity가 다른 Fragment로 대체될 때 Fragment와 ViewModel은 같이 소멸
// 하지만 Fragment를 backstack에 추가를 하면, 소멸되지 않고 남아있게 됨
class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this).get(CrimeListViewModel::class.java)
    }

    private var adapter : CrimeAdapter? = CrimeAdapter(emptyList())

    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager =
            LinearLayoutManager(context) //RecyclerView를 생성하면 반드시 layoutManager를 설정해줘야함

        val decoration = DividerItemDecoration(context, VERTICAL)
        crimeRecyclerView.addItemDecoration(decoration)
        crimeRecyclerView.adapter = adapter
//        updateUI(crimeListViewModel.crimes)

        crimeListViewModel.insertCrime(Crime(title = "Crime #0"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #1"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #2"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #3"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #4"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #5"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #6"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #7"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #8"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #9"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #10"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #11"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #12"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #13"))
        crimeListViewModel.insertCrime(Crime(title = "Crime #14"))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer { crimes ->
                crimes?.let {
                    Log.d(TAG, "Got crimes ${crimes.size}")
                    updateUI(crimes)
                }
            }
        )
    }
    private fun updateUI(crimes: List<Crime>) {
        crimeRecyclerView.adapter = CrimeAdapter(crimes)
    }
    private inner class CrimeButtonHolder(view : View) : ViewHolder(view), View.OnClickListener {
        private lateinit var crime : Crime
        private val titleTextView: TextView = view.findViewById(R.id.crime_title)
        private val dateTextView: TextView = view.findViewById(R.id.crime_date)
        private val challengeButton : Button = view.findViewById(R.id.challenge_button)

        init {
            view.setOnClickListener(this)
            challengeButton.setOnClickListener {
                Toast.makeText(context,"${crime.title} Button Clicked!", Toast.LENGTH_SHORT).show()
            }
        }
        fun bind(crime : Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            challengeButton.text = "Challenge"
        }

        override fun onClick(p0: View?) {
            val toast = Toast.makeText(context, "${crime.title} pressed!",Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP,Gravity.AXIS_X_SHIFT,Gravity.AXIS_Y_SHIFT)
            toast.show()
        }
    }

    private inner class CrimeHolder(view: View) : ViewHolder(view),
        View.OnClickListener {
        private lateinit var crime : Crime
        private val titleTextView: TextView = view.findViewById(R.id.crime_title)
        private val dateTextView: TextView = view.findViewById(R.id.crime_date)
        private val solvedImageView : ImageView = view.findViewById(R.id.crime_solved)

        init {
            view.setOnClickListener(this)
        }
        fun bind(crime: Crime) {
            this.crime = crime
            titleTextView.text = this.crime.title
//            dateTextView.text = this.crime.date.toString()
            val date = SimpleDateFormat("EEEE, MMM, dd, yyyy HH:mm:ss") //요일, 월, 일, 년, 시:분:초
            dateTextView.text = date.format(Calendar.getInstance().time).toString()

            solvedImageView.visibility = if(crime.isSolved) View.VISIBLE else View.GONE
        }

        override fun onClick(p0: View?) {
            val toast = Toast.makeText(context, "${crime.title} pressed!",Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER,Gravity.AXIS_X_SHIFT,Gravity.AXIS_Y_SHIFT)
            toast.show()
        }
    }

    private inner class CrimeAdapter(var crimes: List<Crime>) :
        RecyclerView.Adapter<ViewHolder>() {
        lateinit var view : View
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            when {
                viewType == 0 -> {
                    view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
                    return CrimeHolder(view)
                }
                else -> {
                    view = layoutInflater.inflate(R.layout.list_item_crime2, parent, false)
                    return CrimeButtonHolder(view)
                }
            }
//            return CrimeHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val crime = crimes[position]
            if(holder is CrimeHolder)
                holder.bind(crime)
            else if(holder is CrimeButtonHolder)
                holder.bind(crime)
        }

        override fun getItemCount(): Int = crimes.size

        override fun getItemViewType(position: Int): Int {
            return if(crimes[position].button) 1 else 0
        }
    }
}