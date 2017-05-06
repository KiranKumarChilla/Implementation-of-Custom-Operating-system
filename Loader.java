
/*It is used to load information about user job from file. It uses buffer to transfer data to memory. It is called by the system to load new jobs when 
 * Memory is available. It follows FCFS for loading jobs*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Loader extends system {
    public static String[] linesplit = new String[4];


    public static void LOADER(int startAddress, int trace) throws IOException {
        String buffer[] = new String[4];
        int location = 0;

        // File name
        String fileName = InputFilename;
        String line = null;

        try {

            // FileReader reads text files in the default encoding.	 
            FileReader fileReader =
                new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedreader =
                new BufferedReader(fileReader);
            if (LoaderFormatError == 1) {

                ReadEnd(bufferedreader);
                line = bufferedreader.readLine();
                linecount++;
                LoaderFormatError = 0;
            } else
                PreviousLines(bufferedreader);
            //loop breaks if it reaches Endof File or if there is no Main memory available 
            loop: while (((line = bufferedreader.readLine()) != null) & MemoryFull == 0) {
                linecount++;
                JobId++;
                //time at which Job entered
                JobInfo[JobId][28] = CLOCK;
                JobInfoStrings[JobId][8] = Integer.toHexString(CLOCK);
                JobInfoStrings[JobId][4] = Integer.toHexString(JobId);
                String testline = line;
                //to check missing jobs
                if (line.length() < 9) {
                    Error.ERROR_HANDLER(52);
                    ReadEnd(bufferedreader);
                    Newjobs[New] = JobId;
                    New++;
                    line = bufferedreader.readLine();
                    linecount++;
                    continue loop;
                }
                linesplit = line.split(" ");

                //missing the ** JoB record
                if (!(linesplit[0].equals("**") & (linesplit[1].equals("JOB")))) {
                    Error.ERROR_HANDLER(52);
                    ReadEnd(bufferedreader);
                    Newjobs[New] = JobId;
                    New++;

                    line = bufferedreader.readLine();
                    linecount++;
                    continue loop;
                }

                //Data lines
                JobInfoStrings[JobId][2] = linesplit[2];
                int Datalength = Integer.parseInt(linesplit[2]);
                JobInfo[JobId][30] = Datalength;

                //outputlines
                JobInfoStrings[JobId][3] = linesplit[3];
                OutputLength = Integer.parseInt(linesplit[3], 16);
                JobInfo[JobId][21] = OutputLength;
                // converting Length in Int
                String Len = bufferedreader.readLine();
                linecount++;
                //checking if only **job and data are present
                if (Len.equals("** END")) {
                    Error.ERROR_HANDLER(58);
                    line = bufferedreader.readLine();
                    linecount++;

                    Newjobs[New] = JobId;
                    New++;
                    continue loop;
                }


                //missing loader format
                try {


                    length = Integer.parseInt(Len, 16);
                } catch (Exception e1) {
                    Error.ERROR_HANDLER(63);
                    ReadEnd(bufferedreader);
                    Newjobs[New] = JobId;
                    New++;
                    line = bufferedreader.readLine();
                    linecount++;
                    continue loop;

                }


                JobInfoStrings[JobId][7] = Len;
                JobInfo[JobId][12] = length;

                //allocating the memoryblock to job
                TotalLength = length + Datalength + OutputLength;
                //Program size too large
                if (TotalLength > 128) {
                    ReadEnd(bufferedreader);

                    //to check if there is no job 
                    if (bufferedreader.readLine().isEmpty()) {

                    } else {
                        if ((line1 = bufferedreader.readLine()).substring(0, 6).equals("** JOB")) {
                            //to check if it starts without **JOB
                        } else
                            line = bufferedreader.readLine();
                    }


                    Newjobs[New] = JobId;
                    New++;
                    if (Error.ERROR_HANDLER(60))
                        continue loop;
                }
                JobInfo[JobId][14] = TotalLength;
                //Location 
                location = MemoryManager.MemoryBlock(TotalLength);
                JobInfo[JobId][15] = location;

                //If there is no Memory available break the loop
                if (location == -1) {
                    JobsEmpty = 1;
                    JobId--;
                    linecount = linecount - 2;
                    MemoryFull = 1;
                    break loop;
                }
                //If sufficient memory space is available
                else {
                    JobsEmpty = 0;
                    Newjobs[New] = JobId;
                    New++;

                    int location1 = location;
                    //it means memory allocated
                    JobInfo[JobId][25]++;
                    int length1 = 4 * (length / 4);
                    int lastrowLength = length - length1;
                    for (int i = 0; i < length / 4; i++) {
                        line = bufferedreader.readLine();
                        linecount++;
                        //missing loader format
                        if (line.equals("** DATA")) {
                            Error.ERROR_HANDLER(57);
                            ReadEnd(bufferedreader);
                            line = bufferedreader.readLine();
                            linecount++;
                            JobInfo[JobId][16] = location;
                            continue loop;
                        }

                        buffer[0] = line.substring(0, 3);
                        buffer[1] = line.substring(3, 6);
                        buffer[2] = line.substring(6, 9);
                        buffer[3] = line.substring(9, 12);

                        for (int j = 0; j < 4; j++) {
                            MEM[location++] = buffer[j];
                            int templo = location - 1;

                        }
                    }


                    for (int i = 0; i < lastrowLength; i++) {
                        if (i == 0) {
                            line = bufferedreader.readLine();
                            linecount++;
                        }
                        buffer[0] = line.substring(3 * i, 3 * (i + 1));
                        MEM[location++] = buffer[0];
                    }


                    line = bufferedreader.readLine();
                    linecount++;
                    String lastRecord = line;
                    String[] lr = new String[2];


                    //StartAdress and Traceflag
                    try {
                        lr = lastRecord.split(" ");
                        JobInfoStrings[JobId][5] = lr[0];
                        JobInfo[JobId][10] = location1 + Integer.parseInt(lr[0], 16);
                        //fixed start adress
                        JobInfo[JobId][11] = location1;
                        //traceflag
                        JobInfoStrings[JobId][6] = lr[1];
                        JobInfo[JobId][24] = Integer.parseInt(lr[1]);

                        JobInfo[JobId][0] = location;


                        if ((JobInfo[JobId][24] != 0) & (JobInfo[JobId][24] != 1)) {
                            Error.ERROR_HANDLER(2);
                        }

                    } catch (Exception e) {
                        Error.ERROR_HANDLER(2);

                        JobInfo[JobId][19] = 1;

                    }


                    line = bufferedreader.readLine();
                    linecount++;

                    int datacheck = 0;
                    JobInfo[JobId][31] = location;
                    //reading data lines
                    if (line.equals("** DATA")) {
                        for (int i = 0; i < Datalength; i++) {

                            line = bufferedreader.readLine();
                            linecount++;
                            //missing Data format
                            if (line.equals("** END")) {
                                Error.ERROR_HANDLER(64);
                                line = bufferedreader.readLine();
                                linecount++;
                                JobInfo[JobId][16] = location;
                                continue loop;
                            }
                            JobInfo[JobId][39]++;
                            buffer[0] = line.substring(0, 3);

                            MEM[location++] = buffer[0];
                        }
                        line = bufferedreader.readLine();
                        linecount++;

                    }

                    //missing **DATA record
                    else {
                        Error.ERROR_HANDLER(4);
                        for (int i = 0; i < Datalength; i++) {

                            line = bufferedreader.readLine();
                            linecount++;
                            //missing Data format
                            if (line.equals("** END")) {
                                Error.ERROR_HANDLER(64);
                                line = bufferedreader.readLine();
                                linecount++;
                                JobInfo[JobId][16] = location;
                                continue loop;
                            }
                            JobInfo[JobId][39]++;
                            buffer[0] = line.substring(0, 3);

                            MEM[location++] = buffer[0];
                        }


                    }

                    //outputlocation
                    JobInfo[JobId][6] = location;
                    JobInfo[JobId][7] = location;



                    if ((line.equals("** END"))) {

                        line = bufferedreader.readLine();
                        linecount++;
                    } else
                        Error.ERROR_HANDLER(3);

                    //invalid tracebit

                }
                ProcessManager(JobId, 0);
                JobInfo[JobId][16] = JobInfo[JobId][15] + TotalLength - 1;
            }

            bufferedreader.close();

        } catch (FileNotFoundException f) {

        } catch (Exception e) {
            Error.ERROR_HANDLER(61);
            Newjobs[New] = JobId;
            New++;
            LoaderFormatError = 1;
            Loader.LOADER(0, 0);

        }

    }


    //returns memoryblock allocated for new job


    public static void PreviousLines(BufferedReader bufferedreader) throws IOException {
            int in ;
            for ( in = 0; in < linecount; in ++) {
                bufferedreader.readLine();

            }

        }
        //reads till the end of file if error occurs
    public static void ReadEnd(BufferedReader bufferedreader) throws IOException {
        line1 = null;
        while (!((line1 = bufferedreader.readLine()).equals("** END"))) {
            linecount++;
            if (line1.isEmpty()) {
                linecount--;
                break;
            }
        }
        linecount++;
    }


}