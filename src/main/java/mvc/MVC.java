package mvc;

import controller.Cliente_Controller;
import model.Cliente_Model;
import view.View;

public class MVC {
    public static void main(String[] args){
        Cliente_Model mod = new Cliente_Model();
        View view = new View();
        
        Cliente_Controller ctrl = new Cliente_Controller(view,mod);
        ctrl.iniciar();
        view.setVisible(true);
    }
}
