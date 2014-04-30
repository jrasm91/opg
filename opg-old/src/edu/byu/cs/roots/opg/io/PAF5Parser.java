package edu.byu.cs.roots.opg.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.JFileChooser;

import edu.byu.cs.roots.opg.io.PAF5RecordObject.PAF5RecordType;
import edu.byu.cs.roots.opg.model.Event;
import edu.byu.cs.roots.opg.model.EventType;
import edu.byu.cs.roots.opg.model.Family;
import edu.byu.cs.roots.opg.model.Gender;
import edu.byu.cs.roots.opg.model.Individual;

public class PAF5Parser {
	
	private static final int PAF5_MAP_TOTALS_SIZE = 8192;
	private static final int PAF5_CLUSTER_SIZE = 67108864;
	private static final int PAF5_BLOCK_SIZE = 8192;
	private static final int PAF5_MAX_CLUSTERS = 31;
	private static final int PAF5_MAX_BLOCK_TYPES = 32;
	private static final int PAF5_MAP_RECORD_SIZE = 8192;
	
	static LEndianRandomAccessFile file = null;
	static private PAF5Header header = new PAF5Header();
	private static short dBMap[] = new short[PAF5_MAP_RECORD_SIZE / 2];
	private static short[][] mapBlocks = new short[PAF5_MAX_CLUSTERS][];
		
	public static void main(String[] args) throws IOException {
		//testing driver
			
		JFileChooser chooser = new JFileChooser();
		int option = chooser.showOpenDialog(null);
		if (option == JFileChooser.APPROVE_OPTION)
			PAF5Parser.parsePAF5File(chooser.getSelectedFile().getAbsolutePath());
		System.exit(0);
	}

	static public GedcomRecord parsePAF5File(String fileName) throws IOException{
		GedcomRecord record = null;
		record = new GedcomRecord();
				
		String tempFileName = null; 
		
		try{
			if (fileName.endsWith(".zip"))
			{
				//extract the paf file from the zip(first paf found) (if it exists)
				// to allow randomFileAccess
				ZipFile zipFile = new ZipFile(fileName);
				Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();
				while (zipEntries.hasMoreElements())
				{
					ZipEntry entry = (ZipEntry)zipEntries.nextElement();
					if (entry.getName().endsWith(".paf"))
					{
						InputStream is = null;
						OutputStream dest = null;
						is = new BufferedInputStream
			              (zipFile.getInputStream(entry));
			            int count;
			            byte data[] = new byte[2048];
			            tempFileName = "~" + entry.getName();
			            FileOutputStream fos = new 
			              FileOutputStream(tempFileName);
			            dest = new BufferedOutputStream(fos, 2048);
			            while ((count = is.read(data, 0, 2048)) 
			              != -1) {
			               dest.write(data, 0, count);
			            }
			            dest.flush();
			            dest.close();
			            is.close();
			            break;
					}
				}
				
				if (tempFileName == null)
					throw new IOException(fileName + "is not a PAF backup file.");
				
				file = new LEndianRandomAccessFile(tempFileName, "r");
				
			} else if (fileName.endsWith(".paf"))
				file = new LEndianRandomAccessFile(fileName, "r");
			else
				throw new IOException(fileName + "is not a PAF file.");
			
			
			//read file header
			header.read(file);
			//
			if (!loadMapBlocks())
				throw new IOException(fileName + "has invalid database map Block(s).");
			loadDBMap();
			
			//loop over all individuals in the individual index
			getIndividuals(record);
									
			//get families
			getFamilies(record);
			
			record.linkRecord();
						
		}
		catch(IOException e){
			System.err.println("Error reading PAF database: " + fileName);
			e.printStackTrace();
		}
		finally
		{
			if (file != null)
				file.close();
			if (tempFileName != null)
			{
				File tempFile = new File(tempFileName);
				tempFile.delete();
			}
		}
		
		return record;
	}

	private static void getIndividuals(GedcomRecord record) throws IOException {
		
		int maxRin = header.fileBlocks.indiCount;
		
		for (int i = 1; i <= maxRin; ++i)
		{
			Individual indi = getIndividual(i);
			if (indi != null)
			{
				record.addIndividual(indi.id, indi);
			} else
				maxRin++;
				
		}
	}

	private static Individual getIndividual(int rin) throws IOException {
		
		Individual indi = new Individual("I" + rin);
		PAF5IndiRecord indiRecord = new PAF5IndiRecord();
		loadFromDatabase(indiRecord, rin);
		//name
		PAF5IndiNameRecord nameRecord = new PAF5IndiNameRecord();
		file.seek(indiRecord.nameOffset);
		nameRecord.read(file);
		parseIndiName(indi, nameRecord.text);
		//gender
		if (indiRecord.sex == 'M')
			indi.gender = Gender.MALE;
		else if (indiRecord.sex == 'F')
			indi.gender = Gender.FEMALE;
		else if (indiRecord.sex == 'U')
			indi.gender = Gender.UNKNOWN;
		else
			return null; //deleted record

		//birth
		if (indiRecord.birth.placeNameOffset != 0 ||
				!indiRecord.birth.date.dateString.equals(""))
		{
			indi.birth = new Event(EventType.BIRTH);
			if (indiRecord.birth.placeNameOffset != 0)
			{
				PAF5NameRecord birthPlace = new PAF5NameRecord();
				file.seek(indiRecord.birth.placeNameOffset);
				birthPlace.read(file);
				indi.birth.place = birthPlace.text;
				
			}
			if (!indiRecord.birth.date.dateString.equals(""))
				indi.birth.date = indiRecord.birth.date.dateString;
			indi.birth.parseDateParts();
		}
		
		//death
		if (indiRecord.death.placeNameOffset != 0 ||
				!indiRecord.death.date.dateString.equals(""))
		{
			indi.death = new Event(EventType.DEATH);
			if (indiRecord.death.placeNameOffset != 0)
			{
				PAF5NameRecord deathPlace = new PAF5NameRecord();
				file.seek(indiRecord.death.placeNameOffset);
				deathPlace.read(file);
				indi.death.place = deathPlace.text;
			}
			if (!indiRecord.death.date.dateString.equals(""))
				indi.death.date = indiRecord.death.date.dateString;
			indi.death.parseDateParts();
		}
		
		//todo - photo and ordinances
		
		return indi;
		
	}
	
