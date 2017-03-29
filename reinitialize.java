
public class reinitialize extends system {

	{

		for (int i = 0; i < 300; i++) {
			MEM[i] = 0;
		}

		// public static long[] A = new long[19];

		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				pagenames[i][j] = 0;
				MemoryPageNumbers[i][j] = 0;
				DataPageTracker[i][j] = 0;
			}
		}

		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				Datastore[i][j] = null;
			}
		}

		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 1000; j++) {
				CpuRegister[i][j] = 0;
			}
		}
		for (int i = 0; i < 1000; i++) {
			PageTracker[i] = 0;
			MemoryPageTracker[i] = 0;
		}

		for (int i = 0; i < 1000; i++) {
			for (int j = 0; j < 205; j++) {
				pcb[i][j] = 0;
			}
		}

		if (valuen == 5) {
			valuen = 5;
		}

		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < 500; j++) {
				turnsarray[i][j] = 0;
			}
		}

		for (int i = 0; i < 300; i++) {
			for (int j = 0; j < 300; j++) {
				pcarray[i][j] = 0;
			}
		}

		if (valueq == 40) {
			valueq = 40;
		}

		mtime.clear();
		cputimemap.clear();
		IpPages.clear();
		OpPages.clear();
		LoaderPages.clear();
		Locations.clear();
		LoaderLength.clear();

		// public static int[] MemoryPageTracker = new int[1000];
		// public static int[][] MemoryPageNumbers = new int[1000][1000];

		jobslist.clear();
		subqueue01.clear();
		blockedqueue.clear();
		Pcqueue.clear();
		subqueue02.clear();

		subqueue03.clear();
		subqueue04.clear();
		// public static int[][] DataPageTracker = new int[1000][1000];

		totaljobs = 0;
		// d = 0;
		r1 = 0;
		r2 = 0;
		r3 = 0;
		pom = 0;
		jp = 0;
		jobid = 0;
		loaderpages = 0;
		datapages = 0;
		EnterLoop = 0;
		TotalLines = 0;
		LinesExecuted = 0;
		MemoryFull = 0;
		testv = 0;

		row = 0;
		in = 0;
		enterdiskload = 0;
		Ent = 0;
		blockclock = 0;
		enterblock = 0;
		migvalues = 0;

	}
}
