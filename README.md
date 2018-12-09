# Olympics History Graph

**Overview**
--------------------------------------------------------------------------
Represent 120 years Olympic History as Graph.  Participation and results from every Olympic event 
 over 120 years is represented in graph form. Nodes include games, events and athletes. Relationships are 
 athlete participation in events and events hosted by games.
 
 ***Primary Technologies***
 * [Spring Boot 2](https://spring.io/projects/spring-boot) Web application for REST endpoints and data load
 * [Neo4j Database](https://neo4j.com/) to persist data in graph form
 * [Spring Data Neo4j](https://spring.io/projects/spring-data-neo4j) for object graph model
 * [Apache Groovy language](http://groovy-lang.org/) for expressiveness and Spring/Java integration
 * [D3.js](https://d3js.org/) to visualize graph of Olympic games, events and athletes

 ***Support Technologies***
 * [Gradle](https://gradle.org/) to build application
 * [Spock Framework](http://spockframework.org/) for automated testing
 * [GroovyCSV](https://github.com/xlson/groovycsv) as library to parse CSV data
 * [Java Platform, Standard Edition 8](https://www.oracle.com/technetwork/java/javase/overview/index.html) to run Spring Boot and Groovy
 
 _all technologies are open source_

**Start Neo4j**
--------------------------------------------------------------------------
First start Neo4j database in a terminal

    $neo4j console

**Start Application**
--------------------------------------------------------------------------
Run Spring Boot application

    $./gradlew clean bootRun

**Loading Data**
---------------------------------------------------------------------------
Data files are located in `src/main/resources` in csv format. The file `olympic_history_120_years.csv` is the complete dataset
To load data when the application starts modify setting `src/main/resources`:
 
    olympics.load-data=true
    olympics.data-file-name=sample.csv

**Testing**
--------------------------------------------------------------------------
Execute automated tests

    $./gradlew clean check

**Data Source**
--------------------------------------------------------------------------
Historical dataset of 120 years Olympic history is available on kaggle.com

[120 years of Olympic history: athletes and results](https://www.kaggle.com/heesoo37/120-years-of-olympic-history-athletes-and-results)

_Special thanks to Randi H Griffin and contributors to www.sports-reference.com for creating and curating this dataset_