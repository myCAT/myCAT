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

import java.rmi.*;
import static org.olanto.util.Messages.*;
import org.olanto.idxvli.server.*;
import org.olanto.senseos.SenseOS;

/**
 * reconstruit les structures de l'indexeur
 *
 * 
 */
public class RunIndexingServerFromEmpty {

    public static void main(String[] args) {


        try {
            System.out.println("initialisation de l'indexeur ...");

            Server_MyCat idxobj = new Server_MyCat();
            idxobj.getAndInit(new ConfigurationIndexingGetFromFile(SenseOS.getMYCAT_HOME()+"/config/IDX_fix.xml"), "NEW", false);

            System.out.println("Enregistrement du serveur");

            //      String name="rmi://"+java.net.InetAddress.getLocalHost()+"/IDX";
            String name = "rmi://localhost/VLI";
            System.out.println("name:" + name);
            Naming.rebind(name, idxobj);
            System.out.println("Serveur lancé");

        } catch (Exception e) {
            error("Serveur Idx", e);
        }


    }
}
