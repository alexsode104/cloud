//
// MIT License
//
// Copyright (c) 2020 IntellectualSites
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.
//
package com.intellectualsites.commands.exceptions;

import com.intellectualsites.commands.components.CommandComponent;
import com.intellectualsites.commands.sender.CommandSender;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Exception sent when a {@link CommandSender} inputs invalid command syntax
 */
public class InvalidSyntaxException extends CommandParseException {

    private final String correctSyntax;

    public InvalidSyntaxException(@Nonnull final String correctSyntax,
                                  @Nonnull final CommandSender commandSender,
                                  @Nonnull final List<CommandComponent<?, ?>> currentChain) {
        super(commandSender, currentChain);
        this.correctSyntax = correctSyntax;
    }

    /**
     * Get the correct syntax of the command
     *
     * @return Correct command syntax
     */
    @Nonnull
    public String getCorrectSyntax() {
        return this.correctSyntax;
    }

    @Override
    public String getMessage() {
        return String.format("Invalid command syntax. Correct syntax is: %s", this.correctSyntax);
    }

}
