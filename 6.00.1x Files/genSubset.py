def genSubset(L):
    res = []
    if len(L)==0:
        return[[]]
    smaller = genSubset(L[:-1])
    extra = L[-1:]
    new = []
    for small in smaller:
        new.append(small+extra)
    return smaller+new

    