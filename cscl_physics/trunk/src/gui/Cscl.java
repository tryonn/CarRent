package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.theme.SubstanceLightAquaTheme;

import exception.ActionCreateException;

/**
 * Classe que inicializa a classe principal da aplicação de Ensino Colaborativo
 * de Física Experimental à Distância e centraliza o frame
 * 
 * @author amadeus
 * @version 1.0
 */
public class Cscl {


    boolean packFrame = false;
   
    /**
     * Construtor
     * 
     * @throws ActionCreateException Exceção lançada quando algo inesperado 
	 * ocorre durante a execução da ação
     */
    public Cscl() throws ActionCreateException {
    	MainFrame frame = new MainFrame();

        // Valida o frame... tamanho
        if (packFrame) {
            frame.pack();
        }
        else {
            frame.validate();
        }

        // Centralizando na tela
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();

        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }

        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    /**
     * Método que inicializa a aplicação
     * 
     * @param args Argumento de parâmetro que pode ou não ser utilizado
     * @throws ActionCreateException Exceção lançada quando algo inesperado 
	 * ocorre durante a criação de uma ação 
     */
    public static void main(String[] args) throws ActionCreateException {
        try {
         //   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            UIManager.setLookAndFeel(new SubstanceLookAndFeel());
            SubstanceLookAndFeel.setCurrentTheme(new SubstanceLightAquaTheme());
            SubstanceLookAndFeel.setCurrentWatermark("");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        new Cscl();
    }
}