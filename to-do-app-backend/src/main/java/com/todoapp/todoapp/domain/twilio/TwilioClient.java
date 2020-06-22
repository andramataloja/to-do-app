package com.todoapp.todoapp.domain.twilio;

import com.twilio.http.TwilioRestClient;

public class TwilioClient {
    private Credentials credentials;
    private TwilioMessageCreator messageCreator;

    public TwilioClient(){
        this.credentials=new Credentials();
        this.messageCreator= new TwilioMessageCreator(new TwilioRestClient.Builder(credentials.getAccountSid(), credentials.getAuthToken()).build());

    }

    public TwilioClient(TwilioMessageCreator messageCreator, Credentials credentials){
        this.messageCreator=messageCreator;
        this.credentials=credentials;
    }

    public void sendMessage(String to, String message){
        messageCreator.create(to, credentials.getPhoneNumber(), message);

    }
}
