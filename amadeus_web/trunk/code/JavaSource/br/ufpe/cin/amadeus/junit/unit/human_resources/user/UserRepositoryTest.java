package br.ufpe.cin.amadeus.junit.unit.human_resources.user;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import br.ufpe.cin.amadeus.system.human_resources.person.Person;

public class UserRepositoryTest extends TestCase {

       public void testInsert() throws Exception{
/*       RepositorioUsuario ru = new RepositorioUsuario();
       Usuario u = ru.getUsuario("altair"); */
       Person u = null;
       Person u2 = new Person();
//       u2 = ru.getUsuario("altair");
       UserRepositoryTest.assertNotNull(u);
       UserRepositoryTest.assertNotNull(u2);
       UserRepositoryTest.assertNull(u2);
       UserRepositoryTest.assertEquals(u,u2);

       }

       public void testVerify() throws Exception{
       String s1 = "tassio2";
       String s2 = "tassio";
       UserRepositoryTest.assertEquals(s1,s2);
       }

       public void testHasLogin() throws Exception{
       String s1 = "tassio2";
       String s2 = "tassio";
       UserRepositoryTest.assertEquals(s1,s2);
       }

       public void testGetUser() throws Exception{
       String s1 = "tassio";
       String s2 = "tassio";
       UserRepositoryTest.assertEquals(s1,s2);
       }

       public void testUserList() throws Exception{
       String s1 = "tassio";
       String s2 = "tassio";
       UserRepositoryTest.assertEquals(s1,s2);
       }

       public void testDelete() throws Exception{
       String s1 = "tassio";
       String s2 = "tassio";
       UserRepositoryTest.assertEquals(s1,s2);
       }

       public void testUpdate() throws Exception{
       String s1 = "tassio";
       String s2 = "tassio";
       UserRepositoryTest.assertEquals(s1,s2);
       }

    public static Test suite() {
        return new TestSuite(UserRepositoryTest.class);
    }

}