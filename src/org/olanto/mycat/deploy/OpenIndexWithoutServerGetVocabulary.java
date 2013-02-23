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

import org.olanto.idxvli.*;
import org.olanto.senseos.SenseOS;
import org.olanto.util.Timer;

/**
 *  listes tous les mots indexés
 */
public class OpenIndexWithoutServerGetVocabulary {      // is an application, not an applet !

    static IdxStructure id;
    static Timer t1 = new Timer("global time");

    public static void main(String[] args) {
        id = new IdxStructure("INCREMENTAL", new ConfigurationIndexingGetFromFile(SenseOS.getMYCAT_HOME()+"/config/IDX_fix.xml"));

        int lastw = id.lastUpdatedWord;

        for (int i = 0; i < lastw; i++) {
            System.out.println(i + " = " + id.getStringforW(i));
        }

        t1.stop();

    }
}
