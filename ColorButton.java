package com.paintify.panels;

import java.awt.Color;

import javax.swing.JButton;

public class ColorButton extends JButton{
    Color selColor=Color.BLACK;

    public void setColor(Color c){
        selColor = c;
    }
    public Color getColor(){
        return selColor;
    }
    
}
