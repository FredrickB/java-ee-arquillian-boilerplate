# Why?
Created to reduce time and frustration usually found with setting up development and testing environment
for Java EE web applications.

Project provides a minimal footprint boilerplate that allows for jumping straight into development.

Includes basic example of integration testing EJB's with Arquillian as test runner and embedded Glassfish
as the chosen container.

This is by no means the best setup, but serves as a minimal simple starting point.

## Requirements
- Java 8
- Maven

## Development
Spin up embedded wildfly with `mvn wildfly:run`


## Testing
Tests can be ran with `mvn verify` or setup a run configuration in IDEA (screenshots provided). 

### When configuring for IDEA
- Ensure your package specification is high enough in the hierarchy to include all tests. 
Specify this in the field "Package" (shown in "idea_container_configuration_run_configuration.png").