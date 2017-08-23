# Uses python3
import sys

fb_table = {}

def get_fibonacci_last_digit(n):
    n = n % 60
    if n in fb_table:
        return fb_table[n]
    elif n <= 1:
        f = n
    else:
        f = (get_fibonacci_last_digit(n - 1) + get_fibonacci_last_digit(n - 2)) % 10
    
    fb_table[n] = f
    
    return f

if __name__ == '__main__':
    input = sys.stdin.read()
    n = int(input)
    print(get_fibonacci_last_digit(n))
