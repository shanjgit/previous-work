# 6.00.2x Problem Set 4

import numpy
import random
import pylab
#from ps3b import TreatedPatient
#from ps3b import ResistantVirus

from ps3b_precompiled_27 import TreatedPatient
from ps3b_precompiled_27 import ResistantVirus

def simulationWithDrug(numViruses, maxPop, maxBirthProb, clearProb, resistances,
                       mutProb, numTrials):
    """
    Runs simulations and plots graphs for problem 5.

    For each of numTrials trials, instantiates a patient, runs a simulation for
    150 timesteps, adds guttagonol, and runs the simulation for an additional
    150 timesteps.  At the end plots the average virus population size
    (for both the total virus population and the guttagonol-resistant virus
    population) as a function of time.

    numViruses: number of ResistantVirus to create for patient (an integer)
    maxPop: maximum virus population for patient (an integer)
    maxBirthProb: Maximum reproduction probability (a float between 0-1)        
    clearProb: maximum clearance probability (a float between 0-1)
    resistances: a dictionary of drugs that each ResistantVirus is resistant to
                 (e.g., {'guttagonol': False})
    mutProb: mutation probability for each ResistantVirus particle
             (a float between 0-1). 
    numTrials: number of simulation runs to execute (an integer)
    
    """

    
    
    popPerStep = [] # a list containing 300 elements; the i-th element 
    ResPerStep = [] #records the i-th step's virus and viruses with resistance population
    for i in range(300): 
        popPerStep.append(0.0)
    for i in range(300):
        ResPerStep.append(0.0)
    
    virList = [] # a list which contains all the ResistantVirus 
    for num in range(numViruses):
        a_virus = ResistantVirus(maxBirthProb, clearProb,resistances, mutProb)
        virList.append(a_virus)
    


    
    for trial in range(numTrials):     
        thePoorGuy =  TreatedPatient(virList,maxPop)
        for i in range(150):
            thePoorGuy.update()
            popPerStep[i] += thePoorGuy.getTotalPop()
            ResPerStep[i] += thePoorGuy.getResistPop( ['guttagonol'])    
        thePoorGuy.addPrescription('guttagonol') #add guttagonol 
        for j in range(150,300):
        
            thePoorGuy.update()
            popPerStep[j] += thePoorGuy.getTotalPop()
            ResPerStep[j] += thePoorGuy.getResistPop( ['guttagonol'])
    for i in range(300):
        popPerStep[i] = float(popPerStep[i])/numTrials
        ResPerStep[i] = float(ResPerStep[i])/numTrials
    
    pylab.plot(popPerStep,'ro', label = 'total viruses')
    pylab.plot(ResPerStep, 'bo',label = 'viruses with resistance to guttagonol')
    pylab.xlabel('Time Steps')
    pylab.ylabel('Average Viruses Population')
    pylab.title('Average Viruses Population over '+str(numTrials)+' trials')
    pylab.legend(loc = 'best')
    pylab.show()
#simulationWithDrug(numViruses=100, maxPop=1000, maxBirthProb=0.1, clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=100)
#
# PROBLEM 1
def simWithDrug_Delay_0(numViruses, maxPop, maxBirthProb, clearProb, 
resistances,mutProb, numTrials):
    """
    return a list containing the final population of each trail 
viruses, a list of 100 ResistantVirus instances

maxPop, maximum sustainable virus population = 1000

Each ResistantVirus instance in the viruses list should be initialized with the following parameters:

maxBirthProb, maximum reproduction probability for a virus particle = 0.1

clearProb, maximum clearance probability for a virus particle = 0.05

resistances, The virus's genetic resistance to drugs in the experiment = {'guttagonol': False}

mutProb, probability of a mutation in a virus particle's offspring = 0.005
    
    
    """
    finalPop = [] # a list containing 150 elements; the i-th element 
    #records the i-th trial's final population of viruses
    for i in range(numTrials): 
        finalPop.append(0.0)
    
    virList = [] # a list which contains all the ResistantVirus 
    for num in range(numViruses):
        a_virus = ResistantVirus(maxBirthProb, clearProb,resistances, mutProb)
        virList.append(a_virus)
        
    for trial in range(numTrials):     
        thePoorGuy =  TreatedPatient(virList,maxPop)
        thePoorGuy.addPrescription('guttagonol') #add guttagonol 
        for i in range(150):
            thePoorGuy.update()
            
        finalPop[trial] += thePoorGuy.getTotalPop()
        
    return finalPop[:]


