package ectest;

import java.io.*;                                                            

public class Test {                                                          
  public static void main(String[] args) throws Exception {                  
    String[] command = { "./myscript", "key", "ls -t | tail -n 1" };         
    Process process = Runtime.getRuntime().exec(command);                    
    BufferedReader reader = new BufferedReader(new InputStreamReader(        
        process.getInputStream()));                                          
    String s;                                                                
    while ((s = reader.readLine()) != null) {                                
      System.out.println("Script output: " + s);                             
    }                                                                        
  }                                                                          
}  

/*#!/bin/bash                                
key="$1"                                   
value=$(eval "$2")                             
echo "The command  $2  evaluated to: $value" 

Here's how we can run myscript separately:

$ ls -t | tail -n 1
Templates

$ ./myscript foo 'ls -t | tail -n 1'
The command  ls -t | tail -n 1  evaluated to: Templates*/