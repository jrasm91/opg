package edu.byu.cs.roots.opg.chart.portrait;

import java.util.ArrayList;

import org.apache.log4j.Layout;

import edu.byu.cs.roots.opg.chart.preset.templates.Layouts;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox.TextDirection;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;

public class StyleBoxFactory{
	
		
	public static ArrayList<StylingBoxScheme> getStyleList(int ancGens, int descGens, boolean includeSpouse){
		
		StyleSchemes schemes = new StyleSchemes();
		
			
		
		if(ancGens == 1 && descGens == 0){
			return schemes.zero_one;
		}
		else if(ancGens == 2 && descGens == 0){
			return schemes.zero_two;
		}
		else if (ancGens == 3 && descGens == 0){
			return schemes.zero_three;
		}
		else if (ancGens == 4 && descGens == 0){
			return schemes.zero_four;
		}
		else if (ancGens == 5 && descGens == 0){
			return schemes.zero_five;
		}
		else if (ancGens == 6 && descGens == 0){
			return schemes.zero_six;
		}
		else if (ancGens == 7 && descGens == 0){
			return schemes.zero_seven;
		}
		else if (ancGens == 8 && descGens == 0){
			return schemes.zero_eight;
		}
		else if (ancGens == 9 && descGens == 0){
			return schemes.zero_nine;
		}
		else if (ancGens == 10 && descGens == 0){
			return schemes.zero_ten;
		}
		else if (ancGens == 11 && descGens == 0){
			return schemes.zero_eleven;
		}
		else if (ancGens == 12 && descGens == 0){
			return schemes.zero_twelve;
		}
		else if (ancGens == 19 && descGens == 0)
		{
			return schemes.zero_nineteen;
		}
		else if (ancGens == 1 && descGens == 1){
			return schemes.one_one;
		}
		else if (ancGens == 2 && descGens == 1){
			return schemes.one_two;
		}
		else if (ancGens == 3 && descGens == 1){
			return schemes.one_three;
		}
		else if (ancGens == 4 && descGens == 1){
			return schemes.one_four;
		}
		else if (ancGens == 5 && descGens == 1){
			return schemes.one_five;
		}
		else if (ancGens == 6 && descGens == 1){
			return schemes.one_six;
		}
		else if (ancGens == 7 && descGens == 1){
			return schemes.one_seven;
		}
		else if (ancGens == 8 && descGens == 1){
			return schemes.one_eight;
		}
		else if (ancGens == 9 && descGens == 1){
			return schemes.one_nine;
		}
		else if (ancGens == 10 && descGens == 1){
			return schemes.one_ten;
		}
		else if (ancGens == 11 && descGens == 1){
			return schemes.one_eleven;
		}
		else if (ancGens == 1 && descGens == 2){
			return schemes.two_one;
		}
		else if (ancGens == 2 && descGens == 2){
			return schemes.two_two;
		}
		else if (ancGens == 3 && descGens == 2){
			return schemes.two_three;
		}
		else if (ancGens == 4 && descGens == 2){
			return schemes.two_four;
		}
		else if (ancGens == 5 && descGens == 2){
			return schemes.two_five;
		}
		else if (ancGens == 6 && descGens == 2){
			return schemes.two_six;
		}
		else if (ancGens == 7 && descGens == 2){
			return schemes.two_seven;
		}
		else if (ancGens == 8 && descGens == 2){
			return schemes.two_eight;
		}
		else if (ancGens == 9 && descGens == 2){
			return schemes.two_nine;
		}
		else if (ancGens == 10 && descGens == 2){
			return schemes.two_ten;
		}
		else if (ancGens == 1 && descGens == 3){
			return schemes.three_one;
		}
		else if (ancGens == 2 && descGens == 3){
			return schemes.three_two;
		}
		else if (ancGens == 3 && descGens == 3){
			return schemes.three_three;
		}
		else if (ancGens == 4 && descGens == 3){
			return schemes.three_four;
		}
		else if (ancGens == 5 && descGens == 3){
			return schemes.three_five;
		}
		else if (ancGens == 6 && descGens == 3){
			return schemes.three_six;
		}
		else if (ancGens == 7 && descGens == 3){
			return schemes.three_seven;
		}
		else if (ancGens == 8 && descGens == 3){
			return schemes.three_eight;
		}
		else if (ancGens == 9 && descGens == 3){
			return schemes.three_nine;
		}
		else if (ancGens == 2 && descGens == 6){
			return schemes.six_two;
		}
		else if (ancGens == 2 && descGens == 4){
			return schemes.six_four;
		}
		else if (descGens*2 + ancGens > 19){
			return schemes.undefined2;
		}
		else {
			return schemes.undefined;
		}
		
	}
	
	public static class StyleSchemes{
		ArrayList<StylingBoxScheme> zero_one = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_two = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_three = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_four = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_five = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_six = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_seven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_eight = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_nine = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_ten = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_eleven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_twelve = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_nineteen = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_one = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_two = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_three = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_four = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_five = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_six = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_seven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_eight = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_nine = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_ten = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_eleven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_one = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_two = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_three = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_four = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_five = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_six = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_seven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_eight = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_nine = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_ten = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_one = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_two = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_three = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_four = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_five = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_six = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_seven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_eight = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_nine = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> six_two = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> six_four = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> undefined = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> undefined2 = new ArrayList<StylingBoxScheme>();
		