#x = simWithDrug_Delay_0(numViruses=100, maxPop=1000, maxBirthProb=0.1,
#clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=1000)
#pylab.hist(x,bins=10)  
#pylab.title("delay 0 time step")
#pylab.xlabel('final population')
#pylab.ylabel('Occurence')
#pylab.show() 
def simWith2Drug_Delay_0(numViruses, maxPop, maxBirthProb, clearProb, 
resistances,mutProb, numTrials):
    """
    return a list containing the final population of each trail 
viruses, a list of 100 ResistantVirus instances

maxPop, maximum sustainable virus population = 1000

Each ResistantVirus instance in the viruses list should be initialized with the following parameters:

maxBirthProb, maximum reproduction probability for a virus particle = 0.1

clearProb, maximum clearance probability for a virus particle = 0.05

resistances, The virus's genetic resistance to drugs in the experiment = {'guttagonol': False}

mutProb, probability of a mutation in a virus particle's offspring = 0.005
    
    
    """
    finalPop = [] # a list containing 150 elements; the i-th element 
    #records the i-th trial's final population of viruses
    for i in range(numTrials): 
        finalPop.append(0.0)
    
    virList = [] # a list which contains all the ResistantVirus 
    for num in range(numViruses):
        a_virus = ResistantVirus(maxBirthProb, clearProb,resistances, mutProb)
        virList.append(a_virus)
        
    for trial in range(numTrials):     
        thePoorGuy =  TreatedPatient(virList,maxPop)
        for i in xrange(150):
            thePoorGuy.update()
        thePoorGuy.addPrescription('guttagonol') #add drugs to the patient
        thePoorGuy.addPrescription('grimpex')
        for i in xrange(150):
            thePoorGuy.update()
             
        finalPop[trial] += thePoorGuy.getTotalPop()
        
    return finalPop[:]
    
    
          
                       
                       

def simWithDrug_Delay_75(numViruses, maxPop, maxBirthProb, clearProb, resistances, mutProb, numTrials):
    finalPop = [] # a list containing 150 elements; the i-th element 
    #records the i-th trial's final population of viruses
    for i in range(numTrials): 
        finalPop.append(0.0)
    
    virList = [] # a list which contains all the ResistantVirus 
    for num in range(numViruses):
        a_virus = ResistantVirus(maxBirthProb, clearProb,resistances, mutProb)
        virList.append(a_virus)
        
    for trial in range(numTrials):     
        thePoorGuy =  TreatedPatient(virList,maxPop)
        
        for i in range(75):
            thePoorGuy.update()
        thePoorGuy.addPrescription('guttagonol') #add guttagonol 
        for j in range(75,225):
            thePoorGuy.update()         
        finalPop[trial] += thePoorGuy.getTotalPop()
        
    return finalPop[:]

def simWith2Drug_Delay_75(numViruses, maxPop, maxBirthProb, clearProb, resistances, mutProb, numTrials):
    finalPop = [] # a list containing 150 elements; the i-th element 
    #records the i-th trial's final population of viruses
    for i in range(numTrials): 
        finalPop.append(0.0)
    
    virList = [] # a list which contains all the ResistantVirus 
    for num in range(numViruses):
        a_virus = ResistantVirus(maxBirthProb, clearProb,resistances, mutProb)
        virList.append(a_virus)
        
    for trial in range(numTrials):     
        thePoorGuy =  TreatedPatient(virList,maxPop)
        for i in xrange(150):
            thePoorGuy.update()
        
        thePoorGuy.addPrescription('guttagonol') #add drugs 
    
        for i in xrange(75):
            thePoorGuy.update()
        thePoorGuy.addPrescription('grimpex') #add drugs
        for j in xrange(150):
            thePoorGuy.update()         
        finalPop[trial] += thePoorGuy.getTotalPop()
        
    return finalPop[:]
#x = simWithDrug_Delay_75(numViruses=100, maxPop=1000, maxBirthProb=0.1,
#clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=1000)
#pylab.hist(x,bins=10) 
#pylab.title("delay 75 time step")
#pylab.xlabel('final population')
#pylab.ylabel('Occurence') 
#pylab.show()     

