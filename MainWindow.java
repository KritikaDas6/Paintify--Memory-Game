package com.paintify.app;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

import java.awt.FlowLayout;

import com.paintify.controllers.BrushController;
import com.paintify.controllers.EraserController;
import com.paintify.controllers.FillController;
import com.paintify.controllers.RectController;
import com.paintify.controllers.FillEraserController;
import com.paintify.panels.ColorPuzzle;
import com.paintify.panels.ColorPalettePicker;
import com.paintify.panels.ImageEditor;
import com.paintify.panels.ProgressPanel;
import com.paintify.panels.GamePanel;

import java.awt.event.*;
import java.awt.*;
import java.util.Timer;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Insets;
import javax.imageio.ImageIO;
 
public class MainWindow implements ActionListener{
    private AppConfig config;
    private static MainWindow instance=null;
    private JFrame mainFrame;
    public static boolean RIGHT_TO_LEFT = false;
    private GamePanel gamePanel = null;
    ColorPalettePicker cp = null;
    JPanel toolbar;
    JPanel fill;
    Timer pulse;

    private MainWindow(){
        config=AppConfig.getInstance();
        pulse=new Timer(); // The "pulse" of the game, used to show hints for a cetain amount of time, ect
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("HINT")){
            ColorPuzzle puzzle = (ColorPuzzle)gamePanel.getEditor();
            puzzle.showHint();           
        }
        else if (command.equals("RELOAD")){
            ColorPuzzle ccEditor = (ColorPuzzle)gamePanel.getEditor();
            ccEditor.reload();
        }
        else if(command.equals("MONSTER")){
            ColorPuzzle monster = (ColorPuzzle)gamePanel.getEditor();
            monster.loadImage("/images/monster.png");
            fill.remove(cp);
            cp = new ColorPalettePicker(monster.getReferenceImage());
            fill.add(cp);
            toolbar.revalidate();
            toolbar.repaint();
            // JPanel tools = createToolPanel();
        
        }
        else if(command.equals("MICKEY")){
            ColorPuzzle mickey = (ColorPuzzle)gamePanel.getEditor();
            mickey.loadImage("/images/mickey.png");
            fill.remove(cp);
            cp = new ColorPalettePicker(mickey.getReferenceImage());
            fill.add(cp);
            toolbar.revalidate();
            toolbar.repaint();

        }
        else if(command.equals("FISH")){
            ColorPuzzle fish = (ColorPuzzle)gamePanel.getEditor();
            fish.loadImage("/images/pond.png");
            fill.remove(cp);
            cp = new ColorPalettePicker(fish.getReferenceImage());
            fill.add(cp);
            toolbar.revalidate();
            toolbar.repaint();
        }
        else if(command.equals("AVACADO")){
            ColorPuzzle ava = (ColorPuzzle)gamePanel.getEditor();
            ava.loadImage("/images/avacado.png");
            fill.remove(cp);
            cp = new ColorPalettePicker(ava.getReferenceImage());
            fill.add(cp);
            toolbar.revalidate();
            toolbar.repaint();
        }
        else 
        gamePanel.setController(e.getActionCommand()); 
               
    }    

    private JButton createImageButton(String actionKey, String iconFilePath){
        JButton button;
        ImageIcon bIcon;

        bIcon=new ImageIcon(MainWindow.class.getResource(iconFilePath));
        button = new JButton(bIcon);
        button.setActionCommand(actionKey);
        button.addActionListener(this);

        button.setBackground(Color.WHITE);
        button.setOpaque(false);
        button.setMargin(new Insets(1,1,1,1));
        return button;
    }
     
    private void createLayout(Container mainWindowPane) {

        AppConfig config=AppConfig.getInstance();

        config.setConfig(AppConfig.FILL_COLOR, Color.RED);
        config.setConfig(AppConfig.BRUSH_SIZE,  Integer.valueOf(10));

         
        if (!(mainWindowPane.getLayout() instanceof BorderLayout)) {
            mainWindowPane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }
        if (RIGHT_TO_LEFT) {
            mainWindowPane.setComponentOrientation(
                    java.awt.ComponentOrientation.RIGHT_TO_LEFT);
        }

        JPanel controlPanel = createControlPanel();
        mainWindowPane.add(controlPanel, BorderLayout.PAGE_START);

        gamePanel=createEditorPanel();

        mainWindowPane.add(gamePanel, BorderLayout.CENTER);
        
        // No 2 ////////////////
        JPanel toolbar = createToolPanel();
        mainWindowPane.add(toolbar, BorderLayout.LINE_START);
         
        // JButton button = new JButton("Footer");
        // mainWindowPane.add(button, BorderLayout.PAGE_END);
        ProgressPanel progress = new ProgressPanel((ColorPuzzle)(gamePanel.getEditor()));
        mainWindowPane.add(progress, BorderLayout.PAGE_END);
        // No 4
         
        // JButton button  = new JButton("Canvas Tools");
        // mainWindowPane.add(button, BorderLayout.LINE_END);
        // No 5
    }

    private GamePanel createEditorPanel() {
        GamePanel panel = new GamePanel();
        panel.addBrushController("RECT", new RectController(panel));
        panel.addBrushController("FILL", new FillController(panel));
        panel.addBrushController("ERASER", new EraserController(panel));
        panel.addBrushController("FILLERASER", new FillEraserController(panel)); //Victoria
        panel.addBrushController("BRUSH", new BrushController(panel));        

        panel.setController("FILL");
        return panel;
    }

    private JPanel createToolPanel() {
        toolbar = new JPanel();
        toolbar.setLayout(new BoxLayout(toolbar, BoxLayout.PAGE_AXIS));

            fill =  new JPanel();

            fill.setBorder(BorderFactory.createEtchedBorder());
            fill.setLayout(new BoxLayout(fill,BoxLayout.Y_AXIS));
            ImageEditor editor = gamePanel.getEditor();

                ColorPuzzle cceditor = (ColorPuzzle) editor;


                cp=new ColorPalettePicker(cceditor.getReferenceImage());
                fill.add(cp);

        toolbar.add(fill);

        // Make Brush Size slider part of new panel     
        JPanel brushTools = new JPanel();
        brushTools.setBorder(BorderFactory.createEtchedBorder());   
            JSlider brushSize = new JSlider(5, 50, 10);
            brushSize.addChangeListener(new ChangeListener() {

                @Override
                public void stateChanged(ChangeEvent e) {
                    // TODO Auto-generated method stub
                    JSlider source = (JSlider)e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        int ibrushSize = (int)source.getValue();
                        config.setConfig("brush.size", Integer.valueOf(ibrushSize));
                    }   
                }
                
            });

            brushTools.add(brushSize);            

        toolbar.add(brushTools);
        return toolbar;
    }

    public void runMonsterFile(){

    }

    private JPanel createControlPanel() {
        JPanel controlPanel =new JPanel(); // for fill, hints,
        JPanel chooser = new JPanel(); //for choosing an image
        chooser.setLayout(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        //  Add Logo
        controlPanel.add(new JLabel(new ImageIcon(MainWindow.class.getResource("/images/logo.png"))), JLabel.LEFT_ALIGNMENT);

        // Add a bunch of images you can color
        chooser.add(createImageButton("MONSTER", "/images/buttons/icons8-cute-monster-50.png"));
        chooser.add(createImageButton("MICKEY", "/images/buttons/icons8-mickey-50.png"));
        chooser.add(createImageButton("FISH", "/images/buttons/icons8-fish-50.png"));
        chooser.add(createImageButton("AVACADO", "/images/buttons/icons8-avocado-50.png"));
    // Adding buttons like fill and draw
        controlPanel.add(createImageButton("FILL","/images/buttons/icons8-fill-color-50.png"));
        controlPanel.add(createImageButton("BRUSH","/images/buttons/icons8-paint-brush-50.png"));
        controlPanel.add(createImageButton( "ERASER", "/images/buttons/icons8-erase-50.png"));
        controlPanel.add(createImageButton( "FILLERASER", "/images/buttons/filleraser_2_10.png")); //Victoria
        controlPanel.add(createImageButton( "HINT", "/images/buttons/icons8-one-shot-50.png"));
        controlPanel.add(createImageButton( "RELOAD", "/images/buttons/icons8-refresh-50.png"));
        controlPanel.add(chooser);
        // Adding the Top Drawing Modes/Images to the Main Frame Panel 
        return controlPanel;
    }
     
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private void createAndShowGUI() {
        //Create and set up the window.
        mainFrame = new JFrame("Paintify");

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Set up the content pane.
        createLayout(mainFrame.getContentPane());
        //makeCursor();
        mainFrame.pack();
        mainFrame.setVisible(true);

    }

    public void makeCursor() {
        // mouse = new CustomCursor()
        Toolkit toolkit = Toolkit.getDefaultToolkit();
      //System.out.print("toolkit");
        Image image = toolkit.getImage("/home/runner/Paintify--- ColorMemory/images/mouse2.png");
        Cursor mouse = toolkit.createCustomCursor(image, new Point(0, 0), "mouse");
        toolbar.setCursor(mouse);
      //System.out.print("x" + image + " <- image");
    }
    
    public static MainWindow getInstance(){
        if (instance==null)
        {
            instance=new MainWindow();
        }
        return instance;
    }
     
    public static void mainFunction(String[] args) {
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            // random stuff to set it up
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use bold fonts */
        // UIManager.put("swing.boldMetal", Boolean.FALSE);
         
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow.getInstance().createAndShowGUI();
                // This is where everything gets Started
            }
        });
    }
}