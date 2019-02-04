package EBM_tool.gui;
import java.awt.Dimension;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JFrame frame = new MainFrame("EBM tool");
                frame.setSize(1600, 1000);
                frame.setMinimumSize(new Dimension(1000, 900));
                //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}
