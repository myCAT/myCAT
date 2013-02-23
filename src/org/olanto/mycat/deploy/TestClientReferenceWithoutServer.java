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

import org.olanto.idxvli.ref.UploadedFile;
import org.olanto.idxvli.ref.UtilsFiles;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.olanto.idxvli.server.*;
import org.olanto.util.Timer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.olanto.idxvli.IdxStructure;
import org.olanto.senseos.SenseOS;

/**
 * test une le service de quotation
 *
 */
public class TestClientReferenceWithoutServer {

    static IdxStructure id;

    public static void main(String[] args) throws FileNotFoundException {

        id = new IdxStructure("QUERY", new ConfigurationIndexingGetFromFile(SenseOS.getMYCAT_HOME() + "/config/IDX_fix.xml"));

        System.out.println(id.getFileNameForDocument(0));
        System.out.println(id.getFileNameForDocument(1));
        System.out.println(id.getFileNameForDocument(2));

//        testref(SenseOS.getMYCAT_HOME() + "/test/World_Bank¦0000188336.doc.txt", SenseOS.getMYCAT_HOME() + "/test/World_Bank¦0000188336.doc.res.html", null , 3);
//       testref(SenseOS.getMYCAT_HOME() + "/test/g245_e.txt", SenseOS.getMYCAT_HOME() + "/test/g245_e.res.html", null , 3);
       testref(SenseOS.getMYCAT_HOME() + "/test/0000188336_EN.doc.txt", SenseOS.getMYCAT_HOME() + "/test/0000188336_EN.doc.txt.res.html", null , 3);
//       testref(SenseOS.getMYCAT_HOME() + "/test/testcdt.txt", SenseOS.getMYCAT_HOME() + "/test/testcdt.res.html", null , 5);

    }

    public static void testref(String fileNameIn, String fileNameOut, String[] collect, int nbref) throws FileNotFoundException {

        // test ref
        FileInputStream in = new FileInputStream(fileNameIn);
        String ex = "";
        ex += UtilsFiles.file2String(in, "UTF-8");
        UploadedFile up = new UploadedFile(ex, "test.txt");

        Timer t1 = new Timer("-------------  ref ");
//        for (int i = 0; i < 20; i++) {
            REFResultNice ref = id.getReferences(up, nbref, "EN", "FR", collect);
//        }
        t1.stop();
//            msg("ref:" + ref.htmlref);
//            msg("nbref:" + ref.nbref);
//            msg("reflength:" + ref.listofref.length);
//            msg("ref:" + ref.listofref[0]);
//            msg("ref:" + ref.reftext[0]);
        FileOutputStream out = new FileOutputStream(fileNameOut);
        try {
            out.write(ref.htmlref.getBytes());
            out.close();
        } catch (IOException ex1) {
            Logger.getLogger(TestClientReferenceWithoutServer.class.getName()).log(Level.SEVERE, null, ex1);
        }

    }
}
