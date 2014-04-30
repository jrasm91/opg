package edu.byu.cs.roots.opg.chart.selectvertical;

import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.ChartDrawInfo;
import edu.byu.cs.roots.opg.fonts.OpgFont;
import edu.byu.cs.roots.opg.model.Individual;
import edu.byu.cs.roots.opg.model.OpgSession;


public class BoxLayoutManager {
	
	private static BoxLayoutManager instance;
	private ArrayList<ArrayList<BoxLayout>> layouts = new ArrayList<ArrayList<BoxLayout>>();
	private ArrayList<BoxLayout> allLayouts = new ArrayList<BoxLayout>();

	public static BoxLayoutManager instance() {
		if(instance == null)
			instance = new BoxLayoutManager();
		return instance;
	}
	
	//TODO: Optimize by sorting these by number of lines.
	public BoxLayoutManager(){
		
		
		//Five Lines
		ArrayList<BoxLayout> fiveLines = new ArrayList<BoxLayout>();
		fiveLines.add(new FiveLines1());
		fiveLines.add(new FiveLines2());
		fiveLines.add(new FiveLines3());
		
		//Four Lines
		ArrayList<BoxLayout> fourLines = new ArrayList<BoxLayout>();
		fourLines.add(new FourLines1());
		fourLines.add(new FourLines2());
		fourLines.add(new FourLines3());
		fourLines.add(new FourLines4());
		fourLines.add(new FourLines5());
		
		
		//Three Lines
		ArrayList<BoxLayout> threeLines = new ArrayList<BoxLayout>();
		threeLines.add(new ThreeLine1());
		threeLines.add(new ThreeLine2());
		threeLines.add(new ThreeLine3());
		threeLines.add(new ThreeLine4());
		threeLines.add(new ThreeLine5());
		threeLines.add(new ThreeLine6());
		threeLines.add(new ThreeLine7());
		threeLines.add(new ThreeLine8());
		threeLines.add(new ThreeLine9());
		threeLines.add(new ThreeLine10());
		threeLines.add(new ThreeLine11());
		threeLines.add(new ThreeLine12());
		threeLines.add(new ThreeLine13());
		threeLines.add(new ThreeLine14());
		threeLines.add(new ThreeLine15());
		
		
		//Two Lines
		ArrayList<BoxLayout> twoLines = new ArrayList<BoxLayout>();
		twoLines.add(new TwoLineFullBirthDeathDates());
		twoLines.add(new TwoLineBirthDeathYears());
		twoLines.add(new TwoLineBirthDatePlaceFull());
		twoLines.add(new TwoLineBirthDateFull());
		twoLines.add(new TwoLineBirthYear());
		twoLines.add(new TwoLineDeathDatePlaceFull());
		twoLines.add(new TwoLineDeathDateFull());
		twoLines.add(new TwoLineDeathYear());

		//One Line
		ArrayList<BoxLayout> oneLine = new ArrayList<BoxLayout>();
		oneLine.add(new OneLineFullNameAllYears());
		//oneLine.add(new OneLineMidInitAllYears());
		//oneLine.add(new OneLineFullFirstLastAllYears());
		//oneLine.add(new OneLineFullFirstLastBirthYear());
		//oneLine.add(new OneLineFullFirstLastDeathYear());
		//oneLine.add(new OneLineAbbrFirstAllYears());
		//oneLine.add(new OneLineAbbrFirstBirthDate());
		//oneLine.add(new OneLineAbbrFirstDeathDate());
		oneLine.add(new OneLineAbbrName());
		ArrayList<BoxLayout> defaultLine = new ArrayList<BoxLayout>();
		
		defaultLine.add(new OneLineAbbrName());
		layouts.add(defaultLine);
		layouts.add(oneLine);
		layouts.add(twoLines);
		layouts.add(threeLines);
		layouts.add(fourLines);
		layouts.add(fiveLines);
		
		for( int i=layouts.size()-1; i >= 0; i--)
			allLayouts.addAll(layouts.get(i));
		
	}
	public double drawBox(OpgSession session, ChartDrawInfo chart, double fontSize, VerticalChartOptions ops,
			double x, double y, double width, double height,Individual indi, String dupLabel){
		
		BoxLayout b = findBestFit(fontSize, session.getOpgOptions().getFont(), width, height, indi, dupLabel);
		if(b==null){
			b = new OneLineAbbrName();
		}
		b.draw(session, chart, fontSize, ops, x, y, width, height, indi, dupLabel);
		return 0;//b.getHeight();
	}
	public BoxLayout findBestFit(double fontSize,OpgFont opgFont, double width, double height, Individual indi, String dupLabel){
//		for(BoxLayout box : layouts){
//			if(box.canFit(indi, width , height, opgFont, fontSize, dupLabel))
//				return box;
//		}
		return null;
	}
	
