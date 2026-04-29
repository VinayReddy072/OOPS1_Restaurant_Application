# Restaurant Management System - Java 21 Features Demo

A comprehensive Java application demonstrating all major language features from Java 21 LTS, including both fundamental and advanced concepts.

## Features Covered

### Fundamentals

#### 1. Classes
- **`this()` vs `this.`**: Demonstrated in `Person.java`
  - `this()` - Constructor chaining (calling another constructor)
  - `this.` - Refers to instance variables and methods
- **Method Overloading**: `getInfo()` method with different parameter lists
- **Varargs**: `addItems(MenuItem... items)` in `Order.java`
- **LVTI (Local Variable Type Inference)**: Using `var` keyword in `RestaurantApp.java`

#### 2. Encapsulation
- Private fields with public getters/setters in all model classes
- Demonstrated in `Person.java`, `Employee.java`, `MenuItem.java`

#### 3. Interfaces
- **Billable Interface** (`Billable.java`):
  - Abstract method: `calculateTotal()`
  - Default methods: `calculateTotalWithTip()`, `calculateTotalWithTax()`, `calculateGrandTotal()`
  - Private methods: `validateTipPercentage()`, `formatAmount()`
  - Static methods: `getDefaultTaxRate()`, `formatCurrency()`

#### 4. Inheritance
- **Base class**: `Person.java`
- **Derived class**: `Employee.java` extends `Person`
- **Overriding**: `displayInfo()` and `toString()` methods overridden
- **Polymorphism**: Demonstrated with Person reference holding Employee object
- **`super()` vs `super.`**:
  - `super()` - Calls parent constructor
  - `super.` - Accesses parent class methods

#### 5. Exceptions
- **Checked Exception**: `InvalidMenuItemException.java`
- **Unchecked Exception**: `OrderNotFoundException.java` (extends RuntimeException)
- Try-catch blocks demonstrated in main application

#### 6. Enums
- `MealType.java` with fields, constructor, and methods
- Enum values: BREAKFAST, LUNCH, DINNER, LATE_NIGHT
- Custom behavior: `isAvailableAt(int hour)`

#### 7. Arrays
- Array creation and iteration in `demonstrateArraysAndVarargs()`
- Array conversion in `ImmutableMenu.getItemsAsArray()`

#### 8. Java Core API
- **String**: Concatenation, methods (`length()`, `contains()`, `toUpperCase()`)
- **StringBuilder**: Building receipts with `append()`
- **List/ArrayList**: Creating and managing collections
- **Date API**: `LocalDateTime`, `DateTimeFormatter` for date operations

### Advanced Features

#### 1. Call-by-Value and Defensive Copying
- Demonstrated in `ImmutableMenu.java`
- Constructor creates defensive copy: `new ArrayList<>(items)`
- Getter returns unmodifiable list: `Collections.unmodifiableList(items)`
- Proves immutability by modifying original list after object creation

#### 2. Interface Methods (Private, Default, Static)
- **Default methods**: Provide default implementations (e.g., `calculateTotalWithTip()`)
- **Private methods**: Helper methods for default methods (e.g., `validateTipPercentage()`)
- **Static methods**: Utility methods (e.g., `getDefaultTaxRate()`)

#### 3. Records
- `OrderRecord.java` - Compact immutable data carrier
- Automatic generation of constructor, getters, `equals()`, `hashCode()`, `toString()`
- Compact constructor with validation
- Custom methods: `getTotalPrice()`, `getItemCount()`

#### 4. Custom Immutable Type
- `ImmutableMenu.java` - Fully immutable class
- All fields are `final`
- Defensive copying in constructor and getters
- No setters provided
- Class marked as `final` to prevent inheritance

#### 5. Lambdas and Predicates
- **Lambdas**: `item -> item.getPrice() > price` in `MenuFilters.java`
- **Predicate**: Type-safe functional interface for filtering
- **Final/Effectively Final**: Variable `priceThreshold` and `lowerKeyword` captured in lambdas
- **Method References**: `System.out::println`, `MenuItem::getPrice`
- **Predicate Composition**: `lunchItems.and(affordableLunch)`

#### 6. Switch Expressions
- Enhanced switch with arrow syntax (`->`)
- Returns values directly
- Pattern matching for type checking
- Demonstrated in `getRecommendation()` and `describeObject()` methods

#### 7. Pattern Matching for Switch
- Type patterns: `case String s ->`, `case Integer i ->`
- Null handling: `case null ->`
- Default case for unknown types
- Exhaustive matching with sealed types

#### 8. Sealed Classes and Interfaces
- `Discountable` - Sealed interface
- Permits only: `VIPDiscount`, `StudentDiscount`, `SeniorDiscount`
- Enables exhaustive pattern matching
- Compiler ensures all permitted types are handled

## Project Structure

```
src/com/restaurant/
├── RestaurantApp.java              # Main application
├── model/
│   ├── Person.java                 # Base class (this, this., overloading)
│   ├── Employee.java               # Inheritance (super, super.)
│   ├── MenuItem.java               # Encapsulation
│   ├── Order.java                  # Implements Billable, varargs
│   ├── OrderRecord.java            # Record type
│   ├── ImmutableMenu.java          # Custom immutable type
│   └── MealType.java               # Enum
├── service/
│   ├── Billable.java               # Interface (default, private, static)
│   ├── Discountable.java           # Sealed interface
│   ├── VIPDiscount.java            # Implements Discountable
│   ├── StudentDiscount.java        # Implements Discountable
│   └── SeniorDiscount.java         # Implements Discountable
├── exception/
│   ├── InvalidMenuItemException.java    # Checked exception
│   └── OrderNotFoundException.java      # Unchecked exception
└── util/
    └── MenuFilters.java            # Lambdas, Predicates, method references
```

