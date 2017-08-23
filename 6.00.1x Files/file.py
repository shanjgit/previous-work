test = open('fileIO.txt','w')

for i in range(2):
    name = raw_input('Enter name: ')
    test.write(name+'\n')

test.close()

read = open('fileIO.txt','r')
x = 0
for i in read:
    print str(x)+'th = ',i[:-1]
    x+=1
read.close()