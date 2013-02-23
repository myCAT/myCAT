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

import org.olanto.senseos.SenseOS;
import org.olanto.idxvli.*;
import org.olanto.idxvli.server.QLResultNice;
import org.olanto.util.Timer;
import static org.olanto.util.Messages.*;

/**
 *  lance en mode query et test
 */
public class OpenIndexWithoutServerTestQuery {      // is an application, not an applet !

    static IdxStructure id;
    static Timer t1 = new Timer("global time");

    public static void main(String[] args) {
        id = new IdxStructure("QUERY", new ConfigurationIndexingGetFromFile(SenseOS.getMYCAT_HOME()+"/config/IDX_fix.xml"));
        test("QUOTATION(\"xxx-111\") ");
        t1.stop();
    }

    static void test(String query) {
        try {
            Timer t1 = new Timer("------------- " + query);
            QLResultNice res = id.evalQLNice(null, query, 0, 200);
            msg("time:" + res.duration);
            msg("nbres:" + res.result.length);
            for (int i = 0; i < res.result.length; i++) {
                msg(i + "  docid: " + res.result[i]);
                msg("  docname: " + res.docname[i]);
                msg("");
            }
            t1.stop();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
