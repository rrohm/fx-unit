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
AssertFX.assertDisabled(javafx.scene.Node node)
AssertFX.assertEditable(javafx.scene.Node node)
AssertFX.assertEnabled(javafx.scene.Node node)
AssertFX.assertFocused(javafx.scene.Node node);
AssertFX.assertHasChildren(javafx.scene.Parent parent, int children)
AssertFX.assertHasItems(javafx.scene.control.ChoiceBox choiceBox)
AssertFX.assertHasItems(javafx.scene.control.ChoiceBox choiceBox, int itemCount)
AssertFX.assertHasItems(javafx.scene.control.ComboBox comboBox) 	
AssertFX.assertHasItems(javafx.scene.control.ComboBox comboBox, int itemCount) 	
AssertFX.assertHasItems(javafx.scene.control.ListView listView) 	
AssertFX.assertHasItems(javafx.scene.control.ListView listView, int itemCount) 	
AssertFX.assertHasItems(javafx.scene.control.TableView tableView) 	
AssertFX.assertHasItems(javafx.scene.control.TableView tableView, int itemCount) 	
AssertFX.assertHasNotStyleClass(javafx.scene.Node node, String styleClass) 	
AssertFX.assertHasStyleClass(javafx.scene.Node node, String styleClass) 	
AssertFX.assertManaged(javafx.scene.Node node) 	
AssertFX.assertNotManaged(javafx.scene.Node node) 	
AssertFX.assertNotSelected(javafx.scene.control.ToggleButton toggleButton)
AssertFX.assertSelected(javafx.scene.control.ToggleButton toggleButton)
AssertFX.assertNotShowing(javafx.stage.Window window)
AssertFX.assertNotVisible(javafx.scene.Node node)
AssertFX.assertSelected(javafx.scene.control.TabPane tabPane, java.lang.String id)
AssertFX.assertShowing(javafx.stage.Window window)
AssertFX.assertText(javafx.scene.control.Labeled node, String text) 	
AssertFX.assertText(javafx.scene.control.TextInputControl node, String text) 	
AssertFX.assertTooltipText(javafx.scene.control.Control node, String text) 	
AssertFX.assertVisible(javafx.scene.Node node)
```
There is also a fluent-style API, that supports these assertions, plus methods for simulating interaction with the UI.

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

The project is a simple Maven project. Just download and unpack it, or clone it. Go to the project directory and issue a mvn clean install at the console. After that, you can use it as a dependency from the local Maven repository.


```
