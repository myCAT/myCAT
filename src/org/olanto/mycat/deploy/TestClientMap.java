/**********
    Copyright © 2010-2012 Olanto Foundation Geneva

   This file is part of myCAT.

   myCAT is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of
    the License, or (at your option) any later version.

    myCAT is distributed in the hope that it will be useful, but
    WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
    See the GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with myCAT.  If not, see <http://www.gnu.org/licenses/>.

**********/

package org.olanto.mycat.deploy;

import java.rmi.*;
import static org.olanto.util.Messages.*;

import org.olanto.mapman.server.GetMapService;
import org.olanto.mapman.server.IntMap;
import org.olanto.mapman.server.MapService;

/** test une recherche d'alignement
 * 
 */
public class TestClientMap {

    static MapService ms;

    public static void main(String[] args) {

        ms = GetMapService.getServiceMAP("rmi://localhost/MAP");
        try {
            msg(ms.getStatistic());

            IntMap m = ms.getMap(329, "EN", "DE");

            msg("from " + m.from.length + " - " + m.from[0] + " - " + m.from[m.from.length - 1]
                    + " to " + m.to.length + " - " + m.to[0] + " - " + m.to[m.to.length - 1]);

            m = ms.getMap(329, "EN", "EO");  // test error

            m = ms.getMap(329, "EN", "RU"); // test error


        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
