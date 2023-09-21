# Operating-Systems-Dining-Philosophers-Problem-Assignment

## Collaborators
[Megan Coscia](https://github.com/m-coscia) <br>
[Inas Fawzi](https://github.com/inas-fawzi) <br>

## Description
The dining philosophers problem is a well-known synchonization problem. While there are many variation of the problem, the version we worked with goes as follows: <br>

> N number of philosphers sit at a table where there is a chopstick between each philosopher. A philospher can either eat, be hungry, think, or speak. If both chopsticks to the left and the right of the philosopher are not available, they must wait to eat until both resources are available. If a philosopher wishes to speak, they must wait until no one is speaking.<br>

In this assignment, we needed to implement synchronization to prevent starvation from occuring, in whihc we used monitors rather than semaphores. Given some incomplete classes, we were expected to implement the data members, some methods related to the synchonization of the dining philosophers, and the driver class. By implementing a way to take turns, it removed deadlock from the solution. To improve the current solution, a priority queue would need to be implemented to give an order to the philosophers - this would remove all cases of starvation.

## Concepts Learned and Technology
- Synchronization through the use of Monitors
- Learning to prevent deadlock and starvation
- Implemented solution in Java
