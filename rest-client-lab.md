# Consuming services with RestClient 

In this exercise, you will configure a Payara Micro application to allow it to consume an external service. 

For this scenario, you will use the existing JAX-RS service, `restaurant`, and your client application will write the code to allow consuming the five apis available in the service:

* List all the items
* Insert new items
* Update items
* Delete Items

## Guides

You have an application with existing classes to support the development of your client application. Although, all the RestClient configuration and implementation is missing. Your task is to finish this application and make sure you can interact with the [restaurant service](https://github.com/otaviojava/payara-fastlane-labs/tree/main/restaurant).

1. The first thing you should do, is clone the repository https://github.com/otaviojava/payara-fastlane-labs to your machine. If you don't have it yet, you can clone it using:

   ​	`$ git clone https://github.com/otaviojava/payara-fastlane-labs`

2. Inside the folder `payara-fastlane-labs`, you'll find the project `restaurant-client-lab`. Open the project `restaurant-client-lab` in your IDE of choice. 

3. Understanding the project: 

   1. This project is a JSF project and it has a `hello.xhtml` page that will consume the `RestaurantBean`. 
   2. The `RestaurantBean`  retrieves the list of items using the injected bean of type `Restaurant Service`.  

4. The `RestaurantService` interface can be configured as a RestClient in order to consume the external API exposed by [Restaurant Controller in the Restaurant service](https://github.com/otaviojava/payara-fastlane-labs/blob/main/restaurant/src/main/java/my/compary/restaurant/RestaurantController.java).

5. Open the `RestaurantService` interface and let's finish the implementation of this interface for all five methods.

6. To configure this interface as a RestClient, on lines 20 and 21, insert the annotations `@Path("restaurants")` and @RegisterRestClient .

7. The next step is to create the contract of the method you will consume on the external service. For example, the `RestaurantController` on the **external service** side, has the following method: 

   ```java
   @POST
   public Response insert(@Valid Item item) {
       return Response.status(Response.Status.CREATED)
       .entity(repository.save(item))
       .build();
   }
   ```

   So, in order to consume this method, you should create in **your Interface**, the following method declaration:

   ```java
   @POST
   public Response insert(Item item);
   ```

8. Implement in your `RestaurantService` interface method declarations to allow consuming all five methods of the external service: `findById`, `insert`, `update`, `delete` and `getAll`. 

9. The next step is to create a MicroProfile Configuration in the `resources/META-INF/microprofile-config.properties` folder to configure the external service URL. Add the following property:

   ```properties
   my.compary.restaurant.RestaurantService/mp-rest/url=http://localhost:8080/
   ```

**Testing the service:**

To test this service you will need to run two applications, the external service and the client service. To do so, each service will run in a different port.

1. In one terminal tab, access the folder `payara-fastlane-labs/restaurant`.

2. Package the project and start it on port 8081:

   ```shell
   $ mvn clean package
   $ java -jar -Djava.net.preferIPv4Stack=true target/restaurant-microbundle.jar  --port 8080
   ```

3. On another terminal tab, access the folder `payara-fastlane-labs/restaurant-client-lab`.

4. Package the project and start it on port 8081:

   ```shell
   $ mvn clean package
   $ java -jar -Djava.net.preferIPv4Stack=true target/restaurant-microbundle.jar  --port 8081
   ```

5. Access your client app on URL: [http://localhost:8081](http://localhost:8081/hello.xhtml). There's nothing listed.

6. Now, use the JAX-RS rest service endpoint to insert two items:

   ```shell
   curl --location --request POST 'http://localhost:8080/restaurants' \
   --header 'Content-Type: application/json' \
   --data-raw '{"name": "water", "description": "Water appears as a clear, nontoxic liquid composed of hydrogen and oxygen, essential for life.", "type": "BEVERAGE", 
   "expires": "2025-12-03", "ingredients": [{"name": "water", "unit": "L", "quantity": 2}]}'
   
   curl --location --request POST 'http://localhost:8080/restaurants' \
   --header 'Content-Type: application/json' \
   --data-raw '{"name": "coconut-water", "description": "Coconut water is the clear liquid inside coconuts.", "type": "BEVERAGE", 
   "expires": "2025-12-03", "ingredients": [{"name": "coconut water", "unit": "L", "quantity": 1}]}'
   
   ```

7. Go back to your browser and reload the page. You should be able to see the two items being listed.

## Conclusion

Congratulations, you've implemented a client application that consumes a JAX-RS service by using the RESTClient MicroProfile configurations!