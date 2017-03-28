package org.tcfoodjustice.stats.ses;

import com.amazonaws.services.simpleemail.model.*;
import groovy.lang.MissingPropertyException;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
public class EmailRequestBuilder {

    private Destination destination;
    private Content subject;
    private Body body;
    private String from;

    private EmailRequestBuilder(String body) {
        Content textBody = new Content().withData(body);
        this.body = new Body().withHtml(textBody);
    }

    public EmailRequestBuilder withTo(String[] to) {
        // Construct an object to contain the recipient address.
        this.destination = new Destination().withToAddresses(to);
        return this;
    }

    public EmailRequestBuilder withSubject(String subject) {
        // Create the subject of the message.
        this.subject = new Content().withData(subject);
        return this;
    }

    public EmailRequestBuilder withFrom(String from) {
        this.from = from;
        return this;
    }

    /**
     * Method to create initial builder
     * @param body
     * @return
     */
    public static EmailRequestBuilder fromBody(String body){
        return new EmailRequestBuilder(body);
}

    /**
     * Method to build SendEmailRequest
     * @return
     */
    public SendEmailRequest build(){
        // Create a message with the specified subject and body.
        if(subject == null){
            throw new MissingPropertyException("Subject missing");
        }
        if(body == null){
            throw new MissingPropertyException("Body missing");
        }
        if(from == null){
            throw new MissingPropertyException("From missing");
        }
        if(destination == null){
            throw new MissingPropertyException("Destination missing");
        }
        Message message = new Message().withSubject(subject).withBody(body);
        // Assemble the email.
        SendEmailRequest request = new SendEmailRequest().withSource(from).withDestination(destination).withMessage(message);
        return request;
    }


}
