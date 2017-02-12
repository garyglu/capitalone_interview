package test.com.glu.capitalone.interview;

import com.glu.capitalone.interview.config.*;
import com.glu.capitalone.interview.handler.*;
import com.glu.capitalone.interview.interfaces.*;
import com.glu.capitalone.interview.parser.*;
import com.glu.capitalone.interview.parser.ApiDataParser;
import junit.framework.*;
import junit.framework.Test;

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
        try {
            new TransactionDataHandler().handle(true, false, false, false);
            assert true;
        } catch (Exception e) {
            assert false;
        }
    }

    /**
     * test Jason parser
     */
    public void testAllTransactionParser() {
        TestHandler handler = getTestHandler();

        //test default setting
        try {
            String result = handler.handle(true, false, false, false);
            assertTrue(
                "testAllTransactionParser default setting failed",
                result.contains("\"2016-10\":{\"spent\":\"$612.85\",\"income\":\"$2227.60\"}"));
            assertTrue(
                "testAllTransactionParser default setting failed",
                result.contains("\"average\":{\"spent\":\"$612.85\",\"income\":\"$2227.60\"}"));
        } catch (Exception e) {
            assertFalse(e.getMessage(), false);
        }

        //ignore donuts
        try {
            String result = handler.handle(true, true, false, false);
            assertTrue(
                "testAllTransactionParser ignore donuts failed",
                result.contains(
                    "\"2016-10\":{\"spent\":\"$602.95\",\"income\":\"$2227.60\"}"));
            assertTrue(
                "testAllTransactionParser ignore donuts failed",
                result.contains(
                    "\"average\":{\"spent\":\"$602.95\",\"income\":\"$2227.60\"}"));
        } catch (Exception e) {
            assertFalse(e.getMessage(), false);
        }

        //projected transaction
        try {
            String result = handler.handle(true, false, true, false);
            assertTrue(
                "testAllTransactionParser projected transaction failed",
                result.contains("\"2017-02\":{\"spent\":\"$1360.96\",\"income\":\"$1708.15\"}"));
            assertTrue(
                "testAllTransactionParser projected transaction failed",
                result.contains("\"average\":{\"spent\":\"$986.91\",\"income\":\"$1967.88\"}"));
        } catch (Exception e) {
            assertFalse(e.getMessage(), false);
        }

        //ignore CC payment
        try {
            String result = handler.handle(true, false, false, true);
            assertTrue(
                "testAllTransactionParser ignore CC payment failed",
                result.contains("\"2016-10\":{\"spent\":\"$93.40\",\"income\":\"$1708.15\"}"));
            assertTrue(
                "testAllTransactionParser ignore CC payment failed",
                result.contains("\"average\":{\"spent\":\"$93.40\",\"income\":\"$1708.15\"}"));
            assertTrue(
                "testAllTransactionParser ignore CC payment failed",
                result.contains("[transactionId:1415042820000; previousTransactionId:; accountId:nonce:comfy-cc/hdhehe; rawMerchant:CREDIT CARD PAYMENT; merchant:Credit Card Payment; isPending:false; transactionTime:2016-10-15T18:58:00.000Z; amount:$519.45 aggregationTime:2014-11-03T10:58:00.000Z; clearDate:2014-11-03T11:27:00.000Z; categorization:Unknown]"));
            assertTrue(
                "testAllTransactionParser ignore CC payment failed",
                result.contains("[transactionId:1415193660000; previousTransactionId:; accountId:nonce:comfy-checking/hdhehe; rawMerchant:CC PAYMENT; merchant:CC Payment; isPending:false; transactionTime:2016-10-15T20:58:00.000Z; amount:$-519.45 aggregationTime:2014-11-03T12:58:00.000Z; clearDate:2014-11-05T05:21:00.000Z; categorization:Unknown]"));
        } catch (Exception e) {
            assertFalse(e.getMessage(), false);
        }
    }

    private TestHandler getTestHandler() {
        TestAllTransactionParser
            testAllTransactionParser = new TestAllTransactionParser(getAllTransactionJasonString());
        TestProjectedTransactionParser
            testProjectedTransactionParser = new TestProjectedTransactionParser(getProjectedTransactionJasonString());
        return new TestHandler(testAllTransactionParser, testProjectedTransactionParser);
    }

    /**
     * test Jason parser
     */
    public void testIgnoreDonutsParser() {

    }

    private String getAllTransactionJasonString() {
        return "{\"error\":\"no-error\",\"transactions\":[" +
            "{\"amount\":-35000,\"is-pending\":false,\"aggregation-time\":1412686740000,\"account-id\":\"nonce:comfy-cc/hdhehe\",\"clear-date\":1412790480000,\"transaction-id\":\"1412790480000\",\"raw-merchant\":\"7-ELEVEN 23853\",\"categorization\":\"Unknown\",\"merchant\":\"7-Eleven 23853\",\"transaction-time\":\"2016-10-07T12:59:00.000Z\"}," +
            "{\"amount\":-800000,\"is-pending\":false,\"aggregation-time\":1414205700000,\"account-id\":\"nonce:comfy-checking/hdhehe\",\"clear-date\":1414410360000,\"transaction-id\":\"1414410360000\",\"raw-merchant\":\"ATM WITHDRAWAL\",\"categorization\":\"Unknown\",\"merchant\":\"ATM Withdrawal\",\"transaction-time\":\"2016-10-08T02:55:00.000Z\"}," +
            "{\"amount\":-99000,\"is-pending\":false,\"aggregation-time\":1412733360000,\"account-id\":\"nonce:comfy-cc/hdhehe\",\"clear-date\":1412845980000,\"transaction-id\":\"1412845980000\",\"raw-merchant\":\"Krispy Kreme Donuts\",\"categorization\":\"Unknown\",\"merchant\":\"Krispy Kreme Donuts\",\"transaction-time\":\"2016-10-13T01:56:00.000Z\"}," +
            "{\"amount\":5194500,\"is-pending\":false,\"aggregation-time\":1415041080000,\"account-id\":\"nonce:comfy-cc/hdhehe\",\"clear-date\":1415042820000,\"transaction-id\":\"1415042820000\",\"raw-merchant\":\"CREDIT CARD PAYMENT\",\"categorization\":\"Unknown\",\"merchant\":\"Credit Card Payment\",\"transaction-time\":\"2016-10-15T18:58:00.000Z\"}," +
            "{\"amount\":-5194500,\"is-pending\":false,\"aggregation-time\":1415048280000,\"account-id\":\"nonce:comfy-checking/hdhehe\",\"clear-date\":1415193660000,\"transaction-id\":\"1415193660000\",\"raw-merchant\":\"CC PAYMENT\",\"categorization\":\"Unknown\",\"merchant\":\"CC Payment\",\"transaction-time\":\"2016-10-15T20:58:00.000Z\"}," +
            "{\"amount\":17081500,\"is-pending\":false,\"aggregation-time\":1414188060000,\"account-id\":\"nonce:comfy-checking/hdhehe\",\"clear-date\":1414304220000,\"transaction-id\":\"1414304220000\",\"raw-merchant\":\"ZENPAYROLL\",\"categorization\":\"Unknown\",\"merchant\":\"Zenpayroll\",\"transaction-time\":\"2016-10-24T22:01:00.000Z\"}]}";
    }

    private String getProjectedTransactionJasonString() {
        return "{\"error\":\"no-error\",\"transactions\":[" +
            "{\"amount\":17081500,\"is-pending\":true,\"payee-name-only-for-testing\":\"ZENPAYROLL\",\"aggregation-time\":189302400000,\"account-id\":\"nonce:comfy-cc/hdhehe\",\"clear-date\":1486844040000,\"memo-only-for-testing\":\"Example Memo\",\"transaction-id\":\"pending:pending-2.9251058176509592\",\"raw-merchant\":\"ZENPAYROLL\",\"categorization\":\"Unknown\",\"merchant\":\"Zenpayroll\",\"transaction-time\":\"2017-02-10T07:00:00.000Z\"}," +
            "{\"amount\":-5860800,\"is-pending\":true,\"payee-name-only-for-testing\":\"AT&T BILL PAYMENT\",\"aggregation-time\":189302400000,\"account-id\":\"nonce:comfy-cc/hdhehe\",\"clear-date\":1486808820000,\"memo-only-for-testing\":\"Example Memo\",\"transaction-id\":\"pending:pending--2.1585336169092058\",\"raw-merchant\":\"AT&T BILL PAYMENT\",\"categorization\":\"Unknown\",\"merchant\":\"At&T Bill Payment\",\"transaction-time\":\"2017-02-10T07:00:00.000Z\"}," +
            "{\"amount\":-5818900,\"is-pending\":true,\"payee-name-only-for-testing\":\"Dummy expenses for day 2\",\"aggregation-time\":189302400000,\"account-id\":\"nonce:comfy-cc/hdhehe\",\"clear-date\":1486743960000,\"memo-only-for-testing\":\"Example Memo\",\"transaction-id\":\"pending:pending-0.021718321441427737\",\"raw-merchant\":\"Dummy expenses for day 2\",\"categorization\":\"Uncategorized\",\"merchant\":\"Dummy Expenses For Day 2\",\"transaction-time\":\"2017-02-10T07:00:00.000Z\"}," +
            "{\"amount\":-1929900,\"is-pending\":true,\"payee-name-only-for-testing\":\"Dummy expenses for day 5\",\"aggregation-time\":189302400000,\"account-id\":\"nonce:comfy-cc/hdhehe\",\"clear-date\":1486712520000,\"memo-only-for-testing\":\"Example Memo\",\"transaction-id\":\"pending:cleared-0.38716576369204175\",\"raw-merchant\":\"Dummy expenses for day 5\",\"categorization\":\"Uncategorized\",\"merchant\":\"Dummy Expenses For Day 5\",\"transaction-time\":\"2017-02-10T07:00:00.000Z\"}]}";
    }

    private class TestHandler extends TransactionDataHandler{
        private final TestAllTransactionParser allTransactionParser;
        private final TestProjectedTransactionParser projectedTransactionParser;


        public TestHandler(TestAllTransactionParser allTransactionParser,
            TestProjectedTransactionParser projectedTransactionParser) {
            this.allTransactionParser = allTransactionParser;
            this.projectedTransactionParser = projectedTransactionParser;
        }

        @Override
        protected Config createNewConfigInstance() {
            return new TestConfig(this.allTransactionParser, this.projectedTransactionParser);
        }
    }

    private class TestAllTransactionParser extends GetAllTransactionsApiDataParser {
        private final String jsonString;

        public TestAllTransactionParser(String jsonString) {
            this.jsonString = jsonString;
        }

        @Override
        public String getApiData() throws Exception {
            return jsonString;
        }
    }

    private class TestProjectedTransactionParser extends GetProjectedTransactionsForMonthParser {
        private final String jsonString;

        public TestProjectedTransactionParser(String jsonString) {
            this.jsonString = jsonString;
        }

        @Override
        public String getApiData() throws Exception {
            return jsonString;
        }
    }

    private class TestConfig extends DefaultConfig {
        private final TestAllTransactionParser allTransactionParser;
        private final TestProjectedTransactionParser projectedTransactionParser;

        public TestConfig(TestAllTransactionParser allTransactionParser,
            TestProjectedTransactionParser projectedTransactionParser) {
            this.allTransactionParser = allTransactionParser;
            this.projectedTransactionParser = projectedTransactionParser;
        }

        @Override
        protected ApiDataParser getDefaultAllTransactionParser() {
            return allTransactionParser != null ? allTransactionParser : super.getDefaultAllTransactionParser();
        }

        @Override
        protected ApiDataParser getDefaultProjectedTransactionParser() {
            return projectedTransactionParser != null ? projectedTransactionParser : super.getDefaultProjectedTransactionParser();
        }
    }
}
