package br.ufpe.cin.amadeus.junit.suite;
import junit.framework.Test;
import junit.framework.TestSuite;
import br.ufpe.cin.amadeus.junit.unit.human_resources.user.UserRepositoryTest;

/**
 * $Revision: 1.1 $. Helder da Rocha (helder@ibpinet.net). $Date: 2006/06/19 13:54:24 $.
 * Runs all tests.
 */
public class TesteAmadeus {

    public static Test suite() {
        TestSuite testSuite = new TestSuite("Run all tests for Amadeus");
        testSuite.addTest(UserRepositoryTest.suite());
        return testSuite;
    }
}