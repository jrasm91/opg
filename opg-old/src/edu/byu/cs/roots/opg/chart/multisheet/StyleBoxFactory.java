package edu.byu.cs.roots.opg.chart.multisheet;

import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.preset.templates.Layouts;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;

public class StyleBoxFactory{
	
	
	public static ArrayList<StylingBoxScheme> getStyleList(int ancGens, int descGens, boolean reverseArrow){
		
		StyleSchemes schemes = new StyleSchemes();
		
		if(ancGens == 0)
			return schemes.zero;
		else if (ancGens == 1)
		{
			if(reverseArrow){
				return schemes.one_yes_arrow;
			}
			else{
				return schemes.one_no_arrow;
			}
		}
		else if (ancGens == 2)
		{
			if(reverseArrow){
				return schemes.two_yes_arrow;
			}
			else{
				return schemes.two_no_arrow;
			}
		}
		else if (ancGens == 3)
		{
			if(reverseArrow){
				return schemes.three_yes_arrow;
			}
			else{
				return schemes.three_no_arrow;
			}
		}
		else// if (ancGens == 4)
		{
			if(reverseArrow){
				return schemes.four_yes_arrow;
			}
			else{
				return schemes.four_no_arrow;
			}
		}
		/*else
		{
			if(reverseArrow){
				return schemes.five_yes_arrow;
			}
			else{
				return schemes.four_no_arrow;
			}
		}*/
	}
	
public static ArrayList<StylingBoxScheme> getStyleListFor5(int ancGens, int descGens, boolean reverseArrow){
		
		StyleSchemes schemes = new StyleSchemes();
		
		if(ancGens == 0)
			return schemes.zero;
		else if (ancGens == 1)
		{
			if(reverseArrow){
				return schemes.one_from_five_yes_arrow;
			}
			else{
				return schemes.one_no_arrow;
			}
		}
		else if (ancGens == 2)
		{
			if(reverseArrow){
				return schemes.two_from_five_yes_arrow;
			}
			else{
				return schemes.two_no_arrow;
			}
		}
		else if (ancGens == 3)
		{
			if(reverseArrow){
				return schemes.three_from_five_yes_arrow;
			}
			else{
				return schemes.three_no_arrow;
			}
		}
		else if (ancGens == 4)
		{
			if(reverseArrow){
				return schemes.four_from_five_yes_arrow;
			}
			else{
				return schemes.four_no_arrow;
			}
		}
		else
		{
			if(reverseArrow){
				return schemes.five_yes_arrow;
			}
			else{
				return schemes.four_no_arrow;
			}
		}
	}
	
	public static class StyleSchemes{
		
		ArrayList<StylingBoxScheme> zero = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_no_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_yes_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_no_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_yes_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_no_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_yes_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> four_no_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> four_yes_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> five_no_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> five_yes_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> four_from_five_yes_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_from_five_yes_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_from_five_yes_arrow = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_from_five_yes_arrow = new ArrayList<StylingBoxScheme>();
		