	private static void getFamilies(GedcomRecord record) throws IOException {
		
		int maxRin = header.fileBlocks.marriageCount;
		
		for (int i = 1; i <= maxRin; ++i)
		{
			Family fam = getFamily(i);
			if (fam != null)
			{
				record.addFamily(fam.id, fam);
				//add fams and famc ids in for individuals in marriage
				if (fam.husbandId != null)
				{
					Individual husband = record.getIndividuals().get(fam.husbandId);
					if (husband != null)
						husband.famsIds.add(fam.id);
				}
				if (fam.wifeId != null)
				{
					Individual wife = record.getIndividuals().get(fam.wifeId);
					if (wife != null)
						wife.famsIds.add(fam.id);
				}
				for(String childId : fam.childrenXRefIds)
				{
					Individual child = record.getIndividuals().get(childId);
					if (child != null)
						child.famcIds.add(fam.id);
				}			
			} else
				maxRin++;
				
		}
	}
	

	private static Family getFamily(int mRin) throws IOException {
		Family fam = new Family("F" + mRin);
		PAF5MarriageRecord marriageRecord = new PAF5MarriageRecord();
		loadFromDatabase(marriageRecord, mRin);
		//check for deleted record
		if (marriageRecord.divorcedStatus == 'D')
			return null;
		
		//husband
		if (marriageRecord.husbandRin != 0)
			fam.husbandId = "I" + marriageRecord.husbandRin;
		
		//wife
		if (marriageRecord.wifeRin != 0)
			fam.wifeId = "I" + marriageRecord.wifeRin;
		
		//marriage event
		if (marriageRecord.marriageEvent.placeNameOffset != 0 ||
				!marriageRecord.marriageEvent.date.dateString.equals(""))
		{
			fam.marriage = new Event(EventType.MARRIAGE);
			if (marriageRecord.marriageEvent.placeNameOffset != 0)
			{
				PAF5NameRecord marriagePlace = new PAF5NameRecord();
				file.seek(marriageRecord.marriageEvent.placeNameOffset);
				marriagePlace.read(file);
				fam.marriage.place = marriagePlace.text;
			}
			if (!marriageRecord.marriageEvent.date.dateString.equals(""))
				fam.marriage.date = marriageRecord.marriageEvent.date.dateString;
		}
		
		
		//children
		PAF5FamilyLinkRecord famLink = new PAF5FamilyLinkRecord();
		int curChildLinkRin = marriageRecord.firstChildLinkRin;
		while (curChildLinkRin > 0)
		{
			loadFromDatabase(famLink, curChildLinkRin);
			if (famLink.linkType == 'D')
				break;
			if (famLink.rin == 0)
				break;
			fam.childrenXRefIds.add("I" + famLink.rin);
			curChildLinkRin = famLink.nextSiblngRin;
			if (curChildLinkRin == marriageRecord.firstChildLinkRin)
				break;
		}
		
		//todo - sealing status info 
		
		return fam;
	}

	private static void parseIndiName(Individual indi, String text) {
		indi.givenName = getGivenName(text);
		indi.surname = getSurname(text);
		indi.nameSuffix = getNameSuffix(text);
	}

	private static String getGivenName(String nameline) {
		int end = nameline.indexOf("/");
		if(end != -1)
			return nameline.substring(0,end);
		else return nameline.trim();
	}
	
	private static String getSurname(String nameline) {
		int start = nameline.indexOf("/");
		int end = nameline.lastIndexOf("/");

		if(start != -1 && end != -1 && start < nameline.length()-1 && start != end){
			return nameline.substring(start+1, end);
		}
		else if(start != -1 && end == -1 && start < nameline.length()-1 && start != end){
			return nameline.substring(start+1);
		}
		else return "";
	}
	
	private static String getNameSuffix(String nameline) {
		int start = nameline.lastIndexOf("/");
		if(start != -1 && start < nameline.length()-1)
			return nameline.substring(start+1);
		
		else return "";
	}

/*	private static String getStringFromFileOffset(long nameOffset) throws IOException {
		//get a null terminated string given an offset in the open file
		byte[] buffer = new byte[256];
		int pos = 0;
		do {			
			buffer[pos] = file.readByte();
		} while (buffer[pos++] != 0);
		
		byte[] buffer2 = new byte[pos-2];
		for (int i = 0; i < buffer2.length; ++i)
			buffer2[i] = buffer[i];
		
		return new String(buffer2, "UTF-8");
	}*/

