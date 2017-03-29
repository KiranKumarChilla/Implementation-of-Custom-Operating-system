
import java.io.IOException;

public class CPU extends system{
	
	
	
	public static int CPU(int X, int Y) {

		// A = new long[17];

		PC = X;
		
		int EA;
		if (pcb[job][19] == 0) {
			pcb[job][7] = CLOCK;
		}
		pcb[job][19]++;
		pom = CLOCK;
		//System.out.println(job);

		if ((pcb[job][6] == 1)&(pcb[job][81]==0)) {
try {pcb[job][81]++;
				
				String s=tfname(job);
				writetrace("\tPC\tINSTR\tA\tEA",s,job);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int poi = 0;
		while (true) {
			pcb[job][8]=0;
pcb[job][190]=0;
			int inst;
			poi++;

			int op, a, b, d, k;

			inst = (int) MEMORY("READ", PC, 0);
String hex = Integer.toHexString(inst);
if(job==1000)
System.out.println(hex);
			String pp = Integer.toBinaryString((int) inst);
			while (pp.length() < 32) {
				pp = "0" + pp;
			}
			int addr = Integer.parseInt(pp.substring(0, 1), 2);
			op = Integer.parseInt(pp.substring(1, 8), 2);
			a = Integer.parseInt(pp.substring(8, 12), 2);
			b = Integer.parseInt(pp.substring(12, 16), 2);
			d = Integer.parseInt(pp.substring(16), 2);
			// System.out.println("address daddr"+d);
			//d = cpt(d, job);
			/// Calculate Effective address

			EA = d;
int p=cpt(d,job);
			// with indirect addressing: EA = C(DADDR)
			if ((addr == 1) && b == 0x00) {
				EA = pcb[job][17] + (int) MEMORY("READ",p, 0);
				

			}
			// with index addressing : EA = C(INDEX) + DADDR
			if (b > 0x00 && addr == 0) {

				EA = pcb[job][17]+(int) (A[b] + p);
				//System.out.println(A[b]+"A[b]"+d+"d");
			}

			// with both index and indirect adressing
			if ((addr == 1) && b > 0x00) {

				op = Integer.parseInt(pp.substring(1, 8), 2);
				EA = pcb[job][17] + (int) (A[b] + (int) MEMORY("READ", p, 0));
			
			//System.out.println(pcb[job][17]+"pcb17"+A[b]+"A[b]"+MEMORY("READ", d, 0));
			}
EA=cpt(EA,job);
			
			if (pcb[job][6] == 1) {
try {

					
					String s=tfname(job);
					writetrace("B:\t" + String.format("%02x", PC) + "\t" + String.format("%08x", inst) + "\t"
							+ String.format("%02x", A[a]),s,job);
				} catch (IOException e) {
					e.printStackTrace();
				}
					
			}
			//Execute Instruction performs the operation based on opcode
			int exit = ExecuteInstruction(op, a, EA);

			if (pcb[job][6] == 1) {
try {
					
					String s=tfname(job);
					writetrace("A:\t\t\t" + String.format("%02x", A[a]) + "\t" + String.format("%02x", EA),s,job);
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			pcb[job][20] = pcb[job][20] + (CLOCK - pom);
			//System.out.println(pcb[job][72]);
			pcb[job][72]=pcb[job][72]+(CLOCK-pom);
			//System.out.println(pcb[job][72]+"after");
			if (exit == 0) {

				return 0;

			}

	if (exit==2) {
				enterblock++;
				turnsarray[job][3]++;
				if (enterblock == 1) {
					blockclock = CLOCK;
				}
				if(turnsarray[job][0]!=4)
		         {
		        	 migvalues++;
		         }
				
				if(job==1000)
				System.out.println("enteringblockqueue"+job);

				CpuRegisterBackup(job);
				if(turnsarray[job][3]>30)
				{
					
				}
				else
				blockedqueue.add(job);
				pcb[job][8] = 1;

	//System.out.println(PC+"before"+job);
	if(pcb[job][190]==1)
	{
		//System.out.println(PC+"branchenter");
	}
	else
	{PC=pct(PC,job);
	
	}		

				
				pcb[job][9] = (PC );
				cputimemap.put(job, 1);
				pcb[job][11] = readdata;
				pcb[job][12] = outdata;
				pcb[job][14] = in;
				return 0;
			}
			
			
			
			if ((CLOCK - pom) > 300) {
				if(job==1000)
				System.err.println(job+"JOBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
				return 0;
				
			}

			//if (exit == 2) 
			if(((pcb[job][72]) >(turnsarray[job][1]*valueq) )&(turnsarray[job][4]>9))
			{
				return 0;
			}
			
			if((pcb[job][72]) >(turnsarray[job][1]*valueq) ){
				// PRINT
pcb[job][72]=0;//
turnsarray[job][4]++;
turnsarray[job][2]++;
	if(((turnsarray[job][1]-valuen))==0)
     {if(turnsarray[job][0]!=1)
     {
    	 migvalues++;
     }
		 turnsarray[job][0]=1;
     subqueue01.add(job);
     }
			
     if(((turnsarray[job][1]-valuen))==2)
     {
    	 
    	 if(turnsarray[job][0]!=2)
         {
        	 migvalues++;
         }
    	 
    	 turnsarray[job][0]=2;
     	subqueue02.add(job);
     }
     if(((turnsarray[job][1]-valuen))==4)
     {
    	 if(turnsarray[job][0]!=3)
         {
        	 migvalues++;
         }
    	 
    	 turnsarray[job][0]=3;
     	subqueue03.add(job);
     }
     if(((turnsarray[job][1]-valuen))>=6)
     {
    	 if(turnsarray[job][0]!=4)
         {
        	 migvalues++;
         }
    	 
    	 
    	 
    	 turnsarray[job][0]=4;
     	subqueue04.add(job);
     }
	
	
	if(turnsarray[job][1]==turnsarray[job][2])
	{
		turnsarray[job][2]=0;
		turnsarray[job][1]=turnsarray[job][1]+2;
		
	}
//System.out.println("enteringsubqueue01"+job);
				CpuRegisterBackup(job);
				//subqueue01.add(job);

				pcb[job][8] = 1;
				//System.out.println(PC+"before"+job);
				if(pcb[job][190]==1)
				{
					//System.out.println(PC+"branchenter");
					
					
				}
				else
				{PC=pct(PC,job);
				//System.out.println(PC);
				}
				//PC=PC+1;
			//if(job==1000)
				
				
				
				pcb[job][9] = (PC);
				cputimemap.put(job, 1);
				pcb[job][11] = readdata;
				pcb[job][12] = outdata;
				pcb[job][14] = in;
				return 0;
			}


			//System.out.println(PC+"before"+job);
if(pcb[job][190]==1)
{//System.out.println(PC+"branchenter");
	
}
else
	{PC=pct(PC,job);
	//System.out.println(PC+"job"+job);
	}
			//if(job==1000)
			//PC=PC+1;
			
			
	
		}

	}

	public static int ExecuteInstruction(int inst, int a, int EffectiveAddress) {

		CLOCK++;
		int qa = 1;

		switch (inst) {

		case 0x00:
			// HALT
			return 0;

		case 0x01:
			// LOAD

			A[a] = MEMORY("READ", EffectiveAddress, 0);
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			break;

		case 0x02:
			// STORE
			MEMORY("WRIT", EffectiveAddress, (int) A[a]);
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			
			break;

		case 0x03:

			// ADD
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			A[a] = A[a] + MEMORY("READ", EffectiveAddress, 0);
			/*
			 * if(A[a] > 0xFFFFFFFFL){ // Invoke memory overflow ERRORHANDLER
			 * ERRORHANDLER(12); A[a] = A[a] - MEMORY("READ",EffectiveAddress,0); }
			 */
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			break;
			
		case 0x04:
			// SUBTRACT
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			A[a] = A[a] - MEMORY("READ", EffectiveAddress, 0);
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			if (A[a] < 0x00) {
						}
			break;
		case 0x05:
			// MULTIPLY
			CLOCK++;
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			A[a] = A[a] * MEMORY("READ", EffectiveAddress, 0);
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));

			if (A[a] > 0xffffffffL) {
				// Invoke memory overflow ERRORHANDLER
				// ERRORHANDLER(12);
				A[a] = A[a] / MEMORY("READ", EffectiveAddress, 0);
			}
			break;
		case 0x06:
			// DIVIDE
			CLOCK++;
			//if(job==1000)
				if(job==1000)
				System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			if (MEMORY("READ", EffectiveAddress, 0) == 0) {
				// Invoke divide by zero ERRORHANDLER

				
			}
			try {
				A[a] = A[a] / MEMORY("READ", EffectiveAddress, 0);
				if(job==1000)
				System.out.println("normaldivide+Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));

			} catch (Exception ee) {
				A[a] = A[a] * MEMORY("READ", EffectiveAddress, 0);
				if(job==1000)
				System.out.println("catch divide+Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));

				return (int) A[a];
			}
			//System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			
			/*
			 * 
			 * if(A[a] < 0x00){ // Invoke memory underflow ERRORHANDLER ?
			 * //System.out.println("addenter5"); ERRORHANDLER(12);
			 * 
			 * A[a] = A[a] * MEMORY("READ",EffectiveAddress,0);
			 */

			break;
		case 0x07:
			// Bitwise shift right
			int pa = 2147483647;
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			A[a] = (A[a] << (EffectiveAddress)) % (2147483647);
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			break;
		case 0x08:
			// Bitwise shift left
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			A[a] = (A[a] >> EffectiveAddress) % (2147483647);
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			break;
		case 0x09:
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			// BRANCH ON MINUS
			if (A[a] < 0) {
				pcb[job][190]=1;
				PC = EffectiveAddress;
			}if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			break;
		case 0x0A:
			// BRANCH ON PLUS
			/*pcb[job][100]++;
			if(pcb[job][100]==0)
			{
				PC=PC+6;
			}*/if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			if (A[a] > 0) {
				pcb[job][190]=1;
				PC = EffectiveAddress;
			}if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			break;
		case 0x0B:
			// BRANCH ON ZERO
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			if (A[a] == 0) {
				pcb[job][190]=1;
				PC = EffectiveAddress;
			}
			if(job==1000)
			{System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			int p=EffectiveAddress;
			
			System.out.println(MEM[p]);}
			break;
		case 0x0C:
			// BRANCH AND LINK
			CLOCK++;
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			A[a] = PC;
			pcb[job][190]=1;
			PC = EffectiveAddress;
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t");
			break;
		case 0x0D:
			// And bitwise
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			A[a] = A[a] & MEMORY("READ", EffectiveAddress, 0);
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			break;
		case 0x0E:
			// Bitwise or
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			A[a] = A[a] ^ MEMORY("READ", EffectiveAddress, 0);
			if(job==1000)
			System.out.println("Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
			break;

		case 0x0F:
			// READ
			CLOCK = CLOCK + 9;
			pcb[job][61]=pcb[job][61]+9;
			SYSTEMCLOCK = SYSTEMCLOCK + 8;
			int j = 0;
pcb[job][66]=pcb[job][66]+1;
if(pcb[job][66]==20)
{  //System.err.println("error read"+job);
	PC=PC+6;
	pcb[job][66]=0;
	break;
}

			long abc=MEMORY("WRIT", EffectiveAddress, (int) MemoryInput(0));
			readdata++;

			long abc2=MEMORY("WRIT", EffectiveAddress + 1, (int) MemoryInput(1));
			readdata++;
			long abc3=MEMORY("WRIT", EffectiveAddress + 2, (int) MemoryInput(2));
			readdata++;
			long abc4=MEMORY("WRIT", EffectiveAddress + 3, (int) MemoryInput(3));
			readdata++;
			if(job==1000)
System.out.println("\t"+"read"+"\t"+abc+"\t"+abc+"\t"+abc2+"\t"+abc3+"\t"+abc4);
			break;

		case 0x10:
			// Write
			CLOCK = CLOCK + 9;
			pcb[job][61]=pcb[job][61]+9;
			SYSTEMCLOCK = SYSTEMCLOCK + 8;

			try {

				if ((MEMORY("READ", EffectiveAddress, 0) < 0) || (MEMORY("READ", EffectiveAddress + 1, 0) < 0)
						|| (MEMORY("READ", EffectiveAddress + 2, 0) < 0)
						|| (MEMORY("READ", EffectiveAddress + 3, 0) < 0)) {
					String pin, pin1, pin2, pin3;
					long pina, pin1a, pin2a, pin3a;
					int i = pcb[job][18] / 4;
					int ka = pcb[job][18] % 4;
					int ji = pcb[job][0];
					int lo = pagenames[job][ji + i];
					String[] pi = new String[1000];
					//System.out.println("write"+"Registervalue"+A[a]+"\t"+"Effectiveadress"+EffectiveAddress+"\t"+ MEMORY("READ", EffectiveAddress, 0));
					pin = Long.toHexString(MEMORY("READ", EffectiveAddress, 0));
					pina = (long) compliment(pin);
					pi[0] = String.format("%08x", pina, 0);
					//System.out.println("write"+"Registervalue"+A[a]+"\t"+"Effectiveadress+1"+EffectiveAddress+1+"\t"+ MEMORY("READ", EffectiveAddress+1, 0));
					pin1 = Long.toHexString(MEMORY("READ", EffectiveAddress + 1, 0));
					pin1a = (long) compliment(pin1);
					pi[1] = String.format("%08x", pin1a, 0);
					//System.out.println("write"+"Registervalue"+A[a]+"\t"+"Effectiveadress+2"+EffectiveAddress+2+"\t"+ MEMORY("READ", EffectiveAddress+2, 0));
					pin2 = Long.toHexString(MEMORY("READ", EffectiveAddress + 2, 0));
					pin2a = (long) compliment(pin2);
					pi[2] = String.format("%08x", pin2a, 0);
					//System.out.println("write"+"Registervalue"+A[a]+"\t"+"Effectiveadress+3"+EffectiveAddress+3+"\t"+ MEMORY("READ", EffectiveAddress+3, 0));
					pin3 = Long.toHexString(MEMORY("READ", EffectiveAddress + 3, 0));
					pin3a = (long) compliment(pin3);
					pi[3] = String.format("%08x", pin3a, 0);
					Datastore[lo][ka] = pi[0] + pi[1] + pi[2] + pi[3];
					if(job==1000)
						System.out.println("write"+pi[0]+"\t"+pi[1]+"\t"+pi[2]+"\t"+pi[3]);
					//if(job==1000)
					{
					
						{
							//job=1;
						}
					}
					pcb[job][18] = pcb[job][18] + 1;
					if ((ka == 3) && lo < (pcb[job][0] + pcb[job][1] - 1)) {
						Datastore[lo + 1][0] = "over";
					}
					if (ka < 3) {
						Datastore[lo][ka + 1] = "over";
					}

				}

				else {

					int i = pcb[job][18] / 4;
					int ka = pcb[job][18] % 4;
					int ji = pcb[job][0];
					int lo = pagenames[job][ji + i];
					String[] pi = new String[1000];
					pi[0] = String.format("%08x", MEMORY("READ", EffectiveAddress, 0));
					pi[1] = String.format("%08x", MEMORY("READ", EffectiveAddress + 1, 0));
					pi[2] = String.format("%08x", MEMORY("READ", EffectiveAddress + 2, 0));
					pi[3] = String.format("%08x", MEMORY("READ", EffectiveAddress + 3, 0));
					if(job==1000)
					System.out.println("write"+pi[0]+"\t"+pi[1]+"\t"+pi[2]+"\t"+pi[3]);
					//if(job==1000)
					{
						if(pi[0]=="00000022")
						{
						//	job=1;
						}
					}
					Datastore[lo][ka] = pi[0] + pi[1] + pi[2] + pi[3];
					pcb[job][18] = pcb[job][18] + 1;
					if ((ka == 3) && lo < (pcb[job][0] + pcb[job][1] - 1)) {
						Datastore[lo + 1][0] = "over";
					}
					if (ka < 3) {
						Datastore[lo][ka + 1] = "over";
					}
				}

			}

			catch (Exception e1) {
				return 0;
			}

			break;

		case 0x11:
			// Dump Memory
			MEMORY("DUMP", 0, 0);
			break;

		default:
				}

		if ((inst == 0x0F) || inst == 0x10) {
			//qa = 2;
			qa=1;

			return qa;
		} else {
			qa = 1;
			return qa;
		}

	}

	
	
	
	
	
	
	
	
	
	
	
	
	

}
