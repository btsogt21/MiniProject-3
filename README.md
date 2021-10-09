# MiniProject-3
Here, I implement a Skiplist in IntelliJ and test how differing node promotion probabilites affect its runtime. 

There is a RuntimeTest.java file which, when ran, allows us to test the number of operations that go into locating an element within a given skip list as a function
of the probability of a given node being promoted to the next list level which it does not occupy. The file chooses a default 'node promotion probability' of 0.5, but you can
specify your own number between 0 and 1 utilizing the command line. 
