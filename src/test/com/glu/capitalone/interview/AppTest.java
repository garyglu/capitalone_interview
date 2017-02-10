package test.com.glu.capitalone.interview;

import com.glu.capitalone.interview.handler.*;
import junit.framework.*;
import junit.framework.Test;

/**
 * Created by glu on 2/8/2017.
 */
public class AppTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(AppTest.class);
    }

    /**
     * test api connection
     */
    public void testAPIConnection() {
        assertTrue(
            "can't connect to API URL to get API data",
            new TransactionDataHandler(true).handle());
    }
}
