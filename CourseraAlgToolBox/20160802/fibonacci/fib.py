# Uses python3
def calc_fib(n,fb_table = {}):   
    
    if n in fb_table:
        return fb_table[n]
    elif n <= 1:
        f = n
    else:
        f = calc_fib(n - 1,fb_table) + calc_fib(n - 2,fb_table)
    
    fb_table[n] = f
    
    return f

n = int(input())
print(calc_fib(n))
