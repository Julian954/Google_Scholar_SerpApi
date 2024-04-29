/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Cliente_Model;
import view.View;

/**
 *
 * @author Usuario
 */
public class Cliente_Controller implements ActionListener{

    private View view;
    private Cliente_Model model;
           
    public Cliente_Controller(View view, Cliente_Model model){
        this.view = view;
        this.model = model;
        this.view.BtnExport.addActionListener(this);
    }
    
    public void iniciar(){
        view.setTitle("Export data from Google Scholar");
        view.setLocationRelativeTo(null);
    }
    
    public void actionPerformed(ActionEvent e){
        model.setApiKey(view.txtApikey.getText());
        model.setCollege(view.txtCollege.getText());
        model.Autor_id();
    }
}
