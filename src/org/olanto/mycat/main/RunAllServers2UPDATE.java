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

package org.olanto.mycat.main;

import java.rmi.Naming;
import org.olanto.convsrv.server.ConvertService_BASIC;
import org.olanto.mycat.map.ConfigurationMapGetFromFile;
import org.olanto.mapman.server.GetMapService;
import org.olanto.mapman.server.MapService;
import org.olanto.idxvli.server.GetIndexService;
import org.olanto.idxvli.server.IndexService_MyCat;
import org.olanto.mycat.deploy.ConfigurationIndexingGetFromFile;
import org.olanto.mycat.deploy.RTUpdate;
import org.olanto.senseos.SenseOS;
import static org.olanto.util.Messages.*;

/**
 * lance l'index, les map et le convertisseur
 *
 * -server  -Xmx500m -Djava.rmi.server.codebase="file:///c:/JG/prog/myCAT/dist/mysearch.jar"  -Djava.security.policy="c:/MYCAT/rmi.policy"
 */
public class RunAllServers2UPDATE {

    public static void main(String[] args) {

        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
        } catch (Exception e) {
            System.out.println("RMI registry is probably running ...");
            //e.printStackTrace();
        }



        try {
            System.out.println("initialize map server ...");

            IndexService_MyCat idxsrv = GetIndexService.runServiceIDX(new ConfigurationIndexingGetFromFile(SenseOS.getMYCAT_HOME()+"/config/IDX_fix.xml"), "rmi://localhost/VLI");
            System.out.println("index server is ready ...");

            System.out.println("initialize map server ...");

            MapService mapsrv = GetMapService.runServiceMAP(new ConfigurationMapGetFromFile(SenseOS.getMYCAT_HOME()+"/config/MAP_fix.xml"), "rmi://localhost/MAP");


            System.out.println("map server is ready ...");


        } catch (Exception e) {
            error("During startup: Index", e);
        }
        try {
            System.out.println("initialisation du convertisseur ...");

            ConvertService_BASIC idxobj = new ConvertService_BASIC();

            System.out.println("Enregistrement du serveur");

            String name = "rmi://localhost/CONVSRV";
            System.out.println("name:" + name);
            Naming.rebind(name, idxobj);
            System.out.println("Server is ready");

        } catch (Exception e) {
            e.printStackTrace();
        }
        RTUpdate.main(null);
    }
}
