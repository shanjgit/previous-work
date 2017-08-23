def f(x):
    import math
    return 10*math.e**(math.log(0.5)/5.27 * x)

def radiationExposure(start, stop, step):
    count = 0
    x = start

    while x< stop:
        y = f(x)*step
        count += y
        x += step
    return count

 
    


     

