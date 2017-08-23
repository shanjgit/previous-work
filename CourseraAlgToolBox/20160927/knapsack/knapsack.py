# Uses python3
import sys

def optimal_weight(W, w):
    # write your code here
    value = []
    n = len(w)
    for i in range(W+1):
        value.append([0]*(n+1))
    for i in range(1,len(w)+1):
        for weight in range(1,W+1):
            value[weight][i] = value[weight][i-1]
            if w[i-1] <= weight:
                val = value[weight-w[i-1]][i-1]+w[i-1]
                if value[weight][i] < val:
                    value[weight][i] = val
                #result = result + x
        #if result + x <= W:
         #   result = result + x
    return value[W][len(w)]

if __name__ == '__main__':
    input = sys.stdin.read()
    W, n, *w = list(map(int, input.split()))
    print(optimal_weight(W, w))
