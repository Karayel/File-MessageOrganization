/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package File;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Karayel
 */
public class MyFile {

    private static final String DESKTOP_DESTINATION = System.getProperty("user.home") + "\\Desktop";

    public static String getCreationTime(BasicFileAttributes attr) {
        FileTime f = MyFileFileProperties.getProperties('c', attr);
        return getDate(new Date(f.toMillis()));
    }

    public static String getCreationYear(BasicFileAttributes attr) {
        FileTime f = MyFileFileProperties.getProperties('m', attr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(f.toMillis()));
        int year = cal.get(Calendar.YEAR);
        return "" + year;
    }
    
    public static String getCreationMonth(BasicFileAttributes attr) {
        String res = "";
        FileTime f = MyFileFileProperties.getProperties('m', attr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(f.toMillis()));
        int month = cal.get(Calendar.MONTH);
        if (month + 1 < 10) {
            res += ("0" + (month + 1));
        } else {
            res += month + 1;
        }
        return res;
    }

    public static String getLastAccessTime(BasicFileAttributes attr) {
        FileTime f = MyFileFileProperties.getProperties('a', attr);
        return getDate(new Date(f.toMillis()));
    }

    public static String getLastModifiedTime(BasicFileAttributes attr) {
        FileTime f = MyFileFileProperties.getProperties('m', attr);
        return getDate(new Date(f.toMillis()));

    }

    private static String getDate(Date d) {
        String res = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        if (day < 10) {
            res += ("0" + day + ".");
        } else {
            res += day + ".";
        }
        if (month + 1 < 10) {
            res += ("0" + (month + 1) + ".");
        } else {
            res += month + 1 + ".";
        }
        res += year;
        return res;
    }

    public static void moveFile(File f, String path) {
        f.renameTo(new File(path + "\\" + f.getName()));
        f.delete();
    }

    public static ArrayList<File> getAllFileName(File f) {
        ArrayList<File> fileList = new ArrayList<>();

        File[] files = f.listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null. 

        for (File file : files) {
            if (file.isFile()) {
                fileList.add(new File(f.getAbsolutePath() + "\\" + file.getName()));
            }
        }
        return fileList;
    }

    public static void moveAllFile(File f) {
        new File(DESKTOP_DESTINATION + "\\Result").mkdir();
        try {
            ArrayList<File> allFile = getAllFileName(f);
            for (File allFile1 : allFile) {
                Path file = Paths.get(allFile1.getAbsolutePath());
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                String creationDate = getLastModifiedTime(attr);
                String creationYear = getCreationYear(attr);
                String creationMonth = getCreationMonth(attr);
                
                String yearPath = DESKTOP_DESTINATION + "\\Result\\" + creationYear;
                String monthPath = DESKTOP_DESTINATION + "\\Result\\" + creationYear+"\\"+creationMonth;
                String datePath = DESKTOP_DESTINATION + "\\Result\\" + creationYear + "\\"+creationMonth +"\\"+ creationDate;
                
                Path creationYearPath = Paths.get(yearPath);
                Path creationMonthPath = Paths.get(monthPath);
                Path creationDatePath = Paths.get(datePath);
                
                
                if (Files.exists(creationYearPath)) {
                    if(Files.exists(creationMonthPath)){
                        setDatePath(allFile1, creationDatePath, datePath);
                    }else{
                        new File(monthPath).mkdir();
                        setDatePath(allFile1, creationDatePath, datePath);
                    }
                } else {
                    new File(yearPath).mkdir();
                    if(Files.exists(creationMonthPath)){
                        setDatePath(allFile1, creationDatePath, datePath);
                    }else{
                        new File(monthPath).mkdir();
                        setDatePath(allFile1, creationDatePath, datePath);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MyFile.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void setDatePath(File f, Path creationDatePath, String datePath) {
        if (Files.exists(creationDatePath)) {
            moveFile(f, datePath);
        } else {
            new File(datePath).mkdir();
            moveFile(f, datePath);
        }
    }
}
