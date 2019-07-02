/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: LORANDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
/**
 * This panel provides a GUI for the LORAN-C manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class LORANDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;
 
   JTextField loranGRI;
   JTextField loranSignalStatus;
   
   JTextField loranMTOA;
   JTextField loranMStatus;
   JTextField loranMECD;
   JTextField loranMSNR;
   
   JTextField loranS1TD;
   JTextField loranS1Status;
   JTextField loranS1ECD;
   JTextField loranS1SNR;
   
   JTextField loranS2TD;
   JTextField loranS2Status;
   JTextField loranS2ECD;
   JTextField loranS2SNR;
   
   JTextField loranS3TD;
   JTextField loranS3Status;
   JTextField loranS3ECD;
   JTextField loranS3SNR;
   
   JTextField loranS4TD;
   JTextField loranS4Status;
   JTextField loranS4ECD;
   JTextField loranS4SNR;
   
   JTextField loranS5TD;
   JTextField loranS5Status;
   JTextField loranS5ECD;
   JTextField loranS5SNR;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public LORANDataPanel (DataFactory data) {
      dataFactory = data;

      setBorder(BorderFactory.createTitledBorder("LORAN-C related data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));
      
      GridBagConstraints constraints = new GridBagConstraints ();
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());   

      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("GRI:"), constraints);  
      
      constraints.gridy = 0;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      loranGRI = new JTextField(dataFactory.getLoranGRI (), 8);
      loranGRI.setToolTipText ("Data used in GLC.");
      loranGRI.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranGRI (loranGRI.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranGRI, constraints);
      
      constraints.gridy = 0;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.insets = new Insets (2, 3, 2, 3);
      JLabel tdLabel = new JLabel("TOA/TD");
      tdLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      gridPanel.add(tdLabel, constraints);

      constraints.gridy = 1;
      constraints.gridx = 2;
      constraints.insets = new Insets (2, 3, 2, 3);
      JLabel statusLabel = new JLabel("Status");
      statusLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      gridPanel.add(statusLabel, constraints);
      
      constraints.gridy = 1;
      constraints.gridx = 3;
      constraints.insets = new Insets (2, 3, 2, 3);
      JLabel ecdLabel = new JLabel("ECD");
      ecdLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      gridPanel.add(ecdLabel, constraints);

      constraints.gridy = 1;
      constraints.gridx = 4;
      constraints.insets = new Insets (2, 3, 2, 3);
      JLabel snrLabel = new JLabel("SNR");
      snrLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      gridPanel.add(snrLabel, constraints);
      
      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Master:"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      loranMTOA = new JTextField(dataFactory.getLoranTOATD (0), 8);
      loranMTOA.setToolTipText ("Data used in GLC, LCD.");
      loranMTOA.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranTOATD (0, loranMSNR.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranMTOA, constraints);  

      constraints.gridy = 2;
      constraints.gridx = 2;
      constraints.anchor = GridBagConstraints.WEST;
      loranMStatus = new JTextField(dataFactory.getLoranStatus (0), 4);
      loranMStatus.setToolTipText ("Data used in GLC, LCD.");
      loranMStatus.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranStatus (0, loranMStatus.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranMStatus, constraints);  

      constraints.gridy = 2;
      constraints.gridx = 3;
      constraints.anchor = GridBagConstraints.WEST;
      loranMECD = new JTextField(dataFactory.getLoranECD (0), 6);
      loranMECD.setToolTipText ("Data used in GLC, LCD.");
      loranMECD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranECD (0, loranMECD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranMECD, constraints);  

      constraints.gridy = 2;
      constraints.gridx = 4;
      constraints.anchor = GridBagConstraints.WEST;
      loranMSNR = new JTextField(dataFactory.getLoranSNR (0), 6);
      loranMSNR.setToolTipText ("Data used in GLC, LCD.");
      loranMSNR.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranSNR (0, loranMSNR.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranMSNR, constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 5;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 3;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Station 1:"), constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      loranS1TD = new JTextField(dataFactory.getLoranTOATD (1), 8);
      loranS1TD.setToolTipText ("Data used in GLC, LCD.");
      loranS1TD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranTOATD (1, loranS1TD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS1TD, constraints);  

      constraints.gridy = 3;
      constraints.gridx = 2;
      constraints.anchor = GridBagConstraints.WEST;
      loranS1Status = new JTextField(dataFactory.getLoranStatus (1), 4);
      loranS1Status.setToolTipText ("Data used in GLC, LCD.");
      loranS1Status.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranStatus (1, loranS1Status.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS1Status, constraints);  

      constraints.gridy = 3;
      constraints.gridx = 3;
      constraints.anchor = GridBagConstraints.WEST;
      loranS1ECD = new JTextField(dataFactory.getLoranECD (1), 6);
      loranS1ECD.setToolTipText ("Data used in GLC, LCD.");
      loranS1ECD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranECD (1, loranS1ECD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS1ECD, constraints);  

      constraints.gridy = 3;
      constraints.gridx = 4;
      constraints.anchor = GridBagConstraints.WEST;
      loranS1SNR = new JTextField(dataFactory.getLoranSNR (1), 6);
      loranS1SNR.setToolTipText ("Data used in GLC, LCD.");
      loranS1SNR.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranSNR (1, loranS1SNR.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS1SNR, constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 5;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 4;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Station 2:"), constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      loranS2TD = new JTextField(dataFactory.getLoranTOATD (2), 8);
      loranS2TD.setToolTipText ("Data used in GLC, LCD.");
      loranS2TD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranTOATD (2, loranS2TD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS2TD, constraints);  

      constraints.gridy = 4;
      constraints.gridx = 2;
      constraints.anchor = GridBagConstraints.WEST;
      loranS2Status = new JTextField(dataFactory.getLoranStatus (2), 4);
      loranS2Status.setToolTipText ("Data used in GLC, LCD.");
      loranS2Status.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranStatus (2, loranS2Status.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS2Status, constraints);  

      constraints.gridy = 4;
      constraints.gridx = 3;
      constraints.anchor = GridBagConstraints.WEST;
      loranS2ECD = new JTextField(dataFactory.getLoranECD (2), 6);
      loranS2ECD.setToolTipText ("Data used in GLC, LCD.");
      loranS2ECD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranECD (2, loranS2ECD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS2ECD, constraints);  

      constraints.gridy = 4;
      constraints.gridx = 4;
      constraints.anchor = GridBagConstraints.WEST;
      loranS2SNR = new JTextField(dataFactory.getLoranSNR (2), 6);
      loranS2SNR.setToolTipText ("Data used in GLC, LCD.");
      loranS2SNR.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranSNR (2, loranS2SNR.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS2SNR, constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 5;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 5;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Station 3:"), constraints);  
      
      constraints.gridy = 5;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      loranS3TD = new JTextField(dataFactory.getLoranTOATD (3), 8);
      loranS3TD.setToolTipText ("Data used in GLC, LCD.");
      loranS3TD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranTOATD (3, loranS3TD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS3TD, constraints);  

      constraints.gridy = 5;
      constraints.gridx = 2;
      constraints.anchor = GridBagConstraints.WEST;
      loranS3Status = new JTextField(dataFactory.getLoranStatus (3), 4);
      loranS3Status.setToolTipText ("Data used in GLC, LCD.");
      loranS3Status.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranStatus (3, loranS3Status.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS3Status, constraints);  

      constraints.gridy = 5;
      constraints.gridx = 3;
      constraints.anchor = GridBagConstraints.WEST;
      loranS3ECD = new JTextField(dataFactory.getLoranECD (3), 6);
      loranS3ECD.setToolTipText ("Data used in GLC, LCD.");
      loranS3ECD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranECD (3, loranS3ECD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS3ECD, constraints);  

      constraints.gridy = 5;
      constraints.gridx = 4;
      constraints.anchor = GridBagConstraints.WEST;
      loranS3SNR = new JTextField(dataFactory.getLoranSNR (3), 6);
      loranS3SNR.setToolTipText ("Data used in GLC, LCD.");
      loranS3SNR.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranSNR (3, loranS3SNR.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS3SNR, constraints);  
      
      constraints.gridy = 5;
      constraints.gridx = 5;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 6;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Station 4:"), constraints);  
      
      constraints.gridy = 6;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      loranS4TD = new JTextField(dataFactory.getLoranTOATD (4), 8);
      loranS4TD.setToolTipText ("Data used in GLC, LCD.");
      loranS4TD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranTOATD (4, loranS4TD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS4TD, constraints);  

      constraints.gridy = 6;
      constraints.gridx = 2;
      constraints.anchor = GridBagConstraints.WEST;
      loranS4Status = new JTextField(dataFactory.getLoranStatus (4), 4);
      loranS4Status.setToolTipText ("Data used in GLC, LCD.");
      loranS4Status.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranStatus (4, loranS4Status.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS4Status, constraints);  

      constraints.gridy = 6;
      constraints.gridx = 3;
      constraints.anchor = GridBagConstraints.WEST;
      loranS4ECD = new JTextField(dataFactory.getLoranECD (4), 6);
      loranS4ECD.setToolTipText ("Data used in GLC, LCD.");
      loranS4ECD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranECD (4, loranS4ECD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS4ECD, constraints);  

      constraints.gridy = 6;
      constraints.gridx = 4;
      constraints.anchor = GridBagConstraints.WEST;
      loranS4SNR = new JTextField(dataFactory.getLoranSNR (4), 6);
      loranS4SNR.setToolTipText ("Data used in GLC, LCD.");
      loranS4SNR.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranSNR (4, loranS4SNR.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS4SNR, constraints); 
      
      constraints.gridy = 6;
      constraints.gridx = 5;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 7;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Station 5:"), constraints);  
      
      constraints.gridy = 7;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      loranS5TD = new JTextField(dataFactory.getLoranTOATD (5), 8);
      loranS5TD.setToolTipText ("Data used in GLC, LCD.");
      loranS5TD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranTOATD (5, loranS5TD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS5TD, constraints);

      constraints.gridy = 7;
      constraints.gridx = 2;
      constraints.anchor = GridBagConstraints.WEST;
      loranS5Status = new JTextField(dataFactory.getLoranStatus (5), 4);
      loranS5Status.setToolTipText ("Data used in GLC, LCD.");
      loranS5Status.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranStatus (5, loranS5Status.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS5Status, constraints);  

      constraints.gridy = 7;
      constraints.gridx = 3;
      constraints.anchor = GridBagConstraints.WEST;
      loranS5ECD = new JTextField(dataFactory.getLoranECD (5), 6);
      loranS5ECD.setToolTipText ("Data used in GLC, LCD.");
      loranS5ECD.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranECD (5, loranS5ECD.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS5ECD, constraints);  

      constraints.gridy = 7; 
      constraints.gridx = 4;
      constraints.anchor = GridBagConstraints.WEST;
      loranS5SNR = new JTextField(dataFactory.getLoranSNR (5), 6);
      loranS5SNR.setToolTipText ("Data used in GLC, LCD.");
      loranS5SNR.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setLoranSNR (5, loranS5SNR.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(loranS5SNR, constraints); 
      
      constraints.gridy = 7;
      constraints.gridx = 5;
      gridPanel.add(new JLabel("*"), constraints);
           
      constraints.gridy = 8;
      constraints.gridx = 5;
      gridPanel.add(new JLabel("* - used in simulated data"), constraints);
      
      gridPanel.setMaximumSize (gridPanel.getPreferredSize ());
      gridPanel.setAlignmentY (JComponent.TOP_ALIGNMENT);
      add(gridPanel);
   }
}
/*
 * Version history:
 *
 * 1.3.0.1 - added astericks to manual values used in simulated data.
 */