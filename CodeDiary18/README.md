# CodeDiary18
### **sometrend_cralwer.py**
sometrend 데이터 크롤링  
실행시 형식 : ```sometrend_crawler.py <youtuber 이름>```
- sometrend_connect(name) :  
  인자 : ```name``` = 검색어  
  sometrend 사이트에 접속해서 데이터 크롤링
- data_processing(html, name) :  
  인자 : ```html``` = sometrend_connect()의 결과인 html code, ```name``` = 검색어
  sometrend_connect()를 통해 얻은 결과를 가공
- sometrend(name) :  
  인자 : ```name``` = 검색어  
  sometrend_connect()와 data_processing()을 호출하는 main 함수

### **sometrend_crawler.exe**
sometrend_crawler.py를 실행파일로 만든 것  
실행시 형식 : ```sometrend_crawler.py <youtuber 이름>```
  
### 주의 사항
sometrend_crawler.py, sometrend_cralwer.exe가 존재하는 위치에 **chromedriver.exe**가 필요하며 **data**라는 폴더에 결과가 저장이되기 때문에 data라는 폴더를 생성할 필요가 있음
