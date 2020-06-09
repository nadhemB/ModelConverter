/*
 * Created by JFormDesigner on Wed May 20 14:03:22 BST 2020
 */

package com.frs.supercad.desktop;

import java.awt.event.*;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import com.badlogic.gdx.graphics.g3d.Model;
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

	Game app = new ModelConverter();
	LwjglCanvas canvas;
	public ModelInfo info = ModelConverter.modelInfo;






	public MainFrame(LwjglApplicationConfiguration config) {
		canvas = new LwjglCanvas(app, config);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		initComponents();
		addCanvas();
		this.info.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						displayModelInfo();

					}
				});
			}
		});

		addTextFieldListener();
	}

	private void addTextFieldListener() {
		this.tfTranslZ.getDocument().addDocumentListener(new CustomDocumentListener());
		this.tfTranslY.getDocument().addDocumentListener(new CustomDocumentListener());
		this.tfTranslX.getDocument().addDocumentListener(new CustomDocumentListener());

		this.tfScaleX.getDocument().addDocumentListener(new CustomDocumentListener());
		this.tfScaleY.getDocument().addDocumentListener(new CustomDocumentListener());
		this.tfScaleZ.getDocument().addDocumentListener(new CustomDocumentListener());


	}

	private void addCanvas() {
		canvasP.add(canvas.getCanvas(),BorderLayout.CENTER);
		pack();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

	}

	public  void displayModelInfo(){
		this.tfScaleX.setText(info.getScale().x + "");
		this.tfScaleY.setText(info.getScale().y + "");
		this.tfScaleZ.setText(info.getScale().z + "");

		this.tfTranslX.setText(info.getTranslation().x + "");
		this.tfTranslY.setText(info.getTranslation().y + "");
		this.tfTranslZ.setText(info.getTranslation().z + "");

	}

	private void menuOpenActionPerformed(ActionEvent e) {

			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

			int returnValue = jfc.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				File destination = new File("core/assets/models/"  + selectedFile.getName());
				try {
					IOUtilities.copyFileUsingChannel(selectedFile,destination);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Model model = ModelAsset.instance.load("core/assets/models/" + selectedFile.getName());
				ViewerController.createModelInstance(model);

			}


	}

	public void updateModelInstance(){
	Vector3 scale = new Vector3();
		Vector3 translation = new Vector3();
		try{
			scale.x = Float.parseFloat(this.tfScaleX.getText());
			scale.y = Float.parseFloat(this.tfScaleY.getText());
			scale.z = Float.parseFloat(this.tfScaleZ.getText());

			translation.x = Float.parseFloat(this.tfTranslX.getText());
			translation.y = Float.parseFloat(this.tfTranslY.getText());
			translation.z = Float.parseFloat(this.tfTranslZ.getText());

		}catch(Exception e){
			scale = new Vector3();
			translation = new Vector3();
		}


		ViewerController.instance.transform.setToScaling(scale);
		ViewerController.instance.calculateTransforms();
		ViewerController.instance.transform.translate(translation);
	}




	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - sana
		menuBar1 = new JMenuBar();
		menu1 = new JMenu();
		menuOpen = new JMenuItem();
		southernPanel = new JPanel();
		canvasP = new JPanel();
		controlPanel = new JPanel();
		tabbedPane1 = new JTabbedPane();
		panel1 = new JPanel();
		label9 = new JLabel();
		panel3 = new JPanel();
		label2 = new JLabel();
		tfScaleX = new JTextField();
		label3 = new JLabel();
		tfScaleY = new JTextField();
		label4 = new JLabel();
		tfScaleZ = new JTextField();
		label5 = new JLabel();
		panel4 = new JPanel();
		label6 = new JLabel();
		tfTranslX = new JTextField();
		label7 = new JLabel();
		tfTranslY = new JTextField();
		label8 = new JLabel();
		tfTranslZ = new JTextField();

		//======== this ========
		setMinimumSize(new Dimension(720, 720));
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== menuBar1 ========
		{

			//======== menu1 ========
			{
				menu1.setText("File");

				//---- menuOpen ----
				menuOpen.setText("Open");
				menuOpen.addActionListener(e -> menuOpenActionPerformed(e));
				menu1.add(menuOpen);
			}
			menuBar1.add(menu1);
		}
		setJMenuBar(menuBar1);

		//======== southernPanel ========
		{
			southernPanel.setBackground(new Color(51, 51, 51));
			southernPanel.setMinimumSize(new Dimension(720, 30));
			southernPanel.setPreferredSize(new Dimension(720, 50));
			southernPanel.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(
			0,0,0,0), "JFor\u006dDesi\u0067ner \u0045valu\u0061tion",javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder
			.BOTTOM,new java.awt.Font("Dia\u006cog",java.awt.Font.BOLD,12),java.awt.Color.
			red),southernPanel. getBorder()));southernPanel. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.
			beans.PropertyChangeEvent e){if("bord\u0065r".equals(e.getPropertyName()))throw new RuntimeException();}});
			southernPanel.setLayout(null);

			{
				// compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < southernPanel.getComponentCount(); i++) {
					Rectangle bounds = southernPanel.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = southernPanel.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				southernPanel.setMinimumSize(preferredSize);
				southernPanel.setPreferredSize(preferredSize);
			}
		}
		contentPane.add(southernPanel, BorderLayout.SOUTH);

		//======== canvasP ========
		{
			canvasP.setLayout(new BorderLayout());
		}
		contentPane.add(canvasP, BorderLayout.CENTER);

		//======== controlPanel ========
		{
			controlPanel.setPreferredSize(new Dimension(250, 720));
			controlPanel.setBackground(SystemColor.windowText);
			controlPanel.setLayout(new BorderLayout());

			//======== tabbedPane1 ========
			{

				//======== panel1 ========
				{
					panel1.setBackground(SystemColor.windowText);
					panel1.setLayout(new GridLayout(6, 2, 2, 2));

					//---- label9 ----
					label9.setText("scale");
					label9.setForeground(SystemColor.text);
					panel1.add(label9);

					//======== panel3 ========
					{
						panel3.setBackground(SystemColor.desktop);
						panel3.setLayout(new GridLayout(0, 6, 5, 0));

						//---- label2 ----
						label2.setText("X:");
						label2.setHorizontalAlignment(SwingConstants.CENTER);
						label2.setForeground(SystemColor.text);
						panel3.add(label2);

						//---- tfScaleX ----
						tfScaleX.setForeground(Color.white);
						tfScaleX.setBackground(SystemColor.desktop);
						panel3.add(tfScaleX);

						//---- label3 ----
						label3.setText("Y:");
						label3.setHorizontalAlignment(SwingConstants.CENTER);
						label3.setForeground(Color.white);
						panel3.add(label3);

						//---- tfScaleY ----
						tfScaleY.setBackground(SystemColor.desktop);
						tfScaleY.setForeground(Color.white);
						panel3.add(tfScaleY);

						//---- label4 ----
						label4.setText("Z:");
						label4.setHorizontalAlignment(SwingConstants.CENTER);
						label4.setForeground(Color.white);
						panel3.add(label4);

						//---- tfScaleZ ----
						tfScaleZ.setBackground(Color.black);
						tfScaleZ.setForeground(Color.white);
						panel3.add(tfScaleZ);
					}
					panel1.add(panel3);

					//---- label5 ----
					label5.setText("translation");
					label5.setForeground(SystemColor.text);
					panel1.add(label5);

					//======== panel4 ========
					{
						panel4.setBackground(SystemColor.desktop);
						panel4.setLayout(new GridLayout(0, 6, 5, 0));

						//---- label6 ----
						label6.setText("X:");
						label6.setHorizontalAlignment(SwingConstants.CENTER);
						label6.setForeground(SystemColor.text);
						panel4.add(label6);

						//---- tfTranslX ----
						tfTranslX.setForeground(Color.white);
						tfTranslX.setBackground(SystemColor.desktop);
						panel4.add(tfTranslX);

						//---- label7 ----
						label7.setText("Y:");
						label7.setHorizontalAlignment(SwingConstants.CENTER);
						label7.setForeground(Color.white);
						panel4.add(label7);

						//---- tfTranslY ----
						tfTranslY.setBackground(SystemColor.desktop);
						tfTranslY.setForeground(Color.white);
						panel4.add(tfTranslY);

						//---- label8 ----
						label8.setText("Z:");
						label8.setHorizontalAlignment(SwingConstants.CENTER);
						label8.setForeground(Color.white);
						panel4.add(label8);

						//---- tfTranslZ ----
						tfTranslZ.setBackground(Color.black);
						tfTranslZ.setForeground(Color.white);
						panel4.add(tfTranslZ);
					}
					panel1.add(panel4);
				}
				tabbedPane1.addTab("text", panel1);
			}
			controlPanel.add(tabbedPane1, BorderLayout.NORTH);
		}
		contentPane.add(controlPanel, BorderLayout.EAST);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - sana
	private JMenuBar menuBar1;
	private JMenu menu1;
	private JMenuItem menuOpen;
	private JPanel southernPanel;
	private JPanel canvasP;
	private JPanel controlPanel;
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JLabel label9;
	private JPanel panel3;
	private JLabel label2;
	private JTextField tfScaleX;
	private JLabel label3;
	private JTextField tfScaleY;
	private JLabel label4;
	private JTextField tfScaleZ;
	private JLabel label5;
	private JPanel panel4;
	private JLabel label6;
	private JTextField tfTranslX;
	private JLabel label7;
	private JTextField tfTranslY;
	private JLabel label8;
	private JTextField tfTranslZ;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	class CustomDocumentListener implements DocumentListener{
		@Override
		public void insertUpdate(DocumentEvent e) {
			System.out.println("handling insert  ...");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if(ViewerController.instance != null)
						updateModelInstance();
				}
			});
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			System.out.println("handling remove  ...");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if(ViewerController.instance != null)
						updateModelInstance();
				}
			});


		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			System.out.println("handling change  ...");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					if(ViewerController.instance != null)
						updateModelInstance();
				}
			});
		}
	}

}

