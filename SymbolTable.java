import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.csv.*;

// Translates symbols and A-instructions taking into consideration predefined symbols
// displayed in the symbol list and adding recently defined symbols to the latter list.
public class SymbolTable {	
	int nextemptymem = 16;  //number of pre-defined Ri symbols 

	public void readTable1(List<Symbol> symbolsList) { 
	try {
		BufferedReader br = new BufferedReader(new FileReader("Symbols.csv"));
		CSVParser prr = new CSVParser(br, CSVFormat.DEFAULT.withHeader() );
				
		for (CSVRecord rec : prr ) {
				Symbol sym = new Symbol( 
						rec.get("Name"),
						Integer.parseInt(rec.get("value")));
				symbolsList.add(sym);
		}
		
		br.close();	
		prr.close();
	}
	
	catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    } 

	// Replace instructions between ( ) by @instruction_lineNb
	public void scan_add_symbolparenthesis2(ArrayList<String> instruction, ArrayList<Symbol> symbolsList) {
		int ii =0;
		ListIterator<String> i = instruction.listIterator();
        while(i.hasNext()){
        	String instr = (i.next());
			int leftParent = instr.indexOf('(');
			int rightParent = instr.indexOf(')');
			if (leftParent != -1) {
				String symb = instr.substring(leftParent +1, rightParent);
				String symb1 = instr.substring(leftParent, rightParent +1);
				instr = instr.replace(symb1,"");
				if ((instr.trim()).isEmpty()) {
					i.remove();
				} else {
				i.set(instr.trim()); }
				Symbol insSym = new Symbol(symb, ii);
				symbolsList.add(insSym);
			}
			ii= ii+1;
		}
	}
	
	// translate A-instructions: add new variables to symbol table and replace 
	// old variables with their value, then convert integers into binary base adding 0 
	//at the beginning to signal A-intructions and not C-instructions that strat with 1.
	
	public void scan_add_symbolvariable3(ArrayList<String> instruction, ArrayList<Symbol> symbolsList){
		  ListIterator<String> i = instruction.listIterator();
          while(i.hasNext()){
        	  String instr = (i.next()); 
			  if (instr.indexOf('@') != -1) {  // !=  => A-instructions  
					boolean j = true;
				  for (int k=1; k< (instr.length()); k++) { //test if we have a nb after @
					  char ic = instr.charAt(k);
					  boolean l = Character.isDigit(ic) ;
					  j = (j & l);
				  }
				  if (j != true) {   // instruction not composed of numerical values only
					 String instrtemp = instr.substring(1);
					 int insert =1;
					 for (Symbol symm : symbolsList) {
						 if ((symm.getName()).equals(instrtemp)) {
							 int dec = symm.getValue();
							 instr = Integer.toBinaryString(dec);
							 int lm = 16 - (instr.length());
							 for (int ll = 0; ll < lm; ll++) {
								 instr = "0" + instr;
								 insert = 0;
								 i.set(instr);
								 }						
						 }}
						if (insert == 1) { 
							Symbol ss = new Symbol(instrtemp,nextemptymem);
							 instr = Integer.toBinaryString(nextemptymem);
							 int lm = 16 - (instr.length());
							 for (int ll = 0; ll < lm; ll++) {
								 instr = "0" + instr;
								 }
							    symbolsList.add(ss);
							    i.set(instr);
								nextemptymem = nextemptymem +1;
						 }
				  }
				  
				   else {
					 String instr1 = instr.substring(1);
					 int subinstr = Integer.parseInt(instr1);
					 instr1 = Integer.toBinaryString(subinstr);
					 int lm = 16 - (instr1.length());
					 for (int ll = 0; ll < lm; ll++) {
					 instr1 = "0" + instr1;	
					 }
					 i.set(instr1);
				     }  
				} 
			}
		}
}