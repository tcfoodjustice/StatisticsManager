package org.tcfoodjustice.stats.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.tcfoodjustice.stats.wordpress.Referrer;
import org.tcfoodjustice.stats.wordpress.Summary;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
@Component
public class WPClient {

    //todo-add as param
    private static final String TOKEN_KEY = "WP_TOKEN";
    private static final String referrerUrl = "https://public-api.wordpress.com/rest/v1.1/sites/tcfoodjustice.org/stats/referrers";
    private static final String summaryUrl = "https://public-api.wordpress.com/rest/v1.1/sites/tcfoodjustice.org/stats/summary";
    private static final String periodParam = "period";
    private static final String period = "month";
    private static final String fieldsParam = "fields";
    private static final String fields = "days";
    private static final String AUTH = "Authorization";
    private static final String bearer = "Bearer ";

    private String token;

    public WPClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        token = "ECWnJapIu!!1)$(zsdEt430^L*$yaquDtFshwQefzyFQTVqUg5dKK6qCFP!Yxpfx";//System.getenv(TOKEN_KEY);
    }

    private RestTemplate restTemplate;

    /**
     * Method to make http call to wordpress API
     * @return
     */
    public Referrer getReferrer(){
        HttpEntity httpEntity = createHttpEntity();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(referrerUrl)
                .queryParam(periodParam, period)
                .queryParam(fieldsParam, fields);
        String url = builder.toUriString();
        ResponseEntity<Referrer> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Referrer.class);
        return response.getBody();
    }


    /**
     * Method to retrieve Wordpress stats summary
     * @return
     */
    public Summary getSummary(){
        HttpEntity httpEntity = createHttpEntity();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(summaryUrl)
                .queryParam(periodParam, period);
        String url = builder.toUriString();
        ResponseEntity<Summary> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Summary.class);

        return response.getBody();
    }

    /**
     * Helper method to set http headers and create http entity object
     * @return
     */
    private HttpEntity createHttpEntity() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(AUTH, bearer + token);
        return new HttpEntity(headers);
    }

}
