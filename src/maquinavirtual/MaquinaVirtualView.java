/*
 * MaquinaVirtualView.java
 * 
 * @author Daniel Franco 07295314
 */

package maquinavirtual;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 * The application's main frame.
 */
public class MaquinaVirtualView extends FrameView {
    
    public DefaultTableModel modelPrograma = new DefaultTableModel();
    public DefaultTableModel modelEntrada = new DefaultTableModel();
    public DefaultTableModel modelSaida = new DefaultTableModel();
    public DefaultTableModel modelMemoria = new DefaultTableModel();
    private int op;
    public  int linha; // linha do codigo
    public BufferedReader in = null; //usado para abrir arquivo .obj
    public int s = 0; // stack pointer
    public int [] M = new int[20000];
    

    public MaquinaVirtualView(SingleFrameApplication app) {
        super(app);
       
        initComponents();
        
        normal.setModel(new javax.swing.table.DefaultTableModel(  // setando a JTable
            new Object [][] {

            },
            new String [] {
                "Linha", "Instrução", "Atributo 1", "Atributo 2", "Comentario"
            }
        ));

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = MaquinaVirtualApp.getApplication().getMainFrame();
            aboutBox = new MaquinaVirtualAboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        MaquinaVirtualApp.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelPilha = new javax.swing.JLabel();
        jLabelNormal = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pilha = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        jMenuOpen = new javax.swing.JMenu();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jLabelEntrada = new javax.swing.JLabel();
        jTextFieldEntrada = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        normal = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        normals = new javax.swing.JRadioButton();
        passoapasso = new javax.swing.JRadioButton();
        jButtonExecutar = new javax.swing.JButton();
        jButtonParar = new javax.swing.JButton();
        jLabelSaida = new javax.swing.JLabel();
        jTextFieldSaida = new javax.swing.JTextField();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        buttonGroup4 = new javax.swing.ButtonGroup();

        mainPanel.setName("mainPanel"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 253, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 227, Short.MAX_VALUE)
        );

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(maquinavirtual.MaquinaVirtualApp.class).getContext().getResourceMap(MaquinaVirtualView.class);
        jLabelPilha.setText(resourceMap.getString("jLabelPilha.text")); // NOI18N
        jLabelPilha.setName("jLabelPilha"); // NOI18N

