/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

/**
 *
 * @author Karayel
 */
import File.MyFile;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;

public class Main extends JPanel
        implements ActionListener {

    JButton file;
    JButton message;

    JFileChooser chooser;
    String choosertitle;

    public Main() {
        setLayout(new GridLayout(2, 1));
        file = new JButton("Select File Destination");
        file.addActionListener(this);
        add(file);
        message = new JButton("Select Message Destination");
        message.addActionListener(this);
        add(message);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int result;

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle(choosertitle);

        if (e.getActionCommand().equals("Select File Destination")) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            try {
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    MyFile.moveAllFile(new File(chooser.getSelectedFile().toString()));
                    JOptionPane.showMessageDialog(this, "Mission completed.", "File Process", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please,check your file destination", "File Process", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals("Select Message Destination")) {
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                Message.Message.allProcess(new File(chooser.getSelectedFile().toString()), this);
                JOptionPane.showMessageDialog(this, "Mission completed.", "Message Process", JOptionPane.INFORMATION_MESSAGE);
            }

        }

    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    public static void main(String s[]) {
        JFrame frame = new JFrame("");
        Main panel = new Main();
        frame.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                }
        );
        frame.getContentPane().add(panel, "Center");
        frame.setTitle("File Organization");
        frame.setSize(250, 250);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
