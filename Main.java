/* This assembler translates assembly language into machine language
 * for the 16 bit minicomputer
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main { 
	
	public static void main(String[] args) {
		//"assembly language -> machine learning" program instruction array
		ArrayList<String> instruction = new ArrayList<String>(); 
		
		// parsing class, aim: get rid of comments, spaces, new lines, semi-columns
		// add the resulting program instructions to array "instruction"
		Parsing_doc pard = new Parsing_doc();
		pard.parsComSpace(instruction);
		
		// create symbols table of the assembly language of my  mini computer
		// first: call the defined Symbol(symbol_name, symbol_value) class
        // 2nd: create the  list of Symbols "symbolsList" out of its CSV file
		//      by calling readTable1
		// 3rd: Translate A-instructions (start with @):
		//       **method scan_add_symbolparenthesis2 deals with the "(symbol)" format
		//       by adding this symbol to the table with its instruction nb as value
		//       and removing it from the instructions list
		//       ** method scan_add_symbolvariable3 adds recently defined 
		//       variables to the symbol  table, replaces all variables by their binary 
	    //       value preceded by 0 like all A-instructions and all numerical A-instructions
		//       by their binary value
		ArrayList<Symbol> symbolsList = new ArrayList<>(); 
		SymbolTable tb = new SymbolTable();
		tb.readTable1(symbolsList);
        tb.scan_add_symbolparenthesis2(instruction, symbolsList);	
        tb.scan_add_symbolvariable3(instruction, symbolsList);
        
        //System.out.println(instruction);
        //System.out.println(symbolsList);
        
        // in the below, we will translate C-instructions (comp/jump)
        C_Instruction CINSTR = new C_Instruction();
        CINSTR.translate_C_instr(instruction);


		try {
		BufferedWriter brr = new BufferedWriter(new FileWriter("Output_MachineLanguageFile.txt", true));
	    	for (int i=0; i< instruction.size(); i++) {
	    		//System.out.println("instruction" + i +"  is___" + instruction.get(i) + "____");	
	    		brr.append("instruction" + i + " is:___" + instruction.get(i) + "____ \n");
	    		} 
	    	brr.close(); } 

		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
