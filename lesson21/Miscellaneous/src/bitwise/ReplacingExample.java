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

public class ReplacingExample {
    public static void main(String[] args) {
        String header = "<h1>This is an H1</h1>";

        Pattern p1 = Pattern.compile("h1");
        Matcher m1 = p1.matcher(header);
        if (m1.find()) {
            header = m1.replaceAll("p");
            System.out.println(header);
        }
    }
}


