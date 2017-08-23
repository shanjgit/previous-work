import random
import pylab

# Global Variables
MAXRABBITPOP = 1000
CURRENTRABBITPOP = 50
CURRENTFOXPOP = 300

def rabbitGrowth():
    """ 
    rabbitGrowth is called once at the beginning of each time step.

    It makes use of the global variables: CURRENTRABBITPOP and MAXRABBITPOP.

    The global variable CURRENTRABBITPOP is modified by this procedure.

    For each rabbit, based on the probabilities in the problem set write-up, 
      a new rabbit may be born.
    Nothing is returned.
    """
    # you need this line for modifying global variables
    global CURRENTRABBITPOP
    r = range(CURRENTRABBITPOP)
    for rabit in r:
        if random.random()<(1-CURRENTRABBITPOP/float(MAXRABBITPOP)):
            if CURRENTRABBITPOP < MAXRABBITPOP:
                CURRENTRABBITPOP += 1
        


            
def foxGrowth():
    """ 
    foxGrowth is called once at the end of each time step.

    It makes use of the global variables: CURRENTFOXPOP and CURRENTRABBITPOP,
        and both may be modified by this procedure.

    Each fox, based on the probabilities in the problem statement, may eat 
      one rabbit (but only if there are more than 10 rabbits).

    If it eats a rabbit, then with a 1/3 prob it gives birth to a new fox.

    If it does not eat a rabbit, then with a 1/10 prob it dies.

    Nothing is returned.
    """
    # you need these lines for modifying global variables
    global CURRENTRABBITPOP
    global CURRENTFOXPOP
    fox = range(CURRENTFOXPOP)
    for f in fox:
        
        if CURRENTFOXPOP < 10:
            continue

        if random.random() < CURRENTRABBITPOP/float(MAXRABBITPOP) and CURRENTRABBITPOP >10:
            CURRENTRABBITPOP -= 1
            if random.random() < 1.0/3:
                CURRENTFOXPOP += 1
        elif random.random() < 0.9:
            CURRENTFOXPOP -= 1
        
        
             
    
    
            
def runSimulation(numSteps):
    """
    Runs the simulation for `numSteps` time steps.

    Returns a tuple of two lists: (rabbit_populations, fox_populations)
      where rabbit_populations is a record of the rabbit population at the 
      END of each time step, and fox_populations is a record of the fox population
      at the END of each time step.

    Both lists should be `numSteps` items long.
    """

    rabit = [CURRENTRABBITPOP]
    fox = [CURRENTFOXPOP]
    for t in range(numSteps-1):
        rabbitGrowth()
        foxGrowth()
        rabit.append(CURRENTRABBITPOP)
        fox.append(CURRENTFOXPOP)
    return (rabit,fox)
        
(r,f) = runSimulation(200)   
x, y,z = pylab.polyfit(range(len(f)),f,2)
w,g,h =  pylab.polyfit(range(len(r)),r,2)


pylab.plot(r,'r-')
#pylab.plot(f,'g^') 
#pylab.plot(pylab.polyval((x,y,z), range(len(f))))
pylab.plot(pylab.polyval((w,g,h), range(len(r))))
  
pylab.show()