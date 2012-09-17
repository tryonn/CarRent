package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.UIManager;

import org.jvnet.substance.SubstanceLookAndFeel;
import org.jvnet.substance.theme.SubstanceLightAquaTheme;

import exception.ActionCreateException;

/**
 * Classe que inicializa a classe principal da aplica��o de Ensino Colaborativo
 * de F�sica Experimental � Dist�ncia e centraliza o frame
 * 
 * @author amadeus
 * @version 1.0
 */
public class Cscl {


    boolean packFrame = false;
   
    /**
     * Construtor
     * 
     * @throws ActionCreateException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a execu��o da a��o
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
     * M�todo que inicializa a aplica��o
     * 
     * @param args Argumento de par�metro que pode ou n�o ser utilizado
     * @throws ActionCreateException Exce��o lan�ada quando algo inesperado 
	 * ocorre durante a cria��o de uma a��o 
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