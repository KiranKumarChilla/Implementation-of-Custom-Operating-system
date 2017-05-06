/* It allocates memory location to jobs if available and keeps track of availability*/

public class MemoryManager extends system {

    public static int MemoryBlock(int i) {

        //allocating memorybounds initially
        if (EnterMem == 0) {
            EnterMem++;
            MBlockBound[0][0] = 0;
            MBlockBound[1][0] = 32;
            MBlockBound[2][0] = 64;
            MBlockBound[3][0] = 128;
            MBlockBound[4][0] = 192;
            MBlockBound[5][0] = 256;
            MBlockBound[6][0] = 384;
            MBlockBound[0][1] = 31;
            MBlockBound[1][1] = 63;
            MBlockBound[2][1] = 127;
            MBlockBound[3][1] = 191;
            MBlockBound[4][1] = 255;
            MBlockBound[5][1] = 383;
            MBlockBound[6][1] = 511;

        }




        int allotted = 0, startloc;
        if (i <= 32) { //Block 0
            if (MBlockAvailability[0] == 0) {
                JobInfoStrings[JobId][0] = "0";
                startloc = 0;
                MBlockAvailability[0]++;
                PCBJobId[0] = JobId;
                Id[JobId] = 0;
                return startloc;

            }
            //Block 1
            else if (MBlockAvailability[1] == 0) {
                JobInfoStrings[JobId][0] = "1";
                startloc = 32;
                PCBJobId[1] = JobId;
                Id[JobId] = 1;
                allotted++;
                MBlockAvailability[1]++;
                return startloc;
            }
        }
        if ((i <= 64) & allotted == 0) { //Block 2
            if (MBlockAvailability[2] == 0) {
                JobInfoStrings[JobId][0] = "2";
                startloc = 64;
                PCBJobId[2] = JobId;
                Id[JobId] = 2;
                allotted++;
                MBlockAvailability[2]++;
                return startloc;

            }
            //Block 3
            else if (MBlockAvailability[3] == 0) {
                JobInfoStrings[JobId][0] = "3";
                startloc = 128;
                PCBJobId[3] = JobId;
                Id[JobId] = 3;
                allotted++;
                MBlockAvailability[3]++;
                return startloc;

            }
            //Block 4
            else if (MBlockAvailability[4] == 0) {
                JobInfoStrings[JobId][0] = "4";
                startloc = 192;
                PCBJobId[4] = JobId;
                Id[JobId] = 4;
                allotted++;
                MBlockAvailability[4]++;
                return startloc;
            }


        }
        //Block 5
        if ((i <= 128) & allotted == 0) {
            if (MBlockAvailability[5] == 0) {
                JobInfoStrings[JobId][0] = "5";
                startloc = 256;
                PCBJobId[5] = JobId;
                Id[JobId] = 5;
                allotted++;
                MBlockAvailability[5]++;
                return startloc;
            }
            //Block 6
            else if (MBlockAvailability[6] == 0) {
                JobInfoStrings[JobId][0] = "6";
                startloc = 384;
                PCBJobId[6] = JobId;
                Id[JobId] = 6;
                allotted++;
                MBlockAvailability[6]++;
                return startloc;

            }

        }

        return -1;

    }

    //calculating block size by using Block Number
    public static int M_Blocksize(int i1) {
        int i = i1;
        if (i == 0) {
            JobInfo[JobId][17] = i;
            return 0;
        } else if (i == 1) {
            JobInfo[JobId][17] = i;
            return 32;
        } else if (i == 2) {
            JobInfo[JobId][17] = i;
            return 64;
        } else if (i == 3) {
            JobInfo[JobId][17] = i;
            return 128;
        } else if (i == 4) {
            JobInfo[JobId][17] = i;
            return 192;
        } else if (i == 5) {
            JobInfo[JobId][17] = i;
            return 256;
        } else if (i == 6) {
            JobInfo[JobId][17] = i;
            return 384;
        } else {
            //JobInfo[JobId][17]=7;
            return 512;
        }

    }

}