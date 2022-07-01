
package com.paintify.panels;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.*;
import java.io.IOException;

public class ImageEditor extends JPanel implements Scrollable, MouseInputListener  {
        /** the image to draw */
        protected BufferedImage image;        
        /** the preferred size of the display */
        private Dimension prefSize;  
        /** the current x index */
        private int currentX = 0;    
        /** the current y index */
        private int currentY = 0;
        private String currentlySelectedImageFileName;
      
        /**
         * Constructor that takes the image to display
         * @param theImage the image to display
         */
        public ImageEditor()
        {
          image = null;
        }
        
        // /**
        //  * Constructor that takes the image and current x and y
        //  * @param theImage the image to display
        //  * @param x the current x value to use
        //  * @param y the current y value to use
        //  */
        // public ImageEditor(Image theImage, int x, int y)
        // {
        //   this();
        //   currentX = x;
        //   currentY = y;
        // }

        public void reload(){
          loadImage(currentlySelectedImageFileName);
          repaint();
        }
        
        /**
         * Method to get the image
         * @return the image
         */
        public BufferedImage getImage() { return image; }
        
        /**
         * Method to get the current x
         * @return the current x value
         */
        public int getCurrentX() { return currentX; }
        
        /**
         * Method to get the current y
         * @return the current y value
         */
        public int getCurrentY() { return currentY; }
        
        /**
         * Method to set the current x
         * @param x the x value to use
         */
        public void setCurrentX(int x) 
        {
          currentX = x;
          repaint();
        }
        
        /**
         * Method to set the current y
         * @param y the y value to use
         */
        public void setCurrentY(int y) 
        {
          currentY = y;
          repaint();
        }
        
        /**
         * Method to set the image
         * @param theImage the new image to use
         */
        public void setImage(BufferedImage theImage)
        {
          image = theImage;
          setPreferredSize(new Dimension(image.getWidth(this),image.getHeight(this)));
          repaint();
        }
        
        /**
         * Method to return the preferred size
         * @return the preferred size of this component
         */
        public Dimension getPreferredScrollableViewportSize()
        {
          return prefSize;
        }
        
        /**
         * Method to return the unit increment for scrolling
         * @param visibleRect the visible rectangle
         * @param orientation vertical or horizontal
         * @param direction neg is up or left and pos is right or down
         * @return the unit increment for arrow clicks
         */
        public int getScrollableUnitIncrement(Rectangle visibleRect, 
                                              int orientation, 
                                              int direction)
        { return 1; }
        
        /**
         * Method to return the block increment for scrolling
         * @param visibleRect the visible rectangle
         * @param orientation vertical or horizontal
         * @param direction neg is up or left and pos is right or down
         * @return the block increment for clicking in scroll area
         */
        public int getScrollableBlockIncrement(Rectangle visibleRect, 
                                               int orientation, 
                                               int direction)
        {
          return 10;
        }
        
        /**
         * Method to check if the viewport width is the source width
         * @return true if viewport and source have same width
         */
        public boolean getScrollableTracksViewportWidth()
        { return false; }
        
        /**
         * Method to check if the viewport height is the source height
         * @return true if viewport and source have same height
         */
        public boolean getScrollableTracksViewportHeight()
        { return false; }
        
        /**
         * Method to handle displaying this object
         * @param g the graphics object for drawing with
         */
        public void paintComponent(Graphics g)
        {
          super.paintComponent(g);

    
          g.drawImage(image,0,0,this);
          
          
        }

        public void loadImage(String fileName) {
          try {
            image = ImageIO.read(ImageEditor.class.getResource(fileName));
            currentlySelectedImageFileName = fileName;
          } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }        
          
          prefSize = new Dimension(image.getWidth(this),image.getHeight(this));
          setPreferredSize(prefSize);
          revalidate();
        }

        @Override
        public void mouseClicked(MouseEvent e) {
          // TODO Auto-generated method stub
          
        }

        @Override
        public void mousePressed(MouseEvent e) {
          // TODO Auto-generated method stub
          
        }

        @Override
        public void mouseReleased(MouseEvent e) {
          // TODO Auto-generated method stub
          
        }

        @Override
        public void mouseEntered(MouseEvent e) {
          // TODO Auto-generated method stub
          
        }

        @Override
        public void mouseExited(MouseEvent e) {
          // TODO Auto-generated method stub
          
        }

        @Override
        public void mouseDragged(MouseEvent e) {
          // TODO Auto-generated method stub
          
        }

        @Override
        public void mouseMoved(MouseEvent e) {
          // TODO Auto-generated method stub
          
        }
      
}

