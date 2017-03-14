package ccproj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.io.ByteArrayInputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

/**

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
    static AmazonS3 s3client;
    static AmazonSQS sqs = new AmazonSQSClient();
    static Region usWest2 = Region.getRegion(Regions.US_WEST_2);
    static AmazonEC2 amazonEC2client;
    static String myQueueUrl = null,respQueueUrl=null;
    String str = null;
    static int cnt=0;
    public static void cretest(){
        try{
            Process p;
            System.out.println("setting credentials");
            String cmd ="/usr/./setcred.sh";
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();

        }
        catch(IOException ex) {
            System.out.println (ex.toString());

        }
        catch(InterruptedException e2) {
            System.out.println("I was interrupted! i am not happy ");
        }
    }
    public void setinp(String msg){
        str=msg;
    }
    public String getinp(){
        return str;
    }
    public void inccnt(){
        cnt++;
    }
    public void deccnt(){
        cnt--;
    }
    public int getcnt(){
        return cnt;
    }
    public RequestPiValue(String message) {
        cretest();
        Boolean flg=true;
        setinp(message);

        sqs.setRegion(usWest2);

        /*
        Map<String, String> attributes = new HashMap<String, String>();
        // A FIFO queue must have the FifoQueue attribute set to True
        attributes.put("FifoQueue", "true");
        // Generate a MessageDeduplicationId based on the content, if the user doesn't provide a MessageDeduplicationId
        attributes.put("ContentBasedDeduplication", "true");
        // The FIFO queue name must end with the .fifo suffix
        CreateQueueRequest createQueueRequest = new CreateQueueRequest("request.fifo").withAttributes(attributes);
        String myQueueUrl = sqs.createQueue(createQueueRequest).getQueueUrl();
        */
        CreateQueueRequest createQueueResponse = new CreateQueueRequest("star-s3-str-res");
        respQueueUrl = sqs.createQueue(createQueueResponse).getQueueUrl();
        System.out.println("Creating Response Queue");

        GetQueueUrlResult gqr = sqs.getQueueUrl("request");
        myQueueUrl = gqr.getQueueUrl();
        try { // just sending a message heres
            SendMessageRequest request = new SendMessageRequest();
            request.withMessageBody(message);
            //request.withMessageGroupId("MyMessageGroupId1234567890");
            //request.withMessageDeduplicationId("MyMessageDeduplicationId1234567890");
            request.withQueueUrl(myQueueUrl);
            sqs.sendMessage(request);
            System.out.println("Sending a message " + message + " to Req ques.\n");

            while (flg){
                System.out.println("Counter is "+getcnt()+"\n");
                if(getcnt()<10){
                    System.out.println("Counter is less than 10 "+getcnt()+"creating instance req\n");

                    ResponsePiValue repv = new ResponsePiValue(message);
                    inccnt();
                    flg=false;
                }
                else {
                    System.out.println("Ec2 instance limit is full,waiting..");
                    listInstances();
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

    public void listInstances() {
        amazonEC2client = AmazonEC2ClientBuilder.standard().withRegion("us-west-2").build();


        DescribeInstancesResult di = amazonEC2client.describeInstances();

        List<Reservation> listReservations = di.getReservations();

        for (Reservation res : listReservations) {
            List<Instance> instances = res.getInstances();
            for (Instance curInst : instances) {
                if ((curInst.getState().getName().equals("running")) && (!curInst.getInstanceId().equals("i-03194b3b6a28255d7"))) {
                    System.out.println("a shark will eat this instance :" + curInst.getInstanceId());
                    terminateIns(curInst.getInstanceId());
                }
            }
        }
    }


    public void terminateIns(String str) {
        TerminateInstancesRequest term = new TerminateInstancesRequest();
        term.withInstanceIds(str);
        System.out.print("in terminate instance deleting instance" + str);
        deccnt();
        TerminateInstancesResult result = amazonEC2client.terminateInstances(term);

        System.out.println("terminate " + result.toString());
    }

    public  String retPiVal() {
        // IF YOU WANT TO DELETE OTHER RUNNING INSTANCES

        GetQueueUrlResult gqr = sqs.getQueueUrl("star-s3-str-res");
        String respQueueUrl = gqr.getQueueUrl();
        System.out.println("i am in retpal,listening to quque " + respQueueUrl);

        ReceiveMessageRequest resMessageRequest = new ReceiveMessageRequest(respQueueUrl);
        String ret = null;
        int cnt=0;
        Boolean flg = true;
        while (flg) {
            List<Message> messages = sqs.receiveMessage(resMessageRequest).getMessages();
            //System.out.print("..Listening...for "+str);

            for (Message message : messages) {
                //System.out.println("  Message");
                //System.out.println("    MessageId:     " + message.getMessageId());
                //System.out.println("    ReceiptHandle: " + message.getReceiptHandle());
                //System.out.println("    MD5OfBody:     " + message.getMD5OfBody());
                //System.out.println("    Body:          " + message.getBody());
                //System.out.println("    Attribute:     " + message.getAttributes().entrySet().size());
                ret = message.getBody();

                String[] token = ret.split("[-]");
                System.out.print("tok : " + token[0] + "\nstr : " + str);
                if (token[0].equals(getinp())) {
                    System.out.println("\nrecieved response for "+token[0]);
                    //    String messageReceiptHandle = message.getReceiptHandle();
                    //    sqs.deleteMessage(new DeleteMessageRequest(respQueueUrl, messageReceiptHandle));
                    //    System.out.println("\nmessage from response queue deleted");
                    System.out.println("\nthe result is \n" + token[1]);
                    Put_Object(token[0],token[1]);
                    deccnt();
                    return token[1];
                }
            }
        }
        return "0.0";
    }
    public  void Put_Object(String key,String message){
        String bucketName="star-cc-pro";

        try {
            s3client = new AmazonS3Client(new ProfileCredentialsProvider());

            ByteArrayInputStream input = new ByteArrayInputStream(message.getBytes());

            System.out.println("Uploading a new object to S3 from a file\n");
            //s3client.putObject(new PutObjectRequest(
            //	"star-cc-pro","1", message));

            s3client.putObject(bucketName,key,input,new ObjectMetadata());


            S3Object s3object = s3client.getObject(new GetObjectRequest(
                    bucketName, key));
            BufferedReader reader = new BufferedReader(new InputStreamReader(s3object.getObjectContent()));
            String line;
            try {
                while((line = reader.readLine()) != null) {
                    // can copy the content locally as well
                    // using a buffered writer
                    System.out.println(line);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
                    "means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
                    "means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }
    }

}

