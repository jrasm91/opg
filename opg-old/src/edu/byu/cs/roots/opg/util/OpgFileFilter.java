package edu.byu.cs.roots.opg.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.swing.filechooser.FileFilter;



public class OpgFileFilter extends FileFilter implements FilenameFilter {

	private static String [] extopg = {"opg"};
	public static OpgFileFilter OPG = new OpgFileFilter("OnePage Project File (.opg)", extopg);
	private static String [] extged = {"ged"};
	public static OpgFileFilter GED = new OpgFileFilter("GEDCOM File (.ged)", extged);
	private static String [] extopgged = {"opg", "ged"};
	public static OpgFileFilter OPGGED = new OpgFileFilter("OPG Compatible Files (.opg, .ged)", extopgged);
	private static String [] extpafzip = {"paf", "zip"};
	public static OpgFileFilter PAFZIP = new OpgFileFilter("Personal Ancestral File 5 databases (.paf, .zip)", extpafzip);

        //---------------------------------------------------------------------------------------
        //  Added By Spencer Hoffa 
        //  Added on: 10/31/2012
        //  This was added to accept all supported files
        //---------------------------------------------------------------------------------------
        private static String [] extAll = {"opg", "ged", "paf", "zip"};
        public static OpgFileFilter ALL = new OpgFileFilter("Supported File Types (.opg, .ged, .pag, .zip)", extAll);
        //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        //
        /////////////////////////////////////////////////////////////////////////////////////////
	
	private String desc;
	private String[] extensions;
	
	
	private OpgFileFilter(String description, String[] extensions){
		this.desc = description;
		this.extensions = extensions;
	}

	@Override
	public boolean accept(File f) {
	    if (f.isDirectory()) {
	    	return true;
	    }

	    String extension = getExtension(f);
	    if (extension != null) {
	    	int len = extensions.length;
			for(int i = 0;i<len;i++){
				if(extension.compareToIgnoreCase((extensions[i])) == 0) return true;
			}
	    }

	    return false;
	}


	
	@Override
	public String getDescription() {
		return desc;
	}
	
	private static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1);
        }
        return ext;
    }

	public boolean accept(File dir, String name) {
		try {
			return accept(new File(dir.getCanonicalPath() + name));
		} catch (IOException e) {
			return false;
		}
	}
   
}