		StylingBoxScheme standard_zero;
		StylingBoxScheme standard_one;
		StylingBoxScheme standard_one_no_arrow;
		StylingBoxScheme standard_one_yes_arrow;
		StylingBoxScheme standard_two;
		StylingBoxScheme standard_two_no_arrow;
		StylingBoxScheme standard_two_yes_arrow;
		StylingBoxScheme standard_three_no_arrow;
		StylingBoxScheme standard_three_yes_arrow;
		StylingBoxScheme standard_four_no_arrow;
		StylingBoxScheme standard_four_yes_arrow;
		StylingBoxScheme standard_five_no_arrow;
		StylingBoxScheme standard_five_yes_arrow;
		StylingBoxScheme standard_four_from_five_yes_arrow;
		StylingBoxScheme standard_three_from_five_yes_arrow;
		StylingBoxScheme standard_two_from_five_yes_arrow;
		StylingBoxScheme standard_one_from_five_yes_arrow;
		
		
		private StyleSchemes(){
			StylingBoxScheme s;
			StylingBox temp;
			
			s = (standard_zero = new StylingBoxScheme("Standard Default", 8.5));
			temp = new FullRootBox();
			//temp.intrudeWidth = 570;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			zero.add(s);
			
			s = (standard_one = new StylingBoxScheme("Standard Default", 8.5));
			temp = new FullRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(290);
			temp.setPermOffset(12);
			s.AncesByGenList.add(temp);
			temp = new FullRootBox();
			temp.setPermWidth(275);
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			one.add(s);
			
			s = (standard_one_no_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new ThreeRootBox();
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			one_no_arrow.add(s);
			
			s = (standard_one_yes_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new ThreeRootBox();
			temp.setPermWidth(135);
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			temp.setPermWidth(125);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			one_yes_arrow.add(s);
			
			s = (standard_two = new StylingBoxScheme("Standard Default", 8.5));
			temp = new FullRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(200);
			temp.setPermOffset(16);
			s.AncesByGenList.add(temp);
			temp = new FullSecondBox();
			temp.isIntruding = false;
			temp.setPermWidth(180);
			temp.setPermOffset(12);
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			temp.isIntruding = false;
			temp.setPermWidth(160);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			two.add(s);
			
			s = (standard_two_no_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new ThreeRootBox();
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new FullFourthBox();
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			two_no_arrow.add(s);
			
			s = (standard_two_yes_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new ThreeRootBox();
			temp.setPermWidth(135);
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			temp.setPermWidth(125);
			s.AncesByGenList.add(temp);
			temp = new FullFourthBox();
			temp.setPermWidth(115);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			two_yes_arrow.add(s);
			
			s = (standard_three_no_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new ThreeRootBox();
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new FullFourthBox();
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new FullFifthBox();
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			three_no_arrow.add(s);
			
			s = (standard_three_yes_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new ThreeRootBox();
			temp.setPermWidth(135);
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			temp.setPermWidth(125);
			s.AncesByGenList.add(temp);
			temp = new FullFourthBox();
			temp.setPermWidth(115);
			s.AncesByGenList.add(temp);
			temp = new FullFifthBox();
			temp.setPermWidth(105);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			three_yes_arrow.add(s);
			
			s = (standard_four_no_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new FullRootBox();
			temp.fontNameSize = 12.5;
			//temp.paddingAmount = 4;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullSecondBox();
			temp.intrudeWidth = 140;
			temp.setPermWidth(75);
			temp.fontNameSize = 11.5;
			//temp.paddingAmount = 4;
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			temp.isIntruding = false;
			temp.setPermWidth(130);
			//temp.paddingAmount = 4;
			s.AncesByGenList.add(temp);
			temp = new FullFourthBox();
			temp.setPermWidth(120);
			//temp.paddingAmount = 4;
			s.AncesByGenList.add(temp);
			temp = new FullFifthBox();
			temp.setPermWidth(110);
			temp.paddingAmount = 4;
			s.AncesByGenList.add(temp);
			four_no_arrow.add(s);
			
			s = (standard_four_yes_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new FullRootBox();
			temp.intrudeWidth = 145;
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new FullSecondBox();
			temp.intrudeWidth = 135;
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			temp.isIntruding = false;
			temp.setPermWidth(125);
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new FullFourthBox();
			temp.setPermWidth(115);
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new FullFifthBox();
			temp.setPermWidth(105);
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			four_yes_arrow.add(s);
			
			s = (standard_five_no_arrow = new StylingBoxScheme("Standard Default", 8.5));
			s.AncesByGenList.add(new FullRootBox());
			temp = new FullSecondBox();
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.isIntruding = true;
			temp.intrudeWidth = 130;
			temp.setPermWidth(60);
			s.AncesByGenList.add(temp);
			temp = new FullFourthBox();
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.setPermWidth(108);
			s.AncesByGenList.add(temp);
			temp = new FullFifthBox();
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.setPermWidth(96);
			s.AncesByGenList.add(temp);
			temp = new FullEndBox();
			temp.weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.setPermWidth(80);
			temp.paddingAmount = 2.5;
			temp.boxHeight = 17;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			five_no_arrow.add(s);
			
			s = (standard_five_yes_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new FullRootBox();
			temp.setPermWidth(60);
			s.AncesByGenList.add(temp);
			temp = new FullSecondBox();
			temp.setPermWidth(95);
			s.AncesByGenList.add(temp);
			temp = new FullThirdBox();
			temp.weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			temp.isIntruding = true;
			temp.intrudeWidth = 130;
			temp.setPermWidth(40);
			s.AncesByGenList.add(temp);
			temp = new FullFourthBox();
			temp.weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			temp.setPermWidth(108);
			s.AncesByGenList.add(temp);
			temp = new FullFifthBox();
			temp.weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			temp.setPermWidth(96);
			s.AncesByGenList.add(temp);
			temp = new FullEndBox();
			temp.weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.setPermWidth(80);
			temp.boxHeight = 17;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(new FullRootBox());
			five_yes_arrow.add(s);
			
			s = (standard_four_from_five_yes_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new FullRootBox();
			temp.setPermWidth(60);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullSecondBox();
			temp.isIntruding = false;
			temp.setPermWidth(135);
			temp.layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new FullFourthBox();
			temp.weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			temp.setPermWidth(108);
			s.AncesByGenList.add(temp);
			temp = new FullFifthBox();
			temp.weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			temp.setPermWidth(96);
			s.AncesByGenList.add(temp);
			temp = new FullEndBoxCompInfo();
			//temp.weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			//temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.setPermWidth(80);
			//temp.boxHeight = 17;
			s.AncesByGenList.add(temp);
			//s.DescByGenList.add(new FullRootBox());
			four_from_five_yes_arrow.add(s);
			
			s = (standard_three_from_five_yes_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new FullRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(145);
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullThirdBox();
			temp.setPermWidth(140);
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new FullFifthBox();
			temp.setPermWidth(106);
			s.AncesByGenList.add(temp);
			temp = new FullEndBoxCompInfo();
			//temp.weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			//temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.setPermWidth(80);
			//temp.boxHeight = 17;
			s.AncesByGenList.add(temp);
			//s.DescByGenList.add(new FullRootBox());
			three_from_five_yes_arrow.add(s);
			
			s = (standard_two_from_five_yes_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new FullRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(145);
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullThirdBox();
			temp.setPermWidth(140);
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new FullFifthBox();
			temp.setPermWidth(96);
			s.AncesByGenList.add(temp);
			//s.DescByGenList.add(new FullRootBox());
			two_from_five_yes_arrow.add(s);
			
			s = (standard_one_from_five_yes_arrow = new StylingBoxScheme("Standard Default", 8.5));
			temp = new FullRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(145);
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullThirdBox();
			temp.setPermWidth(140);
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			//s.DescByGenList.add(new FullRootBox());
			one_from_five_yes_arrow.add(s);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static class FullRootBox extends StylingBox{
		public FullRootBox(){
			boxHeight = 49;
			setPermWidth(80);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 8;
			fontNameSize = 11.5;
			setPermOffset(0);
			borderlineWidth = 1.3;
			cornerCurve = 12;
			paddingAmount = 2;
			
			endLineArrowShaftLength = 10;
			endLineArrowShaftHeight = 30;
			endLineArrowHeadLength = 10;
			endLineArrowHeadHeight = 40;
			endLineArrowFontSize = 10;
			
			isIntruding = true;
			intrudeWidth = 150;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(11.0);
			textPositions.add(20.0);
			textPositions.add(28.0);
			textPositions.add(37.0);
			textPositions.add(45.0);
			textPositions.add(54.0);
			textPositions.add(62.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class ThreeRootBox extends StylingBox{
		public ThreeRootBox(){
			boxHeight = 46;
			setPermWidth(140);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 8;
			fontNameSize = 10.5;
			setPermOffset(15);
			borderlineWidth = 1.3;
			cornerCurve = 12;
			paddingAmount = 2;
			
			endLineArrowShaftLength = 20;
			endLineArrowShaftHeight = 30;
			endLineArrowHeadLength = 12;
			endLineArrowHeadHeight = 40;
			endLineArrowFontSize = 10;
			
			isIntruding = false;
			intrudeWidth = 150;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(9.0);
			textPositions.add(18.0);
			textPositions.add(26.5);
			textPositions.add(35.0);
			textPositions.add(42.0);
			textPositions.add(50.0);
			textPositions.add(57.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullSecondBox extends StylingBox{
		public FullSecondBox(){
			boxHeight = 46;
			setPermWidth(75);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 8;
			fontNameSize = 10.5;
			setPermOffset(0);
			borderlineWidth = 1.1;
			cornerCurve = 9;
			paddingAmount = 2;
			
			endLineArrowShaftLength = 20;
			endLineArrowShaftHeight = 13;
			endLineArrowHeadLength = 10;
			endLineArrowHeadHeight = 20;
			endLineArrowFontSize = 8;
			
			isIntruding = true;
			intrudeWidth = 140;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(9.0);
			textPositions.add(18.0);
			textPositions.add(26.5);
			textPositions.add(35.0);
			textPositions.add(42.0);
			textPositions.add(50.0);
			textPositions.add(57.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullThirdBox extends StylingBox{
		public FullThirdBox(){
			boxHeight = 38;
			setPermWidth(130);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 8;
			fontNameSize = 9.5;
			setPermOffset(10);
			borderlineWidth = 1;
			cornerCurve = 8;
			paddingAmount = 2;
			
			endLineArrowShaftLength = 20;
			endLineArrowShaftHeight = 13;
			endLineArrowHeadLength = 10;
			endLineArrowHeadHeight = 20;
			endLineArrowFontSize = 8;
			
			isIntruding = false;
			intrudeWidth = 160;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(8.5);
			textPositions.add(17.0);
			textPositions.add(25.0);
			textPositions.add(33.5);
			textPositions.add(41.5);
			textPositions.add(49.5);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullFourthBox extends StylingBox{
		public FullFourthBox(){
			boxHeight = 33;
			setPermWidth(120);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 7;
			//fontSize=8;
			fontNameSize = 8.5;
			//fontNameSize=9;
			setPermOffset(6);
			borderlineWidth = 0.9;
			cornerCurve = 7;
			paddingAmount = 2;
			
			endLineArrowShaftLength = 20;
			endLineArrowShaftHeight = 13;
			endLineArrowHeadLength = 10;
			endLineArrowHeadHeight = 20;
			endLineArrowFontSize = 8;
			
			isIntruding = false;
			intrudeWidth = 80;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(8.0);
			textPositions.add(15.0);
			textPositions.add(22.0);
			textPositions.add(29.0);
			textPositions.add(36.0);
			textPositions.add(43.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullFifthBox extends StylingBox{
		public FullFifthBox(){
			boxHeight = 29;
			setPermWidth(110);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 6;
			//fontSize = 7.5;
			fontNameSize = 7.5;
			setPermOffset(6);
			borderlineWidth = 0.7;
			cornerCurve = 6;
			paddingAmount = 2;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 13;
			endLineArrowHeadLength = 10;
			endLineArrowHeadHeight = 20;
			endLineArrowFontSize = 6;
			
			isIntruding = false;
			intrudeWidth = 80;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(7.0);
			textPositions.add(14.0);
			textPositions.add(20.0);
			textPositions.add(26.0);
			textPositions.add(32.0);
			textPositions.add(38.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullEndBox extends StylingBox{
		public FullEndBox(){
			boxHeight = 18.75;
			setPermWidth(92);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 6;
			fontNameSize = 7;
			setPermOffset(4);
			borderlineWidth = 0.6;
			cornerCurve = 5;
			paddingAmount = 2;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 13;
			endLineArrowHeadLength = 10;
			endLineArrowHeadHeight = 20;
			endLineArrowFontSize = 6;
			
			isIntruding = false;
			intrudeWidth = 60;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(7.0);
			textPositions.add(14.0);
			textPositions.add(20.0);
			textPositions.add(26.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullEndBoxCompInfo extends StylingBox{
		public FullEndBoxCompInfo(){
			boxHeight = 29;
			setPermWidth(92);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 6;
			fontNameSize = 7;
			setPermOffset(4);
			borderlineWidth = 0.6;
			cornerCurve = 5;
			paddingAmount = 2;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 13;
			endLineArrowHeadLength = 10;
			endLineArrowHeadHeight = 20;
			endLineArrowFontSize = 6;
			
			isIntruding = false;
			intrudeWidth = 60;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(7.0);
			textPositions.add(14.0);
			textPositions.add(20.0);
			textPositions.add(26.0);
			textPositions.add(32.0);
			textPositions.add(38.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	
	
}






