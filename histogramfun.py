import pylab

# You may have to change this path
WORDLIST_FILENAME = "words.txt"

def loadWords():
    """
    Returns a list of valid words. Words are strings of uppercase letters.
    
    Depending on the size of the word list, this function may
    take a while to finish.
    """
    print "Loading word list from file..."
    # inFile: file
    inFile = open(WORDLIST_FILENAME, 'r', 0)
    # wordList: list of strings
    wordList = []
    for line in inFile:
        wordList.append(line.strip().lower())
    print "  ", len(wordList), "words loaded."
    return wordList

def plotVowelProportionHistogram(wordList, numBins=10):
    """
    Plots a histogram of the proportion of vowels in each word in wordList
    using the specified number of bins in numBins
    """
    num = []
    for e in wordList:
        countVowel = 0
        for x in e:
            if x == 'a' or x == 'e' or x == 'i' or x == 'o' or x == 'u':
                countVowel += 1
        num.append(countVowel/float(len(e)))
    pylab.hist(num,numBins,)
    pylab.show()
                
            

if __name__ == '__main__':
    wordList = loadWords()
    plotVowelProportionHistogram(wordList)
