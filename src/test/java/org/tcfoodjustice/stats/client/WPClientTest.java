package org.tcfoodjustice.stats.client;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.tcfoodjustice.stats.wordpress.Referrer;
import org.tcfoodjustice.stats.wordpress.Summary;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = SpringConfig.class)
public class WPClientTest {

    @Mock
    private RestTemplate restTemplate;

    private WPClient client;
    private String referrerUrl = "https://public-api.wordpress.com/rest/v1.1/sites/tcfoodjustice.org/stats/referrers?period=month&fields=days";
    private String summaryUrl = "https://public-api.wordpress.com/rest/v1.1/sites/tcfoodjustice.org/stats/summary?period=month";

    private ResponseEntity responseEntity;
    private ResponseEntity summaryResponse;

    private Referrer referrer;
    private Summary summary;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        referrer = new Referrer();
        summary = new Summary();
        responseEntity = new ResponseEntity<Referrer>(referrer, HttpStatus.OK);
        summaryResponse = new ResponseEntity<Summary>(summary, HttpStatus.OK);

        client = new WPClient(restTemplate);
    }

    @Test
    public void testGetReferrer() throws Exception {
        given(restTemplate.exchange(eq(referrerUrl), eq(HttpMethod.GET), anyObject(),  Matchers.any(Class.class))).willReturn(responseEntity);
        Referrer response = client.getReferrer();

        assertThat(response, is(referrer));
    }
    @Test
    public void testGetSummary() throws Exception {
        given(restTemplate.exchange(eq(summaryUrl), eq(HttpMethod.GET), anyObject(),  Matchers.any(Class.class))).willReturn(summaryResponse);
        Summary response = client.getSummary();

        assertThat(response, is(summary));
    }
}