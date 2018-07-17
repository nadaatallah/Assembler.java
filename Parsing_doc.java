	import java.io.BufferedReader;
	import java.io.BufferedWriter;
	import java.io.File;
	import java.io.FileReader;
	import java.io.FileWriter;
	import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Scanner;

	public class Parsing_doc {			

		public ArrayList<String> parsComSpace(ArrayList<String> instruction) {
			try {
				Scanner br= new Scanner(new File("mult_Parsing_example2.asm"));
				br.useDelimiter("\n");   
				//BufferedWriter brr = new BufferedWriter(new FileWriter("EditedFile.txt", true));
				
	        while (br.hasNext()) {
                String instr = br.next();
	        	int icom1 = instr.indexOf("//");  
	        	int icom2 = instr.indexOf("/*");   
	        	int icom3 = instr.indexOf("*/");   
	        	if (icom2 == -1) {   
	        	// String tempitem1 = instr.replaceAll("(?m)^\\s*\\r?\\n|\\r?\\n\\s*(?!.*\\r?\\n)", "");
	        	String tempitem1 = instr.replaceAll(" ", "");
	        	tempitem1 = tempitem1.trim();
	        	if ((icom1 == -1) && (tempitem1.isEmpty() == false)) {
	        	instruction.add(tempitem1);
	        	 //brr.append(tempitem1); 
	        	}
	        	}
	        	else { 
	        		while(icom3 == -1) {
	        			// String tempitem1 = instr.replace(instr, "");
	        			instr = br.next();
	                	icom3 = instr.indexOf("*/");                	
	                	}
	        		   if (icom3 != -1) {
	        			   instr = instr.substring(icom3+2,instr.length()-1);
	        			   if (!instr.trim().isEmpty()) {
	        				   if (!instr.trim().isEmpty()) {
	        				   instruction.add(instr.trim());   
	        				   }
	        			   }
	        		   }
	        		  }
	        	}
	        
	        // rmvSemColJoinInstr(instruction);
	        
	        // brr.close();
	        br.close();   
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally {
				
			}
			
 			return instruction;
		}

		// removes semi-colons and joins detached instructions together on the same line
        /* void rmvSemColJoinInstr(ArrayList<String> instruction){
	        String rem = "";
	       	ListIterator<String> i = instruction.listIterator();
            while(i.hasNext()){    	   // for (String s: instruction.data() ){ System.Out.println(s);}
            	String instr = i.next();   
            	int icom1 = instr.indexOf(";");
               	instr = rem.trim() + instr;
               	instr = instr.replace(";", "");
               	i.set(instr.trim());               	

            	if (icom1 == -1) {
            		rem = instr;
            		i.remove();
            		// instr1[j] = instr1[j].replaceAll(";", "");
            	}
            	else { 
            		rem = "";               	
            	}
              }
            } */

	}