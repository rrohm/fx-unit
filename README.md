# fx-unit

fx-unit wants to make unit testing JavaFX controllers simple as possible: 
+ test early
+ write tests with at most no overhead
+ no complex dependencies for testing - currently none at all
+ no dependencies in the application, at any time.

At present, it offers these additional assertions: 

```java
assertFocused(javafx.scene.Node node);
assertNotShowing(javafx.stage.Window window)
assertNotVisible(javafx.scene.Node node)
assertSelected(javafx.scene.control.TabPane tabPane, java.lang.String id)
assertShowing(javafx.stage.Window window)
assertVisible(javafx.scene.Node node)
```

# About the Project

The fx-unit project is currently in a proof-of-concept phase. It is used in several internal projects at Aeonium Software Systems, in order to evaluate the aproach. 

If you are interested, drop a message!
Within the next day, further documentation follows, and any comments are welcome.

The project is open source, the code is made available under LGPL 3.
