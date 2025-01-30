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
package DeadLiveLock;

/**
 * @author oracle
 */
public class Deadlock {
    public static void main(String[] args) {
        final Friend tom =
                new Friend("Tom");
        final Friend sam =
                new Friend("Sam");
        new Thread(new Runnable() {
            public void run() {
                tom.bow(sam);
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                sam.bow(tom);
            }
        }).start();
    }

    static class Friend {
        private final String name;

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public synchronized void bow(Friend bower) {
            System.out.format("%s: %s"
                            + "  has bowed to me!%n",
                    this.name, bower.getName());
            this.bowBack(this);
        }

        public synchronized void bowBack(Friend bower) {
            System.out.format("%s"
                            + " has bowed back to me!%n",
                    this.name, bower.getName());
        }
    }
}


