# CodeDiary18
### **sometrend_cralwer.py**
sometrend 데이터 크롤링
- sometrend_connect(name) :  
  인자 : **name** = 검색어  
  sometrend 사이트에 접속해서 데이터 크롤링
- data_processing(html, name) :  
  인자 : **html** = sometrend_connect()의 결과인 html code, **name** = 검색어
  sometrend_connect()를 통해 얻은 결과를 가공
- sometrend(name) :  
  인자 : **name** = 검색어  
  sometrend_connect()와 data_processing()을 호출하는 main 함수

### **sometrend_crawler.exe**
sometrend_crawler.py를 실행파일로 만든 것