def simWithDrug_Delay_150(numViruses, maxPop, maxBirthProb, clearProb, resistances,mutProb, numTrials):
    
    finalPop = [] # a list containing 150 elements; the i-th element 
    #records the i-th trial's final population of viruses
    
    for i in range(numTrials): 
        finalPop.append(0.0)
    
    virList = [] # a list which contains all the ResistantVirus 
    for num in range(numViruses):
        a_virus = ResistantVirus(maxBirthProb, clearProb,resistances, mutProb)
        virList.append(a_virus)
        
    for trial in range(numTrials):     
        thePoorGuy =  TreatedPatient(virList,maxPop)
        for i in range(150):
            thePoorGuy.update()
        thePoorGuy.addPrescription('guttagonol') #add guttagonol 
        for j in range(150,300):
            thePoorGuy.update()         
        finalPop[trial] += thePoorGuy.getTotalPop()
        
    return finalPop[:]
"""
x_100 = simWithDrug_Delay_150(numViruses=100, maxPop=1000, maxBirthProb=0.1,
clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=100)
x_1000 = simWithDrug_Delay_150(numViruses=150, maxPop=1000, maxBirthProb=0.1,
clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=100)
pylab.subplot(211)
pylab.hist(x_100,bins=10) 
pylab.title("initial viruses:100 delay 150 time step (100 trails)")
pylab.xlabel('final population')
pylab.ylabel('Occurence') 
pylab.subplot(212)
pylab.hist(x_1000,bins=10) 
pylab.title("initial viruses:150 delay 150 time step (100 trails)")
pylab.xlabel('final population')
pylab.ylabel('Occurence') 
pylab.show() """

'''x_1000P = simWithDrug_Delay_150(numViruses=100, maxPop=1000, maxBirthProb=0.1,
clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=1000)
x_4000P = simWithDrug_Delay_150(numViruses=100, maxPop=4000, maxBirthProb=0.1,
clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=1000)
pylab.figure(1)
pylab.subplot(211)
pylab.hist(x_1000P,bins=10)
pylab.title("maxPop: 1000 , delay time : 150  (1000 trails)")
pylab.xlabel('final population')
pylab.ylabel('Occurence') 

pylab.subplot(212)
pylab.hist(x_4000P,bins=10)
pylab.title("maxPop: 4000 , delay time : 150  (1000 trails)")
pylab.xlabel('final population')
pylab.ylabel('Occurence') 

x_1B = simWithDrug_Delay_150(numViruses=100, maxPop=1000, maxBirthProb=0.1,
clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=1000)

x_5B = simWithDrug_Delay_150(numViruses=100, maxPop=1000, maxBirthProb=0.5,
clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=1000)
pylab.figure(2)
pylab.subplot(211)
pylab.hist(x_1B,bins=10)
pylab.title("maxBirthProb=0.1 , delay time : 150  (1000 trails)")
pylab.xlabel('final population')
pylab.ylabel('Occurence') 

pylab.subplot(212)
pylab.hist(x_5B,bins=10)
pylab.title("maxBirthProb=0.5 , delay time : 150  (1000 trails)")
pylab.xlabel('final population')
pylab.ylabel('Occurence') 

x_05C = simWithDrug_Delay_150(numViruses=100, maxPop=1000, maxBirthProb=0.1,
clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=1000)

x_1C = simWithDrug_Delay_150(numViruses=100, maxPop=1000, maxBirthProb=0.1,
clearProb=0.1, resistances={'guttagonol': False}, mutProb=0.005, numTrials=1000)
pylab.figure(3)
pylab.subplot(211)
pylab.hist(x_05C,bins=10)
pylab.title("clearProb=0.05 , delay time : 150  (1000 trails)")
pylab.xlabel('final population')
pylab.ylabel('Occurence') 

pylab.subplot(212)
pylab.hist(x_1C,bins=10)
pylab.title("clearProb=0.1 , delay time : 150  (1000 trails)")
pylab.xlabel('final population')
pylab.ylabel('Occurence') 

x_F = simWithDrug_Delay_150(numViruses=100, maxPop=1000, maxBirthProb=0.1,
clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=1000)
x_T = simWithDrug_Delay_150(numViruses=100, maxPop=1000, maxBirthProb=0.1,
clearProb=0.05, resistances={'guttagonol': True}, mutProb=0.005, numTrials=1000)
pylab.figure(4)
pylab.subplot(211)
pylab.hist(x_F,bins=10)
pylab.title("Initializing each virus without resistance to guttagonol   , delay time : 150  (1000 trails)")
pylab.xlabel('final population')
pylab.ylabel('Occurence') 

pylab.subplot(212)
pylab.hist(x_T,bins=10)
pylab.title("Initializing each virus with resistance to guttagonol , delay time : 150  (1000 trails)")
pylab.xlabel('final population')
pylab.ylabel('Occurence') 

pylab.show()'''


