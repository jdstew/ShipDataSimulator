/* Maritime Digital Laboratory (MDL)
 * Copyright (c) 2005 Jeffrey Stewart
 * 
 * Licensed under the Open Software License version 2.1
 * (Re: www.opensource.org)
 *
 * File: MiscDataPanel.java
 * Created: 2005-01-01, 12:01:01
 */
package mdl.gui.manualData;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import mdl.data.*;
import serialComms.sentences.*;
/**
 * This panel provides a GUI for the miscellaneous manual data.
 *
 * @author Jeff Stewart
 * @version 1.0.0.0, 2005-01-01
 */
public class MiscDataPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	final DataFactory dataFactory;

   JTextField ossTrain; // (horizontal), in degrees.
   JTextField ossElevation; // (vertical), in degrees.
   JTextField ossStatusFromSCCS;
   JTextField ossStatusFromDCU;
   
   JTextField freeTextSentence;
   JButton computeChecksumButton;
   JCheckBox carriageReturnBox;
   JCheckBox lineFeedBox;
   
   /**
    * Initializes this manual data control panel.
    *
    * @param data The DataFactory object which contains the manual data values.
    */
   public MiscDataPanel (DataFactory data) {
      dataFactory = data;
      
      setAlignmentY (Component.TOP_ALIGNMENT);
      setBorder(BorderFactory.createTitledBorder("Miscellaneous data"));
      setLayout (new BoxLayout(this, BoxLayout.X_AXIS));     
      
      GridBagConstraints constraints = new GridBagConstraints ();      
      JPanel gridPanel = new JPanel();
      gridPanel.setLayout (new GridBagLayout());
      
      constraints.gridy = 0;
      constraints.gridx = 0;
      constraints.insets = new Insets (2, 3, 2, 3);
      JLabel ossLabel = new JLabel("OSS Related Data");
      ossLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      gridPanel.add(ossLabel, constraints);

      constraints.gridy = 1;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Train angle:"), constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      ossTrain = new JTextField(dataFactory.getOssTrain(), 8);
      ossTrain.setToolTipText ("Data used in PBBG.");
      ossTrain.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setOssTrain (ossTrain.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(ossTrain, constraints);  
      
      constraints.gridy = 1;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 2;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Elevation angle:"), constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      ossElevation = new JTextField(dataFactory.getOssElevation (), 8);
      ossElevation.setToolTipText ("Data used in PBBG.");
      ossElevation.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setOssElevation (ossElevation.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(ossElevation, constraints);  
      
      constraints.gridy = 2;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 3;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Status from SCCS:"), constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      ossStatusFromSCCS = new JTextField(dataFactory.getOssStatusFromSCCS (), 8);
      ossStatusFromSCCS.setToolTipText ("Data used in PBBG.");
      ossStatusFromSCCS.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setOssStatusFromSCCS (ossStatusFromSCCS.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(ossStatusFromSCCS, constraints);  
      
      constraints.gridy = 3;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);

      constraints.gridy = 4;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Status from DCU:"), constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 1;
      constraints.anchor = GridBagConstraints.WEST;
      ossStatusFromDCU = new JTextField(dataFactory.getOssStatusFromDCU (), 8);
      ossStatusFromDCU.setToolTipText ("Data used in PBBG.");
      ossStatusFromDCU.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               dataFactory.setOssStatusFromDCU (ossStatusFromDCU.getText ());
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(ossStatusFromDCU, constraints);  
      
      constraints.gridy = 4;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("*"), constraints);
      
      constraints.gridy = 5;
      constraints.gridx = 2;
      gridPanel.add(new JLabel("* - used in simulated data"), constraints);
      
      constraints.gridy = 6;
      constraints.gridx = 0;
      constraints.insets = new Insets (2, 3, 2, 3);
      JLabel freeTextLabel = new JLabel("Free Text");
      freeTextLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
      gridPanel.add(freeTextLabel, constraints);
      
      constraints.gridy = 6;
      constraints.gridx = 3;
      constraints.anchor = GridBagConstraints.WEST;
      gridPanel.add(new JLabel("Include:"), constraints); 
      
      constraints.gridy = 7;
      constraints.gridx = 0;
      constraints.anchor = GridBagConstraints.EAST;
      gridPanel.add(new JLabel("Text:"), constraints);  
      
      constraints.gridy = 7;
      constraints.gridx = 1;
      constraints.gridwidth = 2;
      constraints.anchor = GridBagConstraints.WEST;
      freeTextSentence = new JTextField(dataFactory.getFreeTextSentence (), 40);
      freeTextSentence.setToolTipText ("Free text sentence data");
      freeTextSentence.addKeyListener (new
         KeyListener () {
            public void keyPressed (KeyEvent e) { }

            public void keyReleased (KeyEvent e) { 
               updateDataFactory ();
            }

            public void keyTyped (KeyEvent e)  { }
         });
      gridPanel.add(freeTextSentence, constraints);  

      constraints.gridy = 7;
      constraints.gridx = 3;
      constraints.gridwidth = 1;
      computeChecksumButton = new JButton("Compute Checksum");
      computeChecksumButton.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
               String freeText = freeTextSentence.getText ();
               if (freeText.length () > 0) {
                  if (freeText.charAt (0) == '$') {
                     freeTextSentence.setText (
                        freeText +
                        '*' + SentenceTools.getChecksum (
                                 freeText.substring (1, freeText.length ())));
                     System.out.println ("'$' found, " + freeText.substring (1, freeText.length ()));
                  }
                  else {
                     freeTextSentence.setText (
                        freeText + '*' + SentenceTools.getChecksum (freeText));
                     System.out.println ("Not found");
                  }
               }
               updateDataFactory ();
            }
         });
      gridPanel.add(computeChecksumButton, constraints);
      
      constraints.gridy = 7;
      constraints.gridx = 4; 
      Box endCharactersBox = new Box(BoxLayout.X_AXIS);
      carriageReturnBox= new JCheckBox ("<CR>", true);
      carriageReturnBox.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
               updateDataFactory ();
            }
         });
      
      lineFeedBox= new JCheckBox ("<LF>", true);
      lineFeedBox.addActionListener (new ActionListener () {
            public void actionPerformed (ActionEvent event) {
               updateDataFactory ();
            }
         });
      
      endCharactersBox.add(carriageReturnBox);
      endCharactersBox.add(lineFeedBox);
      gridPanel.add(endCharactersBox, constraints);
      gridPanel.setMaximumSize (gridPanel.getPreferredSize ());
      gridPanel.setAlignmentY (JComponent.TOP_ALIGNMENT);
      add(gridPanel);
   }

   private void updateDataFactory () {
      String freeText = freeTextSentence.getText ();
      if (freeText.length () > 0) {
         if (carriageReturnBox.isSelected()) {
            freeText += SentenceTools.CR;
         }
         if (lineFeedBox.isSelected ()) {
            freeText += SentenceTools.LF;
         }
         dataFactory.setFreeTextSentence (freeText);
      }
      else {
         dataFactory.setFreeTextSentence ("");
      }
   }
}
/*
 * Version history:
 *
 * 1.3.0.1 - added astericks to manual values used in simulated data.
 */