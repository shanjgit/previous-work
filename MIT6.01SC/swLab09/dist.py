class DDist:
    def __init__(self, dictionary):
        if not (abs(sum(dictionary.values())-1) < 1e-6 and min(dictionary.values()) >= 0.0):
            raise Exception, "Probabilities must be nonnegative, and must sum to 1"
        self.d = dictionary

    def prob(self, elt):
        pass #your code here

    def support(self):
        pass #your code here

    def __repr__(self):
        return "DDist(%r)" % self.d
    
    __str__ = __repr__

    def project(self, mapFunc):
        pass #your code here

    def condition(self, testFunc):
        pass #your code here

def marginalize(d, i):
    pass #your code here

def makeJointDistribution(PA, PBgA):
    pass #your code here

def totalProbability(PA, PBgA):
    pass #your code here

def bayesRule(PA, PBgA, b):
    pass #your code here
