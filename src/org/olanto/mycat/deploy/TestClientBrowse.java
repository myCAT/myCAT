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

import org.olanto.conman.server.GetContentService;
import java.rmi.*;
import org.olanto.idxvli.server.*;
import static org.olanto.util.Messages.*;
import org.olanto.util.Timer;

/** test une recherche
 *
 */
public class TestClientBrowse {

    static IndexService_MyCat is;

    public static void main(String[] args) {

        is = GetContentService.getServiceMYCAT("rmi://localhost/VLI");
        try {
            showVector(is.getDictionnary().result);
            msg(is.getDocName(0));
            msg(is.getDocName(1));
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }


 
//        testnice("*glos3*",0,0,null,"ALPHA");
//        testnice("*Glos*",0,0,null,"ALPHA");
//        testnice("*FR*",0,0,null,"ALPHA");
//        testnice("*EN*",0,0,null,"ALPHA");
        String[] co = new String[1];
        co[0] = "COLLECTION.persian-collection¦unesco iran";
       testnice("*",0,0,null,"NAME");
     testnice("*",0,0,co,"NAME");
    testnice("*EN/*",0,0,co,"NAME");

    }

    static void testnice(String request, int start, int size, String[] collections, String order) {
        try {
            Timer t1 = new Timer("------------- " + request);
            QLResultNice res = is.browseNice(request, "RU", start,  size,  collections, order, false);
            msg("time:" + res.duration);
            msg("nbres:" + res.result.length);
            for (int i = 0; i < Math.min(10, res.result.length); i++) {
                msg(i + "  docid: " + res.result[i]);
                msg("  docname: " + res.docname[i]);
            }
            t1.stop();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

  
}
