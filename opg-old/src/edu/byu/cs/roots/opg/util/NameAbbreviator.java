package edu.byu.cs.roots.opg.util;

import java.awt.Font;
import java.awt.font.FontRenderContext;


public class NameAbbreviator {
	
	public static FontRenderContext frc = null; //will be set by the JPanel used to draw charts
	static double fontsize;
	static String formattedName;
	final static double MINFONTSCALE = 1;
	
	public static double getSize() {
		return fontsize;
	}

	public static String getName() {
		return formattedName.trim();
	}

	public static void nameFit(String Prefix, String GivenNames, String Surname, String Postfix, float width, Font font) {
		//System.out.println("prefix: " + prefix + "\tgivennames: " + GivenNames + "\tsurname: " + Surname + "\tpostfix: " + Postfix + "\twidth: " + width + "\tfont: " + font);
		while(Prefix.indexOf("  ") != -1)
			Prefix = Prefix.substring(0, Prefix.indexOf("  ")) + " " + Prefix.substring(Prefix.indexOf("  ") + 2);		
		while(GivenNames.indexOf("  ") != -1)
			GivenNames = GivenNames.substring(0,GivenNames.indexOf("  ")) + " " + GivenNames.substring(GivenNames.indexOf("  ")+2);
		while(Surname.indexOf("  ") != -1)
			Surname = Surname.substring(0,Surname.indexOf("  ")) + " " + Surname.substring(Surname.indexOf("  ")+2);
		while(Postfix.indexOf("  ") != -1)
			Postfix = Postfix.substring(0,Postfix.indexOf("  ")) + " " + Postfix.substring(Postfix.indexOf("  ")+2);
		String result = getFitInfo(Prefix, GivenNames, Surname, Postfix, width, font);
		fontsize = Double.parseDouble(result.substring(0,result.indexOf(":")));
		formattedName = result.substring(result.indexOf(":") + 1);
		
	}

	/****************************************************************
	 * 
	 * @param Prefix
	 * @param GivenNames
	 * @param Surname
	 * @param Postfix
	 * @param width
	 * @param font
	 * @return the abbreviated name
	 * 
	 * Order of Abbreviations:
	 *  1. check to see if the original can fit in given width
	 *  2. set each name to have the first letter capitalized and the rest lower case
	 *  3. remove the "or __" from the surname
	 *  4. remove the "or __" from the given names
	 *  5. remove (_) from surname
	 *  6. remove (_) from given names
	 *  7. reduce the middle name to only the middle initial
	 *  8. remove the post-fix
	 *  9. remove the middle initial
	 * 10. go through all the given names and one by one reduce the names
	 *     to initials, until the name fits
	 * 11. remove one by one all the given initials until the name fits
	 * 12. go through and make all the surnames into initials except the last one
	 *     until the name fits
	 * 13. remove all the surnames except the last name
	 * 14. remove the pre-fix
	 * 15. remove last name with ellipsis last portion that doesn't fit    **NEEDS TO BE DONE!**
	 * 16. make the surname into an initial
	 * 17. if there is no room for any of this, return a blank String.
	 */
	
