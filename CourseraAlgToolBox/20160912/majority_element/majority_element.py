# Uses python3
import sys

def get_majority_element(a, left, right):
    dic = {}
    how_many = len(a) // 2
    for x in a:
        if x in dic:
            dic[x] = dic[x] + 1
        else:
            dic[x] = 1
    #print(dic)
    for x in a:
        if dic[x] > how_many:
            return x
    #if left == right:
     #   return -1
    #if left + 1 == right:
     #   return a[left]
    #write your code here
    
    return -1

if __name__ == '__main__':
    input = sys.stdin.read()
    n, *a = list(map(int, input.split()))
    if get_majority_element(a, 0, n) != -1:
        print(1)
    else:
        print(0)
