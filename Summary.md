# Wrangler Core Parser Enhancement

This document provides a detailed breakdown of the enhancements made to the `wrangler-core` and `wrangler-api` modules, focusing on parsing improvements for ByteSize and TimeDuration arguments.

---

## Part A: Project Overview and Setup

### 1. Project Overview

In Part A, the goal was to enhance the parsing capability of the Wrangler tool by adding support for new argument types such as byte sizes and time durations. This required updates to the grammar, parser, and token system within the wrangler-core module.

### 2. Key Accomplishments

- **Grammar Enhancements**: Modified the ANTLR grammar to support `ByteSizeArg` and `TimeDurationArg`.
- **Visitor Methods**: Added/updated visitor methods to handle the new parser rules.
- **TokenGroup Integration**: Integrated the parsed results into the token grouping system for consistent parsing output.

### 3. Technologies Used

- **Java**
- **Maven**
- **ANTLR**
- **CDAP Wrangler Plugin**

### 4. Changes Made

- Added/Modified ANTLR rules for:
  - `ByteSizeArg`
  - `TimeDurationArg`
- Implemented methods:
  - `visitByteSizeArg`
  - `visitTimeDurationArg`
- Added parsed tokens into `TokenGroup`.
- Ensured backwards compatibility with existing argument parsing rules.

---

## Part B: API Updates (`wrangler-api` module)

### 1. Overview

This part introduces two new token classes: `ByteSize.java` and `TimeDuration.java`. These classes extend the base `Token` class and provide enhanced functionality for handling byte sizes and time durations in a standardized way.

### 2. Changes Implemented

#### ByteSize.java

- Parses string values like `"10KB"`, `"2MB"`, `"1GB"`.
- Converts the parsed size into bytes.
- Exposes `getBytes()` method for easy retrieval.

#### TimeDuration.java

- Parses string values like `"100ms"`, `"2s"`, `"10min"`.
- Converts the parsed duration into milliseconds.
- Exposes `getMillis()` method for use in scheduling, delays, etc.

#### Token Types Update

- Added new token types:
  - `BYTE_SIZE`
  - `TIME_DURATION`
- These are now accepted as valid directive arguments.

---

### 3. Benefits

- **Improved Flexibility**: Byte sizes and time durations can now be parsed and utilized natively.
- **Enhanced Token System**: Expands the capabilities of the token system.
- **Better Developer Experience**: Reduces manual parsing and improves code readability.

---

### 4. Usage Examples

```java
ByteSize byteSize = new ByteSize("10KB");
System.out.println(byteSize.getBytes()); // Output: 10240

TimeDuration duration = new TimeDuration("150ms");
System.out.println(duration.getMillis()); // Output: 150

## Part C: Core Parser Updates (wrangler-core module)

### 1. Overview

This part involves updating the core parser visitor to handle the new grammar rules for byte size and time duration arguments, integrating them cleanly into the parsing pipeline.

### 2. Changes Implemented

- **Visitor Methods**:
  - Added `visitByteSizeArg(ctx)` and `visitTimeDurationArg(ctx)` in the parser visitor.
  - Modified `visitValue(ctx)` where necessary to support new types.

- **TokenGroup Integration**:
  - Used `ctx.getText()` to extract argument text.
  - Created appropriate `Token` instances (`ByteSize`, `TimeDuration`).
  - Added these tokens to the `TokenGroup` for downstream use.

### 3. Benefit

- Enables custom directive parsing using new data types.
- Integrates with the existing parsing framework and ANTLR rules.

---

## Part D: New Directive Implementation (wrangler-core module)

### 1. Overview

This part introduces a new directive that performs aggregation across byte size and time duration columns.

### 2. Directive Design

- **Directive Class**: Implemented a new class (e.g., `AggregateStatsDirective`) that implements the `Directive` interface.

- **define() Method**:
  - Accepts four required arguments:
    1. Source column name for byte size values.
    2. Source column name for time duration values.
    3. Target column name for total size.
    4. Target column name for total/average time.
  - Optional arguments for:
    - Unit conversions (`MB`, `GB`, `seconds`, `minutes`).
    - Aggregation type (`total`, `average`).

- **Initialization**:
  - Stores the source and target column names.
  - Parses optional arguments (units, aggregation type).

- **Execution Logic**:
  - Uses a store (via `ExecutorContext`) to accumulate totals across rows.
  - For each row:
    - Reads values from the byte size and time duration columns.
    - Converts to canonical units (bytes, milliseconds/nanoseconds).
    - Adds to the totals stored.
  - On finalization:
    - Retrieves the total values from the store.
    - Converts them based on unit arguments.
    - Returns a single new row with aggregated values:
      - `total_size_mb`, `total_time_sec`, etc.

### 3. Benefit

- Enables aggregation operations like total size and average duration.
- Makes it easier to analyze large datasets within pipelines.

---

## Part E: Testing (wrangler-core module)

### 1. ByteSize & TimeDuration Class Testing

- Verified correct parsing of inputs like:
  - `"10kb"`, `"1.5MB"`, `"2GB"`
  - `"5ms"`, `"2.1s"`, `"3min"`
- Asserted:
  - Correct canonical values using `.getBytes()` and `.getMillis()`.

### 2. Parser Testing

- Added tests in `GrammarBasedParserTest.java` / `RecipeCompilerTest.java`.
  - Valid syntax:
    - `"parse-as-bytes :column1"`, `"parse-as-duration :column2"`
  - Invalid syntax:
    - Ensured parser rejects malformed input strings.

### 3. Directive Unit Testing

- Added comprehensive unit tests for `AggregateStatsDirective`.
  - Verified aggregation of values across rows.
  - Checked correctness for both `total` and `average` logic.
  - Tested unit conversions (e.g., bytes → MB, ms → sec).
  - Validated output structure (target columns populated correctly).

---

## ✅ Summary

This project enhances the CDAP Wrangler Core module with powerful new parsing and aggregation capabilities. By introducing custom token types (`ByteSize`, `TimeDuration`), extending the grammar and parser visitor logic, and implementing a fully functional aggregation directive, we’ve made it easier to handle complex data transformation scenarios.

The changes are thoroughly tested across parsing, directive logic, and unit levels to ensure reliability and accuracy. These enhancements significantly improve the flexibility, usability, and performance of data processing within CDAP DataPrep.

---

## 📌 Future Enhancements

- Add support for additional units like microseconds or terabytes.
- Introduce customizable rounding/formatting for output values.
- Expand directive capabilities to support group-by and multi-column operations.


---
