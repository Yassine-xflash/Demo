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
public class Main {
    public static void main(String[] args) {
        PeerSingleton peerList01 = PeerSingleton.getInstance();
        PeerSingleton peerList02 = PeerSingleton.getInstance();

        for (String hostName : peerList01.getHostNames()) {
            System.out.println("Host name: " + hostName);
        }

        System.out.println();
        for (String hostName : peerList02.getHostNames()) {
            System.out.println("Host name: " + hostName);
        }
    }
}
