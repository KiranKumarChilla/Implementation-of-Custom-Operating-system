
//   NAME : CHILLA, KIRAN KUMAR
/*It has the main function. it calls loader and retrieves the current job from Scheduler and then calls CPU. If the Job is completed, 
it writes output to the Progressive File and deallocates PCB and Memory of that Job and calls loader
 * to load new Jobs If new Jobs are present in File. If all the jobs are executed, then it writes output to the outputStats file
 * it has all declared global variables */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class system {
    public static String MEM[] = new String[512], terminationnature = "normal", outputdata,
        warning = "no warning messages", line1;
    public static String JobInfoStrings[][] = new String[1000][30];
    public static String PCB[][] = new String[7][30];
    public static int length, subtraction = 0, CLOCK = 0, JobId = 0, IOtime = 0, 
    		Executiontime = 0, DataLength, OutputLength,TotalLength;
    public static int MeanRuntime, MeanExecutiontime, MeanIOtime, MeanJobTime, Runtime = 0;
    public static int StartAddress, TraceSwitch, check = 0, LoaderFormatError = 0, CPUIdleTime, Unusedwords, newfile = 0;
    public static int JobInfo[][] = new int[1000][50];
    public static int[] MBlockAvailability = new int[7];
    public static int linecount = 0, CurrentJob, timeLost = 0;
    public static int ReadyQueue[][] = new int[8][100], Max_BQ = 0, New = 0, 
    		EnterMem = 0, SnapSHotClock = 0;
    public static int MBlockBound[][] = new int[7][2];
    public static int Newjobs[] = new int[100], PCBJobId[] = new int[7];
    public static int InfJob = 0, Inf_LoopTime;
    public static int MaxRQ[] = new int[8], count, Id[] = new int[1000];
    public static int BlockedQueue[][] = new int[1000][2], MemoryFull = 0, 
    		NormalJobs = 0, AbnormalJobs = 0, JobsEmpty = 0;
    public static String InfiniteJobs, Registers[][] = new String[1000][10], 
    		             InputFilename;
    static ERRORHANDLER Error = new ERRORHANDLER();
    //Main class
    public static void main(String args[]) throws IOException {
        InputFilename = args[0];
        Loader loader = new Loader();
        // calling loader

        Memory Memry = new Memory();
        CPU cpu = new CPU();
        loader.LOADER(0, 0);
        //this loop executes till all the jobs are completed
        do {
            CurrentJob = Scheduler.ReadyQueues();
            if (CurrentJob == -1) {
                count++;

                Scheduler.TimeIncre(10);
                CPUIdleTime = CPUIdleTime + 10;
                CLOCK = CLOCK + 10;

                CurrentJob = Scheduler.ReadyQueues();

                //exit if all jobs are done

                if (CurrentJob == -1) {
                    CLOCK = CLOCK - 10;
                    Runtime = Executiontime + IOtime;
                    StatisticsOutput();
                    System.exit(0);
                }


            }


            //deallocate jobs with loader errors
            if (JobInfo[CurrentJob][19] == 1) {
                AbnormalJobs++;
                //deallocating if memory is allocated;
                if (JobInfo[CurrentJob][25] > 0) {
                    JobInfo[CurrentJob][32] = CLOCK;
                    JobInfoStrings[JobId][9] = Integer.toHexString(CLOCK);
                    JobInfo[CurrentJob][5] = JobInfo[CurrentJob][33] + JobInfo[CurrentJob][34];
                    timeLost = timeLost + JobInfo[CurrentJob][5];
                    writetofile();
                    int a = Integer.parseInt(JobInfoStrings[CurrentJob][0]);
                    MBlockAvailability[a] = 0;
                    DeallocatePCB(PCBJobId[a]);
                    MemoryFull = 0;
                    loader.LOADER(0, 0);
                } else {
                    loader.LOADER(0, 0);
                }

            } else {
                //executing no error jobs

                if (CurrentJob != -1) {
                    //periodic information display
                    if (CLOCK - SnapSHotClock > 500) {
                        SnapSHotClock = CLOCK;
                        SnapShotPrint();
                    }

                    cpu.CPU(0, 0);
                    JobInfo[CurrentJob][5] += JobInfo[CurrentJob][4];
                    Scheduler.TimeIncre(JobInfo[CurrentJob][4]);
                    //deallocate Memory for finished no eror jobs
                    if (JobInfo[CurrentJob][13] == 1) {

                        JobInfo[CurrentJob][32] = CLOCK;
                        JobInfoStrings[CurrentJob][9] = Integer.toHexString(CLOCK);
                        JobInfo[CurrentJob][5] = JobInfo[CurrentJob][33] + JobInfo[CurrentJob][34];
                        //progressivefile output for job
                        writetofile();
                        int a = Integer.parseInt(JobInfoStrings[CurrentJob][0]);
                        Unusedwords = Unusedwords + (MemoryManager.M_Blocksize(a + 1) - MemoryManager.M_Blocksize(a)) - JobInfo[CurrentJob][14];
                        //deallocate
                        DeallocatePCB(PCBJobId[a]);
                        MBlockAvailability[a] = 0;
                        DeallocateMain(a);
                        MemoryFull = 0;

                        loader.LOADER(0, 0);
                    }

                    //deallocate for CPU error jobs
                    else if (JobInfo[CurrentJob][20] > 0) {
                        if (CurrentJob == 103)
                            CurrentJob = 103;
                        timeLost = timeLost + JobInfo[CurrentJob][5];
                        AbnormalJobs++;
                        JobInfo[CurrentJob][32] = CLOCK;
                        JobInfoStrings[JobId][9] = Integer.toHexString(CLOCK);
                        JobInfo[CurrentJob][5] = JobInfo[CurrentJob][33] + JobInfo[CurrentJob][34];
                        writetofile();
                        int a = Integer.parseInt(JobInfoStrings[CurrentJob][0]);
                        Unusedwords = Unusedwords + (MemoryManager.M_Blocksize(a + 1) - MemoryManager.M_Blocksize(a)) - JobInfo[CurrentJob][14];
                        //deallocate
                        DeallocatePCB(PCBJobId[a]);
                        MBlockAvailability[a] = 0;
                        DeallocateMain(a);
                        MemoryFull = 0;

                        loader.LOADER(0, 0);
                    }
                }



            }

        } while (true);



    }

    //to print the periodic information
    public static void SnapShotPrint() throws IOException {
        int DegMulPro = 0;
        for (int i = 0; i < 7; i++) {
            if (MBlockAvailability[i] != 0)
                DegMulPro++;

        }
        writetoFile("\nDegree of Multi Programming is\t" + DegMulPro + "DECIMAL", "progress_file.txt");
        writetoFile("JobId of CurrentJob being executed is\t" + CurrentJob + "DECIMAL", "progress_file.txt");
        int enter1 = 0;
        for (int i = 0; i < 8; i++) {
            int j = 0;

            while (true) {
                if (ReadyQueue[i][j] == 0) {

                    break;
                } else {
                    if (enter1 == 0) {
                        writetoFile("JobIds of CurrentJobs in READYQUEUE are", "progress_file.txt");
                        enter1++;
                    }
                    writetoFile("\t\t" + ReadyQueue[i][j] + "DECIMAL", "progress_file.txt");
                }
                j++;
            }
        }
        if (New > 0) {
            if (enter1 == 0) {
                writetoFile("JobIds of CurrentJobs in READYQUEUE are", "progress_file.txt");
                enter1++;
            }
            for (int i = 0; i < New; i++) {
                writetoFile("\t\t" + Newjobs[i] + "DECIMAL", "progress_file.txt");
            }
        }
        int B_QueueCheck = 0;
        for (int i = 0; i < Max_BQ; i++) {
            B_QueueCheck++;
            if (i == 0)
                writetoFile("JobIds of CurrentJobs in BLOCKEDQUEUE are", "progress_file.txt");
            writetoFile("\t\t" + BlockedQueue[i][0] + "DECIMAL ", "progress_file.txt");
        }
        if (B_QueueCheck == 0)
            writetoFile("No Jobs in BLOCKEDQUEUE at this time", "progress_file.txt");

    }



    //deallocates Memory for finished jobs
    public static void DeallocateMain(int i1) {
        int i = i1;
        int begin = MemoryManager.M_Blocksize(i);
        int end = MemoryManager.M_Blocksize(i + 1);
        for (int p = begin; p < end; p++) {
            MEM[p] = "";
        }
    }


    //progressfile output
    public static void writetofile() throws IOException {
        writetoFile("\n" + "Cumulative Job Identification number : " + CurrentJob + "DECIMAL", "progress_file.txt");
        newfile++;
        writetoFile("the part of partition occupied by this program :(HEX)", "progress_file.txt");
        for (int i = JobInfo[CurrentJob][15]; i <= JobInfo[CurrentJob][16]; i++) {
            String Inp = null;
            if (i + 7 >= JobInfo[CurrentJob][16]) {
                Inp = String.format("%04x", i) + " ";
                while (i <= JobInfo[CurrentJob][16]) {
                    Inp = Inp + MEM[i++] + " ";
                }
            } else
                Inp = String.format("%04x", i) + " " + MEM[i++] + " " + MEM[i++] + " " + MEM[i++] + " " +
                MEM[i++] + " " + MEM[i++] + " " + MEM[i++] + " " + MEM[i++] + " " + MEM[i];
            writetoFile(Inp, "progress_file.txt");
        }
        //loader/CPU jobs
        if ((((JobInfo[CurrentJob][19]) > 0) || (JobInfo[CurrentJob][20] > 0)) || ((AbnormalJobs > 0 &JobInfo[CurrentJob][40] >0)))            
        		{
            //to print input data
            if (JobInfo[CurrentJob][39] > 0) {
                writetoFile("Input lines are:" + "(HEX)", "progress_file.txt");
                //input data
                for (int i = 0; i < JobInfo[CurrentJob][30]; i++) {
                    int IndexM = JobInfo[CurrentJob][31] + i;
                    writetoFile("\t" + "\t" + MEM[IndexM], "progress_file.txt");
                }
            }
            ////to print output data
            if (JobInfo[CurrentJob][40] > 0) {

                //outputdata
                for (int i = 0; i < JobInfo[CurrentJob][21]; i++) {
                    int IndexM = JobInfo[CurrentJob][6] + i;
                    if (MEM[IndexM].equals("")) {
                        break;
                    } else {
                        if (i == 0)
                            writetoFile("output lines are:" + "(HEX)", "progress_file.txt");

                        writetoFile("\t" + "\t" + MEM[IndexM], "progress_file.txt");
                    }
                }
            }



        } else { //noerrors
            writetoFile("Input lines are:" + "(HEX)", "progress_file.txt");
            //input data
            for (int i = 0; i < JobInfo[CurrentJob][30]; i++) {
                int IndexM = JobInfo[CurrentJob][31] + i;
                writetoFile("\t" + "\t" + MEM[IndexM], "progress_file.txt");
            }
            writetoFile("output lines are:" + "(HEX)", "progress_file.txt");
            //outputdata
            int OUTPUTS = 0;
            for (int i = 0; i < JobInfo[CurrentJob][21]; i++) {
                int IndexM = JobInfo[CurrentJob][6] + i;
                if (MEM[IndexM] == null || MEM[IndexM].equals("")) {
                    break;

                }
                writetoFile("\t" + "\t" + MEM[IndexM], "progress_file.txt");
            }

        }
        writetoFile("Partition number used by this job" + "\t" + 
                       JobInfo[CurrentJob][17] + "(DECIMAL)" + "\t", "progress_file.txt");
        writetoFile(" size occupied by this job" + "\t" + JobInfo[CurrentJob][14] 
        		                              + "Words" + "(DECIMAL)", "progress_file.txt");
        writetoFile("Time Job Entered the system\t" + JobInfoStrings[CurrentJob][8] 
        		                                              + "HEX", "progress_file.txt");
        writetoFile("Time Job is leaving the system\t" + JobInfoStrings[CurrentJob][9] 
        		                                         + "HEX", "progress_file.txt");
        writetoFile("Execution time\t" + JobInfo[CurrentJob][34] 
        		                                           + "DECIMAL", "progress_file.txt");
        writetoFile("Time spent doing I/O\t" + JobInfo[CurrentJob][33] 
        		                                           + "DECIMAL", "progress_file.txt");
        writetoFile("Total run time\t" + JobInfo[CurrentJob][5] + "DECIMAL", 
        		                                                     "progress_file.txt");
        //warnings display
        if (JobInfo[CurrentJob][36] > 0) {
            writetoFile("Warning:\t" + JobInfoStrings[CurrentJob][10],
            		                                        "progress_file.txt");
        }
        //errorsdisplay
        if (JobInfo[CurrentJob][18] > 0) {
            writetoFile("Error:\t" + JobInfoStrings[CurrentJob][11], "progress_file.txt");
        }


    }

    //Prints Statisticak file output to progressive file
    public static void StatisticsOutput() throws IOException {
        MeanRuntime = Runtime / JobId;
        MeanExecutiontime = Executiontime / JobId;
        MeanIOtime = IOtime / JobId;
        MeanJobTime = CLOCK / JobId;
        int InternalFragment = Unusedwords / JobId;
        writetoFile("\n" + "Statistics of this batch:", "progress_file.txt");
        writetoFile("Current value of CLOCK" + "\t" + Integer.toHexString(CLOCK) 
                                                         + "HEX", "progress_file.txt");
        writetoFile("mean user Job runtime" + "\t" + MeanRuntime 
        		                                      + "DECIMAL", "progress_file.txt");
        writetoFile("mean user Job Execution time" + "\t" 
        		                      + MeanExecutiontime + "DECIMAL", "progress_file.txt");
        writetoFile("meanuser Job IO time" + "\t" 
        		                              + MeanIOtime + "DECIMAL", "progress_file.txt");
        writetoFile("mean user job time in the system" + "\t" 
        		                                + MeanJobTime + "DECIMAL", "progress_file.txt");
        writetoFile("total CPU idle time" + "\t" + CPUIdleTime + "DECIMAL", "progress_file.txt");
        writetoFile("total time lost due to partial processing of abnormally terminated jobs\t" 
                                            + timeLost + "DECIMAL", "progress_file.txt");
        writetoFile("number of jobs that terminated normally" + "\t"
                                                      + NormalJobs + "DECIMAL", "progress_file.txt");
        writetoFile("number of jobs that terminated abnormally" + "\t" 
                                                       + AbnormalJobs + "DECIMAL", "progress_file.txt");
        writetoFile("time lost due to partial processing of jobs containing "
        		       + "suspected infinite loops" + "\t" + Inf_LoopTime +"DECIMAL", "progress_file.txt");
        writetoFile("ID of jobs considered infinite " + "(HEX)" + InfiniteJobs, "progress_file.txt");
        writetoFile("mean internal fragmentation" + "\t" + InternalFragment + "DECIMAL", "progress_file.txt");
    }


    // Hexadecimal to binary conversion
    public static String HexBin(String S) {
            S = padzeros(S, 3);

            String s1 = S.substring(0, 1);
            String s2 = S.substring(1, 2);
            String s3 = S.substring(2, 3);
            int num1 = Integer.parseInt(s1, 16);
            int num2 = Integer.parseInt(s2, 16);
            int num3 = Integer.parseInt(s3, 16);
            String Binvalue1 = String.format("%4s", 
            		Integer.toBinaryString(num1)).replace(' ', '0');
            String Binvalue2 = String.format("%4s", 
            		Integer.toBinaryString(num2)).replace(' ', '0');
            String Binvalue3 = String.format("%4s", 
            		Integer.toBinaryString(num3)).replace(' ', '0');
            String Binvalue = Binvalue1 + Binvalue2 + Binvalue3;
            return Binvalue;

        }
        //paddingg zeros to hex string
    public static String padzeros(String s, int l) {
        int len1 = l;
        String s1 = s;
        if (s == null) {
            s1 = "0";
        }

        int len = s1.length();
        for (int i = len; i < len1; i++) {
            s1 = "0" + s1;
        }
        return s1;
    }

    // binary to hexa decimal conversion
    public static String BinHex(String Bin) {
        int Decimalvalue;
        String Bin1[] = new String[3];
        Bin1[0] = Bin.substring(0, 4);
        Bin1[1] = Bin.substring(4, 8);
        Bin1[2] = Bin.substring(8, 12);
        String hexnumber, Hex1[] = new String[3];

        for (int i = 0; i < 3; i++) {
            Decimalvalue = Integer.parseInt(Bin1[i], 2);
            Hex1[i] = Integer.toString(Decimalvalue, 16);

        }

        hexnumber = Hex1[0] + Hex1[1] + Hex1[2];

        return hexnumber;
    }

    // calculating power of a number
    public static int power(int num, int pow) {
        int tempa = num, tempb = pow;
        for (int i = 0; i < tempb; i++) {
            tempa = tempa * 2;
        }
        if (tempb == 0) {
            tempa = 1;
        }
        return tempa;
    }

    // addition of two binary numbers
    public static String BinAdd(String S, int x) {
        int num0 = Integer.parseInt(HexBin(S), 2);
        int num1 = x;
        int sum = num0 + num1;
        String a = Integer.toBinaryString(sum);
        return a;

    }

    // addition for two hexadecimal numbers or for hexadecimal and integer
    public static int Addition(String p, String In, int A1) {
        int Y;
        // if()
        int X = twosComplement(p);
        if (In.equals("-1")) {
            Y = A1;
        } else {
            Y = twosComplement(In);
        }
        int Z = X + Y;
        return Z;

    }

    // calculating two's Compliment
    public static int twosComplement(String bin) {
        if (bin.charAt(0) == '1') {
            String InvertBin = Invert(bin);
            int value = Integer.parseInt(InvertBin, 2);
            value = -(value + 1);
            return value;

        } else {
            return Integer.parseInt(bin, 2);
        }
    }

    // Invert Binary for two's compliment
    public static String Invert(String bin) {
        String Binary = bin;
        Binary = Binary.replace("0", " ");
        Binary = Binary.replaceAll("1", "0");
        Binary = Binary.replace(" ", "1");
        return Binary;
    }

    // rotatebits towards right or left
    public static String Bitswap(String s, int r, int a) {
        String b = null;
        int Wordlength = s.length();
        s = s + s;
        //rightshift
        if (r == 1) {

            b = s.substring(Wordlength - a, 2 * Wordlength - a);

        }
        //leftshift
        else {
            b = s.substring(a, a + Wordlength);

        }

        return b;
    }

    //deallocate PCB
    public static void DeallocatePCB(int Jid) {
        int J = Id[Jid];
        for (int i = 0; i < 20; i++) {
            PCB[J][i] = "000";
        }
    }


    // this function writes to the tracefile as per JobId
    public static void writetrace(String s, int Jid) throws IOException {
        String file = tfname(Jid);
        if (JobInfo[CurrentJob][27] == 0) {
            FileWriter f = new FileWriter(file, false);
            JobInfo[CurrentJob][27]++;
            f.close();
        }

        FileWriter f = new FileWriter(file, true);

        f.write(s);
        f.write(System.lineSeparator());
        f.close();
    }



    //generation of dynamic filenames
    public static String tfname(int job) {
        String id = Integer.toString(job);
        id = "tracefile_job_" + id;
        return id;
    }

    // to write data to the outputfile
    public static void writetoFile(String s, String file) throws IOException {
        if (newfile == 0) {
            FileWriter f = new FileWriter(file, false);
            f.close();
        }
        FileWriter f = new FileWriter(file, true);
        f.write(s);
        f.write(System.lineSeparator());
        f.close();
    }

    // pad zeros to the input string
    public static String PadZeros(String p) {
        for (int i = p.length(); i < 3; i++) {
            p = "0" + p;
        }
        return p;
    }

    // ProcessControlBlock
    public static void ProcessManager(int jobi, int type) {
        int i = Id[jobi];
        if (type == 0) {
            // jobid
            PCB[i][0] = Integer.toHexString(jobi);
            PCB[i][1] = Integer.toHexString(JobInfo[i][15]);
            PCB[i][2] = Integer.toHexString(JobInfo[i][16]);
            PCB[i][3] = Integer.toHexString(JobInfo[i][3]);

        } else {
            PCB[i][4] = Integer.toHexString(JobInfo[i][4]);
            PCB[i][5] = Integer.toHexString(JobInfo[i][8]);
            PCB[i][6] = Integer.toHexString(JobInfo[i][34]);
        }

    }

    //integer to hex
    public static String InttoHex(int num) {
        int num1, sign;
        String numB, numH;
        int numI;
        if (num < 0) {
            num1 = Math.abs(num);
            numB = Integer.toBinaryString(num1);
            numB = padzeros(numB, 12);
            numB = Invert(numB);
            numI = Integer.parseInt(numB, 2);
            numI = numI + 1;
            numB = Integer.toBinaryString(numI);
            numB = padzeros(numB, 12);

        } else {
            num1 = num;
            numB = Integer.toBinaryString(num1);
            numB = padzeros(numB, 12);
        }

        numH = BinHex(numB);
        return numH;

    }


}
