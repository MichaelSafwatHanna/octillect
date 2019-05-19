# Octillect Style Guide

<!-- TOC depthFrom:2 depthTo:3 -->

- [1. Git & GitHub Guidelines](#1-git--github-guidelines)
  - [1.1. Commit](#11-commit)
  - [1.2. Commit Message](#12-commit-message)
  - [1.3. Submitting Pull Requests](#13-submitting-pull-requests)
- [2. Java Programming Guidelines](#2-java-programming-guidelines)
  - [2.1. Naming Conventions](#21-naming-conventions)
  - [2.2. Declarations](#22-declarations)
  - [2.3. Statements](#23-statements)
  - [2.4. Programming Practices](#24-programming-practices)
  - [2.5. Formatting](#25-formatting)
  - [2.6. References](#26-references)
- [3. JavaFX Guidelines](#3-javafx-guidelines)
  - [3.1. Controls Naming Conventions](#31-controls-naming-conventions)
  - [3.2. Event Handlers Naming Conventions](#32-event-handlers-naming-conventions)
  - [3.3. @FXML: always used](#33-fxml-always-used)
  - [3.4. Spacing](#34-spacing)
  - [3.5. Code readability](#35-code-readability)
  - [3.6. References](#36-references)
- [4. UI Guidelines](#4-ui-guidelines)
  - [4.1. Structure](#41-structure)
  - [4.2. Responsive layout grid](#42-responsive-layout-grid)
  - [4.3. Window Sizes](#43-window-sizes)
  - [4.4. Cards](#44-cards)
  - [4.5. Color Palette](#45-color-palette)
  - [4.6. References](#46-references)
- [5. External Libraries](#5-external-libraries)
- [6. Design Patterns](#6-design-patterns)

<!-- /TOC -->

---

## 1. Git & GitHub Guidelines

### 1.1. Commit

- Commits **shouldn't contain multiple unrelated changes**; try and make piecemeal changes if you can, to make it easier to review and merge. In particular, don't commit style/whitespace changes and functionality changes in a single commit.
- Modify **one file** per commit. This will make merging and pulling easier for everyone.
- Make sure that the App still **runs** before making a commit.
- Make sure that the App **passes all the tests** before making a commit.

### 1.2. Commit Message

- Separate message from body with a **blank line**.
- **Do not** end the message line with a **period**.
- **Capitalize** the message line and each paragraph.
- Use the **imperative mood** in the message line.
- Limit the **message line** to **50 characters**.
- Wrap lines of the **body** at **72 characters**.
- **Asterisks** are used for the bullets in message's body.
- **Punctuate** your commit message's body.
- Add **two blank lines** followed by **Co-authors**, if found, at the end of your commit message.
- Example:

```unix shell
Add comments and other XAML code edits

 * Add x:Name attribute to all fields and buttons.
 * Use Pascal Case in naming instead of Camel Case.
 * Add Comments to make it easier to read the code.
 * Add whitespaces between code blocks.


Co-authored-by: Michael Safwat <michaelsafwat.hanna@gmail.com>
Co-authored-by: Micheline Medhat <MichelineMedhat@gmail.com>
Co-authored-by: Monica Adel <monicatanios@gmail.com>
Co-authored-by: Monica Atef <monicaatef46@gmail.com>
Co-authored-by: Youssef Raafat <YoussefRaafatNasry@gmail.com>
```

> **Pro tip**: Use [Code Spell Checker](https://marketplace.visualstudio.com/items?itemName=streetsidesoftware.code-spell-checker) to avoid typos.

### 1.3. Submitting Pull Requests

1. Fork and clone the repository.
1. Create a new branch based on `master`: `git checkout -b my-branch-name`
1. Make your changes, and make sure the app still runs and passes all the tests.
1. Push to your fork and submit a pull request from your branch to `master`

   > **Here are a few things you have to do:**
   >
   > - Write a good commit message.
   > - Follow the style guide where possible.
   > - Keep your change as focused as possible. If there are multiple changes you would like to make that are not dependent upon each other, consider submitting them as separate pull requests.

1. **After your pull request is merged**:

   1. **Delete** the branch you created from your **GitHub** fork by navigating near the bottom of your pull request and clicking `Delete branch`:

      ![delete-branch-github](https://help.github.com/assets/images/help/pull_requests/delete_branch_button.png)

   1. **Delete** the branch you created from your **local** fork.

      ```bash
      git checkout master
      git branch -d my-branch-name
      ```

   1. Make your fork's `master` branch **even** with `MonicaTanios:master`

      ```bash
      git remote add upstream https://github.com/MonicaTanios/octillect.git
      git pull upstream master
      git push origin master
      ```

   1. Prepare for your next pull request.

---

## 2. Java Programming Guidelines

### 2.1. Naming Conventions

| Identifier | Case & Naming  | Example          |
| :--------- | :------------- | :--------------- |
| Package    | lowercase      | `deepspace`      |
| Class      | UpperCamelCase | `Character`      |
| Method     | lowerCamelCase | `sendMessage`    |
| Variable   | lowerCamelCase | `computedValues` |
| Parameter  | lowerCamelCase | `name`           |
| Constant   | CONSTANT_CASE  | `MIN_WIDTH`      |

> - [Specific Naming Conventions](https://petroware.no/javastyle.html#Specific).
> - [Camel case: defined](https://google.github.io/styleguide/javaguide.html#s5.3-camel-case)

### 2.2. Declarations

#### 2.2.1. Class and Interface declaration

##### 2.2.1.1. Modifiers

Class and member modifiers, when present, appear in the order recommended by the Java Language Specification:

```java
public protected private abstract default static final transient volatile synchronized native strictfp
```

Example:

```java
final volatile private String value; // bad
private final volatile String value; // good
```

##### 2.2.1.2. Order

Class and Interface declarations should be organized in the following manner:

1. Class or Interface documentation, set inside the comment delimiters `/**...*/`.
2. Class or Interface statement.
3. Static variables in the order **public**, **protected**, package (no access modifier), **private**.
4. Instance variables in the order **public**, **protected**, package (no access modifier), **private**.
5. Constructors.
6. Getter and setters.
7. Methods (no specific order) but separated by a blank line.

```java
/** Model Object for Example. */
public class Example {

    public static int foo;
    protected int id;
    private String name;

    public Example(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateData(int newId, String newName) {
        setId(newId);
        setName(newName);
    }

}
```

#### 2.2.2. Variable declaration

Every variable declaration (field or local) declares only one variable.

```java
int a;    // good
int b;
int c, d; // bad
```

#### 2.2.3. Array declaration

The square brackets form a part of the type, not the variable

```java
String[] args; // good
String args[]; // bad
```

### 2.3. Statements

#### 2.3.1. Import statements

##### 2.3.1.1 No wildcard/unused imports

- Wildcard imports, static or otherwise, are **not used**.
- Unused imports should be removed.

```java
import java.util.ArrayList; // good
import java.util.Date;

import java.util.*;         // bad
```

##### 2.3.1.2 Ordering and spacing

- Imports should be grouped in blocks by top-level package.
- Each block should be wrapped by a new line.
- Blocks should be ordered in ASCII order.
- Within each block, imports should be ordered in ASCII order.

```java
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
```

#### 2.3.2. Simple Statements

Each line should contain at most one statement.

```java
argv++;         // good
argc--;

argv++; argc--; // bad
```

#### 2.3.3. return Statements

A return statement with a value should not use **parentheses** unless they make the return value more obvious in some way.

```java
return;
return myDisk.size();
return (size ? size : defaultSize);
```

#### 2.3.4. if, if-else, if else-if else Statements

The if-else class of statements should have the following form:

```java
if (condition) {
    statements;
} else if (condition) {
    statements;
} else {
    statements;
}
```

Note: if statements **always use braces**.

```java
if (condition) // bad
    statement;
```

#### 2.3.5. try-catch Statements

A try-catch statement should have the following format:

```java
try {
    statements;
} catch (ExceptionClass e) {
    statements;
} finally {
    statements;
}
```

### 2.4. Programming Practices

#### 2.4.1. Consider setters and getters for field access

Within the same class consider using getters and setter to access fields values to ensure [lazy initialization](https://www.javaworld.com/article/2077568/java-tip-67--lazy-instantiation.html) and other intended logic implemented in getters and setters is always applied.

```java
public void changeName(String name) {
    setName(name); // good
}

public void changeName(String name) {
    this.name = name; // bad
}
```

#### 2.4.2. @Override: always used

A method is marked with the `@Override` annotation whenever it is legal.  
**Exception**: `@Override` may be omitted when the parent method is [`@Deprecated`](https://docs.oracle.com/javase/1.5.0/docs/api/java/lang/Deprecated.html).

#### 2.4.3. Static members: qualified using class

When a reference to a static class member must be qualified, it is qualified with that class's name, not with a reference or expression of that class's type.

```java
Foo aFoo = ...;
Foo.aStaticMethod();  // good
aFoo.aStaticMethod(); // bad
```

### 2.5. Formatting

> Use the following shortcuts in **IntelliJ** to help you optimize your code.
>
> - **Optimize Imports:** <kbd>Ctrl</kbd> + <kbd>Alt</kbd> + <kbd>O</kbd>
> - **Reformat Code:** <kbd>Ctrl</kbd> + <kbd>Alt</kbd> + <kbd>L</kbd>

#### 2.5.1. Nonempty blocks

Braces follow the Kernighan and Ritchie style ("Egyptian brackets") for nonempty blocks and block-like constructs:

- No line break before the opening brace.
- Line break after the opening brace.
- Line break before the closing brace.
- Line break after the closing brace.

```java
void myFunction() {
    System.out.println("I'm written in Egyptian brackets");
}
```

#### 2.5.2. Empty blocks

An empty block or block-like construct may be in K & R style. Alternatively, it may be closed immediately after it is opened, with no characters or line break in between (`{}`).

```java
  // This is acceptable
  void doNothing() {}

  // This is equally acceptable
  void doNothingElse() {
  }
```

#### 2.5.3. Column limit

Java code has a column limit of **100** characters.

#### 2.5.4. Blank Spaces

- Blank space **should not** be used between a method name and its opening parenthesis. This helps to distinguish keywords from method calls.

  ```java
  void doSomething(int x) {}  // good
  void doSomething (int x) {} // bad
  ```

- Blank spaces **should** be used in the following circumstances:

1. A keyword followed by a parenthesis should be separated by a space. Example:

   ```java
      while (true) {
          statements;
      }
   ```

1. A blank space should appear before the opening parenthesis (`{`) of a method or a class.

   ```java
   void doSomething() throw Exception {}
   class Bar extends Foo {}
   ```

1. A blank space should appear after commas in argument lists.

   ```java
   void doSomething(int x, int y) {}
   ```

1. All binary operators except `.` should be separated from their operands by spaces. Blank spaces should never separate unary operators such as unary minus, increment ("++"), and decrement ("--") from their operands. Example:

   ```java
   a += c + d;
   a = (a + b) / (c * d);
   a++;
   ```

1. The expressions in a for statement should be separated by blank spaces. Example:

   ```java
   for (expr1; expr2; expr3)
   ```

1. Casts should be followed by a blank space. Examples:

   ```java
   myMethod((byte) aNum, (Object) x);
   myMethod((int) (cp + 5), ((int) (i + 3)) + 1);
   ```

### 2.6. References

1. [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
2. [Oracle Code Conventions for the Java Programming Language](https://www.oracle.com/technetwork/java/codeconvtoc-136057.html)
3. [Java Programming Style Guidelines](https://petroware.no/javastyle.html)
4. [Twitter Java Style Guide](https://github.com/twitter/commons/blob/master/src/java/com/twitter/common/styleguide.md#field-class-and-method-declarations)
5. [47deg Java Style Guide](https://github.com/47deg/coding-guidelines/tree/master/java)

---

## 3. JavaFX Guidelines

### 3.1. Controls Naming Conventions

Use **lowerCamelCase** names with a **type indication** Suffix.

```fxml
<TextField fx:id="emailTextField" />
```

### 3.2. Event Handlers Naming Conventions

Use `handle` + `Element Name` + `Event Name`.

```java
@FXML
protected void handleSubmitButtonAction(ActionEvent event) {
    .
    .
}
```

### 3.3. @FXML: always used

Variables and event handlers in the controller should **always** be annotated with `@FXML` especially if they are `private` or `protected`.

```java
public class RegistrationFormController {

    @FXML private Button submitButton;

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) {
        .
        .
    }

}
```

### 3.4. Spacing

- If the tag is self closing tag (`/>`), **leave a whitespace** before closing it.
- Else, just close it _(without any whitespace)_.

```fxml
<GridPane>
    <TextField fx:id="passwordTextField" />
</GridPane>
```

### 3.5. Code readability

- If the element has **two or less** attributes, put them in **one line**.

```fxml
<Label text="name" textFill="black" />
```

- If the element has **more than two** attributes, put **one attribute per line**.
- Put the first attribute on the element line.

```fxml
<Button fx:id="submitButton"
        text="Submit"
        onAction="handleSubmitButtonAction" />
```

- Order Attributes in the following [logical order\*](https://github.com/cmaneu/xaml-coding-guidelines/issues/1):

```text
1. Name
   - fx:id

2. Containers' attached properties
   - GridPane.rowIndex
   - GridPane.columnIndex
   - GridPane.rowSpan
   - GridPane.columnSpan
   - GridPane.halignment
   - GridPane.valignment
   - AnchorPane.topAnchor
   - AnchorPane.rightAnchor
   - AnchorPane.bottomAnchor
   - AnchorPane.leftAnchor
   - HBox.hgrow
   - VBox.vgrow
   - HBox.margin
   - VBox.margin
   - FlowPane.margin
   - GridPane.margin

3. Box Model
   - hgap
   - vgap
   - alignment
   - padding

4. Size
   - width
   - height
   - minWidth
   - minHeight
   - prefWidth
   - prefHeight
   - maxWidth
   - maxHeight

5. Position & Transform
   - layoutX
   - layoutY
   - rotate
   - scaleX
   - scaleY
   - scaleZ

6. Typography
   - text
   - promptText
   - textFill
   - font

7. Visual & Styling
   - background
   - opacity
   - visible
   - styleSheets
   - style

8. Event handlers (Alphabetically)
   - onAction
   - onDragDetected
```

> _\* Note the following:_
>
> - **Width** comes before **Height**
> - **Rows** comes before **Columns**
> - **Horizontal** comes before **Vertical**
> - **X-axis** comes before **Y-axis**
> - Use **top**, **right**, **bottom**, **left** rule.

### 3.6. References

1. [AwesomeJavaFX](https://github.com/mhrimaz/AwesomeJavaFX)
1. [XAML Coding Guidelines](https://github.com/cmaneu/xaml-coding-guidelines)
1. [Introduction to FXML](https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/doc-files/introduction_to_fxml.html)
1. [JavaFX CSS Reference Guide](https://docs.oracle.com/javafx/2/api/javafx/scene/doc-files/cssref.html)
1. [caspian.css](https://gist.github.com/tmazeika/c90c03c645d18722ddb0)
1. [modena.css](https://gist.github.com/maxd/63691840fc372f22f470)

---

## 4. UI Guidelines

### 4.1. Structure

Material Design layouts are visually balanced. Most measurements align to an 8dp grid applied, which aligns both spacing and the overall layout. Smaller components, such as iconography and typography, can align to a 4dp grid.

![8dp and 4dp units](https://storage.googleapis.com/spec-host-backup/mio-design%2Fassets%2F1BwORvuZ0Pv88f7m0z1BoAWaY0q8Y_H8K%2Flayout-unitsmeasurements-dev-grid.png)

### 4.2. Responsive layout grid

The Material Design layout grid is made up of three elements:

1. Columns
2. Gutters
3. Margins

![Responsive layout grid](https://storage.googleapis.com/spec-host-backup/mio-design%2Fassets%2F1P_b7NIZ5_IBvs9VraJx7tu8KO-dUZXJW%2Flayout-responsive-columns-margins-gutters.png)

### 4.3. Window Sizes

Regardless of form-factor, most popular screen sizes are divisible by 8 on at least one axis - usually both. Additionally, some platforms’ style guides (like Material Design) call specifically for a grid of 4 or 8 points, making this system a natural fit.

![most-used-screen-resolutions](https://spec.fm/static/img/specifics/001/fig-7.png)

### 4.4. Cards

Cards should have **16dp** padding.

![card-padding](https://user-images.githubusercontent.com/41103290/55407119-c1028c80-555d-11e9-8d84-721a539d86c9.png)

### 4.5. Color Palette

Use the following colors:

| Primary _[light]_ |  Primary   | Primary _[Dark]_ |  Success   |    Info    |  Warning   |   Danger   |
| :---------------: | :--------: | :--------------: | :--------: | :--------: | :--------: | :--------: |
|    ![#428feb]     | ![#1473e6] |    ![#0f64d2]    | ![#28a745] | ![#17a2b8] | ![#ffc107] | ![#dc3545] |
|     `#428feb`     | `#1473e6`  |    `#0f64d2`     | `#28a745`  | `#17a2b8`  | `#ffc107`  | `#dc3545`  |

[#428feb]: https://imgplaceholder.com/100x100/transparent/428feb/fa-circle
[#1473e6]: https://imgplaceholder.com/100x100/transparent/1473e6/fa-circle
[#0f64d2]: https://imgplaceholder.com/100x100/transparent/0f64d2/fa-circle
[#28a745]: https://imgplaceholder.com/100x100/transparent/28a745/fa-circle
[#17a2b8]: https://imgplaceholder.com/100x100/transparent/17a2b8/fa-circle
[#ffc107]: https://imgplaceholder.com/100x100/transparent/ffc107/fa-circle
[#dc3545]: https://imgplaceholder.com/100x100/transparent/dc3545/fa-circle

|  Dark 100  |  Dark 200  |  Dark 300  |  Dark 400  |  Dark 500  |  Dark 600  |  Dark 700  |  Dark 800  |  Dark 900  |
| :--------: | :--------: | :--------: | :--------: | :--------: | :--------: | :--------: | :--------: | :--------: |
| ![#f5f5f5] | ![#eeeeee] | ![#cdcdcd] | ![#9e9e9e] | ![#979797] | ![#616161] | ![#424242] | ![#212121] | ![#1b1b1b] |
| `#f5f5f5`  | `#eeeeee`  | `#cdcdcd`  | `#9e9e9e`  | `#979797`  | `#616161`  | `#424242`  | `#212121`  | `#1b1b1b`  |

[#f5f5f5]: https://imgplaceholder.com/100x100/transparent/f5f5f5/fa-circle
[#eeeeee]: https://imgplaceholder.com/100x100/transparent/eeeeee/fa-circle
[#cdcdcd]: https://imgplaceholder.com/100x100/transparent/cdcdcd/fa-circle
[#9e9e9e]: https://imgplaceholder.com/100x100/transparent/9e9e9e/fa-circle
[#979797]: https://imgplaceholder.com/100x100/transparent/979797/fa-circle
[#616161]: https://imgplaceholder.com/100x100/transparent/616161/fa-circle
[#424242]: https://imgplaceholder.com/100x100/transparent/424242/fa-circle
[#212121]: https://imgplaceholder.com/100x100/transparent/212121/fa-circle
[#1b1b1b]: https://imgplaceholder.com/100x100/transparent/1b1b1b/fa-circle

### 4.6. References

1. [Google Material Design](https://material.io/)
1. [The 8-Point Grid](https://spec.fm/specifics/8-pt-grid)

---

## 5. External Libraries

1. [JFoenix](http://www.jfoenix.com/)
1. [Java API for GitHub](https://github-api.kohsuke.org/)
1. [Ikonli](http://aalmiray.github.io/ikonli/)
1. [Firebase Admin SDK](https://github.com/firebase/firebase-admin-java)
1. [gson](https://github.com/google/gson)

---

## 6. Design Patterns

1. [Singleton Design Pattern](https://en.wikipedia.org/wiki/Singleton_pattern)
1. [Composite Design Pattern](https://en.wikipedia.org/wiki/Composite_pattern)
1. [Repository Design Pattern](https://blog.kylegalbraith.com/2018/03/06/getting-familiar-with-the-awesome-repository-pattern/)
1. [Builder Design Pattern](https://www.geeksforgeeks.org/builder-design-pattern/)
1. [Advanced Builder Design Pattern](https://medium.com/beingprofessional/think-functional-advanced-builder-pattern-using-lambda-284714b85ed5)
1. [Observer Design Pattern](https://en.wikipedia.org/wiki/Observer_pattern)
1. [Factory Design Pattern](https://www.geeksforgeeks.org/design-patterns-set-2-factory-method/)
