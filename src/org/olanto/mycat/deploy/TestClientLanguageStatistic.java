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

import org.olanto.idxvli.doc.PropertiesList;
import java.rmi.*;
import org.olanto.idxvli.server.*;
import org.olanto.idxvli.util.SetOfBits;
import static org.olanto.util.Messages.*;

/**
 * rapport sur les langues
 */
public class TestClientLanguageStatistic {

    static IndexService_MyCat is;

    public static void main(String[] args) {


        try {

            System.out.println("connect to serveur");

            Remote r = Naming.lookup("rmi://localhost/VLI");

            System.out.println("access to serveur");

            if (r instanceof IndexService_MyCat) {
                is = ((IndexService_MyCat) r);
                String s = is.getInformation();
                System.out.println("chaîne renvoyée = " + s);

                inventoryOf();




                msg("end ...");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void inventoryOf() {
        msg("");
        msg("");
        msg("---------- ");
        try {
            msg("---- all properties:");
            PropertiesList prop = is.getDictionnary();
            showVector(prop.result);
            msg("---- all properties SOURCE:");
            prop = is.getDictionnary("SOURCE.");
            showVector(prop.result);
            msg("---- all properties TARGET:");
            prop = is.getDictionnary("TARGET.");
            showVector(prop.result);
            msg("#doc:" + is.getSize());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        msg("----- Source ---------------------------");
        countSet("SOURCE.EN");
        countSet("SOURCE.FR");
        countSet("SOURCE.RU");
        msg("----- Target ---------------------------");
        countSet("TARGET.EN");
        countSet("TARGET.FR");
        countSet("TARGET.RU");
    }

    static void countSet(String setName) {
        try {
            int lastdoc = is.getSize();
            int count = 0;
            SetOfBits sob = is.satisfyThisProperty(setName);
            if (sob == null) {
                msg("no property for " + setName + " :" + count);
                return;
            }

            for (int i = 0; i < lastdoc; i++) {
                if (sob.get(i)) {
                    count++;
                }
            }
            msg("count for " + setName + " :" + count);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
