package org.tcfoodjustice.stats.ses;

import com.amazonaws.services.simpleemail.model.*;
import groovy.lang.MissingPropertyException;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
public class EmailRequestBuilder {

    private List<String> destinations;
    private Content subject;
    private RawMessage body;
    private String from;

    private EmailRequestBuilder(String body) {
        RawMessage rawMessage = new RawMessage();
        rawMessage.setData(ByteBuffer.wrap(body.getBytes()));
        this.body = rawMessage;
    }

    public EmailRequestBuilder withTo(List<String> to) {
        // Construct an object to contain the recipient address.
        this.destinations = to;
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
    public SendRawEmailRequest build(){
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
        if(destinations == null || destinations.isEmpty()){
            throw new MissingPropertyException("Destination missing");
        }
        // Assemble the email.
        SendRawEmailRequest request = new SendRawEmailRequest().withSource(from).withDestinations(destinations).withRawMessage(body);
        return request;
    }


}
