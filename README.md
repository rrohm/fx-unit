# fx-unit

fx-unit wants to make unit testing JavaFX controllers simple as possible: 
+ test early
+ write tests with at most no overhead
+ no complex dependencies for testing - currently none at all
+ no dependencies in the application, at any time.

At present, it offers these additional assertions: 

```java
AssertFX.assertFocused(javafx.scene.Node node);
AssertFX.assertNotShowing(javafx.stage.Window window)
AssertFX.assertNotVisible(javafx.scene.Node node)
AssertFX.assertSelected(javafx.scene.control.TabPane tabPane, java.lang.String id)
AssertFX.assertShowing(javafx.stage.Window window)
AssertFX.assertVisible(javafx.scene.Node node)
```

Also, Hamcrest matchers will be provided. At present, there are the first two candidates: 
```java
public static Matcher<Node> isVisible()
public static Matcher<Node> isNotVisible()
``` 

# About the Project

The fx-unit project is currently in a proof-of-concept phase. It is used in several internal projects at Aeonium Software Systems, in order to evaluate the aproach. 

If you are interested, drop a message!
Within the next days, further documentation follows, and any comments are welcome.

The project is open source, the code is made available under LGPL 3.


# Getting started

The project is currently beeing developed with NetBeans IDE 8.2 and Ant, but if you just want to use the library for testing your JavaFX project, you may simply download or clone the repository. Building with Ant is not necessary, since currently the project contains the library jar in the folder fx-unit/aeFXUnit/dist.


```
