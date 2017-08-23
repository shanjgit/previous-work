import pdb
import string
import operator
class SM(object):
    #def __init__(self,startState):
        #self.startState = startState  
        #self.state = None          
    def start(self):
        self.state = self.startState
    def step(self,inp):
        (s,o)=self.getNextValues(self.state,inp)
        self.state = s
        return o
    def transduce(self,inputs):
        self.start()
        return [self.step(inp) for inp in inputs]

class BinaryOp:
    def __init__(self, left, right):
        self.left = left
        self.right = right

    def __str__(self):
        return self.opStr + '(' + \
               str(self.left) + ', ' +\
               str(self.right) + ')'
    __repr__ = __str__

class Sum(BinaryOp):
    opStr = 'Sum'
    def eval(self,env):
        return operator.add(self.left.eval(env), self.right.eval(env))

class Prod(BinaryOp):
    opStr = 'Prod'
    def eval(self,env):
        return self.left.eval(env)*self.right.eval(env)

class Quot(BinaryOp):
    opStr = 'Quot'
    def eval(self,env):
        return self.left.eval(env)/self.right.eval(env)

class Diff(BinaryOp):
    opStr = 'Diff'
    def eval(self,env):
        return self.left.eval(env)-self.right.eval(env)

class Assign(BinaryOp):
    opStr = 'Assign'
    def eval(self,env):
        name = self.left.name
        val = self.right.eval(env)
        env[name] = val
        
class Number:
    def __init__(self, val):
        self.value = val
    def __str__(self):
        return 'Num('+str(self.value)+')'
    def eval(self,env):
        return self.value   
    __repr__ = __str__

class Variable:
    def __init__(self, name):
        self.name = name
    def __str__(self):
        return 'Var('+self.name+')'
    def eval(self,env):
        return env[self.name]  
    __repr__ = __str__

# characters that are single-character tokens
seps = ['(', ')', '+', '-', '*', '/', '=']

# Convert strings into a list of tokens (strings)
def tokenize(string):
    output = []
    word = ''
    if len(string) == 1:
        output.append(string)
        
    for item in string:
        if item in seps:      
            if not word =='':
                output.append(word)
                word = ''    
            output.append(item)                 
        elif not item == ' ':
            word = word+item
        else:
            if not word == '':
                output.append(word)
                word = ''        
    return output

# tokens is a list of tokens
# returns a syntax tree:  an instance of {\tt Number}, {\tt Variable},
# or one of the subclasses of {\tt BinaryOp} 
def parse(tokens):
    def parseExp(index):
        token = tokens[index]
        if numberTok(token):
            number =float(token)
            return (Number(number),index+1)
        elif variableTok(token):
            return (Variable(token),index+1)
        else:
            (leftTree,indexOP) = parseExp(index+1)
            op = tokens[indexOP]
            (rightTree,indexRParen)=parseExp(indexOP+1)
            if op == '+':
                SyntaxTree = Sum(leftTree,rightTree)
            elif op == '-':
                 SyntaxTree = Diff(leftTree,rightTree)
            elif op == '*':
                 SyntaxTree = Prod(leftTree,rightTree)
            elif op == '/':
                 SyntaxTree = Quot(leftTree,rightTree)
            elif op == '=':
                 SyntaxTree = Assign(leftTree,rightTree)
            return (SyntaxTree,indexRParen+1)                        
    (parsedExp, nextIndex) = parseExp(0)
    return parsedExp

# token is a string
# returns True if contains only digits
def numberTok(token):
    for char in token:
        if not char in string.digits: return False
    return True

# token is a string
# returns True its first character is a letter
def variableTok(token):
    for char in token:
        if char in string.letters: return True
    return False

# thing is any Python entity
# returns True if it is a number
def isNum(thing):
    return type(thing) == int or type(thing) == float

# Run calculator interactively
def calc():
    env = {}
    while True:
        e = raw_input('%') # prints %, returns user input
        answer=parse(tokenize(e))
        print '%', answer.eval(env)
        print '   env =', env

