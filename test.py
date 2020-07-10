from selenium import webdriver

import time


driver = webdriver.Chrome("/Users/xiao230/Desktop/soft/chromedriver")
driver.get("https://su.lianjia.com/ditu/")


# input = driver.find_element_by_css_selector('#kw')
# input.send_keys("aaa")
#
# button = driver.find_element_by_css_selector('#su')
# button.click()


time.sleep(5)

# 缩小地图，显示全部地区
pinch_button = driver.find_element_by_css_selector('a[class="narrow"]')
pinch_button.click()
time.sleep(5)


# 处理城市区的逻辑
def select_qu(qu):
    qu.click()
    # 大区下面所有小区划片（比如，姑苏区 -- 平江新城）
    hpxqs = driver.find_elements_by_css_selector('div[data-longitude]')
    for hpxq in hpxqs:
        print(hpxq.location)
        selector = hpxq.find_elements_by_css_selector('p')
        for info in selector:
            print(info.location)
            print(info.text)



# 显示所有区
selector = driver.find_elements_by_css_selector('div[data-name]')
for bt in selector:
    css_selector = bt.find_elements_by_css_selector("p")
    for info in css_selector:
        print(info.text)
    # select_qu(bt)



button = driver.find_element_by_css_selector('div[data-name="姑苏"]')
select_qu(button)
