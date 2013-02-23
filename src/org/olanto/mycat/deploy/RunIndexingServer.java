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

import static org.olanto.util.Messages.*;
import org.olanto.idxvli.server.*;
import org.olanto.senseos.SenseOS;

/** index et stop
 *
 * -server  -Xmx500m -Djava.rmi.server.codebase="file:///c:/JG/prog/myCAT/dist/mysearch.jar"  -Djava.security.policy="c:/MYCAT/rmi.policy"
 */
public class RunIndexingServer {

    public static void main(String[] args) {

        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
        } catch (Exception e) {
            System.out.println("RMI registry is probably running ...");
            //e.printStackTrace();
        }


        try {
            System.out.println("initialize index server ...");

            IndexService_MyCat idxsrv = GetIndexService.runServiceIDX(new ConfigurationIndexingGetFromFile(SenseOS.getMYCAT_HOME()+"/config/IDX_fix.xml"), "rmi://localhost/VLI");

            System.out.println("update index ...");

            IndexDirectory.updateIndex(idxsrv); // need to stop and restart
            msg("current Index manager is closing ...");
            idxsrv.quit();
            msg("current Index manager is closed");



        } catch (Exception e) {
            error("During startup: Index", e);
        }


    }
}
