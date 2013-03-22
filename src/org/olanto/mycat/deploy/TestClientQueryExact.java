/**
 * ********
 * Copyright © 2010-2012 Olanto Foundation Geneva
 *
 * This file is part of myCAT.
 *
 * myCAT is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Affero General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * myCAT is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with myCAT. If not, see <http://www.gnu.org/licenses/>.
 *
 *********
 */
package org.olanto.mycat.deploy;

import org.olanto.conman.server.GetContentService;
import java.rmi.*;
import org.olanto.idxvli.server.*;
import static org.olanto.util.Messages.*;
import org.olanto.util.Timer;

/**
 * test une recherche
 *
 */
public class TestClientQueryExact {

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



        testnice("QUOTATION(\"for which an established\") IN[\"SOURCE.EN\" ANDL \"TARGET.FR\"]");
        testnice("QUOTATION(\"made by the President \") IN[\"SOURCE.EN\" ANDL \"TARGET.FR\"]");
        testnice("QUOTATION(\"Wto\") IN[\"SOURCE.EN\" ANDL \"TARGET.FR\"]");
        testnice("QUOTATION(\"tariff negotiations between\") IN[\"SOURCE.EN\" ANDL \"TARGET.FR\"]");
        testnice("QUOTATION(\"usa\") IN[\"SOURCE.EN\" ANDL \"TARGET.FR\"]");
        testnice("QUOTATION(\"USA\") IN[\"SOURCE.EN\" ANDL \"TARGET.FR\"]");
        testnice("QUOTATION(\"wto\") IN[\"SOURCE.EN\" ANDL \"TARGET.FR\"]");

    }

    static void testnice(String query) {
        try {
            Timer t1 = new Timer("------------- " + query);
            QLResultNice res = is.evalQLNice(query, 0, 1000, "NAME", true, false);
            msg("time:" + res.duration);
            msg("nbres:" + res.result.length);
            msg("query:" + res.query);
            msg("query2:" + res.query2);
            for (int i = 0; i < res.result.length; i++) {
                msg(i + "  docid: " + res.result[i]);
                msg("  docname: " + res.docname[i]);
                msg("  title: " + res.title[i]);
                msg("  clue: " + res.clue[i]);
                msg("");
            }
            t1.stop();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    static void testmore(String query) {
        try {
            Timer t1 = new Timer("------------- " + query);
            QLResultAndRank res = is.evalQLMore(query);
            msg("time:" + res.duration);
            msg("nbres:" + res.result.length);
            for (int i = 0; i < res.result.length; i++) {
                msg(i + "  docid: " + res.result[i]);
                msg("  docname: " + res.docName[i]);
                msg("");
            }
            t1.stop();
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }
}
