package ectest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
import com.amazonaws.services.ec2.model.AttachVolumeRequest;
import com.amazonaws.services.ec2.model.AttachVolumeResult;
import com.amazonaws.services.ec2.model.CreateVolumeRequest;
import com.amazonaws.services.ec2.model.CreateVolumeResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.amazonaws.services.ec2.model.TerminateInstancesResult;

//i-052b1ff280ecd8ca6

public class AttachVolume {
	static AmazonEC2 amazonEC2client = AmazonEC2ClientBuilder.standard().withRegion("us-west-2").build();

	static CreateVolumeRequest cvr = new CreateVolumeRequest();
	static List sgp = new ArrayList();
	static List stpls =  new ArrayList();
	static String keyName="EC2LINUX";
	static String insid="";
	static String insavzone="";
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		amazonEC2client = AmazonEC2ClientBuilder.standard().withRegion("us-west-2").build();
		//createVolume();
		createInstance();
		terminateIns();
		
	}
	/*static void createVolume(){
		cvr.withAvailabilityZone(insavzone);
		cvr.withSize(1);
		cvr.withVolumeType("gp2");
		cvr.withSnapshotId("snap-06765a16c077a0c96"); // we will hardcode this value
		
		CreateVolumeResult result = amazonEC2client.createVolume(cvr);
		System.out.println("vol created : "+result.toString());
		
		AttachVolumeRequest avr= new AttachVolumeRequest();
		avr.withInstanceId(insid);
		avr.withVolumeId(result.getVolume().getVolumeId());
		
		AttachVolumeResult atchRes = amazonEC2client.attachVolume(avr);
		System.out.println("attached volume : "+ atchRes.toString());
	}*/
	static void createInstance(){
		sgp.add("default");
		
		String Userdata ="#!/bin/bash\n ./execcsendm.sh 561234";
		String formateddd = Base64.encodeBase64String(Userdata.getBytes());
		
		RunInstancesRequest run = new RunInstancesRequest();
		run.withImageId("ami-8747c7e7");
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
			insavzone=i.getPlacement().getAvailabilityZone();
		}
	}
	static void terminateIns(){
		stpls.add(insid);
	 TerminateInstancesRequest term = new TerminateInstancesRequest();
	 term.withInstanceIds(stpls);
	 
	 TerminateInstancesResult result = amazonEC2client.terminateInstances(term);
	 
	 System.out.println("terminated "+result.toString());
 }

}
