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
 * This class represents a time duration, typically in terms of hours, minutes, and seconds.
 * It provides methods for converting between different time units and performing time-related calculations.
 */
public class TimeDuration implements Token {
    private long valueInMillis;

    public TimeDuration(String token) {
        // Regex to match the value followed by the time unit (e.g., 10ms, 5s)
        Pattern pattern = Pattern.compile("(\\d+)(ms|s|m|h)");
        Matcher matcher = pattern.matcher(token);

        if (matcher.matches()) {
            long value = Long.parseLong(matcher.group(1));
            String unit = matcher.group(2);

            // Convert the value to milliseconds based on the unit
            switch (unit) {
                case "ms":
                    this.valueInMillis = value;
                    break;
                case "s":
                    this.valueInMillis = value * 1000;
                    break;
                case "m":
                    this.valueInMillis = value * 1000 * 60;
                    break;
                case "h":
                    this.valueInMillis = value * 1000 * 60 * 60;
                    break;
                default:
                    this.valueInMillis = value;
                    break;
            }
        }
    }

    public long getMilliseconds() {
        return valueInMillis;
    }

    @Override
    public Object value() {
        return getMilliseconds();
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION; // Return the corresponding TokenType
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(valueInMillis);
    }
}
