package playlistcreatorwithxspf;

import View.Interface;
import controller.MainController;

/**
 *
 * @author AllWLKa
 */
public class PlayListCreatorWithXspf {
    
    public static void main(String[] args) {
        
        Interface view = new Interface();
        MainController mc = new MainController(view);
        view.setBounds(500, 500, 700, 150);
        view.setVisible(true);
    }
    
}
