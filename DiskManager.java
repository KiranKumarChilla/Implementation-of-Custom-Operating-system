
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

// loading data from files to disk
	public class DiskManager extends system {
		
		
		
		
		public static void dmgr(){
		int buf[] = new int[4], Enterfin = 0;
		int loc, ki, intermlines;
		DiskFull = 0;
		enterdiskload++;
		LinesExecuted = 0;
		// File name
		String fileName = inp;

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.

			FileReader fileReader = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String sr = "";

			outerloop: while (((sr = bufferedReader.readLine()) != null) && (DiskFull == 0) && (Ent == 0)) {
				LinesExecuted++;
				intermlines = 0;// ||()

				// enter loop to check if DiskManager is invoked again
				if (EnterLoop == 1) {
					EnterLoop = 0;
					for (int j = 0; j < (TotalLines - 2); j++) {
						LinesExecuted++;

						intermlines++;

						sr = bufferedReader.readLine();
					}
					if ((sr = bufferedReader.readLine()) == null) {
						Ent = 1;

						{

							break outerloop;
						}

					}

				}
				
				else
	            {   //checks the input format	
					if(!sr.contains("**JOB"))
	              	{
	              		//pcb[job][25]=51;
	              		inpu="**JOB"+inpu;
	              	}
	            }
				
				
				String inpu = sr;
				String[] fr = inpu.split(" ");
//checks the input format
				/*if(!(fr[0].contains("**JOB")))
				{
					pcb[jobid+1][25]=51;
					//inpu="job"+inpu;
				}*/
			
					while (inpu.contains("**JOB")) {
						
					pcb[job][23] = CLOCK;
					// finding program no and pages
					//inputpages = Integer.parseInt(fr[1], 16);
					try{
						inputpages = Integer.parseInt(fr[1], 16);

						}
						catch(Exception ee)
						{pcb[jobid][25]=55;
							//inputpages=03;
						}

					inputpages++;
					int outpages;
					try{
					 outpages = Integer.parseInt(fr[2], 16);
					}
					catch(Exception ee)
					{  pcb[jobid][25]=56;
						outpages=04;
					}
					

					// output pages
					//int outpages = Integer.parseInt(fr[2], 16);

					totalpages = inputpages + outpages;

					jobid++;

					IpPages.put(jobid, inputpages);
					OpPages.put(jobid, outpages);

					for (int t = 0; (t < totalpages) & DiskFull == 0; t++) {
						int xy = pagetable();

						// it checks if there is no enough space in disk
						if (xy == 1000) {
							DiskFull = 1;
							for (int i = 0; i < t - 1; i++) {// nesExecuted=4;
								int r = pagenames[jobid][i];
								PageTracker[r] = 0;
							}

							
							
							jobid--;
							break outerloop;
						} else {
							pagenames[jobid][t] = xy;

						}
					}
//adding the current job to the lists array
	
					jobslist.add(jobid);

					sr = bufferedReader.readLine();
					String loaderrecord = sr;
					LinesExecuted++;
					String[] gr = loaderrecord.split(" ");
					try{
					loc = Integer.parseInt(gr[0], 16);
					}
					catch(Exception ee)
					{
						pcb[jobid][25]=42;
						loc=0;
					}
					Locations.put(jobid, loc);
					// length of loader
					length = Integer.parseInt(gr[1], 16);
					LoaderLength.put(jobid, length);
					if((length)%16==0)
					{
						pcb[jobid][3]=(int) ((Math.ceil((double) length / (double)16)) + 1);
						
					}
					else
					{
						pcb[jobid][3]=(int) Math.ceil((double) length / 16);
					}
					//loading the input data from file to disk
					for (int i = 0, p = 0, c = 0; (i < (Math.ceil((double) length /(double) 16)) + 1) && c < 1; i++, p++) {
						int j = pagenames[jobid][p];
						loaderpages = i + 1;

						for (int x = 0; x < 4 & c < 1; x++) {
							sr = bufferedReader.readLine();
							inpu = sr;
							LinesExecuted++;
							
							if(inpu.contains("**JOB"))
			            	{
			            		pcb[jobid][25]=52;
			            		inpu=bufferedReader.readLine();
			            	}

							if (inpu.contains("**DATA")) {

								if (x == 0) {
									loaderpages = i;
									c++;
								}

								else {
									c++;
									i++;
								}
							} else {

								Datastore[j][x] = inpu;
								
								if(jobid==53)
								{
								//	System.out.println(Datastore[j][x]);
								}

							}
						}
					}

				}
				LoaderPages.put(jobid, loaderpages);
				pcb[jobid][10] = loaderpages;

				String ir = inpu;
				/*if(!ir.contains("**DATA"))
			       {
			    	   pcb[jobid][25]=53;
			    	   continue outerloop;
			       }*/
             
			loop5:	while (ir.contains("**DATA")) {
					int check = 1, i, l = 0, lko, p = 0;
					while (check != 0) {
						p++;
						if (p == 2) {
							ir = bufferedReader.readLine();
							if (ir.contains("**FIN")) {
								check = 0;
								Enterfin = 1;
							}
						}
						for (i = loaderpages; i < inputpages & (check != 0); i++) {
							p = 0;

							int j = pagenames[jobid][i];
							if (l == 0) {

							}

							for (int x = 0; x < 4 & check > 0; x++) {

								sr = bufferedReader.readLine();
								ir = sr;
								LinesExecuted++;
								if(ir==null)
								{pcb[jobid][25]=54;
									break loop5;
								}
								if (ir.contains("**FIN"))

								{
									check = 0;
									Enterfin = 1;

								}

								else {
									lko = pagenames[jobid][loaderpages];
									Datastore[j][x] = ir;

								}
							}
							
							/*if(check==0)
							{
								pcb[jobid][25]=54;
							}*/
				            }
						
//totaljobs++;			
						
						    
						
					}

				}

				if (Traceflag > 1) {
					pcb[jobid][25]=15;
				}
 
			}

			bufferedReader.close();
			TotalLines = LinesExecuted;

		}

		catch (FileNotFoundException ex1) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("ERRORHANDLER reading file '" + fileName + "'");

		}

	}
	}