	public static String getNullTermStringFromBuff(byte[] buffer) {
		
		int charCount = 0;
		for(int i = 0; i < buffer.length; ++i)
		{
			if(buffer[i] != 0)
				++charCount;
			else
				break;
		}
			
		try {
			return new String(buffer, 0, charCount, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	static private void loadDBMap() throws IOException
	{
		file.seek(PAF5Header.size);
		for (int i = 0; i < dBMap.length; ++i)
			dBMap[i] = file.readShortLE();
	}
	
	static private boolean loadMapBlocks() throws IOException
	{
		boolean retVal = true;
		for(int i = 0; i < header.fileBlocks.mapBlockCount; ++i)
			retVal &= loadMapBlock(i);
		return retVal;
	}
	
	static private boolean loadMapBlock(int cluster) throws IOException
	{
		// Load the Map Block  
		byte[] mapBlockTmp = new byte[PAF5_BLOCK_SIZE];
		long filePos = PAF5Header.size + PAF5_MAP_TOTALS_SIZE + cluster * PAF5_CLUSTER_SIZE;
		file.seek(filePos);
		file.readFully(mapBlockTmp);
		
		if (mapBlockTmp[0] != PAF5RecordType.MAP.typeId)
			return false;
		// Allocate space for the pre-compiled Map Block
		int clusterCount = 0;
		while (clusterCount <= cluster) {
			mapBlocks[clusterCount] = new short[PAF5_MAX_BLOCK_TYPES + PAF5_BLOCK_SIZE];
			++clusterCount;
		}
		short[] mapBlock = mapBlocks[cluster];
		// Precompile it to a table of indexes so we don't have to walk it
		int[] blockCount = new int[PAF5_MAX_BLOCK_TYPES];
		// Count number of each kind of block 
		for (int i = 0; i < PAF5_BLOCK_SIZE; ++i) {
			if (mapBlockTmp[i] > 0 && mapBlockTmp[i] < PAF5_MAX_BLOCK_TYPES + 1)
				blockCount[mapBlockTmp[i] - 1]++;
		}
		// Set Front Indexes to point to where block location will be stored
		int tmp = 0;
		for (int i = 0; i < PAF5_MAX_BLOCK_TYPES; ++i) {
			mapBlock[i] = (short)(tmp + PAF5_MAX_BLOCK_TYPES);
			tmp += blockCount[i];
		}
		// Now fill the chart
		int[] foundBlocks = new int[PAF5_MAX_BLOCK_TYPES];
		for (int i = 0; i < PAF5_BLOCK_SIZE; ++i) {
			tmp = mapBlockTmp[i] - 1;
			if (tmp >= 0 && tmp < PAF5_MAX_BLOCK_TYPES)
			{
				mapBlock[mapBlock[tmp] + foundBlocks[tmp]] = (short)i;
				foundBlocks[tmp]++;
			}
		}
	
		return true;
	}
		
	static PAF5RecordObject loadFromDatabase(PAF5RecordObject obj, long rin) throws IOException
	{
		long filePos = findRecordPos(obj.type, rin);
		if (filePos == 0)
			return null;
		file.seek(filePos);
		obj.read(file);
		return obj;
		
	}

	private static long findRecordPos(PAF5RecordObject.PAF5RecordType type, long rin) {
		
		PAFCBR loc = findCBR(rin, type);
		
		if (rin < 1 || loc == null)
			return 0;
				
		return PAF5Header.size + PAF5_MAP_TOTALS_SIZE + 
			loc.cluster * PAF5_CLUSTER_SIZE + 
			loc.block * PAF5_BLOCK_SIZE + 
			loc.record * type.size;
	}
	
	private static PAFCBR findCBR(long rin, PAF5RecordType type) {

		PAFCBR result = new PAFCBR();
		long gotSoFar;
		long recordsLeft;
		
		gotSoFar = findWhichCluster(rin, type, result);
		if (result.cluster < 0 || result.cluster >= PAF5_MAX_CLUSTERS)
			return null;
		
		recordsLeft = rin - gotSoFar;
		result.block = findWhichBlock(result.cluster, rin, type);
		if (result.block < 1 || result.block > PAF5_BLOCK_SIZE)
			return null;
		
		result.record = (int)(recordsLeft - type.numPerBlock*((recordsLeft - 1)/type.numPerBlock)) - 1;
		if (result.record < 0 || result.record > PAF5_BLOCK_SIZE)
			return null;
		
		return result;
	}

	private static int findWhichBlock(int cluster, long rin,
			PAF5RecordType type) {
			
		short[] mapBlock = mapBlocks[cluster];
		int tmp = mapBlock[type.typeId - 1] + (int)((rin - 1) / type.numPerBlock);
		
		return mapBlock[tmp];
	}

	private static long findWhichCluster(long rin, PAF5RecordType type,
			PAFCBR result) {
		
		long gotSoFar = 0;
			
		for (result.cluster = 0;  rin > gotSoFar + 
			(long)dBMap[(int)PAF5_MAX_BLOCK_TYPES * result.cluster + type.typeId - 1] * 
			(long)type.numPerBlock;)
		{
			gotSoFar += (long)dBMap[PAF5_MAX_BLOCK_TYPES * result.cluster + type.typeId - 1] *
				(long)type.numPerBlock;
			result.cluster++;
			if (result.cluster >= PAF5_MAX_CLUSTERS)
			{
				gotSoFar=0;
				break;
			}
		}
				
		return gotSoFar;
	}

	static class PAFCBR {
		int cluster;
		int block;
		int record;
	}

	
}

abstract class PAF5RecordObject {
	
	enum PAF5RecordType {
		
		NAME(1, 269), INDI(2, 221), MARRIAGE(3, 106), NOTE(2, 256), FAMILY_LINK(5, 46),
			CITATION(6, 141), SOURCE(7, 507), REPOSITORY(8, 497), INDI_NAME(9, 271),
			INDI_INDEX(10, 16), MAP(11, 8192), USER_EVENT(12, 59), EVENT_TYPE(13, 140),
			CONTACT(17, 57), OTHER_NAME(18, 269), MEDIA(20, 62);
		
		public int typeId;
		public int size; //size in database in bytes
		public int numPerBlock;
		public int BLOCK_SIZE = 8192;
		
		private PAF5RecordType(int typeId, int size){
			this.typeId = typeId;
			this.numPerBlock = BLOCK_SIZE / size; 
			this.size = size;
		}
	}
	
	PAF5RecordType type;
	abstract public void read(LEndianRandomAccessFile file) throws IOException;
	
}

class  PAF5NameRecord extends PAF5RecordObject {
	
	public long alphaLessOffset;
	public long alphaGreaterOffset;
	public long parentRecordOffset;
	byte nameType;
	String text;
	
	public PAF5NameRecord() {
		type = PAF5RecordType.NAME;
		type.size = 269; 
	}

	@Override
	public void read(LEndianRandomAccessFile file) throws IOException {
		alphaLessOffset = (long)file.readIntLE();
		alphaGreaterOffset = (long)file.readIntLE();
		parentRecordOffset = (long)file.readIntLE();
		nameType = file.readByte();
		byte[] buffer = new byte[256];
		file.readFully(buffer); //? maybe read one byte at a time until hit null byte
		text = PAF5Parser.getNullTermStringFromBuff(buffer);
	}
}

class  PAF5IndiNameRecord extends PAF5RecordObject {
	
	public long nextOffset;
	public long previousOffset;
	public long ownerRin;
	byte nameType;
	short textLength;
	String text;
	
	public PAF5IndiNameRecord() {
		type = PAF5RecordType.INDI_NAME;
		type.size = 271; //variable on read (based on textLength 
	}

	@Override
	public void read(LEndianRandomAccessFile file) throws IOException {
		nextOffset = file.readIntLE();
		previousOffset = file.readIntLE(); 
		ownerRin = file.readIntLE();
		nameType = file.readByte();
		textLength = file.readShortLE();
		byte[] buffer = new byte[textLength+1];
		file.readFully(buffer);
		text = PAF5Parser.getNullTermStringFromBuff(buffer);
	}
}

class  PAF5IndiIndexRecord extends PAF5RecordObject {
	
	public int alphaLessRecord;
	public int alphaGreaterRecord;
	public int parentRecord;
	public long nameOffset;
		
	public PAF5IndiIndexRecord() {
		type = PAF5RecordType.INDI_INDEX;
		type.size = 16;
	}

	@Override
	public void read(LEndianRandomAccessFile file) throws IOException {
		alphaLessRecord = file.readIntLE();
		alphaGreaterRecord = file.readIntLE();
		parentRecord = file.readIntLE();
		nameOffset = file.readLongLE();
	}	
}

class  PAF5IndiRecord extends PAF5RecordObject {
	
	public long nameOffset;
	public int firstMRin;
	public int firstFamLinkRin;
	public int noteRin;
	public int firstCitationRin;
	public long modifiedTime;
	public long submittedTime;
	public int firstUserEventRin;
	public int mediaRin;
	public int contactRin;
	public long relDescNameOffset;
	public int primaryMRin;
	byte[] uId = new byte[16];
	PAF5Event birth = new PAF5Event();
	PAF5Event christening = new PAF5Event();
	PAF5Event death = new PAF5Event();
	PAF5Event burial = new PAF5Event();
	PAF5OrdinanceEvent ldsBaptism = new PAF5OrdinanceEvent();
	PAF5OrdinanceEvent ldsEndowment = new PAF5OrdinanceEvent();
	char sex;
	byte numEventsWithSources;
	byte numSPEventsWtihSources;
			
	public PAF5IndiRecord() {
		type = PAF5RecordType.INDI;
		type.size = 221;
	}

	@Override
	public void read(LEndianRandomAccessFile file) throws IOException {
		nameOffset = file.readIntLE();
		firstMRin = file.readIntLE();
		firstFamLinkRin = file.readIntLE();
		noteRin = file.readIntLE();
		firstCitationRin = file.readIntLE();
		modifiedTime = file.readIntLE();
		submittedTime = file.readIntLE();
		firstUserEventRin = file.readIntLE();
		mediaRin = file.readIntLE();
		contactRin = file.readIntLE();
		relDescNameOffset = file.readIntLE();
		primaryMRin = file.readIntLE();
		file.readFully(uId);
		birth.read(file);
		christening.read(file);
		death.read(file);
		burial.read(file);
		ldsBaptism.read(file);
		ldsEndowment.read(file);
		byte[] byteBuf = new byte[1];
		byteBuf[0] = (byte)file.read();
		sex = new String(byteBuf, "ASCII").charAt(0);
		numEventsWithSources = file.readByte();
		numSPEventsWtihSources = file.readByte();
	}
}

class PAF5MarriageRecord extends PAF5RecordObject {
	public long modifiedTime;
	public int husbandRin;
	public int wifeRin;
	public int firstChildLinkRin;
	public int husbandNextMRin;
	public int wifeNextMRin;
	public int firstUserEventRin;
	public int noteRin;
	public int citationRin;
	byte[] uId = new byte[16];
	PAF5Event marriageEvent = new PAF5Event();
	PAF5OrdinanceEvent ldsSealingEvent = new PAF5OrdinanceEvent();
	char divorcedStatus;
	
	public PAF5MarriageRecord() {
		type = PAF5RecordType.MARRIAGE;
		type.size = 106;
	}
	
	@Override
	public void read(LEndianRandomAccessFile file) throws IOException {
		modifiedTime = file.readIntLE();
		husbandRin = file.readIntLE();
		wifeRin = file.readIntLE();
		firstChildLinkRin = file.readIntLE();
		husbandNextMRin = file.readIntLE();
		wifeNextMRin = file.readIntLE();
		firstUserEventRin = file.readIntLE();
		noteRin = file.readIntLE();
		citationRin = file.readIntLE();
		file.readFully(uId);
		marriageEvent.read(file);
		ldsSealingEvent.read(file);
		byte[] byteBuf = new byte[1];
		byteBuf[0] = (byte)file.read();
		divorcedStatus = new String(byteBuf, "ASCII").charAt(0);
		byte[] buffer = new byte[8];
		file.readFully(buffer);
	}
}

class PAF5FamilyLinkRecord extends PAF5RecordObject {
	public int mRin; //??marriage this individual is a child in??
	public int nextSiblngRin;
	public int nextParentsMRin;
	public int rin;
	PAF5OrdinanceEvent ldsSealingToParents = new PAF5OrdinanceEvent();
	char linkType;
	
	public PAF5FamilyLinkRecord() {
		type = PAF5RecordType.FAMILY_LINK;
		type.size = 46;
	}
	
	@Override
	public void read(LEndianRandomAccessFile file) throws IOException {
		mRin = file.readIntLE();
		nextSiblngRin = file.readIntLE();
		nextParentsMRin = file.readIntLE();
		rin = file.readIntLE();
		ldsSealingToParents.read(file);
		byte[] byteBuf = new byte[1];
		byteBuf[0] = (byte)file.read();
		linkType = new String(byteBuf, "ASCII").charAt(0);
		byte[] buffer = new byte[8];
		file.readFully(buffer);
	}
}

class PAF5MediaRecord extends PAF5RecordObject {
	public int ownerRin;
	public int nextMediaRin;
	public int previousMediaRin;
	public int pathNameRin;
	public int captionNameRin;
	public int descriptionNameRin;
	public int audioPathNameRin;
	char mediaType;
	byte ownerType;
	short flags;
	short width;
	short height;
	short clipTop;
	short clipBottom;
	short clipLeft;
	short clipRight;
	int slideTime;
	
	public PAF5MediaRecord() {
		type = PAF5RecordType.MEDIA;
		type.size = 62;
	}
	
	@Override
	public void read(LEndianRandomAccessFile file) throws IOException {
		ownerRin = file.readIntLE();
		nextMediaRin = file.readIntLE();
		previousMediaRin = file.readIntLE();
		pathNameRin = file.readIntLE();
		captionNameRin = file.readIntLE();
		descriptionNameRin = file.readIntLE();
		audioPathNameRin = file.readIntLE();
		byte[] byteBuf = new byte[1];
		byteBuf[0] = (byte)file.read();
		mediaType = new String(byteBuf, "ASCII").charAt(0);
		ownerType = (byte)file.read();
		flags = file.readShortLE();
		width = file.readShortLE();
		height = file.readShortLE();
		clipTop = file.readShortLE();
		clipBottom = file.readShortLE();
		clipLeft = file.readShortLE();
		clipRight = file.readShortLE();
		slideTime = ((int)file.readShort() & 0x0000FFFF); //stored in file as unsigned short
		byte[] buffer = new byte[16];
		file.readFully(buffer);
	}
	
}

class PAF5Event
{
	public PAF5Date date = new PAF5Date();
	public long placeNameOffset;
	public long ageAtNameOffset;
	public int citationRin;
	
	public void read(LEndianRandomAccessFile file) throws IOException
	{
		date.read(file);
		placeNameOffset = file.readIntLE();
		ageAtNameOffset = file.readIntLE();
		citationRin = file.readIntLE();
	}
}

class PAF5OrdinanceEvent
{
	public PAF5Date date = new PAF5Date();
	public long placeNameOffset;
	public int citationRin;
	byte isTemple;
	
	public void read(LEndianRandomAccessFile file) throws IOException
	{
		date.read(file);
		placeNameOffset = file.readIntLE();
		citationRin = file.readIntLE();
		isTemple = file.readByte();
	}
}

class PAF5Date
{
	byte[] dateInfo = new byte[12];
	String dateString = "";
	
	public static int getJulianDay(int year, int month, int day)
	{
		int result = day - 32075
	      + 1461 * ( year + 4800 - ( 14 - month ) / 12 )/4
	      + 367 * ( month - 2 + ( ( 14 - month ) / 12 ) * 12 ) / 12
	      - 3 * ( ( year + 4900 - ( 14 - month ) / 12 ) / 100 ) / 4;
		
		return result;
	}
	
	public int getJulianDayDate1()
	{
		int julianDay = (dateInfo[4] & 0xFF) +
						((dateInfo[5] & 0xFF) << 8) +
						((dateInfo[6] & 0xFF) << 16);
		
		//read julian date flags
		
		
		return julianDay;
	}
	
	public int getJulianDayDate2()
	{
		int julianDay = (dateInfo[9] & 0xFF) +
						((dateInfo[10] & 0xFF) << 8) +
						((dateInfo[11] & 0xFF) << 16);
		
		return julianDay;
	}
	
	public static Calendar getCalendarFromJulianDay(int julianDay)
	{
		int jd = julianDay;
		int jdate_tmp;
		int y, m, d;
		
		jdate_tmp = jd - 1721119;
        y = (4 * jdate_tmp - 1)/146097;
        jdate_tmp = 4 * jdate_tmp - 1 - 146097 * y;
        d = jdate_tmp/4;
        jdate_tmp = (4 * d + 3)/1461;
        d = 4 * d + 3 - 1461 * jdate_tmp;
        d = (d + 4)/4;
        m = (5 * d - 3)/153;
        d = 5 * d - 3 - 153 * m;
        d = (d + 5) / 5;
        y = 100 * y + jdate_tmp;
        if(m < 10) {
                m += 3;
        } else {
                m -= 9;
                ++y;
        }
        
        Calendar cal = new GregorianCalendar(y, m-1, d);
        
        return cal;
	}
	
	public void read(LEndianRandomAccessFile file) throws IOException
	{
		file.readFully(dateInfo);
		int dateRecordDescriptor = dateInfo[0] & 0x07;
		int date1Type = (dateInfo[0] >>> 4) & 0x03;
		int date2Type = (dateInfo[0] >>> 6) & 0x03;
		
		if (dateRecordDescriptor == 0)// no date
			return;
		
//		int dateStatusOrModifier = (dateInfo[1]) & 0xFF;
		
		String date1String = null;
		String date2String = null;
		boolean date1NoDay = false;
		boolean date1NoMonth = false;
		boolean date1NoYear = false;
		boolean date2NoDay = false;
		boolean date2NoMonth = false;
		boolean date2NoYear = false;
		int julianDayDate1 = 0;
		int julianDayDate2 = 0;
		
		
		if (dateRecordDescriptor == 1) //date status (e.g. BIC, etc)
		{
			//check flags in dateStatusOrModifier
			dateString = "Date Status";
			return;
		}
		
		if (date1Type == 1)//julian date
		{
			//read date flags
			julianDayDate1 = getJulianDayDate1();
			date1NoDay = (((dateInfo[3] >> 5) & 0x1) == 1)? true : false;
			date1NoMonth = (((dateInfo[3] >> 6) & 0x1) == 1)? true : false;
			date1NoYear = (((dateInfo[3] >> 7) & 0x1) == 1)? true : false;
//			int eraId = (dateInfo[2] & 0xFF) + 
//					((dateInfo[3] & 0x0F) << 8);
			date1String = getJulianDateString(julianDayDate1, date1NoDay, date1NoMonth, date1NoYear);
		} else if (date1Type == 2) //non-standard date
		{
			//get date text from name record
			long oldFilePos = file.getFilePointer();
			int nameOffset = (dateInfo[2] & 0xFF) +
				((dateInfo[3] & 0xFF) << 8) +
				((dateInfo[4] & 0xFF) << 16) +
				((dateInfo[5] & 0xFF) << 24);
			PAF5NameRecord dateName = new PAF5NameRecord();
			file.seek(nameOffset);
			dateName.read(file);
			file.seek(oldFilePos);
			date1String = dateName.text;
		}
		
		if (dateRecordDescriptor == 3 || dateRecordDescriptor == 4)
		{
			if (date2Type == 1)//julian date
			{
				julianDayDate2 = getJulianDayDate2();
				date2NoDay = (((dateInfo[3] >> 5) & 0x1) == 1)? true : false;
				date2NoMonth = (((dateInfo[3] >> 6) & 0x1) == 1)? true : false;
				date2NoYear = (((dateInfo[3] >> 7) & 0x1) == 1)? true : false;
//				int eraId = (dateInfo[7] & 0xFF) + 
//				((dateInfo[8] & 0x0F) << 8);
				date2String = getJulianDateString(julianDayDate2, date2NoDay, date2NoMonth, date2NoYear);
			} else if (date2Type == 2) //non-standard date
			{
				//get date text from name record
				long oldFilePos = file.getFilePointer();
				int nameOffset = (dateInfo[2] & 0xFF) +
					((dateInfo[3] & 0xFF) << 8) +
					((dateInfo[4] & 0xFF) << 16) +
					((dateInfo[5] & 0xFF) << 24);
				PAF5NameRecord dateName = new PAF5NameRecord();
				file.seek(nameOffset);
				dateName.read(file);
				file.seek(oldFilePos);
				date2String = dateName.text;
			}
		}
				
		
		switch (dateRecordDescriptor)
		{
		case 2://single date
			dateString = date1String;
			break;
		case 3://split date
			//NOTE: PAF5 only allows one field of day/month/year to differ
			Calendar cal1 = getCalendarFromJulianDay(julianDayDate1);
			Calendar cal2 = getCalendarFromJulianDay(julianDayDate2);
			boolean first = true;
			if (!date1NoDay)
			{
				first = false;
				DateFormat format = new SimpleDateFormat("d");
				if (cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH) && !date2NoDay)
				{
					dateString += format.format(cal1.getTime()) + "/";
					dateString += format.format(cal2.getTime());
				} else
					dateString += format.format(cal1.getTime());
			}
			if (!date1NoMonth)
			{
				if (!first)
					dateString += " ";
				first = false;
				DateFormat format = new SimpleDateFormat("MMM");
				if (cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH) && !date2NoMonth)
				{
					dateString += format.format(cal1.getTime()) + "/";
					dateString += format.format(cal2.getTime());
				} else
					dateString += format.format(cal1.getTime());
			}
			if (!date1NoYear)
			{
				if (!first)
					dateString += " ";
				first = false;
				DateFormat format = new SimpleDateFormat("yyyy");
				if ((cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR) ||
						(cal1.get(Calendar.ERA) != cal2.get(Calendar.ERA))) && !date2NoYear)
				{
					if (cal1.get(Calendar.ERA) == GregorianCalendar.BC)
						format = new SimpleDateFormat("yyyy G");
					dateString += format.format(cal1.getTime()) + "/";
					if (cal2.get(Calendar.ERA) == GregorianCalendar.BC)
						format = new SimpleDateFormat("yyyy G");
					else
						format = new SimpleDateFormat("yyyy");
					dateString += format.format(cal2.getTime());
				} else
					dateString += format.format(cal1.getTime());
			}			
			break;
		case 4://date range
			dateString = "from " + date1String + " to " + date2String;
			break;
		};	
	}

