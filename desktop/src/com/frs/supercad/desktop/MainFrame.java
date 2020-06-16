/*
 * Created by JFormDesigner on Wed May 20 14:03:22 BST 2020
 */

package com.frs.supercad.desktop;

import java.awt.event.*;
import javax.swing.table.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglCanvas;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.bulenkov.darcula.DarculaLaf;
import com.frs.supercad.ModelConverter;
import com.frs.supercad.assets.ModelAsset;
import com.frs.supercad.modelviewer.ModelInfo;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.io.*;
import java.util.UUID;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;

import com.frs.supercad.modelviewer.ViewerController;
import org.jdesktop.beansbinding.*;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;


/**
 * @author sana
 */
public class MainFrame extends JFrame {


    private ModelInfo info;

	LwjglCanvas canvas;

	public MainFrame(LwjglApplicationConfiguration config) {

        this.info = new ModelInfo();
        this.info.setName("model alpha");
		canvas = new LwjglCanvas(ModelConverter.instance, config);

		UIManager.LookAndFeelInfo[] infos = UIManager.getInstalledLookAndFeels();
		for(UIManager.LookAndFeelInfo info:infos){
			System.out.println(info.getClassName());
		}
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

	//getters
    public ModelInfo getInfo() {
        return info;
    }


    private void btnGenerateUUIDActionPerformed(ActionEvent e) {
        UUID uuid = UUID.randomUUID();
        this.info.setUuid(uuid);
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
        textField2 = new JTextField();
        btnGenerateUUID = new JButton();
        label7 = new JLabel();
        panel7 = new JPanel();
        label8 = new JLabel();
        textField5 = new JTextField();
        label9 = new JLabel();
        textField6 = new JTextField();
        label10 = new JLabel();
        textField7 = new JTextField();
        button3 = new JButton();
        panel8 = new JPanel();
        label3 = new JLabel();
        scrollPane1 = new JScrollPane();
        table1 = new JTable();
        panel9 = new JPanel();
        label4 = new JLabel();
        label5 = new JLabel();
        textField3 = new JTextField();
        textField4 = new JTextField();
        button2 = new JButton();
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
            pCanvas.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(
            0,0,0,0), "JF\u006frmDes\u0069gner \u0045valua\u0074ion",javax.swing.border.TitledBorder.CENTER,javax.swing.border.TitledBorder
            .BOTTOM,new java.awt.Font("D\u0069alog",java.awt.Font.BOLD,12),java.awt.Color.
            red),pCanvas. getBorder()));pCanvas. addPropertyChangeListener(new java.beans.PropertyChangeListener(){@Override public void propertyChange(java.
            beans.PropertyChangeEvent e){if("\u0062order".equals(e.getPropertyName()))throw new RuntimeException();}});
            pCanvas.setLayout(new BorderLayout());
        }
        contentPane.add(pCanvas, BorderLayout.CENTER);

        //======== pControl ========
        {
            pControl.setPreferredSize(new Dimension(310, 720));
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
                        panel5.add(label1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));
                        panel5.add(textField1, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- label2 ----
                        label2.setText("UUID");
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

                            //---- textField2 ----
                            textField2.setPreferredSize(new Dimension(80, 30));
                            panel6.add(textField2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));

                            //---- btnGenerateUUID ----
                            btnGenerateUUID.setText("generate");
                            btnGenerateUUID.setPreferredSize(new Dimension(25, 30));
                            btnGenerateUUID.addActionListener(e -> btnGenerateUUIDActionPerformed(e));
                            panel6.add(btnGenerateUUID, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 0), 0, 0));
                        }
                        panel5.add(panel6, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                            new Insets(0, 0, 0, 0), 0, 0));

                        //---- label7 ----
                        label7.setText("Dimension");
                        label7.setPreferredSize(new Dimension(57, 25));
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

                            //---- textField5 ----
                            textField5.setPreferredSize(new Dimension(50, 30));
                            panel7.add(textField5, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- label9 ----
                            label9.setText("H");
                            panel7.add(label9, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- textField6 ----
                            textField6.setPreferredSize(new Dimension(50, 30));
                            panel7.add(textField6, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- label10 ----
                            label10.setText("D");
                            panel7.add(label10, new GridBagConstraints(4, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- textField7 ----
                            textField7.setPreferredSize(new Dimension(50, 30));
                            panel7.add(textField7, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0,
                                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL,
                                new Insets(0, 0, 0, 2), 0, 0));

                            //---- button3 ----
                            button3.setIcon(UIManager.getIcon("FileView.floppyDriveIcon"));
                            button3.setPreferredSize(new Dimension(24, 30));
                            panel7.add(button3, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0,
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

                        //---- label3 ----
                        label3.setText("Properties");
                        label3.setPreferredSize(new Dimension(53, 25));
                        panel8.add(label3, BorderLayout.NORTH);

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
                        panel8.add(scrollPane1, BorderLayout.CENTER);

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
                        panel8.add(panel9, BorderLayout.SOUTH);
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
            textField5, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("info.height"),
            textField6, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("info.depth"),
            textField7, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            this, BeanProperty.create("info.uuid"),
            textField2, BeanProperty.create("text_ON_ACTION_OR_FOCUS_LOST")));
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
    private JTextField textField2;
    private JButton btnGenerateUUID;
    private JLabel label7;
    private JPanel panel7;
    private JLabel label8;
    private JTextField textField5;
    private JLabel label9;
    private JTextField textField6;
    private JLabel label10;
    private JTextField textField7;
    private JButton button3;
    private JPanel panel8;
    private JLabel label3;
    private JScrollPane scrollPane1;
    private JTable table1;
    private JPanel panel9;
    private JLabel label4;
    private JLabel label5;
    private JTextField textField3;
    private JTextField textField4;
    private JButton button2;
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

