def sumDigits(s):
    """Assumes s is a string 
    return the sum of the decimal digits in s 
    for example, if s is 'a2b3c' it returns 5"""
    Sum = 0
    for i in s:
        try:
            x=int(i)
        except ValueError:
            continue
        Sum += x
    return Sum
        
def divide(x,y):
    try:
        result = x/y
    except ZeroDivisionError, e:
        print 'Division by zero'+str(e)
    except TypeError:
        divide(int(x),int(y))
    else:
        print 'result is: ', result
    finally:
        print 'excecuting finally clause'
        
def getRatio(v1,v2):
    """Assumes: v1 and v2 are lists of euqanl length of numbers 
    Returns a list containing the meaning values of v1[i]/v2[i]"""
    ratios = []
    for index in range(len(v1)):
        try:
            ratios.append(v1[index]/float(v2[index]))   
        except ZeroDivisionError:
            ratios.append(float('NaN'))
        except:
            raise ValueError('getRatio called with bad arg')   
    return ratios   
try :
    print getRatio([1.0,2.0,7.0,6.0],[1.0,2.0,0.0,3.0])
    print 'a',getRatio([1.0,2.0],[3.0])
except ValueError, msg:
    print msg             