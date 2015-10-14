The instructions say to upload a single jar and to upload two jars, so I just did it how I thought was best and made two Idential jars with a different default class in the manifest of each. Client jar defaults to running the client, server jar defaults to running the server, but they have the same classes. I also used args4j since I figured the point of the assignment wasn't implementing tedious command line parsing.

Running the jars without any parameters will tell you how to use them. 

Jars can be executed with the following commands in the jar directory; 
"java -jar OTPClient.jar" 
"java -jar OTPServer.jar"

To run compiled bytecode without jar with args4j references in it, it must be included in the classpath.

"java -cp .;args4j-2.32 OTPClient"

Generated javadocs are also included.
