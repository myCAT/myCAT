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
public class TestClientQuerySimple {

    static IndexService_MyCat is;

    public static void main(String[] args) {

        is = GetContentService.getServiceMYCAT("rmi://localhost/VLI");
        try {
            showVector(is.getDictionnary().result);
            showVector(is.getCorpusLanguages());
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }


//test("armement OR nucléaire");
//test("weapon IN[\"SOURCE.EN\" AND \"TARGET.RU\"]");
//test("(weapon AND nuclear)IN[\"SOURCE.EN\" AND \"TARGET.RU\"]");
//test("NEAR(\"weapon\",\"nuclear\")IN[\"SOURCE.EN\" AND \"TARGET.RU\"]");
//test("QUOTATION(\"efforts to mainstream gender perspectives\") IN[\"SOURCE.EN\" AND \"TARGET.FR\"]");
//test("QUOTATION(\"financial the collappse\") ");
        test("QUOTATION(\"揽\") ");
    }

    static void test(String query) {
        try {
            Timer t1 = new Timer("------------- " + query);
            QLResultNice res = is.evalQLNice(query, 0, 200);
            msg("time:" + res.duration);
            msg("nbres:" + res.result.length);
            for (int i = 0; i < res.result.length; i++) {
                msg(i + "  docid: " + res.result[i]);
                msg("  docname: " + res.docname[i]);
                msg("");
            }
            t1.stop();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
