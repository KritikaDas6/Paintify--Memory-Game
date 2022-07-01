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

// The brains of the operation...the actual game. Stuff like hints and  getting the completed ratio for progress bars and showing messages (text saying GOOD JOB)

public class ColorPuzzle extends ImageEditor implements ActionListener {
    private BufferedImage imageReference;
    private int hintPercentage = 100;
    Timer animation = new Timer(0, this);
    String showMessage;

    int x=0;

    private int initialMatch=0;
    private Color messageColor;

    ColorPuzzle(){
        showHint();
        addMouseMotionListener(this);
        showMessage="";
        messageColor = Color.BLACK;
        // addMouseListener(this);
        // addMouseInpu(this);

    }
    public void showHint(){
        hintPercentage =100;
        repaint();
        animation.setInitialDelay(5000); //Victoria
        animation.start();
    }


    public void setInitialMatch(){
        initialMatch=0;
        for(int x = 0; x< image.getWidth(); x ++){
            for(int y = 0; y < image.getHeight(); y++){
                Color bwColor = new Color(image.getRGB(x, y));
                Color fillColor = new Color(imageReference.getRGB(x, y));
                if(isColorCloseEnough(bwColor,fillColor))
                    initialMatch++;
            }
        }
    }    
// Completed ratio = Coloring page's colored in pixels / answer key's colored in pixels
    public float getCompletedRatio(){
        
        // same is the number of black and white pixels that are in both pictures, not to be added to filled ratio
        float same = 0; 
        for(int x = 0; x< image.getWidth(); x ++){
            for(int y = 0; y < image.getHeight(); y++){
                Color bwColor = new Color(image.getRGB(x, y));
                Color fillColor = new Color(imageReference.getRGB(x, y));
                
                if(isColorCloseEnough(bwColor,fillColor))
                    same++;
            }
        }
        int totalPixels=image.getHeight()*image.getWidth();
        totalPixels-=initialMatch;
        same-=initialMatch;
        return same/ (float)(totalPixels);
    }


    public BufferedImage getReferenceImage(){
        return imageReference;

    }

    public void showMessage(String message, Color c,  int timeout){
        this.showMessage = message;
        this.messageColor = c;
        Timer messageTimout = new Timer(timeout, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                showMessage="";
            }
        });
        messageTimout.setRepeats(false);
        messageTimout.start();
    }



    public void loadImage(String str){
        super.loadImage(str);
        int indexExtn = str.indexOf(".png");
        String colorImageFile = str.substring(0, indexExtn) + ".color" + str.substring(indexExtn);
        try {
            imageReference = ImageIO.read(ImageEditor.class.getResource(colorImageFile));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }  

        ColorPalettePicker cp=new ColorPalettePicker(imageReference);
        setInitialMatch();
    }

    public void paintComponent(Graphics g)
        {
           super.paintComponent(g);

          if(hintPercentage > 0){ // Hint Modes 
            int width = imageReference.getWidth();
            int showWidth= (int)((hintPercentage*width)/100.0);
;           
                //Hint Side Sweep
              g.drawImage(imageReference.getSubimage(0, 0, showWidth, imageReference.getHeight()), 0, 0, this);

              // Hint Grid

              // Hint Maginfying Glass / Brush Reveal

          }

            if(showMessage.length()>0){
                Graphics2D g2d = (Graphics2D) g;
    
                Font font = new Font("Serif", Font.PLAIN, 32);
                // font.setColor(Color.RED);
                g2d.setColor(messageColor);
                g2d.setFont(font);
                FontMetrics fontMetrics = g2d.getFontMetrics();
                g2d.drawString(showMessage, 0, 10+fontMetrics.getAscent());
            }
        //     g.drawImage(imageReference,0,0,this);
        }

        public void setHintPercentage(int hintPercent){
            this.hintPercentage  = hintPercent;
            this.repaint();
        }

        public int getHintPercent(){
            return this.hintPercentage;
        }

        
          // just a rough comparison 
    private boolean isColorCloseEnough(Color c1, Color c2){
        return ((Math.abs(c1.getGreen() - c2.getGreen())<30) &&
        (Math.abs(c1.getBlue() - c2.getBlue())<30) && 
        (Math.abs(c1.getRed() - c2.getRed())<30));  
    }
    
    public boolean isColorMatch(int x, int y, Color fillColor){
        Color colorAt = new Color(imageReference.getRGB(x,y));
        // float[] hsbValsColorAt = new float[3];

        // Color.RGBtoHSB(colorAt.getRed(),colorAt.getGreen(),colorAt.getBlue(),hsbValsColorAt);

        // float [] hsbValsFillColor = new float[3];

        // Color.RGBtoHSB(fillColor.getRed(),fillColor.getGreen(),fillColor.getBlue(),hsbValsFillColor);
        // int fillHue = (int)((hsbValsFillColor[0])*360) /30*30;
        // int colorAtHue = (int)((hsbValsColorAt[0])*360) /30*30;
        // return (fillHue  == colorAtHue);


        return isColorCloseEnough(fillColor,colorAt);  
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x=e.getX();
        repaint();        
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        hintPercentage--;
        if (hintPercentage == 0){
            animation.stop();
        }
        repaint();
    
    }  
}