## Compilation and Execution

### Prerequisites
- Java 21 or later (Java LTS)

### Compile
```bash
javac -d bin src/com/restaurant/**/*.java src/com/restaurant/*.java
```

### Run
```bash
java -cp bin com.restaurant.RestaurantApp
```

### Simplified Single Command
```bash
javac src/com/restaurant/**/*.java src/com/restaurant/*.java && java -cp src com.restaurant.RestaurantApp
```

## Java 22 Features (Extra Credit)

### 1. Unnamed Variables and Patterns (JEP 456)
Java 22 introduced unnamed variables using `_` to indicate unused variables.

**Example Enhancement for our code:**
```java
// Before (Java 21)
for (MenuItem item : items) {
    // not using 'item'
}

// After (Java 22)
for (MenuItem _ : items) {
    // clearly indicates unused variable
}

// Pattern matching with unnamed patterns
if (obj instanceof Point(int x, int _)) {
    // only x is used, y is explicitly unnamed
}
```

**To use in project:**
1. Update Java version to 22
2. Add unnamed patterns in switch expressions where some components aren't needed
3. Use in exception handling: `catch (Exception _)` when exception details aren't needed

### 2. Statements before super() (JEP 447)
Java 22 allows statements before `super()` calls in constructors, enabling validation logic.

**Example Enhancement for Employee.java:**
```java
// Java 22 allows this:
public Employee(String name, int age, String employeeId, double salary) {
    if (salary < 0) {
        throw new IllegalArgumentException("Salary cannot be negative");
    }
    if (employeeId == null || employeeId.isBlank()) {
        throw new IllegalArgumentException("Employee ID required");
    }
    super(name, age);  // super() can come after validation
    this.employeeId = employeeId;
    this.salary = salary;
}
```

**Benefits:**
- Validate parameters before calling parent constructor
- Compute values needed for super() call
- Fail fast with better error messages

**To use in project:**
1. Update to Java 22
2. Add validation before `super()` calls in Employee constructor
3. Compute/transform parameters before passing to parent

### 3. Stream Gatherers (Preview - JEP 461)
Java 22 introduces Stream Gatherers for custom intermediate operations.

**Example Enhancement for MenuFilters.java:**
```java
// Custom gatherer to group items by price ranges
public static Stream<List<MenuItem>> groupByPriceRange(Stream<MenuItem> items) {
    return items.gather(Gatherers.windowFixed(3)); // Group in windows of 3
}

// Or create custom gathering operation
public static Gatherer<MenuItem, ?, List<MenuItem>> expensiveItemsGatherer() {
    return Gatherer.of(
        ArrayList::new,
        (list, item) -> {
            if (item.getPrice() > 15.0) {
                list.add(item);
            }
            return true;
        }
    );
}
```

**To enable in Java 22:**
```bash
javac --enable-preview --release 22 src/com/restaurant/**/*.java
java --enable-preview -cp src com.restaurant.RestaurantApp
```

## Java 23 Features (Extra Credit)

### 1. String Templates (Preview - JEP 430)
Java 23 continues refinement of string templates for safer string interpolation.

**Example Enhancement for RestaurantApp.java:**
```java
// Instead of string concatenation:
String message = "Order " + orderId + " for " + customerName + " total: $" + total;

// Java 23 String Templates:
String message = STR."Order \{orderId} for \{customerName} total: $\{total}";

// With formatting:
String formatted = STR."Total: $\{total}%.2f with tax: $\{total * 1.08}%.2f";
```

**To enable:**
```bash
javac --enable-preview --release 23 src/com/restaurant/**/*.java
```

### 2. Primitive Types in Patterns (Preview - JEP 455)
Java 23 allows pattern matching with primitive types.

**Example Enhancement for describeObject():**
```java
static String describeValue(Object obj) {
    return switch (obj) {
        case int i when i > 0 -> "Positive integer: " + i;
        case int i when i < 0 -> "Negative integer: " + i;
        case double d -> "Double value: " + d;
        case String s -> "String: " + s;
        default -> "Other type";
    };
}
```

**Benefits:**
- Direct pattern matching on primitives
- Guard conditions with `when` clause
- More expressive code

### 3. Module Import Declarations (Preview - JEP 476)
Simplifies imports by importing entire modules.

**Example:**
```java
// Instead of multiple imports:
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Java 23 module import:
import module java.base;  // Imports all from java.base module
```

## Key Concepts Demonstrated

### this() vs this.
- `this()` calls another constructor in the same class
- `this.` refers to the current instance's fields/methods

### super() vs super.
- `super()` calls the parent class constructor
- `super.` accesses parent class fields/methods

### Final vs Effectively Final
- Variables captured by lambdas must be final or effectively final
- Effectively final: variable is not reassigned after initialization
- Example: `priceThreshold` in lambdas must not be modified

### Defensive Copying
- Creating copies of mutable objects to maintain immutability
- Prevents external modification of internal state
- Used in `ImmutableMenu` constructor and getters

### Method References
- Shorthand for lambda expressions
- `System.out::println` equivalent to `item -> System.out.println(item)`
- `MenuItem::getPrice` equivalent to `item -> item.getPrice()`

## Learning Outcomes

This application demonstrates:
1. Modern Java syntax and best practices
2. Object-oriented programming principles
3. Functional programming concepts
4. Type safety with sealed types
5. Immutability patterns
6. Exception handling strategies
7. Effective use of Java Core APIs
8. Pattern matching and switch expressions

---

**Author**: Java 21 LTS Demonstration Project
**Purpose**: Educational showcase of Java language features
**Java Version**: Java 21 (LTS) with Java 22/23 preview features documented
