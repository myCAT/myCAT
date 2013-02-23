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

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import org.olanto.idxvli.IdxStructure;
import org.olanto.senseos.SenseOS;

/** test une le service de quotation
 *
 */
public class TestDateAndSortWithoutServer {

 static IdxStructure id;
 
    public static void main(String[] args) throws FileNotFoundException {

        id = new IdxStructure("QUERY", new ConfigurationIndexingGetFromFile(SenseOS.getMYCAT_HOME()+"/config/IDX_fix.xml"));

             infoOnDoc(0);
             infoOnDoc(10);
             infoOnDoc(20);
            infoOnDoc(30);
         String[] expandtest=id.wordExpander.getExpand("*", 100000000);
        System.out.println("   expand all word:"+expandtest.length);
         expandtest=id.wordExpander.getExpand("*a*", 100000000);
        System.out.println("   expand *a*:"+expandtest.length);
         expandtest=id.docNameExpander.getExpand("*", 100000000);
        System.out.println("   expand for file *:"+expandtest.length);
 
    }
static void infoOnDoc(int i){
       System.out.println("------ id doc:"+i);
       System.out.println("   name:"+id.getFileNameForDocument(i));
       System.out.println("   date:"+id.getDateOfD(i)+" - "+(new SimpleDateFormat("dd/MM/yyyy")).format(id.getDateOfD(i)));
       System.out.println("   size:"+id.getLengthOfD(i));
       
       
 }

}
