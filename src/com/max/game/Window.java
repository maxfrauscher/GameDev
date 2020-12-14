package com.max.game;

import javax.swing.JFrame;

public class Window extends JFrame {

    public Window() {
        setTitle("Monster Hunter Java");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel(1200,720));
        setIgnoreRepaint(true);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

}
