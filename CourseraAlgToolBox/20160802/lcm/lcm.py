# Uses python3
import sys

def lcm(a, b):
    x = abs(max(a,b))
    y = abs(min(a,b))
    while x % y != 0:
        rem = x % y
        x = y
        y = rem
    return a*b // y

if __name__ == '__main__':
    input = sys.stdin.read()
    a, b = map(int, input.split())
    print(lcm(a, b))

