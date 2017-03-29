
public class MemoryLoader extends system {


	public  void MemoryLoader(int sA, int tS) {

		innerloop: while ((MemoryFull != 1) && !(jobslist.isEmpty())) {
			int lim = 0;

			long buf[] = new long[4];
			int loc, memlength = 0, enter = 0;
			testv = 0;
			int j = jobslist.get(0);

			int in, rn, M, che = 0;
			String[] br = new String[300];

			int inppages = IpPages.get(j);
			int oppages = OpPages.get(j);

			loc = Locations.get(j);
//System.out.println("pages allocated for job"+j);
if(j==23)
{
	j=23;
}
			int FLength = LoaderLength.get(j);
			int MPagesRequired = 1 + (int) Math.ceil((double) (FLength / 16));
			for (int i = 0; i < MPagesRequired; i++) {
				MemoryPageNumbers[j][i] = MemoryPagetable(j);
			/*MemoryPageNumbers[j][0]=1;
				MemoryPageNumbers[j][1]=4;
				MemoryPageNumbers[j][2]=7;
				MemoryPageNumbers[j][3]=11;*/
//System.out.println(MemoryPageNumbers[j][i]);
				if (MemoryPageNumbers[j][i] == 17) {
					enter = 1;
					jobsnum++;
					for (int k = 0; k < i; k++) {
						int p = MemoryPageNumbers[j][k];
						MemoryPageTracker[p] = 0;
					}
					break innerloop;
				}

			}

			 if(FLength>(256-memlength))
			{
             pcb[job][25]=45;
			}
			
			{int ind=jobslist.get(0);
				jobslist.remove(0);
				subqueue01.add(j);
				initializetime(j);
				turnsarray[j][1]=valuen;

				for (in = 0; in < (1 + Math.ceil((double) FLength / 16)) & che < 1; in++) {
					rn = pagenames[j][in];
					for (M = 0; M < 4 && che < 1; M++) {
						String line = Datastore[rn][M];
						if(ind==53)
						{
							//System.out.println(line);
						}
try{
						br = line.split(" ");

					if (Character.isWhitespace(line.charAt(2))) {
							che++;
						} else {
							if (line.length() < 32) {

								line = rightPadZeros(line, 32);

							}

							buf[0] = Long.parseLong(line.substring(0, 8), 16);
							buf[1] = Long.parseLong(line.substring(8, 16), 16);
							buf[2] = Long.parseLong(line.substring(16, 24), 16);

							buf[3] = Long.parseLong(line.substring(24, 32), 16);
							memlength = memlength + 4;
							// }
						}
						for (int po = 0; po < 4 && che < 1; po++) {

							loc = mpt(loc, j);
							MEM[loc++] = buf[po];

						}
					}catch(Exception ee)
					{
						pcb[j][25]=102;
					}
						
						
						
					}
					
				}

				try {
					StartAddress = Integer.parseInt(br[0], 16) + (MemoryPageNumbers[j][0]) * 16;
				} catch (Exception ee) {
					// StartAddress=mpt(StartAddress,j);
				}
				try{
				Traceflag = Integer.parseInt(br[1], 16);
				}
				catch(Exception ee)
				{
					pcb[j][25]=15;
				}
				pcb[j][5] = StartAddress;
				pcb[j][17] = StartAddress;
				pcb[j][6] = Traceflag;
				totaljobs++;
				processcontrolblock(j);

				if (Traceflag > 1) {
					ERRORHANDLER(15);
				}

			}
		}
	}



}
