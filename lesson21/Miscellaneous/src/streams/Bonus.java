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

/**
 * @author oracle
 */

public enum Bonus {

    STAFF(0.02),
    MANAGER(0.04),
    EXECUTIVE(0.06);

    private final double percent;

    Bonus(double percent) {
        this.percent = percent;
    }

    public static double byRole(Role r) {

        double bonusPercent = 0.0d;

        switch (r) {
            case EXECUTIVE:
                bonusPercent = EXECUTIVE.percent;
                break;
            case MANAGER:
                bonusPercent = MANAGER.percent;
                break;
            case STAFF:
                bonusPercent = STAFF.percent;
                break;
        }

        return bonusPercent;
    }

    public double percent() {
        return percent;
    }


}
