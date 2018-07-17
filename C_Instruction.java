import java.awt.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class C_Instruction {
	private ArrayList<comp_symb> cmpList;
	private ArrayList<dest_symb> destList;
	private ArrayList<jump_symb> jmpList;
	
	public C_Instruction() {
	cmpList = new ArrayList<comp_symb>();
	destList = new ArrayList<dest_symb>();
	jmpList = new ArrayList<jump_symb>();
	}
	
	// it's more efficient to use Lists over arrayLists in this case to represent the symbols
	// as it's even way more efficient to use HashMaps<String, Integer> (speedwise)
	// extract the "assembly vs machine language" translation tables for C-instruction parts 
	// from csv files  and store them in lists
	private void getSymbolLists() {
		try {
		BufferedReader brc = new BufferedReader(new FileReader("comp_mnemonics.csv"));
		BufferedReader brd = new BufferedReader(new FileReader("dest_mnemonics.csv"));
		BufferedReader brj = new BufferedReader(new FileReader("jump_mnemonics.csv"));
		
		CSVParser prc = new CSVParser(brc, CSVFormat.DEFAULT.withHeader());
		CSVParser prd = new CSVParser(brd, CSVFormat.DEFAULT.withHeader());
		CSVParser prj = new CSVParser(brj, CSVFormat.DEFAULT.withHeader());
		
		for (CSVRecord rc : prc) {
		   comp_symb comp = new comp_symb();
		   comp.setName( rc.get("name") );
		   comp.setValue(rc.get("value"));
		   cmpList.add(comp);
		}
		for (CSVRecord rd : prd ) {
			dest_symb dest = new dest_symb( 
					rd.get("name"),
					rd.get("value"));
			destList.add(dest);
	    }
		for (CSVRecord rj : prj ) {
			jump_symb jump = new jump_symb( 
					rj.get("name"),
					rj.get("value"));
			jmpList.add(jump);
	    }
		
		brc.close();	
		prc.close();
		brd.close();	
		prd.close();
		brj.close();	
		prj.close();
	}
	
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	 
	public void translate_C_instr(ArrayList<String> instruction) {
		getSymbolLists();
		
		
		ListIterator<String> i = instruction.listIterator();
		while(i.hasNext()) {	
			String destTransl = null;
			String compTransl = null;
			String jmpTransl = null;

			String instr = i.next();
			boolean b = true;         // used to distinguish btwn A(true) and C(false) instructions
			for (int k=0; k<instr.length(); k++) {
				b= b & Character.isDigit(instr.charAt(k));
			}
			
			if (b != true) {        // true b corresponds to already translated A instruction
			int ieq = instr.indexOf("=");     // determines the index of = the seperator between dest and comp fields
			int iscl = instr.indexOf(";");   // determines the index of ";" the seperator btwn comp and jmp fields
			
			// translate dest field, comp, jump field
			if (ieq == -1) {           
				destTransl = "000";
				String compfld = instr.substring(0, iscl);
				for(comp_symb csym : cmpList) {
					if(compfld.equals(csym.getName())) {
						compTransl = csym.getValue();
						}
				}
				String jmpfld = instr.substring(iscl+1);
				for(jump_symb jsym : jmpList) {
					if(jmpfld.equals(jsym.getName())) {
						jmpTransl = jsym.getValue();
					}
				}

			}
			else {
				String destfld = instr.substring(0, ieq);
				for(dest_symb dsym : destList) {
					if(destfld.equals(dsym.getName() )) {
						destTransl = dsym.getValue() ;
						//System.out.println(destTransl);
					}
				}
				

				if (iscl != -1) {
				String compfld = instr.substring(ieq +1, iscl);
				for(comp_symb csym : cmpList) {
					if(compfld.equals(csym.getName())) {
						compTransl = csym.getValue();
					}
				}
				String jmpfld = instr.substring(iscl+1);
				for(jump_symb jsym : jmpList) {
					if(jmpfld.equals(jsym.getName())) {
						jmpTransl = jsym.getValue();
					}
				}
				}
				else {
					String compfld = instr.substring(ieq +1);
					for(comp_symb csym : cmpList) {
						if(compfld.equals(csym.getName())) {
							compTransl = csym.getValue();
						}
					}
					jmpTransl = "000";					
				}
				}
		instr = "111" +  compTransl + destTransl + jmpTransl;	
		i.set(instr);
			}
			
		}
	}
	
}
