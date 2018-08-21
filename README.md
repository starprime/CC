# Cloud Pi
An elastic, scalable SaaS application using Amazon IaaS to calculate the value of ’Pi’ using SFFT C program.

## Decriptions
It follows the typical three-tier architecture: 
1) the web tier services the requests for computing Pi
2) the application tier performs the computation and 
3) the data tier stores the computing result. The application tier needs to automatically scales up and down based on the demand of the requests.

## Deployment
1) start.sh starts your web tier and application tier instances and initializes your S3 storage.
2) stop.sh stops all your instances. 
3) list_instances.sh list all your instances’ IDs and their current CPU usages, one instance per
line.
4) list_data.sh list all the input-output pairs stored in S3, one input-output pair per line. Note
that it should list the results from only the current run of CloudPi.
