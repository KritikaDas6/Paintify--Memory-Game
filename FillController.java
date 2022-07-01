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

public class FillController extends DrawingController {

    public FillController(GamePanel viewer){
        super(viewer);
    }

    private boolean isColorSimilar(Color c1, Color c2){

        double dist = Math.sqrt((Math.pow((c1.getRed()-c2.getRed()),2)+
        Math.pow((c1.getGreen()-c2.getGreen()),2)+
        Math.pow((c1.getBlue()-c2.getBlue()),2)));

        return (dist<220);
    }

    public void fillColor(int x, int y, Color col){
        BufferedImage image  = getImage();

        Rectangle rectBounds = new Rectangle(0,0,image.getWidth(), image.getHeight());
        Stack<Point> stack=new Stack<Point>();
        HashSet<Point> visited = new HashSet<Point>();

        stack.push(new Point(x,y));
        Color startingColor = new Color(image.getRGB(x,y));
        

        while (!stack.isEmpty()){
            Point currentPoint = stack.pop();
            if (stack.size()>300000) {
                System.out.println("I am really lost now, so no more flood fill");
                break;
            }


            if (rectBounds.contains(currentPoint)){
                Color currentColor=new Color(image.getRGB(currentPoint.x, currentPoint.y));
                if (isColorSimilar(currentColor, startingColor))
                {
                    visited.add(currentPoint);
                    image.setRGB(currentPoint.x, currentPoint.y, col.getRGB());

                    Point left=(Point)currentPoint.clone(); left.translate(-1,0);
                    Point right=(Point)currentPoint.clone(); right.translate(1,0);
                    Point top=(Point)currentPoint.clone(); top.translate(0,1);
                    Point bottom=(Point)currentPoint.clone(); bottom.translate(0,-1);

                    if (!visited.contains(left)) 
                        stack.push(left);
                    if (!visited.contains(right)) 
                        stack.push(right);
                    if (!visited.contains(top))   
                        stack.push(top);
                    if (!visited.contains(bottom)) 
                        stack.push(bottom);


                    // stack.push(new Point(currentPoint.x-1,currentPoint.y));
                    // stack.push(new Point(currentPoint.x-1,currentPoint.y));                    stack.push(new Point(currentPoint.x+1,currentPoint.y));
                    // stack.push(new Point(currentPoint.x,currentPoint.y+1));
                    // stack.push(new Point(currentPoint.x,currentPoint.y-1));

                }
            }
        }        
        viewer.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e){
        AppConfig config=AppConfig.getInstance();
        Color chosen = (Color)config.getConfig(AppConfig.FILL_COLOR);

        ImageEditor editor = viewer.getEditor();

        if (editor instanceof ColorPuzzle){
            ColorPuzzle ccEditor = (ColorPuzzle) editor;
            if (ccEditor.isColorMatch(e.getX(),e.getY(), chosen)){
                fillColor(e.getX(),e.getY(), chosen);
                ccEditor.showMessage("NICE , Keep Going!", Color.GREEN, 500);
            }else
                ccEditor.showMessage("Wrong Color, Try Again",Color.RED, 2000);

        }else
            fillColor(e.getX(),e.getY(), chosen);

        viewer.repaint();
    }
}

