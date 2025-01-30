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
package factory;

/**
 * @author oracle
 **/
public class CarFactory {

    public static Car getCar(String color, String engSize, String style, String type) {
        if ("RangeRover".equalsIgnoreCase(type)) return new RangeRover(color, engSize, style, type);
        else if ("RollsRoyce".equalsIgnoreCase(type)) return new RollsRoyce(color, engSize, style, type);

        return null;
    }
}
