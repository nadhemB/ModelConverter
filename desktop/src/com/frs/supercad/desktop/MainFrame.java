/*
 * Created by JFormDesigner on Wed May 20 14:03:22 BST 2020
 */

package com.frs.supercad.desktop;

import java.awt.event.*;
import javax.swing.table.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.frs.supercad.ModelConverter;
import com.frs.supercad.assets.ModelAsset;
import com.frs.supercad.modelviewer.ModelInfo;
import com.frs.supercad.modelviewer.ViewerController;
import com.frs.supercad.utilities.IOUtilities;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;

/**
 * @author sana
 */
public class MainFrame extends JFrame {


	LwjglCanvas canvas;
	public ModelInfo info = new ModelInfo();

	public MainFrame(LwjglApplicationConfiguration config) {
		canvas = new LwjglCanvas(ModelConverter.instance, config);


		
		initComponents();
		addCanvas();
		pack();
		this.info.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {


					}
				});
			}
		});

	}

	private void addCanvas() {
		pCanvas.add(canvas.getCanvas(),BorderLayout.CENTER);
		pack();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

	}



	

	private void menuOpenActionPerformed(ActionEvent e) {

			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

			int returnValue = jfc.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();

				ModelAsset.instance.load(selectedFile.getAbsolutePath());

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						miExport.setEnabled(true);
						repaint();
					}
				});

			}


	}

	

	private void miExportActionPerformed(ActionEvent e) {

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			//ModelConverter.adjustedModel.export(selectedFile.getAbsolutePath());

		}

	}


	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - sana
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        menuOpen = new JMenuItem();
        miExport = new JMenuItem();
        menuItem3 = new JMenuItem();
        menuItem1 = new JMenuItem();
        pCanvas = new JPanel();
        pControl = new JPanel();

        //======== this ========
        setMinimumSize(new Dimension(720, 720));
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {
            menuBar1.setMaximumSize(new Dimension(200, 200));
            menuBar1.setPreferredSize(new Dimension(72, 30));

            //======== menu1 ========
            {
                menu1.setText("File");

                //---- menuOpen ----
                menuOpen.setText("Open");
                menuOpen.addActionListener(e -> menuOpenActionPerformed(e));
                menu1.add(menuOpen);

                //---- miExport ----
                miExport.setText("Export");
                miExport.setEnabled(false);
                miExport.addActionListener(e -> miExportActionPerformed(e));
                menu1.add(miExport);

                //---- menuItem3 ----
                menuItem3.setText("Exit");
                menu1.add(menuItem3);
            }
            menuBar1.add(menu1);

            //---- menuItem1 ----
            menuItem1.setText("Edit");
            menuBar1.add(menuItem1);
        }
        setJMenuBar(menuBar1);

        //======== pCanvas ========
        {
            pCanvas.setBorder (new javax. swing. border. CompoundBorder( new javax .swing .border .TitledBorder (new javax. swing. border
            . EmptyBorder( 0, 0, 0, 0) , "JF\u006frmDesi\u0067ner Ev\u0061luatio\u006e", javax. swing. border. TitledBorder. CENTER, javax
            . swing. border. TitledBorder. BOTTOM, new java .awt .Font ("Dialo\u0067" ,java .awt .Font .BOLD ,
            12 ), java. awt. Color. red) ,pCanvas. getBorder( )) ); pCanvas. addPropertyChangeListener (new java. beans
            . PropertyChangeListener( ){ @Override public void propertyChange (java .beans .PropertyChangeEvent e) {if ("borde\u0072" .equals (e .
            getPropertyName () )) throw new RuntimeException( ); }} );
            pCanvas.setLayout(new BorderLayout());
        }
        contentPane.add(pCanvas, BorderLayout.CENTER);

        //======== pControl ========
        {
            pControl.setPreferredSize(new Dimension(250, 720));
            pControl.setLayout(null);
        }
        contentPane.add(pControl, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - sana
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem menuOpen;
    private JMenuItem miExport;
    private JMenuItem menuItem3;
    private JMenuItem menuItem1;
    private JPanel pCanvas;
    private JPanel pControl;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	class CustomDocumentListener implements DocumentListener{

		private JTextField source;

		public CustomDocumentListener(JTextField source) {
			this.source = source;
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			System.out.println("handling insert  ...");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					/*if(ModelConverter.adjustedModel.getInstance() != null)
							updateModelInstance();*/
				}
			});
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			System.out.println("handling remove  ...");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					/*if(ModelConverter.adjustedModel.instance != null)
							updateModelInstance();*/
				}
			});


		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			System.out.println("handling change  ...");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					/*if(ModelConverter.adjustedModel.instance != null)
						updateModelInstance();*/
				}
			});
		}
	}

}

