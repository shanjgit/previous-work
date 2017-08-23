#Newton-Raphson for square root
#Find x such that x**2 - 24 is within epsilon of 0
epsilon = 0.01
count = 0
k = 25.0
guess = k/2.0
while abs(guess*guess - k) >= epsilon:
    count += 1
    guess = guess - (((guess**2) - k)/(2*guess))
print 'number of guess',count, 'Square root of', k, 'is about', guess