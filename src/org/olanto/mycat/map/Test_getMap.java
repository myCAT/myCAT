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
import org.olanto.mapman.server.AlignBiText;

/**
 * Test du service des map
 */
public class Test_getMap {

    /**
     * application de test
     * @param args sans
     */
    public static void main(String[] args) {
        test("EN/Activity_Report_and_Work_Programme¦Activity report_2003¦cdt_ra_2003.pdf.txt", "EN", "FR");
        test("EN/Activity_Report_and_Work_Programme¦Activity report_2006¦ra_2006.pdf.txt", "EN", "HU");
        test("HU/Activity_Report_and_Work_Programme¦Activity report_2007¦cdt_rapport_2007.pdf.txt", "HU", "LV");

    }

    static void test(String fn, String langso, String langta) {
        Timer t1 = new Timer("test " + fn + ":");
        AlignBiText ab = new AlignBiText(fn, langso, langta, "transmission", 10, 10);
        t1.stop();
        System.out.println("source " + ab.source.nblines + "\n");
        System.out.println("target " + ab.target.nblines + "\n");
        System.out.println("map from " + ab.map.from.length + "\n");
        System.out.println("map to " + ab.map.to.length + "\n");
        System.out.println("Name " + ab.source.uri + "\n");

    }
}
