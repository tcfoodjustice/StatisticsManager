package org.tcfoodjustice.stats.ses;

import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import org.tcfoodjustice.stats.wordpress.Group;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
public class TCFJEmailTemplateBuilder {

    private static final String GROUP_KEY = "groups";
    private static final String VIEWS_KEY = "views";
    private static final String VISITORS_KEY = "visitors";

    private Map<String, Object> groupBindings;
    private String html;

    /**
     * Constructor which will set default values for bindings
     */
    public TCFJEmailTemplateBuilder(String html) {
        this.html = html;
        this.groupBindings = new HashMap<String, Object>(){{
            put(GROUP_KEY, new ArrayList());
            put(VIEWS_KEY, "0");
            put(VISITORS_KEY, "0");


        }};
    }

    /**
     * Method to create standard html template
     * @return
     */
    public static TCFJEmailTemplateBuilder fromHtml(String html){
        return new TCFJEmailTemplateBuilder(html);
    }

    /**
     * Method to add group bindings
     * @param groups
     * @return
     */
    public TCFJEmailTemplateBuilder withGroupBindings(List<Group> groups){
        this.groupBindings.put(GROUP_KEY, groups);
        return this;
    }

    /**
     * Method to add views bindings
     * @param views
     * @return
     */
    public TCFJEmailTemplateBuilder withViewsBindings(String views){
        this.groupBindings.put(VIEWS_KEY, views);
        return this;
    }

    /**
     * Method to add visitors bindings
     * @param visitors
     * @return
     */
    public TCFJEmailTemplateBuilder withVisitorsBindings(String visitors){
        this.groupBindings.put(VISITORS_KEY, visitors);
        return this;
    }

    /**
     * Method to render html as string
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String renderHtml() throws IOException, ClassNotFoundException {
        SimpleTemplateEngine engine = new SimpleTemplateEngine();
        Template template = engine.createTemplate(this.html);
        return template.make(this.groupBindings).toString();
    }
}
