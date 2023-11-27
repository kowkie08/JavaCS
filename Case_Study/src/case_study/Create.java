/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package case_study;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

/**
 *
 * @author Kowkie
 */
public class Create implements ActionListener, Runnable{
    public int lvl = 0,score = 0,bomb=0;
    public String name = "";
    private static final String  BACK = "Back";
    private static final String  EXIT = "Exit";
    private int  counter;
    private JFrame  frame;
    private JPanel  panel;
    private GridBagConstraints cons;
    private int[] mine = {};
    private ScoresClass scc = new ScoresClass();
   
    public Create(ScoresClass scc){
        scc = this.scc;
    }
    @Override // java.awt.event.ActionListener
    public void actionPerformed(ActionEvent event) {
        String actionCommand = event.getActionCommand();
        switch (actionCommand) {
            case BACK:   frame.setVisible(false);
                         MainMenu mm = new MainMenu();
                         mm.setVisible(true);
                break;
            case EXIT:                
            default:
                JOptionPane.showMessageDialog(frame,
                                              actionCommand,
                                              "Unhandled",
                                              JOptionPane.WARNING_MESSAGE);
        }
        
            
    }

    @Override // java.lang.Runnable
    public void run() {
//        JOptionPane.showMessageDialog(null,String.valueOf(lvl));
        createGui();
     
        Checker chk = new Checker();
        mine = chk.CheckMines(lvl);
        while(counter != 36)
        {
            addButton();
        }
        
    }
    

    private void addButton() {
        JButton button = new JButton("");
        ++counter;
        Dimension size = new Dimension(50, 50);
        button.setMaximumSize(size);
        button.setMinimumSize(size);
        button.setPreferredSize(size);
        for(int x=0;x< mine.length;x++){
            
            if(counter == mine[x])
            {
                 button.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
//                         JOptionPane.showMessageDialog(null,"Bomba!!");
                         bomb = 1;
                         button.setEnabled(false);
                         name = JOptionPane.showInputDialog("GAME OVER!!\nPlease Enter Name: ");
                         scc.setName(name);
                         frame.setVisible(false);
                         MainMenu mm = new MainMenu();
                         mm.setVisible(true);
                        String test = "";
                        FileWriter fw = null; 
                        BufferedWriter bw = null; 
                        PrintWriter pw = null;
                    try {
                             fw = new FileWriter("scores.txt", true); 
                             bw = new BufferedWriter(fw); 
                             pw = new PrintWriter(bw);

//                             PrintWriter out = new PrintWriter("filename.txt");

                                pw.println(scc.getName()+"     " +scc.getScore());   // writes the bytes
                               pw.flush();
                          }
                           catch (IOException io) 
                           {// can't do anything } 
                           }
                  

                        finally {
                           try {
                               pw.close();
                               bw.close();
                               fw.close();
                            }
                           catch (IOException io) {// can't do anything }
                           }

                            }

                         
                     }
                     
                 });
                  
                 
                
                  break;
                  
            }
           
            else{
                  button.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                        button.setEnabled(false);
                        score+=1;
                        scc.setScore(score);
                        
                       
                     }
                 });
                   
            }
            
    }
        
        cons = new GridBagConstraints();
        GridBagLayout gb = new GridBagLayout();
        JPanel gridbag = new JPanel( gb);
               gridbag.setSize(size);
        cons.fill = GridBagConstraints.BOTH;
        gb.setConstraints(frame, cons);
         gridbag.add(button);
        panel.add(gridbag);
        panel.revalidate();
    }

    private JButton createButton(String text, int mnemonic, String tooltip) {
        JButton button = new JButton(text);
        button.setMnemonic(mnemonic);
        button.setToolTipText(tooltip);
        button.addActionListener((ActionListener) this);
        return button;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(createButton(BACK, KeyEvent.VK_B, "Add button to panel"));
        buttonsPanel.add(createButton(EXIT, KeyEvent.VK_X, "Exit the application"));
        return buttonsPanel;
    }

    private void createGui() {
        frame = new JFrame("Minesweeper");
        frame.setUndecorated(true);
        frame.setMaximumSize(new Dimension(500,500));
        frame.setMinimumSize(new Dimension(500,500));
        frame.setPreferredSize(new Dimension(500,500));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(createPanel(), BorderLayout.CENTER);
        frame.add(createButtonsPanel(), BorderLayout.PAGE_END);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
    }

    private JPanel createPanel() {
        panel = new JPanel();
        Border blackline = BorderFactory.createLineBorder(Color.black);
//       GridLayout mgr = new GridLayout(6,6);
//       mgr.setHgap(0);
//       mgr.setVgap(0);
        GridLayout gb = new GridLayout(6,6);
        gb.setHgap(0);
        gb.setVgap(0);
        
        panel.setLayout(gb);
        panel.setSize(new Dimension(300,300));
        panel.setBorder(blackline);
        return panel;
    }
}
