# -*- coding: utf-8 -*-
"""
Created on Mon Oct 31 17:11:58 2016

@author: shan
"""

import sys
import peak
import trace
import algorithms
import json
import utils

################################################################################
################################ The Main Method ###############################
################################################################################

def loadProblem(file = "problem.py", variable = "problemMatrix"):
    """
    Loads a matrix from a python file, and constructs a PeakProblem from it.
    """

    namespace = dict()
    with open(file) as handle:
        exec(handle.read(), namespace)
    return peak.createProblem(namespace[variable])


