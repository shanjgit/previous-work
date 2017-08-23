# Uses python3
def evalt(a, b, op):
    if op == '+':
        return a + b
    elif op == '-':
        return a - b
    elif op == '*':
        return a * b
    else:
        assert False

def matrix(num_list):
    """return an n+1 by n+1 matrix with all the elements being 0"""
    m = []
    n = len(num_list)
    for i in range(n+1):
        m.append([0]*(n+1))
    for j in range(n+1):
        if j >= 1:
            m[j][j] = num_list[j-1]
    return m

def MinAndMax(M,m,i,j,op):
    min_val = float("Inf")
    max_val = float("-Inf")
    for k in range(i,j):
        a = evalt(M[i][k],M[k+1][j],op[k])
        b = evalt(M[i][k],m[k+1][j],op[k])
        c = evalt(m[i][k],M[k+1][j],op[k])
        d = evalt(m[i][k],m[k+1][j],op[k])
        min_val = min(min_val,a,b,c,d)
        max_val = max(max_val,a,b,c,d)
        
    return (min_val,max_val)
    
    
    

def get_maximum_value(dataset):
    
    op_list = [0]
    num_list = []
    for i in range(len(dataset)):
        if i % 2 ==0 :
            num_list.append(int(dataset[i]))
        else:
            op_list.append(dataset[i])
    #write your code here
    N = len(num_list)
    M_matrix = matrix(num_list)
    m_matrix = matrix(num_list)
    
    for s in range(1,N+1):
        for i in range(1,N-s+1):
            j = i + s
            m_matrix[i][j], M_matrix[i][j] = MinAndMax(M_matrix,m_matrix,i,j,op_list)
            
            



    return M_matrix[1][N]


if __name__ == "__main__":
    print(get_maximum_value(input()))
