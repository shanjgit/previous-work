# Uses python3
n = int(input())
a = [int(x) for x in input().split()]
assert(len(a) == n)

max_idx1 = -1
max_idx2 = -1

for i in range(0, n):
    if max_idx1 == -1 or a[i] > a[max_idx1]:
        max_idx1 = i
        
for j in range(0, n):
    if j != max_idx1 and (max_idx2 == -1 or a[j] > a[max_idx2]):
        max_idx2 = j

result = a[max_idx2] * a[max_idx1]
print(result)
