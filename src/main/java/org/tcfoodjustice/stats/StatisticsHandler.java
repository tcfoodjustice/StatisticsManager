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
            String[] recipients = env.getOrDefault(RECIPIENTS_KEY, "").split(" ");
            String subject = env.getOrDefault(SUBJECT_KEY, "");
            String from = env.getOrDefault(FROM_KEY, "");
            String emailTemplate = env.getOrDefault(EMAIL_TEMPLATE_KEY, "");

            //first grap the referrers
            Referrer referrer = wpClient.getReferrer();
            List<Group> groups = referrer.getGroups();
            //then summary
            Summary summary = wpClient.getSummary();
            //now create the email with our email builder by reading in the emailtemplate html page as a string
            String html = fileReader.getFileAsString(emailTemplate);

            String email = TCFJEmailTemplateBuilder.fromHtml(html)
                    .withGroupBindings(groups)
                    .withVisitorsBindings(String.valueOf(summary.getVisitors()))
                    .withViewsBindings(String.valueOf(summary.getViews()))
                    .renderHtml();

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
