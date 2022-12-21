package teeu.android.criminalintent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrimeListViewModel : ViewModel() {
    val crimes = mutableListOf<Crime>()

    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData by lazy {
        crimeRepository.getCrimes()
    }

//    init {
//        for (i in 0 until 100) {
//            val crime = Crime()
//            crime.title = "Crime #$i"
//            crime.isSolved = i % 2 == 0
//            crime.button = i % 3 == 0
////            crimes.add(crime)
//            crimes += crime
//        }
//    }

    fun insertCrime(crime : Crime) {
        viewModelScope.launch(Dispatchers.IO) {
            crimeRepository.insertCrime(crime)
        }
    }
}