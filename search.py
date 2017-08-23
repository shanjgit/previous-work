class Stack(object):
    def __init__(self):
        self.data = []
    def push(self,item):
        self.data.append(item)
    def pop(self):
        return self.data.pop()
    def isEmpty(self):
        return len(self.data)==0
class Queue:
    def __init__(self):
        self.data = []
    def push(self,item):
        self.data.append(item)
    def pop(self):
        return self.data.pop(0)
    def isEmpty(self):
        return len(self.data) ==0

class PriorityQueue(object):
    def __init__(self):
        self.data = []
    def push(self,item,cost):
        self.data.append((cost,item))
    def pop(self):
        self.data.sort()
        return self.data.pop(0)[1]
    def isEmpty(self):
        return len(self.data) ==0 