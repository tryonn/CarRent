package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static Connection connection;
    private String driver;
    private String url;
    private String login;
    private String senha;    
	
	public Conexao() {
		
	}
    
    private  void carregarPropriedades() {
        this.url = Constantes.URL;
        this.driver = Constantes.DRIVER;
        this.login = Constantes.LOGIN;
        this.senha = Constantes.SENHA;
    }
    
    public Connection getConnection() {
            carregarPropriedades ();

            try {
            	
				Class.forName (this.driver);
	            connection = DriverManager.getConnection (url,login,senha);
	            
			} catch (ClassNotFoundException e) {

				e.printStackTrace();
			} catch (SQLException e) {

				e.printStackTrace();
			}

        return connection;
    }	

}