def simWith2Drug_Delay_150(numViruses, maxPop, maxBirthProb, clearProb, resistances,mutProb, numTrials):
    
    finalPop = [] # a list containing 150 elements; the i-th element 
    #records the i-th trial's final population of viruses
    
    for i in range(numTrials): 
        finalPop.append(0.0)
    
    virList = [] # a list which contains all the ResistantVirus 
    for num in range(numViruses):
        a_virus = ResistantVirus(maxBirthProb, clearProb,resistances, mutProb)
        virList.append(a_virus)
        
    for trial in range(numTrials): 
        thePoorGuy =  TreatedPatient(virList,maxPop)    
        for i in xrange(150):
            thePoorGuy.update() 
        thePoorGuy.addPrescription('guttagonol') #add drugs 
        for i in xrange(150):
            thePoorGuy.update()
        thePoorGuy.addPrescription('grimpex') #add drugs
        for j in xrange(150):
            thePoorGuy.update()         
        finalPop[trial] += thePoorGuy.getTotalPop()
        
    return finalPop[:]

def simWithDrug_Delay_300(numViruses, maxPop, maxBirthProb, clearProb, resistances, mutProb, numTrials):
    finalPop = [] # a list containing 150 elements; the i-th element 
    #records the i-th trial's final population of viruses
    
    for i in range(numTrials): 
        finalPop.append(0.0)
    
    virList = [] # a list which contains all the ResistantVirus 
    for num in range(numViruses):
        a_virus = ResistantVirus(maxBirthProb, clearProb,resistances, mutProb)
        virList.append(a_virus)
        
    for trial in range(numTrials):     
        thePoorGuy =  TreatedPatient(virList,maxPop)
        for i in range(300):
            thePoorGuy.update()
        thePoorGuy.addPrescription('guttagonol') #add guttagonol 
        for j in range(300,450):
            thePoorGuy.update()         
        finalPop[trial] += thePoorGuy.getTotalPop()
        
    return finalPop[:]

def simWith2Drug_Delay_300(numViruses, maxPop, maxBirthProb, clearProb, resistances, mutProb, numTrials):
    finalPop = [] # a list containing 150 elements; the i-th element 
    #records the i-th trial's final population of viruses
    
    for i in range(numTrials): 
        finalPop.append(0.0)
    
    virList = [] # a list which contains all the ResistantVirus 
    for num in range(numViruses):
        a_virus = ResistantVirus(maxBirthProb, clearProb,resistances, mutProb)
        virList.append(a_virus)
        
    for trial in range(numTrials): 
        thePoorGuy =  TreatedPatient(virList,maxPop)    
        for i in xrange(150):
            thePoorGuy.update() 
        thePoorGuy.addPrescription('guttagonol') #add drugs 
        for i in xrange(300):
            thePoorGuy.update()
        thePoorGuy.addPrescription('grimpex') #add drugs
        for j in xrange(150):
            thePoorGuy.update()         
        finalPop[trial] += thePoorGuy.getTotalPop()  
              
    return finalPop[:]
