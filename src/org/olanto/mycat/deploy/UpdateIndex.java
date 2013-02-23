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

import org.olanto.idxvli.server.IndexDirectory;
import org.olanto.idxvli.server.IndexService_MyCat;
import java.rmi.Naming;
import java.rmi.Remote;

/**
 * mise à jour de l'index
 *
 */
public class UpdateIndex {

    public static void main(String[] args) {


        try {

            System.out.println("connect to serveur");

            Remote r = Naming.lookup("rmi://localhost/VLI");

            System.out.println("access to serveur");

            if (r instanceof IndexService_MyCat) {
                IndexService_MyCat ismain = ((IndexService_MyCat) r);
                IndexDirectory.updateIndex(ismain);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
