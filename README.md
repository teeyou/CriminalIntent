# 2022.12.20 12장까지 완료
### RecyclerView 에서 서로 다른 list_item을 보여주고 싶을 때
- layout, ViewHolder 따로 만들어서 viewType에 따라 보여주도록 하면 됨 

### 하나의 Activity를 가지고 여러개의 Fragment를 호스팅할 때 (Single Activity Architecture - Navigation을 이용해서 구현)
- Fragment안에 Callbacks interface를 정의해놓고, 호스팅하는 Activity에서 이를 반드시 구현하게 함
- onAttatch (callbacks = context as Callbacks? )
- onDetatch (callback = null)

### Fragment
- Fragment가 인자를 가지고 생성되려면 companion object 안에 newInstance(args)를 정의해서 Bundle객체를 만들어서 arguemnts를 세팅함
- 생명주기
- onCreate (데이터 읽어옴) -> 
- onCreateView (inflate) -> 
- onViewCreated (UI로직 처리)

### 로컬데이터베이스 Room 구현
- Repository - 싱글톤 패턴적용
- 싱글톤은 activity, fragment보다 생명주기가 더 길다. 장치 회전시에도 여전히 존재함 
- Database에 접근하려면 스레드를 만들어서 백그라운드에서 수행해야함 (오래 걸리는 작업들이 이러함)
- UI는 main thread에서만 변경가능

### LiveData - 데이터 전달을 쉽게 만드는 것이 목표
- 다른 스레드간에 전달 가능(백그라운드 <-> main)
- LiveData를 관찰해서 변경되면 view도 변경
- LiveData를 쓰면 데이터베이스에서 읽어올때 알아서 백그라운드 스레드로 실행
- 하지만 insert, update는 알아서 하지 않음. 책에서는 Executors를 사용함. 
- 나는 ViewModel에서 제공하는 coroutine 사용

### TypeConverter
- table에 저장할 때 일반 datatype을 씀
- Date, UUID와 같은 type은 TypeConverter를 써서 
- db에 넣을 때 쓰는 type과 
- db에서 꺼내서 쓸 때 type으로 바꿔준다

### Application() 클래스
- activity 생성 전에 먼저 생성됨. 전역 상태 초기화할 때 사용
- 따로 Application 클래스를 상속받은 클래스를 만들면 manifests에 application 안에 android:name 설정해야함

### ListAdapter이용해서 RecyclerView 효율적으로 사용하기
- DiffUtill.ItemCallback<T>을 구현하고,
- ListAdapter를 적용해서 submitList(mutableList)을 쓰면 바뀐 item만 채워서 보여줌

### 이외에
- layout이 중첩되면 속도가 느려짐. 그래서 복잡한 layout 구성할 때 constraint layout이 좋음
- val date = SimpleDateFormat("EEEE, MMM, dd, yyyy HH:mm:ss") //요일, 월, 일, 년, 시:분:초
- Room 사용시 build.gradle에서 plugins {id 'kotlin-kapt'} 추가하기

# 2022.12.21 14장까지 완료
- intent extra - Activity 간의 데이터 전달
- callback interface - Fragment -> Activity 데이터 전달
- newInstance(args) - Activity -> Fragment 데이터 전달
## 같은 Activity에 의해 호스팅되는 Fragment 간의 데이터 전달
- setTargetFragment . targetFragment 를 썼지만 deprecated 됨
- setFragmentResultListener(데이터 받음), setFragmentResult(데이터 보냄) 로 대체
#
- Fragment에서 쓰는 onCreateOptionsMenu, onOptionsItemSelected, setHasOptionsMenu 전부 deprecated 됨
- res -> image asset -> Action Bar and Tab Icons설정 후 Clip Art에서 선택해서 이미지 만들면 해상도 별로 쓰이는 파일이 다 만들어짐