	private static String getFitInfo(String Prefix, String GivenNames, String Surname, String Postfix, float width, Font font) {
		String name = Prefix + " " + GivenNames + " " + Surname + " " + Postfix;
		
		//Full Name
		double fontSize = CheckFit(name, width, font);
		//if(fontSize > -1)
		//	return fontSize + ": " + name;
		//System.out.println(name);

		//DO THESE IMMEDIATELY (EVEN IF THEY WOULD/COULD FIT):
		
		//Split Names
		String[] Prefixes = Prefix.split(" ");
		String[] Givens = GivenNames.split(" ");
		String[] Surnames = Surname.split(" ");
		String[] Postfixes = Postfix.split(" ");

		//Convert names to having the first letter capitalized,
		//and the rest lowercase (for uniformity)
		Prefixes = firstLetterCapsOnly(Prefixes);
		Givens = firstLetterCapsOnly(Givens);
		Surnames = firstLetterCapsOnly(Surnames);
		Postfixes = firstLetterCapsOnly(Postfixes);
					
		Surnames = removeOrs(Surnames);
		Givens = removeOrs(Givens);
		
		//Remove ?'s from name
		for(int i = 0; i < Surnames.length; i++) {
			if (Surnames[i] != null) {
				Surnames[i] = Surnames[i].replace('?', ' ');
				Surnames[i] = Surnames[i].trim();
			}
		}
		for(int i = 0; i < Givens.length; i++) {
			if (Givens[i] != null) {
				Givens[i] = Givens[i].replace('?', ' ');
				Givens[i] = Givens[i].trim();
			}
		}
		
			//check for fit
		//	name = GetName(Prefixes, Givens,Surnames,Postfixes);
		//		fontSize = CheckFit(name, width, font);
		//		if(fontSize > -1)
		//			return fontSize + ": " + name;
			//System.out.println(name);
		
		//Remove (_) from surname
		for (int i=0; i<Surnames.length; i++) {
			if(Surnames[i] != null && Surnames[i].startsWith("(")) {
				while (i<Surnames.length) {
					if (Surnames[i] != null && Surnames[i].endsWith(")")) {
						Surnames[i] = null;
						//++i; //Removed By: Spencer Hoffa 3/21/2013
						break;
					}
					else if (Surnames[i] != null) {
						Surnames[i] = null;
						//++i; //Removed By: Spencer Hoffa 3/21/2013
					}
					++i; //Added By: Spencer Hoffa 3/21/2013 This should fix infinite loop problem.
				}
			}
		}
		
		//remove (_) from Surnames names that are inside (not necessarily at the front
		for(int i=0; i<Surnames.length; i++) {
			if(Surnames[i] != null) {
				//System.out.println("name: " + Surnames[i]);
				int k = 0;
				while(k < Surnames[i].length()) {
					//System.out.println("char: " + Surnames[i].charAt(k));
					if(Surnames[i].charAt(k) == '(') {
						//System.out.println("IN HERE!");
						int p = k;
						while(k < Surnames[i].length()) {
							//System.out.println("char2: " + Surnames[i].charAt(k));
							if(Surnames[i].charAt(k) == ')') {
								k++;
								//System.out.println("Substring: " + Surnames[i].substring(p, k));
								Surnames[i] = Surnames[i].replace(Surnames[i].substring(p, k), "");
								k -= 2;
								break;
							}
							k++;
						}
					}
					k++;
				}
			}
		}
		
		//check for fit
		name = GetName(Prefixes, Givens, Surnames, Postfixes);
		//	fontSize = CheckFit(name, width, font);
		//	if(fontSize > -1)
		//			return fontSize + ": " + name;
		//System.out.println(name);
			
		//Remove (_) from given names
		//ToDo: check that the () don't span over multiple names in GivenNames (see Mary Spoor)
		for (int i=0; i<Givens.length; i++) {
			if(Givens[i] != null && Givens[i].startsWith("(")) {
				while(i < Givens.length) {
					if (Givens[i] != null && Givens[i].endsWith(")")) {
						Givens[i] = null;
						//++i; //Removed By: Spencer Hoffa 3/21/2013
						break;
					}
					else if (Givens[i] != null) {
						Givens[i] = null;
						//++i; //Removed By: Spencer Hoffa 3/21/2013
					}
					++i; //Added By: Spencer HOffa 3/21/2013 This should fix the infinite loop problem here.
				}
			}
		}
			
		//remove (_) from Given names that are inside (not necessarily at the front
		for(int i=0; i<Givens.length; i++) {
			if(Givens[i] != null) {
				//System.out.println("name: " + Givens[i]);
				int k = 0;
				while(k < Givens[i].length()) {
					//System.out.println("char: " + Givens[i].charAt(k));
					if(Givens[i].charAt(k) == '(') {
						//System.out.println("IN HERE!");
						int p = k;
						while(k < Givens[i].length()) {
							//System.out.println("char2: " + Givens[i].charAt(k));
							if(Givens[i].charAt(k) == ')') {
								k++;
								//System.out.println("Substring: " + Givens[i].substring(p, k));
								Givens[i] = Givens[i].replace(Givens[i].substring(p, k), "");
								k -= 2;
								break;
							}
							k++;
						}
					}
					k++;
				}
			}
		}
			
		//remove (_) from Prefixes names that are inside (not necessarily at the front
		for(int i=0; i<Prefixes.length; i++) {
			if(Prefixes[i] != null) {
				//System.out.println("name: " + Prefixes[i]);
				int k = 0;
				while(k < Prefixes[i].length()) {
					//System.out.println("char: " + Prefixes[i].charAt(k));
					if(Prefixes[i].charAt(k) == '(') {
						//System.out.println("IN HERE!");
						int p = k;
						while(k < Prefixes[i].length()) {
							//System.out.println("char2: " + Prefixes[i].charAt(k));
							if(Prefixes[i].charAt(k) == ')') {
								k++;
								//System.out.println("Substring: " + Prefixes[i].substring(p, k));
								Prefixes[i] = Prefixes[i].replace(Prefixes[i].substring(p, k), "");
								k -= 2;
								break;
							}
							k++;
						}
					}
					k++;
				}
			}
		}
			
		//remove (_) from Postfixes names that are inside (not necessarily at the front
		for(int i=0; i<Postfixes.length; i++) {
			if(Postfixes[i] != null) {
				//System.out.println("name: " + Postfixes[i]);
				int k = 0;
				while(k < Postfixes[i].length()) {
					//System.out.println("char: " + Postfixes[i].charAt(k));
					if(Postfixes[i].charAt(k) == '(') {
						//System.out.println("IN HERE!");
						int p = k;
						while(k < Postfixes[i].length()) {
							//System.out.println("char2: " + Postfixes[i].charAt(k));
							if(Postfixes[i].charAt(k) == ')') {
								k++;
								//System.out.println("Substring: " + Postfixes[i].substring(p, k));
								Postfixes[i] = Postfixes[i].replace(Postfixes[i].substring(p, k), "");
								k -= 2;
								break;
							}
							k++;
						}
					}
					k++;
				}
			}
		}
			
		//check for fit
		name = GetName(Prefixes, Givens, Surnames, Postfixes);
		//System.out.println(name);
		fontSize = CheckFit(name, width, font);
		if(fontSize > -1)
			return fontSize + ": " + name;
				
		//Middle Initials
		for(int i=1;i<Givens.length;i++) {
			if(Givens[i] != null && Givens[i].length() > 0) {
				Givens[i] = Givens[i].substring(0,1);
			}
		}
		//check for fit
		name = GetName(Prefixes, Givens,Surnames,Postfixes);
		fontSize = CheckFit(name, width, font);
		if(fontSize > -1)
			return fontSize + ": " + name;
		//System.out.println(name);

		//Remove Postfix
		for(int i=0; i<Postfixes.length; i++) {
			Postfixes[i] = null;
		}
			//check for fit
		name = GetName(Prefixes, Givens,Surnames,Postfixes);
		fontSize = CheckFit(name, width, font);
		if(fontSize > -1)
			return fontSize + ": " + name;
		//System.out.println(name);

		//Remove Middle Initials
		for(int i=1;i<Givens.length;i++) {
			Givens[i] = null;
		}
			//check for fit
		name = GetName(Prefixes, Givens,Surnames,Postfixes);
		fontSize = CheckFit(name, width, font);
		if(fontSize > -1)
			return fontSize + ": " + name;
		//System.out.println(name);

		//First Initial
		if(Givens.length > 0 && Givens[0] != null && Givens[0].length() > 1)
			Givens[0] = Givens[0].substring(0,1);
		//check for fit
		name = GetName(Prefixes, Givens,Surnames,Postfixes);
		fontSize = CheckFit(name, width, font);
		if(fontSize > -1)
			return fontSize + ": " + name;
		//System.out.println(name);

		//Remove First Initial
		Givens[0] = null;
			//check for fit
		name = GetName(Prefixes, Givens,Surnames,Postfixes);
		fontSize = CheckFit(name, width, font);
		if(fontSize > -1)
			return fontSize + ": " + name;
		//System.out.println(name);

		//Initials of all but the last of the surname
		for(int i=0;i<Surnames.length-1;i++) {
			if(Surnames[i] != null && Surnames[i].length() > 0)
				Surnames[i] = Surnames[i].substring(0,1);
		}
		//check for fit
		name = GetName(Prefixes, Givens,Surnames,Postfixes);
		fontSize = CheckFit(name, width, font);
		if(fontSize > -1)
			return fontSize + ": " + name;
		//System.out.println(name);
			
		//Remove all but the last of the surname
		for(int i=0;i<Surnames.length-1;i++) {
			Surnames[i] = null;
		}
		//check for fit
		name = GetName(Prefixes, Givens,Surnames,Postfixes);
		fontSize = CheckFit(name, width, font);
		if(fontSize > -1)
			return fontSize + ": " + name;
		//System.out.println(name);
		
		//TODO: last name with ellipsis last portion that doesn't fit
		
		//Remove Prefix
		for(int i=0; i<Prefixes.length; i++) {
			Prefixes[i] = null;
		}
		//check for fit
		name = GetName(Prefixes, Givens,Surnames,Postfixes);
		fontSize = CheckFit(name, width, font);
		if(fontSize > -1)
			return fontSize + ": " + name;
		//System.out.println(name);
			
		//Last Initial
		if(Surnames.length > 0 && Surnames[Surnames.length-1] != null && Surnames[Surnames.length-1].length() != 0) {
			return font.getSize2D() + ": " + Surnames[Surnames.length-1].substring(0,1);
		}
		return "-1:none";
	}

