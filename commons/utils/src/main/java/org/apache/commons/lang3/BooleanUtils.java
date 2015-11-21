/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3;

public class BooleanUtils {

    /**
     * <p>
     * {@code BooleanUtils} instances should NOT be constructed in standard programming. Instead, the class should be
     * used as {@code BooleanUtils.negate(true);}.</p>
     *
     * <p>
     * This constructor is public to permit tools that require a JavaBean instance to operate.</p>
     */
    public BooleanUtils() {
        super();
    }

    /**
     * <p>
     * Converts a String to a Boolean throwing an exception if no match found.</p>
     *
     * <pre>
     *   BooleanUtils.toBoolean("true", "true", "false")  = true
     *   BooleanUtils.toBoolean("false", "true", "false") = false
     * </pre>
     *
     * @param str the String to check
     * @param trueString the String to match for {@code true} (case sensitive), may be {@code null}
     * @param falseString the String to match for {@code false} (case sensitive), may be {@code null}
     * @return the boolean value of the string
     * @throws IllegalArgumentException if the String doesn't match
     */
    public static boolean toBoolean(final String str, final String trueString, final String falseString) {
        if (str == trueString) {
            return true;
        } else if (str == falseString) {
            return false;
        } else if (str != null) {
            if (str.equals(trueString)) {
                return true;
            } else if (str.equals(falseString)) {
                return false;
            }
        }
        // no match
        throw new IllegalArgumentException("The String did not match either specified value");
    }
}
