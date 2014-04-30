package edu.byu.cs.roots.opg.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class FSMGenerator {

	private int numberOfStates;
	private String[] terminals;
	private ArrayList<State> states;
	private BufferedWriter out;
	
	public FSMGenerator(String[] terminals){
		this.terminals = terminals;
		this.states = new ArrayList<State>();
		this.numberOfStates = 0;
		try {
			out = new BufferedWriter(new FileWriter("FSMt.java"));
			generateStates(terminals);
			createFSM();
			out.close();
		} catch (IOException e) {			
			e.printStackTrace();
		}
	}
	
	private void generateStates(String[] terminals) {
		
		states.add(new State(0));
		State current;
		State next;
		int tokenId = 1;

		for(String s : terminals){
			current = states.get(0);
			
			for(int i=0; i<s.length(); i++){
				char ch = s.charAt(i);
				
				if(!current.transitions.containsKey(ch)){
					next = new State(++numberOfStates);
					states.add(next);
					current.transitions.put(ch, next);
				}
				else next = current.transitions.get(ch);
				current = next;
			}
			current.isEndState = true;
			current.id = tokenId++;
		}
	}
	

	
	private class State{
		int num;
		int id;
		boolean isEndState;
		HashMap<Character,State> transitions;

		public State(int num){
			this.transitions = new HashMap<Character,State>();
			this.isEndState = false;
			this.num = num;
			this.id = 0;
		}
	}
	
	private void createFSM() throws IOException{
		
		Collection<Character> trans;
		out.write("import java.io.*;\n");
		out.write("public class FSM{\n\n");
		out.write("\tpublic static final int EOF = -1;\n");
		out.write("\tpublic static final int IGNORE = 0;\n");
		for(int i=0; i<terminals.length; i++) {
			String term = terminals[i];
			if(!Character.isJavaIdentifierStart(term.charAt(0)))
				term = "LEVEL_"+term;
			out.write("\tpublic static final int "+term+" = "+(i+1)+";\n");
		}
		/*
		 * 	public int lineNumber = 1;
			public int markLine = 1;
		 */
		out.write("\n\tprivate BufferedReader reader;\n\n");
		out.write("\tpublic int lineNumber = 1;\n");
		out.write("\tpublic int markLine = 1;\n");		
		out.write("\tpublic FSM(String fileName){\n");
		out.write("\t\ttry{\n");
		out.write("\t\t\tReader in = new InputStreamReader(new FileInputStream(fileName));\n");
		out.write("\t\t\tthis.reader = new BufferedReader(new FileReader(fileName));\n");
		out.write("\t\t}catch(IOException e){\n");
		out.write("\t\t\te.printStackTrace();\n");
		out.write("\t\t}\n");
		out.write("\t\ttry {\n");
		out.write("\t\t\treader.mark(10);\n");
		out.write("\t\t\tint ch;\n");
		out.write("\t\t\tif((ch = reader.read()) == '0') reader.reset();\n");
		out.write("\t\t\telse while(ch != '0' && ch != -1) {\n");
		out.write("\t\t\t\treader.mark(2);\n");
		out.write("\t\t\t\tch = reader.read();\n");
		out.write("\t\t\t}\n");
		out.write("\t\t\treader.reset();\n");
		out.write("\t\t} catch (IOException e) {\n");
		out.write("\t\t\te.printStackTrace();\n");
		out.write("\t\t}\n");
		out.write("\t}\n");
		out.write("\tpublic int nextTokenId() throws IOException{\n");
		writeStates();
		out.write("\t\tint currentState = 0;\n");
		out.write("\t\tint in;\n");
		out.write("\t\tchar ch;\n");
		out.write("\t\twhile((in = reader.read()) != -1 && Character.isWhitespace((char)in)) {\n");
		out.write("\t\t\tif(in == '\\n') lineNumber++;\n");
		out.write("\t\t}");
		out.write("\t\tif(in == -1) return -1;\n");
		out.write("\t\twhile(!Character.isWhitespace(ch=(char)in) && in != -1){\n");
		out.write("\t\t\touter : switch(currentState){\n");
		
		for(int i=0; i<numberOfStates; i++){
			State current = states.get(i);
			out.write("\t\t\t\tcase "+i+":\n");
			trans = current.transitions.keySet();
			out.write("\t\t\t\t\tswitch(ch){\n");

			for(Character c : trans){
				out.write("\t\t\t\t\t\tcase '"+c+"':\n");
				out.write("\t\t\t\t\t\t\tcurrentState = "+current.transitions.get(c).num+";\n");
				out.write("\t\t\t\t\t\t\tbreak outer;\n");
			}
			
			out.write("\t\t\t\t\t\tdefault: return 0;\n");
			out.write("\t\t\t\t\t}\n");
		}
		out.write("\t\t\t}\n");
		out.write("\t\t\tin = reader.read();\n");
		out.write("\t\t}\n");
		out.write("\t\treturn tokenIds[currentState];\n");		
		out.write("\t}\n");

		out.write("\tpublic int peekTokenId() throws IOException{\n");
		out.write("\t\treader.mark(100);\n");
		out.write("\t\tmarkLine = lineNumber;\n");
		out.write("\t\tint id = nextTokenId();\n");
		out.write("\t\treturn id;\n\t}\n");

		out.write("\tpublic int peekTokenId(int ahead) throws IOException{\n");
		out.write("\t\treader.mark(100);\n");
		out.write("\t\tmarkLine = lineNumber;\n");
		out.write("\t\tint id = -1;\n");
		out.write("\t\tfor(int i=0; i<ahead; i++){\n");
		out.write("\t\t\tid = nextTokenId();\n\t\t}\n");
		out.write("\t\treturn id;\n\t}\n");
		
		out.write("\tpublic void resetToken() throws IOException{\n");
		out.write("\t\treader.reset();\n");
		out.write("\t\tlineNumber = markLine;\n");
		out.write("\t}\n");		

		out.write("\tpublic void skipLine() throws IOException{\n");
		out.write("\t\tlineNumber++;\n");
		out.write("\t\treader.readLine();\n\t}\n");

		out.write("\tpublic String nextLine() throws IOException{\n");
		out.write("\t\tlineNumber++;\n");
		out.write("\t\treturn reader.readLine();\n\t}\n");
		
		
		out.write("\tpublic String nextWord() throws IOException{\n");
		out.write("\t\tStringBuilder word = new StringBuilder();\n");
		out.write("\t\tchar ch;\n");
		out.write("\t\ttry{\n");
		out.write("\t\t\twhile((ch = (char) reader.read()) != ' ' && ch != '\\t' && ch != '\\n' && ch != '\\r')\n");
		out.write("\t\t\t\tword.append(ch);\n");
		out.write("\t\t\tif(ch == '\\n') lineNumber++;\n");
		out.write("\t\t}catch(IOException e){\n");		
		out.write("\t\t\tSystem.out.println(\"Error\");\n");
		out.write("\t\t\treturn null;\n\t\t}\n");
		out.write("\t\treturn word.toString();\n\t}\n");
		out.write("\tpublic static void main(String[] args) throws IOException{\n");
		out.write("\t\tFSM fsm = new FSM(\"test.txt\");\n");
		out.write("\t\tfsm.nextTokenId();\n");
		out.write("\t}\n");
		out.write("}\n");

	}
	
	private void writeStates() throws IOException {
		out.write("\t\tint[] tokenIds = {");
		for(int i = 0; i < states.size(); i++){
			if(i==states.size()-1) out.write(states.get(i).id+"};\n");
			else out.write(states.get(i).id+",");
		}		
	}

	public static void main(String[] args){
		String[] terminals = {"NAME","INDI","BIRT","DEAT","FAM","FAMC","FAMS",
 				  "HEAD","SEX","DATE","NPFX",
 				  "GIVN","NICK","SPFX","SURN","NSFX",
 				  "HUSB","WIFE","CHIL","TRLR","PLAC","MARR","Y","INDIVIDUAL",
 				  "BIRTH","DEATH","FAMILY","FAMILY_CHILD","FAMILY_SPOUSE","PLACE",
 				  "MARRIAGE","TRAILER","HUSBAND","CHILD","CLEARED","COMPLETED",
 				  "INFANT","PRE-1970","QUALIFIED","STILLBORN","SUBMITTED","UNCLEARED",
 				  "BIC","DNS","CANCELED","DNS/CAN",
 				  "BAPL","CONL","ENDL","SLGC","SLGS","STAT",
 				  "OBJE","FILE",
 				  "0","1","2","3","4","5","6"};
		new FSMGenerator(terminals);
	}
}
