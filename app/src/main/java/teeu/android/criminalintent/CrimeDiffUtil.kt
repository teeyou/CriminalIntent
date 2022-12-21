package teeu.android.criminalintent

import androidx.recyclerview.widget.DiffUtil

class CrimeDiffUtil : DiffUtil.ItemCallback<Crime>() {
    override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean = oldItem.id == newItem.id //고유의 id를 가지고 동일한 item인지 확인

    override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean { //위 함수가 true일 때 호출되는 함수
        return oldItem == newItem
//      return oldItem.title == newItem.title && oldItem.isSolved == newItem.isSolved && oldItem.date.toString() == newItem.date.toString()
    }
}