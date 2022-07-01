package com.paintify.controllers;
import java.awt.image.BufferedImage;
import java.awt.Graphics;


import javax.swing.event.MouseInputAdapter;

import com.paintify.panels.GamePanel;

public abstract class DrawingController extends MouseInputAdapter{
    GamePanel viewer;

    protected DrawingController(GamePanel viewer){
        this.viewer=viewer;
    }

    protected Graphics getGraphics(){
        return viewer.getEditor().getImage().getGraphics();
    }

    protected BufferedImage getImage(){
        return viewer.getEditor().getImage();
    }

}