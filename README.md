# Gatling    

Open source library written in Scala based on Akka and Netty.
Supports Java and Kotlin as well.   
Main advantage against e.g. JMeter - flexibility - more programming-wise.   
Generates nice html reports after test (simulation) execution with gatling-test (for maven).    
Based on virtual users concept (by default 1 user = 1 connection, but can be configured for shared connections).   
Each user is bound to Session. Session is basically a state placeholder, where testers can inject or capture and store data. 
Provides DSL in order to build all main counterparts.  
Documenation for current version can e found - [here](https://gatling.io/docs/gatling/reference/current/)
______________
## Purpose
* Load tests
* Stress tests
There are 2 different strategies for load tests:
1. opened - allow new user unless application blows up
2. closed - when number of users is defined and if exceed threshold is buffered to queue
______________

## Counterparts
* Simulation - aka unit test (description of the load test); entry point.  
* Scenario - chain of logically connected steps
* Step (ChainBuilder) - usually 1 or  several connected requests (executional steps)
* feeder - input data source
______________

## Configuration
* gatling.conf
* own configuration via ConfigFactory (e.g. load properties file with some user defined configuration)
* system properties (java options)
All default options can be found [on github](https://github.com/gatling/gatling/blob/main/gatling-core/src/main/resources/gatling-defaults.conf#L78)
_____________

## Features/extensions
Apart from widely used HTTP also supports the following protocols:
* WebSocket
* JMS
* MQTT (enterprise edition)
____________

## Data input - Feeders
User can define data for execution steps with own data.  
Can be defined on all levels - from step to simulation.    
Supports different data sources:
* text files
* csv
* json (both local file system and API response)
* jdbc
* jms
* redis
* kafka ?
* and others
