

/* CPU reads  instructions from memory of the current Job. It transfers the Job to Ready queue or blocked queue using Scheduler
 * If all instructions are executed the job will leave CPU  */
import java.io.IOException;
import java.util.Scanner;

public class CPU extends system {
    static String[] CPUreg = new String[10];
    private static int AbNormalJobs;

    public static void CPU(int startadres, int tracswitch) throws IOException {
        //retreiving CPU registers Data
        RetrieveReg();
        int A = 0;
        int EA = 0, Rindex = 0, traceheadings = 1, TEMPC2 = 0;
        int PC, OpcodeCount = 0, b = AbnormalJobs;
        String opcode, Instruct, Rbit;
        String MEMtest[] = new String[4096];
        PC = JobInfo[CurrentJob][10];
        EA = JobInfo[CurrentJob][22];
        TraceSwitch = JobInfo[CurrentJob][24];
        int loopEntered = 0;
        JobInfo[CurrentJob][4] = 0;
        JobInfo[CurrentJob][8] = 0;
        JobInfo[CurrentJob][9] = 0;
        count = JobInfo[CurrentJob][5];
        /*executes till it goes to blocked queue or ready queue 
         *loop breaks if all the instructions are executed*/
        Cpuloop: while (true) {

            CLOCK++;
            JobInfo[CurrentJob][34]++;
            Executiontime++;
            JobInfo[CurrentJob][4]++;
            //Clock time at which Job entered CPU
            if (JobInfo[CurrentJob][2] == 0) {
                JobInfoStrings[CurrentJob][8] = Integer.toHexString(CLOCK);
                JobInfo[CurrentJob][2]++;

            }
            // read instruction
            TEMPC2 = PC;
            Instruct = "null";
            try {
                Instruct = HexBin(MEM[PC]);
                //memory overflow
                if(MEM[PC].length()>3)
                {
               	 Error.ERROR_HANDLER(25);
               	 break;
                }

            } catch (Exception e) {
                if (Error.ERROR_HANDLER(27))
                    break;

            }

            opcode = Instruct.substring(1, 4);
            Rbit = Instruct.substring(4, 5);

            String Ins = Instruct.substring(6, 12);
            if (Rbit.equals("0")) {
                Rindex = 5;
            } else if (Rbit.equals("1")) {
                Rindex = 4;
            } else {
                Error.ERROR_HANDLER(52);
            }
            String RbeforeExec = CPUreg[Rindex];
            // Writing headings to file in traceflag
            if (TraceSwitch == 1 & JobInfo[CurrentJob][26] == 0) {
                JobInfo[CurrentJob][26] = 1;
                writetrace("PC" + "(HEX)" + "\t" + "Instruct" + "(HEX)" + "\t\t" + 
                "R-type:" + "\t"+"\t" + "EA" + "(HEX)" + "\t" + "Rbefore" + "(HEX)" + "\t" +
                "EA before" + "(HEX)" + "\t" + "R after" + "(HEX)" + "\t" + "EAafter" + "(HEX)", CurrentJob);
            }
            A = Addition(Ins, "-1", PC);
            if (opcode.equals("110")) {
                // TYPE II Instructions
                String r, w, h;
                OpcodeCount++;
                r = Instruct.substring(5, 6);
                w = Instruct.substring(6, 7);
                h = Instruct.substring(7, 8);

                if ((r.equals("1") & w.equals("1")) || (w.equals("1") & h.equals("1")) ||
                    (h.equals("1") & r.equals("1"))) {
                    //Invalid R,W,H bits
                    if (Error.ERROR_HANDLER(24)) {
                        break;
                    }
                }
                if (r.equals("1")) {
                    // readinput

                    try {
                        int dataloc = JobInfo[CurrentJob][0];
                        String Pa = MEM[dataloc];

                        JobInfo[CurrentJob][0]++;
                        String p = PadZeros(Pa);
                        JobInfo[CurrentJob][8]++;
                        if (Pa.length() < 3) {
                            Error.ERROR_HANDLER(81);
                        } else if (Pa.length() > 3) {
                            //illegalinput
                            Error.ERROR_HANDLER(27);
                            break;
                        }

                        Long InputCheck = Long.parseLong(p, 16);
                        CPUreg[Rindex] = p;
                        IOtime = IOtime + 10;
                        JobInfo[CurrentJob][33] = JobInfo[CurrentJob][33] + 10;
                    } catch (Exception en) {
                        // illegal input

                        Error.ERROR_HANDLER(27);
                        break;
                    }
                }
                //store output
                if (w.equals("1")) {
                    // output should be in hex
                    int outputLoc = JobInfo[CurrentJob][7];
                    JobInfo[CurrentJob][23]++;
                    //InsufficientOutputSpace
                    if (JobInfo[CurrentJob][23] > JobInfo[CurrentJob][21]) {
                        Error.ERROR_HANDLER(28);
                        break;
                    }
                    MEM[outputLoc] = CPUreg[Rindex];
                    JobInfo[CurrentJob][7]++;
                    JobInfo[CurrentJob][8]++;
                    outputdata = CPUreg[Rindex];
                    IOtime = IOtime + 10;
                    JobInfo[CurrentJob][33] = JobInfo[CurrentJob][33] + 10;
                    JobInfo[JobId][40]++;
                }
                if (h.equals("1")) {
                    // haltoperation
                    //last instruction of job
                    JobInfo[CurrentJob][13] = 1;
                    NormalJobs++;
                    break;
                }
                if (JobInfo[CurrentJob][24] == 1) {
                    writetrace(String.format("%03x", PC) + "\t" + "\t" + MEM[PC] + "\t" + "\t" 
                         + "R" + Rindex + "\t" + "\t" + "  "+ "\t" + "\t" +
                        RbeforeExec + "\t" + "\t" + "  " + "\t" + "\t" + CPUreg[Rindex] + "\t" + "\t" + " ", CurrentJob);
                }
            } else if (opcode.equals("111")) {
                int check3 = 0;
                if (Instruct.substring(0, 1).equals("0")) {
                    // Type III instruction
                    OpcodeCount++;
                    if (Instruct.substring(5, 6).equals("1")) {
                        // clear
                        CPUreg[Rindex] = "0";
                    }

                    if (Instruct.substring(6, 7).equals("1")) {
                        // increment	
                        CPUreg[Rindex] = InttoHex(Addition(HexBin(CPUreg[Rindex]), "-1", 1));
                    }
                    if (Instruct.substring(7, 8).equals("1")) {
                        String temp3 = HexBin(CPUreg[Rindex]);
                        // onesComplement
                        temp3 = temp3.replace("0", " ");
                        temp3 = temp3.replaceAll("1", "0");
                        temp3 = temp3.replace(" ", "1");
                        CPUreg[Rindex] = BinHex(temp3);

                    }
                    if (Instruct.substring(8, 9).equals("1")) { // 6bits
                        // rightshift
                        CPUreg[Rindex] = BinHex(Bitswap(HexBin(CPUreg[Rindex]), 1, 6));
                    }
                    if (Instruct.substring(11, 12).equals("0")) { // rotate one
                        // or
                        // twobits
                        check3 = -1;
                    }
                    if (Instruct.substring(9, 10).equals("1")) { // rotateleft
                        CPUreg[Rindex] = BinHex(Bitswap(HexBin(CPUreg[Rindex]), -1, 2 + check3));
                    }
                    if (Instruct.substring(10, 11).equals("1")) { // rotateright
                        CPUreg[Rindex] = BinHex(Bitswap(HexBin(CPUreg[Rindex]), 1, 2 + check3));
                    }
                    if (JobInfo[CurrentJob][24] == 1) {
                        writetrace(
                            String.format("%03x", PC) + "\t" + "\t" + MEM[PC] + "\t" + "\t" + "R" +
                                                           Rindex + "\t" + "\t" + " " +"\t" + "\t" +
                            RbeforeExec + "\t" + "\t " + "  " + "\t" + "\t" + CPUreg[Rindex] + "\t" + "\t" + " ", CurrentJob);
                    }
                } else { // TYPE IV instruction
                    OpcodeCount++;
                    String equal, less, greater;
                    equal = Instruct.substring(5, 6);
                    less = Instruct.substring(6, 7);
                    greater = Instruct.substring(7, 8);
                    int Rvalue = Addition(HexBin(CPUreg[Rindex]), "-1", 0);
                    if (equal.equals("0")) {
                        // equal bit
                        if (less.equals("0")) {
                            if (greater.equals("0")) {
                                // noskip i.e., executed normally
                            } else {
                                // greater
                                if (Rvalue > 0) {
                                    PC = PC + 1;
                                }
                            }
                        } else {
                            if (greater.equals("0")) {
                                // less
                                if (Rvalue < 0) {
                                    PC = PC + 1;
                                }
                            } else {
                                // notequal
                                if (Rvalue != 0) {
                                    PC = PC + 1;
                                }
                            }

                        }
                    } else {
                        if (less.equals("0")) {
                            if (greater.equals("0")) {
                                // equal
                                if (Rvalue == 0) {
                                    PC = PC + 1;
                                }
                            } else {
                                // greater or equal
                                if (Rvalue >= 0) {
                                    PC = PC + 1;
                                }
                            }
                        } else {
                            if (greater.equals("0")) {
                                // lessorequal
                                if (Rvalue <= 0) {
                                    PC = PC + 1;
                                }
                            } else { // unconditionalskip
                                PC = PC + 1;
                            }

                        }

                    }
                    if (JobInfo[CurrentJob][24] == 1) {
                        writetrace(String.format("%03x", PC) + "\t" + "\t" + MEM[PC], CurrentJob);
                    }
                }

            } else if (!(Integer.parseInt(Instruct, 2) == 0)) { // TYPE I
                // instruction
                OpcodeCount++;
                String I, X;
                I = Instruct.substring(0, 1);
                X = Instruct.substring(5, 6);

                if (I.equals("1")) {

                    if (X.equals("1")) {
                        // Indirect+Indexing
                        EA = 1 + Integer.parseInt(MEM[A], 16) + Integer.parseInt(CPUreg[4], 16);

                    } else {
                        try {
                            EA = 1 + Integer.parseInt(MEM[A + 1], 16);
                            if (EA < JobInfo[CurrentJob][15]) {
                                EA = EA-1 + JobInfo[CurrentJob][15];
                            }

                        } catch (Exception e) {
                            EA = A + 1;
                        }
                        // indirect
                    }
                } else if (I.equals("0")) {
                    if (X.equals("1")) {
                        // indexing
                        try {
                            EA = 1 + A + Integer.parseInt(CPUreg[4], 16);
                            if (EA < JobInfo[CurrentJob][15]) {
                                EA = EA-1+ JobInfo[CurrentJob][15];
                            }

                        } catch (Exception ee) {
                            EA = 1 + A;
                        }
                    } else {
                        // direct addressing
                        EA = A + 1;
                        if (EA < JobInfo[CurrentJob][15]) {
                            EA = EA + JobInfo[CurrentJob][15];
                        }

                    }
                } else { // invalid I,X bits
                    Error.ERROR_HANDLER(55);
                }
                // operations in type1 instructions
                if ((EA > 512)) {
                    //	Error.ERROR_HANDLER(28);
                }

                String EAContentbefore = MEM[0]; // MEM[EA];
                switch (opcode) {
                    // logical and
                    case "000":
                        int s1, s2;
                        try {
                            s1 = Integer.parseInt(CPUreg[Rindex], 16);
                            s2 = Integer.parseInt(Memory.Memory("READ", EA, "0"), 16);
                            //CHECK HERE
                            int temp = s1 & s2;


                            CPUreg[Rindex] = BinHex(String.format("%16s", Integer.toBinaryString(temp)).replace(' ', '0'));
                        } catch (Exception ee) {
                            JobInfo[CurrentJob][13] = 1;
                            NormalJobs++;

                            break Cpuloop;

                        }
                        break;

                    case "001":
                        // addition
                        String Z1 = Memory.Memory("READ", EA, "0");
                        CPUreg[Rindex] = InttoHex(Addition(HexBin(CPUreg[Rindex]), HexBin(Z1), 0));

                        break;
                    case "010":
                        if ((EA >= JobInfo[CurrentJob][15]) & (EA <= JobInfo[CurrentJob][16]))
                            Memory.Memory("WRITE", EA, CPUreg[Rindex]); // MEM[EA]=CPUreg[Rindex];
                        else {
                            Error.ERROR_HANDLER(23);
                            break Cpuloop;
                        }
                        break;

                    case "011":
                        // load
                        CPUreg[Rindex] = Memory.Memory("READ", EA, "0");;
                        break;
                    case "100":
                        // unconditional branching

                        PC = EA - 1;

                        break;
                    case "101":
                        // jumpand link
                        CPUreg[Rindex] = Integer.toHexString(PC);
                        PC = EA - 1;
                        break;
                }
                if (JobInfo[CurrentJob][24] == 1) {
                    writetrace(
                        String.format("%03x", TEMPC2) + "\t" + "\t" + MEM[TEMPC2] + "\t" + "\t" + "R" 
                                + Rindex + "\t" + "\t" +String.format("%03x", EA) + "\t" + "\t" +
                        RbeforeExec + "\t" + "\t" + EAContentbefore + "\t" + "\t" + CPUreg[Rindex] + "\t" + "\t" + MEM[EA],
                        CurrentJob);
                }

            }

            //more than 35virtual units

            if (JobInfo[CurrentJob][4] >= 35) {
                ReadyQueue[7][MaxRQ[7]] = CurrentJob;
                MaxRQ[7]++;
                PC++;
                break;
            }

            if (count > 600) {
                if (b == 0) {
                    JobInfo[CurrentJob][13] = 1;
                    NormalJobs++;
                    break;
                }
            }

            if (check()) {
                break;
            }

            //call scheduler
            if (JobInfo[CurrentJob][8] == 1) {
                BlockedQueue[Max_BQ][0] = CurrentJob;
                JobInfo[CurrentJob][8] = 0;
                Max_BQ++;
                PC++;
                break;
            } else {
                if (PC != -1) {}
                PC++;
                ProcessManager(CurrentJob, 1);
            }

        }
        //infiniteloop



        StoreReg();
        JobInfo[CurrentJob][22] = EA;

        //Stores the PC of a job for next time	  
        JobInfo[CurrentJob][10] = PC;


    }
    public static void RetrieveReg() {
            for (int i = 0; i < 10; i++) {
                CPUreg[i] = Registers[CurrentJob][i];
            }


        }
        // Cjhecking infinite loop
    public static boolean check() {
            if (JobInfo[CurrentJob][5] > 2000) {
                if (InfJob < 1)
                    InfiniteJobs = Integer.toHexString(CurrentJob);
                else
                    InfiniteJobs = InfiniteJobs + "\t" 
                                    + Integer.toHexString(CurrentJob);
                InfJob++;
                Inf_LoopTime = Inf_LoopTime + JobInfo[CurrentJob][5];
                Error.ERROR_HANDLER(29);
                return true;

            }
            return false;
        }
        //Storing CPU registers of the job  moving to Queues
    public static void StoreReg() {
        for (int i = 0; i < 10; i++) {
            Registers[CurrentJob][i] = CPUreg[i];
        }
    }

}