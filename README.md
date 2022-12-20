# 2022.12.20
RecyclerView 에서 서로 다른 list_item을 보여주고 싶을 때
- layout, ViewHolder 따로 만들어서 viewType에 따라 보여주도록 하면 됨 

layout이 중첩되면 속도가 느려짐
- 그래서 복잡한 layout 구성할 때 constraint layout이 좋음

val date = SimpleDateFormat("EEEE, MMM, dd, yyyy HH:mm:ss") //요일, 월, 일, 년, 시:분:초

# 로컬데이터베이스 Room 구현
- Repository - 싱글톤 패턴적용
- 싱글톤은 activity, fragment보다 생명주기가 더 길다
- 장치 회전시에도 여전히 존재함 

- Database에 접근하려면 스레드를 만들어서 백그라운드에서 수행해야함 (오래 걸리는 작업들이 이러함)
- UI는 main thread에서만 변경가능

# LiveData - 데이터 전달을 쉽게 만드는 것이 목표
- 다른 스레드간에 전달 가능(백그라운드 <-> main)
- LiveData를 관찰해서 변경되면 view도 변경

#Application() 클래스
- activity 생성 전에 먼저 생성됨. 전역 상태 초기화할 때 사용
- 따로 Application 클래스를 상속받은 클래스를 만들면 manifests에 application 안에 android:name 설정해야함
