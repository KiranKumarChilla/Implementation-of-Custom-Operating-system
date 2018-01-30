# Implementation-of-Custom-Operating-system
An Individual project where I designed a basic virtual operating system using Java where jobs are loaded from file to disk(drive).Few Jobs(as per available memory) are then loaded from disk to Memory . since it is sequential , jobs are stored in ready queue and operations are performed in FIFO process.Ready queue is dynamically updated with new jobs when one job enters memory.  Memory is released as soon as the job finish its execution and new jobs are loaded to memory.
Input to the system will be in Hex format.

System.java module has the "main class"
Input.txt is the input file which can be passed as a command line argument
Output file will be Progress_file
