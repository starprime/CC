package ccproj;


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
import com.amazonaws.services.sqs.model.*;

public class ResponsePiValue {
static AmazonSQS sqs = new AmazonSQSClient();
static Region usWest2 = Region.getRegion(Regions.US_WEST_2);
static String myQueueUrl = null;
ReceiveMessageRequest receiveMessageRequest;
static EConnect ec;



public  ResponsePiValue(String remessage) {
//System.out.print("i am in constructor");
sqs.setRegion(usWest2);
//System.out.print("i am in constructor");

GetQueueUrlResult gqr = sqs.getQueueUrl("request");
myQueueUrl = gqr.getQueueUrl();


try {
    receiveMessageRequest = new ReceiveMessageRequest(myQueueUrl);
    System.out.println("Receiving messages from Request Queue.\n");

        List<Message> messages = sqs.receiveMessage(receiveMessageRequest).getMessages();
        for (Message message : messages) {
            System.out.println("  Message");
            System.out.println("    MessageId:     " + message.getMessageId());
            System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
            System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
            System.out.println("    Body:          " + message.getBody());
            System.out.println("    Attribute:     " + message.getAttributes().entrySet().size());
            if(remessage.equals(message.getBody())){
                ec =new EConnect();
                ec.createInstance(message.getBody());
                String messageReceiptHandle = message.getReceiptHandle();
                sqs.deleteMessage(new DeleteMessageRequest(myQueueUrl, messageReceiptHandle));
                System.out.println("message from request queue deleted");
            }



        }
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
}
