def dToB(n,numDigits):
    """requires: n is a nature number less than 2**numDigits 
    returns a binary staring string of length numDigits representing the decimal 
    number n."""
    assert type(n)== int and type (numDigits) == int\
    and n>= 0 and n < 2**numDigits
    
    bStr = ''
    while n>0:
        bStr = str(n%2)+bStr
        n = n//2
    
    while numDigits -len(bStr) >0:
        bStr = '0' +bStr
    return bStr

def genPset(Items):
    """Generate a list of lists representing the power set of Items"""
    numSubsets = 2**len(Items)
    templates=[]
    for i in range(numSubsets):
        templates.append(dToB(i, len(Items)))
    
    pset = []
    for t in templates:
        elem = []
        for j in range(len(t)):
            if t[j] == '1':
                elem.append(Items[j])
        pset.append(elem)
    return pset

def chooseBest(pset,constraint, getVal, getWeight):
    bestVal = 0.0
    bestSet = None
    for Items in pset:
        ItemsVal = 0.0
        ItemsWeight = 0.0
        for item in Items:
            ItemsVal += getWeight(item)
        if ItemsWeight <= constraint and ItemsVal > bestVal:
            bestVal - ItemsVal
            bestSet = Items
        return (bestSet,bestVal)

"""def testBest():
    Items = buildItems()
    pset = getPset(Items)
    taken, val = chooseBest(pset,20,Item.getvalue,Item.getWeight)
    print ('Total value of items taken = '+str(val))
    for item in taken:
        print ' ', item"""
    