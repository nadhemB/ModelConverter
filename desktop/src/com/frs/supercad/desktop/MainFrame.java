/*
 * Created by JFormDesigner on Wed May 20 14:03:22 BST 2020
 */

package com.frs.supercad.desktop;

import java.awt.event.*;
import javax.swing.table.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.bulenkov.darcula.DarculaLaf;
import com.frs.supercad.ModelConverter;
import com.frs.supercad.assets.ModelAsset;
import com.frs.supercad.modelviewer.ModelInfo;
import java.awt.*;
import java.io.*;
import java.util.UUID;
import java.util.Vector;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;

import com.frs.supercad.modelviewer.ViewerController;
import org.jdesktop.beansbinding.*;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;


/**
 * @author sana
 */
public class MainFrame extends JFrame {


    private ModelInfo info;
    private float rotationVectorX ,rotationVectorY, rotationVectorZ, rotationDegree;
    private float translationX ,translationY, translationZ;


    LwjglCanvas canvas;

	public MainFrame(LwjglApplicationConfiguration config) {
        this.info = new ModelInfo();
        this.info.setName("model alpha");
		canvas = new LwjglCanvas(ModelConverter.instance, config);

		try {
			UIManager.setLookAndFeel(DarculaLaf.class.getName());
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
		displayTransform(null);
        addCanvas();
		pack();
	}


	private void addCanvas() {
		pCanvas.add(canvas.getCanvas(),BorderLayout.CENTER);
		pack();
		setVisible(true);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

	}
	private void displayTransform(Matrix4 trans){
	    if(trans == null)
	        trans = new Matrix4();
	    float[] values = trans.getValues();
        TableModel model = this.tTransform.getModel();
        model.setValueAt(values[Matrix4.M00],0,0);    model.setValueAt(values[Matrix4.M10],1,0);
        model.setValueAt(values[Matrix4.M01],0,1);    model.setValueAt(values[Matrix4.M11],1,1);
        model.setValueAt(values[Matrix4.M02],0,2);    model.setValueAt(values[Matrix4.M12],1,2);
        model.setValueAt(values[Matrix4.M03],0,3);    model.setValueAt(values[Matrix4.M13],1,3);

        model.setValueAt(values[Matrix4.M20],2,0);    model.setValueAt(values[Matrix4.M30],3,0);
        model.setValueAt(values[Matrix4.M21],2,1);    model.setValueAt(values[Matrix4.M31],3,1);
        model.setValueAt(values[Matrix4.M22],2,2);    model.setValueAt(values[Matrix4.M32],3,2);
        model.setValueAt(values[Matrix4.M23],2,3);    model.setValueAt(values[Matrix4.M33],3,3);
    }

	//actions
	private void menuOpenActionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			int returnValue = jfc.showOpenDialog(null);

			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				ModelAsset.instance.load(selectedFile.getAbsolutePath());
				ModelConverter.instance.setModelPath(selectedFile.getAbsolutePath());
				getInitialInfo();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						miExport.setEnabled(true);
						repaint();
					}
				});
			}
	}
	public void getInitialInfo(){
	    if(ModelConverter.instance.getModelInstance() != null){
            Vector3 dimension = ViewerController.controller.getModelDimension();
            this.info.setWidth(dimension.x);
            this.info.setHeight(dimension.y);
            this.info.setDepth(dimension.z);
            this.tfWidth.setText(this.info.getWidth() + "");
            this.tfHeight.setText(this.info.getHeight() + "");
            this.tfDepth.setText(this.info.getDepth() + "");
        }
    }

	private void miExportActionPerformed(ActionEvent e) {

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			ModelConverter.instance.export(selectedFile.getAbsolutePath(),info);

		}

	}

	//getters and setters
    public ModelInfo getInfo() {
        return info;
    }

    public float getRotationVectorX() {
        return rotationVectorX;
    }

    public float getRotationVectorY() {
        return rotationVectorY;
    }

    public float getRotationVectorZ() {
        return rotationVectorZ;
    }

    public float getRotationDegree() {
        return rotationDegree;
    }

    public void setRotationVectorX(float rotationVectorX) {
        this.rotationVectorX = rotationVectorX;
    }

    public void setRotationVectorY(float rotationVectorY) {
        this.rotationVectorY = rotationVectorY;
    }

    public void setRotationVectorZ(float rotationVectorZ) {
        this.rotationVectorZ = rotationVectorZ;
    }

    public void setRotationDegree(float rotationDegree) {
        this.rotationDegree = rotationDegree;
    }

    public float getTranslationX() {
        return translationX;
    }

    public void setTranslationX(float translationX) {
        this.translationX = translationX;
    }

    public float getTranslationY() {
        return translationY;
    }

    public void setTranslationY(float translationY) {
        this.translationY = translationY;
    }

    public float getTranslationZ() {
        return translationZ;
    }

    public void setTranslationZ(float translationZ) {
        this.translationZ = translationZ;
    }

    private void btnGenerateUUIDActionPerformed(ActionEvent e) {
        UUID uuid = UUID.randomUUID();
        this.info.setUuid(uuid);
    }

    private void btnScaleMouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            Vector3 scale =new Vector3(info.getWidth(),info.getHeight(),info.getDepth());
            ViewerController.controller.scale(scale);
        }
    }

    private void btnGenerateUUIDMouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            this.info.generateUUID();
            this.tfUUID.setText(this.info.getUuid() + "");
        }
    }

    private void btnRotateMouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            Vector3 rotationAxis = new Vector3(this.rotationVectorX, this.rotationVectorY, this.rotationVectorZ);
            ViewerController.controller.rotate(rotationAxis, this.rotationDegree);
            
        }
    }

    private void btnTranslateMouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            
            ViewerController.controller.translate(this.translationX,this.translationY,this.translationZ);

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
        tabbedPane1 = new JTabbedPane();
        panel1 = new JPanel();
        panel5 = new JPanel();
        label1 = new JLabel();
        textField1 = new JTextField();
        label2 = new JLabel();
        panel6 = new JPanel();
        tfUUID = new JTextField();
        btnGenerateUUID = new JButton();
        label7 = new JLabel();
        panel7 = new JPanel();
        label8 = new JLabel();
        tfWidth = new JTextField();
        label9 = new JLabel();
        tfHeight = new JTextField();
        label10 = new JLabel();
        tfDepth = new JTextField();
        btnScale = new JButton();
        panel8 = new JPanel();
        panel11 = new JPanel();
        label3 = new JLabel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel9 = new JPanel();
        label4 = new JLabel();
        label5 = new JLabel();
        textField3 = new JTextField();
        textField4 = new JTextField();
        button2 = new JButton();
        panel12 = new JPanel();
        label11 = new JLabel();
        panel13 = new JPanel();
        label12 = new JLabel();
        panel14 = new JPanel();
        label14 = new JLabel();
        textField2 = new JTextField();
        label15 = new JLabel();
        textField5 = new JTextField();
        label16 = new JLabel();
        textField6 = new JTextField();
        label13 = new JLabel();
        textField7 = new JTextField();
        btnRotate = new JButton();
        panel15 = new JPanel();
        label17 = new JLabel();
        panel16 = new JPanel();
        label18 = new JLabel();
        textField8 = new JTextField();
        label19 = new JLabel();
        textField9 = new JTextField();
        label20 = new JLabel();
        textField10 = new JTextField();
        btnTranslate = new JButton();
        panel10 = new JPanel();
        label6 = new JLabel();
        scrollPane2 = new JScrollPane();
        tTransform = new JTable();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();

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
            pCanvas.setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new
            javax . swing. border .EmptyBorder ( 0, 0 ,0 , 0) ,  "JFor\u006dDesi\u0067ner \u0045valu\u0061tion" , javax
            . swing .border . TitledBorder. CENTER ,javax . swing. border .TitledBorder . BOTTOM, new java
            . awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,java . awt
            . Color .red ) ,pCanvas. getBorder () ) ); pCanvas. addPropertyChangeListener( new java. beans .
            PropertyChangeListener ( ){ @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "bord\u0065r" .
            equals ( e. getPropertyName () ) )throw new RuntimeException( ) ;} } );
            pCanvas.setLayout(new BorderLayout());
        }
        contentPane.add(pCanvas, BorderLayout.CENTER);

        //======== pControl ========
        {
            pControl.setPreferredSize(new Dimension(320, 720));
            pControl.setLayout(new BorderLayout());

            //======== tabbedPane1 ========
            {

                //======== panel1 ========
                {
                    panel1.setLayout(new BorderLayout());

                    //======== panel5 ========
                    {
                        panel5.setLayout(new GridBagLayout());
                        ((GridBagLayout)panel5.getLayout()).columnWidths = new int[] {0, 0, 0};
                        ((GridBagLayout)panel5.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                        ((GridBagLayout)panel5.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                        ((GridBagLayout)panel5.getLayout()).rowWeights = new double[] {1.0, 1.0, 0.0, 1.0E-4};

                        //---- label1 ----
                        label1.setText("Name:");
                        label1.setPreferredSize(new Dimension(20, 16));
                        panel5.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                        panel5.add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- label2 ----
                        label2.setText("UUID");
                        label2.setPreferredSize(new Dimension(20, 16));
                        panel5.add(label2, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //======== panel6 ========
                        {
                            panel6.setLayout(new GridBagLayout());
                            ((GridBagLayout)panel6.getLayout()).columnWidths = new int[] {0, 0, 0};
                            ((GridBagLayout)panel6.getLayout()).rowHeights = new int[] {0, 0};
                            ((GridBagLayout)panel6.getLayout()).columnWeights = new double[] {1.0, 1.0, 1.0E-4};
                            ((GridBagLayout)panel6.getLayout()).rowWeights = new double[] {1.0, 1.0E-4};

                            //---- tfUUID ----
                            tfUUID.setPreferredSize(new Dimension(100, 30));
                            panel6.add(tfUUID, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));

                            //---- btnGenerateUUID ----
                            btnGenerateUUID.setText("generate");
                            btnGenerateUUID.setPreferredSize(new Dimension(25, 30));
                            btnGenerateUUID.addActionListener(e -> btnGenerateUUIDActionPerformed(e));
                            btnGenerateUUID.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    btnGenerateUUIDMouseClicked(e);
                                }
                            });
                            panel6.add(btnGenerateUUID, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                        }
                        panel5.add(panel6, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- label7 ----
                        label7.setText("Dimension");
                        label7.setPreferredSize(new Dimension(20, 25));
                        panel5.add(label7, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //======== panel7 ========
                        {
                            panel7.setLayout(new GridBagLayout());
                            ((GridBagLayout)panel7.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
                            ((GridBagLayout)panel7.getLayout()).rowHeights = new int[] {0, 0};
                            ((GridBagLayout)panel7.getLayout()).columnWeights = new double[] {0.0, 0.0, 1.0, 0.0, 1.0, 0.0, 0.0, 1.0E-4};
                            ((GridBagLayout)panel7.getLayout()).rowWeights = new double[] {0.0, 1.0E-4};

                            //---- label8 ----
                            label8.setText("W:");
                            panel7.add(label8, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- tfWidth ----
                            tfWidth.setPreferredSize(new Dimension(50, 30));
                            panel7.add(tfWidth, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- label9 ----
                            label9.setText("H");
                            panel7.add(label9, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- tfHeight ----
                            tfHeight.setPreferredSize(new Dimension(50, 30));
                            panel7.add(tfHeight, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- label10 ----
                            label10.setText("D");
                            panel7.add(label10, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- tfDepth ----
                            tfDepth.setPreferredSize(new Dimension(50, 30));
                            panel7.add(tfDepth, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- btnScale ----
                            btnScale.setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));
                            btnScale.setPreferredSize(new Dimension(24, 30));
                            btnScale.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    btnScaleMouseClicked(e);
                                }
                            });
                            panel7.add(btnScale, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.NONE,
                                new Insets(0, 0, 0, 0), 0, 0));
                        }
                        panel5.add(panel7, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                    }
                    panel1.add(panel5, BorderLayout.NORTH);

                    //======== panel8 ========
                    {
                        panel8.setPreferredSize(new Dimension(452, 250));
                        panel8.setLayout(new BorderLayout(5, 5));

                        //======== panel11 ========
                        {
                            panel11.setPreferredSize(new Dimension(452, 200));
                            panel11.setLayout(new BorderLayout());

                            //---- label3 ----
                            label3.setText("Properties");
                            label3.setPreferredSize(new Dimension(53, 25));
                            panel11.add(label3, BorderLayout.NORTH);

                            //======== scrollPane1 ========
                            {
                                scrollPane1.setPreferredSize(new Dimension(452, 200));

                                //---- table1 ----
                                table1.setModel(new DefaultTableModel(
                                    new Object[][] {
                                        {null, null},
                                    },
                                    new String[] {
                                        "Name", "Value"
                                    }
                                ) {
                                    Class<?>[] columnTypes = new Class<?>[] {
                                        String.class, Object.class
                                    };
                                    @Override
                                    public Class<?> getColumnClass(int columnIndex) {
                                        return columnTypes[columnIndex];
                                    }
                                });
                                table1.setRowHeight(25);
                                scrollPane1.setViewportView(table1);
                            }
                            panel11.add(scrollPane1, BorderLayout.CENTER);

                            //======== panel9 ========
                            {
                                panel9.setLayout(new GridBagLayout());
                                ((GridBagLayout)panel9.getLayout()).columnWidths = new int[] {0, 0, 0, 0};
                                ((GridBagLayout)panel9.getLayout()).rowHeights = new int[] {0, 0, 0};
                                ((GridBagLayout)panel9.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};
                                ((GridBagLayout)panel9.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                                //---- label4 ----
                                label4.setText("Name");
                                panel9.add(label4, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 10, 10), 0, 0));

                                //---- label5 ----
                                label5.setText("Value");
                                panel9.add(label5, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 10, 0), 0, 0));

                                //---- textField3 ----
                                textField3.setMinimumSize(new Dimension(75, 30));
                                textField3.setPreferredSize(new Dimension(75, 30));
                                panel9.add(textField3, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 0, 10), 0, 0));

                                //---- textField4 ----
                                textField4.setPreferredSize(new Dimension(75, 30));
                                panel9.add(textField4, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 0, 10), 0, 0));

                                //---- button2 ----
                                button2.setText("Add");
                                button2.setPreferredSize(new Dimension(60, 30));
                                panel9.add(button2, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 0, 0), 0, 0));
                            }
                            panel11.add(panel9, BorderLayout.SOUTH);
                        }
                        panel8.add(panel11, BorderLayout.NORTH);

                        //======== panel12 ========
                        {
                            panel12.setPreferredSize(new Dimension(426, 100));
                            panel12.setLayout(new BorderLayout());

                            //---- label11 ----
                            label11.setText("rotation:");
                            panel12.add(label11, BorderLayout.NORTH);

                            //======== panel13 ========
                            {
                                panel13.setPreferredSize(new Dimension(426, 80));
                                panel13.setLayout(new GridBagLayout());
                                ((GridBagLayout)panel13.getLayout()).columnWidths = new int[] {0, 0, 0};
                                ((GridBagLayout)panel13.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                                ((GridBagLayout)panel13.getLayout()).columnWeights = new double[] {0.0, 1.0, 1.0E-4};
                                ((GridBagLayout)panel13.getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 1.0E-4};

                                //---- label12 ----
                                label12.setText("axis");
                                panel13.add(label12, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //======== panel14 ========
                                {
                                    panel14.setLayout(new GridLayout(0, 6, 5, 5));

                                    //---- label14 ----
                                    label14.setText("X:");
                                    label14.setPreferredSize(new Dimension(10, 25));
                                    label14.setHorizontalAlignment(SwingConstants.CENTER);
                                    panel14.add(label14);

                                    //---- textField2 ----
                                    textField2.setPreferredSize(new Dimension(60, 30));
                                    panel14.add(textField2);

                                    //---- label15 ----
                                    label15.setText("Y:");
                                    label15.setPreferredSize(new Dimension(10, 25));
                                    label15.setHorizontalAlignment(SwingConstants.CENTER);
                                    panel14.add(label15);

                                    //---- textField5 ----
                                    textField5.setPreferredSize(new Dimension(60, 30));
                                    panel14.add(textField5);

                                    //---- label16 ----
                                    label16.setText("Z");
                                    label16.setPreferredSize(new Dimension(10, 25));
                                    label16.setHorizontalAlignment(SwingConstants.CENTER);
                                    panel14.add(label16);

                                    //---- textField6 ----
                                    textField6.setPreferredSize(new Dimension(60, 30));
                                    panel14.add(textField6);
                                }
                                panel13.add(panel14, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 0), 0, 0));

                                //---- label13 ----
                                label13.setText("degree");
                                panel13.add(label13, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));
                                panel13.add(textField7, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 0), 0, 0));

                                //---- btnRotate ----
                                btnRotate.setText("rotate");
                                btnRotate.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        btnRotateMouseClicked(e);
                                    }
                                });
                                panel13.add(btnRotate, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 0, 0), 0, 0));
                            }
                            panel12.add(panel13, BorderLayout.CENTER);
                        }
                        panel8.add(panel12, BorderLayout.CENTER);

                        //======== panel15 ========
                        {
                            panel15.setLayout(new BorderLayout(5, 5));

                            //---- label17 ----
                            label17.setText("Translation");
                            label17.setPreferredSize(new Dimension(58, 30));
                            panel15.add(label17, BorderLayout.NORTH);

                            //======== panel16 ========
                            {
                                panel16.setLayout(new GridBagLayout());
                                ((GridBagLayout)panel16.getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
                                ((GridBagLayout)panel16.getLayout()).rowHeights = new int[] {0, 0, 0};
                                ((GridBagLayout)panel16.getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
                                ((GridBagLayout)panel16.getLayout()).rowWeights = new double[] {0.0, 0.0, 1.0E-4};

                                //---- label18 ----
                                label18.setText("X:");
                                panel16.add(label18, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));
                                panel16.add(textField8, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- label19 ----
                                label19.setText("Y:");
                                panel16.add(label19, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));
                                panel16.add(textField9, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- label20 ----
                                label20.setText("Z:");
                                panel16.add(label20, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));
                                panel16.add(textField10, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 5, 5), 0, 0));

                                //---- btnTranslate ----
                                btnTranslate.setText("Translate");
                                btnTranslate.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseClicked(MouseEvent e) {
                                        btnTranslateMouseClicked(e);
                                    }
                                });
                                panel16.add(btnTranslate, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0,
                                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                    new Insets(0, 0, 0, 5), 0, 0));
                            }
                            panel15.add(panel16, BorderLayout.CENTER);
                        }
                        panel8.add(panel15, BorderLayout.SOUTH);
                    }
                    panel1.add(panel8, BorderLayout.CENTER);

                    //======== panel10 ========
                    {
                        panel10.setPreferredSize(new Dimension(200, 200));
                        panel10.setLayout(new BorderLayout(0, 10));

                        //---- label6 ----
                        label6.setText("Transform");
                        label6.setPreferredSize(new Dimension(54, 25));
                        panel10.add(label6, BorderLayout.NORTH);

                        //======== scrollPane2 ========
                        {

                            //---- tTransform ----
                            tTransform.setModel(new DefaultTableModel(
                                new Object[][] {
                                    {null, null, null, null},
                                    {null, null, null, null},
                                    {null, null, null, null},
                                    {null, null, null, null},
                                },
                                new String[] {
                                    "X", "Y", "Z", "W"
                                }
                            ) {
                                Class<?>[] columnTypes = new Class<?>[] {
                                    Float.class, Float.class, Float.class, Float.class
                                };
                                @Override
                                public Class<?> getColumnClass(int columnIndex) {
                                    return columnTypes[columnIndex];
                                }
                            });
                            {
                                TableColumnModel cm = tTransform.getColumnModel();
                                cm.getColumn(0).setPreferredWidth(50);
                                cm.getColumn(1).setPreferredWidth(50);
                                cm.getColumn(2).setPreferredWidth(50);
                                cm.getColumn(3).setPreferredWidth(50);
                            }
                            tTransform.setPreferredScrollableViewportSize(new Dimension(450, 200));
                            tTransform.setRowHeight(30);
                            scrollPane2.setViewportView(tTransform);
                        }
                        panel10.add(scrollPane2, BorderLayout.CENTER);
                    }
                    panel1.add(panel10, BorderLayout.SOUTH);
                }
                tabbedPane1.addTab("General", panel1);

                //======== panel2 ========
                {
                    panel2.setLayout(new BorderLayout());
                }
                tabbedPane1.addTab("Meshs", panel2);

                //======== panel3 ========
                {
                    panel3.setLayout(new BorderLayout());
                }
                tabbedPane1.addTab("Shading", panel3);

                //======== panel4 ========
                {
                    panel4.setLayout(new BorderLayout());
                }
                tabbedPane1.addTab("Grid and views", panel4);
            }
            pControl.add(tabbedPane1, BorderLayout.CENTER);
        }
        contentPane.add(pControl, BorderLayout.EAST);
        pack();
        setLocationRelativeTo(getOwner());

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("info.name"),
            textField1, BeanProperty.create("text")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("info.width"),
            tfWidth, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST"), "widthBinding"));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("info.height"),
            tfHeight, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("info.depth"),
            tfDepth, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ,
            this, BeanProperty.create("info.uuid"),
            tfUUID, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("rotationVectorX"),
            textField2, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("rotationVectorY"),
            textField5, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("rotationVectorZ"),
            textField6, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("rotationDegree"),
            textField7, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("translationX"),
            textField8, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("translationY"),
            textField9, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("translationZ"),
            textField10, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.bind();
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
    private JTabbedPane tabbedPane1;
    private JPanel panel1;
    private JPanel panel5;
    private JLabel label1;
    private JTextField textField1;
    private JLabel label2;
    private JPanel panel6;
    private JTextField tfUUID;
    private JButton btnGenerateUUID;
    private JLabel label7;
    private JPanel panel7;
    private JLabel label8;
    private JTextField tfWidth;
    private JLabel label9;
    private JTextField tfHeight;
    private JLabel label10;
    private JTextField tfDepth;
    private JButton btnScale;
    private JPanel panel8;
    private JPanel panel11;
    private JLabel label3;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel9;
    private JLabel label4;
    private JLabel label5;
    private JTextField textField3;
    private JTextField textField4;
    private JButton button2;
    private JPanel panel12;
    private JLabel label11;
    private JPanel panel13;
    private JLabel label12;
    private JPanel panel14;
    private JLabel label14;
    private JTextField textField2;
    private JLabel label15;
    private JTextField textField5;
    private JLabel label16;
    private JTextField textField6;
    private JLabel label13;
    private JTextField textField7;
    private JButton btnRotate;
    private JPanel panel15;
    private JLabel label17;
    private JPanel panel16;
    private JLabel label18;
    private JTextField textField8;
    private JLabel label19;
    private JTextField textField9;
    private JLabel label20;
    private JTextField textField10;
    private JButton btnTranslate;
    private JPanel panel10;
    private JLabel label6;
    private JScrollPane scrollPane2;
    private JTable tTransform;
    private JPanel panel2;
    private JPanel panel3;
    private JPanel panel4;
    private BindingGroup bindingGroup;
	// JFormDesigner - End of variables declaration  //GEN-END:variables


}

