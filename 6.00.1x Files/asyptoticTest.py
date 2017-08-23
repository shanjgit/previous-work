import numpy as np
import pylab
x= np.linspace(0.1, 10, 10) 
y = x*np.sin(1.0/x)
pylab.plot(x,y)
pylab.show()