# exprs is a list of strings
# runs calculator on those strings, in sequence, using the same environment
def calcTest(exprs):
    env = {}
    for e in exprs:
        print '%', e                    # e is the experession 
        oup = parse(tokenize(e))
        print oup.eval(env)
        print '   env =', env

# Simple tokenizer tests
'''Answers are:
['fred']
['777']
['777', 'hi', '33']
['*', '*', '-', ')', '(']
['(', 'hi', '*', 'ho', ')']
['(', 'fred', '+', 'george', ')']
['(', 'hi', '*', 'ho', ')']
['(', 'fred', '+', 'george', ')']
'''
def testTokenize():
    print tokenize('fred ')
    print tokenize('777 ')
    print tokenize('777 hi 33 ')
    print tokenize('**-)(')
    print tokenize('( hi * ho )')
    print tokenize('(fred + george)')
    print tokenize('(hi*ho)')
    print tokenize('( fred+george )')
#testTokenize()
# Simple parsing tests from the handout
'''Answers are:
Var(a)
Num(888.0)
Sum(Var(fred), Var(george))
Quot(Prod(Var(a), Var(b)), Diff(Var(cee), Var(doh)))
Quot(Prod(Var(a), Var(b)), Diff(Var(cee), Var(doh)))
Assign(Var(a), Prod(Num(3.0), Num(5.0)))
'''
class Tokenizer(SM):
    def __init__(self):
        self.startState = ''
        
    def getNextValues(self, state, inp):
        if inp == ' ': return ('',state)
        elif inp in seps: return (inp,state)
        else:
            if state == '': return (state+inp,state)     
            elif state in seps: return (inp,state)   
            else: return (state+inp,'')
            
            
            
            

    
def testParse():
    print parse(['a'])
    print parse(['888'])
    print parse(['(', 'fred', '+', 'george', ')'])
    print parse(['(', '(', 'a', '*', 'b', ')', '/', '(', 'cee', '-', 'doh', ')' ,')'])
    print parse(tokenize('((a * b) / (cee - doh))'))
    print parse(tokenize('(a = (3 * 5))'))
#testParse()
####################################################################
# Test cases for EAGER evaluator
####################################################################

def testEval():
    env = {}
    Assign(Variable('a'), Number(5.0)).eval(env)
    print Variable('a').eval(env)
    env['b'] = 2.0
    print Variable('b').eval(env)
    env['c'] = 4.0
    print Variable('c').eval(env)
    print Sum(Variable('a'), Variable('b')).eval(env)
    print Sum(Diff(Variable('a'), Variable('c')), Variable('b')).eval(env)
    Assign(Variable('a'), Sum(Variable('a'), Variable('b'))).eval(env)
    print Variable('a').eval(env)
    print env
# Basic calculator test cases (see handout)
testExprs = ['(2 + 5)',
             '(z = 6)',
             'z',
             '(w = (z + 1))',
             'w'
             ]


####################################################################
# Test cases for LAZY evaluator
####################################################################

# Simple lazy eval test cases from handout
'''Answers are:
Sum(Var(b), Var(c))
Sum(2.0, Var(c))
6.0
'''
def testLazyEval():
    env = {}
    Assign(Variable('a'), Sum(Variable('b'), Variable('c'))).eval(env)
    print Variable('a').eval(env)
    env['b'] = Number(2.0)
    print Variable('a').eval(env)
    env['c'] = Number(4.0)
    print Variable('a').eval(env)

# Lazy partial eval test cases (see handout)
lazyTestExprs = ['(a = (b + c))',
                  '(b = ((d * e) / 2))',
                  'a',
                  '(d = 6)',
                  '(e = 5)',
                  'a',
                  '(c = 9)',
                  'a',
                  '(d = 2)',
                  'a']
# calcTest(lazyTestExprs)

## More test cases (see handout)
partialTestExprs = ['(z = (y + w))',
                    'z',
                    '(y = 2)',
                    'z',
                    '(w = 4)',
                    'z',
                    '(w = 100)',
                    'z']

# calcTest(partialTestExprs)
