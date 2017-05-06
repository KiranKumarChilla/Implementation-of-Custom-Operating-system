
/*It is responsible for dispatching jobbs and maintaining ready queue and blocked queue*/
public class Scheduler extends system {



    public static int ReadyQueues() {
        //retrieving jobs from ready queue
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < MaxRQ[i]; j++) {
                int jId = ReadyQueue[i][j];

                for (int p = j; p < MaxRQ[i] - 1; p++) {
                    ReadyQueue[i][p] = ReadyQueue[i][p + 1];
                }
                ReadyQueue[i][MaxRQ[i] - 1] = 0;
                MaxRQ[i]--;

                return jId;

            }
        }
        if (New > 0) {
            int jobR = Newjobs[0];
            for (int j = 0; j < New - 1; j++) {
                Newjobs[j] = Newjobs[j + 1];
            }
            Newjobs[New - 1] = 0;
            New--;
            return jobR;
        }

        return -1;

    }

    //incrementing jobs in blocked queue
    public static void TimeIncre(int t) {
        for (int i = 0; i < Max_BQ; i++) {

            //incrementing clockfor job
            if (CurrentJob != BlockedQueue[i][0]) {
                BlockedQueue[i][1] += t;
                JobInfo[BlockedQueue[i][0]][5] += t;
                if ((BlockedQueue[i][1] >= 10) & (BlockedQueue[i][0] > 0)) {
                    BlockedQueue[i][1] = 0;
                    Rqueue(0, BlockedQueue[i][0]);

                    for (int j = i; j < Max_BQ - 1; j++) {
                        BlockedQueue[j][0] = BlockedQueue[j + 1][0];
                        BlockedQueue[j][1] = BlockedQueue[j + 1][1];
                    }
                    BlockedQueue[Max_BQ - 1][0] = 0;
                    Max_BQ--;
                    i = i - 1;
                }
            }
        }

    }

    //inserting new jobs to readyqueue from blocked queue
    public static void Rqueue(int type, int JId)

    { 
        if (type == 0) {
            if ((JobInfo[JId][4] > 30) & (JobInfo[JId][4] < 36)) {
                ReadyQueue[0][MaxRQ[0]] = JId;
                MaxRQ[0]++;
            } else if ((JobInfo[JId][4] > 25) & (JobInfo[JId][4] < 31)) {
                ReadyQueue[1][MaxRQ[1]] = JId;
                MaxRQ[1]++;
            } else if ((JobInfo[JId][4] > 20) & (JobInfo[JId][4] < 26)) {

                ReadyQueue[2][MaxRQ[2]] = JId;
                MaxRQ[2]++;
            } else if ((JobInfo[JId][4] > 15) & (JobInfo[JId][4] < 21)) {
                ReadyQueue[3][MaxRQ[3]] = JId;
                MaxRQ[3]++;
            } else if ((JobInfo[JId][4] > 10) & (JobInfo[JId][4] < 16)) {
                ReadyQueue[4][MaxRQ[4]] = JId;
                MaxRQ[4]++;
            } else if ((JobInfo[JId][4] > 5) & (JobInfo[JId][4] < 11)) {
                ReadyQueue[5][MaxRQ[5]] = JId;
                MaxRQ[5]++;
            } else if ((JobInfo[JId][4] > 0) & (JobInfo[JId][4] < 6)) {
                ReadyQueue[6][MaxRQ[6]] = JId;
                MaxRQ[6]++;
            }

        } else {
            ReadyQueue[7][MaxRQ[7]] = JId;
            MaxRQ[7]++;
        }


    }



}