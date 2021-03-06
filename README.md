# New Relic Coding Challenge

Solution

## Assumptions ##
- The text or file provided won't have words that will be followed by_ (bug on the reguler expression)
- Considering the following the chars as being used by contractions ( '’‘ ) 

## Build

### Requirements 

- Java JDK version >=1.8 <=11.x

### How to build the project and generate a jar

- Build project.

```
$ ./gradlew build
```

- Create shadow JAR file.

```
$ ./gradlew shadow
```

### Run Application 

- Run application from jar file generated on the previous step. (Please use java versions between 8 and 11)

- For file input please run: 

```
java -jar ./build/libs/NRchallenge.jar moby_dick.txt 
``` 

- For Stdin input please run: 

```
cat moby_dick.txt | java -jar ./build/libs/NRchallenge.jar 
```

- Once application has finished processing succesfully you will see the list of 3 words sequences and its respective counts, for example:

```
The count will be done over the stdin input
of the whale - 93

the sperm whale - 89

the white whale - 76
```

#Run Tests

The small subset of tests was included to test parts of the functionalities

```
./gradlew clean test

> Task :test

com.nr.challenge.ParserTest > TestFileParserInvalidFile PASSED

com.nr.challenge.ParserTest > TestUnwantedCharsContractions2 PASSED

com.nr.challenge.ParserTest > TestUnwantedCharsTrim PASSED

com.nr.challenge.ParserTest > TestStringParser PASSED

com.nr.challenge.ParserTest > TestUnwantedCharsContractions PASSED

BUILD SUCCESSFUL in 2s
3 actionable tasks: 2 executed, 1 up-to-date

```

## What could have been done better ##
- BUG -> Regular expression could be tuned to handle all special chars. (related to words that start or end with _)
- BUG -> There is no continuity when there is more than 1 file passed as arguments. So in this scenario, it is not considering the last sequences of words when a file ends and another starts (it is working as expected when the input comes from stdin)
- Performance could have been improved.
- There could be more unit tests (including one to validate unicode chars).  

