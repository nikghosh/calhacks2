import urllib.request
import json

appId = '5wWMgIqkEQkCbqjJes4x'
appCode = '27suaUwub3nbfaBTDbEDEw'

url = 'https://geocoder.cit.api.here.com/6.2/geocode.json?app_id={}&app_code={}'.format(appId, appCode)

#print(str(lst))

try:
    f = open('addresses.txt','r')
except IOError:
    print("Cannot read file addresses.txt")
except Exception as e:
    print(str(e))
    f.close()
else:
    line = f.readline()
    writer = open('locations.txt','w')
    while line != '':
        d = 'dict({})'.format(line)
        d = eval(d)
        req_url = url + "&gen=9&housenumber={}&street={}&city={}".format(d['street_number'],
                                                                         d['street_name'],
                                                                         d['city'])
        req_url = req_url.replace(' ','%20')
        req = urllib.request.Request(req_url)
        response = urllib.request.urlopen(req)
        raw = response.read()
        raw = raw.decode('utf-8')
        d_raw = eval('dict({})'.format(raw))

        """debugging code
        print('1'+ str(d_raw['Response']))
        print('2'+ str(d_raw['Response']['View']))
        print('3'+ str(d_raw['Response']['View'][0]))
        print('4'+ str(d_raw['Response']['View'][0]['Result']))
        print('5'+ str(d_raw['Response']['View'][0]['Result'][0]))
        print('6'+ str(d_raw['Response']['View'][0]['Result'][0]['Location']))
        print('7'+ str(d_raw['Response']['View'][0]['Result'][0]['Location']['DisplayPosition']))
        """

        to_write = '\n'
        try:
            to_write = str(d_raw['Response']['View'][0]['Result'][0]['Location']['DisplayPosition']) + '\n'
        except Exception as e:
            print('lol why is something null\n' + str(e))
        
        print("WRITING " + repr(to_write))
        writer.write(to_write)
        
        line = f.readline()
        print('NEXT LINE READ: ' + str(line))
        
    writer.close()
    f.close()

print("done here api")
