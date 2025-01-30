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
package singleton;

/**
 * @author oracle
 */
public final class PeerSingleton {

    private static final String[] hostNames = new String[5];
    private static final PeerSingleton instance = new PeerSingleton();

    static {
        // Read from File
        hostNames[0] = "192.168.1.2";
        hostNames[1] = "192.168.1.4";
        hostNames[2] = "192.168.2.3";
        hostNames[3] = "192.168.2.5";
        hostNames[4] = "192.168.2.14";
    }

    private PeerSingleton() {
    }

    public static PeerSingleton getInstance() {
        return instance;
    }

    public String[] getHostNames() {
        return hostNames;
    }
}
