package teeu.android.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.DatePicker
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

//Fragment Lifecycle
// onAttach -> onCreate -> onCreateView -> onActivityCreated ->
// onStart -> onResume -> onPause -> onStop -> onDestroyView ->
// Activity종료 -> onDestroy -> onDetach

private const val DIALOG_DATE ="DialogDate"
private const val ARG_KEY = "crime_id"
class CrimeFragment : Fragment() {
    private lateinit var crime : Crime
    private lateinit var titleField : EditText
    private lateinit var dateButton : Button
    private lateinit var solvedCheckBox : CheckBox
    private val crimeDetailViewModel : CrimeDetailViewModel by lazy {
        ViewModelProvider(this).get(CrimeDetailViewModel::class.java)
    }
    companion object {
        fun newInstance(crimeId : UUID) : CrimeFragment {
            val args = Bundle().apply {
                putSerializable(ARG_KEY, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        crime = Crime()
        val crimeId = arguments?.getSerializable(ARG_KEY) as UUID
        Log.d("MYTAG", "crimeId : " + crimeId)
        crimeDetailViewModel.loadCrime(crimeId)

        setFragmentResultListener("requestKey") { key, bundle ->
            val date = bundle.getSerializable("bundleKey") as Date
            crime.date = date
            updateUI()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        titleField = view.findViewById(R.id.crime_title) as EditText
        dateButton = view.findViewById(R.id.crime_date) as Button
//        dateButton.apply {
//            text = crime.date.toString()
//            isEnabled = false
//        }

        solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer { crime->
                crime?.let {
                    this.crime = crime
                    updateUI()
                }
            }
        )
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime.title = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        }
        titleField.addTextChangedListener(titleWatcher)
        solvedCheckBox.setOnCheckedChangeListener { _, isCheck ->
            crime.isSolved = isCheck
        }

        dateButton.setOnClickListener {
            DatePickerFragment.newInstance(crime.date).apply {
                show(this@CrimeFragment.parentFragmentManager,DIALOG_DATE)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.updateCrime(crime)
    }

    private fun updateUI() {
        titleField.setText(crime.title)
//        dateButton.setText(crime.date.toString())
        val date = SimpleDateFormat("EEEE, MMM, dd, yyyy HH:mm:ss").format(crime.date)
        dateButton.setText(date.toString())

        dateButton.apply {
            isEnabled = true
            jumpDrawablesToCurrentState() //애니메이션 효과 끄기인데 isEnabled = false로 바꿨을 때 제대로 동작 안하네...
        }
        solvedCheckBox.apply {
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }

    }
}