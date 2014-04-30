package edu.byu.cs.roots.opg.chart.landscape;

import java.util.ArrayList;

import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChart5thSmallestBox;
import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChart10thSmallestBox;
import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChart12thSmallestBox;
import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChart2ndDescBox;
import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChart3rdDescBox;
import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChart4thDescBox;
import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChart5thDescBox;
import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChart6thDescBox;
import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChart8thSmallestBox;
import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChartDescBox;
import edu.byu.cs.roots.opg.chart.portrait.StyleBoxFactory.LargeChartSmallestBox;
import edu.byu.cs.roots.opg.chart.preset.templates.Layouts;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBoxScheme;
import edu.byu.cs.roots.opg.chart.preset.templates.StylingBox.TextDirection;

public class StyleBoxFactory{
	
		
	public static ArrayList<StylingBoxScheme> getStyleList(int ancGens, int descGens){
		
		StyleSchemes schemes = new StyleSchemes();
		
		if (ancGens == 0 && descGens == 4)
			return schemes.zero_four;
		if (ancGens == 0 && descGens == 5)
			return schemes.zero_five;
		if (ancGens == 0 && descGens == 6)
			return schemes.zero_six;
		if (ancGens == 0 && descGens == 7)
			return schemes.zero_seven;
		if (ancGens == 0 && descGens == 8)	
			return schemes.zero_eight;
		if (ancGens == 0 && descGens == 9)
			return schemes.zero_nine;
		if (ancGens == 0 && descGens == 10)
			return schemes.zero_ten;
		if (ancGens == 0 && descGens == 11)
			return schemes.zero_eleven;
		if (ancGens == 0 && descGens == 12)
			return schemes.zero_twelve;
		if (ancGens == 1 && descGens == 4)
			return schemes.one_four;
		if (ancGens == 1 && descGens == 5)
			return schemes.one_five;
		if (ancGens == 1 && descGens == 6)
			return schemes.one_six;
		if (ancGens == 1 && descGens == 7)
			return schemes.one_seven;
		if (ancGens == 1 && descGens == 8)
			return schemes.one_eight;
		if (ancGens == 1 && descGens == 9)
			return schemes.one_nine;
		if (ancGens == 1 && descGens == 10)
			return schemes.one_ten;
		if (ancGens == 1 && descGens == 11)
			return schemes.one_eleven;
		if (ancGens == 2 && descGens == 4)
			return schemes.two_four;
		if (ancGens == 2 && descGens == 5)
			return schemes.two_five;
		if (ancGens == 2 && descGens == 6)
			return schemes.two_six;
		if (ancGens == 2 && descGens == 7)
			return schemes.two_seven;
		if (ancGens == 2 && descGens == 8)
			return schemes.two_eight;
		if (ancGens == 2 && descGens == 9)
			return schemes.two_nine;
		if (ancGens == 2 && descGens == 10)
			return schemes.two_ten;
		if (ancGens == 3 && descGens == 4)
			return schemes.three_four;
		if (ancGens == 3 && descGens == 5)
			return schemes.three_five;
		if (ancGens == 3 && descGens == 6)
			return schemes.three_six;
		if (ancGens == 3 && descGens == 7)
			return schemes.three_seven;
		if (ancGens == 3 && descGens == 8)
			return schemes.three_eight;
		if (ancGens == 3 && descGens == 9)
			return schemes.three_nine;
		if(ancGens == 3 && descGens == 10)
			return schemes.three_ten;
		if (ancGens == 12 && descGens == 0)
			return schemes.twelve_zero;
		if (ancGens == 12 && descGens == 2)
			return schemes.twelve_two;
		if(descGens > 20 || ancGens + descGens*2 > 41)
			return schemes.undefined2;
		return schemes.undefined;
			
		
		
	}
	
	public static class StyleSchemes{
		
		ArrayList<StylingBoxScheme> undefined = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> undefined2 = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_four = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_five = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_six = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_seven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_eight = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_nine = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_ten = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_eleven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> zero_twelve = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_four = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_five = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_six = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_seven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_eight = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_nine = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_ten = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> one_eleven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_four = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_five = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_six = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_seven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_eight = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_nine = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> two_ten = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_four = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_five = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_six = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_seven = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_eight = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_nine = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> three_ten = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> twelve_zero = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> twelve_two = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> six_two = new ArrayList<StylingBoxScheme>();
		ArrayList<StylingBoxScheme> six_four = new ArrayList<StylingBoxScheme>();
		
