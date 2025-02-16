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

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author oracle
 */
public class StarvationExample {

    public static void main(String[] args) {
        ThreadWorker worker = new ThreadWorker();

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        worker.work();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(StarvationExample.class.
                                getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }).start();
        }
    }
}
