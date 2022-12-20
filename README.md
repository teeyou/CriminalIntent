# 2022.12.20
RecyclerView 에서 서로 다른 list_item을 보여주고 싶을 때
- layout, ViewHolder 따로 만들어서 viewType에 따라 보여주도록 하면 됨 

layout이 중첩되면 속도가 느려짐
- 그래서 복잡한 layout 구성할 때 constraint layout이 좋음
