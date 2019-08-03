package com.green.learning_algs4.util;

import java.io.File;
import java.io.IOException;

public class FileUtils
{
    private static String dataDir;
    private static final String pathSep = File.separator; // windows "\\"
//    private static final String pathSep = File.pathSeparator; // windows ";"
    
    static {
        try{
            String classpath = new File("").getCanonicalPath()
                    + pathSep + "src/main/resources";
            dataDir = classpath +  pathSep + "data" + pathSep;
        }catch (IOException ex)
        {
            dataDir = pathSep + "data" + pathSep;
            ex.printStackTrace();
        }
    }
    
    public static String getDataDir()
    {
        return dataDir;
    }
    
    /**
     * string/ip.csv
     * @param dir
     * @param filename
     * @return
     */
    public static File getFile(String dir, String filename)
    {
        String filepath = dataDir + dir + pathSep + filename;
        return new File(filepath);
    }
}
