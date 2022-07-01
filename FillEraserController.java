package com.paintify.controllers;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Stack;

import com.paintify.app.AppConfig;
import com.paintify.panels.ColorPuzzle;
import com.paintify.panels.ImageEditor;
import com.paintify.panels.GamePanel;

import java.awt.image.BufferedImage;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;

public class FillEraserController extends FillController {

    public FillEraserController(GamePanel viewer){
        super(viewer);
    }

    @Override
    public void fillColor(int x, int y, Color col){
        super.fillColor(x, y, Color.WHITE); 
    }

     @Override
    public void mousePressed(MouseEvent e){
        AppConfig config=AppConfig.getInstance();
        Color chosen = (Color)config.getConfig(AppConfig.FILL_COLOR);
        ImageEditor editor = viewer.getEditor();
        fillColor(e.getX(),e.getY(), chosen);
        viewer.repaint();
    }
}