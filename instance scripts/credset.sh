#!/bin/bash


aws configure set aws_access_key_id 
aws configure set aws_secret_access_key 
aws configure set default.region us-west-2
aws configure set default.output text
echo "credential set"
