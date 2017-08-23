import random

def drawing_without_replacement_sim(numTrials):
    '''
    Runs numTrials trials of a Monte Carlo simulation
    of drawing 3 balls out of a bucket containing
    4 red and 4 green balls. Balls are not replaced once
    drawn. Returns a float - the fraction of times 3 
    balls of the same color were drawn in the first 3 draws.
    '''
    suc = 0.0
    for i in xrange(numTrials):
        box = [0,0,0,0,1,1,1,1]
        draw = []
        for j in xrange(3):
            x = random.choice(box)
            draw.append(x)
            box.remove(x)
        if (draw == [0,0,0] or draw == [1,1,1]):
            suc += 1
    return suc/float(numTrials)
            
        
        