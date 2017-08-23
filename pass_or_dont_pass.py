import random

def stdDev(X):
    """Assumes that X is a list of numbers.
    Returns the standard deviation of X"""
    mean = float(sum(X))/len(X)
    tot = 0.0
    for x in X:
        tot += (x - mean)**2
    return (tot/len(X))**0.5 #Square root of mean difference




def rollDie():
    return random.choice([1,2,3,4,5,6])


class CrapsGame(object):
    def __init__(self):
        self.passWins, self.passLosses = (0,0)
        self.dpWins, self.dpLosses, self.dpPushes = (0,0,0)
    def playHand(self):
        throw = rollDie() + rollDie()
        if throw == 7 or throw == 11:
            self.passWins += 1
            self.dpLosses += 1
        elif throw == 2 or throw == 3 or throw == 12:
            self.passLosses += 1
            if throw == 12:
                self.dpPushes += 1
            else:
                self.dpWins += 1
        else:
            point = throw
            while True:
                throw = rollDie() + rollDie()
                if throw == point:
                    self.passWins += 1
                    self.dpLosses += 1
                    break
                elif throw == 7:
                    self.passLosses += 1
                    self.dpWins += 1
                    break
    def passResults(self):
        return (self.passWins, self.passLosses)
    def dpResults(self):
        return (self.dpWins, self.dpLosses, self.dpPushes)

def crapsSim(handsPerGame, numGames):
    """Assumes handsPerGame and numGames are ints > 0
    Play numGames games of handsPerGame hands, and print results"""
    games = []
    #Play numGames games
    for t in xrange(numGames):
        c = CrapsGame()
        for i in xrange(handsPerGame):
            c.playHand()
        games.append(c)
    #Produce statistics for each game
    pROIPerGame, dpROIPerGame = [], []
    for g in games:
        wins, losses = g.passResults()
        pROIPerGame.append((wins - losses)/float(handsPerGame))
        wins, losses, pushes = g.dpResults()
        dpROIPerGame.append((wins - losses)/float(handsPerGame))
        
    #Produce and print summary statistics
    meanROI = str(round((100.0*sum(pROIPerGame)/numGames), 4)) + '%'
    sigma = str(round(100.0*stdDev(pROIPerGame), 4)) + '%'
    print 'Pass:', 'Mean ROI =', meanROI, 'Std. Dev. =', sigma
    meanROI = str(round((100.0*sum(dpROIPerGame)/numGames), 4)) + '%'
    sigma = str(round(100.0*stdDev(dpROIPerGame), 4)) + '%'
    print 'Don\'t pass:','Mean ROI =', meanROI, 'Std Dev =', sigma
        