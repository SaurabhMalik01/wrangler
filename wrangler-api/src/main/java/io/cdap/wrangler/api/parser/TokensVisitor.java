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

import java.util.ArrayList;
import java.util.List;

/**
 * Simple visitor to validate and extract arguments based on UsageDefinition.
 */
public class TokensVisitor {

  private final UsageDefinition usage;

  public TokensVisitor(UsageDefinition usage) {
    this.usage = usage;
  }

  public Arguments visit(List<String> tokens) throws DirectiveParseException {
    List<TokenDefinition> expectedTokens = usage.getTokens();
    List<Argument> args = new ArrayList<>();

    int requiredCount = expectedTokens.size() - usage.getOptionalTokensCount();
    if (tokens.size() < requiredCount || tokens.size() > expectedTokens.size()) {
      throw new DirectiveParseException("Invalid number of arguments for directive: " + usage.getDirectiveName());
    }

    for (int i = 0; i < expectedTokens.size(); i++) {
      TokenDefinition expected = expectedTokens.get(i);

      String value = i < tokens.size() ? tokens.get(i) : null;

      if (value == null && !expected.optional()) {
        throw new DirectiveParseException("Missing required argument: " + expected.name());
      }

      args.add(new Argument(expected.name(), value));
    }

    return new Arguments(args);
  }
}
