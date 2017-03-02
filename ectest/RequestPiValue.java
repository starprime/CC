package ectest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.DeleteMessageRequest;
import com.amazonaws.services.sqs.model.DeleteQueueRequest;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;

/**
 * This sample demonstrates how to make basic requests to Amazon SQS using the
 * AWS SDK for Java.
 *
 * Prerequisites: You must have a valid Amazon Web
 * Services developer account, and be signed up to use Amazon SQS. For more
 * information on Amazon SQS, see http://aws.amazon.com/sqs.
 * 
 * aws sqs send-message --queue-url "https://us-west-2.queue.amazonaws.com/778891075578/response.fifo" --message-body "from console" 
--message-group-id  "MyMessageGroupId1234567890" --message-deduplication-id "MyMessageDeduplicationId1234567890"
 * 
 * #!/bin/bash
 * echo "$1"
 *	cd /usr/pical/ && exec ./hel "$1"
 * 
 * 
 * Fill in your AWS access credentials in the provided credentials file
 * template, and be sure to move the file to the default location
 * (~/.aws/credentials) where the sample code will load the credentials from.
 * 
 * WARNING: To avoid accidental leakage of your credentials, DO NOT keep
 * the credentials file in your source directory.
 */
public class RequestPiValue {
	AttachVolume av;
    static AmazonSQS sqs = new AmazonSQSClient();
    static Region usWest2 = Region.getRegion(Regions.US_WEST_2);

	public  RequestPiValue(String message){
    	sqs.setRegion(usWest2);
        try { // just sending a message heres
            String myQueueUrl = "https://sqs.us-west-2.amazonaws.com/778891075578/request.fifo";          
            Map<String, MessageAttributeValue> messageAttributes = new HashMap<String, MessageAttributeValue>();
            messageAttributes.put("Value", new MessageAttributeValue().withDataType("Number").withStringValue("230.000000000000000001"));
            SendMessageRequest request = new SendMessageRequest();
			request.withMessageBody(message);
			request.withMessageGroupId("MyMessageGroupId1234567890");
			request.withMessageDeduplicationId("MyMessageDeduplicationId1234567890");
			request.withQueueUrl(myQueueUrl);
			request.withMessageAttributes(messageAttributes);
			sqs.sendMessage(request);     
            System.out.println("Sending a message to MyQueue.\n");
        } 
        catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it " +
                    "to Amazon SQS, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered " +
                    "a serious internal problem while trying to communicate with SQS, such as not " +
                    "being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }
}