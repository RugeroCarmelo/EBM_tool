# EBM tool

Recommendations are processed using the camunda (https://camunda.com/). The ontology is displayed using OWL2Prefuse (http://owl2prefuse.sourceforge.net/).

## Getting Started
### Prerequisites
- [Java](https://www.java.com/en/download/)
- [Maven](https://maven.apache.org/) 
  -[installing maven](https://www.mkyong.com/maven/how-to-install-maven-in-windows/)
  
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


## New Features
-

## Todos
- 


## License
This project is licensed under the MIT License
**Free Software**
## Acknowledgments
Other open source projects and libraries were used to make this project better

- [OWL2Prefuse](http://owl2prefuse.sourceforge.net/) (used to display the ontology)
- [Camunda](https://camunda.com/) (used to run the rules and get the recommendations)