	private String getJulianDateString(int julianDayDate, boolean noDay, boolean noMonth, boolean noYear) {
		Calendar cal = getCalendarFromJulianDay(julianDayDate);
		boolean first = true;
		String formatString = "";
		if (!noDay)
		{
			first = false;
			formatString += "d";
		}
		if (!noMonth)
		{
			if (!first)
				formatString += " ";
			first = false;
			formatString += "MMM";	
		}
		if (!noYear)
		{
			if (!first)
				formatString += " ";
			first = false;
			formatString += "yyyy";
		}
		if (cal.get(Calendar.ERA) == GregorianCalendar.BC)
			formatString += " G";
		DateFormat format = new SimpleDateFormat(formatString);;
		return format.format(cal.getTime());
		
	}
}


class PAF5Header {
	
	static final int size = 4096;
	
	public void read(LEndianRandomAccessFile file) throws IOException
	{
		byte[] buffer4 = new byte[4];
		file.readFully(buffer4);//ignore last version of paf to write to file
		lastVersion = PAF5Parser.getNullTermStringFromBuff(buffer4);
		file.readFully(buffer4);//earliest version of paf that can read the file
		String pafVersion;
		try {
			pafVersion = PAF5Parser.getNullTermStringFromBuff(buffer4);
		} catch (NumberFormatException e) {
			throw new IOException("File read problem - Invalid paf version.");
		}
		if (Integer.parseInt(pafVersion) < 500)
			throw new IOException("File read problem - PAF file is not at least version 5.");
		file.readFully(buffer4);//file identifier - must be "PAF"
		identifier = PAF5Parser.getNullTermStringFromBuff(buffer4);
		if (!identifier.equals("PAF"))
			throw new IOException("File read problem - not a PAF file.");
		databaseVersion = file.readShortLE();
		fileBlocks.read(file);
		pedigreeIndiRecordNum = file.readIntLE();
		userData.read(file);
		for (int i = 0; i < 10; ++i)
			lastSavedIndiRecordNums[i] = file.readIntLE();
		for (int i = 0; i < 10; ++i)
			lastSavedCitationRecordNums[i] = file.readIntLE();
		for (int i = 0; i < 10; ++i)
			lastSavedMarriageRecordNums[i] = file.readIntLE();
		lastViewedIndiRecordNum = file.readIntLE();
		cLog = file.readByte();
		numTimesClosedSinceBackup = file.readShortLE();
		showRelationshipIndiRecordNum = file.readIntLE();
		
		//system time - last modified
		for (int i = 0; i < 8; ++i)
			modifedTime[i] = file.readShortLE();
		
		databaseUId = file.readLongLE();
		auxValue1 = file.readLongLE();
		auxValue2 = file.readLongLE();
		flags1 = file.readLongLE();
		flags2 = file.readLongLE();
		flags3 = file.readLongLE();
		flags4 = file.readLongLE();
		flags5 = file.readLongLE();
	}
	
