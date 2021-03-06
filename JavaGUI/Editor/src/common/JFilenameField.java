/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.event.EventListenerList;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author elvio
 */
public class JFilenameField extends javax.swing.JPanel {

    private FileFilter filter = null;
    private Color OK_CLR, ERR_CLR;
    private String dirPrefKey = "open-dir";
    
    /**
     * Creates new form JFilenameField
     */
    public JFilenameField() {
        initComponents();
        OK_CLR = jTextFieldFilename.getForeground();
        ERR_CLR = Color.RED;
    }
    
    public FileFilter getFilter() {
        return filter;
    }

    public void setFilter(FileFilter filter) {
        this.filter = filter;
        if (jTextFieldFilename.getText().length() > 0)
            validateFilename();
    }
    
    public String getText() {
        return jTextFieldFilename.getText();
    }
    
    public void setText(String filename) {
        jTextFieldFilename.setText(filename);
        validateFilename();
    }

    public File getFilename() {
        return new File(getText());
    }

    public void setFilename(File filename) {
        setText(filename.toString());
    }
    
    private void validateFilename() {
        if (filter != null) {
            boolean ok = filter.accept(getFilename());
            jTextFieldFilename.setForeground(ok ? OK_CLR : ERR_CLR);
        }
    }
    
    private boolean isOpenDialog = true;
    public boolean isIsOpenDialog() { return isOpenDialog; }
    public void setIsOpenDialog(boolean isOpenDialog) { this.isOpenDialog = isOpenDialog; }
    
    // I Listeners registrati a questo oggetto Action
    private EventListenerList myListenerList = new EventListenerList();
    public void addActionListener(ActionListener al) {
        myListenerList.add(ActionListener.class, al);
    }    
    public void removeActionListener(ActionListener al) {
        myListenerList.remove(ActionListener.class, al);
    }    
    public ActionListener[] getActionListeners() {
        return myListenerList.getListeners(ActionListener.class);
    }    
    protected void fireAction(ActionEvent evt) {
        ActionListener[] list = getActionListeners();
        for (ActionListener al : list) {
            al.actionPerformed(evt);
        }
    }

    public void setDirPrefKey(String dirPrefKey) {
        this.dirPrefKey = dirPrefKey;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        resourceFactory = new editor.gui.ResourceFactory();
        jTextFieldFilename = new javax.swing.JTextField();
        jButtonChoose = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        jTextFieldFilename.setColumns(5);
        jTextFieldFilename.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldFilenameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 264;
        gridBagConstraints.weightx = 0.5;
        add(jTextFieldFilename, gridBagConstraints);

        jButtonChoose.setIcon(resourceFactory.getFolder16());
        jButtonChoose.setText("Scegli...");
        jButtonChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChooseActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        add(jButtonChoose, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldFilenameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldFilenameActionPerformed
        validateFilename();
        fireAction(new ActionEvent(this, 0, ""));
    }//GEN-LAST:event_jTextFieldFilenameActionPerformed

    private void jButtonChooseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChooseActionPerformed
        JFileChooser fc = new JFileChooser(getFilename());
        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);
        String curDir = Util.getPreferences().get(dirPrefKey, null);
        fc.setCurrentDirectory(curDir!=null ? new File(curDir) : null);
        int retval;
        if (isIsOpenDialog())
            retval = fc.showOpenDialog(this);
        else
            retval = fc.showSaveDialog(this);
        if (retval != JFileChooser.APPROVE_OPTION) 
            return;
        
        File file = fc.getSelectedFile();
        curDir = fc.getCurrentDirectory().getAbsolutePath();
        Util.getPreferences().put(dirPrefKey, curDir);
        
        // Cambia estensione
        if (fc.getFileFilter() instanceof FileNameExtensionFilter) {
            FileNameExtensionFilter fnef = (FileNameExtensionFilter)fc.getFileFilter();
            String path = file.getName();
            int lastDot = path.lastIndexOf(".");
            if (lastDot != -1)
                path = path.substring(0, lastDot);
            file = new File(path + "." + fnef.getExtensions()[0]);
        }
        
        jTextFieldFilename.setText(file.toString());
        validateFilename();
        fireAction(new ActionEvent(this, 0, ""));
    }//GEN-LAST:event_jButtonChooseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonChoose;
    private javax.swing.JTextField jTextFieldFilename;
    private editor.gui.ResourceFactory resourceFactory;
    // End of variables declaration//GEN-END:variables
}
