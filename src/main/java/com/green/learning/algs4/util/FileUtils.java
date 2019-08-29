package com.green.learning.algs4.util;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUtils
{
    private static String dataDir;
    private static final String pathSep = File.separator; // windows "\\"
//    private static final String pathSep = File.pathSeparator; // windows ";"
    
    static
    {
        try
        {
            String classpath = new File("").getCanonicalPath()
                    + pathSep + "src/main/resources";
            dataDir = classpath + pathSep + "data" + pathSep;
        } catch (IOException ex)
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
     *
     * @param dir
     * @param filename
     * @return
     */
    public static File getFile(String dir, String filename)
    {
        String filepath = dataDir + dir + pathSep + filename;
        return new File(filepath);
    }
    
    private static final int RADIX = 16;
    private static final String KEY_MD5 = "MD5";
    private static final String KEY_SHA = "SHA";
    
    public static String md5(String filePath)
    {
        return encrypt(filePath, KEY_MD5);
    }
    
    public static String sha(String filePath)
    {
        return encrypt(filePath, KEY_SHA);
    }
    
    private static String encrypt(String filePath, String algorithm)
    {
        try
        {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(filePath)));
            byte[] buffer = new byte[4096];
            while (in.available() > 0)
            {
                int len = in.read(buffer);
                messageDigest.update(buffer, 0, len);
            }
            return new BigInteger(messageDigest.digest()).toString(RADIX);
        } catch (NoSuchAlgorithmException | IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
//    public static long length(String filePath)
//    {
//        File file = new File(filePath);
//        return file.length();
//    }
}
