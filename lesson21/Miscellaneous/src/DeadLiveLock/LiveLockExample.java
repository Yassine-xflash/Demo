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

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LiveLockExample {
    final Lock lock = new ReentrantLock();
    private double price;
    private int customerID;

    public LiveLockExample(int id, double amount) {
        this.customerID = id;
        this.price = amount;
    }

    public static void main(String[] args) throws InterruptedException {
        LiveLockExample Customer = new LiveLockExample(1, 3000.0);
        LiveLockExample vendor = new LiveLockExample(2, 3000.0);
        new Thread(() -> {
            try {
                while (!Customer.process(Customer, vendor, 3000.0))
                    continue;
            } catch (InterruptedException ex) {
                Logger.getLogger(LiveLockExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();

        new Thread(() -> {
            try {
                while (!vendor.process(vendor, Customer, 3000.0))
                    continue;
            } catch (InterruptedException ex) {
                Logger.getLogger(LiveLockExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }

    public boolean charge(double amount) throws InterruptedException {
        if (this.lock.tryLock()) {
            Thread.sleep(1000);
            price -= price;
            return true;
        } else {
            return false;
        }
    }

    public boolean refund(double amount) throws InterruptedException {
        if (this.lock.tryLock()) {
            Thread.sleep(1000);
            price += amount;
            return true;
        } else {
            return false;
        }
    }

    public boolean process(LiveLockExample from, LiveLockExample to, double amount) throws InterruptedException {
        if (from.charge(amount)) {
            System.out.println("Charging " + amount + " from " + customerID);
            if (to.refund(amount)) {
                System.out.println("Refunding " + amount + " to " + customerID);
                return true;
            } else {
                from.refund(amount);
                System.out.println("Refunding amout:  " + amount + " to " + customerID);
            }
        }
        return false;
    }
}

        
    