	private static String[] removeOrs(String[] names) {
		for (int i=0;i<names.length;i++) {
			if(names[i] != null) {
				if(names[i].toLowerCase().compareTo("or") == 0) {
					while(i<names.length) {
						names[i] = null;
						++i;
					}
				}
			}
		}
		return names;
	}

	private static String[] firstLetterCapsOnly(String[] names) {
		for(int i = 0; i < names.length; i++) {
			if (names[i] != null && names[i].length() != 0) {
				names[i] = names[i].toLowerCase();
				String lowerName = names[i].substring(1);
				names[i] = Character.toString(Character.toUpperCase(names[i].charAt(0)));
				names[i] = names[i].concat(lowerName);
			}
		}
		return names;
	}

	public static String GetName(String[] Prefixes, String[] Givens, String[] Surnames, String[] Postfixes) {
		String name = "";
		for(int i=0;i<Prefixes.length;i++) {
			if(Prefixes[i] != null)
				name += Prefixes[i].trim() + " ";
		}
		for(int i=0;i<Givens.length;i++) {
			if(Givens[i] != null)
				name += Givens[i].trim() + " ";
		}
		for(int i=0;i<Surnames.length;i++) {
			if(Surnames[i] != null)
				name += Surnames[i].trim() + " ";
		}
		for(int i=0;i<Postfixes.length;i++) {
			if(Postfixes[i] != null)
				name += Postfixes[i].trim() + " ";
		}
		return name;
	}

	//returns font size if it fits and -1 if it doesn't fit
	public static double CheckFit(String name, float width, Font font) {
		double stringwidth = font.getStringBounds(name, frc).getWidth();
		//double stringwidth = font.createGlyphVector(frc, name).getPixelBounds(frc, 0, 0).getWidth();
		
		if(stringwidth <= width)
			return font.getSize2D();
		
		else if(stringwidth * MINFONTSCALE <= width) {
			double minWidth = font.deriveFont((float)(font.getSize() * MINFONTSCALE)).getStringBounds(name, frc).getWidth();
				
			double scaleFactor = (width - minWidth) / (stringwidth - minWidth);
			double scaledFontSize = Math.floor((((1.0 - MINFONTSCALE) * scaleFactor) + MINFONTSCALE) * font.getSize2D());
			double scaledWidth = font.deriveFont((float)scaledFontSize).getStringBounds(name, frc).getWidth();
			

			
			if(scaledWidth <= width)
				return scaledFontSize;
			
			
				//return font.getSize2D() * newWidth/width;
		}

		return -1;
	}
}