		StylingBoxScheme standard_Zero_One_36;
		StylingBoxScheme standard_Zero_One_24;
		StylingBoxScheme standard_Zero_Two_36;
		StylingBoxScheme standard_Zero_Three_36;
		StylingBoxScheme standard_Zero_Four_36;
		StylingBoxScheme standard_Zero_Five_36;
		StylingBoxScheme standard_Zero_Six_36;
		StylingBoxScheme standard_Zero_Seven_36;
		StylingBoxScheme standard_Zero_Eight_36;
		StylingBoxScheme standard_Zero_Nine_36;
		StylingBoxScheme standard_Zero_Ten_36;
		StylingBoxScheme standard_Zero_Eleven_36;
		StylingBoxScheme standard_Zero_Twelve_36;
		StylingBoxScheme standard_Zero_Nineteen_36;
		StylingBoxScheme standard_One_One_36;
		StylingBoxScheme standard_One_Two_36;
		StylingBoxScheme standard_One_Three_36;
		StylingBoxScheme standard_One_Four_36;
		StylingBoxScheme standard_One_Five_36;
		StylingBoxScheme standard_One_Six_36;
		StylingBoxScheme standard_One_Seven_36;
		StylingBoxScheme standard_One_Eight_36;
		StylingBoxScheme standard_One_Nine_36;
		StylingBoxScheme standard_One_Ten_36;
		StylingBoxScheme standard_One_Eleven_36;
		StylingBoxScheme standard_Two_One_36;
		StylingBoxScheme standard_Two_Two_36;
		StylingBoxScheme standard_Two_Three_36;
		StylingBoxScheme standard_Two_Four_36;
		StylingBoxScheme standard_Two_Five_36;
		StylingBoxScheme standard_Two_Six_36;
		StylingBoxScheme standard_Two_Seven_36;
		StylingBoxScheme standard_Two_Eight_36;
		StylingBoxScheme standard_Two_Nine_36;
		StylingBoxScheme standard_Three_One_36;
		StylingBoxScheme standard_Three_Two_36;
		StylingBoxScheme standard_Three_Three_36;
		StylingBoxScheme standard_Three_Four_36;
		StylingBoxScheme standard_Three_Five_36;
		StylingBoxScheme standard_Three_Six_36;
		StylingBoxScheme standard_Three_Seven_36;
		StylingBoxScheme standard_Three_Eight_36;
		StylingBoxScheme standard_Three_Nine_36;
		StylingBoxScheme standard_Two_Ten_36;
		StylingBoxScheme standard_Six_Two_36;
		StylingBoxScheme eleven_seventeen_Six_Two_36;
		StylingBoxScheme standard_Six_Four_36;
		StylingBoxScheme eleven_seventeen_Six_Four_36;
		StylingBoxScheme standard_Default;
		StylingBoxScheme standard_Default2;
		private StyleSchemes(){
			StylingBoxScheme s;
			StylingBox temp;
			
			//Zero Descendant set of Boxes
			//Zero_One
			s = (standard_Zero_One_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new SmallChartRootBox();
			temp.isIntruding = false;
			temp.intrudeWidth = 1200;
			temp.setPermWidth(950);
			temp.rootBackOffset = 220;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new SmallChartSecondBox();
			temp.paddingAmount = 58;
			s.AncesByGenList.add(temp);
			zero_one.add(s);
			
			s = (standard_Zero_One_24 = new StylingBoxScheme("Standard 2'", 24));
			temp = new SmallChartRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(950.0);
			temp.setPermOffset(10.0);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new SmallChartSecondBox();
			temp.setPermWidth(700.0);
			s.AncesByGenList.add(temp);
			zero_one.add(s);
			
			//Zero_Two
			s = (standard_Zero_Two_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new MediumChartRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(950);
			temp.rootBackOffset = 20;
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChartSecondBox();
			temp.isIntruding = false;
			temp.setPermWidth(700);
			s.AncesByGenList.add(temp);
			temp = new LargeChartThirdBox();
			temp.paddingAmount = 28;
			s.AncesByGenList.add(temp);
			zero_two.add(s);
			
			//Zero_Three
			s = (standard_Zero_Three_36 = new StylingBoxScheme("Standard 3 '", 36));
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(500);
			temp.intrudeWidth = 500;
			temp.rootBackOffset = 50;
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChartFourthBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChartFourthBox2();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChartFifthBox();
			temp.paddingAmount = 11;
			s.AncesByGenList.add(temp);
			zero_three.add(s);
			
			//Zero_Four
			s = (standard_Zero_Four_36 = new StylingBoxScheme("Standard 3 '", 36));
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(500);
			temp.intrudeWidth = 500;
			temp.rootBackOffset = 50;
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChartFourthBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChartFourthBox2();
			s.AncesByGenList.add(temp);
			temp = new LargeChartFifthBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChartSixthBox();
			temp.paddingAmount = 8.5;
			s.AncesByGenList.add(temp);
			zero_four.add(s);
			
			//Zero_Five
			s = (standard_Zero_Five_36 = new StylingBoxScheme("Standard 3 '", 36));
			temp = new LargeChartRootBox();
			temp.setPermWidth(500);
			temp.intrudeWidth = 500;
			temp.isIntruding = false;
			temp.setPermOffset(40);
			temp.rootBackOffset = 40;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChartFourthBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartFourthBox2();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartFifthBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSixthBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSeventhBox();
			temp.paddingAmount = 5.5;
			s.AncesByGenList.add(temp);
			zero_five.add(s);
			
			//Zero_Six
			s = (standard_Zero_Six_36 = new StylingBoxScheme("Standard 3 '", 36));
			temp = new LargeChartRootBox();
			temp.setPermWidth(520);
			temp.rootBackOffset = 70;
			temp.setPermOffset(70);
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(50);
			temp.setPermWidth(370);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(320);
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermWidth(270);
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermWidth(220);
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermWidth(190);
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermWidth(170);
			temp.setPermOffset(40);
			//temp.paddingAmount = 10;
			s.AncesByGenList.add(temp);
			zero_six.add(s);
			
			//Zero_Seven
			s = (standard_Zero_Seven_36 = new StylingBoxScheme("Standard 3 '", 36));
			temp = new LargeChartRootBox();
			temp.setPermWidth(450);
			temp.rootBackOffset = 70;
			temp.setPermOffset(70);
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			//temp.paddingAmount = 5;
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			zero_seven.add(s);
			
			//Zero_Eight
			s = (standard_Zero_Eight_36 = new StylingBoxScheme("Standard 3 '", 36));
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(450);
			temp.setPermOffset(30);
			temp.rootBackOffset = 40;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			temp.setPermOffset(30);
			s.AncesByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(30);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(30);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(30);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(30);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(30);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(30);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			//temp.paddingAmount = 5;
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			zero_eight.add(s);
			
			//Zero_Nine
			s = (standard_Zero_Nine_36 = new StylingBoxScheme("Standard 3 '", 36));
			temp = new LargeChartSmallestRootBox();
			temp.isIntruding = false;
			temp.intrudeWidth = 300;
			temp.setPermWidth(350);
			temp.rootBackOffset = 40;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			s.AncesByGenList.add(temp);
			zero_nine.add(s);
			
			//Zero_Ten
			s = (standard_Zero_Ten_36 = new StylingBoxScheme("Standard 3 '", 36));
			temp = new LargeChartSmallestRootBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			s.AncesByGenList.add(temp);
			temp = new LargeChart11thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart9thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			s.AncesByGenList.add(temp);
			zero_ten.add(s);
			
			//Zero_Eleven
			s = (standard_Zero_Eleven_36 = new StylingBoxScheme("Standard 3 '", 36));
			temp = new LargeChartSmallestRootBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermWidth(140);
			s.AncesByGenList.add(temp);
			temp = new LargeChart11thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 260;
			temp.setPermWidth(130);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart9thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			s.AncesByGenList.add(temp);
			zero_eleven.add(s);
			
			//Zero_Twelve
			s = (standard_Zero_Twelve_36 = new StylingBoxScheme("Standard 3 '", 36));
			temp = new LargeChartSmallestRootBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermWidth(140);
			s.AncesByGenList.add(temp);
			temp = new LargeChart11thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 260;
			temp.setPermWidth(130);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 260;
			temp.setPermWidth(130);
			s.AncesByGenList.add(temp);
			temp = new LargeChart9thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			s.AncesByGenList.add(temp);
			zero_twelve.add(s);
			
			//Zero_Nineteen
			s = (standard_Zero_Nineteen_36 = new StylingBoxScheme("Standard 3.5 '", 42));
			temp = new LargeChart11thSmallestBox();
			temp.layout = Layouts.textboxlayouts.OneLineAbbrName;
			temp.weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			temp.rootBackOffset = 10;
			temp.boxHeight = 25;
			temp.setPermWidth(50);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.cornerCurve = 10;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermWidth(50);
			temp.isIntruding = true;
			temp.intrudeWidth = 250;
			temp.boxHeight = 48;
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.cornerCurve = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			temp.setPermWidth(50);
			temp.boxHeight = 43;
			temp.isIntruding = true;
			temp.intrudeWidth = 225;
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.cornerCurve = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermWidth(50);
			temp.isIntruding = true;
			temp.intrudeWidth = 200;
			temp.layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 40;
			temp.cornerCurve = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 40;
			temp.cornerCurve = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 40;
			temp.cornerCurve = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 40;
			temp.cornerCurve = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 38;
			temp.cornerCurve = 10;
			temp.fontNameSize = 12;
			temp.setPermWidth(181);
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 38;
			temp.cornerCurve = 10;
			temp.fontNameSize = 12;
			temp.setPermWidth(181);
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 38;
			temp.fontNameSize = 12;
			temp.setPermWidth(181);
			temp.cornerCurve = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 29;
			temp.setPermWidth(150);
			temp.cornerCurve = 10;
			temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 29;
			temp.setPermWidth(150);
			temp.cornerCurve = 10;
			temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 29;
			temp.setPermWidth(150);
			temp.cornerCurve = 10;
			temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 29;
			temp.setPermWidth(150);
			temp.cornerCurve = 10;
			temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 25;
			temp.setPermWidth(134);
			temp.cornerCurve = 10;
			temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 25;
			temp.setPermWidth(134);
			temp.cornerCurve = 10;
			temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 25;
			temp.setPermWidth(134);
			temp.cornerCurve = 10;
			temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.boxHeight = 25;
			temp.setPermWidth(134);
			temp.cornerCurve = 10;
			temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(10);
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 18;
			temp.setPermWidth(125);
			temp.cornerCurve = 10;
			temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.fontNameSize = 6;
			temp.setPermWidth(89);
			temp.setPermOffset(10);
			temp.cornerCurve = 10;
			temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			zero_nineteen.add(s);
			
			//One Descendant set of Boxes
			//One_One
			s = (standard_One_One_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new MediumChartRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(950);
			temp.setPermOffset(50);
			temp.rootBackOffset = 50;
			s.AncesByGenList.add(temp);
			temp = new MediumChartRootBox();
			temp.isIntruding = false;
			temp.setPermWidth(950);
			temp.setPermOffset(50);
			temp.rootBackOffset = 50;
			s.DescByGenList.add(temp);
			temp = new LargeChartSecondBox();
			temp.paddingAmount = 58;
			temp.endLineArrowShaftLength = 100;
			s.AncesByGenList.add(temp);
			temp = new SmallChartDescBox();
			s.DescByGenList.add(temp);
			one_one.add(s);
			
			//One_Two
			s = (standard_One_Two_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.setPermWidth(600);
			temp.intrudeWidth = 600;
			temp.rootBackOffset = 100;
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			temp.setPermOffset(100);
			temp.setPermWidth(450);
			s.AncesByGenList.add(temp);
			temp = new LargeChart11thSmallestBox();
			temp.setPermWidth(400);
			temp.setPermOffset(100);
			temp.paddingAmount = 17;
			s.AncesByGenList.add(temp);
			temp = new SmallChartDescBox();
			s.DescByGenList.add(temp);
			one_two.add(s);
			
			//One_Three
			s = (standard_One_Three_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.setPermWidth(500);
			temp.intrudeWidth = 500;
			temp.rootBackOffset = 80;
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			temp.setPermOffset(70);
			temp.setPermWidth(400);
			s.AncesByGenList.add(temp);
			temp = new LargeChart11thSmallestBox();
			temp.setPermWidth(350);
			temp.setPermOffset(60);
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			temp.setPermWidth(300);
			temp.setPermOffset(50);
			temp.paddingAmount = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			s.DescByGenList.add(temp);
			one_three.add(s);
			
			//One_Four
			s = (standard_One_Four_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.setPermWidth(500);
			temp.intrudeWidth = 500;
			temp.rootBackOffset = 70;
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			temp.setPermOffset(50);
			temp.setPermWidth(400);
			s.AncesByGenList.add(temp);
			temp = new LargeChart11thSmallestBox();
			temp.setPermWidth(350);
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			temp.setPermWidth(300);
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			temp.setPermWidth(250);
			temp.setPermOffset(40);
			temp.paddingAmount = 6;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			s.DescByGenList.add(temp);
			one_four.add(s);
			
			//One_Five
			s = (standard_One_Five_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.setPermWidth(500);
			temp.intrudeWidth = 500;
			temp.rootBackOffset = 70;
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(50);
			temp.setPermWidth(350);
			s.AncesByGenList.add(temp);
			temp = new LargeChart9thSmallestBox();
			temp.setPermWidth(300);
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermWidth(250);
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			temp.setPermWidth(200);
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermWidth(150);
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			s.DescByGenList.add(temp);
			one_five.add(s);
			
			//One_Six
			s = (standard_One_Six_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(450);
			temp.intrudeWidth = 450;
			temp.rootBackOffset = 70;
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			s.DescByGenList.add(temp);
			one_six.add(s);
			
			//One_Seven
			s = (standard_One_Seven_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(300);
			temp.rootBackOffset = 70;
			temp.setPermOffset(70);
			temp.isIntruding = false;
			temp.intrudeWidth = 320;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(300);
			temp.rootBackOffset = 70;
			temp.isIntruding = false;
			temp.intrudeWidth = 320;
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(40);
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			s.DescByGenList.add(temp);
			one_seven.add(s);
			
			//One_Eight
			s = (standard_One_Eight_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(200);
			temp.isIntruding = true;
			temp.intrudeWidth = 320;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(200);
			temp.isIntruding = true;
			temp.intrudeWidth = 320;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			s.DescByGenList.add(temp);
			one_eight.add(s);
			
			//One_Nine
			s = (standard_One_Nine_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(200);
			temp.isIntruding = true;
			temp.intrudeWidth = 320;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(200);
			temp.isIntruding = true;
			temp.intrudeWidth = 320;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(20);
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermWidth(200);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			s.DescByGenList.add(temp);
			one_nine.add(s);
			
			//One_Ten
			s = (standard_One_Ten_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(150);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(130);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			temp = new LargeChart11thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			s.DescByGenList.add(temp);
			one_ten.add(s);
			
			//One_Eleven
			s = (standard_One_Eleven_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(100);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(80);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			temp = new LargeChart11thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 270;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 260;
			temp.setPermWidth(100);
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart9thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			s.DescByGenList.add(temp);
			one_eleven.add(s);
			
			//Two Descendant set of Boxes
			//Two_One
			s = (standard_Two_One_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermOffset(80);
			temp.rootBackOffset = 80;
			temp.setPermWidth(600);
			s.AncesByGenList.add(temp);
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.intrudeWidth = 600;
			temp.setPermOffset(80);
			temp.rootBackOffset = 80;
			temp.setPermWidth(600);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			temp.setPermWidth(500);
			temp.paddingAmount = 26.5;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(80);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			s.DescByGenList.add(temp);
			two_one.add(s);
			
			//Two_Two
			s = (standard_Two_Two_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermOffset(70);
			temp.rootBackOffset = 70;
			temp.setPermWidth(500);
			s.AncesByGenList.add(temp);
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.intrudeWidth = 500;
			temp.setPermOffset(70);
			temp.rootBackOffset = 70;
			temp.setPermWidth(500);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			temp.setPermWidth(400);
			temp.setPermOffset(70);
			s.AncesByGenList.add(temp);
			temp = new LargeChart13thSmallestBox();
			temp.setPermWidth(350);
			temp.setPermOffset(70);
			temp.paddingAmount = 20.5;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			s.DescByGenList.add(temp);
			two_two.add(s);
			
			//Two_Three
			s = (standard_Two_Three_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermOffset(60);
			temp.rootBackOffset = 60;
			temp.setPermWidth(450);
			s.AncesByGenList.add(temp);
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermOffset(60);
			temp.rootBackOffset = 60;
			temp.setPermWidth(450);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			temp.setPermOffset(60);
			s.AncesByGenList.add(temp);
			temp = new LargeChart13thSmallestBox();
			temp.setPermOffset(60);
			s.AncesByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(60);
			//temp.paddingAmount = 40;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			s.DescByGenList.add(temp);
			two_three.add(s);
			
			//Two_Four
			s = (standard_Two_Four_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.setPermOffset(40);
			temp.setPermWidth(450);
			temp.isIntruding = false;
			temp.rootBackOffset = 40;
			s.AncesByGenList.add(temp);
			temp = new LargeChartRootBox();
			temp.setPermOffset(40);
			temp.setPermWidth(450);
			temp.isIntruding = false;
			temp.rootBackOffset = 40;
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart11thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart9thSmallestBox();
			temp.setPermOffset(40);
			//temp.paddingAmount = 40;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			s.DescByGenList.add(temp);
			two_four.add(s);
			
			//Two_Five
			s = (standard_Two_Five_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.setPermWidth(400);
			temp.rootBackOffset = 30;
			temp.setPermOffset(30);
			temp.intrudeWidth = 400;
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			temp = new LargeChartRootBox();
			temp.setPermWidth(400);
			temp.rootBackOffset = 30;
			temp.setPermOffset(30);
			temp.isIntruding = false;
			temp.intrudeWidth = 400;
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart9thSmallestBox();
			temp.setPermOffset(50);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			//temp.paddingAmount = 4;
			//temp.paddingAmount = 5;
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(25);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermOffset(25);
			s.DescByGenList.add(temp);
			two_five.add(s);
			
			//Two_Six
			s = (standard_Two_Six_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(350);
			temp.intrudeWidth = 350;
			temp.isIntruding = false;
			temp.setPermOffset(30);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(350);
			temp.intrudeWidth = 350;
			temp.isIntruding = false;
			temp.rootBackOffset = 30;
			temp.setPermOffset(30);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			//temp.paddingAmount = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(30);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermOffset(30);
			s.DescByGenList.add(temp);
			two_six.add(s);
			
			//Two_Seven
			s = (standard_Two_Seven_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(200);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(200);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermOffset(25);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(25);
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(25);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermOffset(25);
			s.DescByGenList.add(temp);
			two_seven.add(s);
			
			//Two_Eight
			s = (standard_Two_Eight_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(150);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(150);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(150);
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermOffset(0);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(30);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			s.DescByGenList.add(temp);
			two_eight.add(s);
			
			//Two_Nine
			s = (standard_Two_Nine_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(150);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(130);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 260;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermWidth(300);
			s.DescByGenList.add(temp);
			two_nine.add(s);
			
			
			
			//Two_Ten
			s = (standard_Two_Ten_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(100);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(80);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 260;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 230;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart4thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermWidth(300);
			s.DescByGenList.add(temp);
			two_ten.add(s);
			
			//Three Descendant set of Boxes
			//Three_One
			s = (standard_Three_One_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermOffset(70);
			temp.rootBackOffset = 70;
			temp.setPermWidth(550);
			s.AncesByGenList.add(temp);
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.intrudeWidth = 550;
			temp.setPermOffset(70);
			temp.rootBackOffset = 70;
			temp.setPermWidth(550);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			temp.setPermWidth(450);
			//temp.paddingAmount = 50;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			s.DescByGenList.add(temp);
			temp = new LargeChart3rdDescBox();
			s.DescByGenList.add(temp);
			three_one.add(s);
			
			//Three_Two
			s = (standard_Two_Two_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermOffset(25);
			temp.rootBackOffset = 25;
			temp.setPermWidth(500);
			s.AncesByGenList.add(temp);
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.intrudeWidth = 500;
			temp.setPermOffset(25);
			temp.rootBackOffset = 25;
			temp.setPermWidth(500);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			temp.setPermWidth(400);
			temp.setPermOffset(25);
			s.AncesByGenList.add(temp);
			temp = new LargeChart13thSmallestBox();
			temp.setPermWidth(350);
			temp.setPermOffset(25);
			//temp.paddingAmount = 40;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(25);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			s.DescByGenList.add(temp);
			temp.setPermOffset(25);
			temp = new LargeChart3rdDescBox();
			s.DescByGenList.add(temp);
			three_two.add(s);
			
			//Three_Three
			s = (standard_Three_Three_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermOffset(20);
			temp.rootBackOffset = 20;
			temp.setPermWidth(450);
			s.AncesByGenList.add(temp);
			temp = new LargeChartRootBox();
			temp.isIntruding = false;
			temp.setPermOffset(20);
			temp.rootBackOffset = 20;
			temp.setPermWidth(450);
			s.DescByGenList.add(temp);
			temp = new LargeChart14thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart13thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(20);
			//temp.paddingAmount = 40;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			s.DescByGenList.add(temp);
			temp.setPermOffset(20);
			temp = new LargeChart3rdDescBox();
			s.DescByGenList.add(temp);
			three_three.add(s);
			
			//Three_Four
			s = (standard_Three_Four_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartRootBox();
			temp.setPermOffset(20);
			temp.setPermWidth(400);
			temp.isIntruding = false;
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			temp = new LargeChartRootBox();
			temp.setPermOffset(20);
			temp.setPermWidth(400);
			temp.isIntruding = false;
			temp.rootBackOffset = 20;
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart11thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart9thSmallestBox();
			temp.setPermOffset(20);
			//temp.paddingAmount = 40;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			s.DescByGenList.add(temp);
			temp.setPermOffset(10);
			temp = new LargeChart3rdDescBox();
			s.DescByGenList.add(temp);
			three_four.add(s);
			
			//Three_Five
			s = (standard_Three_Five_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(350);
			temp.intrudeWidth = 350;
			temp.isIntruding = false;
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(350);
			temp.intrudeWidth = 350;
			temp.isIntruding = false;
			temp.rootBackOffset = 10;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			s.AncesByGenList.add(temp);
			//temp = new LargeChart3rdSmallestBox();
			//s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			//temp.paddingAmount = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new LargeChart3rdDescBox();
			//temp.setPermWidth(280);
			s.DescByGenList.add(temp);
			three_five.add(s);
			
			//Three_Six
			s = (standard_Three_Six_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(200);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(200);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			//temp = new LargeChart2ndSmallestBox();
			//temp.setPermOffset(20);
			//s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(20);
			//temp.paddingAmount = 5;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart3rdDescBox();
			//temp.setPermWidth(280);
			s.DescByGenList.add(temp);
			three_six.add(s);
			
			//Three_Seven
			s = (standard_Three_Seven_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(150);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(150);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(150);
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermOffset(0);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			//temp = new LargeChart6thSmallestBox();
			//temp.setPermOffset(20);
			//s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart3rdDescBox();
			//temp.setPermWidth(280);
			s.DescByGenList.add(temp);
			three_seven.add(s);
			
			//Three_Eight
			s = (standard_Three_Eight_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(150);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(130);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermOffset(15);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 260;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			//temp = new LargeChart7thSmallestBox();
			//temp.setPermOffset(15);
			//s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermWidth(300);
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new LargeChart3rdDescBox();
			temp.setPermWidth(280);
			s.DescByGenList.add(temp);
			three_eight.add(s);
			
			//Three_Nine
			s = (standard_Three_Nine_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(100);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(80);
			temp.isIntruding = true;
			temp.intrudeWidth = 300;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 280;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 260;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermOffset(0);
			temp.isIntruding = true;
			temp.intrudeWidth = 230;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			temp = new LargeChart7thSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			//temp = new LargeChart4thSmallestBox();
			//temp.setPermOffset(10);
			//s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChart2ndSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.setPermOffset(10);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermWidth(300);
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new LargeChart3rdDescBox();
			temp.setPermWidth(280);
			s.DescByGenList.add(temp);
			three_nine.add(s);
			
			//Six_Two
			s = (standard_Six_Two_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(370);
			temp.cornerCurve = 10;
			temp.rootBackOffset = 50;
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(72);
			temp.boxHeight = 280;
			temp.direction = TextDirection.NINETY; 
			//temp.setPermWidth(280);
			//temp.boxHeight = 72;
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(61);
			temp.boxHeight = 260;
			temp.direction = TextDirection.NINETY;
			//temp.setPermWidth(260);
			//temp.boxHeight = 61;
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.textPositions.set(3, 79.0);
			temp.textPositions.set(4, 96.0);
			temp.boxHeight = 45;
			temp.setPermWidth(320);
			temp.setPermOffset(40);
			temp.rootBackOffset = 50;
			temp.setPermWidth(300);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new LargeChart3rdDescBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new LargeChart4thDescBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new LargeChart5thDescBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new LargeChart6thDescBox();
			s.DescByGenList.add(temp);
			six_two.add(s);			
			
			//Six_Two_Tiny
			s = (eleven_seventeen_Six_Two_36 = new 
					StylingBoxScheme("11\"x17\"", 36));
			temp = new LargeChartSmallestBox();
			temp.fontNameSize = 6;
			temp.setPermWidth(91);
			temp.boxHeight = 32;
			temp.layout = Layouts.textboxlayouts.FiveLine1;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			//temp.cornerCurve = 10;
			temp.rootBackOffset = 5;
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.fontNameSize = 6;
			temp.setPermWidth(8);
			temp.boxHeight = 90;
			temp.direction = TextDirection.NINETY; 
			temp.setPermOffset(5);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.fontNameSize = 6;
			temp.setPermWidth(8);
			temp.boxHeight = 90;
			temp.direction = TextDirection.NINETY; 
			temp.setPermOffset(5);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 13;
			temp.fontNameSize = 6;
			temp.setPermWidth(91);
			temp.setPermOffset(5);
			temp.rootBackOffset = 5;
			//temp.setPermWidth(300);
			s.DescByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 13;
			temp.fontNameSize = 6;
			temp.setPermWidth(91);
			temp.setPermOffset(5);
			s.DescByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 13;
			temp.fontNameSize = 6;
			temp.setPermWidth(91);
			temp.setPermOffset(5);
			s.DescByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 13;
			temp.fontNameSize = 6;
			temp.setPermWidth(91);
			temp.setPermOffset(5);
			s.DescByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 13;
			temp.fontNameSize = 6;
			temp.setPermWidth(91);
			temp.setPermOffset(5);
			s.DescByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.fontNameSize = 6;
			temp.setPermWidth(91);
			s.DescByGenList.add(temp);
			six_two.add(s);
			
			//Six_Four
			s = (standard_Six_Four_36 = new StylingBoxScheme("Standard 3'", 36));
			temp = new LargeChartSmallestRootBox();
			temp.setPermWidth(400);
			temp.cornerCurve = 10;
			temp.rootBackOffset = 100;
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(72);
			temp.boxHeight = 280;
			temp.direction = TextDirection.NINETY; 
			temp.setPermOffset(100);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(61);
			temp.boxHeight = 260;
			temp.direction = TextDirection.NINETY; 
			temp.setPermOffset(100);
			//temp.paddingAmount = 10;
			s.AncesByGenList.add(temp);
			temp = new LargeChartDescBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			temp.textPositions.set(3, 79.0);
			temp.textPositions.set(4, 96.0);
			temp.boxHeight = 45;
			temp.setPermOffset(100);
			temp.rootBackOffset = 100;
			temp.setPermWidth(350);
			s.DescByGenList.add(temp);
			temp = new LargeChart2ndDescBox();
			temp.setPermWidth(325);
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new LargeChart3rdDescBox();
			temp.setPermWidth(300);
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new LargeChart4thDescBox();
			temp.setPermWidth(275);
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			six_four.add(s);
			
			//Six_Four_Tiny
			s = (eleven_seventeen_Six_Four_36 = new 
					StylingBoxScheme("11\"x17\"", 36));
			temp = new LargeChartSmallestBox();
			temp.fontNameSize = 6;
			temp.setPermWidth(100);
			temp.boxHeight = 32;
			temp.layout = Layouts.textboxlayouts.FiveLine1;
			temp.weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			//temp.cornerCurve = 10;
			temp.rootBackOffset = 20;
			temp.isIntruding = false;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.fontNameSize = 6;
			temp.setPermWidth(8);
			temp.boxHeight = 100;
			temp.direction = TextDirection.NINETY; 
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.fontNameSize = 6;
			temp.setPermWidth(8);
			temp.boxHeight = 100;
			temp.direction = TextDirection.NINETY; 
			temp.setPermOffset(20);
			s.AncesByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 13;
			temp.fontNameSize = 6;
			temp.setPermWidth(100);
			temp.setPermOffset(20);
			temp.rootBackOffset = 5;
			s.DescByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 13;
			temp.fontNameSize = 6;
			temp.setPermWidth(100);
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 13;
			temp.fontNameSize = 6;
			temp.setPermWidth(100);
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new LargeChartSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 13;
			temp.fontNameSize = 6;
			temp.setPermWidth(100);
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			six_four.add(s);
			
			//Standard
			s = (standard_Default = new StylingBoxScheme("Standard Default", 36));
			temp = new LargeChartStandardRootBox();
			temp.rootBackOffset = 10;
			//temp.isIntruding = true;
			//temp.intrudeWidth = 200;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart9thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 250;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 200;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 20;
			temp.isIntruding = true;
			temp.intrudeWidth = 160;
			temp.setPermWidth(100);
			temp.setPermOffset(0);
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 150;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			s.AncesByGenList.add(temp);
			s.AncesByGenList.add(new EndBox2());
			//s.AncesByGenList.add(new EndBox2());
			s.DescByGenList.add(new SmallDescStyleBox());
			s.DescByGenList.add(new SmallDescStyleBox2());
			s.DescByGenList.add(new SmallDescStyleBox3());
			undefined.add(s);
			
			//Standard larger paper
			s = (standard_Default2 = new StylingBoxScheme("Standard Default", 42));
			temp = new LargeChartStandardRootBox();
			temp.rootBackOffset = 10;
			//temp.isIntruding = true;
			//temp.intrudeWidth = 200;
			temp.setPermWidth(150);
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart9thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 250;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			temp = new LargeChart6thSmallestBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 200;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			temp = new LargeChart3rdSmallestBox();
			temp.layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			temp.boxHeight = 20;
			temp.isIntruding = true;
			temp.intrudeWidth = 160;
			temp.setPermWidth(100);
			temp.setPermOffset(0);
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			temp.isIntruding = true;
			temp.intrudeWidth = 150;
			temp.setPermWidth(100);
			s.AncesByGenList.add(temp);
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			s.AncesByGenList.add(temp);
			temp = new EndBox();
			s.AncesByGenList.add(temp);
			s.AncesByGenList.add(new EndBox2());
			//s.AncesByGenList.add(new EndBox2());
			s.DescByGenList.add(new SmallDescStyleBox());
			s.DescByGenList.add(new SmallDescStyleBox2());
			s.DescByGenList.add(new SmallDescStyleBox3());
			undefined2.add(s);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static class SmallChartRootBox extends StylingBox{
		public SmallChartRootBox(){
			boxHeight = 300;
			setPermWidth(500);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 40;
			fontNameSize = 110;
			setPermOffset(250);
			borderlineWidth = 6;
			cornerCurve = 70;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 5;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 8;
			endLineArrowFontSize = 6;
			
			isIntruding = true;
			intrudeWidth = 1200;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(100.0);
			textPositions.add(170.0);
			textPositions.add(205.0);
			textPositions.add(245.0);
			textPositions.add(280.0);
			textPositions.add(325.0);
			textPositions.add(360.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallChartDescRootBox extends StylingBox{
		public SmallChartDescRootBox(){
			boxHeight = 300;
			setPermWidth(500);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 40;
			fontNameSize = 110;
			setPermOffset(250);
			borderlineWidth = 6;
			cornerCurve = 70;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 1100;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(100.0);
			textPositions.add(170.0);
			textPositions.add(205.0);
			textPositions.add(245.0);
			textPositions.add(280.0);
			textPositions.add(325.0);
			textPositions.add(360.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallChartInclDescRootBox extends StylingBox{
		public SmallChartInclDescRootBox(){
			boxHeight = 300;
			setPermWidth(500);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 40;
			fontNameSize = 110;
			setPermOffset(250);
			borderlineWidth = 6;
			cornerCurve = 70;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 1100;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(100.0);
			textPositions.add(170.0);
			textPositions.add(205.0);
			textPositions.add(245.0);
			textPositions.add(280.0);
			textPositions.add(325.0);
			textPositions.add(360.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	
		
	public static class SmallChartSecondBox extends StylingBox{
		public SmallChartSecondBox(){
			boxHeight = 235;
			setPermWidth(800);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 30;
			fontNameSize = 80;
			setPermOffset(50);
			borderlineWidth = 3;
			cornerCurve = 20;
			paddingAmount = 10;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(66.0);
			textPositions.add(120.0);
			textPositions.add(150.0);
			textPositions.add(190.0);
			textPositions.add(220.0);
			textPositions.add(260.0);
			textPositions.add(290.0);
			
			textMargin = 10;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallChartThirdBox extends StylingBox{
		public SmallChartThirdBox(){
			boxHeight = 115;
			setPermWidth(550);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 20;
			fontNameSize = 50;
			setPermOffset(100);
			borderlineWidth = 3;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(45.0);
			textPositions.add(80.0);
			textPositions.add(100.0);
			textPositions.add(120.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallChartFourthBox extends StylingBox{
		public SmallChartFourthBox(){
			boxHeight = 90;
			setPermWidth(550);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 16;
			fontNameSize = 36;
			setPermOffset(60);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(32.0);
			textPositions.add(55.0);
			textPositions.add(78.0);
			textPositions.add(101.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallChartFifthBox extends StylingBox{
		public SmallChartFifthBox(){
			boxHeight = 55;
			setPermWidth(550);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 14;
			fontNameSize = 30;
			setPermOffset(100);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(28.0);
			textPositions.add(46.0);
			textPositions.add(64.0);
			textPositions.add(101.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class MediumChartRootBox extends StylingBox{
		public MediumChartRootBox(){
			boxHeight = 280;
			setPermWidth(200);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 40;
			fontNameSize = 85;
			setPermOffset(0);
			borderlineWidth = 6;
			cornerCurve = 70;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 950;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(85.0);
			textPositions.add(135.0);
			textPositions.add(177.0);
			textPositions.add(216.0);
			textPositions.add(258.0);
			textPositions.add(297.0);
			textPositions.add(339.0);
			textPositions.add(420.0);
			textPositions.add(470.0);
			textPositions.add(512.0);
			textPositions.add(551.0);
			textPositions.add(593.0);
			
			textMargin = 14;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartRootBox extends StylingBox{
		public LargeChartRootBox(){
			boxHeight = 135;
			setPermWidth(250);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 20;
			fontNameSize = 36;
			setPermOffset(0);
			borderlineWidth = 3;
			cornerCurve = 20;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(35.0);
			textPositions.add(65.0);
			textPositions.add(85.0);
			textPositions.add(105.0);
			textPositions.add(125.0);
			textPositions.add(145.0);
			textPositions.add(165.0);
			textPositions.add(195.0);
			textPositions.add(225.0);
			textPositions.add(245.0);
			textPositions.add(265.0);
			textPositions.add(285.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartDescRootBox extends StylingBox{
		public LargeChartDescRootBox(){
			boxHeight = 330;
			setPermWidth(200);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 46;
			fontNameSize = 100;
			setPermOffset(0);
			borderlineWidth = 6;
			cornerCurve = 70;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 1450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(90.0);
			textPositions.add(160.0);
			textPositions.add(210.0);
			textPositions.add(265.0);
			textPositions.add(315.0);
			textPositions.add(370.0);
			textPositions.add(420.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartInclDescRootBox extends StylingBox{
		public LargeChartInclDescRootBox(){
			boxHeight = 330;
			setPermWidth(200);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 46;
			fontNameSize = 100;
			setPermOffset(0);
			borderlineWidth = 6;
			cornerCurve = 70;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 1450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(90.0);
			textPositions.add(160.0);
			textPositions.add(210.0);
			textPositions.add(265.0);
			textPositions.add(315.0);
			textPositions.add(370.0);
			textPositions.add(420.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartSecondBox extends StylingBox{
		public LargeChartSecondBox(){
			boxHeight = 170;
			setPermWidth(180);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 24;
			fontNameSize = 54;
			setPermOffset(50);
			borderlineWidth = 3;
			cornerCurve = 20;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			
			
			isIntruding = true;
			intrudeWidth = 700;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(50.0);
			textPositions.add(80.0);
			textPositions.add(105.0);
			textPositions.add(130.0);
			textPositions.add(155.0);
			textPositions.add(180.0);
			
			textMargin = 9;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartSecondBox2 extends StylingBox{
		public LargeChartSecondBox2(){
			boxHeight = 160;
			setPermWidth(180);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 24;
			fontNameSize = 50;
			setPermOffset(50);
			borderlineWidth = 3;
			cornerCurve = 20;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 650;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(41.0);
			textPositions.add(70.0);
			textPositions.add(95.0);
			textPositions.add(120.0);
			textPositions.add(145.0);
			textPositions.add(170.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartThirdBox extends StylingBox{
		public LargeChartThirdBox(){
			boxHeight = 115;
			setPermWidth(550);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 18;
			fontNameSize = 40;
			setPermOffset(70);
			borderlineWidth = 3;
			cornerCurve = 10;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(36.0);
			textPositions.add(64.0);
			textPositions.add(84.0);
			textPositions.add(104.0);
			textPositions.add(124.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartFourthBox extends StylingBox{
		public LargeChartFourthBox(){
			boxHeight = 75;
			setPermWidth(480);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 16;
			fontNameSize = 34;
			setPermOffset(60);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(30.0);
			textPositions.add(50.0);
			textPositions.add(68.0);
			textPositions.add(86.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartFourthBox2 extends StylingBox{
		public LargeChartFourthBox2(){
			boxHeight = 65;
			setPermWidth(430);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 15;
			fontNameSize = 30;
			setPermOffset(60);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(26.0);
			textPositions.add(45.0);
			textPositions.add(60.0);
			textPositions.add(75.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartFifthBox extends StylingBox{
		public LargeChartFifthBox(){
			boxHeight = 44;
			setPermWidth(360);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 12;
			fontNameSize = 24;
			setPermOffset(40);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(22.0);
			textPositions.add(38.0);
			textPositions.add(64.0);
			textPositions.add(101.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartFifthBox2 extends StylingBox{
		public LargeChartFifthBox2(){
			boxHeight = 36;
			setPermWidth(290);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 12;
			fontNameSize = 20;
			setPermOffset(40);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(16.0);
			textPositions.add(31.0);
			textPositions.add(64.0);
			textPositions.add(101.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartSixthBox extends StylingBox{
		public LargeChartSixthBox(){
			boxHeight = 35;
			setPermWidth(290);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 11;
			fontNameSize = 19;
			setPermOffset(20);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(18.0);
			textPositions.add(30.0);
			textPositions.add(64.0);
			textPositions.add(101.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	
	
	public static class LargeChartSeventhBox extends StylingBox{
		public LargeChartSeventhBox(){
			
			boxHeight = 22;
			setPermWidth(250);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 12;
			fontNameSize = 18;
			setPermOffset(20);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(17.0);
			textPositions.add(35.0);
			textPositions.add(64.0);
			textPositions.add(101.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}

	public static class LargeChartStandardRootBox extends StylingBox{
		public LargeChartStandardRootBox(){
			boxHeight = 59;
			setPermWidth(300);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 15;
			fontNameSize = 22;
			setPermOffset(0);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 300;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(18.0);
			textPositions.add(38.0);
			textPositions.add(52.0);
			textPositions.add(68.0);
			textPositions.add(89.0);
			textPositions.add(108.0);
			textPositions.add(124.0);
			textPositions.add(136.0);
			textPositions.add(155.0);
			textPositions.add(171.0);
			textPositions.add(189.0);
			textPositions.add(206.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartSmallestRootBox extends StylingBox{
		public LargeChartSmallestRootBox(){
			boxHeight = 93;
			setPermWidth(300);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 15;
			fontNameSize = 26;
			setPermOffset(0);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 300;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(21.0);
			textPositions.add(41.0);
			textPositions.add(55.0);
			textPositions.add(71.0);
			textPositions.add(85.0);
			textPositions.add(101.0);
			textPositions.add(115.0);
			textPositions.add(139.0);
			textPositions.add(158.0);
			textPositions.add(174.0);
			textPositions.add(192.0);
			textPositions.add(209.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart14thSmallestBox extends StylingBox{
		public LargeChart14thSmallestBox(){
			boxHeight = 115;
			setPermWidth(350);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 18;
			fontNameSize = 32;
			setPermOffset(50);
			borderlineWidth = 3;
			cornerCurve = 20;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 650;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(28.0);
			textPositions.add(55.0);
			textPositions.add(72.0);
			textPositions.add(92.0);
			textPositions.add(110.0);
			textPositions.add(130.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart13thSmallestBox extends StylingBox{
		public LargeChart13thSmallestBox(){
			boxHeight = 85;
			setPermWidth(320);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 16;
			fontNameSize = 28;
			setPermOffset(70);
			borderlineWidth = 3;
			cornerCurve = 10;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(26.0);
			textPositions.add(46.0);
			textPositions.add(62.0);
			textPositions.add(80.0);
			textPositions.add(96.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart12thSmallestBox extends StylingBox{
		public LargeChart12thSmallestBox(){
			
			boxHeight = 72;
			setPermWidth(280);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 14;
			fontNameSize = 24;
			setPermOffset(20);
			borderlineWidth = 2;
			cornerCurve = 10;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(21.0);
			textPositions.add(38.0);
			textPositions.add(52.0);
			textPositions.add(66.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart11thSmallestBox extends StylingBox{
		public LargeChart11thSmallestBox(){
			
			boxHeight = 70;
			setPermWidth(270);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 14;
			fontNameSize = 22;
			setPermOffset(20);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(20.0);
			textPositions.add(35.0);
			textPositions.add(49.0);
			textPositions.add(64.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart10thSmallestBox extends StylingBox{
		public LargeChart10thSmallestBox(){
			
			boxHeight = 61;
			setPermWidth(260);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 13;
			fontNameSize = 20;
			setPermOffset(20);
			borderlineWidth = 2;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(17.0);
			textPositions.add(31.0);
			textPositions.add(44.0);
			textPositions.add(57.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart9thSmallestBox extends StylingBox{
		public LargeChart9thSmallestBox(){
			
			boxHeight = 47;
			setPermWidth(250);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 13;
			fontNameSize = 20;
			setPermOffset(20);
			borderlineWidth = 1;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(17.0);
			textPositions.add(31.0);
			textPositions.add(43.0);
			textPositions.add(101.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart8thSmallestBox extends StylingBox{
		public LargeChart8thSmallestBox(){
			
			boxHeight = 49;
			setPermWidth(230);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 12;
			fontNameSize = 19;
			setPermOffset(20);
			borderlineWidth = 1;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(17.0);
			textPositions.add(31.0);
			textPositions.add(44.0);
			textPositions.add(57.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart7thSmallestBox extends StylingBox{
		public LargeChart7thSmallestBox(){
			
			boxHeight = 40;
			setPermWidth(210);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 11;
			fontNameSize = 17;
			setPermOffset(20);
			borderlineWidth = 1;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(13.0);
			textPositions.add(25.0);
			textPositions.add(36.0);
			textPositions.add(49.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart6thSmallestBox extends StylingBox{
		public LargeChart6thSmallestBox(){
			
			boxHeight = 29;
			setPermWidth(200);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 11;
			fontNameSize = 16;
			setPermOffset(20);
			borderlineWidth = 1;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(13.0);
			textPositions.add(25.0);
			textPositions.add(37.0);
			textPositions.add(49.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart5thSmallestBox extends StylingBox{
		public LargeChart5thSmallestBox(){
			
			boxHeight = 29;
			setPermWidth(190);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 10;
			fontNameSize = 15;
			setPermOffset(20);
			borderlineWidth = 1;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(13.0);
			textPositions.add(25.0);
			textPositions.add(37.0);
			textPositions.add(49.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart4thSmallestBox extends StylingBox{
		public LargeChart4thSmallestBox(){
			
			boxHeight = 27;
			setPermWidth(180);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 10;
			fontNameSize = 13;
			setPermOffset(20);
			borderlineWidth = .5;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(11.0);
			textPositions.add(23.0);
			textPositions.add(35.0);
			textPositions.add(47.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart3rdSmallestBox extends StylingBox{
		public LargeChart3rdSmallestBox(){
			
			boxHeight = 13;
			setPermWidth(160);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 8;
			fontNameSize = 10;
			setPermOffset(20);
			borderlineWidth = .5;
			cornerCurve = 5;
			paddingAmount = 3;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(9.0);
			textPositions.add(17.0);
			textPositions.add(26.5);
			textPositions.add(35.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart2ndSmallestBox extends StylingBox{
		public LargeChart2ndSmallestBox(){
			
			boxHeight = 12;
			setPermWidth(140);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 7;
			fontNameSize = 9;
			setPermOffset(20);
			borderlineWidth = .5;
			cornerCurve = 5;
			paddingAmount = 3;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(8.0);
			textPositions.add(15.5);
			textPositions.add(22.0);
			textPositions.add(29.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartSmallestBox extends StylingBox{
		public LargeChartSmallestBox(){
			
			boxHeight = 8;
			setPermWidth(120);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 6;
			fontNameSize = 7;
			setPermOffset(20);
			borderlineWidth = .5;
			cornerCurve = 5;
			paddingAmount = 2;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(6.0);
			textPositions.add(12.0);
			textPositions.add(18.5);
			textPositions.add(24.0);
			textPositions.add(30.0);
			textPositions.add(36.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	
	
	
	public static class SmallChartDescBox extends StylingBox{
		public SmallChartDescBox()
		{
			boxHeight = 135;
			setPermWidth(500);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 30;
			fontNameSize = 60;
			setPermOffset(100);
			borderlineWidth = 3;
			cornerCurve = 25;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(50.0);
			textPositions.add(90.0);
			textPositions.add(120.0);
			textPositions.add(150.0);
			textPositions.add(180.0);
			textPositions.add(240.0);
			textPositions.add(280.0);
			textPositions.add(310.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class MediumChartDescBox extends StylingBox{
		public MediumChartDescBox()
		{
			boxHeight = 100;
			setPermWidth(400);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 20;
			fontNameSize = 30;
			setPermOffset(100);
			borderlineWidth = 3;
			cornerCurve = 25;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(35.0);
			textPositions.add(65.0);
			textPositions.add(90.0);
			textPositions.add(115.0);
			textPositions.add(140.0);
			textPositions.add(165.0);
			textPositions.add(195.0);
			textPositions.add(220.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChartDescBox extends StylingBox{
		public LargeChartDescBox()
		{
			boxHeight = 69;
			setPermWidth(330);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 14;
			fontNameSize = 24;
			setPermOffset(100);
			borderlineWidth = 2;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(22.0);
			textPositions.add(42.0);
			textPositions.add(57.0);
			textPositions.add(76.0);
			textPositions.add(93.0);
			textPositions.add(120.0);
			textPositions.add(140.0);
			textPositions.add(157.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart2ndDescBox extends StylingBox{
		public LargeChart2ndDescBox()
		{
			boxHeight = 44;
			setPermWidth(290);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 14;
			fontNameSize = 20;
			setPermOffset(100);
			borderlineWidth = 2;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(20.0);
			textPositions.add(39.0);
			textPositions.add(55.0);
			textPositions.add(76.0);
			textPositions.add(95.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart3rdDescBox extends StylingBox{
		public LargeChart3rdDescBox()
		{
			boxHeight = 38.5;
			setPermWidth(270);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 12;
			fontNameSize = 17;
			setPermOffset(100);
			borderlineWidth = 2;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(16.0);
			textPositions.add(33.0);
			textPositions.add(47.0);
			textPositions.add(65.0);
			textPositions.add(83.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart4thDescBox extends StylingBox{
		public LargeChart4thDescBox()
		{
			boxHeight = 33;
			setPermWidth(250);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 11;
			fontNameSize = 15;
			setPermOffset(100);
			borderlineWidth = 2;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(16.0);
			textPositions.add(30.0);
			textPositions.add(43.0);
			textPositions.add(60.0);
			textPositions.add(72.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart5thDescBox extends StylingBox{
		public LargeChart5thDescBox()
		{
			boxHeight = 28;
			setPermWidth(225);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 10;
			fontNameSize = 13;
			setPermOffset(100);
			borderlineWidth = 1;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 6;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(12.0);
			textPositions.add(25.0);
			textPositions.add(36.0);
			textPositions.add(51.0);
			textPositions.add(64.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class LargeChart6thDescBox extends StylingBox{
		public LargeChart6thDescBox()
		{
			boxHeight = 15;
			setPermWidth(200);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 8;
			fontNameSize = 11;
			setPermOffset(100);
			borderlineWidth = 1;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(11.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	
	
	
	
	public static class ExtraLargeBox extends StylingBox{
		public ExtraLargeBox()
		{
			boxHeight = 600;
			setPermWidth(500);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 22;
			fontNameSize = 72;
			setPermOffset(300);
			borderlineWidth = 5;
			cornerCurve = 12;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 2500;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(50.0);
			textPositions.add(80.0);
			textPositions.add(140.0);
			textPositions.add(160.0);
			textPositions.add(180.0);
			textPositions.add(195.0);
			textPositions.add(210.0);
			
			textMargin = 3;
			
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class ExtraLargeDescBox extends StylingBox{
		public ExtraLargeDescBox()
		{
			boxHeight = 600;
			setPermWidth(500);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 22;
			fontNameSize = 72;
			setPermOffset(300);
			borderlineWidth = 5;
			cornerCurve = 12;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = true;
			intrudeWidth = 2500;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(50.0);
			textPositions.add(80.0);
			textPositions.add(140.0);
			textPositions.add(160.0);
			textPositions.add(180.0);
			textPositions.add(50.0);
			textPositions.add(80.0);
			textPositions.add(140.0);
			textPositions.add(160.0);
			textPositions.add(180.0);
			textPositions.add(195.0);
			textPositions.add(210.0);
			
			textMargin = 3;			
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	
	
	public static class LargeBox extends StylingBox{
		public LargeBox()
		{
			boxHeight = 200;
			setPermWidth(400);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 14;
			fontNameSize = 18;
			setPermOffset(100);
			borderlineWidth = 3;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 2000;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(10.0);
			textPositions.add(40.0);
			textPositions.add(60.0);
			textPositions.add(80.0);
			textPositions.add(100.0);
			textPositions.add(120.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class MediumBox extends StylingBox{
		public MediumBox()
		{
			boxHeight = 80;
			setPermWidth(300);
			layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			fontSize = 12;
			fontNameSize = 16;
			setPermOffset(40);
			borderlineWidth = 3;
			cornerCurve = 5;
			paddingAmount = 4;

			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 6;
			
			isIntruding = true;
			intrudeWidth = 700;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(10.0);
			textPositions.add(40.0);
			textPositions.add(60.0);
			textPositions.add(80.0);
			textPositions.add(100.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallBox extends StylingBox{
		public SmallBox()
		{
			boxHeight = 60;
			setPermWidth(200);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 10;
			fontNameSize = 14;
			setPermOffset(30);
			borderlineWidth = 3;
			cornerCurve = 5;
			paddingAmount = 4;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(10.0);
			textPositions.add(50.0);
			textPositions.add(70.0);
			textPositions.add(90.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class EndBox extends StylingBox{
		public EndBox()
		{
			boxHeight = 12;
			setPermWidth(150);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 6;
			fontNameSize = 8;
			setPermOffset(10);
			borderlineWidth = 1;
			cornerCurve = 5;
			paddingAmount = 3;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 200;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(8.0);
			textPositions.add(16.0);
			textPositions.add(22.0);
			textPositions.add(28.0);
			textPositions.add(34.0);
			
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class EndBox2 extends StylingBox{
		public EndBox2()
		{
			boxHeight = 8.5;
			setPermWidth(100);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 6;
			fontNameSize = 6;
			setPermOffset(10);
			borderlineWidth = 1;
			cornerCurve = 5;
			paddingAmount = 2;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 4;
			
			isIntruding = false;
			intrudeWidth = 350;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(6.0);
			textPositions.add(12.0);
			textPositions.add(18.0);
			textPositions.add(24.0);
			textPositions.add(30.5);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class DescStyleBox extends StylingBox{
		public DescStyleBox()
		{
			boxHeight = 300;
			setPermWidth(300);
			layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			fontSize = 20;
			fontNameSize = 22;
			setPermOffset(100);
			borderlineWidth = 5;
			cornerCurve = 5;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 6;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(10.0);
			textPositions.add(50.0);
			textPositions.add(70.0);
			textPositions.add(90.0);
			textPositions.add(110.0);
			textPositions.add(130.0);
			textPositions.add(150.0);
			textPositions.add(170.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallDescStyleBox extends StylingBox{
		public SmallDescStyleBox()
		{
			boxHeight = 16;
			setPermWidth(200);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 8;
			fontNameSize = 10;
			setPermOffset(10);
			borderlineWidth = 1;
			cornerCurve = 5;
			paddingAmount = 3;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 6;
			
			isIntruding = false;
			intrudeWidth = 200;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(9.0);
			textPositions.add(21.0);
			textPositions.add(33.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.WIFE_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallDescStyleBox2 extends StylingBox{
		public SmallDescStyleBox2()
		{
			boxHeight = 14;
			setPermWidth(180);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 6;
			fontNameSize = 8;
			setPermOffset(10);
			borderlineWidth = 1;
			cornerCurve = 5;
			paddingAmount = 3;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 6;
			
			isIntruding = false;
			intrudeWidth = 200;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(8.0);
			textPositions.add(19.0);
			textPositions.add(29.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.WIFE_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	public static class SmallDescStyleBox3 extends StylingBox{
		public SmallDescStyleBox3()
		{
			boxHeight = 12;
			setPermWidth(150);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 6;
			fontNameSize = 6;
			setPermOffset(10);
			borderlineWidth = 1;
			cornerCurve = 5;
			paddingAmount = 3;
			
			endLineArrowShaftLength = 15;
			endLineArrowShaftHeight = 8;
			endLineArrowHeadLength = 5;
			endLineArrowHeadHeight = 10;
			endLineArrowFontSize = 6;
			
			isIntruding = false;
			intrudeWidth = 200;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(8.0);
			textPositions.add(17.0);
			textPositions.add(26.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.WIFE_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
}






