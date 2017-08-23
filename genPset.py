import itertools
def genPset(Items):
    result = []
    for i in range(len(Items)+1):
        for e in itertools.combinations(Items, i):
            result.append(list(e))
    return result