		StylingBoxScheme standard_Default;
		StylingBoxScheme standard_DefaultTall;
		StylingBoxScheme standard_zero_four_24;
		StylingBoxScheme standard_zero_five_24;
		StylingBoxScheme standard_zero_six_48;
		StylingBoxScheme standard_zero_seven_48;
		StylingBoxScheme standard_zero_eight_48;
		StylingBoxScheme standard_zero_nine_48;
		StylingBoxScheme standard_zero_ten_48;
		StylingBoxScheme standard_zero_eleven_48;
		StylingBoxScheme standard_zero_twelve_48;
		StylingBoxScheme standard_one_four_24;
		StylingBoxScheme standard_one_five_24;
		StylingBoxScheme standard_one_six_48;
		StylingBoxScheme standard_one_seven_48;
		StylingBoxScheme standard_one_eight_48;
		StylingBoxScheme standard_one_nine_48;
		StylingBoxScheme standard_one_ten_48;
		StylingBoxScheme standard_one_eleven_48;
		StylingBoxScheme standard_two_four_24;
		StylingBoxScheme standard_two_five_24;
		StylingBoxScheme standard_two_six_48;
		StylingBoxScheme standard_two_seven_48;
		StylingBoxScheme standard_two_eight_48;
		StylingBoxScheme standard_two_nine_48;
		StylingBoxScheme standard_three_four_24;
		StylingBoxScheme standard_three_five_36;
		StylingBoxScheme standard_three_six_48;
		StylingBoxScheme standard_three_seven_48;
		StylingBoxScheme standard_three_eight_48;
		StylingBoxScheme standard_three_nine_48;
		StylingBoxScheme standard_two_ten_48;
		StylingBoxScheme standard_three_ten_48;
		StylingBoxScheme standard_twelve_zero_48;
		StylingBoxScheme standard_twelve_two_48;
		StylingBoxScheme standard_Six_Two_36;
		StylingBoxScheme eleven_seventeen_Six_Two_36;
		StylingBoxScheme standard_Six_Four_36;
		StylingBoxScheme eleven_seventeen_Six_Four_36;
		
