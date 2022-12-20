package teeu.android.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import teeu.android.criminalintent.database.CrimeDatabase
import java.util.*

private const val DATABASE_NAME = "crime-database"

class CrimeRepository private constructor(context : Context){
    private val database : CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val crimeDao = database.crimeDao()

//    fun getCrimes() : List<Crime> = crimeDao.getCrimes()
    fun getCrimes() : LiveData<List<Crime>> = crimeDao.getCrimes()

//    fun getCrime(id : UUID) : Crime? = crimeDao.getCrime(id)
    fun getCrime(id : UUID) : LiveData<Crime?> = crimeDao.getCrime(id)

    fun insertCrime(crime : Crime) {
        crimeDao.insertCrime(crime)
    }
    companion object {
        private var instance : CrimeRepository? = null

        fun init(context: Context) {
            if(instance == null)
                instance = CrimeRepository(context)
        }

        fun get() : CrimeRepository {
            return instance ?: throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}