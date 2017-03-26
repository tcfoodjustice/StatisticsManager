package org.tcfoodjustice.stats.client;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tcfoodjustice.stats.config.SpringConfig;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class WPClientTest extends TestCase {

    @Autowired
    private WPClient client;

    @Test
    public void testGetReferrer() throws Exception {
        client.getReferrer();
    }
}