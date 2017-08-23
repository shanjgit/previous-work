import random 
def noReplacementSimulation(numTrials):
    '''
    Runs numTrials trials of a Monte Carlo simulation
    of drawing 3 balls out of a bucket containing
    3 red and 3 green balls. Balls are not replaced once
    drawn. Returns the a decimal - the fraction of times 3 
    balls of the same color were drawn.
    '''
    yes = 0.0
    for trial in range(numTrials):
        balls = []
        bag = [0,0,0,1,1,1]        
        for i in range(3):
            ball = random.choice(bag)
            bag.remove(ball)            
            balls.append(ball)
        if balls == [0,0,0] or balls == [1,1,1]:
            yes += 1
    return yes/numTrials
        
            
        
