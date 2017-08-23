# Uses python3
import sys
l  = []
denominator = [10,5,1]
def get_change(m):
    nex = m
    
    for d in denominator:
        l.append(nex // d)
        nex = nex % d
        
    return sum(l)

if __name__ == '__main__':
    m = int(sys.stdin.read())
    print(get_change(m))
