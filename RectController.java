package com.paintify.controllers;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import com.paintify.panels.GamePanel;

import java.awt.Color;
import java.awt.Graphics;

public class RectController extends DrawingController {

    int startX=0, startY=0;
    int endX=0, endY=0;    

    public RectController(GamePanel viewer){
        super(viewer);
    }

    @Override
    public void mousePressed(MouseEvent e){
        startX=e.getX();
        startY=e.getY();        
    }

    @Override
    public void mouseMoved(MouseEvent e){
        viewer.setCurrentX(e.getX());
        viewer.setCurrentY(e.getY());
        viewer.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Graphics graphics = viewer.getEditor().getGraphics();        
        endX=e.getX();
        endY=e.getY();
        if ((startX<endX) &&  (startY<endY)){
            graphics.setColor(Color.RED);
            graphics.drawRect(startX, startY, endX-startX,endY-startY);
            viewer.repaint();
        }
        System.out.println(e.getX()+" : "+e.getY());
        
    }
    
}
