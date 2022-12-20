package teeu.android.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import teeu.android.criminalintent.Crime
import java.util.*

@Dao
interface CrimeDao {
    @Query("select * from crime")
    fun getCrimes() : LiveData<List<Crime>>
//    fun getCrimes() : List<Crime>

    @Query("select * from crime where id=(:id)")
    fun getCrime(id : UUID) : LiveData<Crime?>
//    fun getCrime(id : UUID) : Crime?

    @Insert
    fun insertCrime(crime : Crime)
}