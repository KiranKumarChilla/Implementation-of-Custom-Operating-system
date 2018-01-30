# Implementation-of-Custom-Operating-system
An Individual project where I designed a basic virtual operating system using Java which performs various arithmetic operations. Input jobs are loaded from file to Loader(drive).Few Jobs(as per available memory) are then loaded from Loader to Memory. since it is sequential , jobs are stored in ready queue and operations are performed in FIFO process.Ready queue is dynamically updated with new jobs when one job enters memory.  Memory is released as soon as the job finish its execution and new jobs are loaded to memory.


System.java module has the "main class"
Input.txt is the input file which can be passed as a command line argument; Input to the system will be in Hex format.
Output of the program will be in Progress_file
It also generates trace file for every input job if tracebit is set in the input. This trace file gives the more insights of how input is processed.
