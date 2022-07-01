
package com.paintify.panels;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import com.paintify.app.MainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.io.IOException;

// Progress bar at the bottom displaying percent completed

public class ProgressPanel extends JComponent implements ActionListener{
    ColorPuzzle puzzle;
    Timer repaintTimer = new Timer(2000, this);
    public ProgressPanel(ColorPuzzle c){
        this.puzzle = c;
        setMinimumSize(new Dimension(400, 40));
        setPreferredSize(new Dimension(400, 40));
        setOpaque(true);
        repaintTimer.start();
    }

    

    public void paintComponent(Graphics g)
    {
       super.paintComponent(g);
       g.setColor(Color.GREEN);

       int width = getWidth();
        // getCompletedRatio math comes from ColorPuzzle
       int fillWidth = (int)(width*puzzle.getCompletedRatio());

       for(int x =0 ; x<fillWidth ; x+=12)
           g.fillRect(x, 0, 10 , 30);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }


}