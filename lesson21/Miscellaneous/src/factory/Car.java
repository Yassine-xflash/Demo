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
abstract class Car {
    String color;
    String engineSize;
    String style;
    String type;

    public abstract String getColor();

    public abstract String getEngineSize();

    public abstract String getStyle();

    public abstract String getType();

    @Override
    public String toString() {
        return "Color= " + this.getColor() + ", EngineSise=" + this.getEngineSize() + ", Style=" + this.getStyle() + ", Type=" + this.getType();
    }
}
