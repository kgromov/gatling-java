# Gatling    

Open source library written in Scala based on Akka and Netty.
Supports Java and Kotlin as well.   
Main advantage against e.g. JMeter - flexibility - more programming-wise.   
Generates nice html reports after test (simulation) execution with gatling-test (for maven)
______________
## Purpose
* Load tests
* Stress tests
______________

## Counterparts
* Simulation - aka unit test; entry point
* Scenario - chain of logically connected steps
* Step (ChainBuilder) - usually 1 or  several connected requests (executional steps)
______________

## Configuration
* gatling.conf
* own configuration via ConfigFactory (e.g. load properties file with some user defined configuration)
* system properties (java options)
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