# fx-unit

fx-unit aims to be a utility for unit testing JavaFX controllers as well as integration testing JavaFX UIs, as simple as possible: 
+ test early
+ write tests with at most no overhead
+ simplified bootstrapping for you test setups
+ no complex dependencies for testing - currently no dependencies at all
+ no dependencies and no modifications in the application, at any time
+ no more hassles getting the threading right

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

At present, the repository contains not only the source code but also the binary distribution. So, just clone or download the repository and have a look at fx-unit/aeFXUnit/dist.

The project is currently beeing developed with NetBeans IDE 8.2 and Ant, but if you just want to use the library for testing your JavaFX project, you may simply download or clone the repository. 


```
