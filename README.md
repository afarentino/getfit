# GetFit-Spring-Native

GetFit-Spring-Native is a sample PoC (Proof of Concept) 
Spring Native console-based application designed to
parse exercise entries found in a text file and 
convert them into a CSV file for analysis.

When taking a new technology for a test drive, a sample
application is a developer's best friend. 

This small application and the programming challenge
it solves, presents a way to do just that.

### Technologies Used
* Spring Native
* Spring Boot
* Gradle 7.x
* JUnit 5
* Java OpenJDK 17
* GraalVM CE with Native Image Support

### Application Background

As part of their terms of service, many of today's wearable and
smartphone based fitness trackers require users to upload metrics 
to third-party cloud services for analysis.

However, some users have privacy concerns with publicly sharing this data. Reference: [USA today article](https://www.usatoday.com/story/sports/2019/08/16/what-info-do-fitness-apps-keep-share/1940916001/)

The GetFit Sample Spring Native application reads as input a local
text file containing user log entries exercise details data and 
converts it into a CSV file. 

### Sample Input Text File Format
Each exercise log entry is separated by a blank line.  Record
entries are all optional and can appear in any order.  The
application is capable of normalizing this data and will
report errors to the user during parsing in the event it reads
record entries that cannot be parsed.

### Usage
``` getfit --inFile fileName```
java --jar --Pargs --fileName
### CSV Output

The output CSV file will be generated to the same directory
that the input file exists.  

It conforms to the CSV Format standardization rules found in [RFC-4180](https://datatracker.ietf.org/doc/html/rfc4180#section-2)

### Building
Download the source code of this project and follow the
documentation over
in the [HELP.md](HELP.md) file.