	String lastVersion;
	String version;
	String identifier;
	short databaseVersion;
	PAF5FileBlocks fileBlocks = new PAF5FileBlocks();
	int pedigreeIndiRecordNum;
	PAF5UserData userData = new PAF5UserData();
	int[] lastSavedIndiRecordNums = new int[10];
	int[] lastSavedCitationRecordNums = new int[10];
	int[] lastSavedMarriageRecordNums = new int[10];
	int lastViewedIndiRecordNum;
	byte cLog;
	short numTimesClosedSinceBackup;
	int showRelationshipIndiRecordNum;
	
	//system time - last modified
	short[] modifedTime = new short[8];
	
	long databaseUId;
	long auxValue1;
	long auxValue2;
	long flags1;
	long flags2;
	long flags3;
	long flags4;
	long flags5;
	
}

class PAF5FileBlocks {
	
	static final int size = 716;
	
	int mapCount;
	int mapBlockCount;
	
	int indiCount;
	int indiBlockCount;
	int firstIndi;
	int firstFreeIndi;
	int indiFreeCount;
	
	int indiIdxCount;
	int indiIdxBlockCount;
	int firstIndiIdx;
	int firstFreeIndiIdx;
	int indiIdxFreeCount;
	
	int indiNameCount;
	int indiNameBlockCount;
	int indiNameStart;
	int indiNameOffset;
	int indiNameHighOffset;
	int firstFreeIndiName;
	int indiNameFreeCount;
	
