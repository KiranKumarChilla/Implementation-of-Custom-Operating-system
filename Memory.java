
import java.io.IOException;

/*It is used to access contents from Memory or to dump the contents*/
public class Memory extends CPU {
    public static String Memory(String X, int Y, String Z)  {

        switch (X) {

            case "READ":
                // to read data from memory
                try {

                    Z = MEM[Y];
                } catch (Exception e) {}
                return Z;
            case "WRITE":
                // to write data to memory
                MEM[Y] = Z;
                break;


                case "DUMP":
                    // dump the memory contenets when there is an error
                	try{
                    for (int i = 0; i < 255; i++) {

                       writetoFile(String.format("%04x", i) + " " + MEM[i++] + " " + MEM[i++] + " " + MEM[i++] + " " +
                            MEM[i++] + " " + MEM[i++] + " " + MEM[i++] + " " + MEM[i++] + " " + MEM[i],"progress_file.txt");
                    }
                	}
                    catch(Exception e)
                    {
                    	
                    }
                    

                }
        
        return Z;

    }
}