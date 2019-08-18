package com.green.learning.algs4;

import java.io.File;
import java.io.IOException;

@Deprecated
public class Config
{
    private static String dataDir;
    private static final String pathSep = File.separator; // windows "\\"
//    private static final String pathSep = File.pathSeparator; // windows ";"
    
    static {
        File projectDirFile = new File("");
//        String projectAbsDir = projectDirFile.getAbsolutePath();
        try{
            String projectDir = projectDirFile.getCanonicalPath();
            dataDir = projectDir +  pathSep + "data" + pathSep;
        }catch (IOException ex)
        {
            dataDir = "";
            ex.printStackTrace();
        }
    }
    
    public static String getDataDir()
    {
        return dataDir;
    }
}
