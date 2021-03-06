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
package com.intellectualsites.commands.components.standard;

import com.intellectualsites.commands.components.CommandComponent;
import com.intellectualsites.commands.components.parser.ComponentParseResult;
import com.intellectualsites.commands.components.parser.ComponentParser;
import com.intellectualsites.commands.context.CommandContext;
import com.intellectualsites.commands.exceptions.parsing.NumberParseException;
import com.intellectualsites.commands.sender.CommandSender;

import javax.annotation.Nonnull;
import java.util.Queue;

@SuppressWarnings("unused")
public class IntegerComponent<C extends CommandSender> extends CommandComponent<C, Integer> {

    private final int min;
    private final int max;

    private IntegerComponent(final boolean required, @Nonnull final String name, final int min, final int max, final String defaultValue) {
        super(required, name, new IntegerParser<>(min, max), defaultValue);
        this.min = min;
        this.max = max;
    }

    @Nonnull public static <C extends CommandSender> Builder<C> newBuilder(@Nonnull final String name) {
        return new Builder<>(name);
    }

    @Nonnull public static <C extends CommandSender> CommandComponent<C, Integer> required(@Nonnull final String name) {
        return IntegerComponent.<C>newBuilder(name).asRequired().build();
    }

    @Nonnull public static <C extends CommandSender> CommandComponent<C, Integer> optional(@Nonnull final String name) {
        return IntegerComponent.<C>newBuilder(name).asOptional().build();
    }

    @Nonnull public static <C extends CommandSender> CommandComponent<C, Integer> optional(@Nonnull final String name, final int defaultNum) {
        return IntegerComponent.<C>newBuilder(name).asOptionalWithDefault(Integer.toString(defaultNum)).build();
    }


    public static final class Builder<C extends CommandSender> extends CommandComponent.Builder<C, Integer> {

        private int min = Integer.MIN_VALUE;
        private int max = Integer.MAX_VALUE;

        protected Builder(@Nonnull final String name) {
            super(name);
        }

        @Nonnull public Builder<C> withMin(final int min) {
            this.min = min;
            return this;
        }

        @Nonnull public Builder<C> withMax(final int max) {
            this.max = max;
            return this;
        }

        @Nonnull
        @Override
        public IntegerComponent<C> build() {
            return new IntegerComponent<>(this.required, this.name, this.min, this.max, this.defaultValue);
        }

    }


    /**
     * Get the minimum accepted integer that could have been parsed
     *
     * @return Minimum integer
     */
    public int getMin() {
        return this.min;
    }

    /**
     * Get the maximum accepted integer that could have been parsed
     *
     * @return Maximum integer
     */
    public int getMax() {
        return this.max;
    }


    private static final class IntegerParser<C extends CommandSender> implements ComponentParser<C, Integer> {

        private final int min;
        private final int max;

        public IntegerParser(final int min, final int max) {
            this.min = min;
            this.max = max;
        }

        @Nonnull
        @Override
        public ComponentParseResult<Integer> parse(
                @Nonnull final CommandContext<C> commandContext,
                @Nonnull final Queue<String> inputQueue) {
            final String input = inputQueue.peek();
            if (input == null) {
                return ComponentParseResult.failure(new NullPointerException("No input was provided"));
            }
            try {
                final int value = Integer.parseInt(input);
                if (value < this.min || value > this.max) {
                    return ComponentParseResult.failure(new IntegerParseException(input, this.min, this.max));
                }
                inputQueue.remove();
                return ComponentParseResult.success(value);
            } catch (final Exception e) {
                return ComponentParseResult.failure(new IntegerParseException(input, this.min, this.max));
            }
        }

    }


    public static final class IntegerParseException extends NumberParseException {

        public IntegerParseException(@Nonnull final String input, final int min, final int max) {
            super(input, min, max);
        }

        @Override
        public boolean hasMin() {
            return this.getMin().intValue() != Integer.MIN_VALUE;
        }

        @Override
        public boolean hasMax() {
            return this.getMax().intValue() != Integer.MAX_VALUE;
        }

        @Override
        public String getNumberType() {
            return "integer";
        }

    }

}
