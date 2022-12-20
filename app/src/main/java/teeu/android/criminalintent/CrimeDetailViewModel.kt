package teeu.android.criminalintent

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class CrimeDetailViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    private val crimeIdLiveData = MutableLiveData<UUID>()

    var crimeLiveData : LiveData<Crime?> = Transformations.switchMap(crimeIdLiveData) { crimeId ->
        crimeRepository.getCrime(crimeId)
    }

    fun loadCrime(crimeId : UUID) {
        crimeIdLiveData.value = crimeId
    }

    fun updateCrime(crime : Crime) {
        viewModelScope.launch(Dispatchers.IO) {
            crimeRepository.updateCrime(crime)
        }
    }
}