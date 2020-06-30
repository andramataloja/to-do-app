In **application.properties** file found in to-do-app-backend/src/main/resources/

Fill these properties with your data:

```
spring.datasource.url
spring.datasource.username 
spring.datasource.password
twilio.phone_number.to = add phone number where you want to get sms notification
```
Have your Twilio account created, where you are able to get your ACCOUNT SID, AUTH TOKEN and TWILIO PHONE NUMBER (find more info from twilio docs)

You have installed Twilio CLI, Twilio Java Helper Library. 

Credentials are declared in to-do-app-backend/src/main/java/com/todoapp/todoapp/domain/twilio/Credentials.java

Set environment variables with your twilio credentials for these 3 variables:
```
TWILIO_ACCOUNT_SID
TWILIO_AUTH_TOKEN
TWILIO_PHONE_NUMBER
```
