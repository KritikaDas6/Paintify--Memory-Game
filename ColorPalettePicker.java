package com.paintify.panels;
import java.io.*;
import java.awt.image.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.paintify.app.AppConfig;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorPalettePicker extends JPanel{
    HashMap<Color,Integer> palette;

    ColorPalettePicker(){
        super();
    }
     // Creating a pallete based on image, has math to round the color by a certain threshold so it selects the right amount

    private void createPalette(BufferedImage image){
        palette = new HashMap<Color,Integer>();
        for(int x = 0; x< image.getWidth(); x ++){
           for(int y = 0; y < image.getHeight(); y++){
               Color c = new Color(image.getRGB(x, y));
               
                // 20 has worked quite well for all images
               int roundOffBy = 20;

            // get the RGB of the selected color
               int red = (c.getRed()/roundOffBy)*roundOffBy;
               int green =         (c.getGreen()/roundOffBy)*roundOffBy;
               int blue = (c.getBlue()/roundOffBy)*roundOffBy;
                // approximate because we don't want 40000     unique palette colors
               Color approxColor = new Color(red,green,blue);

               //  if the pallete already has the approximate color, don't add it, otherwise, add it 
                           if (palette.containsKey(approxColor)) {
                   Integer colorCount=palette.get(approxColor);
                   palette.put(approxColor, Integer.valueOf(colorCount.intValue()+1));
               }else
                    palette.put(approxColor, Integer.valueOf(1));
           }
       }
        // Drawing the pallete as buttons with action listeners
       for(Color c:palette.keySet()){
           ColorButton btn = new ColorButton();
           btn.setBackground(c);
           btn.setColor(c);
           btn.setSize(new Dimension(50,50));
           btn.setPreferredSize(new Dimension(50,50));
           btn.addActionListener(new ActionListener(){
               // Class inside a method parameter!! Because we want to override the actionPerformed

            @Override
            public void actionPerformed(ActionEvent e) {
                ColorButton bt= (ColorButton)e.getSource();

                System.out.println(palette.get(bt.getColor()));
                
             // Setting the fill color based on button pressed
                AppConfig config=AppConfig.getInstance();
                config.setConfig(AppConfig.FILL_COLOR, bt.getColor());
            }
           }); // end of mini class
           
            // And finaly, add that button to the palette
           add(btn);
       }
        // testing # of colors in palette
       System.out.println("Palette is : " + palette.size());
    }

    public ColorPalettePicker(BufferedImage image){
        setPreferredSize(new Dimension(200,200));        
        createPalette(image);
        setLayout(new FlowLayout());
        setBorder(BorderFactory.createEtchedBorder());

    }
    
}
