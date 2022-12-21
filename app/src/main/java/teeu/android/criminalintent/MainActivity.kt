package teeu.android.criminalintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.MenuProvider
import java.util.*

class MainActivity : AppCompatActivity() , CrimeListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if(currentFragment == null) {
            val fragment = CrimeListFragment.newInstance()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit()

        }
    }

    override fun onCrimeSelected(crimeId: UUID) {

        val fragment = CrimeFragment.newInstance(crimeId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment) //fragment_container가 비어있으면 add와 동일한 기능을 수행
            .addToBackStack(null) //백 버튼 누르면 이전 Fragment로 이동
            .commit()
    }
}