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
package streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author oracle
 */
public class Collect {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        List<Employee> eList = Employee.createShortList();

        List<Employee> nList = new ArrayList<>();

        // Collect CO Executives
        nList = eList.stream()
                .filter(e -> e.getRole().equals(Role.EXECUTIVE))
                .filter(e -> e.getState().equals("CO"))
                .sorted(Comparator.comparing(Employee::getSurName))
                .collect(Collectors.toList());

        System.out.println("\n== CO Bonus Details ==");

        nList.stream()
                .forEach(Employee::printSummary);

    }

}
