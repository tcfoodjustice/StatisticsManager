package org.tcfoodjustice.stats;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import org.apache.log4j.Logger;
import org.tcfoodjustice.stats.client.WPClient;
import org.tcfoodjustice.stats.config.SpringConfig;
import org.tcfoodjustice.stats.file.FileReader;
import org.tcfoodjustice.stats.ses.EmailClient;
import org.tcfoodjustice.stats.ses.EmailRequestBuilder;
import org.tcfoodjustice.stats.ses.TCFJEmailTemplateBuilder;
import org.tcfoodjustice.stats.wordpress.Group;
import org.tcfoodjustice.stats.wordpress.Referrer;
import org.tcfoodjustice.stats.wordpress.Summary;

import java.util.List;
import java.util.Map;

/**
 * Created by andrew.larsen on 3/25/2017.
 */
public class StatisticsHandler extends AbstractHandler<SpringConfig> implements RequestHandler<String, String> {
    private static final Logger log = Logger.getLogger(StatisticsHandler.class);
    private static final String RECIPIENTS_KEY = "RECIPIENTS";
    private static final String SUBJECT_KEY = "SUBJECT";
    private static final String FROM_KEY = "FROM";
    private static final String EMAIL_TEMPLATE_KEY = "EMAIL_TEMPLATE";

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
            Map<String, String> env = System.getenv();
            String[] recipients = env.getOrDefault(RECIPIENTS_KEY, "andrew.larsen@tcfoodjustice.org").split(" ");
            log.info("Recipients " + recipients);
            String subject = env.getOrDefault(SUBJECT_KEY, "TCFJ Wordpress Statistics");
            log.info("subject " + subject);
            String from = env.getOrDefault(FROM_KEY, "andrew.larsen@tcfoodjustice.org");
            log.info("from " +  from);
            String emailTemplate = env.getOrDefault(EMAIL_TEMPLATE_KEY, "emailtemplate.html");
            log.info("emplateTemplate " + emailTemplate);
            //first grap the referrers
            Referrer referrer = wpClient.getReferrer();
            log.info("retrieved referrer " + referrer);
            List<Group> groups = referrer.getGroups();
            //then summary
            Summary summary = wpClient.getSummary();
            log.info("Retrieved summary " + subject);
            //now create the email with our email builder by reading in the emailtemplate html page as a string
            String html = fileReader.getFileAsString(emailTemplate);
            log.debug("read html template " + html);
            String email = TCFJEmailTemplateBuilder.fromHtml(html)
                    .withGroupBindings(groups)
                    .withVisitorsBindings(String.valueOf(summary.getVisitors()))
                    .withViewsBindings(String.valueOf(summary.getViews()))
                    .renderHtml();
            log.debug("created html template " + email);

            SendEmailRequest request = EmailRequestBuilder
                    .fromBody(email)
                    .withFrom(from)
                    .withTo(recipients)
                    .withSubject(subject).build();

            emailClient.sendEmail(request);
        }
        catch (Exception e){
            log.error("Exception encountered during processing ", e);
        }
        return null;
    }
}
