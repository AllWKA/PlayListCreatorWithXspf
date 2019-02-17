/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Models.Manager;
import View.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author AllWLKa
 */
public class MainController implements ActionListener {

    private final Interface i;
    private final MoreOptions mo;
    private Manager mm;
    private List <Integer> posEpisodes;
    private String path;

    @Override
    public void actionPerformed(ActionEvent ae) {

        if (ae.getSource() == i.selec) {

            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnVal = chooser.showOpenDialog(i);
            i.textPath.setText(chooser.getSelectedFile().getAbsolutePath());
            path = chooser.getSelectedFile().getAbsolutePath();
        }
        if (ae.getSource() == i.next) {
            
            if (i.textPath.getText().isEmpty()) {
                
                JOptionPane.showMessageDialog(null, "No has seleccionado ninguna carpeta.");
            } else {
                
                mm = new Manager(new File(i.textPath.getText()));
                mm.mixIt();
            }

        }
        if (ae.getSource() == i.editList) {

            if (i.textPath.getText().isEmpty()) {
                
                JOptionPane.showMessageDialog(null, "No has seleccionado ninguna carpeta.");
            } else {
                
                mo.setBounds(i.getX(),i.getY(),500,390);
                mo.setVisible(true);
                
                mm = new Manager(new File(i.textPath.getText()));
                
                chargeData();
                
                i.setVisible(false);
                path = i.textPath.getText();
            }

        }

        if (ae.getSource() == mo.selectShow) {
            DefaultListModel<String> model = new DefaultListModel<>();
            
            for (int j = 0; j < mm.getShow(mo.selectShow.getSelectedIndex()).size(); j++) {
                
                if (!mm.getShow(mo.selectShow.getSelectedIndex()).get(j).getName().contains(".xml")) {
                    
                    model.addElement(mm.getShow(mo.selectShow.getSelectedIndex()).get(j).getName());
                }
            }
            
            mo.episodeList.setModel(model);
            
            if (posEpisodes.get(mo.selectShow.getSelectedIndex()) == 0) {
                
                mo.episodeList.setSelectedIndex(0);
            } else {
                
                mo.episodeList.setSelectedIndex(posEpisodes.get(mo.selectShow.getSelectedIndex()));
            }
        }
        
        if(ae.getSource() == mo.add){
            
            posEpisodes.add(mo.selectShow.getSelectedIndex(), mo.episodeList.getSelectedIndex());
        }
        if (ae.getSource() == mo.conttinueWithDetails) {
            
            mo.setVisible(false);
            i.setVisible(true);
            
            mm = new Manager(new File(path),posEpisodes);
            mm.mixIt();
        }

    }

    public void chargeData() {
        
        posEpisodes = new ArrayList<Integer>();
        
        for (int j = 0; j < mm.getShows().size(); j++) {
            
            posEpisodes.add(0);
        }
        for (int j = 0; j < mm.getShows().size(); j++) {
            
            mo.selectShow.addItem(mm.getShows().get(j).getName());
        }
    }

    public void startButtons() {
        i.next.addActionListener(this);
        i.selec.addActionListener(this);
        i.editList.addActionListener(this);
        mo.selectShow.addActionListener(this);
        mo.add.addActionListener(this);
        mo.conttinueWithDetails.addActionListener(this);
    }

    public MainController(Interface i) {
        this.i = i;
        this.mo = new MoreOptions();
        mm = new Manager();
        path = "";
        startButtons();
    }

}
