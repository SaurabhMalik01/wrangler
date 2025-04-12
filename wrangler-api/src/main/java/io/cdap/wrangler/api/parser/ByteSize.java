/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * This class represents a byte size in different units.
 * It provides methods to convert between units such as bytes, kilobytes, and megabytes.
 */
public class ByteSize implements Token {
    private final long valueInBytes;

    public ByteSize(String token) {
        // Regex to match the value followed by the unit (e.g., 10KB, 5MB)
        Pattern pattern = Pattern.compile("(\\d+)(KB|MB|GB|TB)");
        Matcher matcher = pattern.matcher(token);

        if (matcher.matches()) {
            long value = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2);

            // Convert the value to bytes based on the unit
            switch (unit) {
                case "KB":
                    this.valueInBytes = value * 1024;
                    break;
                case "MB":
                    this.valueInBytes = value * 1024 * 1024;
                    break;
                case "GB":
                    this.valueInBytes = value * 1024 * 1024 * 1024;
                    break;
                case "TB":
                    this.valueInBytes = value * 1024 * 1024 * 1024 * 1024;
                    break;
                default:
                    this.valueInBytes = value; // Just in case an invalid unit is given
                    break;
            }
        } else {
            throw new IllegalArgumentException("Invalid ByteSize format: " + token);
        }
    }

    // Getter to retrieve the byte value
    public long getBytes() {
        return valueInBytes;
    }

    @Override
    public Object value() {
        return valueInBytes;
    }

    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    @Override
    public JsonElement toJson() {
        // Return the value in bytes as a JsonElement
        return new JsonPrimitive(valueInBytes);
    }
}
