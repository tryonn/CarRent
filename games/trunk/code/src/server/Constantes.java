/*
 * constantes.java
 *
 * Created on 31 de Julho de 2006, 20:54
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package server;


/**
 *
 * @author Vinicius
 */
public class Constantes {
    
    public final static int DEFAULT_PORT = 3000;
    public final static int DEFAULT_PORT_GAME = 3001;
    public final static int TIMEOUT_LOBBY = 60;
    
    //Constantes do Protocolo Geral
    public final static int LOBBY = 1;
    public final static int JOGO = 2;
    public final static int SERVIDOR_CLIENTE = 1;
    public final static int CLIENTE_SERVIDOR = 2;

    //Constantes do Protocolo Lobby
    public final static String L_SC_ListarSalas = "001";
    public final static String L_SC_ListarUsers = "002";
    public final static String L_SC_NovaSala = "003";
    public final static String L_SC_FimSala = "004";
    public final static String L_SC_UserEntrouSala = "005";
    public final static String L_SC_UserSaiuSala = "006";
    public final static String L_SC_NovaMsg = "007";
    public final static String L_SC_LogoffUser = "008";
    public final static String L_SC_LoginUser = "009";
    public final static String L_SC_ListarGames = "010";
    public final static String L_SC_TIMEOUT = "011";
    public final static String L_SC_CHATPESSOAL = "012";
    public final static String L_SC_STATUSSALA = "013";
    public final static String L_SC_STATUSUSER = "014";
    public final static String L_SC_JAFEZLOGIN = "015";

    public final static String L_CS_LoginUser = "001";
    public final static String L_CS_EnviaMsg = "002";
    public final static String L_CS_CriaSala = "003";
    public final static String L_CS_SairSala = "004";
    public final static String L_CS_EntrarSala = "005";
    public final static String L_CS_LogoutUser = "006";
    public final static String L_CS_ListarGames = "007";
    public final static String L_CS_TIMEOUT = "008";
    public final static String L_CS_CHATPESSOAL = "009";
    
    //Constantes do Protocolo Jogo Padrão
    public final static String J_JS_UsuarioEntrou = "001";
    public final static String J_JS_UsuarioSaiu = "002";
    public final static String J_JS_MsgBroadcast = "003";
    public final static String J_JS_MsgUnlessMe = "004";

    public final static String J_SJ_ListaUsers = "001";
    public final static String J_SJ_UserEntrou = "002";
    public final static String J_SJ_UsuarioSaiu = "003";
    public final static String J_SJ_Msg = "004";

    public final static char FINALIZADORFLASH = '\u0000';
}
