# Uses python3
import sys

period = 0

fb_table = {}

def get_fibonacci_huge(n, m):
    global period
    if period != 0:
        n = n % period
        
    if n in fb_table:
        return fb_table[n]
    elif n <= 1:
        f = n
    else:
        f = (get_fibonacci_huge(n - 1,m) + get_fibonacci_huge(n - 2,m)) % m
        if f in fb_table.values():
            period  = len(fb_table.values())
            return fb_table[0]
    
    fb_table[n] = f
    

    
    return f

if __name__ == '__main__':
    input = sys.stdin.read();
    n, m = map(int, input.split())
    print(get_fibonacci_huge(n, m))
