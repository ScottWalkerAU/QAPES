# QAPES
Quadratic Assignment Problem, an Enhanced Solver

A genetic programming framework for the Quadratic Assignment Problem (QAP) done in completion for my Honours thesis in Software Engineering.

All instance files have been sourced from [QAPLIB](http://anjos.mgi.polymtl.ca/qaplib/), everything else is original work.

## Usage
### Installation
Simplying run the maven task `mvn compile install`

### Execution
```
usage: QAPES (Required: -i, -p)
 -i,--instances <arg...>  Instance names separated by commas
 -l,--load <arg...>       Saved algorithms to load separated by spaces
 -p,--population <arg>    Population type. Accepts 'list' or 'tree'
 ```
 
 ## Modifications
 This project uses maven, and within that it uses Log4j2 for logging utilities. If you have errors in your IDE you will need to install the [Lombok plugin](https://projectlombok.org/)
