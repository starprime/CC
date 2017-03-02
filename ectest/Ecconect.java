package ectest;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cloudsearchdomain.model.Bucket;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;

public class Ecconect {
	static AmazonEC2 amazonEC2client;
	
	static AmazonS3Client s3client;
	
	//static String[] grpName="default";
	static List sgp = new ArrayList();
	static List stpls =  new ArrayList();
	static String insid="";

	
	static String keyName="EC2LINUX";
	public static void main(String[] args) {
		//TODO Auto-generated method stub
		amazonEC2client = AmazonEC2ClientBuilder.standard().withRegion("us-west-2").build();
		
		s3client = new AmazonS3Client();
		createInstance();
		stopInstance();
		//createBucket();
		//terminateIns();
		//listBucket();
		//listInstances();
}  
	static void createBucket(){
		String bucketName = "starscream-aws-stu";
		
		com.amazonaws.services.s3.model.Bucket buck = s3client.createBucket(bucketName);
		
		System.out.println("buc "+buck.toString());
	}
	static void listBucket(){
		 List<com.amazonaws.services.s3.model.Bucket> buckets = s3client.listBuckets();
		 
		 for(com.amazonaws.services.s3.model.Bucket b: buckets){
			 	System.out.println("- "+b.getName());
		 }
	 }
	 
	 static void terminateIns(){
			stpls.add("i-0d95f711217c3fa02");
		 TerminateInstancesRequest term = new TerminateInstancesRequest();
		 term.withInstanceIds(stpls);
		 
		 TerminateInstancesResult result = amazonEC2client.terminateInstances(term);
		 
		 System.out.println("terminate "+result.toString());
	 }
	
	
	
	static void createInstance(){
		sgp.add("default");
		
		String Userdata ="#!/bin/bash\nsudo ./execcsendm.sh 100";
		String formateddd = Base64.encodeBase64String(Userdata.getBytes());
		
		RunInstancesRequest run = new RunInstancesRequest();
		run.withImageId("ami-081e9d68");
		run.withInstanceType("t2.micro");
		run.withMinCount(Integer.valueOf(1));
		run.withMaxCount(Integer.valueOf(1));
		run.withKeyName(keyName);
		run.withSecurityGroups(sgp);
		run.withUserData(formateddd);

		RunInstancesResult result = amazonEC2client.runInstances(run);
		System.out.println("Instance Descript :"+ result.toString());
		
		List<Instance> inst = result.getReservation().getInstances();

		for (Instance i: inst){
			System.out.println("Instance Descript :"+i.getInstanceId());
			insid=i.getInstanceId();
		}
		
	}
	static void stopInstance(){
		stpls.add(insid);
		System.out.println("sending stop request :: ");

		StopInstancesRequest stop = new StopInstancesRequest();
		
		stop.withInstanceIds(stpls);
				
		StopInstancesResult result = amazonEC2client.stopInstances(stop);
		
		System.out.println("it is stopped : "+result);
	}
	
	static void listInstances(){
		DescribeInstancesResult di = amazonEC2client.describeInstances();
		
		List<Reservation> listReservations = di.getReservations();
		
		for(Reservation res: listReservations){
			System.out.println("list is :"+res.toString());
		}
	}
	
}


// String Userdata = "run a bash script";
// userdata ="#!/bin/bash\n mkdir haha";
//String formateddd = Base64.encodeBase64String(userdata.getBytes());
// setUserData will do encoding automatically
// 
// for long script make a file
//and pass content of the file to the script

//run.withUserData