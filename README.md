The project demonstrates below aspects:
1. Building REST end points using spring MVC
2. REST conventions.
3. Global Error Handling.
4.  Spring data.
5.  JPA/Hibernate.
6. Spring AOP.
7. Unit testing using H2.
8. Integration testing.


The project consists of :
-entities
-repositories
-services
-controllers
-advice

Running project:
just run ./mvn clean install to build the project

once ready go to target folder and run the command java -jar transationApp-0.0.1-SNAPSHOT.jar

Please find my understanding below.

For /transaction POST api, as per my understanding we have 4 condition 
->1 In case of success
When Transaction will get saved sucessfully , we will get an empty body with HttpStatus 201

->2 In case of transaction timestamp is in future
When we are trying to save a transaction having timestamp less than 60 seconds than current time, we will get an empty body with HttpStatus code 204


->3 In case of JSON is invalid
When we are trying to save a transaction having invalid JSON format, we will get an empty body with HttpStatus code 400

->4 any of the fields are not parsable or transaction date is in future.
fields are not parsable means incase we are passing amount which is not parsable.
transaction date is in future means we are trying to save a transaction having timestamp greater than current time
Fot both cases we will get an empty body with HttpStatus code 422


For /transaction DELETE api, as per my understanding we are deleting all existing transation  
->1 In case of success
When Transaction , we will accept an empty request body and  return 204 status code


For /statistics GET api, as per my understanding we are fetching all transaction ,whose timestamp value and current timestamp diffrence
is less or equal than  60 seconds  transcation and performing sum,avg,min,max and count operation on that list of transction.
