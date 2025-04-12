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
