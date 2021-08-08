from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from html_table_parser import parser_functions as parser
from selenium import webdriver
import time
import json
import time
import sys

def sometrend_connect(name):
    path = "./chromedriver.exe"     # chrome 드라이버를 사용해서 웹 브라우저를 실행
    options = webdriver.ChromeOptions()
    options.add_argument("headless")    # 창 숨기는 옵션 추가
    options.add_experimental_option("excludeSwitches", ["enable-logging"])
    driver = webdriver.Chrome(path,options=options)
    driver.get("https://some.co.kr/analysis/social/reputation") # 썸트렌드 페이지
    time.sleep(2) # 페이지가 모두 열릴 때 까지 2초 기다리기
    driver.find_element_by_link_text("다시보지않기").click()
    time.sleep(1)

    html = driver.page_source
    soup = BeautifulSoup(html, 'html.parser')
    content=soup.find('input', class_='input-keyword-text')["name"]
    driver.find_element_by_name(content).click()    # 검색창 찾기
    time.sleep(1)

    html = driver.page_source
    content = soup.find('div', {'class': 'modal-search-analysis-keyword'}).input["name"]
    element = driver.find_element_by_name(content)
    element.send_keys(name) # 검색어 입력
    time.sleep(2)
    element.send_keys(Keys.ENTER)   # 검색 실행
    time.sleep(7)
    html = driver.page_source
    driver.quit()
    return html

def data_processing(html, name):
    soup = BeautifulSoup(html, 'html.parser')
    # 댓글 평판 크롤링
    temp = soup.find_all('table')
    p = parser.make2d(temp[1])
    positive = [ [x[0],int(x[2].replace(",",""))] for x in p if x[1]=='긍정']
    negative = [ [x[0],int(x[2].replace(",",""))] for x in p if x[1]=='부정']
    neutral = [ [x[0],int(x[2].replace(",",""))] for x in p if x[1]=='중립']
    
    # 긍정, 부정, 중립 건수
    p3 = [int((x.text[:-1]).replace(",","")) for x in soup.find_all('span','legend-value')]
    p3_rate = [ [x, x/sum(p3)*100] for x in p3]

    data = {}
    data['name'] = name
    data["rateOfSentiment"]=[]
    data["rateOfSentiment"].append({
        "type":"positive",
        "count":p3_rate[0][0],
        "rate":p3_rate[0][1]
    })
    data["rateOfSentiment"].append({
        "type":"negative",
        "count":p3_rate[1][0],
        "rate":p3_rate[1][1]
    })
    data["rateOfSentiment"].append({
        "type":"neutral",
        "count":p3_rate[2][0],
        "rate":p3_rate[2][1]
    })

    data['reputation'] = []
    data["reputation"].append({
        "type" : "positive",
        "data" : positive
    })
    data["reputation"].append({
        "type" : "negative",
        "data" : negative
    })
    data["reputation"].append({
        "type" : "neutral",
        "data" : neutral
    })
    return data
    
def sometrend(name):
    try:
        html = sometrend_connect(name)
        data = data_processing(html, name)
        with open("./data/sometrend_{}.json".format(name), 'w',encoding="UTF-8") as outfile:
            json.dump(data, outfile, ensure_ascii = False, indent = 4)
    except Exception as e:
        print("ERROR :", e)

if __name__=="__main__":
    if len(sys.argv)!=2:
        sys.exit("입력 형식 : 'sometrend_crawler.exe <Youtuber name>'")
    else:
        sys.exit(sometrend(sys.argv[-1]))