	int nameCount;
	int nameBlockCount;
	int nameStart;
	int nameOffset;
	int nameHighOffset;
	
	int otherNameCount;
	int otherNameBlockCount;
	int otherNameStart;
	int otherNameOffset;
	int otherNameHighOffset;
	
	int marriageCount;
	int marriageBlockCount;
	int firstMarriage;
	int firstFreeMarriage;
	int marriageFreeCount;
	
	int noteCount;
	int noteBlockCount;
	int firstNote;
	int firstFreeNote;
	int noteFreeCount;
	
	int citationCount;
	int citationBlockCount;
	int firstCitation;
	int firstFreeCitation;
	int citationFreeCount;
	
	int sourceCount;
	int sourceBlockCount;
	int firstSource;
	int firstFreeSource;
	int sourceFreeCount;
	
	int repositoryCount;
	int repositoryBlockCount;
	int firstRepository;
	int firstFreeRepository;
	int repositoryFreeCount;
	
	int familyLinkCount;
	int familyLinkBlockCount;
	int firstFamilyLink;
	int firstFreeFamilyLink;
	int familyLinkFreeCount;
	
	int eventTypeCount;
	int eventTypeBlockCount;
	int firstEventType;
	int firstFreeEventType;
	int eventTypeFreeCount;
	
