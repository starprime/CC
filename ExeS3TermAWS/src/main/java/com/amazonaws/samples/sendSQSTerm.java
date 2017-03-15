package com.amazonaws.samples;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.io.IOException;

import com.amazon.sqs.javamessaging.AmazonSQSExtendedClient;
import com.amazon.sqs.javamessaging.ExtendedClientConfiguration;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;





public class sendSQSTerm {
    private static final String s3BucketName = "star-cc-pro";

    static AmazonSQS sqs = new AmazonSQSClient();
    static Region usWest2 = Region.getRegion(Regions.US_WEST_2);
    static String inp=null;
	
	public static void cretest(String cmd){
    	try{
    		Process p;
        	System.out.println ("in cretest\n "+cmd);

    		p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
    	}
        catch(IOException ex) {
        	System.out.println (ex.toString());

        }
    	catch(InterruptedException e2) {            
        	System.out.println("I was interrupted!");
        }
	}
	
	public static void terminate(){
    	try{
    		Process p;
//    		String cmd="echo haha";
    		String cmd="/home/ec2-user/./teminate.sh";
    		p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
    	}
        catch(IOException ex) {
        	System.out.println (ex.toString());

        }
    	catch(InterruptedException e2) {            
        	System.out.println("I was interrupted!");
        }
	}
    public static void main(String args[]) {
		String op="";
       String s=args[0];
       String cmnd ="/home/ec2-user/./crtes.sh "+s;
            inp=s;

        Process p,q;
        try {
        	System.out.println ("execccc");

        	cretest(cmnd);
            q = Runtime.getRuntime().exec("/home/ec2-user/./execc.sh");
            q.waitFor();
            
			String sCurrentLine="";
			BufferedReader br = new BufferedReader(new FileReader("/home/ec2-user/out.txt"));

			while ((sCurrentLine = br.readLine()) != null) {
				op=op+sCurrentLine;
			}
        }
        catch(IOException ex) {
        	System.out.println (ex.toString());

        }
        catch(InterruptedException e2) {            
        	System.out.println("I was interrupted!");
        }
        
        sendReqMesg(op);
        System.out.println("finished.\n\n"+op);
        terminate();
    }
    
    public static void sendReqMesg(String message) {
    	message=inp+"-"+message;
    	storeinS3(message);
        sqs.setRegion(usWest2);
//    	GetQueueUrlResult gqr = sqs.getQueueUrl("response");
//        String myQueueUrl = gqr.getQueueUrl();
        GetQueueUrlResult gqr = sqs.getQueueUrl("star-s3-str-res");
        String myQueueUrl = gqr.getQueueUrl();

        try { // just sending a message heres
            SendMessageRequest request = new SendMessageRequest();
            request.withMessageBody(message);
            request.withQueueUrl(myQueueUrl);
            sqs.sendMessage(request);
            
            
            System.out.println("Sending a message " + message + " to Req ques.\n");

            SendMessageResult sendMessageResult = sqs.sendMessage(request);
            String sequenceNumber = sendMessageResult.getSequenceNumber();
            System.out.println("sending message result "+sendMessageResult.toString());
            
            

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
    
    public static  void storeinS3(String message){
    	try{        
        AWSCredentials credentials = null;

        try {
            credentials = new ProfileCredentialsProvider("default").getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException(
                    "Cannot load the AWS credentials from the expected AWS credential profiles file. "
                            + "Make sure that your credentials file is at the correct "
                            + "location (/home/$USER/.aws/credentials) and is in a valid format.", e);
        }

        AmazonS3 s3 = new AmazonS3Client(credentials);
        Region s3Region = Region.getRegion(Regions.US_WEST_2);
        s3.setRegion(s3Region);


        // Set the SQS extended client configuration with large payload support enabled.
        ExtendedClientConfiguration extendedClientConfig = new ExtendedClientConfiguration()
                .withLargePayloadSupportEnabled(s3, s3BucketName);

        AmazonSQS sqsExtended = new AmazonSQSExtendedClient(new AmazonSQSClient(credentials), extendedClientConfig);
        Region sqsRegion = Region.getRegion(Regions.US_WEST_2);
        //sqsExtended.setRegion(sqsRegion);


        //String QueueName = "star-s3-str-res";//"star-s3-str-res";
        //CreateQueueRequest createQueueRequest = new CreateQueueRequest(QueueName);
        

        //GetQueueUrlResult gqr = sqs.getQueueUrl("star-s3-str-res");
        //String myQueueUrl = gqr.getQueueUrl();
        //String myQueueUrl="https://sqs.us-west-2.amazonaws.com/778891075578/response";
        String myQueueUrl = "https://sqs.us-west-2.amazonaws.com/778891075578/star-s3-str-res";
        System.out.println("Queue created.");

        // Send the message.
        SendMessageRequest myMessageRequest = new SendMessageRequest(myQueueUrl, message);
        sqsExtended.sendMessage(myMessageRequest);
        System.out.println("Sent the message.");    	
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

