Prerequisites: Java 8 JDK(and tools) and Maven need to be installed
File format: The attached file jml.txt is in fact a jar'red file
             Please save to disk at a location of your choice.
             Change name of file from jml.txt to jml.jar
             Un-jar file with following command from a shell
                   jar xvf jml.jar
                This will created a directory jml.
                Change to directory jml
                Issue mvn clean install

Project:
    Simple maven project with a dependency on JUnit.
    To build and run unit tests:
         mvn clean install
    
    Starting point:
        No main supplied for test.
        Hard coded limit on cache size imposed to 10000 Sale records.
        Accuracy of BigDecimal in doubt resorted to using Double.
        Accuracy of Double may still be an issue.
        No logging code provided so as to limit external jar dependencies.
        Main point of entry in class MessageControllerImpl.acceptMessage()
        Used Optional<..> judiciously
        
        
