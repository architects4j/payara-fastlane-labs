# Creating an entire RESTFul Java application with Payara Micro 

In this final challenge, you will create a RESTful Java application from scratch, using Payara Micro. Let's take a look at the use case and the exercise goals. 

## Scenario

You were recently hired by a small company Acme Store. The company is new to the online world and they have a technical team that want to evaluate what a Java service would look like in case they wanted to start an e-commerce. 

Your first task is to create a sample webservice using Java programming language, and based on the JAX-RS specification. They will evaluate whether they should move foward using Java technologies, using your MVP as a starting point. They've heard of MicroProfile and are interested in a solution with Payara Micro.

### Goals

Here are the pre-requisites of the first delivery of this application:

* The new application should be called `acme-store-service`. 
  * It needs to use Microprofile 4.0 and Payara Micro.
* The goal is to work with products. The `Product` should have:
  * The ID is the `name` : mandatory and should be at max 100 chars.
  *  A `description`:  mandatory, should have at least 5 chars
  * A `quantity`: mandatory and should be higher than 0. 
* It should allow all the CRUD operations for a `Product`:
  * List all the products
  * Insert a new product
  * Retrieve a product by ID
  * Update a product based on its ID
  * Delete a product using its ID
*  It should follow REST patterns. The urls should follow these rules:
  * To list all products:  GET "/products/"
  * To find a product by ID: GET "/products/{productName}"
  * To delete a product: DELETE "/products/{productName}"
  * To update a product: PUT "/products/{productName}"
  * To insert a product: POST "/products/{productName}"
* Document the API using [Open-API](https://swagger.io/specification/) using [Eclipse Microprofile-Open-API](https://github.com/eclipse/microprofile-open-api)
* 

# Implementing the project

We recommend you to try creating the project from scratch. If you need, you can take a look at the [restaurant example](https://github.com/otaviojava/payara-fastlane-demos/tree/main/restaurant) demonstrated by the instructor.

Next, you can also find some tips.

**Creating the project**

1. You can use the [microprofile starter](https://start.microprofile.io/) to create your Payara Micro application based on Microprofile 4.0. You don't need to select any of the "Examples for specifications". 

2. You will need to create the POJO `Product.java` with the described attributes.

3. You don't need database persistence. You can use in-memory repository. To get started faster, you can create a `ProductRepository` interface, and an implementation of it, the `ProductRepositoryMemory`. You can use the [RestaurantRepository](https://github.com/otaviojava/payara-fastlane-demos/blob/main/restaurant/src/main/java/my/compary/restaurant/RestaurantRepository.java) and [RestaurantRepositoryMemory](https://github.com/otaviojava/payara-fastlane-demos/blob/main/restaurant/src/main/java/my/compary/restaurant/RestaurantRepositoryMemory.java) as an inspiration.  

   1. Notice the CDI scope defined for the [RestaurantRepositoryMemory](https://github.com/otaviojava/payara-fastlane-demos/blob/9873e5b848389770a679ecd9634561849bec67e3/restaurant/src/main/java/my/compary/restaurant/RestaurantRepositoryMemory.java#L11) bean. 

4. You will need a `ProductController` to implement your REST APIs. 

   1. To best fit this application need, it can be configured with `@RequestScope`. 

   2. Remember to set the `javax.ws.rs.@Path` according to the goals of the project;

   3. You can use `@Inject` to use your `ProductRepository` and manipulate the data.

   4. Make sure to avoid any exceptions in case an invalid data is sent to your endpoints.

   5. To differenciate your APIs, you can annotate your methods with: `@DELETE`, `@PUT`,  `@POST` and `@GET` from `javax.ws.rs`.

   6. To create an endpoint like `/products/{productName}`, you can annotate the respective method with `@Path("{id}")` and make sure you add a @pathParam to your method. Example:

      ```Java
      public Product findById(@PathParam("id")String id) {}
      ```

**Field validation**

To implement the business rules related to the product attributes, you can use Bean Validation annotations on your `Product.java`. 

1. To do so, you need to add the following dependency in your `pom.xml`

```xml
        <dependency>
            <groupId>jakarta.validation</groupId>
            <artifactId>jakarta.validation-api</artifactId>
            <version>2.0.2</version>
            <scope>provided</scope>
        </dependency>
```

2. You can use the annotations `@NotBlank` and `@Size` from `javax.validation.constraints` on each attribute to define the rules and error message you want to associate with it.

**Running your application**

* To test your application, you can package it with Maven, run with Java, and use Postman, curl, or other tool of preference to invoke the rest APIs. 

* To package your application:

  ```bash
  $ mvn clean package
  ```

* To run your application: 

  ```shell
  java -jar -Djava.net.preferIPv4Stack=true target/acme-store-service-microbundle.jar
  ```

* By default it will run on port 8080. So you should be able to test the application with the following request examples:
  * Insert a new product

    ```shell
    curl --location --request POST 'http://localhost:8080/products' \
    --header 'Content-Type: application/json' \
    --data-raw '{"name": "bottle", "description": "Can store cold and hot liquids.", "quantity": "2"}'
    ```
    
  * List all products
  
    ```shell
    curl --location --request GET 'http://localhost:8080/products'
    ```
  * Search a product by ID (name)
  
    ```shell
    curl --location --request GET 'http://localhost:8080/products/bottle'
    ```
  * Update a product using its ID (name)
  
    ```shell
    curl --location --request POST 'http://localhost:8080/products/bottle' \
    --header 'Content-Type: application/json' \
    --data-raw '{"name": "bottle", "description": "Can store ONLY cold liquids.", "quantity": "2"}'
    ```

  * Delete a product by ID (name)
  
    ```shell
    curl --location --request DELETE 'http://localhost:8080/products/bottle'
    ```

If you can successfully execute the above requests, it means your endpoints are working! The next step is to try data that actually breaks the business rules and validate if your application is validating for example, the required fields.

# Congratulations!

You've finished your first task in the Acme Store! You have created the base project that will be later used to persist the e-commerce data using a database, will have properly documented APIs, and much more! We're looking forward to have you helping  with the next task!
