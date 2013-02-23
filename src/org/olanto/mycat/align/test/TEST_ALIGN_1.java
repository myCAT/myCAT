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

package org.olanto.mycat.align.test;

import org.olanto.idxvli.IdxStructure;
import org.olanto.util.Timer;
import org.olanto.zahir.align.*;
import org.olanto.mapman.server.IntMap;
import org.olanto.senseos.SenseOS;

/**
 * test un alignement entre deux fichiers
 */
public class TEST_ALIGN_1 {

    private static IdxStructure id;
    private static Timer t1 = new Timer("global time");
    private static BiSentence d;

    /**
     * application de test
     * @param args sans
     */
    public static void main(String[] args) {

        IntMap map = AlignService.getAlign(SenseOS.getMYCAT_HOME()+"/corpus/txt/EN/E_2003_INF_2_Add4.txt",
                SenseOS.getMYCAT_HOME()+"/corpus/txt/FR/E_2003_INF_2_Add4.txt",
                "EN",
                "FR");


    }
}