#x_300 = simWithDrug_Delay_300(numViruses=100, maxPop=1000, maxBirthProb=0.1,
#clearProb=0.05, resistances={'guttagonol': False}, mutProb=0.005, numTrials=1000)
#pylab.hist(x_300,bins=10) 
#pylab.title("delay 300 time step (1000 trials)")
#pylab.xlabel('final population')
#pylab.ylabel('Occurence') 
#pylab.show()
#        
def simulationDelayedTreatment(numTrials):
    """
    Runs simulations and make histograms for problem 1.

    Runs numTrials simulations to show the relationship between delayed
    treatment and patient outcome using a histogram.

    Histograms of final total virus populations are displayed for delays of 300,
    150, 75, 0 timesteps (followed by an additional 150 timesteps of
    simulation).

    numTrials: number of simulation runs to execute (an integer)
    """
    resistances= {'guttagonol': False, 'grimpex': False}
    numViruses=100
    maxPop=1000
    maxBirthProb=0.1
    clearProb=0.05
    mutProb=0.005
    result_0 = simWithDrug_Delay_0(numViruses, maxPop, maxBirthProb, clearProb,
    resistances,mutProb, numTrials)
    
    result_75 = simWithDrug_Delay_75(numViruses, maxPop, maxBirthProb, clearProb,
    resistances,mutProb, numTrials)
    
    result_150 = simWithDrug_Delay_150(numViruses, maxPop, maxBirthProb, clearProb,
    resistances,mutProb, numTrials)
    
    result_300 = simWithDrug_Delay_300(numViruses, maxPop, maxBirthProb, clearProb,
    resistances,mutProb, numTrials)
    
    pylab.figure(1)    
    pylab.hist(result_0,bins=10)
    pylab.title("delay time : 0"+ " ("+str(numTrials)+" trails)")
    pylab.xlabel('Final Population of Virus')
    pylab.ylabel('Number of Occurence') 
    
    pylab.figure(2)  
    pylab.hist(result_75,bins=10)
    pylab.title("delay time : 75"+ " ("+str(numTrials)+" trails)")
    pylab.xlabel('Final Population of Virus')
    pylab.ylabel('Number of Occurence') 
    
    pylab.figure(3)  
    pylab.hist(result_150,bins=10)
    pylab.title("delay time : 150"+ " ("+str(numTrials)+" trails)")
    pylab.xlabel('Final Population of Virus')
    pylab.ylabel('Number of Occurence') 
    
    pylab.figure(4)  
    pylab.hist(result_300,bins=10)
    pylab.title("delay time : 300"+ " ("+str(numTrials)+" trails)")
    pylab.xlabel('Final Population of Virus')
    pylab.ylabel('Number of Occurence') 
    
    pylab.show()

    
    
    
    
    
    






#
# PROBLEM 2
#
def simulationTwoDrugsDelayedTreatment(numTrials):
    """
    Runs simulations and make histograms for problem 2.

    Runs numTrials simulations to show the relationship between administration
    of multiple drugs and patient outcome.

    Histograms of final total virus populations are displayed for lag times of
    300, 150, 75, 0 timesteps between adding drugs (followed by an additional
    150 timesteps of simulation).

    numTrials: number of simulation runs to execute (an integer)
    """
    resistances= {'guttagonol': False, 'grimpex': False}
    numViruses=100
    maxPop=1000
    maxBirthProb=0.1
    clearProb=0.05
    mutProb=0.005
    #result_0 = simWith2Drug_Delay_0(numViruses, maxPop, maxBirthProb, clearProb,
   # resistances,mutProb, numTrials)
    
    result_75 = simWith2Drug_Delay_75(numViruses, maxPop, maxBirthProb, clearProb,
    resistances,mutProb, numTrials)
    
    result_75_b = simWith2Drug_Delay_75(numViruses, maxPop, maxBirthProb, clearProb,
    resistances,0.1, numTrials)
    
   # result_150 = simWith2Drug_Delay_150(numViruses, maxPop, maxBirthProb, clearProb,
    #resistances,mutProb, numTrials)
    
   # result_300 = simWith2Drug_Delay_300(numViruses, maxPop, maxBirthProb, clearProb,
   # resistances,mutProb, numTrials)
    
    #pylab.figure(1)    
    #pylab.hist(result_0,bins=10)
    #pylab.title("delay time of 2nd drug : 0"+ " ("+str(numTrials)+" trails)")
    #pylab.xlabel('Final Population of Virus')
    #pylab.ylabel('Number of Occurence') 
    
    pylab.figure(2)
    pylab.subplot(211)  
    pylab.hist(result_75,bins=10)
    pylab.title("mutProb = 0.005 delay time of 2nd drug: 75"+ " ("+str(numTrials)+" trails)")
    pylab.xlabel('Final Population of Virus')
    pylab.ylabel('Number of Occurence') 
    pylab.subplot(212)  
    pylab.hist(result_75_b,bins=10)
    pylab.title("mutProb = 0.1 delay time of 2nd drug: 75"+ " ("+str(numTrials)+" trails)")
    pylab.xlabel('Final Population of Virus')
    pylab.ylabel('Number of Occurence')
    
    #pylab.figure(3)  
   # pylab.hist(result_150,bins=10)
    #pylab.title("delay time of 2nd drug : 150"+ " ("+str(numTrials)+" trails)")
   # pylab.xlabel('Final Population of Virus')
   #pylab.ylabel('Number of Occurence') 
    
   # pylab.figure(4)  
    #pylab.hist(result_300,bins=10)
    #pylab.title("delay time of 2nd drug : 300"+ " ("+str(numTrials)+" trails)")
   #pylab.xlabel('Final Population of Virus')
    #pylab.ylabel('Number of Occurence') 
    
    pylab.show()
