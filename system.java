
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class system {

	// Global Variables
	public static int order = 0;
	public static long[] MEM = new long[300];
	public static int length;
	public static int StartAddress, Traceflag;
	public static long[] A = new long[19];
	public static int PC;
	public static int CLOCK = 0;
	public static int SYSTEMCLOCK;
	public static String inp, oup, inpu, oup1, oup2, oup3;
	public static int d = 0, adm;
	public static int r1 = 0;
	public static int r2 = 0;
	public static int r3 = 0;
	public static int nm, pom = 0;
	public static int jp = 0;
	public static int jobid = 0, job;
	public static int loaderpages = 0, inputpages, totalpages;
	public static int datapages = 0;
	public static int EnterLoop = 0;
	public static int TotalLines = 0;
	public static int[][] pagenames = new int[1000][1000];
	public static String[][] Datastore = new String[1000][1000];
	public static int[] PageTracker = new int[1000];
	public static HashMap<Integer, Integer> mtime = new HashMap<>();
	public static HashMap<Integer, Integer> cputimemap = new HashMap<>();
	public static String b;
	public static long[][] CpuRegister = new long[1000][1000];
	public static int job_C, totaljobs = 0;
	public static int[][] pcb = new int[1000][205];
	public static int DiskFull;
	public static int LinesExecuted = 0;
	public static HashMap<Integer, Integer> IpPages = new HashMap<>();
	public static HashMap<Integer, Integer> OpPages = new HashMap<>();
	public static HashMap<Integer, Integer> LoaderPages = new HashMap<>();
	public static HashMap<Integer, Integer> Locations = new HashMap<>();
	public static HashMap<Integer, Integer> LoaderLength = new HashMap<>();
	public static int MemoryFull = 0;
	public static int[] MemoryPageTracker = new int[1000];
	public static int[][] MemoryPageNumbers = new int[1000][1000];
	public static int testv = 0, jobsnum;
	public static ArrayList<Integer> jobslist = new ArrayList<Integer>();
	public static ArrayList<Integer> subqueue01 = new ArrayList<Integer>();
	public static ArrayList<Integer> blockedqueue = new ArrayList<Integer>();
	public static ArrayList<Integer> Pcqueue = new ArrayList<Integer>();
	public static ArrayList<Integer> subqueue02 = new ArrayList<Integer>();
	public static ArrayList<Integer> subqueue03 = new ArrayList<Integer>();
	public static ArrayList<Integer> subqueue04 = new ArrayList<Integer>();

	public static ArrayList<Integer> subqueue01size = new ArrayList<Integer>();
	public static ArrayList<Integer> subqueue02size = new ArrayList<Integer>();
	public static ArrayList<Integer> subqueue03size = new ArrayList<Integer>();
	public static ArrayList<Integer> subqueue04size = new ArrayList<Integer>();

	public static int[][] DataPageTracker = new int[1000][1000];
	public static int readdata, outdata, inpdata, row = 0, in = 0;
	public static int enterdiskload = 0, Ent = 0;
	public static int blockclock = 0, enterblock = 0;
	public static int[][] pcarray = new int[300][300];
	public static int[][] tracefiles = new int[500][500];
	public static int valuen = 3, valueq = 35;
	public static int migvalues = 0;
	public static int[][] queuescount = new int[500][500];
	public static int[][] turnsarray = new int[500][500];
	public static int queue1, queue2, queue3, queue4, queuesempty = 0, rows = 0, columns = 0;
	public static int e = 0;

	public static void write(String s, String file) throws IOException {
		if (d == 0) { // this is for file not appending after every job
			FileWriter f = new FileWriter(file, false);
			d++;
			f.close();
		}

		FileWriter f = new FileWriter(file, true);

		f.write(s);
		f.write(System.lineSeparator());
		f.close();
	}

	public static void fwrite(String s, String file) throws IOException {
		if (e == 0) { // this is for file not appending after every job

			FileWriter f = new FileWriter(file, false);
			e++;
			f.close();
		}

		FileWriter f = new FileWriter(file, true);

		f.write(s);
		f.write(System.lineSeparator());
		f.close();
	}

	public static void writetrace(String s, String file, int j) throws IOException {
		if (tracefiles[j][0] == 0) { // this is for file not appending after
										// every job
			FileWriter f = new FileWriter(file, false);
			tracefiles[j][0]++;
			f.close();
		}

		FileWriter f = new FileWriter(file, true);

		f.write(s);
		f.write(System.lineSeparator());
		f.close();
	}

	public static String tfname(int job) {
		String a = Integer.toString(job);
		a = "tracefile_" + a;
		return a;
	}

	// ProcessControlBlock
	public static void processcontrolblock(int jobi) { // inputpages
		pcb[jobi][0] = IpPages.get(jobi);
		// outputpages
		pcb[jobi][1] = OpPages.get(jobi);
		// location
		pcb[jobi][2] = LoaderPages.get(jobi);
		// length
		pcb[jobi][3] = Locations.get(jobi);
		// currentinst where gone
		pcb[jobi][4] = LoaderLength.get(jobi);
		pcb[jobi][8] = 0;
		// pcb[job][13]=0;
		pcb[job][19] = 0;
		pcb[job][20] = 0;
	}

	// keeps track of inputdata
	public static int DataPagetable() {
		int k = 0, check = 0;

		for (int i = pcb[job][10]; (i < (pcb[job][0]) & check < 1); i++) {
			if (DataPageTracker[job][i] == 0) {
				DataPageTracker[job][i] = 1;
				check = 1;
				k = i;
				return k;
			}
		}

		return k;
	}

	// returnsjobid in map
	public static int jimc(int jobid) {
		int p = jobid;
		return p;
	}

	// two'scompliment to handle negativenumbers
	public static int compliment(String qa) {
		while (qa.length() < 8) {
			qa = "0" + qa;
		}
		String p = qa;
		String[] bina = new String[10];
		for (int i = 0; i < 8; i++) {
			if ((bina[i] != "0") || (bina[i] != "1") || (bina[i] != "2") || (bina[i] != "3") || (bina[i] != "4")
					|| (bina[i] != "5") || (bina[i] != "6") || (bina[i] != "7") || (bina[i] != "8") || (bina[i] != "9")
					|| (bina[i] != "A") || (bina[i] != "B") || (bina[i] != "C") || (bina[i] != "D") || (bina[i] != "E")
					|| (bina[i] != "F")) {
				bina[i] = "0";
			}

		}
		if (p.contains("MN")) {
			p = "00000000";
		}
		bina[0] = Integer.toBinaryString(Integer.parseInt(p.substring(0, 1), 16));
		bina[1] = Integer.toBinaryString(Integer.parseInt(p.substring(1, 2), 16));
		bina[2] = Integer.toBinaryString(Integer.parseInt(p.substring(2, 3), 16));
		bina[3] = Integer.toBinaryString(Integer.parseInt(p.substring(3, 4), 16));
		bina[4] = Integer.toBinaryString(Integer.parseInt(p.substring(4, 5), 16));
		bina[5] = Integer.toBinaryString(Integer.parseInt(p.substring(5, 6), 16));
		bina[6] = Integer.toBinaryString(Integer.parseInt(p.substring(6, 7), 16));
		bina[7] = Integer.toBinaryString(Integer.parseInt(p.substring(7, 8), 16));
		for (int i = 0; i < 8; i++) {
			while (bina[i].length() < 4) {
				bina[i] = "0" + bina[i];
			}
		}

		String FinalNumber = bina[0] + bina[1] + bina[2] + bina[3] + bina[4] + bina[5] + bina[6] + bina[7];
		int a = convertInteger(FinalNumber);

		return a;
	}
	// public static long Compli(String q)

	public static int convertInteger(String bin) {
		while (bin.length() < 32) {
			bin = "0" + bin;
		}
		String num = bin.substring(1, 32);
		String sign = bin.substring(0, 1);
		int decimal = Integer.parseInt(num, 2);
		if (sign.equals("1")) {
			decimal = -2147483648 + decimal;
		}

		return decimal;

	}

	// function to retrieve inputdata if required from the disk
	public static long MemoryInput(int k) {
		if (job == 10) {
			job = 10;
		}

		String[] pin = new String[40];
		if ((readdata % 16) == 0) {
			inpdata = DataPagetable();
			in = 0;

		}
		if (in % 4 == 0) {
			row = (in / 4);
			in++;
		} else {
			in++;
		}

		int j = pagenames[job][inpdata];

		String load = Datastore[j][row];

		if (load != null) {
			while (load.length() < 32) {
				load = "0" + load;
			}
		}

		Long[] l = new Long[100];
		try {

			pin[0] = load.substring(0, 8);
			// System.out.println(pin[0]);
			l[0] = (long) compliment(pin[0]);
			// System.out.println(l[0]);
			pin[1] = load.substring(8, 16);
			l[1] = (long) compliment(pin[1]);
			pin[2] = load.substring(16, 24);
			l[2] = (long) compliment(pin[2]);
			pin[3] = load.substring(24, 32);
			l[3] = (long) compliment(pin[3]);
		} catch (Exception ee) {
			return 01;
		}
		return l[k];
	}

	// memory pagetable
	public static int pt(int loc) {
		int p = loc;
		int q = loc;

		p = (p / 16);
		q = (q - (p * 16));

		int[] num = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

		int s = p;

		nm = (((num[s]) * 16) + q);

		return nm;
	}

	// disk pagetable it allocates pages required to disk while loading jobs
	// from file
	public static int pagetable()

	{
		int ip = 0, y = 0, check = 0;

		for (ip = 0; y != 4 && DiskFull == 0 && ip < 256; ip++) {
			check++;

			if (PageTracker[ip] == 0) {
				PageTracker[ip] = 1;
				y = 4;

			}

		}
		if ((ip == 256) || check == 0) {
			jp = 1000;
			DiskFull = 1;

		} else {
			jp = ip - 1;
		}
		return jp;
	}

	// memory loader pagetable. it allocates pages in memory while retrieving
	// the jobs from disk
	public static int MemoryPagetable(int j) {
		int page = 17;
		for (int i = 0, check = 0; i < 16 && check == 0; i++) {

			if (MemoryPageTracker[i] == 0) {
				page = i;
				MemoryPageTracker[i] = 1;
				check = 1;
			}
		}
		if (page == 17) {
			return 17;
		} else {
			return page;
		}

	}

	// padding with zeros
	public static String rightPadZeros(String lin, int number) {
		return String.format("%1$-" + number + "s", lin).replace(' ', '0');
	}

	// intializing cpu clock time to all jobs loaded in to memory loader
	public static void initializetime(int j) {
		// for(int jp=0;jp<jobid+1;jp++)
		{
			cputimemap.put(j, -1);
		}
	}

	// to backup the contents of cpuregister if i/p or o/p requested
	public static void CpuRegisterBackup(int jiid) {
		int p = jiid;
		for (int i = 0; i < 17; i++) {
			CpuRegister[p][i] = A[i];
		}
	}

	// Virtualadress conversion

	public static int mpt(int k, int jo) {

		int i = (testv) / 16;
		if ((testv) % 16 == 0) {

			int a = (MemoryPageNumbers[jo][i]) * 16;
			if (testv == 0) {
				a = a + k;
				testv = testv + k;
			}
			testv++;
			return a;
		}

		else {
			testv++;
			int a = k;
			return a;
		}
	}

	// to clear all the allocated memory for a job when it is done
	public static void ClearMemory(int j) {
		int t = pcb[j][0] + pcb[j][1];

		for (int i = 0; i < t; i++) {
			int p = pagenames[j][i];
			PageTracker[p] = 0;
			MemoryFull = 0;
			DiskFull = 0;
		}
		EnterLoop = 1;
		int r = pcb[j][2];
		if (j == 23) {
			j = 23;
		}
		// System.out.println("jobs removed for"+j);
		for (int i = 0; i < r; i++) {
			int p = MemoryPageNumbers[j][i];
			MemoryPageTracker[p] = 0;
			// System.out.println(p);
		}

	}

	// virtual adress conversion while execution
	public static int cpt(int a, int j) {

		int i = (a) / 16;
		// to find the index of a particular page
		int p = (a - ((a / 16) * 16));

		int q;
		try {
			q = p + (MemoryPageNumbers[j][i] * 16);
		} catch (Exception ee) {
			return p + 2;
		}

		return q;
	}

	public static int pct(int pc, int job) {
		int l = 0, in, q = 0;
		// System.out.println(pcb[job][17]);
		pcarray[job][0] = (pcb[job][17] / 16);
		// System.out.println(pcarray[job][0]);
		if (((((pc + 1) % 16) == 0))) {
			int pn = ((pc + 1) / 16);
			for (int i = 0; (i < (15) & (l == 0)); i++) {
				if ((pn - 1) == pcarray[job][i]) {
					l = 1;
					q = (MemoryPageNumbers[job][i + 1] * 16);
					if (pcarray[job][i + 1] != MemoryPageNumbers[job][i + 1]) {
						pcarray[job][i + 1] = MemoryPageNumbers[job][i + 1];
					}
				}
			}

		} else {
			q = pc + 1;
		}
		return q;
	}

	public static void restore(int job) {
		for (int i = 0; i < 17; i++) {
			A[i] = CpuRegister[job][i];
			if (job == 1000) {
				// System.out.println(A[i]);
			}
		}
	}

	public static void makestore() {
		for (int i = 0; i < 16; i++) {
			A[i] = 0;
		}
	}

	// Main System Class
	public static void main(String args[]) throws IOException {

		oup = "progressfile.txt";
		oup1 = "queuescount.txt";
		oup2 = "MLFBQ.txt";

		for (valuen = 3; valuen < 6; valuen++) {
			columns = 0;

			System.err.println("enter q");
			for (valueq = 35; valueq < 55; valueq = valueq + 5) {
				System.err.println("enter q" + valuen + "valuen" + valueq + "valueq" + "\t");

				reinitialize clear = new reinitialize();

				DiskManager p = new DiskManager();
				MemoryLoader qol = new MemoryLoader();
				CPU cpu = new CPU();

				int ipoi = 0;
				// CLOCK = 0;
				SYSTEMCLOCK = 0;
				int enterbq = 0;
				length = 0;
				// MEM = new long[256];
				inp = args[0];

				int j1 = 0;
				int enterfirsttime = 1;
				turnsarray[job][1] = valuen;
				queuesempty = 0;
				// this loop executes when there are no jobs in ready
				// queue(i.e., all jobs available in disk are loaded and
				// executed)
				loop1: while (enterfirsttime == 1 || (!blockedqueue.isEmpty()) || (!subqueue01.isEmpty())
						|| (!subqueue02.isEmpty()) || (!subqueue03.isEmpty()) || (!subqueue04.isEmpty())) {
					enterbq = 0;
					queue1 = 0;
					queue2 = 0;
					queue3 = 0;
					queue4 = 0;
					if (enterfirsttime == 1) {
						p.dmgr();
						qol.MemoryLoader(StartAddress, Traceflag);
					}
					enterfirsttime++;

					if ((((CLOCK - blockclock) > 8) & !(blockedqueue.isEmpty()))
							|| ((queuesempty == 0) & !(blockedqueue.isEmpty()))) {
						enterbq++;
						blockclock = CLOCK;
						int qd = blockedqueue.get(0);
						if (turnsarray[qd][0] == 1)
							subqueue01.add(qd);
						if (turnsarray[qd][0] == 2)
							subqueue02.add(qd);
						if (turnsarray[qd][0] == 3)
							subqueue03.add(qd);
						if (turnsarray[qd][0] == 4)
							subqueue01.add(qd);
						blockedqueue.remove(0);
					}

					if (subqueue01.isEmpty()) {
						if (subqueue02.isEmpty()) {
							if (subqueue03.isEmpty()) {
								if (subqueue04.isEmpty()) {
									if (!blockedqueue.isEmpty()) {
										int qd = blockedqueue.get(0);
										if (turnsarray[qd][0] == 1) {
											subqueue01.add(qd);
										}
										if (turnsarray[qd][0] == 2) {
											subqueue02.add(qd);
										}
										if (turnsarray[qd][0] == 3) {
											subqueue03.add(qd);
										}
										if (turnsarray[qd][0] == 4) {
											subqueue01.add(qd);
										}
										blockedqueue.remove(0);
									} else
										queuesempty = 1;
								} else {
									queue4++;
									// System.out.println(subqueue04.get(0)+"subqueue04.get(0)");
									job = subqueue04.get(0);
									turnsarray[job][0] = 4;
								}
							} else {
								queue3++;
								// System.out.println(subqueue03.get(0)+"subqueue03.get(0)");
								job = subqueue03.get(0);
								turnsarray[job][0] = 3;
							}
						} else {
							queue2++;
							// System.out.println(subqueue02.get(0)+"subqueue02.get(0)");
							job = subqueue02.get(0);
							turnsarray[job][0] = 2;
						}
					} else {
						queue1++;
						// System.out.println(subqueue01.get(0)+"subqueue01.get(0)");
						job = subqueue01.get(0);
						turnsarray[job][0] = 1;
					}

					if (cputimemap.get(job) != -1) {
						readdata = pcb[job][11];
						outdata = pcb[job][12];
						in = pcb[job][14];
						restore(job);

						// Assigning start adress to current location;
						pcb[job][5] = pcb[job][9];
					} else {
						// making the contents of CPU Register to zero
						makestore();
						/*
						 * for (int i = 0; i < 16; i++) { A[i] = 0; }
						 */
						readdata = 0;
						outdata = 0;
						in = 0;

					}
					// if(job==1000)

					testv = 0;

					if ((CLOCK % 1200 == 0) & (CLOCK != 0)) {

						write("current jobs in subqueue01 : 0x", oup2);
						for (int i = 0; i < subqueue01.size(); i++) {
							write("0x" + String.format("%02x", subqueue01.get(i)), oup2);
						}
						write("current jobs in subqueue02 : 0x", oup2);
						for (int i = 0; i < subqueue02.size(); i++) {
							write("0x" + String.format("%02x", subqueue02.get(i)), oup2);
						}
						write("current jobs in subqueue03 : 0x", oup2);
						for (int i = 0; i < subqueue03.size(); i++) {
							write("0x" + String.format("%02x", subqueue03.get(i)), oup2);

						}
						write("current jobs in subqueue04 : 0x", oup2);
						for (int i = 0; i < subqueue04.size(); i++) {
							write("0x" + String.format("%02x", subqueue04.get(i)) + "\n", oup2);
						}
					}

					int a = cpu.CPU(pcb[job][5], pcb[job][6]);

					if (queue1 != 0) {
						subqueue01.remove(0);
					}

					if (queue2 != 0) {
						subqueue02.remove(0);
					}
					if (queue3 != 0) {
						subqueue03.remove(0);
					}
					if (queue4 != 0) {
						subqueue04.remove(0);
					}

					if (CLOCK % 500 == 0) {
						subqueue01size.add(subqueue01.size());
						subqueue02size.add(subqueue02.size());
						subqueue03size.add(subqueue03.size());
						subqueue04size.add(subqueue04.size());
					}

					// to print the information at regular intervals
					if (CLOCK % 50 == 0)

					{
						try {

							write("current job executing : 0x" + String.format("%02x", job), oup);
							write("current jobs in subqueue01 : 0x", oup);
							for (int i = 0; i < subqueue01.size(); i++) {
								write("0x" + String.format("%02x", subqueue01.get(i)), oup);
							}
							write("current jobs in blockedqueue : 0x", oup);
							for (int i = 0; i < blockedqueue.size(); i++) {
								write("0x" + String.format("%02x", blockedqueue.get(i)), oup);

							}

						} catch (IOException e) {

							e.printStackTrace();

						}
					}

					if (pcb[job][8] == 0) {
						// retrieving the data from the disk and
						// writing in to the output file
						try {
							// printing the job information after spooling
							write("jobid : 0x" + String.format("%02x", job), oup);
							write("CLOCK TIME AT WHICH JOB ENTERED : 0x" + String.format("%02x", pcb[job][7]), oup);

							write("CURRENT CLOCK TIME " + CLOCK, oup);
							pcb[job][63] = CLOCK - pcb[job][7];
							int ck = 0, enters = 0;
							for (int i = pcb[job][0]; i < (pcb[job][0] + pcb[job][1]) & ck == 0; i++) {
								int k = pagenames[job][i];
								for (int j = 0; j < 4 && ck == 0 && Datastore[k][j] != null; j++) {
									// enters++;
									if (Datastore[k][j] == "over") {
										ck = 1;
									} else {
										String[] inp = new String[100];
										while (Datastore[k][j].length() < 32) {
											Datastore[k][j] = "0" + Datastore[k][j];
										}
										inp[0] = Datastore[k][j].substring(0, 8);
										inp[1] = Datastore[k][j].substring(8, 16);
										inp[2] = Datastore[k][j].substring(16, 24);
										inp[3] = Datastore[k][j].substring(24, 32);
										write(" " + inp[0] + "\t\t" + inp[1] + "\t\t" + inp[2] + "\t\t" + inp[3], oup);

									}
								}
							}

							// printing the ERRORHANDLERs
							if (pcb[job][25] != 0) {
								ERRORHANDLER(pcb[job][25]);
							}
							if ((enters == 0) & pcb[job][25] == 0) {
								write("TERMINATION: NORMAL", oup);
							} else {
								write("TERMINATION: ABNORMAL", oup);
							}
							write("RUN TIME : " + String.format("%d", pcb[job][20]), oup);
							// write("CPU CLOCK : " + (CLOCK - SYSTEMCLOCK),
							// oup);

						} catch (IOException e) {

						}

						ClearMemory(job);
						p.dmgr();

						qol.MemoryLoader(StartAddress, Traceflag);

					} else {// it is executing again since it requested i/p or
							// o/p
						// pcb[job][8] = 0;

					}

				}

				// System.out.println(totaljobs);
				// for(int i=1;i<(totaljobs+1)
				int MeanIotime, total = 0, MeanRuntime, total2 = 0, MeantimeinSystem, total3 = 0, cpuidletime;
				for (int i = 1; i < totaljobs + 1; i++) {
					total = total + pcb[i][61];
					total2 = total2 + pcb[i][20];
					total3 = total3 + pcb[i][63];
				}
				MeanIotime = (total / totaljobs);
				MeanRuntime = (total2 / totaljobs);
				MeantimeinSystem = (total3 / totaljobs);
				cpuidletime = ((total3 - total2 - total));

				try {
					// printing the formatted report after the batch is
					// completed
					write("CURRRENT CLOCK VALUE " + String.format("%d", CLOCK), oup);
					// write("RUN TIME : " + String.format("%d",
					// pcb[job][20]),oup);
					write("MeanUSER JOB RUN TIME : " + String.format("%d", MeanRuntime), oup);
					write("Mean JOB I/O TIME : " + String.format("%d", MeanIotime), oup);
					write("MEANUSER JOBTIME IN SYSTEM : " + String.format("%d", MeantimeinSystem), oup);
					write("TOTAL CPU IDLE TIME : " + String.format("%d", cpuidletime), oup);
					write("RUN TIME : " + String.format("%d", pcb[job][20]), oup);
				} catch (IOException e) {
					e.printStackTrace();
				}
				queuescount[rows][columns] = migvalues;
				columns++;
				if (valuen == 5 & valueq == 50) {
					fwrite("\t\t" + "35\t" + "40\t" + "45\t" + "50\t\n", oup1);
					fwrite("3\t\t" + queuescount[0][0] + "\t" + queuescount[0][1] + "\t" + queuescount[0][2] + "\t"
							+ queuescount[0][3] + "\t", oup1);
					fwrite("4\t\t" + queuescount[1][0] + "\t" + queuescount[1][1] + "\t" + queuescount[1][2] + "\t"
							+ queuescount[1][3] + "\t", oup1);
					fwrite("5\t\t" + queuescount[2][0] + "\t" + queuescount[2][1] + "\t" + queuescount[2][2] + "\t"
							+ queuescount[2][3] + "\t", oup1);
					// fwrite("queuescount:"+migvalues,oup1);
				}

				System.out.println(totaljobs);
			}
			rows++;
		}
	}

	// it handles loading jobs from disk to Memory, retrieving new jobs to ready
	// queue from the disk

	// }

	// MEMORY, it performs the operations after receving the call from CPU
	public static long MEMORY(String X, int Y, long Z) {

		// Y=cpt(Y,job);

		if (Y > 0xFF || Y < 0x00) {
			// CAll ERRORHANDLER handler - memory out of range
			// ERRORHANDLER(31);
		}

		switch (X) {

		case "READ":
			try {
				Z = (int) MEM[Y];
			} catch (Exception ee) {
				return 0;
			}
			return Z;
		// break;

		case "WRIT":
			if (Z > 0xffffffffL) {
				ERRORHANDLER(32);
			}
			try {
				MEM[Y] = Z;
			} catch (Exception ee) {
				return 0;
			}
			break;

		case "DUMP":
			for (int i = 0x00; i < 255; i++) {
				// i=pt(i);
				try {
					if (i == 249)

						write(String.format("%04x", i) + "	" + String.format("%08x", MEM[i++]) + "	"
								+ String.format("%08x", MEM[i++]) + "	" + String.format("%08x", MEM[i++]) + "	"
								+ String.format("%08x", MEM[i++]) + "	" + String.format("%08x", MEM[i++]) + "	"
								+ String.format("%08x", MEM[i++]) + "	" + String.format("%08x", MEM[i]), oup);

					else
						write(String.format("%04x", i) + "	" + String.format("%08x", MEM[i++]) + "	"
								+ String.format("%08x", MEM[i++]) + "	" + String.format("%08x", MEM[i++]) + "	"
								+ String.format("%08x", MEM[i++]) + "	" + String.format("%08x", MEM[i++]) + "	"
								+ String.format("%08x", MEM[i++]) + "	" + String.format("%08x", MEM[i++]) + "	"
								+ String.format("%08x", MEM[i]), oup);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;

		default:
			break;

		}
		return 0;
	}

	// it executes the each instruction of a job and performs the required
	// operation

	// ERRORHANDLER HANDLER
	public static void ERRORHANDLER(int e) {

		String desc[] = new String[1000];
		desc[1] = "ExecutiontimeERRORHANDLERs";
		desc[2] = "Loadtime ERRORHANDLERs";
		desc[3] = "Memory ERRORHANDLERs";

		// CPU ERRORHANDLERS:
		desc[10] = "Invalid OP-code";
		desc[11] = "Invalid User Input";
		desc[12] = "Regester Overflow";
		desc[13] = "Attempt to divide by zero";
		desc[14] = "Regester address out of range";
		desc[15] = "Invalid Trace flag";
		desc[16] = "Suspect infinite job";

		// LOADER ERRORHANDLERS:

		desc[20] = "Suspect infinite job";
		desc[21] = "Programe size too large";
		desc[22] = "Invalid loder format";

		// MEMORY ERRORHANDLERS

		desc[30] = "Memory address fault";
		desc[31] = "Address out of range";
		desc[32] = "Invalid word size";
		desc[40] = "Invalidinputformat";
		desc[42] = "ERROR IN STARTING LOCATION";
		desc[45] = "loader length exceeded";
		desc[102] = "error in length";
		// warnings
		desc[51] = " incorrect input format(MISSING **JOB )";
		desc[52] = "incorrect input format(DOUBLE **JOB)";
		desc[53] = "incorrect input format(MISSING **DATA)";
		desc[54] = "incorrect input format(MISSING **FIN)";

		if (e < 50) // Fatal ERRORHANDLERs
		{

			try {
				write("Fatal " + desc[e / 10] + " ERRORHANDLER : " + String.format("%d", e), oup);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				write(" : " + desc[e], oup);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			return;

		} else // Warning
		{

			try {
				write("Warning code: " + String.format("%d", e), oup);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				write(" : " + desc[e], oup);
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			return;
		}
	}

}
