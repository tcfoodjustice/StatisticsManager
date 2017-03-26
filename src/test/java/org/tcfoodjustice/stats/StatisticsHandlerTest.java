package org.tcfoodjustice.stats;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
public class StatisticsHandlerTest {

    @Mock
    Context context;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

    }
    @Test
    public void testHandleRequest() throws Exception {
        StatisticsHandler handler = new StatisticsHandler();
        handler.handleRequest("", context);
    }
}