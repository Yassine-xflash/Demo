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

import java.util.List;

/**
 * @author oracle
 */
public class CalcSum {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<Employee> eList = Employee.createShortList();

        System.out.println("\n== Total CO Bonus ==");
        double result;

        result = eList.stream()
                .filter(e -> e.getRole().equals(Role.EXECUTIVE))
                .filter(e -> e.getState().equals("CO"))
                .mapToDouble(
                        e -> e.getSalary() * Bonus.byRole(e.getRole()))
                .sum();

        System.out.printf("Total Bonuses paid: $%,6.2f %n",
                result);

        System.out.println("\n== Total CO Bonus Details ==");

        result = eList.stream()
                .filter(e -> e.getRole().equals(Role.EXECUTIVE))
                .filter(e -> e.getState().equals("CO"))
                .peek(e -> System.out.print("Name: "
                        + e.getGivenName() + " " + e.getSurName() + " "))
                .mapToDouble(e -> e.getSalary() * Bonus.byRole(e.getRole()))
                .peek(d -> System.out.printf("Bonus paid: $%,6.2f %n", d))
                .sum();

        System.out.printf("Total Bonuses paid: $%,6.2f %n", result);
    }


}
