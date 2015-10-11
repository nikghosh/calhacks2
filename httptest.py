import urllib.request
import json
import pprint

customerID = '560f0205f8d8770df0ef99e7'
apiKey = 'd3a81dbab024b45a8aa3857d732d67ee'

url = 'http://api.reimaginebanking.com/enterprise/accounts?key={}'.format(apiKey)
#url = 'http://api.reimaginebanking.com/enterprise/customers/{}?key={}'.format(customerID,apiKey)

test = urllib.request.Request(url)
response = urllib.request.urlopen(test)
raw = response.read()
lst = json.loads(raw.decode('utf-8'))['results']
#print(str(lst))


memo = []
f = open('test','w')
#f.write(str(lst))

for obj in lst:
    new_url = 'http://api.reimaginebanking.com/enterprise/customers/{}?key={}'.format(obj['customer_id'],apiKey)
    temp_req = urllib.request.Request(new_url)
    temp_resp = urllib.request.urlopen(temp_req)
    temp_raw = temp_resp.read()
    data = json.loads(temp_raw.decode('utf-8'))
    print('writing data')
    if data['address'] not in memo:
        print('data unique')
        memo.append(data['address'])
        f.write(str(data['address'])+'\n')

f.close()


print("done")
