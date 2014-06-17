package edu.upc.od.project;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple RDFCollector.
 */
public class QueryOrchestratorTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public QueryOrchestratorTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( QueryOrchestratorTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testRDFCollector()
    {
        assertTrue( true );
    }
}
