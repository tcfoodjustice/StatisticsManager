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

/**
 * Created by andrew.larsen on 3/26/2017.
 */
@Component
public class WPClient {

    //todo-add as param
    private static final String TOKEN_KEY = "WP_TOKEN";
    private static final String url = "https://public-api.wordpress.com/rest/v1.1/sites/tcfoodjustice.org/stats/referrers";
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
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(AUTH, bearer + token);
        HttpEntity httpEntity = new HttpEntity(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam(periodParam, period)
                .queryParam(fieldsParam, fields);
        String url = builder.toUriString();
        ResponseEntity<Referrer> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Referrer.class);
        return response.getBody();
    }
}
