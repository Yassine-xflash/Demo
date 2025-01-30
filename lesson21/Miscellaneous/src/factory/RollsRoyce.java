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
public class RollsRoyce extends Car {
    public RollsRoyce(String color, String engsize, String style, String type) {
        this.color = color;
        this.engineSize = engsize;
        this.style = style;
        this.type = type;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public String getEngineSize() {
        return this.engineSize;
    }

    @Override
    public String getStyle() {
        return this.style;
    }

    @Override
    public String getType() {
        return this.type;
    }
}
