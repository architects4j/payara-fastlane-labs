# Creating a RESTFul Java application with Payara Micro 

In this exercise you will create a RESTful Java application from scratch, using Payara Micro. Let's take a look at the use case and the exercise goals. 

## Scenario

You were recently hired by a small company Acme Store. The company is new to the online world and they have a technical team that want to evaluate what a Java service would look like in case they wanted to start an e-commerce. 

You were given the task to create a sample webservice using Java programming language, and based on the JAX-RS specification. They will evaluate whether they should move foward using Java technologies, using your MVP as a starting point. They've heard of MicroProfile and are interested in a solution with Payara Micro.

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

