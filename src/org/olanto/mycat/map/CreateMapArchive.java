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

import org.olanto.util.Timer;
import org.olanto.mapman.MapArchiveStructure;
import org.olanto.senseos.SenseOS;

/**
 *
 * Test de map manager, création d'un nouveau map manager
 */
public class CreateMapArchive {

    private static MapArchiveStructure id;
    private static Timer t1 = new Timer("global time");

    public static void main(String[] args) {

        id = new MapArchiveStructure("NEW", new ConfigurationMapGetFromFile(SenseOS.getMYCAT_HOME()+"/config/MAP_fix.xml"));


        // id.Statistic.global();



        id.close();
        t1.stop();

    }
}
