def fibonacci(n):
    ''' n: non-negative integer
    returs Fibonacci of n
    '''
    assert type(n) == int and n>=0

    if n == 1 or n ==0: # Leonardo da Pisa(aka Fibonacci) modeled the following challenge
        return 1 #Newborn pair of rabbits (female x1, male x1) are put in a pen
    else: #Rabits mate at the age of one month
        return fibonacci(n-2)+fibonacci(n-1) 
# Rabbits have a one month gestation period
# Assume rabbits never die, that female always produces one new pair(one male, one female) every month from its second month on. 
# How many females are there after the nth months?    
             