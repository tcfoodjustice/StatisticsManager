package org.tcfoodjustice.stats.ses;

import org.junit.Before;
import org.junit.Test;
import org.tcfoodjustice.stats.wordpress.Group;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
public class TCFJEmailTemplateBuilderTest {

    private String html;

    @Before
    public void setUp() throws Exception {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource("emailtemplate.html").toURI());

        StringBuilder data = new StringBuilder();
        Stream<String> lines = Files.lines(path);
        lines.forEach(line -> data.append(line).append("\n"));
        lines.close();

        html = data.toString();

    }

    @Test
    public void testRenderHtmlName() throws Exception {
        Group group = new Group();
        group.setName("NAME1");
        Group group1 = new Group();
        group1.setName("NAME2");

        String result = TCFJEmailTemplateBuilder.fromHtml(html).withGroupBindings(Arrays.asList(group, group1)).renderHtml();
        assert result.contains(" <td class='text ' data-title='Referrer'>"+group.getName()+"</td>");
        assert result.contains(" <td class='text ' data-title='Referrer'>"+group1.getName()+"</td>");
    }

    @Test
    public void testRenderHtmlTotal() throws Exception {
        Group group = new Group();
        group.setTotal(100);
        Group group1 = new Group();
        group1.setTotal(200);

        String result = TCFJEmailTemplateBuilder.fromHtml(html).withGroupBindings(Arrays.asList(group, group1)).renderHtml();
        assert result.contains(" <td class='text ' data-title='Visits'>"+group.getTotal()+"</td>");
        assert result.contains(" <td class='text ' data-title='Visits'>"+group1.getTotal()+"</td>");
    }
    @Test
    public void testRenderHtmlNoGroups() throws Exception {
        String result = TCFJEmailTemplateBuilder.fromHtml(html).renderHtml();
        System.out.println(result);
        assert result.contains(" <td class='text ' data-title='Referrer'>");
        assert !result.contains("<td class='text ' data-title='Visits'>");

    }
}