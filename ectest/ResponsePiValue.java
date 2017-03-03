package ectest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
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


public class ResponsePiValue {
	static AmazonSQS sqs = new AmazonSQSClient();
    static Region usWest2 = Region.getRegion(Regions.US_WEST_2);
	static String myQueueUrl = "https://sqs.us-west-2.amazonaws.com/778891075578/request.fifo";      

	 ReceiveMessageRequest receiveMessageRequest;
    static Ecconect ec;

	
	public  ResponsePiValue() {
		//System.out.print("i am in constructor");
        sqs.setRegion(usWest2);
        //System.out.print("i am in constructor");

        try {
            receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
            System.out.println("sssssddsdsdsdsdsd");

        	// Receive messages
            System.out.println("Receiving messages from Request Queue.\n");
                //av=new AttachVolume();
                //av.createInstance();
                //av.terminateIns();
        } catch (AmazonServiceException ase) {
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
	public  void processReq(){
    	List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        for (Message message : messages) {
            System.out.println("  Message");
            System.out.println("    MessageId:     " + message.getMessageId());
            System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
            System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
            System.out.println("    Body:          " + message.getBody());
            System.out.println("    Attribute:     " + message.getAttributes().entrySet().size());
		    ec =new Ecconect();
		    ec.createInstance(message.getBody());
		    }
        System.out.println("Deleting a message.\n");
        //String messageReceiptHandle = messages.get(0).getReceiptHandle();
        //sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageReceiptHandle));
		}
}
	