        jLabelNormal.setText(resourceMap.getString("jLabelNormal.text")); // NOI18N
        jLabelNormal.setName("jLabelNormal"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        pilha.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        pilha.setName("pilha"); // NOI18N
        jScrollPane2.setViewportView(pilha);
        pilha.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("pilha.columnModel.title0")); // NOI18N
        pilha.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("pilha.columnModel.title1")); // NOI18N
        pilha.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("pilha.columnModel.title2")); // NOI18N
        pilha.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("pilha.columnModel.title3")); // NOI18N

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jLabelNormal)
                .add(448, 448, 448)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(447, 447, 447)
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jLabelPilha, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(1055, Short.MAX_VALUE))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(183, 183, 183)
                        .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(mainPanelLayout.createSequentialGroup()
                        .add(12, 12, 12)
                        .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabelNormal)
                            .add(jLabelPilha, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(18, 18, 18)
                        .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 563, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(9, 9, 9))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        jMenuOpen.setText(resourceMap.getString("jMenuOpen.text")); // NOI18N
        jMenuOpen.setName("jMenuOpen"); // NOI18N
        jMenuOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuOpenMouseClicked(evt);
            }
        });
        jMenuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuOpenActionPerformed(evt);
            }
        });
        fileMenu.add(jMenuOpen);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(maquinavirtual.MaquinaVirtualApp.class).getContext().getActionMap(MaquinaVirtualView.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        jPanel2.setName("jPanel2"); // NOI18N

        jLabelEntrada.setText(resourceMap.getString("jLabelEntrada.text")); // NOI18N
        jLabelEntrada.setName("jLabelEntrada"); // NOI18N

        jTextFieldEntrada.setText(resourceMap.getString("jTextFieldEntrada.text")); // NOI18N
        jTextFieldEntrada.setName("jTextFieldEntrada"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        normal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Linha", "Atributo 1", "Atributo 2", "Comentario"
            }
        ));
        normal.setName("normal"); // NOI18N
        jScrollPane1.setViewportView(normal);
        normal.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("normal.columnModel.title0")); // NOI18N
        normal.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("normal.columnModel.title1")); // NOI18N
        normal.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("normal.columnModel.title2")); // NOI18N
        normal.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("normal.columnModel.title3")); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(12, 12, 12)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabelEntrada, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 238, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jTextFieldEntrada, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 300, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(600, 600, 600)
                .add(jLabelEntrada, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(18, 18, 18)
                .add(jTextFieldEntrada, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 572, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setName("jPanel3"); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel3Layout = new org.jdesktop.layout.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 506, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 904, Short.MAX_VALUE)
        );

        buttonGroup1.add(normals);
        normals.setText(resourceMap.getString("normals.text")); // NOI18N
        normals.setName("normals"); // NOI18N
        normals.setOpaque(true);

        buttonGroup1.add(passoapasso);
        passoapasso.setText(resourceMap.getString("passoapasso.text")); // NOI18N
        passoapasso.setName("passoapasso"); // NOI18N

        jButtonExecutar.setText(resourceMap.getString("jButtonExecutar.text")); // NOI18N
        jButtonExecutar.setName("jButtonExecutar"); // NOI18N
        jButtonExecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExecutarActionPerformed(evt);
            }
        });

        jButtonParar.setText(resourceMap.getString("jButtonParar.text")); // NOI18N
        jButtonParar.setName("jButtonParar"); // NOI18N

        jLabelSaida.setText(resourceMap.getString("jLabelSaida.text")); // NOI18N
        jLabelSaida.setName("jLabelSaida"); // NOI18N

        jTextFieldSaida.setText(resourceMap.getString("jTextFieldSaida.text")); // NOI18N
        jTextFieldSaida.setName("jTextFieldSaida"); // NOI18N

        org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(statusPanelLayout.createSequentialGroup()
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(statusPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(statusMessageLabel))
                    .add(statusPanelLayout.createSequentialGroup()
                        .add(40, 40, 40)
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(147, 147, 147)
                        .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jTextFieldSaida, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 289, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabelSaida))))
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(statusPanelLayout.createSequentialGroup()
                        .add(29, 29, 29)
                        .add(normals)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(passoapasso))
                    .add(statusPanelLayout.createSequentialGroup()
                        .add(20, 20, 20)
                        .add(jButtonExecutar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 109, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButtonParar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(418, 418, 418)
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, statusPanelLayout.createSequentialGroup()
                        .add(127, 127, 127)
                        .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(statusAnimationLabel)
                        .addContainerGap())))
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, statusPanelLayout.createSequentialGroup()
                .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, statusPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                        .add(org.jdesktop.layout.GroupLayout.LEADING, statusPanelLayout.createSequentialGroup()
                            .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(31, 31, 31)
                            .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(statusMessageLabel)
                                .add(statusAnimationLabel)
                                .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(143, 143, 143)
                            .add(jLabelSaida)
                            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                            .add(jTextFieldSaida, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE))
                        .add(org.jdesktop.layout.GroupLayout.LEADING, statusPanelLayout.createSequentialGroup()
                            .addContainerGap()
                            .add(jPanel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(statusPanelLayout.createSequentialGroup()
                        .add(232, 232, 232)
                        .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(normals)
                            .add(passoapasso))
                        .add(18, 18, 18)
                        .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jButtonExecutar)
                            .add(jButtonParar))))
                .add(71, 71, 71))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuOpenActionPerformed

    }//GEN-LAST:event_jMenuOpenActionPerformed

    private void jMenuOpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuOpenMouseClicked
          
          /* Declaracoes de Strings etc.. */
          String path = null; //para sabermos o caminho do arquvo
          String str = null;  // valor da String
          this.linha = 0; //Linha inicial
          s = 0; // stack pointer em zero
          M = new int[20000]; // Pilha
          
          /* Declaracoes a Respeito do tipo de Arquivo */
          
          JFileChooser arquivo = new JFileChooser();
          arquivo.setFileFilter(new FileNameExtensionFilter("*.obj","obj")); // filtro para o tipo de arquivo
          //arquivo.setAcceptAllFileFilterUsed(false); //tirar todas  ass extensoes 
          arquivo.setFileSelectionMode(JFileChooser.FILES_ONLY);  //somente files sao abertos 
          
          /* String de expreção regular para fazer os filtros no arquivo lido */
          String regex = "[ ]*([a-zA-Z0-9]*)[ ]*([a-zA-Z0-9]*)[ ]*([a-zA-Z0-9]*)[ ]*([a-zA-Z0-9]*).*";
          Pattern pattern = Pattern.compile(regex);
          Matcher matcher;
          HashMap rotulos = new HashMap();
          
           /* Models das Tabelas */
          
          modelPrograma = (DefaultTableModel) normal.getModel();
  
          /* Codigo de Abertura do Arquivo e Verificacao das Expressoes Regulares */
          
          if(arquivo.showOpenDialog(arquivo) == JFileChooser.APPROVE_OPTION){
           
            try {
                
                File arq = arquivo.getSelectedFile();
                path = arq.toString();
                in = new BufferedReader(new FileReader(path));
                System.out.printf("Teste "+path);
                
             while (in.ready()) {
                 
                str = in.readLine();    // Leitura de uma linha de cada vez
                matcher = pattern.matcher(str);
                
            if (matcher.find()) {   
                /* Percorre os casamentos para achar o indice do comando */
                for (int indice = 1; indice < matcher.groupCount(); indice++) {
                    /* Verifica se o valor é um comando */
                    if (isCommand(matcher.group(indice))) {
                        /* Caso em que o commando é o segundo valor casado, consequentemente o primeiro valor é o rotulo */
                        if(indice == 2){
                            /* Insere chave = rotulo e valor = linha */
                            rotulos.put(matcher.group(1), linha);
                        }
                        break;
                    }
                }
            }
                 
                linha++;              
           }
             
            } catch (IOException ex) {
                Logger.getLogger(MaquinaVirtualView.class.getName()).log(Level.SEVERE, null, ex);
              }
            
    try {
        
        in = new BufferedReader(new FileReader(path));
        linha = 0;
        while (in.ready()) {
            str = in.readLine();    // Leitura de uma linha de cada vez
            matcher = pattern.matcher(str);
            if (matcher.find()) {   
                /* Percorre os casamentos para achar o indice do comando */
                for (int indice = 1; indice < matcher.groupCount(); indice++) {
                    /* Verifica se o valor é um comando */
                    if (isCommand(matcher.group(indice))) {
                        switch (indice) {
                            case 1: /* Caso em que o comando é o primero valor*/
                                if(isCommandLabel(matcher.group(1)) && rotulos.containsKey(matcher.group(2))){
                                    /* Adiciona na tabela a linha e seus respectivos valores */
                                    System.out.printf("INDICE "+indice);
                                  //  modelPrograma.addRow(new Object[]{linha++, matcher.group(1), rotulos.get(matcher.group(2)), matcher.group(3)});   
                                }
                                else{
                                    System.out.printf(" INDICE ELSE " +indice);
                                    /* Adiciona na tabela a linha e seus respectivos valores */
                                     modelPrograma.addRow(new Object[]{linha++, matcher.group(1), matcher.group(2), matcher.group(3)});
                                     
                                }
                                break;
                            case 2: /* Caso em que o commando é o segundo valor casado, consequentemente o primeiro valor é o rotulo */
                                if(isCommandLabel(matcher.group(1)) && rotulos.containsKey(matcher.group(3))){
                                    /* Adiciona na tabela a linha e seus respectivos valores */
                                    modelPrograma.addRow(new Object[]{linha++, matcher.group(2), rotulos.get(matcher.group(3)), matcher.group(4)});
                                    System.out.printf("case"+indice);
                                }
                                else{
                                    /* Adiciona na tabela a linha e seus respectivos valores */
                                    System.out.printf("case"+indice);
                                    modelPrograma.addRow(new Object[]{linha++, matcher.group(2), matcher.group(3), matcher.group(4)});
                                }
                                break;
                                
                        }
                        break;
                    }
                }
            }
        }
        in.close();
    } catch (IOException e) {
        System.out.println("Erro ao ler o arquivo...");
    }
          
            
          }          
            
          else{   
        //TODO
               return; 
          }
        
    }//GEN-LAST:event_jMenuOpenMouseClicked

    private void jButtonExecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExecutarActionPerformed
        if(!(normals.isSelected() || passoapasso.isSelected())){
            JOptionPane.showMessageDialog(null, "Escolha um Modo de Execução Normal/Passo A Passo", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(normals.isSelected()){
            System.out.println("Preciso colocar o txt la dentro");
        } else if(passoapasso.isSelected()){
            System.out.println("Passo a Passo");
            
        }
}//GEN-LAST:event_jButtonExecutarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.ButtonGroup buttonGroup4;
    private javax.swing.JButton jButtonExecutar;
    private javax.swing.JButton jButtonParar;
    private javax.swing.JLabel jLabelEntrada;
    private javax.swing.JLabel jLabelNormal;
    private javax.swing.JLabel jLabelPilha;
    private javax.swing.JLabel jLabelSaida;
    private javax.swing.JMenu jMenuOpen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextFieldEntrada;
    private javax.swing.JTextField jTextFieldSaida;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JTable normal;
    private javax.swing.JRadioButton normals;
    private javax.swing.JRadioButton passoapasso;
    private javax.swing.JTable pilha;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JSeparator statusPanelSeparator;
    // End of variables declaration//GEN-END:variables
    
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;
    
     private boolean isCommand(String group) {
        String commands[] = {"LDC", "LDV", "ADD", "SUB", "MULT", "DIVI",
        "INV", "AND", "OR", "NEG", "CME", "CMA", "CEQ", "CDIF", "CMEQ", "CMAQ",
        "START", "HLT", "STR", "JMP", "JMPF", "NULL", "RD", "PRN", "ALLOC", "DALLOC",
        "CALL", "RETURN"};
        boolean flag = false;

        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals(group)) {
                flag = true;
            }
        }

        if (flag) {
            return true;
        } else {
            return false;
        }

    }

    private boolean isCommandLabel(String group) {
        String commands[] = {"JMP", "JMPF", "CALL", "RETURN"};
        boolean flag = false;

        for (int i = 0; i < commands.length; i++) {
            if (commands[i].equals(group)) {
                flag = true;
            }
        }

        if (flag) {
            return true;
        } else {
            return false;
        }
    }
}
