# Cloud Pi
An elastic, scalable SaaS application using Amazon IaaS to calculate the value of ’Pi’ using SFFT C program.

## Decriptions
It follows the typical three-tier architecture: 1) the web tier services the requests for computing Pi; 2) the
application tier performs the computation; and 3) the data tier stores the computing result. The
application tier needs to automatically scales up and down based on the demand of the requests.