	int mediaCount;
	int mediaBlockCount;
	int firstMedia;
	int firstFreeMedia;
	int mediaFreeCount;
	
	int eventCount;
	int eventBlockCount;
	int firstEvent;
	int firstFreeEvent;
	int eventFreeCount;
	
	int contactCount;
	int contactBlockCount;
	int firstContact;
	int firstFreeContact;
	int contactFreeCount;
		
	
	public void read(LEndianRandomAccessFile file) throws IOException
	{
		mapCount = file.readIntLE();
		mapBlockCount = file.readIntLE();
		
		indiCount = file.readIntLE();
		indiBlockCount = file.readIntLE();
		firstIndi = file.readIntLE();
		firstFreeIndi = file.readIntLE();
		indiFreeCount = file.readIntLE();
		
		indiIdxCount = file.readIntLE();
		indiIdxBlockCount = file.readIntLE();
		firstIndiIdx = file.readIntLE();
		firstFreeIndiIdx = file.readIntLE();
		indiIdxFreeCount = file.readIntLE();
		
		indiNameCount = file.readIntLE();
		indiNameBlockCount = file.readIntLE();
		indiNameStart = file.readIntLE();
		indiNameOffset = file.readIntLE();
		indiNameHighOffset = file.readIntLE();
		firstFreeIndiName = file.readIntLE();
		indiNameFreeCount = file.readIntLE();
		
		nameCount = file.readIntLE();
		nameBlockCount = file.readIntLE();
		nameStart = file.readIntLE();
		nameOffset = file.readIntLE();
		nameHighOffset = file.readIntLE();
		
		otherNameCount = file.readIntLE();
		otherNameBlockCount = file.readIntLE();
		otherNameStart = file.readIntLE();
		otherNameOffset = file.readIntLE();
		otherNameHighOffset = file.readIntLE();
		
		marriageCount = file.readIntLE();
		marriageBlockCount = file.readIntLE();
		firstMarriage = file.readIntLE();
		firstFreeMarriage = file.readIntLE();
		marriageFreeCount = file.readIntLE();
		
		noteCount = file.readIntLE();
		noteBlockCount = file.readIntLE();
		firstNote = file.readIntLE();
		firstFreeNote = file.readIntLE();
		noteFreeCount = file.readIntLE();
		
		citationCount = file.readIntLE();
		citationBlockCount = file.readIntLE();
		firstCitation = file.readIntLE();
		firstFreeCitation = file.readIntLE();
		citationFreeCount = file.readIntLE();
		
		sourceCount = file.readIntLE();
		sourceBlockCount = file.readIntLE();
		firstSource = file.readIntLE();
		firstFreeSource = file.readIntLE();
		sourceFreeCount = file.readIntLE();
		
		repositoryCount = file.readIntLE();
		repositoryBlockCount = file.readIntLE();
		firstRepository = file.readIntLE();
		firstFreeRepository = file.readIntLE();
		repositoryFreeCount = file.readIntLE();
		
		familyLinkCount = file.readIntLE();
		familyLinkBlockCount = file.readIntLE();
		firstFamilyLink = file.readIntLE();
		firstFreeFamilyLink = file.readIntLE();
		familyLinkFreeCount = file.readIntLE();
		
		eventTypeCount = file.readIntLE();
		eventTypeBlockCount = file.readIntLE();
		firstEventType = file.readIntLE();
		firstFreeEventType = file.readIntLE();
		eventTypeFreeCount = file.readIntLE();
		
		mediaCount = file.readIntLE();
		mediaBlockCount = file.readIntLE();
		firstMedia = file.readIntLE();
		firstFreeMedia = file.readIntLE();
		mediaFreeCount = file.readIntLE();
		
		eventCount = file.readIntLE();
		eventBlockCount = file.readIntLE();
		firstEvent = file.readIntLE();
		firstFreeEvent = file.readIntLE();
		eventFreeCount = file.readIntLE();
		
		contactCount = file.readIntLE();
		contactBlockCount = file.readIntLE();
		firstContact = file.readIntLE();
		firstFreeContact = file.readIntLE();
		contactFreeCount = file.readIntLE();
		
		//read 400 spare bytes
		byte[] extra = new byte[400];
		file.readFully(extra);
	}
}

