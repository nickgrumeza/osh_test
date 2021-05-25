# Name

Optimal Solution Hub Test task to apply for the job 

## Description

Within the project was realized reading data from .Csv file, sorting it by the count of columns, saving correct data to In-memory database, saving incorrect data to "bad-data-*timestamp*.csv" file and realization the .log file to see the results of the work of the programm. 

Libraries and frameworks used: 
    1)SuperCSV - One of the most popular library for working with .csv files.
    2)SQLite - The most used database engine in the world.
    3)Apache Maven - A software project management and comprehension tool.

At the very beginning I thought the project wont lack a much time to complete, but once I started learning about the problem, some problems appreared. 

First of all I started learning about the SQLite database because database architecture is one of the most important steps on project lifecycle. By that moment I already had some experience with SQL, but I was working in SQL Server Management Studio so working with database directly from the IDE via programming on Java was something different for me.

Secondly I started learning about the process of working with .csv files. After a little research I found out that SuperCSV library is one of the best tools I could use. Due to the lack of experience on programming on Java it was a tought challenge for me to understand the principles of working with such elements as tokens, Pojo classes and different interfaces such as ICsvBeanReader, ICsvListWriter, etc. One of the biggest problems was handling incorrect rows withing csv file I got because the cell-processor that is used to read the file could not manage with the dynamic number of columns if their number exceeded the stated number, so I had to extend the tokenizer for handling this problem. Fortunately I managed to complete the tasks and I feel the deepest satisfaction from the work done.

After all I wrote some test functions to see if programm is working correctly and worte a little function of creating .log file.

## Installation

For compiling the project Apache Maven framework was used. 
I order to run and compile the code programmer should install the Maven.
Than build the project via writing "mvn package" command in command line while being in project folder.
Atfer all run compiled and packaged JAR file. 