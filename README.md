# malda-java
A simple Malware detection restful api written in java

RUNNING
To start malda you can run the following command in your terminal:
$ ./mvnw spring-boot:run

MALDA DETECTION MESSAGES
A message to malda contains the following fields
Device information, with the fields
uuid: A unique ID of the device which is an UUID and a mandatory field.
type: A String and mandatory field (acceptable values: WEB, ANDROID, IOS)
model: A String and mandatory field (you can make-up data).
osVersion: String and mandatory field (you can make-up data).

Detection information
A device can have one, several, or no detections. And there can be three types of detections:
NEW (after the scan of the device, a new detection was made)
uuid: A UUID and a mandatory field.
Time: An epoch timestamp and mandatory field.
nameOfApp: A string and mandatory field (you can make-up data).
typeOfApp. A string and mandatory field (you can make-up data).

RESOLVED (after a scan, the threat previously detected is no longer present)
Detection_ID: The UUID of the detection that was made earlier in time. You
can consider there was a detection made with a UUID before.
Time: An epoch timestamp and mandatory field.

NO_DETECTION (after a scan, no threats were discovered on the device)
Time: An epoch timestamp and mandatory field.

SENDING MESSAGES TO MALDA
You can send detections to the api in the following JSON format:
{"uuid":"YOUR-DEVICE-UUID", "type": "DEVICE-TYPE", "model":"DEVICE-MODEL", "osVersion":"DEVICE-OS-VERSION", "detections": [{"uuid":"DETECTION-UUID", "type": "DETECTION-TYPE", "time":"DETECTION-TIMESTAMP","nameOfApp":"APP-NAME","typeOfApp":"APP-TYPE"}] }

For example, to send a NEW dection of a ransomware in an IOS, one can execute the following command in a terminal:
$curl -v -X POST localhost:8080/detection -H 'Content-type:application/json' -d '{"uuid":"1e575616-a986-400a-a37d-4da13452b4c9", "type": "IOS", "model":"iPhone 12 Pro Max", "osVersion":"iOS 14.2", "detections": [{"uuid":"63c65447-f890-436e-8b9f-1498412d7bde","type":"NEW","time":"2022-05-24T17:29:56.740+00:00","nameOfApp":"RYUK","typeOfApp":"Ransomware"}]'

QUERYING
Searchs in Malda are done by Example, which means it accepts the same JSON fields we used during creation as query parameters. 
E.g., if we would like to search for all messages sent from IOS devices we can send a GET message to:
http://localhost:8080/device/search?type=IOS

And if we would like to see all the RESOLVED detections of ANDROID devices, we can send a GET message to:
http://localhost:8080/detection/search?type=RESOLVED&device.type=ANDROID