		private StyleSchemes(){
			StylingBoxScheme s;
			StylingBox temp;
			
			//zero_Four
			s = (standard_zero_four_24 = new StylingBoxScheme("Standard Default", 24));
			temp = new FullChartRootBox();
			temp.setPermOffset(100);
			temp.rootBackOffset = 100;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(100);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			zero_four.add(s);
			
			//zero_Five
			s = (standard_zero_five_24 = new StylingBoxScheme("Standard Default", 24));
			temp = new FullChartRootBox();
			temp.setPermOffset(50);
			temp.rootBackOffset = 50;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(50);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(50);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			zero_five.add(s);
			
			//Zero_Six
			s = (standard_zero_six_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(50);
			temp.rootBackOffset = 100;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.boxHeight = 400;
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.boxHeight = 350;
			temp.setPermOffset(90);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.boxHeight = 300;
			temp.setPermOffset(80);
			s.DescByGenList.add(temp);
			zero_six.add(s);
			
			//Zero_Seven
			s = (standard_zero_seven_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(50);
			temp.rootBackOffset = 100;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(100);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(90);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(80);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			s.DescByGenList.add(temp);
			zero_seven.add(s);
			
			//Zero_Eight
			s = (standard_zero_eight_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(40);
			temp.rootBackOffset = 40;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			s.DescByGenList.add(temp);
			zero_eight.add(s);
			
			//Zero_Nine
			s = (standard_zero_nine_48 = new StylingBoxScheme("Standard 3 '", 36));
			s.AncesByGenList.add(new FullChartRootBox());
			s.DescByGenList.add(new FullChartRootBox());
			s.DescByGenList.add(new FullChartSecondBox());
			s.DescByGenList.add(new FullChartThirdBox());
			s.DescByGenList.add(new FullChartFourthBox());
			s.DescByGenList.add(new FullChartFifthBox());
			s.DescByGenList.add(new FullChartSixthBox());
			s.DescByGenList.add(new FullChartSeventhBox());
			s.DescByGenList.add(new FullChartEighthBox());
			s.DescByGenList.add(new FullChartNinthBox());
			s.DescByGenList.add(new FullChartEndBox());
			zero_nine.add(s);
			
			//Zero_Ten
			s = (standard_zero_ten_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(20);
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(20);
			//temp.setPermWidth(240);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(20);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(20);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 170;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			s.DescByGenList.add(temp);
			zero_ten.add(s);
			
			//Zero_Eleven
			s = (standard_zero_eleven_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(20);
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(20);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(20);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 170;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 170;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			s.DescByGenList.add(temp);
			zero_eleven.add(s);
			
			//Zero_twelve
			s = (standard_zero_twelve_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(10);
			temp.rootBackOffset = 10;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(10);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 180;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 180;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			s.DescByGenList.add(temp);
			zero_twelve.add(s);
			
			//one_Four
			s = (standard_one_four_24 = new StylingBoxScheme("Standard Default", 24));
			temp = new FullChartRootBox();
			temp.setPermOffset(90);
			temp.rootBackOffset = 90;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(90);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(90);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(90);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(90);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(90);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			one_four.add(s);
			
			//one_Five
			s = (standard_one_five_24 = new StylingBoxScheme("Standard Default", 24));
			temp = new FullChartRootBox();
			temp.setPermOffset(20);
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(20);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(20);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(20);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			one_five.add(s);
			
			//One_Six
			s = (standard_one_six_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(70);
			temp.rootBackOffset = 70;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(70);
			temp.boxHeight = 400;
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(70);
			temp.boxHeight = 350;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(70);
			temp.boxHeight = 350;
			s.DescByGenList.add(temp);
			one_six.add(s);
			
			//One_Seven
			s = (standard_one_seven_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(70);
			temp.rootBackOffset = 70;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(70);
			s.DescByGenList.add(temp);
			one_seven.add(s);
			
			//One_Eight
			s = (standard_one_eight_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(40);
			temp.rootBackOffset = 40;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			one_eight.add(s);
			
			//One_Nine
			s = (standard_one_nine_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(20);
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 200;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			one_nine.add(s);
			
			//One_Ten
			s = (standard_one_ten_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(20);
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(20);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(20);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(20);
			temp.boxHeight = 180;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			s.DescByGenList.add(temp);
			one_ten.add(s);
			
			//One_Eleven
			s = (standard_one_eleven_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(10);
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(10);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 180;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			s.DescByGenList.add(temp);
			one_eleven.add(s);
			
			//Two_Four
			s = (standard_two_four_24 = new StylingBoxScheme("Standard Default", 24));
			temp = new FullChartRootBox();
			temp.setPermOffset(60);
			temp.rootBackOffset = 60;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(60);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(60);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(60);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			two_four.add(s);
			
			//Two_Five
			s = (standard_two_five_24 = new StylingBoxScheme("Standard Default", 24));
			temp = new FullChartRootBox();
			temp.setPermOffset(10);
			temp.rootBackOffset = 10;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(10);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			two_five.add(s);
			
			//Two_Six
			s = (standard_two_six_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(60);
			temp.rootBackOffset = 60;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(60);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(60);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(60);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(60);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			two_six.add(s);
			
			//Two_Seven
			s = (standard_two_seven_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(50);
			temp.rootBackOffset = 50;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(50);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			two_seven.add(s);
			
			//Two_Eight
			s = (standard_two_eight_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(40);
			temp.rootBackOffset = 40;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(40);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(40);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(20);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(40);
			temp.boxHeight = 180;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			two_eight.add(s);
			
			//Two_Nine
			s = (standard_two_nine_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(20);
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(20);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(20);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(20);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(20);
			temp.boxHeight = 180;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			two_nine.add(s);
			
			//Two_Ten
			s = (standard_two_ten_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(10);
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(10);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 180;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			s.DescByGenList.add(temp);
			two_ten.add(s);
			
			//Three_Four
			s = (standard_three_four_24 = new StylingBoxScheme("Standard Default", 24));
			temp = new FullChartRootBox();
			temp.setPermOffset(50);
			temp.rootBackOffset = 50;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(50);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermWidth(230);
			temp.boxHeight = 47;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(50);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			three_four.add(s);
			
			//Three_Five
			s = (standard_three_five_36 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(100);
			temp.rootBackOffset = 100;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(90);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermWidth(230);
			temp.boxHeight = 47;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(90);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(90);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(90);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(90);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(90);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			three_five.add(s);
			
			//Three_Six
			s = (standard_three_six_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(60);
			temp.rootBackOffset = 60;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(60);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermWidth(230);
			temp.boxHeight = 47;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(60);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(60);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(60);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(60);
			temp.boxHeight = 300;
			s.DescByGenList.add(temp);
			three_six.add(s);
			
			//Three_Seven
			s = (standard_three_seven_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(50);
			temp.rootBackOffset = 50;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(50);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermWidth(230);
			temp.boxHeight = 47;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(50);
			s.DescByGenList.add(temp);
			three_seven.add(s);
			
			//Three_Eight
			s = (standard_three_eight_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(40);
			temp.rootBackOffset = 40;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(40);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermWidth(230);
			temp.boxHeight = 47;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(40);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(20);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(40);
			temp.boxHeight = 180;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(40);
			s.DescByGenList.add(temp);
			three_eight.add(s);
			
			//Three_Nine
			s = (standard_three_nine_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(20);
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.setPermOffset(20);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermWidth(230);
			temp.boxHeight = 47;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(20);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(20);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(20);
			temp.boxHeight = 180;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			temp.setPermOffset(20);
			s.DescByGenList.add(temp);
			three_nine.add(s);
			
			//Three_Ten
			s = (standard_three_ten_48 = new StylingBoxScheme("Standard Default", 36));
			temp = new FullChartRootBox();
			temp.setPermOffset(10);
			temp.rootBackOffset = 20;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart12thSmallestBox();
			temp.setPermWidth(280);
			temp.boxHeight = 72;
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(260);
			temp.boxHeight = 61;
			s.AncesByGenList.add(temp);
			temp = new LargeChart8thSmallestBox();
			temp.setPermWidth(230);
			temp.boxHeight = 47;
			s.AncesByGenList.add(temp);
			temp = new FullChartSecondBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartThirdBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartFourthBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartFifthBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartSixthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 225;
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.setPermOffset(10);
			temp.boxHeight = 200;
			s.DescByGenList.add(temp);
			temp = new FullChartEighthBox();
			temp.setPermOffset(10);
			temp.boxHeight = 180;
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 150;
			s.DescByGenList.add(temp);
			three_ten.add(s);
			
			//Twelve_Zero
			s = (standard_twelve_zero_48 = new StylingBoxScheme("Standard Default", 36));
			s.AncesByGenList.add(new LargeChartSmallestRootBox());
			s.AncesByGenList.add(new LargeChartSmallestRootBox());
			s.AncesByGenList.add(new LargeChartSmallestRootBox());
			s.AncesByGenList.add(new LargeChartSmallestRootBox());
			s.DescByGenList.add(new LargeChartSmallestRootBox());
			twelve_zero.add(s);
			
			//Twelve_Two
			s = (standard_twelve_two_48 = new StylingBoxScheme("Standard Default", 36));
			s.AncesByGenList.add(new FullChartRootBox());
			s.AncesByGenList.add(new LargeChartSmallestRootBox());
			s.DescByGenList.add(new FullChartRootBox());
			s.DescByGenList.add(new FullChartSecondBox());
			twelve_two.add(s);
			
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
			temp.setPermOffset(40);
			s.AncesByGenList.add(temp);
			temp = new LargeChart10thSmallestBox();
			temp.setPermWidth(61);
			temp.boxHeight = 260;
			temp.direction = TextDirection.NINETY;
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
			temp = new SmallestRootBox();
			temp.setPermOffset(10);
			temp.rootBackOffset = 10;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.setPermOffset(10);
			temp.boxHeight = 42;
			s.AncesByGenList.add(temp);
			temp = new SmallestSecondBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.boxHeight = 180;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 130;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			s.DescByGenList.add(new FullChartEndBox2());
			undefined.add(s);
			
			//StandardTall
			s = (standard_DefaultTall = new StylingBoxScheme("Standard Default", 42));
			temp = new SmallestRootBox();
			temp.setPermOffset(10);
			temp.rootBackOffset = 10;
			s.AncesByGenList.add(temp);
			s.DescByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp.layout = Layouts.textboxlayouts.ThreeLineAbbrNameBDYearsBDPlaces;
			temp.setPermOffset(10);
			temp.boxHeight = 42;
			s.AncesByGenList.add(temp);
			temp = new LargeChart5thSmallestBox();
			temp = new SmallestSecondBox();
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartSeventhBox();
			temp.boxHeight = 180;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartNinthBox();
			temp.boxHeight = 160;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			temp = new FullChartEndBox();
			temp.boxHeight = 130;
			temp.setPermOffset(10);
			s.DescByGenList.add(temp);
			s.DescByGenList.add(new FullChartEndBox2());
			undefined2.add(s);
		
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static class LargeChartSmallestRootBox extends StylingBox{
		public LargeChartSmallestRootBox(){
			boxHeight = 122;
			setPermWidth(300);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 15;
			fontNameSize = 26;
			setPermOffset(0);
			borderlineWidth = 2;
			cornerCurve = 4;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
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
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullChartRootBox extends StylingBox{
		public FullChartRootBox(){
			boxHeight = 113;
			setPermWidth(529);
			layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			fontSize = 18;
			fontNameSize = 28;
			setPermOffset(60);
			rootBackOffset = 20;
			borderlineWidth = 3;
			cornerCurve = 30;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(22.0);
			textPositions.add(43.0);
			textPositions.add(64.0);
			textPositions.add(87.0);
			textPositions.add(105.0);
			
			textMargin = 12;
			
			weddingLayout = Layouts.textboxlayouts.NoIndentTwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullChartFirstBox extends StylingBox{
		public FullChartFirstBox(){
			boxHeight = 109;
			setPermWidth(487);
			layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			fontSize = 18;
			fontNameSize = 26;
			setPermOffset(60);
			borderlineWidth = 3;
			cornerCurve = 20;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(24.0);
			textPositions.add(42.0);
			textPositions.add(60.0);
			textPositions.add(78.0);
			textPositions.add(102.0);
			
			textMargin = 12;
			
			weddingLayout = Layouts.textboxlayouts.NoIndentTwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullChartSecondBox extends StylingBox{
		public FullChartSecondBox(){
			boxHeight = 160;
			setPermWidth(300);
			layout = Layouts.textboxlayouts.ThreeLineRotatedSingleDescBox;
			fontSize = 18;
			fontNameSize = 26;
			setPermOffset(40);
			borderlineWidth = 3.2;
			cornerCurve = 20;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(24.0);
			textPositions.add(42.0);
			textPositions.add(60.0);
			textPositions.add(78.0);
			textPositions.add(102.0);
			textPositions.add(120.0);
			textPositions.add(138.0);
			
			textMargin = 12;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullChartThirdBox extends StylingBox{
		public FullChartThirdBox(){
			boxHeight = 187;
			setPermWidth(160);
			layout = Layouts.textboxlayouts.ThreeLineRotatedSingleDescBox;
			fontSize = 13;
			fontNameSize = 22;
			setPermOffset(12);
			borderlineWidth = 2.4;
			cornerCurve = 20;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(20.0);
			textPositions.add(36.0);
			textPositions.add(50.0);
			textPositions.add(66.0);
			textPositions.add(81.0);
			textPositions.add(96.0);
			textPositions.add(115.0);
			textPositions.add(131.0);
			textPositions.add(145.0);
			textPositions.add(163.0);
			textPositions.add(179.0);

			textMargin = 12;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class FullChartFourthBox extends StylingBox{
		public FullChartFourthBox(){
			boxHeight = 320;
			setPermWidth(52);
			layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			fontSize = 12;
			fontNameSize = 20;
			setPermOffset(12);
			borderlineWidth = 1.6;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(17.0);
			textPositions.add(29.0);
			textPositions.add(40.0);
			textPositions.add(51.0);
			
			textMargin = 8;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NINETY;
		}
	}
	
	public static class FullChartFifthBox extends StylingBox{
		public FullChartFifthBox(){
			boxHeight = 240;
			setPermWidth(26);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 10;
			fontNameSize = 18;
			setPermOffset(10);
			borderlineWidth = 1.6;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(13.0);
			textPositions.add(27.0);
			textPositions.add(45.0);
			textPositions.add(57.0);
			
			textMargin = 8;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NINETY;
		}
	}
	
	public static class FullChartSixthBox extends StylingBox{
		public FullChartSixthBox(){
			boxHeight = 240;
			setPermWidth(25);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 10;
			fontNameSize = 16;
			setPermOffset(10);
			borderlineWidth = 1.6;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(14.0);
			textPositions.add(25.0);
			textPositions.add(43.0);
			textPositions.add(56.0);
			
			textMargin = 8;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NINETY;
		}
	}
	
	public static class FullChartSeventhBox extends StylingBox{
		public FullChartSeventhBox(){
			boxHeight = 240;
			setPermWidth(22);
			layout = Layouts.textboxlayouts.TwoLineAbbrNameBDYearsBPlace;
			fontSize = 8;
			fontNameSize = 14;
			setPermOffset(20);
			borderlineWidth = 1.6;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(12.0);
			textPositions.add(23.0);
			textPositions.add(37.0);
			textPositions.add(48.0);
			
			textMargin = 8;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NINETY;
		}
	}
	
	public static class FullChartEighthBox extends StylingBox{
		public FullChartEighthBox(){
			boxHeight = 240;
			setPermWidth(12);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 6;
			fontNameSize = 12;
			setPermOffset(20);
			borderlineWidth = 1.6;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(10.0);
			textPositions.add(23.0);
			//textPositions.add(37.0);
			//textPositions.add(48.0);
			
			textMargin = 8;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NINETY;
		}
	}
	
	public static class FullChartNinthBox extends StylingBox{
		public FullChartNinthBox(){
			boxHeight = 240;
			setPermWidth(9);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 6;
			fontNameSize = 10;
			setPermOffset(20);
			borderlineWidth = 1.6;
			cornerCurve = 10;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(8.0);
			textPositions.add(17.0);
			//textPositions.add(37.0);
			//textPositions.add(48.0);
			
			textMargin = 8;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NINETY;
		}
	}
	
	public static class FullChartEndBox extends StylingBox{
		public FullChartEndBox(){
			boxHeight = 240;
			setPermWidth(8);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 6;
			fontNameSize = 8;
			setPermOffset(8);
			borderlineWidth = .7;
			cornerCurve = 8;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(6.5);
			textPositions.add(15.5);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NINETY;
		}
	}

	public static class FullChartEndBox2 extends StylingBox{
		public FullChartEndBox2(){
			boxHeight = 100;
			setPermWidth(8);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 6;
			fontNameSize = 6;
			setPermOffset(8);
			borderlineWidth = .5;
			cornerCurve = 8;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 10;
			endLineArrowHeadLength = 10;
			endLineArrowFontSize = 6;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(5.0);
			textPositions.add(15.5);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NINETY;
		}
	}
	
	public static class SmallestRootBox extends StylingBox{
		public SmallestRootBox(){
			boxHeight = 80;
			setPermWidth(350);
			layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			fontSize = 14;
			fontNameSize = 16;
			setPermOffset(60);
			rootBackOffset = 20;
			borderlineWidth = 2;
			cornerCurve = 30;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 20;
			endLineArrowHeadLength = 20;
			endLineArrowFontSize = 10;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(15.0);
			textPositions.add(31.0);
			textPositions.add(46.0);
			textPositions.add(61.0);
			textPositions.add(76.0);
			
			textMargin = 12;
			
			weddingLayout = Layouts.textboxlayouts.NoIndentTwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallestSecondBox extends StylingBox{
		public SmallestSecondBox(){
			boxHeight = 120;
			setPermWidth(200);
			layout = Layouts.textboxlayouts.ThreeLineRotatedSingleDescBox;
			fontSize = 12;
			fontNameSize = 14;
			setPermOffset(10);
			borderlineWidth = 2;
			cornerCurve = 20;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 20;
			endLineArrowHeadLength = 20;
			endLineArrowFontSize = 10;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(14.0);
			textPositions.add(28.0);
			textPositions.add(42.0);
			textPositions.add(58.0);
			textPositions.add(76.0);
			textPositions.add(90.0);
			textPositions.add(102.0);
			
			textMargin = 12;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallChartRootBox extends StylingBox{
		public SmallChartRootBox(){
			boxHeight = 575;
			setPermWidth(2400);
			layout = Layouts.textboxlayouts.FiveLine1;
			fontSize = 70;
			fontNameSize = 180;
			setPermOffset(250);
			borderlineWidth = 6;
			cornerCurve = 70;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(150.0);
			textPositions.add(275.0);
			textPositions.add(350.0);
			textPositions.add(450.0);
			textPositions.add(525.0);
			textPositions.add(625.0);
			textPositions.add(700.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallChartDescRootBox extends StylingBox{
		public SmallChartDescRootBox(){
			boxHeight = 575;
			setPermWidth(2400);
			layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			fontSize = 70;
			fontNameSize = 180;
			setPermOffset(250);
			borderlineWidth = 6;
			cornerCurve = 70;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = true;
			intrudeWidth = 2450;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(150.0);
			textPositions.add(275.0);
			textPositions.add(350.0);
			textPositions.add(450.0);
			textPositions.add(525.0);
			textPositions.add(625.0);
			textPositions.add(700.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.NoWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallChartFirstDescBox extends StylingBox{
		public SmallChartFirstDescBox()
		{
			boxHeight = 700;
			setPermWidth(800);
			layout = Layouts.textboxlayouts.FourLineAbbrNameBDYearsBDPlaces;
			fontSize = 45;
			fontNameSize = 80;
			setPermOffset(100);
			borderlineWidth = 5;
			cornerCurve = 4;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(70.0);
			textPositions.add(130.0);
			textPositions.add(190.0);
			textPositions.add(250.0);
			textPositions.add(310.0);
			textPositions.add(370.0);
			textPositions.add(450.0);
			textPositions.add(510.0);
			textPositions.add(570.0);
			textPositions.add(630.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.HUSBAND_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
	public static class SmallChartFinalDescBox extends StylingBox{
		public SmallChartFinalDescBox()
		{
			boxHeight = 300;
			setPermWidth(100);
			layout = Layouts.textboxlayouts.ThreeLineJointDescBox;
			fontSize = 20;
			fontNameSize = 22;
			setPermOffset(100);
			borderlineWidth = 5;
			cornerCurve = 4;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
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
			
			direction = StylingBox.TextDirection.NINETY;
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
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
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
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
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
			borderlineWidth = 4;
			cornerCurve = 3;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
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
			borderlineWidth = 4;
			cornerCurve = 3;
			paddingAmount = 0;

			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
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
			cornerCurve = 2;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
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
			boxHeight = 20;
			setPermWidth(150);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 8;
			fontNameSize = 14;
			setPermOffset(20);
			borderlineWidth = 2;
			cornerCurve = 1;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			isIntruding = false;
			intrudeWidth = 350;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(10.0);
			textPositions.add(20.0);
			textPositions.add(30.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.TwoLineWeddingLayout;
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
			cornerCurve = 4;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
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
			boxHeight = 25;
			setPermWidth(200);
			layout = Layouts.textboxlayouts.OneLineAbbrNameBPlaceBDYears;
			fontSize = 8;
			fontNameSize = 10;
			setPermOffset(20);
			borderlineWidth = 2;
			cornerCurve = 1;
			paddingAmount = 0;
			
			endLineArrowShaftLength = 200;
			endLineArrowHeadLength = 50;
			endLineArrowFontSize = 20;
			
			textPositions = new ArrayList<Double>();
			textPositions.add(20.0);
			textPositions.add(40.0);
			textPositions.add(50.0);
			
			textMargin = 3;
			
			weddingLayout = Layouts.textboxlayouts.OneLineWeddingLayout;
			weddingDisplayType = StylingBox.WeddingPositions.WIFE_POSTFIX;
			
			direction = StylingBox.TextDirection.NORMAL;
		}
	}
	
}