class PAF5UserData {
	
	static final int size = 768;
	
	String name;
	String address1;
	String address2;
	String address3;
	String address4;
	String country;
	String phone;
	String submitterAFN;
	String submitterEmail;
	long submitterUId;
	long defaultLanguage;
		
	
	public void read(LEndianRandomAccessFile file) throws IOException
	{
		byte[] buffer96 = new byte[96];
		file.read(buffer96, 0, 84);
		name = PAF5Parser.getNullTermStringFromBuff(buffer96);
		file.read(buffer96, 0, 84);
		address1 = PAF5Parser.getNullTermStringFromBuff(buffer96);
		file.read(buffer96, 0, 84);
		address2 = PAF5Parser.getNullTermStringFromBuff(buffer96);
		file.read(buffer96, 0, 84);
		address3 = PAF5Parser.getNullTermStringFromBuff(buffer96);
		file.read(buffer96, 0, 84);
		address4 = PAF5Parser.getNullTermStringFromBuff(buffer96);
		file.read(buffer96, 0, 84);
		country = PAF5Parser.getNullTermStringFromBuff(buffer96);
		file.read(buffer96, 0, 52);
		phone = PAF5Parser.getNullTermStringFromBuff(buffer96);
		file.read(buffer96, 0, 28);
		submitterAFN = PAF5Parser.getNullTermStringFromBuff(buffer96);
		file.read(buffer96, 0, 84);
		submitterEmail = PAF5Parser.getNullTermStringFromBuff(buffer96);
		submitterUId = file.readLongLE();
		defaultLanguage = file.readLongLE();
		
		//read extra space - 96 bytes
		file.read(buffer96, 0, 96);
		
	}
}

class LEndianRandomAccessFile extends RandomAccessFile
{
	public LEndianRandomAccessFile(String fileName, String mode) throws FileNotFoundException
	{
		super(fileName, mode);
	}
	
	public LEndianRandomAccessFile(File file, String mode) throws FileNotFoundException
	{
		super(file, mode);
	} 
	
	
	/**
	 * @return next four bytes of the stream as a Little Endian integer
	 * @throws IOException 
	 */
	public int readIntLE() throws IOException {
		int b4 = read();
		int b3 = read();
		int b2 = read();
		int b1 = read();
		return b4 + (b3 << 8) + (b2 << 16) + (b1 << 24);
	}
	
	public short readShortLE() throws IOException {
		int b2 = read();
		int b1 = read();
		return (short)(b2 + (b1 << 8));
	}
	
	public long readLongLE() throws IOException {
		int b8 = read();
		int b7 = read();
		int b6 = read();
		int b5 = read();
		int b4 = read();
		int b3 = read();
		int b2 = read();
		int b1 = read();
			
		return (b8 + ((long)b7 << 8) + ((long)b6 << 16) + ((long)b5 << 24) + 
				((long)b4 << 32) + ((long)b3 << 40) + ((long)b2 << 48) +
				((long)b1 << 56));
	}
}
