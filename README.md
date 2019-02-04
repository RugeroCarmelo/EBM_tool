# Evidence Based Management tool

Recommendations are processed using the camunda dmn engine(https://camunda.com/). The ontology is displayed using OWL2Prefuse (http://owl2prefuse.sourceforge.net/).
To try the tool click the file menu and open the test file in the data folder

## Getting Started
### Prerequisites
- [Java](https://www.java.com/en/download/)
- [Maven](https://maven.apache.org/) 
  - [installing maven](https://www.mkyong.com/maven/how-to-install-maven-in-windows/)
  
### Running
After the prerequisites are installed open the command prompt, change to the cloned folder from githut and run the following commands:
```sh
mvn clean package
```
to package the project into a jar
```sh
cd target
```
to change to the directory with the jar
```sh
java -jar EBM_tool.jar
```
to run the program

### Using the Tool
To make use of the tool you first need to have a data file made using the [EBM_ruleManagement](https://github.com/tom277/EBM_ruleManagement) protege plugin. There is a sample data file called 'testData.per'. To open the data file go to File -> Open and choose the desired file. You can now browse the ontology by pressing on the concepts displayed on the window on the left. Information and rules for this concept will be displayed on the right.

## New Features
-

## Todos
- 


## License
This project is licensed under the MIT License
**Free Software**
## Acknowledgments
Open source projects and libraries which were used to make this project better

- [OWL2Prefuse](http://owl2prefuse.sourceforge.net/) (used to display the ontology)
- [Camunda](https://camunda.com/) (used to run the rules and get the recommendations)
- HOANG THUAN, N. (2016). ESTABLISH CROWDSOURCING AS AN ORGANISATIONAL BUSINESS PROCESS: A DESIGN SCIENCE APPROACH. Ph.D. Victoria University of Wellington. -- For sample onltology and rules
