"""
Edits the data to improve the spread of a paired list of data
matching it against another paird list of data

It standardizes the second list and then transforms it back 
in accordance to the first list

Outputs a file mod_[filename].txt. in format:

f(x1) f(y1)
f(x2) f(y1)
...

Kazu Kogachi, Cal Hacks 2.0
"""

from math import sqrt

pplList = [] #list A, [var x, var y]
mercList = [] #list B, [var x, var y]

reader = open('loc/va_loc.txt','r')
line = reader.readline()
while line != '':
    temp = str(line).split()
    pplList.append([float(temp[0]),float(temp[1])])
    line = reader.readline()
reader.close()

reader = open('loc/va_merc_loc.txt','r')
line = reader.readline()
while line != '':
    temp = str(line).split()
    mercList.append([float(temp[0]),float(temp[1])])
    line = reader.readline()
reader.close()


def sum_stats(lst):
    total_x, total_y, n = 0, 0, 0
    for point in lst:
        total_x += point[0]
        total_y += point[1]
        n += 1
        
    d = {}
    d['x_avg'] = total_x / n
    d['y_avg'] = total_y / n
    
    sumsqresid_x, sumsqresid_y = 0, 0
    for point in lst:
        sumsqresid_x += (point[0]-d['x_avg'])**2
        sumsqresid_y += (point[1]-d['y_avg'])**2
    d['sd_x'] = sqrt(sumsqresid_x/n)
    d['sd_y'] = sqrt(sumsqresid_y/n)
    
    return d

#summary statistics x_avg, y_avg, sd_x, sd_y 
statsA = sum_stats(pplList)
statsB = sum_stats(mercList)

###print(str(statsA) + "\n" + str(statsB))

#Convert B into Z-score, then retransform it into A
#z = (x - x_avg)/sd_x
zBList = [[(p[0]-statsB['x_avg'])/statsB['sd_x'],(p[1]-statsB['y_avg'])/statsB['sd_y']] for p in mercList]
transB = [[ p[0]*statsA['sd_x']+statsA['x_avg'] , p[1]*statsA['sd_y']+statsA['y_avg'] ] for p in zBList]
###print(str(sum_stats(zBList)))
###print(str(sum_stats(transB)))

writer = open('loc/mod_va_merc_loc.txt','w')
for p in transB:
    writer.write(str(p[0]) + ' ' + str(p[1]) + '\n')
writer.close()

print('Data normalized against list A')
