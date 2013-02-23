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
import java.rmi.*;
import org.olanto.idxvli.server.*;
import static org.olanto.util.Messages.*;

/** Test
 *
 *<p
 */
public class TestClientServices {

    public static void main(String[] args) {


        try {

            System.out.println("connect to serveur");

            Remote r = Naming.lookup("rmi://localhost/VLI");

            System.out.println("access to serveur");

            if (r instanceof IndexService_MyCat) {
                IndexService_MyCat is = ((IndexService_MyCat) r);
                String s = is.getInformation();

                System.out.println("chaîne renvoyée = " + s);
                System.out.println("corpus source = " + is.getROOT_CORPUS_SOURCE());
                System.out.println("corpus txt = " + is.getROOT_CORPUS_TXT());

                System.out.println("docname = " + is.getDocName(100));
                String[] col = is.getCollection(SenseOS.getMYCAT_HOME()+"/corpus/txt/EN/Admin¦2009¦TM_2009_1006.txt");
                System.out.println("Collections = " + col[0]);



                msg("end ...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
