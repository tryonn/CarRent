package util.resource;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;


/**
 * Classe que contém todas as constantes do programa
 *  
 * @author Amadeus
 * @version 1.0
 */

public class Resource {

    // diretorio de imagens...
    public static final String dirImages = "images/";
    
    // Cores...
    public static final Color backgroundColor = new Color(230, 233, 237);
    
    //Strings
    public static final String sourceName = "ParallelSource"; 
    public static final String targetClassIncompatible = Messages.getString("Message_1");    //$NON-NLS-1$
    public static final String title = Messages.getString("Message_2"); //$NON-NLS-1$
    public static final String hostAddress = Messages.getString("Message_3"); //$NON-NLS-1$
    public static final String localhost = "localhost";
    public static final String group = "cin";
    public static final String userLogin = Messages.getString("Message_4"); //$NON-NLS-1$
    public static final String serverNotFound = Messages.getString("Message_5"); //$NON-NLS-1$
    public static final String connectionError = Messages.getString("Message_6"); //$NON-NLS-1$
    public static final String openConnectionServerPlattus = "CSCLPhysic";
    public static final String pendulumHeightNotDefined = Messages.getString("Message_7"); //$NON-NLS-1$
    public static final String columnNameInstant = Messages.getString("Message_8"); //$NON-NLS-1$
    public static final String columnNameTime = Messages.getString("Message_9"); //$NON-NLS-1$
    public static final String parallelPortLibraryName = "parport";
    public static final String ajusteInstructionText1 = Messages.getString("Message_10"); //$NON-NLS-1$
    public static final String ajusteInstructionText2 = Messages.getString("Message_11"); //$NON-NLS-1$
    public static final String medicaoInstructionText1 = Messages.getString("Message_12"); //$NON-NLS-1$
    public static final String medicaoInstructionText2 = Messages.getString("Message_13"); //$NON-NLS-1$
    public static final String medicaoInstructionText3 = Messages.getString("Message_14"); //$NON-NLS-1$
    public static final String medicaoInstructionText4 = Messages.getString("Message_15"); //$NON-NLS-1$
    public static final String montagemInstructionText1 = Messages.getString("Message_16"); //$NON-NLS-1$
    public static final String montagemInstructionText2 = Messages.getString("Message_17"); //$NON-NLS-1$
    public static final String montagemInstructionText3 = Messages.getString("Message_18"); //$NON-NLS-1$
    public static final String userNotInformed = Messages.getString("Message_19"); //$NON-NLS-1$
    public static final String newButtonText = Messages.getString("Message_20"); //$NON-NLS-1$
    public static final String newButtonToolTipText = Messages.getString("Message_21"); //$NON-NLS-1$
    public static final String openButtonText = Messages.getString("Message_22"); //$NON-NLS-1$
    public static final String openButtonToolTipText = Messages.getString("Message_23"); //$NON-NLS-1$
    public static final String saveButtonText = Messages.getString("Message_24"); //$NON-NLS-1$
    public static final String saveButtonToolTipText = Messages.getString("Message_25"); //$NON-NLS-1$
    public static final String previousButtonText = Messages.getString("Message_26"); //$NON-NLS-1$
    public static final String previousButtonToolTipText = Messages.getString("Message_27"); //$NON-NLS-1$
    public static final String nextButtonText = Messages.getString("Message_28"); //$NON-NLS-1$
    public static final String nextButtonToolTipText = Messages.getString("Message_29"); //$NON-NLS-1$
    public static final String waitingText = Messages.getString("Message_30"); //$NON-NLS-1$
    public static final String panelWaitingIdentifier = "waiting";
    public static final String panelTableIdentifier = "table";
    
    // Fonte Instruções...
    public static final Font font = new Font("Trebuchet MS", Font.BOLD, 12);
    public static final Font waitFont = new Font("Trebuchet MS", Font.BOLD, 20);
    
    // Imagens do programa...
    public static final ImageIcon userIcon = new ImageIcon(Resource.class.getResource(dirImages + "userred32.png"));
    public static final ImageIcon userOwnerIcon = new ImageIcon(Resource.class.getResource(dirImages + "usergreen32.png"));
    public static final ImageIcon logo = new ImageIcon(Resource.class.getResource(dirImages + "ctte-logo-pb-mini3.png"));
    public static final ImageIcon montagem = new ImageIcon(Resource.class.getResource(dirImages + "montagem-titulo.png"));
    public static final ImageIcon ajustes = new ImageIcon(Resource.class.getResource(dirImages + "ajustes-titulo.png"));
    public static final ImageIcon medicao = new ImageIcon(Resource.class.getResource(dirImages + "medicao-titulo.png"));
    public static final ImageIcon resultados = new ImageIcon(Resource.class.getResource(dirImages + "resultados-titulo.png"));
    public static final ImageIcon stepID_1 = new ImageIcon(Resource.class.getResource(dirImages + "passo01.png"));
    public static final ImageIcon stepID_2 = new ImageIcon(Resource.class.getResource(dirImages + "passo02.png"));
    public static final ImageIcon stepID_3 = new ImageIcon(Resource.class.getResource(dirImages + "passo03.png"));
    public static final ImageIcon stepID_4 = new ImageIcon(Resource.class.getResource(dirImages + "passo04.png"));
    public static final ImageIcon pendulum = new ImageIcon(Resource.class.getResource(dirImages + "pendulo2.png"));
    public static final ImageIcon openIcon = new ImageIcon(Resource.class.getResource(dirImages + "abrir-off.png"));
    public static final ImageIcon newIcon = new ImageIcon(Resource.class.getResource(dirImages + "novo-off.png"));
    public static final ImageIcon saveIcon = new ImageIcon(Resource.class.getResource(dirImages + "salvar-off.png"));
    public static final ImageIcon parallelGif = new ImageIcon(Resource.class.getResource(dirImages + "paralela.gif"));
    public static final ImageIcon opticalsensorGif = new ImageIcon(Resource.class.getResource(dirImages + "sensor-otico.gif"));
    public static final ImageIcon interfaceBoxGif = new ImageIcon(Resource.class.getResource(dirImages + "caixa-da-interface.gif"));
    public static final ImageIcon connectInterfaceGif = new ImageIcon(Resource.class.getResource(dirImages + "ligar-a-interface.gif"));
    public static final ImageIcon measureHeightGif = new ImageIcon(Resource.class.getResource(dirImages + "medir-altura.gif"));
    public static final ImageIcon buildSensorGif = new ImageIcon(Resource.class.getResource(dirImages + "montar-sensor-1.gif"));
    public static final ImageIcon testSensorGif = new ImageIcon(Resource.class.getResource(dirImages + "testar-sensor-otico.gif"));
    public static final ImageIcon findCenterMass = new ImageIcon(Resource.class.getResource(dirImages + "achar-centro-de-massa.gif"));
    public static final ImageIcon movePendulum = new ImageIcon(Resource.class.getResource(dirImages + "angulo-de-abertura.gif"));
    
    public Resource() {
    }
    
}