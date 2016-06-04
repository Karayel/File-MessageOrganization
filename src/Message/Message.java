/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Karayel
 */
public class Message {

    private static final String DESKTOP_DESTINATION = System.getProperty("user.home") + "\\Desktop";

    public static void allProcess(File f, JPanel panel) {
        createAllFile(f, panel);
        readAllLine(f, panel);
    }

    private static ArrayList<String> findingTextName(File f, JPanel panel) {
        ArrayList<String> textNames = new ArrayList();
        int i = 1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();

            while (line != null) {
                String full = findingFullDay(line).toString();
                if (!textNames.contains(full)) {
                    textNames.add(full);
                }
                line = br.readLine();
                i++;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            JOptionPane.showMessageDialog(panel, "Please, check the line " + i, "Message Process", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return textNames;
    }

    private static String findingFullDay(String s) {
        String fullDate = "";
        if(findingDay(s) < 10){
            fullDate += "0"+findingDay(s)+" ";
        }else{
          fullDate += findingDay(s)+" ";  
        }
        if(findingMonth(s) < 10){
            fullDate += "0"+findingMonth(s)+" ";
        }
        else{
           fullDate += findingMonth(s)+" ";  
        }
        fullDate += findingYear(s);
        return fullDate;
    }

    private static int findingDay(String s) {
        return Integer.parseInt(s.substring(0, 2));
    }

    private static int findingMonth(String s) {
        return Integer.parseInt(s.substring(3, 5));
    }

    private static int findingYear(String s) {
        return Integer.parseInt(s.substring(6, 10));
    }

    private static void createAllFile(File f, JPanel panel) {
        ArrayList<String> list = findingTextName(f, panel);
        String main = "";
        if (list != null) {
            main = DESKTOP_DESTINATION + "\\WhatsapMessage";
            new File(main).mkdir();
        }
        int i = 0;
        for (i = 0; i < list.size(); i++) {
            String year = main + "\\" + Integer.toString(findingYear(list.get(i)));
            String month = year + "\\" + Integer.toString(findingMonth(list.get(i)));
            String day = month + "\\" + findingFullDay(list.get(i)) + ".txt";

            File y = new File(year);
            File m = new File(month);
            File d = new File(day);

            if (!y.exists()) {
                y.mkdir();
            }
            if (!m.exists()) {
                m.mkdir();
            }
            if (!d.exists()) {
                try {
                    d.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(Message.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private static int fileSize(File f, JPanel panel) {
        return findingTextName(f, panel).size();
    }

    private static void readAllLine(File f, JPanel panel) {
        int i = 1;
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = br.readLine();
            String main = DESKTOP_DESTINATION + "\\WhatsapMessage";
            while (line != null) {
                String fullDay = findingFullDay(line).toString();
                String year = main + "\\" + findingYear(fullDay);
                String month = year + "\\" + findingMonth(fullDay);
                String day = month + "\\" + fullDay + ".txt";
                File file = new File(day);
                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(line + "\n");
                bw.close();
                i++;
                line = br.readLine();
            }
            br.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(panel, "check" + i, "Message Process", JOptionPane.ERROR_MESSAGE);
        }
    }
}
