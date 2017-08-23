s =  'cqbauathqiqwovzvfx'
a = 'abcdefghijklmnopqrstuvwxyz'
l1, M = "", ""
for i in range(len(s)-1):
    alph = s[i] 
    beta = s[i+1]
    if l1 =='':
        l1 = l1+alph
    if a.index(alph)<=a.index(beta):
        l1 = l1+beta
        if i == len(s)-2 and len(l1)>len(M):
            M = l1
                  
           
    elif a.index(alph)>a.index(beta):  
        if len(l1)>len(M):
            M = l1             
        l1 = ''           
print 'Longest substring in alphabetical order is:',M


    
    