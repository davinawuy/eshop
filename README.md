<h2>Reflection 1</h2>

I have learned alot about clean code practices from the slides and lectures. Many practices I found most practical was naming conventions. I also applied the idea of separation of concern through Spring Boot's separation of concern. Finally, I also applied SRP or single responsibility functions to isolate each function or need to a single function to ease development. 

As for security practices I believe that the Thymeleaf aspect provides some form of encoding protection. Other than that in the getters and setters there are basic access restriction so that objects are not easily modified.

There are some points of possible improvement in the code such as the findid function could have better exception handling incase it is not found. I should also implement better validation upon updates to ensure the inputted value is valid.

<h2>Reflection 2</h2>

Creating the unit test was very comforting since it showed that my code was working as I intended it to. At times the unit and functional test would be able to catch unintended interactions. This is often frustrating but after sorting out the issue it was good to know that the code worked well.

I do not know if there is a specific rule that determines how many tests should be in each class or function. That being said, I think the bare minimum would be to have at least one case to handle intended interactions and one to handle non-intended interactions. But it depends on what is being tested. For example a function could use 2 but something more complex with many possible ways to fail could need more. 

After learning about code coverage, I do not believe my code has been tested enough to reach a 100% coverage. Inspite of that I do not think that a 100% code coverage automatically gurantees flawless code. It just means that each line has been run or tested. Therefore, there is still room for errors and unintended interactions.

I think if the setup is the same, it may be possible to isolate that into its own class to prevent repetition in code, which should reduce duplicated code. From a reading perspective, I think that modifying only a section while keeping most of it the same may actually cause confusion in the long run. Practices like that may make it hard for me and others to discern which part of the code is different. The best way to help prevent redundant code and readability issues is to implement a base class where the setup is stored. To help readers read the code it maybe logical to implement meaningful naming conventions so that it is easy to understand what the code does.

<h2>Reflection 3</h2>

There were 3 main types of issues I fixed in terms of code quality they ranged from High to Low in severity. The first I prioritized was the high severity cases which was the nested comments for empty methods. To fix this it was really easy I just justified why I needed the function in the first place and that resolved it.
The second issue I fixed was hardcoded versions in the build.gradle. To fix this I created a lib in the settings of the gradle to hide the versions for security reasons. Then I had an issue with field injections which I resolved very simply by adding constructors and self-declaring inside the class. The last issue was with the functional test having pointless exceptions. I did however leave one code quality issue and that is using mockbean.
I understand its status as depreciated but I have decided it is needed for the test cases.

I do believe my implementation of the CI/CD does align with the ideas as I have automatic code testing, automatic deployment on master branch, and automatic code scanning to create a full CI/CD pipeline.
The first reason would be the application of code testing in the ci.yml also with the code quality checks from sonarcloud. My workflow automatically deploys to Koyeb through actions and automatically by Koyeb whenever I push to master branch. Finally Sonarcloud and OSSF scoreboard help check code quality and security.
These actions all apply concepts of CI/CD since the tasks are automatically executed after every push and ensure the quality and reliability of the code. I have also integrated Jacoco with Sonarcloud to get better communication on the code coverage which I have aimed to a 100%. Since I have used meaningful names and clear implementation my wokflows are very readable.

Link to Deployment: https://fixed-panther-davinawuy-faa99f67.koyeb.app/

<h2>Reflection 4</h2>

