package org.tcfoodjustice.stats.ses;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
@Component
public class EmailClient {
    private static final Logger log = Logger.getLogger(EmailClient.class);

    public void sendEmail( SendEmailRequest request) {
        log.info("Attempting to send an email through Amazon SES by using the AWS SDK for Java...");
        // Choose the AWS region of the Amazon SES endpoint you want to connect to. Note that your sandbox
        // status, sending limits, and Amazon SES identity-related settings are specific to a given AWS
        // region, so be sure to select an AWS region in which you set up Amazon SES. Here, we are using
        // the US West (Oregon) region. Examples of other regions that Amazon SES supports are US_EAST_1
        // and EU_WEST_1. For a complete list, see http://docs.aws.amazon.com/ses/latest/DeveloperGuide/regions.html
        Region REGION = Region.getRegion(Regions.US_WEST_2);

        // Instantiate an Amazon SES client, which will make the service call. The service call requires your AWS credentials.
        // Because we're not providing an argument when instantiating the client, the SDK will attempt to find your AWS credentials
        // using the default credential provider chain. The first place the chain looks for the credentials is in environment variables
        // AWS_ACCESS_KEY_ID and AWS_SECRET_KEY.
        // For more information, see http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
        AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard().withRegion(REGION.getName()).build();
        // Send the email.
        SendEmailResult result = client.sendEmail(request);
        log.info("Email sent with messageId " + result.getMessageId());

    }

}
