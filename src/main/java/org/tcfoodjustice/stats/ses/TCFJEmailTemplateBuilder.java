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
    private Map<String, List<Group>> groupBindings;
    private String html;

    /**
     * Constructor which will set default values for bindings
     */
    public TCFJEmailTemplateBuilder(String html) {
        this.html = html;
        this.groupBindings = new HashMap<String, List<Group>>(){{
            put(GROUP_KEY, new ArrayList());
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
        this.groupBindings = new HashMap<String, List<Group>>(){{
            put(GROUP_KEY, groups);
        }};
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
