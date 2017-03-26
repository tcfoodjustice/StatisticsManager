package org.tcfoodjustice.stats;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.simpleemail.model.SendRawEmailRequest;
import org.apache.log4j.Logger;
import org.tcfoodjustice.stats.client.WPClient;
import org.tcfoodjustice.stats.config.SpringConfig;
import org.tcfoodjustice.stats.file.FileReader;
import org.tcfoodjustice.stats.ses.EmailClient;
import org.tcfoodjustice.stats.ses.EmailRequestBuilder;
import org.tcfoodjustice.stats.ses.TCFJEmailTemplateBuilder;
import org.tcfoodjustice.stats.wordpress.Group;
import org.tcfoodjustice.stats.wordpress.Referrer;

import java.util.Arrays;
import java.util.List;

/**
 * Created by andrew.larsen on 3/25/2017.
 */
public class StatisticsHandler extends AbstractHandler<SpringConfig> implements RequestHandler<String, String> {
    private static final Logger log = Logger.getLogger(StatisticsHandler.class);
    private static final String templateFilePath = "emailtemplate.html";
    private static final List<String> TO = Arrays.asList("andrew.larse514@gmail.com");
    private static final String FROM = "andrew.larsen@tcfoodjustice.org";
    private static final String SUBJECT = "LAMBDA EMAIL SES 1";
    private WPClient wpClient;
    private FileReader fileReader;
    private EmailClient emailClient;
    public StatisticsHandler(){
        super();
        this.wpClient = applicationContext.getBean(WPClient.class);
        this.fileReader = applicationContext.getBean(FileReader.class);
        this.emailClient = applicationContext.getBean(EmailClient.class);

    }
    @Override
    public String handleRequest(String input, Context context) {
        try {
            //first grap the referrers
            Referrer referrer = wpClient.getReferrer();
            List<Group> groups = referrer.getGroups();
            //now create the email with our email builder by reading in the emailtemplate html page as a string
            String html = fileReader.getFileAsString(templateFilePath);
            String email = TCFJEmailTemplateBuilder.fromHtml(html)
                    .withGroupBindings(groups).renderHtml();
            SendRawEmailRequest request = EmailRequestBuilder
                    .fromBody(email)
                    .withFrom(FROM)
                    .withTo(TO)
                    .withSubject(SUBJECT).build();

            emailClient.sendEmail(request);
        }
        catch (Exception e){
            log.error("Exception encountered during processing ", e);
        }
        return null;
    }
}