	public double getHieght(OpgSession session, double fontSize, VerticalChartOptions ops, double width, double height,Individual indi, String dupLabel){
		
		BoxLayout b = findBestFit(fontSize, session.getOpgOptions().getFont(), width, height, indi, dupLabel);
		if(b==null){
			b = new OneLineAbbrName();
		}
		return b.getTextHeight(height,session.getOpgOptions().getFont(),fontSize);
	}
	
	
	
	public ArrayList<String> getText(BoxFormat fbox,Individual indi, String dupLabel){
		double fontSize = fbox.nameFontSize;
		OpgFont opgFont = BoxFormat.font;
		double width = fbox.width;
		double height = fbox.height;
		
		int lines = (int) height/((int) (fontSize*1.1) + 3);
		lines = Math.min(lines, 5);
		for(int i=lines; i >= 0; i--)
		{
		for(BoxLayout box : layouts.get(i)){
			if(box.canFit(indi, width , height, opgFont, fontSize, dupLabel))
				return box.getContent(fbox, indi, dupLabel);
		}
		}
		BoxLayout b = layouts.get(0).get(0);
		return b.getContent(fbox, indi, dupLabel);
		//return null;
	}
	
	public ArrayList<TextLine> getTextLines(BoxFormat fbox,Individual indi, String dupLabel){
		double fontSize = fbox.nameFontSize;
		OpgFont opgFont = BoxFormat.font;
		double width = fbox.width;
		double height = fbox.height;
		int lines = (int) height/((int) (fontSize*1.1) + 3);
		lines = Math.min(lines, 5);
		for(int i=lines; i >= 0; i--)
		{
		for(BoxLayout box : layouts.get(i)){
			if(box.canFit(indi, width , height, opgFont, fontSize, dupLabel))
				return box.getTextLines(fbox, indi, dupLabel);
		}
		}
		BoxLayout b = layouts.get(0).get(0);
		return b.getTextLines(fbox, indi, dupLabel);
		//return null;
	}
	
	public BoxLayout getBL(BoxFormat fbox,Individual indi, String dupLabel){
		double fontSize = fbox.nameFontSize;
		OpgFont opgFont = BoxFormat.font;
		double width = fbox.width;
		double height = fbox.height;
		
		int lines = (int) height/((int) (fontSize*1.1) + 3);
		lines = Math.min(lines, 5);
		for(int i=lines; i >= 0; i--)
		{
		for(BoxLayout box : layouts.get(i)){
			if(box.canFit(indi, width , height, opgFont, fontSize, dupLabel))
				return box;
		}
		}
		BoxLayout b = layouts.get(0).get(0);
		return b;
		//return null;
	}
	
	
	public ArrayList<LineLayout> getLineLayouts(BoxFormat bf,Individual indi, String dupLabel){

		int lines = bf.getNumOfLines();
		for(int i=lines; i >= 0; i--)
		{
			for(BoxLayout box : layouts.get(i))
			{
				if(box.canFit(indi, bf.getWidth(),0,BoxFormat.font, bf.getNameFontSize(), dupLabel))
					return box.getLineLayouts();
			}
		}
		BoxLayout b = layouts.get(0).get(0);
		return b.getLineLayouts();
		//return null;
	}
	

}
