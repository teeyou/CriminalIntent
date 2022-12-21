package teeu.android.criminalintent

import android.app.DatePickerDialog
import android.app.Dialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.DatePicker
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_DATE = "date"
const val PICKER_DATE = "picker_date"
class DatePickerFragment : DialogFragment() {
    companion object {
        fun newInstance(date: Date) : DatePickerFragment {
            val fragment = DatePickerFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(ARG_DATE, date)
            }

            return fragment
        }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_DATE) as Date

        val calendar = Calendar.getInstance()
        calendar.time = date
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(requireContext(),  {_,year : Int ,month : Int ,day : Int ->
            val resultDate : Date = GregorianCalendar(year,month,day).time
            setFragmentResult("requestKey", bundleOf("bundleKey" to resultDate))
        }, year,month,day)
    }

}