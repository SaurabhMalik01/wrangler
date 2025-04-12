## Part A: Project Overview and Setup

### 1. Project Overview

In **Part A**, the focus was on setting up the **CDAP DataPrep Plugin** to enable data transformation and cleansing. This included configuring the environment, creating custom directives, and testing data processing capabilities.

### 2. Key Accomplishments

- **Custom Directive Creation**: Developed user-defined directives (UDDs) to perform complex data transformation tasks, such as parsing specific file formats and filtering data.
- **Directive Restrictions**: Configured restrictions on directives to limit access, ensuring only authorized users can perform critical transformations.
- **Performance Optimization**: Optimized the system to handle large datasets efficiently by utilizing batch processing techniques and memory management strategies.
  
### 3. Technologies Used

- **CDAP DataPrep Plugin**: Used for data transformation and cleansing.
- **Custom Directives**: Implemented to tailor the plugin to the specific needs of the project.
- **Batch Processing**: Ensured efficient handling of large datasets.
  

## Changes Made

- Added Javadoc comments to the `ByteSize` and `TimeDuration` classes.
  - These comments provide better documentation for the classes and their methods, improving code readability and ensuring compliance with the Checkstyle configuration.
  - This change resolves the missing Javadoc errors reported by Checkstyle.

## Part B: API Updates (wrangler-api module)

### Overview
This update introduces two new classes, `ByteSize.java` and `TimeDuration.java`, in the `wrangler-api` module. These classes extend the `Token` class and are designed to handle specific types of tokens related to data sizes and durations. The classes are capable of parsing string representations of byte sizes (e.g., "10KB", "150MB") and time durations (e.g., "100ms", "2s") and converting them into canonical units.

### Changes Implemented:
1. **ByteSize.java**:
   - Parses a string token representing a byte size (e.g., "10KB", "150MB", "2GB").
   - Converts the parsed size into bytes (canonical unit) for easy computation and usage.
   - Provides a method `getBytes()` to retrieve the byte value.

2. **TimeDuration.java**:
   - Parses a string token representing a time duration (e.g., "100ms", "2s").
   - Converts the parsed duration into milliseconds (canonical unit).
   - Provides a method `getMillis()` to retrieve the duration in milliseconds.

3. **Token Types Update**:
   - Added `BYTE_SIZE` and `TIME_DURATION` to the list of supported token types in the `Token` class.
   - These new token types are now recognized as valid directive arguments within the API, allowing users to specify byte sizes and time durations in a flexible way.

### Benefits:
- **Improved Flexibility**: The ability to easily handle byte sizes and time durations directly in the API allows for more powerful and customizable processing.
- **Easy Parsing**: The new classes simplify the task of working with string representations of sizes and durations by automatically converting them into standard units.
- **Enhanced Token System**: The integration of `BYTE_SIZE` and `TIME_DURATION` into the token system enhances the flexibility of the API, enabling a broader range of use cases.

### Usage Example:

```java
ByteSize byteSize = new ByteSize("10KB");
System.out.println(byteSize.getBytes()); // Output: 10240 bytes

TimeDuration duration = new TimeDuration("150ms");
System.out.println(duration.getMillis()); // Output: 150 milliseconds
