# Contexts and Dependency Injection (CDI) Lab

## Quick introduction to CDI

[Contexts and Depedendency Injection (CDI)](http://cdi-spec.org/) is an specification included in Java since version 6. It brings loose coupling between components of the application and makes it easy for developers to manage stateful object instances and connect together different layers of the application. 

When getting started with CDI, some of the important concepts to be aware of are: Beans, Dependency Injection, Qualifiers, Scopes, Interceptors and Events. For detailed explanation and examples, you can check [CDI's user guides](https://docs.jboss.org/cdi/learn/userguide/CDI-user-guide.html). 

A managed bean, according the the [CDI's user guides](https://docs.jboss.org/cdi/learn/userguide/CDI-user-guide.html#_what_is_a_bean), can be defined as: *"(...) container-managed objects with minimal programming restrictions, otherwise known by the acronym POJO (Plain Old Java Object). They support a small set of basic services, such as resource injection, lifecycle callbacks and interceptors. (...)"*.

Using CDI, developers can manage stateful components  lifecycle. There are in total five different scopes that can be used, being @Dependent, @Conversation, @RequestScoped and @ApplicationScoped. See below explanation for the default option, and other two comonly used options:

- The default scope for a bean (in case none is configured), will make it last the same time as its client bean. (`@Dependent`)
- An HTTP request lifespan, say, in a web application. (`@RequestScoped`)
- All the interactions with an application, including different users' interaction and requests. (`@ApplicationScoped`)

TIP: To see more about application scopes, you can check the Java official documentation in Oracle's page: [Using Scopes](https://docs.oracle.com/javaee/7/tutorial/cdi-basic008.htm#GJBBK). 

-----

## Goals

Here are the main goals you will achieve with this hands-on execise:

* Learn how to use the reference implementation of the CDI specification, [Weld](https://weld.cdi-spec.org/). 
* Use an important class in the the CDI world, `javax.enterprise.inject.se.SeContainer`. This is the class that provides access to the current container in Java SE applications. 
* Try out scopes and test the the injection of different objects and its behavior;
* Practice the concept of observers and event listeners; 

## Pre requisites

* Java 11 (JDK)
* IDE of choice
* Git client
* Maven

## Exercise

Let's get started with the exercise with a series of steps that will guide you through completing the implementation of a project.

### Adding CDI dependency

1. The first thing you should do, is clone the repository https://github.com/otaviojava/payara-fastlane-labs to your machine.

   ​	`$ git clone https://github.com/otaviojava/payara-fastlane-labs`

2. Inside the folder `payara-fastlane-labs`, you'll find the project `cdi-lab`. Open the project `cdi-lab` in your IDE of choice. 

3. Open the `pom.xml` file. In the `<dependencies>` section, line 21, add the dependency to Weld. Weld is reference implementation of the CDI specification.

```xml
<dependency>
    <groupId>org.jboss.weld.se</groupId>
    <artifactId>weld-se-shaded</artifactId>
    <version>${weld.se.core.version}</version>
</dependency>
```

4. Notice the version is already configured in `pom.xml`, in the property `weld.se.core.version`.

4. Build and install the application using maven command line or your IDE:

   `$ mvn clean install`

Your project isn't going to compile yet, due to other unfinished code. Let's move on to the next step.

### Contexts and objects behaviors

Now, let's start practicing with the Vehicle example demonstrated by the instructor. You will finish the project implementation, run it and analise it yourself.

1. Using your IDE of choice, open the Class `App1.java`. In the next steps, we'll update this class where: a new container should be created, and we will use it to inject an instance of an object that inherits the Interface `Vehicle` and an instance of an object based on the class `Car`.

2. **Instantiating an SeContainer**:

   1. Locate in the `App1.java` the line 15. 
     ```Java
             try (SeContainer container = null) {
     ```

   2. This code is incorrect, since container should not be initializated with null. To fix this code, replace the "null" initialization with an intialization done with the class [SeContainerInitializer](https://docs.jboss.org/cdi/api/2.0.EDR2/javax/enterprise/inject/se/SeContainerInitializer.html). 
      **TIP**: To use the `initialize()` method, you should first create a `newInstance()` if the `SeContainerInitializer`.

   3. Make sure the project is compiling and you can run `mvn clean install` without errors.

      **TIP:** If you try to run the application you will get a RuntimeException, `NullPointerException` because there is still code that needs to be initialized correctly.

3. **Obtaining existing object instances in the SeContainer**:

   1. In the class `App1`, locate the object car initialization on line 23. 

      ```java
      Car car = null;
      car.move();
      ```

   2. An object car may already exist in the container. If so, there is no need to create a new instance of this class. Use the container to obtain the existing car instance (if existing). The great thing about the CDI API is that by default, if no object instance is located in memory, it creates a new instance and retrieves it.

      **TIP:** Check how the Vehicle is being obtained from the container. Be sure to obtain an instance of the `Car` class, and not the interface `Vehicle`.

   3. Make sure the project is compiling and you can run `mvn clean install` without errors.

4. **Testing your code:**

   1. Now, execute the main method of the class `App1`. 
   2. Confirm you see a message output in the logs like: *"Is the same vehicle? true"*

5. Question: In this code, we use CDI to obtain an instance of a Vehicle and then, of a Car. Why does the CDI API return the same object?

6. **CDI Scope:**

   1. Open the class `Car`.

   2. Locate the declaration of this bean's scope, on line 8:

      ```java
      @ApplicationScoped
      public class Car implements Vehicle {
      ```

   3. Comment the annotation `@ApplicationScoped` and save the file. The class should look like:

      ```java
      // @ApplicationScoped
      public class Car implements Vehicle {
      ```

   4. Now, run your code again and check the output. 

7. Question: Did you get a different output message? Why changing the scope of the Car bean changed the behavior of our code? 

### Producers and Consumers

When working with Java code, we can leverage the CDI API to create classes that will behave and producers and consumers. In other words, instead of invoking a behavior of a specific class, we can instead create decoupled code that reacts to specific events in the container. Let's see this in action by trying out the news example demonstrated by the instructor.

1. Open the project `cdi-lab` in your IDE of choice. In this project we have the package `my.compary.cdi.lab.news`  with the classes:

   1. `Journalist`  class that is a producer responsible for notifying the news to every consumer in the project;
   2. There are three consumers in this project:  `Magazine`, `Newspaper`, and `SocialMedia` .

2. Open the `Journalist.java` class, and:

   1. Analyse the different CDI annotations, like `@ApplicationScoped` and `@Inject`. 
      1. Question: What is this "Event" class and what can you use it for?
   2. On the line 14, inside the method `receiveNews` , fire events containing the news, using the injected object `event`.

3. Open the `Magazine`, `Newspaper`, and `SocialMedia` classes.

   1. Notice it implements the interface `java.util.function.Consumer`. 
   2. Fix the method `accept`, by making it react to events that were fired in this application scope. 
      **TIP:** You may want to use the anotation `@javax.enterprise.event.Observes`;

4. Finally, open the class `App4.java` and analyse it.

5. Validate if you did everything right by running the main method in `App4`. You should see in a log output for each observer (three in total), containing messages like: "We got the news, we'll publish it on Social Media: Java 17 has arrived!!".

   ```
   INFO: WELD-ENV-002003: Weld SE container 724cb2e4-edb7-4de5-8b0f-df2adc814bb3 initialized
   Oct 10, 2021 8:52:36 PM my.compary.cdi.lab.news.SocialMedia accept
   INFO: We got the news, we'll publish it on Social Media: Java 17 has arrived!!
   Oct 10, 2021 8:52:36 PM my.compary.cdi.lab.news.NewsPaper accept
   INFO: We got the news, we'll publish it on a newspaper: Java 17 has arrived!!
   Oct 10, 2021 8:52:36 PM my.compary.cdi.lab.news.Magazine accept
   INFO: We got the news, we'll publish it on a magazine: Java 17 has arrived!!
   Oct 10, 2021 8:52:36 PM org.jboss.weld.environment.se.WeldContainer shutdown
   INFO: WELD-ENV-002001: Weld SE container 724cb2e4-edb7-4de5-8b0f-df2adc814bb3 shut down
   ```

6. Question: Why these messages were logged, if we never invoked the method `accept` in those classes (e.g. `magazine.accept(news)`)?

# Conclusion

There are several ways to use CDI in Java applications. In these exercises you could practice some of the features that can be leverage in application where you can use CDI. 

You also configured and used the specification through Weld, the reference implementation of the specification, but there are several others where each vendor provides their own implementation of the CDI spec.

To know more about CDI, we recommend studying a trusted source of knowledge like the [CDI's user guides](https://docs.jboss.org/cdi/learn/userguide/CDI-user-guide.html). 

