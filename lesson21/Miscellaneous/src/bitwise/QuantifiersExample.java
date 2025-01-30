/*
 * Copyright (C) 2021 Oracle
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package bitwise;

/**
 * @author oracle
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuantifiersExample {
    public static void main(String[] args) {
        String t = "Longlonglong ago, in a galaxy far far away.";

        Pattern p1 = Pattern.compile("ago.*");
        Matcher m1 = p1.matcher(t);
        if (m1.find()) System.out.println("Found: " + m1.group());

        Pattern p2 = Pattern.compile("gal.{3}");
        Matcher m2 = p2.matcher(t);
        if (m2.find()) System.out.println("Found: " + m2.group());

        Pattern p3 = Pattern.compile("(long){2}");
        Matcher m3 = p3.matcher(t);
        if (m3.find()) System.out.println("Found: " + m3.group());
    }
}

