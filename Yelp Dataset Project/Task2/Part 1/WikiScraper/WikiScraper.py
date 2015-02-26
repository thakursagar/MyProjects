import requests
import bs4
import re
from pymongo import MongoClient


from pymongo import MongoClient
client = MongoClient()
db = client.test
collection = db.food

list_of_dishes = []
response = requests.get('http://en.wikipedia.org/wiki/List_of_soul_foods_and_dishes')
soup = bs4.BeautifulSoup(response.text)

tree = soup.find_all(attrs={'class': 'wikitable'})

for branch in tree:
	node = branch.find_all('tr')
	for row in node:
	    thisrow = row.find_all('td')
	    for atag in thisrow:
	    	dishes=atag.find_all('a')
	    	if len(dishes) > 0:
	    		for elem in dishes:
	    			if re.search('[A-Za-z]+', elem.get_text()):
	    				list_of_dishes.append(elem.get_text())


post_dishes = {"keywords": list_of_dishes}
print post_dishes
collection.insert(post_dishes)

#print len(list_of_Indian_dishes)    			

    

