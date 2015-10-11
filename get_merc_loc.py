import urllib.request
import json

"""
Gets the geolocation of merchants within the
CapitalOne Hackathon API

Outputs a file merc_loc.txt in format:

lat lng
lat lng
...


Kazu Kogachi, Cal Hacks 2.0
"""

apiKey = 'd3a81dbab024b45a8aa3857d732d67ee'

url = 'http://api.reimaginebanking.com/enterprise/merchants?key={}'.format(apiKey)
req = urllib.request.Request(url)
response = urllib.request.urlopen(req)
raw = response.read() #in bytes, decode with .decode('utf-8')
#Result is in the form of a dictionary with a list of merchants
lst = json.loads(raw.decode('utf-8'))['results']

memo = [] #removes duplicat addresses, though there might not be any

f = open('merc_loc.txt','w')

for obj in lst[0:2]:
    geo = obj['geocode']
    f.write(str(geo['lat']) + ' ' + str(geo['lng']))

print('Merc Loc Done')
