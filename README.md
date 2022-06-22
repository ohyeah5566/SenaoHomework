# SenaoHomework

### 需求：
Call Api取得 商品列表 __需要儲存資料__ 再顯示到List<br/>
點擊 item 前往商品詳細頁<br/>
搜尋功能

### 使用的套件：
Retrofit + coroutine 負責網路請求的部分<br/>
Paging3 負責實作LoadMore的功能，雖然撈回來的資料不到10筆而且也不清楚下一頁資料的機制，但跟List有關的還是用Paging實作 將來方便調整<br/>
Room 儲存資料<br/>
MVVM 相關套件<br/>

框架使用MVVM
