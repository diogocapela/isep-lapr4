package rest;

import com.anarsoft.vmlens.concurrent.junit.ConcurrentTestRunner;
import com.anarsoft.vmlens.concurrent.junit.ThreadCount;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(ConcurrentTestRunner.class)
public class HTTPServerTest {
    private static final int THREAD_COUNT = 100;


    //throttling test variables
    private static final Object syncThrottlingObject = new Object();
    private static final int throttlingLowerBound = 5;
    private static final int throttlingUpperBound = 10;
    private HTTPServer serverThrottling = new HTTPServer(8000, throttlingUpperBound, throttlingLowerBound);
    private static int throttlingAcceptCount = 0;
    private static int throttlingRejectCount = 0;


    @Test
    @ThreadCount(THREAD_COUNT)
    public void ensureServerHasThrottlingStopsAcceptingMultiTest() {
        synchronized (syncThrottlingObject) {
            if (serverThrottling.acceptRequest()) {
                throttlingAcceptCount++;
                //System.out.println(String.format("a %d", throttlingAcceptCount));
            } else {
                throttlingRejectCount++;
                //System.out.println(String.format("a %d", throttlingRejectCount));
            }
        }
    }

    //capacity test variables
    private static final Object syncCapacityObject = new Object();
    private static final int capacityLowerBound = 5;
    private static final int capacityUpperBound = 10;
    private HTTPServer serverCapacity = new HTTPServer(8000, capacityLowerBound, capacityUpperBound);
    private static int capacityAcceptCount = 0;
    private static int capacityRejectCount = 0;


    @Test
    @ThreadCount(THREAD_COUNT)
    public void ensureServerStopsAcceptingCapacityMultiTest() {
        synchronized (syncCapacityObject) {
            if (serverCapacity.acceptRequest()) {
                capacityAcceptCount++;
                //System.out.println(String.format("a %d", throttlingAcceptCount));
            } else {
                capacityRejectCount++;
                //System.out.println(String.format("a %d", throttlingRejectCount));
            }
        }
    }

    @After
    public void afterTest() {
        System.out.println("ensureServerHasThrottlingStopsAcceptingTest");
        assertEquals(throttlingAcceptCount, throttlingLowerBound);
        assertEquals(throttlingRejectCount, THREAD_COUNT - throttlingAcceptCount);
        serverThrottling.terminateRequest();
        if (serverThrottling.acceptRequest()) {
            throttlingAcceptCount++;
        } else {
            throttlingRejectCount++;
        }
        assertEquals(throttlingAcceptCount, throttlingLowerBound);
        assertEquals(throttlingRejectCount, THREAD_COUNT + 1 - throttlingAcceptCount);


        System.out.println("ensureServerHasThrottlingStopsAcceptingTest");
        assertEquals(capacityAcceptCount, capacityLowerBound);
        assertEquals(capacityRejectCount, THREAD_COUNT - capacityAcceptCount);
        serverCapacity.terminateRequest();
        if (serverCapacity.acceptRequest()) {
            capacityAcceptCount++;
        } else {
            capacityRejectCount++;
        }
        assertEquals(capacityAcceptCount, throttlingLowerBound + 1);
        assertEquals(capacityRejectCount, THREAD_COUNT + 1 - capacityAcceptCount);
    }
}
