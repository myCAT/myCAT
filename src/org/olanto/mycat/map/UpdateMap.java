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

package org.olanto.mycat.map;

import org.olanto.mapman.mapper.MapAll;
import org.olanto.mapman.server.GetMapService;
import org.olanto.mapman.server.MapService;

/** mise à jour des map en utilisant le service

 */
public class UpdateMap {

    public static void main(String[] args) {

        MapService mapsrv = GetMapService.getServiceMAP("rmi://localhost/MAP");

        System.out.println("update map ...");

        MapAll.updateMap(mapsrv);

        System.out.println("map server is ready ...");

    }
}
