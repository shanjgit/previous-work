#!/usr/bin/env python2.7

import unittest
from dnaseqlib import *

### Utility classes ###

# Maps integer keys to a set of arbitrary values.
class Multidict:
    # Initializes a new multi-value dictionary, and adds any key-value
    # 2-tuples in the iterable sequence pairs to the data structure.
    def __init__(self, pairs=[]):
        self.dic = {}
        for item in pairs:
            self.put(item[0],item[1])
    # Associates the value v with the key k.
    def put(self, k, v):
        if k in self.dic:
            self.dic[k].append(v)
        else:
            self.dic[k] = [v]
    # Gets any values that have been associated with the key k; or, if
    # none have been, returns an empty sequence.
    def get(self, k):
        if k in self.dic:
            returned_list = self.dic[k][:]
            return returned_list
        else:
            return []
        

# Given a sequence of nucleotides, return all k-length subsequences
# and their hashes.  (What else do you need to know about each
# subsequence?)
def subsequenceHashes(seq, k):
    assert k > 0
    try:
        
        subseq = ''
        for i in range(k):
            subseq += seq.next()
        hash_iterator = RollingHash(subseq)
        pos = 0
        while True:
                      
            yield  (hash_iterator.current_hash() , (pos, subseq))
            old = subseq[0]
            new = seq.next()
            subseq = subseq[1:] + new
            hash_iterator.slide(old,new)
            pos += 1
            
    except StopIteration:
        return
    
    
    

# Similar to subsequenceHashes(), but returns one k-length subsequence
# every m nucleotides.  (This will be useful when you try to use two
# whole data files.)
def intervalSubsequenceHashes(seq, k, m):
    assert m >= k
    assert m > 0 and k > 0
    try:
        pos = 0
        while True:
            subseq = ''
            for i in range(k):
                subseq += seq.next()
            hash_iterator = RollingHash(subseq)
            yield (hash_iterator.current_hash() , (pos, subseq))
            
            for j in range(m-k):
                seq.next()
            
            pos = pos + m
                                           
    except StopIteration:
        return 
    

# Searches for commonalities between sequences a and b by comparing
# subsequences of length k.  The sequences a and b should be iterators
# that return nucleotides.  The table is built by computing one hash
# every m nucleotides (for m >= k).
def getExactSubmatches(a, b, k, m):
    print "Building table from sequence A..."
    seq_dic = Multidict(intervalSubsequenceHashes(a, k, m))
    print "...successfully building table."
    
    print "Building generator B..."
    hash_generator_b = subsequenceHashes(b, k)
    print "...done building generator."
    
    for hash_b , (pos_b, sub_seq_b) in hash_generator_b:
        for (pos_a, sub_seq_a) in seq_dic.get(hash_b):
            if sub_seq_b == sub_seq_a:
                yield (pos_a,pos_b)
    return 

        
    
    

    

if __name__ == '__main__':
    if len(sys.argv) != 4:
        print 'Usage: {0} [file_a.fa] [file_b.fa] [output.png]'.format(sys.argv[0])
        sys.exit(1)

    # The arguments are, in order: 1) Your getExactSubmatches
    # function, 2) the filename to which the image should be written,
    # 3) a tuple giving the width and height of the image, 4) the
    # filename of sequence A, 5) the filename of sequence B, 6) k, the
    # subsequence size, and 7) m, the sampling interval for sequence
    # A.
    compareSequences(getExactSubmatches, sys.argv[3], (500,500), sys.argv[1], sys.argv[2], 8, 100)
