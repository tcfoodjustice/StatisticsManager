package org.tcfoodjustice.stats.wordpress;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
public class ReferrerTest {

    private String json = "{\"2017-03-01\":{\"groups\":[{\"group\":\"Search Engines\",\"name\":\"Search Engines\"," +
            "\"icon\":\"https://wordpress.com/i/stats/search-engine.png\",\"total\":46,\"follow_data\":null,\"results\":[{\"name\":\"Google Search\",\"icon\":\"https://secure.gravatar.com/blavatar/6741a05f4bc6e5b65f504c4f3df388a1?s=48\",\"views\":44,\"children\":[{\"name\":\"google.com\",\"url\":\"http://www.google.com/\",\"icon\":\"https://secure.gravatar.com/blavatar/ff90821feeb2b02a33a6f9fc8e5f3fcd?s=48\",\"views\":41},{\"name\":\"google.kz\",\"url\":\"http://www.google.kz\",\"icon\":\"https://secure.gravatar.com/blavatar/d458417816489d945a890c5c7c6d4043?s=48\",\"views\":2},{\"name\":\"google.com/search\",\"url\":\"http://www.google.com/search\",\"icon\":null,\"views\":1}]},{\"name\":\"Bing\",\"url\":\"http://www.bing.com\",\"icon\":\"https://secure.gravatar.com/blavatar/112a7e096595d1c32c4ecdfd9e56b66c?s=48\",\"views\":1},{\"name\":\"live.com\",\"url\":\"https://outlook.live.com/\",\"icon\":null,\"views\":1}]},{\"group\":\"facebook.com\",\"name\":\"Facebook\",\"url\":\"http://facebook.com/\",\"icon\":\"https://secure.gravatar.com/blavatar/2343ec78a04c6ea9d80806345d31fd78?s=48\",\"total\":13,\"follow_data\":null,\"results\":{\"views\":13}},{\"group\":\"android-app\",\"name\":\"com.google.android.googlequicksearchbox\",\"url\":\"http://android-app://com.google.android.googlequicksearchbox\",\"icon\":null,\"total\":3,\"follow_data\":null,\"results\":{\"views\":3}},{\"group\":\"sustainableamerica.org\",\"name\":\"sustainableamerica.org/foodrescue/locations/tc-food-justice/\",\"url\":\"http://sustainableamerica.org/foodrescue/locations/tc-food-justice/\",\"icon\":null,\"total\":2,\"follow_data\":null,\"results\":{\"views\":2}},{\"group\":\"idealist.org\",\"name\":\"idealist.org/view/nonprofit/MnnnnxjDhnH4/\",\"url\":\"http://www.idealist.org/view/nonprofit/MnnnnxjDhnH4/\",\"icon\":null,\"total\":1,\"follow_data\":null,\"results\":{\"views\":1}},{\"group\":\"amazon-seo-service.com\",\"name\":\"amazon-seo-service.com/try.php?u=http%3A%2F%2Ftcfoodjustice.org\",\"url\":\"http://amazon-seo-service.com/try.php?u=http%3A%2F%2Ftcfoodjustice.org\",\"icon\":null,\"total\":1,\"follow_data\":null,\"results\":{\"views\":1}},{\"group\":\"northeastmarket.org\",\"name\":\"northeastmarket.org/events/\",\"url\":\"http://www.northeastmarket.org/events/\",\"icon\":null,\"total\":1,\"follow_data\":null,\"results\":{\"views\":1}}]," +
            "\"other_views\":0,\"total_views\":67}}";
    private ObjectMapper objectMapper = new ObjectMapper();
    private Map<String, Map<String, Object>> days;
    private Referrer referrer;
    @Before
    public void setup() throws IOException {
        days = objectMapper.readValue(json, Map.class);
        referrer = new Referrer();
        referrer.setDays(days);

    }
    @Test
    public void testGetGroups() throws Exception {
        assertThat(referrer.getGroups().size(), is(7));
    }
    @Test
    public void testGetGroupsSearchEngine() throws Exception {
        List<Group> groups = referrer.getGroups();
        //assumption that order is maintained.  If this begins to fail, rewrite
        Group group = groups.get(0);
        assertThat(group.getGroup(), is("Search Engines"));
    }
    @Test
    public void testGetGroupsName() throws Exception {
        List<Group> groups = referrer.getGroups();
        //assumption that order is maintained.  If this begins to fail, rewrite
        Group group = groups.get(0);
        assertThat(group.getName(), is("Search Engines"));
    }
    @Test
    public void testGetGroupsIcon() throws Exception {
        List<Group> groups = referrer.getGroups();
        //assumption that order is maintained.  If this begins to fail, rewrite
        Group group = groups.get(0);
        assertThat(group.getIcon(), is("https://wordpress.com/i/stats/search-engine.png"));
    }
    @Test
    public void testGetGroupsTotal() throws Exception {
        List<Group> groups = referrer.getGroups();
        //assumption that order is maintained.  If this begins to fail, rewrite
        Group group = groups.get(0);
        assertThat(group.getTotal(), is(46));
    }
    @Test
    public void testGetGroupsUrl() throws Exception {
        List<Group> groups = referrer.getGroups();
        //assumption that order is maintained.  If this begins to fail, rewrite
        Group group = groups.get(1);
        assertThat(group.getUrl(), is("http://facebook.com/"));
    }

}