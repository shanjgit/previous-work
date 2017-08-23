x = float(raw_input("Enter a number: "))
epsilon = 0.01
numGuesses = 0

if abs(x)<1:
    if x <1:
        high = 1.0
        low = 0
    else:
        high = 0
        low = -1
else:
    low = min(0.0, x)
    high = max(0.0,x)
ans = (high + low)/2.0
while abs(ans**3-x)>= epsilon:
    print 'low =',  low ,  'high=', high,  'ans= ', ans
    numGuesses += 1
    if ans**3<x:
        low = ans
    else: 
        high = ans
    ans = (high + low)/2.0
print 'numGuesses= ',  numGuesses
print ans,  'is close to square root of',  x
    
