import urllib.request
import json

"""
Gets the address from CapitalOne Hackathon API accounts

Kazu Kogachi, Cal Hacks 2.0
"""

customerID = '560f0205f8d8770df0ef99e7'
apiKey = 'd3a81dbab024b45a8aa3857d732d67ee'


#Gets list of accounts from API
url = 'http://api.reimaginebanking.com/enterprise/accounts?key={}'.format(apiKey)
test = urllib.request.Request(url)
response = urllib.request.urlopen(test)
raw = response.read() #raw is in bytes lol
lst = json.loads(raw.decode('utf-8'))['results']
#print(str(lst))

memo = [] #removes duplicate addresses
f = open('addresses.txt','w')

#Writes each address to f
for obj in lst:
    new_url = 'http://api.reimaginebanking.com/enterprise/customers/{}?key={}'.format(obj['customer_id'],apiKey)
    temp_req = urllib.request.Request(new_url)
    temp_resp = urllib.request.urlopen(temp_req)
    temp_raw = temp_resp.read()
    data = json.loads(temp_raw.decode('utf-8'))
    if data['address'] not in memo:
        memo.append(data['address'])
        f.write(str(data['address'])+'\n')

f.close()


print("done")
