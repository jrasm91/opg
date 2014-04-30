package edu.byu.cs.roots.opg.io;

import java.io.*;
public class FSM{

	public static final int EOF = -1;
	public static final int IGNORE = 0;
	public static final int NAME = 1;
	public static final int INDI = 2;
	public static final int BIRT = 3;
	public static final int DEAT = 4;
	public static final int FAM = 5;
	public static final int FAMC = 6;
	public static final int FAMS = 7;
	public static final int HEAD = 8;
	public static final int SEX = 9;
	public static final int DATE = 10;
	public static final int NPFX = 11;
	public static final int GIVN = 12;
	public static final int NICK = 13;
	public static final int SPFX = 14;
	public static final int SURN = 15;
	public static final int NSFX = 16;
	public static final int HUSB = 17;
	public static final int WIFE = 18;
	public static final int CHIL = 19;
	public static final int TRLR = 20;
	public static final int PLAC = 21;
	public static final int MARR = 22;
	public static final int Y = 23;
	public static final int INDIVIDUAL = 24;
	public static final int BIRTH = 25;
	public static final int DEATH = 26;
	public static final int FAMILY = 27;
	public static final int FAMILY_CHILD = 28;
	public static final int FAMILY_SPOUSE = 29;
	public static final int PLACE = 30;
	public static final int MARRIAGE = 31;
	public static final int TRAILER = 32;
	public static final int HUSBAND = 33;
	public static final int CHILD = 34;
	public static final int CLEARED = 35;
	public static final int COMPLETED = 36;
	public static final int INFANT = 37;
	public static final int PRE_1970 = 38;
	public static final int QUALIFIED = 39;
	public static final int STILLBORN = 40;
	public static final int SUBMITTED = 41;
	public static final int UNCLEARED = 42;
	public static final int BIC = 43;
	public static final int DNS = 44;
	public static final int CANCELED = 45;
	public static final int DNS_CAN = 46;
	public static final int BAPL = 47;
	public static final int CONL = 48;
	public static final int ENDL = 49;
	public static final int SLGC = 50;
	public static final int SLGS = 51;
	public static final int STAT = 52;
	public static final int OBJE = 53;
	public static final int FILE = 54;
	public static final int LEVEL_0 = 55;
	public static final int LEVEL_1 = 56;
	public static final int LEVEL_2 = 57;
	public static final int LEVEL_3 = 58;
	public static final int LEVEL_4 = 59;
	public static final int LEVEL_5 = 60;
	public static final int LEVEL_6 = 61;
	public static final int _PRIMARY = 62;

	public BufferedReader reader;

