package org.tcfoodjustice.stats.ses;

import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import groovy.lang.MissingPropertyException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
public class EmailRequestBuilderTest {
    static final String FROM = "SENDER@EXAMPLE.COM";  // Replace with your "From" address. This address must be verified.
    static final String TO = "RECIPIENT@EXAMPLE.COM"; // Replace with a "To" address. If your account is still in the
    // sandbox, this address must be verified.
    static final String BODY = "This email was sent through Amazon SES by using the AWS SDK for Java.";
    static final String SUBJECT = "Amazon SES test (AWS SDK for Java)";

    @Test
    public void testBuildBody() throws Exception {
        SendEmailRequest request = EmailRequestBuilder.fromBody(BODY).withFrom(FROM).withTo(new String[]{TO}).withSubject(SUBJECT).build();
        assertThat(new String(request.getMessage().getBody().getHtml().toString()), is("{Data: This email was sent through Amazon SES by using the AWS SDK for Java.,}"));
    }
    @Test
    public void testBuildFrom() throws Exception {
        SendEmailRequest request = EmailRequestBuilder.fromBody(BODY).withFrom(FROM).withTo(new String[]{TO}).withSubject(SUBJECT).build();
        assertThat(request.getSource(), is(FROM));
    }
    @Test
    public void testBuildTo() throws Exception {
        SendEmailRequest request = EmailRequestBuilder.fromBody(BODY).withFrom(FROM).withTo(new String[]{TO}).withSubject(SUBJECT).build();
        assertThat(request.getDestination().getToAddresses().get(0), is(TO));
    }
    @Test
    public void testBuildSubject() throws Exception {
        SendEmailRequest request = EmailRequestBuilder.fromBody(BODY).withFrom(FROM).withTo(new String[]{TO}).withSubject(SUBJECT).build();
        assertThat(request.getMessage().getSubject().toString(), is("{Data: Amazon SES test (AWS SDK for Java),}"));
    }

    @Test(expected = MissingPropertyException.class)
    public void testBuildNoFrom() throws Exception {
        SendEmailRequest request = EmailRequestBuilder.fromBody(BODY).withTo(new String[]{TO}).withSubject(SUBJECT).build();
    }
    @Test(expected = MissingPropertyException.class)
    public void testBuildNoTo() throws Exception {
        SendEmailRequest request = EmailRequestBuilder.fromBody(BODY).withFrom(FROM).withSubject(SUBJECT).build();
    }
    @Test(expected = MissingPropertyException.class)
    public void testBuildNoSubject() throws Exception {
        SendEmailRequest request = EmailRequestBuilder.fromBody(BODY).withFrom(FROM).withTo(new String[]{TO}).build();
    }
}