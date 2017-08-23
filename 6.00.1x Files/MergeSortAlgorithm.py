import operator
inversionNum =0
inFile = open('IntegerArray.txt','r',0)
list = list(inFile)
l = []
for x in list:
    y = int(x)
    l.append(y)
L = [0,-1,-2,3,4]
def merge(left,right,compare): 
    global inversionNum 
    i, j  =0, 0
    result = []
    while i < len(left) and j<len(right) : 
        if compare(left[i],right[j]):  
            result.append(left[i])
            i += 1 
        else: 
            result.append(right[j])
            Lst = left[i:]
            inversionNum +=len(Lst)   
            j+=1 
    while(i< len(left)):
        result.append(left[i])
        i += 1 
    while(j<len(right)):
        result.append(right[j])
        j+=1            
    return result

def mergeSort(L,compare=operator.lt): 
    if len(L)<2: 
        return L[:]
    else: 
        middle = int(len(L)/2)
        (left) = mergeSort(L[:middle],compare) 
        (right) = mergeSort(L[middle:],compare)
        return merge(left,right,compare)

mergeSort(l)
print inversionNum