In this project I managed to apply three principles from the SOLID concept to my project. These were SRP (single responsibility principle), OCP (open/closed principle) and DIP (dependency inversion principle. I applied the SRP when splitting the controller since it began with two controller in one file. This made that file contain two classes and this is not optinal since it maybe prone to unintended changes and issues. Therefore, splitting them was super optimal since it put the responsibility of the controllers to individual files instead of one. I applied DIP by creating an inteface for my repositories. This will help in the code that implement repository since they will use the interface and not the concrete implementation. Finally, I used OCP to help create a generic product class which was then extended by car and product which help promote reusability and ensure consistency.

The advatages of using SOLID are that code is reusable with minor modifications. For example the Car model would have had to been defined and this would mean a new product class will be made. Overtime in bigger projects this will be hard to implement throughly and there will be a lack of consistency. But if we apply SOLID's OCP we can then ensure reusabiblity.

```Java
package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractProduct {
    private String id;
    private String name;
    private int quantity;
}

package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Car extends AbstractProduct {
    private String color;
}

```

This saves alot of time and code reusability. Another benefit its maintainability and this is found in the implementation of an interface in the use of SRP. It is easier to maintain a code base if the items are seperated. This is seen through the division of the controllers for Car and Product which makes it easier to maintain. By implementing SRP we ensure that the items we update and fix are focused only on that certain section.

Finally, Another benefit is testability, code using SOLID ensure proper testability and this was done through my use of DIP. Since we use the interface instead of the concrete implementation like here:

```Java
package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import java.util.Iterator;

public interface CarRepositoryInterface {
    Car create(Car car);
    Iterator<Car> findAll();
    Car findById(String carId);
    Car update(String carId, Car updatedCar);
    void delete(String carId);
}
```

We can trace issues in the repository then since we know and can narrow down the issues. It is also very reliable when it is applied to many items or classes using DIP for testability will be very productive.


The negatives of not using SOLID are that it may make it hard to implement universal changes. This means that if many classes are identical or the same it maybe hard to implement change to each one individually. For example the repository interface are all identical but without the use of OCP it makes modfying each one hard especially in larger projects this will be almost impossible. Another disadvantage of not applying SOLID is that it may be harder to extend or modify classes without changing existing code or implementation. This would have been the case if I did not implement interfaces for my repositories which would force me to change the code in the concrete implementation. Finally, testing will be far more complex without DIP since it will need me to test from the concrete implementation of my repositories which is often slower and harder to scale rather than calling their interfaces.

<h2>Reflection 5</h2>

TDD was very useful since it allowed me to structure my tests before implementing my code, which helped ensure better code coverage on Jacoco reports and allowed me to help identify what I should aim for. Since the code help guide my design process it really help me get a workflow going. I would structure tests for multiple scenarios and functionalities, from there I could develop around that. Furthermore using stuff like Mockito helped ensure that unit tests were isolated and exception handling as tested well. This saved me alot of time in development since I can just get straight to what I intend to do. Of course there are alot of challenges with TDD. Covering all behaviours and edge cases is very hard and if I did it outside of the tutorial, I am not sure I acan do it. This requires alot of investigation and planning to pull off. Another thing I noticed was test confidence in detecting flaws in the code. The quality of the tests are really hard to ensure and keeping it robust is even harder. At the end of the day I need to improve more in how I develop test cases, I need to identify where test need to cover and to create a test for most if not all probable scenarios. I should also use more libraries that can help support what I intend to achieve and should help in refactoring and maintaining the clarity and quality of my test cases.

I would say my test cases were fast since they relied only on operations that exist within the memory. However, I did notice that sometimes in practice, code can have redundant setups that can slow down the tests. I would say my code was isolated since I used Mockito for unit tests they are independent from the database. However this is not perfect as I still see some tests like “testFindAllByAuthorIfAuthorCorrect” which could be more isolated since I believe it uses a shared list. I believe that my test cases were repeatable and since the used only environmental factors it should be the same every time. My tests are self-validating since I can tell which cases failed or passed since those are the only two options. The tests were timely since they were written before and alongside the implementation.

<h2>Reflection 5 Bonus 2 </h2>

I think overall the code was good it did what was asked of it and more. I think however that some aspects are lacking mostly in the test cases. Some test cases are super hard fitted to a certain outcome which may not be optimal. Some test cases also do not work such as in the payment.
Other than that the code worked well but there are many points that could be improved like fixing the use of constructors in the implementation. There are also misuse of code getters and setters.

I improved her code through the OrderServiceImpl and OrderServiceImplTest by fixing the error-handling issues and clarifying the constructor parameters in the code. I also ensured proper exception handling inorder to prevent NullPointerExceptions and data errors in the running of the code.

I identified code smells such as returning null instead of throwing an exception when the order already exists, this makes it hard to identify errors and handle them. Incorrect constructor parameters in the code could lead to data inconsistencies are dangerous if left unchecked. Furthermore, I found that the repository's save method was being called unnecessarily when an order already existed. This made the call redundant and dangerous.

I refactored the code to throw specific exceptions (IllegalArgumentException and NoSuchElementException) in the scenario that an error does exist instead of returning a null value which is dangerous if left unchanged, making errors clearer. I also corrected constructor parameters in the updateStatus method by ensuring the correct fields were passed, preventing potential data mismatches when updating the status of the order.