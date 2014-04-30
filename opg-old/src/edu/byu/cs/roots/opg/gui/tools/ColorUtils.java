package edu.byu.cs.roots.opg.gui.tools;

import java.awt.Color;

public class ColorUtils {

	public enum colorScheme { 
		RGB("RGB"),HSV("HSV"),HSL("HSL");
		public String name;
		private colorScheme(String name){
			this.name = name;
		}
		public String toString(){ return name;}
	}
	
	
	
	public static float[] HSLtoHSB(float hue, float saturation, float luminosity, float[] hsb){
//		System.out.println("Converting   ... hue " + hue + " sat " + saturation + " Luminosity " + luminosity);
		double temp2 = (luminosity < .5) ? luminosity*(1 + saturation) : luminosity + saturation - (luminosity*saturation);
		double temp1 = 2*luminosity - temp2;
		double tempr = hue + (1/3.0);
		double tempg = hue;
		double tempb = hue - (1/3.0);
		if(tempr > 1) tempr--;
		if(tempr < 0) tempr++;
		if(tempg > 1) tempg--;
		if(tempg < 0) tempg++;
		if(tempb > 1) tempb--;
		if(tempb < 0) tempb++;
		double r = (tempr < (1/6.)) ?  temp1 + ((temp2-temp1) * 6. * tempr) : 
			      (tempr < 1/2.)  ? temp2 :
			      (tempr < 2./3.) ? temp1 + ((temp2-temp1)*6*(2./3.-tempr)) : temp1;
	    double g = (tempg < (1./6.)) ?  temp1 + ((temp2-temp1) * 6. * tempg) : 
			      (tempg < 1./2.)  ? temp2 :
			      (tempg < 2./3.) ? temp1 + ((temp2-temp1)*6*(2./3.-tempg)) : temp1;
        double b = (tempb < (1./6.)) ?  temp1 + ((temp2-temp1) * 6. * tempb) : 
			      (tempb < 1./2.)  ? temp2 :
			      (tempb < 2./3.) ? temp1 + ((temp2-temp1)*6*(2./3.-tempb)) : temp1;
//		System.out.println("Converting back to rgb ... Red "+ r + " Green " + g + " Blue " + b);
        double max = Math.max(Math.max(r,g),b);
		double min = Math.min(Math.min(r,g),b);
		
		hsb[0] = hue;
		hsb[1] = (float) ((max-min)/max);
		hsb[2] = (float) max;
//		System.out.println("hue " + hsb[0] + " sat " + hsb[1] + " Brightness " + hsb[2]);
		return hsb;
		
		
	}
	
	
	public static float[] HSBtoHSL(float hue, float saturation, float brightness, float[] hsl){
		int rgb = Color.HSBtoRGB(hue, saturation, brightness);
		double r = ((rgb & 0xff0000)>>16)/255.; 
		double g = ((rgb & 0x00ff00)>>8)/255.;
		double b = ((rgb & 0x0000ff))/255.;
//		int rr = (rgb & 0xff0000)>>16;
//		int gg = (rgb & 0x00ff00)>>8;
//		int bb = (rgb & 0x0000ff);
//		System.out.println("Red "+ rr + " Green " + gg + " Blue " + bb);
//		System.out.println("Normalized: " + r + " " + g + " " + b);
		double max = Math.max(Math.max(r,g),b);
		double min = Math.min(Math.min(r,g),b);
//		System.out.println(max + " " + min);
		if(hsl == null) hsl = new float[3];
		hsl[0] = hue;
		hsl[2] = (float) (.5*(max+min));
		hsl[1] = (hsl[2] == 0) ? 0 :
				 (hsl[2] <= .5) ? (float)((max-min)/(2*hsl[2])) : (float)((max-min)/(2 - 2*hsl[2]));
//		System.out.println("hue " + hsl[0] + " sat " + hsl[1] + " Luminosity " + hsl[2]);
		return hsl;
		
	}

}
