package edu.byu.cs.roots.opg.io;

import java.io.*;
public class FSMold{

	private BufferedReader reader;
	public int lineNumber = 1;
	public int markLine = 1;
	
	public FSMold(String fileName){
		try{
			Reader in = new InputStreamReader(new FileInputStream(fileName));
			this.reader = new BufferedReader(in);
		}catch(IOException e){
			e.printStackTrace();
		}
		try {
			reader.mark(10);
			int ch;
			if((ch = reader.read()) == '0') reader.reset();
			else while(ch != '0' && ch != -1){
				reader.mark(2);
				ch = reader.read();
			}
			reader.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public int nextTokenId() throws IOException{
		int[] tokenIds = {0,0,0,0,1,0,0,0,2,0,0,0,3,0,0,0,4,0,0,5,6,7,0,0,0,8,0,0,9,0,0,10,0,0,11,0,0,0,12,0,0,13,0,0,14,0,0,15,0,0,16,0,0,17,0,0,0,18,0,0,0,19,0,0,0,20,0,0,0,21,0,0,0,22,23,0,0,0,0,0,24,25,26,0,0,27,0,0,0,0,0,28,0,0,0,0,0,29,30,0,0,0,31,0,0,0,0,32,0,0,33,34,35,36,37,38,39,40,41};
		int currentState = 0;
		int in;
		char ch;
		while((in = reader.read()) != -1 && Character.isWhitespace((char)in)){
			if(in == '\n') lineNumber++;
		}
		if(in == -1) return -1;
		
		while(!Character.isWhitespace(ch=(char)in) && in != -1){
			outer : switch(currentState){
				case 0:
					switch(ch){
						case 'S':
							currentState = 26;
							break outer;
						case 'D':
							currentState = 13;
							break outer;
						case '3':
							currentState = 115;
							break outer;
						case 'W':
							currentState = 54;
							break outer;
						case 'H':
							currentState = 22;
							break outer;
						case '2':
							currentState = 114;
							break outer;
						case 'C':
							currentState = 58;
							break outer;
						case '0':
							currentState = 112;
							break outer;
						case 'P':
							currentState = 66;
							break outer;
						case 'B':
							currentState = 9;
							break outer;
						case '6':
							currentState = 118;
							break outer;
						case '1':
							currentState = 113;
							break outer;
						case 'G':
							currentState = 35;
							break outer;
						case 'I':
							currentState = 5;
							break outer;
						case 'M':
							currentState = 70;
							break outer;
						case '5':
							currentState = 117;
							break outer;
						case 'F':
							currentState = 17;
							break outer;
						case 'Y':
							currentState = 74;
							break outer;
						case 'T':
							currentState = 62;
							break outer;
						case 'N':
							currentState = 1;
							break outer;
						case '4':
							currentState = 116;
							break outer;
						default: return 0;
					}
				case 1:
					switch(ch){
						case 'S':
							currentState = 48;
							break outer;
						case 'I':
							currentState = 39;
							break outer;
						case 'A':
							currentState = 2;
							break outer;
						case 'P':
							currentState = 32;
							break outer;
						default: return 0;
					}
				case 2:
					switch(ch){
						case 'M':
							currentState = 3;
							break outer;
						default: return 0;
					}
				case 3:
					switch(ch){
						case 'E':
							currentState = 4;
							break outer;
						default: return 0;
					}
				case 4:
					switch(ch){
						default: return 0;
					}
				case 5:
					switch(ch){
						case 'N':
							currentState = 6;
							break outer;
						default: return 0;
					}
				case 6:
					switch(ch){
						case 'D':
							currentState = 7;
							break outer;
						default: return 0;
					}
				case 7:
					switch(ch){
						case 'I':
							currentState = 8;
							break outer;
						default: return 0;
					}
				case 8:
					switch(ch){
						case 'V':
							currentState = 75;
							break outer;
						default: return 0;
					}
				case 9:
					switch(ch){
						case 'I':
							currentState = 10;
							break outer;
						default: return 0;
					}
				case 10:
					switch(ch){
						case 'R':
							currentState = 11;
							break outer;
						default: return 0;
					}
				case 11:
					switch(ch){
						case 'T':
							currentState = 12;
							break outer;
						default: return 0;
					}
				case 12:
					switch(ch){
						case 'H':
							currentState = 81;
							break outer;
						default: return 0;
					}
				case 13:
					switch(ch){
						case 'A':
							currentState = 29;
							break outer;
						case 'E':
							currentState = 14;
							break outer;
						default: return 0;
					}
				case 14:
					switch(ch){
						case 'A':
							currentState = 15;
							break outer;
						default: return 0;
					}
				case 15:
					switch(ch){
						case 'T':
							currentState = 16;
							break outer;
						default: return 0;
					}
				case 16:
					switch(ch){
						case 'H':
							currentState = 82;
							break outer;
						default: return 0;
					}
				case 17:
					switch(ch){
						case 'A':
							currentState = 18;
							break outer;
						default: return 0;
					}
				case 18:
					switch(ch){
						case 'M':
							currentState = 19;
							break outer;
						default: return 0;
					}
				case 19:
					switch(ch){
						case 'S':
							currentState = 21;
							break outer;
						case 'I':
							currentState = 83;
							break outer;
						case 'C':
							currentState = 20;
							break outer;
						default: return 0;
					}
				case 20: return 0;
					
				case 21: return 0;
				
				case 22:
					switch(ch){
						case 'U':
							currentState = 51;
							break outer;
						case 'E':
							currentState = 23;
							break outer;
						default: return 0;
					}
				case 23:
					switch(ch){
						case 'A':
							currentState = 24;
							break outer;
						default: return 0;
					}
				case 24:
					switch(ch){
						case 'D':
							currentState = 25;
							break outer;
						default: return 0;
					}
				case 25: return 0;
					
				case 26:
					switch(ch){
						case 'U':
							currentState = 45;
							break outer;
						case 'P':
							currentState = 42;
							break outer;
						case 'E':
							currentState = 27;
							break outer;
						default: return 0;
					}
				case 27:
					switch(ch){
						case 'X':
							currentState = 28;
							break outer;
						default: return 0;
					}
				case 28: return 0;
					
				case 29:
					switch(ch){
						case 'T':
							currentState = 30;
							break outer;
						default: return 0;
					}
				case 30:
					switch(ch){
						case 'E':
							currentState = 31;
							break outer;
						default: return 0;
					}
				case 31: return 0;
					
				case 32:
					switch(ch){
						case 'F':
							currentState = 33;
							break outer;
						default: return 0;
					}
				case 33:
					switch(ch){
						case 'X':
							currentState = 34;
							break outer;
						default: return 0;
					}
				case 34: return 0;
					
				case 35:
					switch(ch){
						case 'I':
							currentState = 36;
							break outer;
						default: return 0;
					}
				case 36:
					switch(ch){
						case 'V':
							currentState = 37;
							break outer;
						default: return 0;
					}
				case 37:
					switch(ch){
						case 'N':
							currentState = 38;
							break outer;
						default: return 0;
					}
				case 38: return 0;
					
				case 39:
					switch(ch){
						case 'C':
							currentState = 40;
							break outer;
						default: return 0;
					}
				case 40:
					switch(ch){
						case 'K':
							currentState = 41;
							break outer;
						default: return 0;
					}
				case 41: return 0;
					
				case 42:
					switch(ch){
						case 'F':
							currentState = 43;
							break outer;
						default: return 0;
					}
				case 43:
					switch(ch){
						case 'X':
							currentState = 44;
							break outer;
						default: return 0;
					}
				case 44: return 0;
					
				case 45:
					switch(ch){
						case 'R':
							currentState = 46;
							break outer;
						default: return 0;
					}
				case 46:
					switch(ch){
						case 'N':
							currentState = 47;
							break outer;
						default: return 0;
					}
				case 47: return 0;
					
				case 48:
					switch(ch){
						case 'F':
							currentState = 49;
							break outer;
						default: return 0;
					}
				case 49:
					switch(ch){
						case 'X':
							currentState = 50;
							break outer;
						default: return 0;
					}
				case 50: return 0;
					
				case 51:
					switch(ch){
						case 'S':
							currentState = 52;
							break outer;
						default: return 0;
					}
				case 52:
					switch(ch){
						case 'B':
							currentState = 53;
							break outer;
						default: return 0;
					}
				case 53:
					switch(ch){
						case 'A':
							currentState = 108;
							break outer;
						default: return 0;
					}
				case 54:
					switch(ch){
						case 'I':
							currentState = 55;
							break outer;
						default: return 0;
					}
				case 55:
					switch(ch){
						case 'F':
							currentState = 56;
							break outer;
						default: return 0;
					}
				case 56:
					switch(ch){
						case 'E':
							currentState = 57;
							break outer;
						default: return 0;
					}
				case 57: return 0;
					
				case 58:
					switch(ch){
						case 'H':
							currentState = 59;
							break outer;
						default: return 0;
					}
				case 59:
					switch(ch){
						case 'I':
							currentState = 60;
							break outer;
						default: return 0;
					}
				case 60:
					switch(ch){
						case 'L':
							currentState = 61;
							break outer;
						default: return 0;
					}
				case 61:
					switch(ch){
						case 'D':
							currentState = 111;
							break outer;
						default: return 0;
					}
				case 62:
					switch(ch){
						case 'R':
							currentState = 63;
							break outer;
						default: return 0;
					}
				case 63:
					switch(ch){
						case 'A':
							currentState = 103;
							break outer;
						case 'L':
							currentState = 64;
							break outer;
						default: return 0;
					}
				case 64:
					switch(ch){
						case 'R':
							currentState = 65;
							break outer;
						default: return 0;
					}
				case 65: return 0;
					
				case 66:
					switch(ch){
						case 'L':
							currentState = 67;
							break outer;
						default: return 0;
					}
				case 67:
					switch(ch){
						case 'A':
							currentState = 68;
							break outer;
						default: return 0;
					}
				case 68:
					switch(ch){
						case 'C':
							currentState = 69;
							break outer;
						default: return 0;
					}
				case 69:
					switch(ch){
						case 'E':
							currentState = 98;
							break outer;
						default: return 0;
					}
				case 70:
					switch(ch){
						case 'A':
							currentState = 71;
							break outer;
						default: return 0;
					}
				case 71:
					switch(ch){
						case 'R':
							currentState = 72;
							break outer;
						default: return 0;
					}
				case 72:
					switch(ch){
						case 'R':
							currentState = 73;
							break outer;
						default: return 0;
					}
				case 73:
					switch(ch){
						case 'I':
							currentState = 99;
							break outer;
						default: return 0;
					}
				case 74: return 0;
					
				case 75:
					switch(ch){
						case 'I':
							currentState = 76;
							break outer;
						default: return 0;
					}
				case 76:
					switch(ch){
						case 'D':
							currentState = 77;
							break outer;
						default: return 0;
					}
				case 77:
					switch(ch){
						case 'U':
							currentState = 78;
							break outer;
						default: return 0;
					}
				case 78:
					switch(ch){
						case 'A':
							currentState = 79;
							break outer;
						default: return 0;
					}
				case 79:
					switch(ch){
						case 'L':
							currentState = 80;
							break outer;
						default: return 0;
					}
				case 80: return 0;
					
				case 81: return 0;
					
				case 82: return 0;
					
				case 83:
					switch(ch){
						case 'L':
							currentState = 84;
							break outer;
						default: return 0;
					}
				case 84:
					switch(ch){
						case 'Y':
							currentState = 85;
							break outer;
						default: return 0;
					}
				case 85:
					switch(ch){
						case '_':
							currentState = 86;
							break outer;
						default: return 0;
					}
				case 86:
					switch(ch){
						case 'S':
							currentState = 92;
							break outer;
						case 'C':
							currentState = 87;
							break outer;
						default: return 0;
					}
				case 87:
					switch(ch){
						case 'H':
							currentState = 88;
							break outer;
						default: return 0;
					}
				case 88:
					switch(ch){
						case 'I':
							currentState = 89;
							break outer;
						default: return 0;
					}
				case 89:
					if(ch == 'L'){
						currentState = 90;
						break outer;
					}
					else return 0;

				case 90:
					if(ch == 'D'){
						currentState = 91;
						break outer;
					}
					else return 0;

				case 91: return 0;
					
				case 92:
					if(ch == 'P'){
						currentState = 93;
						break outer;
					}
					else return 0;
				
				case 93:
					if(ch == 'O'){
						currentState = 94;
						break outer;
					}
					else return 0;
				
				case 94:
					if(ch == 'U'){
						currentState = 95;
						break outer;
					}
					else return 0;

				case 95:
					if(ch == 'S'){
						currentState = 96;
						break outer;
					}
					else return 0;

				case 96:
					if(ch == 'E'){
						currentState = 97;
						break outer;
					}
					else return 0;

				case 97: return 0;
					
				case 98: return 0;
					
				case 99:
					if(ch == 'A'){
						currentState = 100;
						break outer;
					}
					else return 0;

				case 100:
					if(ch == 'G'){
						currentState = 101;
						break outer;
					}
					else return 0;
				case 101:
					if(ch == 'E'){
						currentState = 102;
						break outer;
					}
					else return 0;
				case 102: return 0;
				case 103:
						if(ch == 'I'){
							currentState = 104;
							break outer;
						}
						else return 0;
				case 104:
					if(ch == 'L'){
						currentState = 105;
						break outer;
					}
					else return 0;
				case 105:
					if(ch == 'E'){
						currentState = 106;
						break outer;
					}
					else return 0;

				case 106:
					if(ch == 'R'){
						currentState = 107;
						break outer;
					}
					else return 0;

				case 107: return 0;
			
				case 108:
					if(ch == 'N'){
						currentState = 109;
						break outer;
					}
					else return 0;

				case 109:
					if(ch == 'D'){
						currentState = 110;
						break outer;
					}
					else return 0;
				case 110: return 0;
				case 111: return 0;
				case 112: return 0;
				case 113: return 0;
				case 114: return 0;
				case 115: return 0;
				case 116: return 0;
				case 117: return 0;
			}
			in = reader.read();
		}
		return tokenIds[currentState];
	}
	public int peekTokenId() throws IOException{
		reader.mark(100);
		markLine = lineNumber; 
		int id = nextTokenId();
		return id;
	}
	public int peekTokenId(int ahead) throws IOException{
		reader.mark(100);
		markLine = lineNumber;
		int id = -1;
		for(int i=0; i<ahead; i++){
			id = nextTokenId();
		}
		return id;
	}
	public void resetToken() throws IOException{
		reader.reset();
		lineNumber = markLine;
	}
	public void skipLine() throws IOException{
		lineNumber++;
		reader.readLine();
	}
	public String nextLine() throws IOException{
		this.lineNumber++;
		return reader.readLine();
	}
	public String nextWord() throws IOException{
		StringBuilder word = new StringBuilder();
		char ch;
		try{
			while((ch = (char) reader.read()) != ' ' && ch != '\t' && ch != '\n' && ch != '\r')
				word.append(ch);
			if(ch == '\n') lineNumber++;
		}catch(IOException e){
			System.out.println("Error");
			return null;
		}
		return word.toString();
	}
	public static void main(String[] args) throws IOException{
	}
}
