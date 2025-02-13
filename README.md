<h2>Reflection 1</h2>

I have learned alot about clean code practices from the slides and lectures. Many practices I found most practical was naming conventions. I also applied the idea of separation of concern through Spring Boot's separation of concern. Finally, I also applied SRP or single responsibility functions to isolate each function or need to a single function to ease development. 

As for security practices I believe that the Thymeleaf aspect provides some form of encoding protection. Other than that in the getters and setters there are basic access restriction so that objects are not easily modified.

There are some points of possible improvement in the code such as the findid function could have better exception handling incase it is not found. I should also implement better validation upon updates to ensure the inputted value is valid.

<h2>Reflection 2</h2>

Creating the unit test was very comforting since it showed that my code was working as I intended it to. At times the unit and functional test would be able to catch unintended interactions. This is often frustrating but after sorting out the issue it was good to know that the code worked well.

I do not know if there is a specific rule that determines how many tests should be in each class or function. That being said, I think the bare minimum would be to have at least one case to handle intended interactions and one to handle non-intended interactions. But it depends on what is being tested. For example a function could use 2 but something more complex with many possible ways to fail could need more. 

After learning about code coverage, I do not believe my code has been tested enough to reach a 100% coverage. Inspite of that I do not think that a 100% code coverage automatically gurantees flawless code. It just means that each line has been run or tested. Therefore, there is still room for errors and unintended interactions.

I think if the setup is the same, it may be possible to isolate that into its own class to prevent repetition in code, which should reduce duplicated code. From a reading perspective, I think that modifying only a section while keeping most of it the same may actually cause confusion in the long run. Practices like that may make it hard for me and others to discern which part of the code is different. The best way to help prevent redundant code and readability issues is to implement a base class where the setup is stored. To help readers read the code it maybe logical to implement meaningful naming conventions so that it is easy to understand what the code does.
