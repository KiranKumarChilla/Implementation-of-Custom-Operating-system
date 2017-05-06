
/*It handles all the errors. The input to this will be error number and the appropriate error message will be returned*/
public class ERRORHANDLER extends system {

    public static boolean ERROR_HANDLER(int errornumber) {
        String Error_type[] = new String[100];
        int En = errornumber;
        // loader warnings
        if (errornumber < 20) {

            Error_type[2] = "bad traceflag";
            Error_type[3] = "Missing the **END record";
            Error_type[4] = "Missing ** DATA record";
            JobInfo[JobId][36]++;
            JobInfoStrings[JobId][10] = Error_type[errornumber];

        }

        //CPU errors
        if ((errornumber > 20) & (errornumber < 50)) {

            Error_type[22] = "Invalid Rbit";
            Error_type[23] = "Address out of bounds";
            Error_type[24] = "more than one of read, write and halt bits are set";
            Error_type[25] = "Insufficient output space";
            Error_type[25] = "Memory overflow";
            //for data
            Error_type[27] = "illegal input";
            //Error_type[28]="adress out of range";
            Error_type[28] = "Insufficient outputspace";
            Error_type[29] = "Suspected Infinite loop";
            JobInfo[CurrentJob][20] = 1;
            JobInfo[CurrentJob][18]++;
            JobInfoStrings[CurrentJob][11] = Error_type[errornumber];
            return true;
        }


        // Loader errors
        if (errornumber > 50) {
            Error_type[51] = "Memory overflow";
            Error_type[52] = "Missing ** JOB record";

            Error_type[55] = "Invalid loader format:";
            Error_type[56] = "Address out of range";
            Error_type[57] = "MissingLoaderformat";
            Error_type[58] = "NULLJOB";
            Error_type[59] = "address out of range";
            Error_type[60] = "programsizetoolarge";
            Error_type[61] = "invalid loader format";
            Error_type[62] = "missing dataitems";
            Error_type[63] = "Invalid loader length";
            Error_type[64] = "Missing Data";

            terminationnature = "abnormal";
            //Memory.Memory("DUMP", 0, "0");
            JobInfo[JobId][19] = 1;
            JobInfo[JobId][18]++;
            JobInfoStrings[JobId][11] = Error_type[errornumber];

            return true;
        }
        //cpu warnings
        if (errornumber > 80) {
            JobInfo[CurrentJob][36]++;
            JobInfoStrings[CurrentJob][10] = Error_type[errornumber];
            Error_type[81] = "Input hex numbers are not of size 3";

        }
        return false;
    }


}