	public int lineNumber = 1;
	public int markLine = 1;
	public FSM(String fileName) throws IOException
	{
//		Reader in = new InputStreamReader(new FileInputStream(fileName));
		this.reader = new BufferedReader(new FileReader(fileName));

		reader.mark(10);
		int ch;
		if((ch = reader.read()) == '0') reader.reset();
		else while(ch != '0' && ch != -1) {
			reader.mark(2);
			ch = reader.read();
		}
		reader.reset();

	}
	public int nextTokenId() throws IOException{
		int[] tokenIds = {0,0,0,0,1,0,0,0,2,0,0,0,3,0,0,0,4,0,0,5,6,7,0,0,0,8,0,0,9,0,0,10,0,0,11,0,0,0,12,0,0,13,0,0,14,0,0,15,0,0,16,0,0,17,0,0,0,18,0,0,0,19,0,0,0,20,0,0,0,21,0,0,0,22,23,0,0,0,0,0,24,25,26,0,0,27,0,0,0,0,0,28,0,0,0,0,0,29,30,0,0,0,31,0,0,0,0,32,0,0,33,34,0,0,0,0,0,35,0,0,0,0,0,0,0,36,0,0,0,37,0,0,0,0,0,0,38,0,0,0,0,0,0,0,0,39,0,0,0,0,0,0,0,40,0,0,0,0,0,0,41,0,0,0,0,0,0,0,0,42,43,0,44,0,0,0,0,0,0,45,0,0,0,46,0,0,47,0,48,0,0,0,49,0,0,50,51,0,52,0,0,0,53,0,0,54,55,56,57,58,59,60,61,0,0,0,0,0,0,0,0,62};
		int currentState = 0;
		int in;
		char ch;
		while((in = reader.read()) != -1 && Character.isWhitespace((char)in)) {
			if(in == '\n') lineNumber++;
		}		if(in == -1) return -1;
		while((!Character.isWhitespace(ch=(char)in) || currentState == 219) && in != -1){
			outer : switch(currentState){
				case 0:
					switch(ch){
						case 'O':
							currentState = 199;
							break outer;
						case '3':
							currentState = 209;
							break outer;
						case 'W':
							currentState = 54;
							break outer;
						case 'H':
							currentState = 22;
							break outer;
						case '0':
							currentState = 206;
							break outer;
						case 'P':
							currentState = 66;
							break outer;
						case 'G':
							currentState = 35;
							break outer;
						case '1':
							currentState = 207;
							break outer;
						case '6':
							currentState = 212;
							break outer;
						case 'I':
							currentState = 5;
							break outer;
						case '5':
							currentState = 211;
							break outer;
						case 'F':
							currentState = 17;
							break outer;
						case 'U':
							currentState = 161;
							break outer;
						case 'N':
							currentState = 1;
							break outer;
						case '4':
							currentState = 210;
							break outer;
						case 'S':
							currentState = 26;
							break outer;
						case 'D':
							currentState = 13;
							break outer;
						case 'C':
							currentState = 58;
							break outer;
						case '2':
							currentState = 208;
							break outer;
						case 'B':
							currentState = 9;
							break outer;
						case 'Q':
							currentState = 137;
							break outer;
						case 'M':
							currentState = 70;
							break outer;
						case 'Y':
							currentState = 74;
							break outer;
						case 'T':
							currentState = 62;
							break outer;
						case 'E':
							currentState = 189;
							break outer;
						case '_':
							currentState = 212;
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
						case 'F':
							currentState = 126;
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
						case 'A':
							currentState = 184;
							break outer;
						default: return 0;
					}
				case 10:
					switch(ch){
						case 'R':
							currentState = 11;
							break outer;
						case 'C':
							currentState = 170;
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
						case 'N':
							currentState = 171;
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
						case 'I':
							currentState = 203;
							break outer;
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
				case 20:
					switch(ch){
						default: return 0;
					}
				case 21:
					switch(ch){
						default: return 0;
					}
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
				case 25:
					switch(ch){
						default: return 0;
					}
				case 26:
					switch(ch){
						case 'U':
							currentState = 45;
							break outer;
						case 'T':
							currentState = 146;
							break outer;
						case 'P':
							currentState = 42;
							break outer;
						case 'L':
							currentState = 193;
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
				case 28:
					switch(ch){
						default: return 0;
					}
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
				case 31:
					switch(ch){
						default: return 0;
					}
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
				case 34:
					switch(ch){
						default: return 0;
					}
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
				case 38:
					switch(ch){
						default: return 0;
					}
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
				case 41:
					switch(ch){
						default: return 0;
					}
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
				case 44:
					switch(ch){
						default: return 0;
					}
				case 45:
					switch(ch){
						case 'R':
							currentState = 46;
							break outer;
						case 'B':
							currentState = 154;
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
				case 47:
					switch(ch){
						default: return 0;
					}
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
				case 50:
					switch(ch){
						default: return 0;
					}
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
				case 57:
					switch(ch){
						default: return 0;
					}
				case 58:
					switch(ch){
						case 'O':
							currentState = 118;
							break outer;
						case 'A':
							currentState = 173;
							break outer;
						case 'H':
							currentState = 59;
							break outer;
						case 'L':
							currentState = 112;
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
				case 65:
					switch(ch){
						default: return 0;
					}
				case 66:
					switch(ch){
						case 'R':
							currentState = 130;
							break outer;
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
				case 74:
					switch(ch){
						default: return 0;
					}
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
				case 80:
					switch(ch){
						default: return 0;
					}
				case 81:
					switch(ch){
						default: return 0;
					}
				case 82:
					switch(ch){
						default: return 0;
					}
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
					switch(ch){
						case 'L':
							currentState = 90;
							break outer;
						default: return 0;
					}
				case 90:
					switch(ch){
						case 'D':
							currentState = 91;
							break outer;
						default: return 0;
					}
				case 91:
					switch(ch){
						default: return 0;
					}
				case 92:
					switch(ch){
						case 'P':
							currentState = 93;
							break outer;
						default: return 0;
					}
				case 93:
					switch(ch){
						case 'O':
							currentState = 94;
							break outer;
						default: return 0;
					}
				case 94:
					switch(ch){
						case 'U':
							currentState = 95;
							break outer;
						default: return 0;
					}
				case 95:
					switch(ch){
						case 'S':
							currentState = 96;
							break outer;
						default: return 0;
					}
				case 96:
					switch(ch){
						case 'E':
							currentState = 97;
							break outer;
						default: return 0;
					}
				case 97:
					switch(ch){
						default: return 0;
					}
				case 98:
					switch(ch){
						default: return 0;
					}
				case 99:
					switch(ch){
						case 'A':
							currentState = 100;
							break outer;
						default: return 0;
					}
				case 100:
					switch(ch){
						case 'G':
							currentState = 101;
							break outer;
						default: return 0;
					}
				case 101:
					switch(ch){
						case 'E':
							currentState = 102;
							break outer;
						default: return 0;
					}
				case 102:
					switch(ch){
						default: return 0;
					}
				case 103:
					switch(ch){
						case 'I':
							currentState = 104;
							break outer;
						default: return 0;
					}
				case 104:
					switch(ch){
						case 'L':
							currentState = 105;
							break outer;
						default: return 0;
					}
				case 105:
					switch(ch){
						case 'E':
							currentState = 106;
							break outer;
						default: return 0;
					}
				case 106:
					switch(ch){
						case 'R':
							currentState = 107;
							break outer;
						default: return 0;
					}
				case 107:
					switch(ch){
						default: return 0;
					}
				case 108:
					switch(ch){
						case 'N':
							currentState = 109;
							break outer;
						default: return 0;
					}
				case 109:
					switch(ch){
						case 'D':
							currentState = 110;
							break outer;
						default: return 0;
					}
				case 110:
					switch(ch){
						default: return 0;
					}
				case 111:
					switch(ch){
						default: return 0;
					}
				case 112:
					switch(ch){
						case 'E':
							currentState = 113;
							break outer;
						default: return 0;
					}
				case 113:
					switch(ch){
						case 'A':
							currentState = 114;
							break outer;
						default: return 0;
					}
				case 114:
					switch(ch){
						case 'R':
							currentState = 115;
							break outer;
						default: return 0;
					}
				case 115:
					switch(ch){
						case 'E':
							currentState = 116;
							break outer;
						default: return 0;
					}
				case 116:
					switch(ch){
						case 'D':
							currentState = 117;
							break outer;
						default: return 0;
					}
				case 117:
					switch(ch){
						default: return 0;
					}
				case 118:
					switch(ch){
						case 'M':
							currentState = 119;
							break outer;
						case 'N':
							currentState = 187;
							break outer;
						default: return 0;
					}
				case 119:
					switch(ch){
						case 'P':
							currentState = 120;
							break outer;
						default: return 0;
					}
				case 120:
					switch(ch){
						case 'L':
							currentState = 121;
							break outer;
						default: return 0;
					}
				case 121:
					switch(ch){
						case 'E':
							currentState = 122;
							break outer;
						default: return 0;
					}
				case 122:
					switch(ch){
						case 'T':
							currentState = 123;
							break outer;
						default: return 0;
					}
				case 123:
					switch(ch){
						case 'E':
							currentState = 124;
							break outer;
						default: return 0;
					}
				case 124:
					switch(ch){
						case 'D':
							currentState = 125;
							break outer;
						default: return 0;
					}
				case 125:
					switch(ch){
						default: return 0;
					}
				case 126:
					switch(ch){
						case 'A':
							currentState = 127;
							break outer;
						default: return 0;
					}
				case 127:
					switch(ch){
						case 'N':
							currentState = 128;
							break outer;
						default: return 0;
					}
				case 128:
					switch(ch){
						case 'T':
							currentState = 129;
							break outer;
						default: return 0;
					}
				case 129:
					switch(ch){
						default: return 0;
					}
				case 130:
					switch(ch){
						case 'E':
							currentState = 131;
							break outer;
						default: return 0;
					}
				case 131:
					switch(ch){
						case '-':
							currentState = 132;
							break outer;
						default: return 0;
					}
				case 132:
					switch(ch){
						case '1':
							currentState = 133;
							break outer;
						default: return 0;
					}
				case 133:
					switch(ch){
						case '9':
							currentState = 134;
							break outer;
						default: return 0;
					}
				case 134:
					switch(ch){
						case '7':
							currentState = 135;
							break outer;
						default: return 0;
					}
				case 135:
					switch(ch){
						case '0':
							currentState = 136;
							break outer;
						default: return 0;
					}
				case 136:
					switch(ch){
						default: return 0;
					}
				case 137:
					switch(ch){
						case 'U':
							currentState = 138;
							break outer;
						default: return 0;
					}
				case 138:
					switch(ch){
						case 'A':
							currentState = 139;
							break outer;
						default: return 0;
					}
				case 139:
					switch(ch){
						case 'L':
							currentState = 140;
							break outer;
						default: return 0;
					}
				case 140:
					switch(ch){
						case 'I':
							currentState = 141;
							break outer;
						default: return 0;
					}
				case 141:
					switch(ch){
						case 'F':
							currentState = 142;
							break outer;
						default: return 0;
					}
				case 142:
					switch(ch){
						case 'I':
							currentState = 143;
							break outer;
						default: return 0;
					}
				case 143:
					switch(ch){
						case 'E':
							currentState = 144;
							break outer;
						default: return 0;
					}
				case 144:
					switch(ch){
						case 'D':
							currentState = 145;
							break outer;
						default: return 0;
					}
				case 145:
					switch(ch){
						default: return 0;
					}
				case 146:
					switch(ch){
						case 'I':
							currentState = 147;
							break outer;
						case 'A':
							currentState = 197;
							break outer;
						default: return 0;
					}
				case 147:
					switch(ch){
						case 'L':
							currentState = 148;
							break outer;
						default: return 0;
					}
				case 148:
					switch(ch){
						case 'L':
							currentState = 149;
							break outer;
						default: return 0;
					}
				case 149:
					switch(ch){
						case 'B':
							currentState = 150;
							break outer;
						default: return 0;
					}
				case 150:
					switch(ch){
						case 'O':
							currentState = 151;
							break outer;
						default: return 0;
					}
				case 151:
					switch(ch){
						case 'R':
							currentState = 152;
							break outer;
						default: return 0;
					}
				case 152:
					switch(ch){
						case 'N':
							currentState = 153;
							break outer;
						default: return 0;
					}
				case 153:
					switch(ch){
						default: return 0;
					}
				case 154:
					switch(ch){
						case 'M':
							currentState = 155;
							break outer;
						default: return 0;
					}
				case 155:
					switch(ch){
						case 'I':
							currentState = 156;
							break outer;
						default: return 0;
					}
				case 156:
					switch(ch){
						case 'T':
							currentState = 157;
							break outer;
						default: return 0;
					}
				case 157:
					switch(ch){
						case 'T':
							currentState = 158;
							break outer;
						default: return 0;
					}
				case 158:
					switch(ch){
						case 'E':
							currentState = 159;
							break outer;
						default: return 0;
					}
				case 159:
					switch(ch){
						case 'D':
							currentState = 160;
							break outer;
						default: return 0;
					}
				case 160:
					switch(ch){
						default: return 0;
					}
				case 161:
					switch(ch){
						case 'N':
							currentState = 162;
							break outer;
						default: return 0;
					}
				case 162:
					switch(ch){
						case 'C':
							currentState = 163;
							break outer;
						default: return 0;
					}
				case 163:
					switch(ch){
						case 'L':
							currentState = 164;
							break outer;
						default: return 0;
					}
				case 164:
					switch(ch){
						case 'E':
							currentState = 165;
							break outer;
						default: return 0;
					}
				case 165:
					switch(ch){
						case 'A':
							currentState = 166;
							break outer;
						default: return 0;
					}
				case 166:
					switch(ch){
						case 'R':
							currentState = 167;
							break outer;
						default: return 0;
					}
				case 167:
					switch(ch){
						case 'E':
							currentState = 168;
							break outer;
						default: return 0;
					}
				case 168:
					switch(ch){
						case 'D':
							currentState = 169;
							break outer;
						default: return 0;
					}
				case 169:
					switch(ch){
						default: return 0;
					}
				case 170:
					switch(ch){
						default: return 0;
					}
				case 171:
					switch(ch){
						case 'S':
							currentState = 172;
							break outer;
						default: return 0;
					}
				case 172:
					switch(ch){
						case '/':
							currentState = 180;
							break outer;
						default: return 0;
					}
				case 173:
					switch(ch){
						case 'N':
							currentState = 174;
							break outer;
						default: return 0;
					}
				case 174:
					switch(ch){
						case 'C':
							currentState = 175;
							break outer;
						default: return 0;
					}
				case 175:
					switch(ch){
						case 'E':
							currentState = 176;
							break outer;
						default: return 0;
					}
				case 176:
					switch(ch){
						case 'L':
							currentState = 177;
							break outer;
						default: return 0;
					}
				case 177:
					switch(ch){
						case 'E':
							currentState = 178;
							break outer;
						default: return 0;
					}
				case 178:
					switch(ch){
						case 'D':
							currentState = 179;
							break outer;
						default: return 0;
					}
				case 179:
					switch(ch){
						default: return 0;
					}
				case 180:
					switch(ch){
						case 'C':
							currentState = 181;
							break outer;
						default: return 0;
					}
				case 181:
					switch(ch){
						case 'A':
							currentState = 182;
							break outer;
						default: return 0;
					}
				case 182:
					switch(ch){
						case 'N':
							currentState = 183;
							break outer;
						default: return 0;
					}
				case 183:
					switch(ch){
						default: return 0;
					}
				case 184:
					switch(ch){
						case 'P':
							currentState = 185;
							break outer;
						default: return 0;
					}
				case 185:
					switch(ch){
						case 'L':
							currentState = 186;
							break outer;
						default: return 0;
					}
				case 186:
					switch(ch){
						default: return 0;
					}
				case 187:
					switch(ch){
						case 'L':
							currentState = 188;
							break outer;
						default: return 0;
					}
				case 188:
					switch(ch){
						default: return 0;
					}
				case 189:
					switch(ch){
						case 'N':
							currentState = 190;
							break outer;
						default: return 0;
					}
				case 190:
					switch(ch){
						case 'D':
							currentState = 191;
							break outer;
						default: return 0;
					}
				case 191:
					switch(ch){
						case 'L':
							currentState = 192;
							break outer;
						default: return 0;
					}
				case 192:
					switch(ch){
						default: return 0;
					}
				case 193:
					switch(ch){
						case 'G':
							currentState = 194;
							break outer;
						default: return 0;
					}
				case 194:
					switch(ch){
						case 'S':
							currentState = 196;
							break outer;
						case 'C':
							currentState = 195;
							break outer;
						default: return 0;
					}
				case 195:
					switch(ch){
						default: return 0;
					}
				case 196:
					switch(ch){
						default: return 0;
					}
				case 197:
					switch(ch){
						case 'T':
							currentState = 198;
							break outer;
						default: return 0;
					}
				case 198:
					switch(ch){
						default: return 0;
					}
				case 199:
					switch(ch){
						case 'B':
							currentState = 200;
							break outer;
						default: return 0;
					}
				case 200:
					switch(ch){
						case 'J':
							currentState = 201;
							break outer;
						default: return 0;
					}
				case 201:
					switch(ch){
						case 'E':
							currentState = 202;
							break outer;
						default: return 0;
					}
				case 202:
					switch(ch){
						default: return 0;
					}
				case 203:
					switch(ch){
						case 'L':
							currentState = 204;
							break outer;
						default: return 0;
					}
				case 204:
					switch(ch){
						case 'E':
							currentState = 205;
							break outer;
						default: return 0;
					}
				case 205:
					switch(ch){
						default: return 0;
					}
				case 206:
					switch(ch){
						default: return 0;
					}
				case 207:
					switch(ch){
						default: return 0;
					}
				case 208:
					switch(ch){
						default: return 0;
					}
				case 209:
					switch(ch){
						default: return 0;
					}
				case 210:
					switch(ch){
						default: return 0;
					}
				case 211:
					switch(ch){
						default: return 0;
					}
				case 212:
					switch(ch){
						case 'P':
							currentState = 213;
							break outer;
						default: return 0;
					}
				case 213:
					switch(ch){
						case 'R':
							currentState = 214;
							break outer;
						default: return 0;
					}
				case 214:
					switch(ch){
						case 'I':
							currentState = 215;
							break outer;
						default: return 0;
					}
				case 215:
					switch(ch){
						case 'M':
							currentState = 216;
							break outer;
						default: return 0;
					}
				case 216:
					switch(ch){
						case 'A':
							currentState = 217;
							break outer;
						default: return 0;
					}
				case 217:
					switch(ch){
						case 'R':
							currentState = 218;
							break outer;
						default: return 0;
					}
				case 218:
					switch(ch){
						case 'Y':
							currentState = 219;
							break outer;
						default: return 0;
					}
				case 219:
					switch(ch){
						case ' ':
							currentState = 220;
							break outer;
						default: return 0;
					}
				case 220:
					switch(ch){
						case 'Y':
							currentState = 221;
							break outer;
						default: return 0;
					}
				case 221:
					switch(ch){
						default: return 0;
					}
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
		lineNumber++;
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
		FSM fsm = new FSM("test.txt");
		fsm.nextTokenId();
	}
}
