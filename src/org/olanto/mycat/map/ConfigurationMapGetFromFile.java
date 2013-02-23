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

package org.olanto.mycat.map;

import org.olanto.senseos.SenseOS;
import java.util.regex.Pattern;
import org.olanto.mapman.MapArchiveInit;
import java.io.FileInputStream;
import java.util.Properties;
import static org.olanto.idxvli.IdxEnum.*;
import static org.olanto.mapman.MapArchiveConstant.*;
import org.olanto.mapman.ParseSetOfWords;
import org.olanto.mapman.ParseTokenDefinition;
import static org.olanto.util.Messages.*;

/**
 * Une classe pour initialiser les constantes.
 */
public class ConfigurationMapGetFromFile implements MapArchiveInit {

    String fileName = "to be initialised";
    Properties prop;

    /** cr�e l'attache de cette classe.
     */
    public ConfigurationMapGetFromFile() {
    }

    /**
     * charge la configuration depuis un fichier de properties
     * @param fileName nom du fichier
     */
    public ConfigurationMapGetFromFile(String fileName) {
        this.fileName = fileName;
        FileInputStream f = null;
        try {
            f = new FileInputStream(fileName);
        } catch (Exception e) {
            error_fatal("cannot find properties file:" + fileName);
        }
        try {
            prop = new Properties();
            prop.loadFromXML(f);
        } catch (Exception e) {
            error("errors in properties file:" + fileName, e);
            System.exit(0);
        }
        msg("properties from: " + fileName);
        prop.list(System.out);
    }

    /** initialisation permanante des constantes.
     * Ces constantes choisies d�finitivement pour toute la dur�e de la vie de l'index.
     */
    public void InitPermanent() {

        DOC_MAXBIT = Integer.parseInt(prop.getProperty("DOC_MAXBIT", "20"));


        DOC_MAX = (int) Math.pow(2, DOC_MAXBIT);  // recalcule

        LANGPAIR_MAXBIT = 5;
        LANGPAIR_MAX = (int) Math.pow(2, LANGPAIR_MAXBIT);  // recalcule

        LANGID = new String[LANGPAIR_MAX];

        LIST_OF_MAP_LANG = prop.getProperty("LIST_OF_MAP_LANG", "XX YY");

        Pattern ps = Pattern.compile("[\\s]");  // le blanc
        
        LANGID=ps.split(LIST_OF_MAP_LANG);

        if (LANGID.length!=LANGPAIR_MAX){
            error("FATAL ERROR: Language list must have the size of 2^LANGPAIR_MAXBIT !! (Check config MAP)");
            error("actual size:"+LANGID.length);
            System.exit(0);
        }
        
        /* EN est la langue pivot */
//        LANGID[ 0] = "EN";
//        LANGID[ 1] = "FR";
//        LANGID[ 2] = "ES";
//        LANGID[ 3] = "BG";
//        LANGID[ 4] = "CS";
//        LANGID[ 5] = "DA";
//        LANGID[ 6] = "DE";
//        LANGID[ 7] = "ET";
//        LANGID[ 8] = "EL";
//        LANGID[ 9] = "GA";
//        LANGID[10] = "IT";
//        LANGID[11] = "LV";
//        LANGID[12] = "LT";
//        LANGID[13] = "HU";
//        LANGID[14] = "MT";
//        LANGID[15] = "NL";
//        LANGID[16] = "PL";
//        LANGID[17] = "PT";
//        LANGID[18] = "RO";
//        LANGID[19] = "SK";
//        LANGID[20] = "SL";
//        LANGID[21] = "FI";
//        LANGID[22] = "SV";
//        LANGID[23] = "AR";
//        LANGID[24] = "RU";
//        LANGID[25] = "ZH";
//        LANGID[26] = "JP";
//        LANGID[27] = "??";
//        LANGID[28] = "??";
//        LANGID[29] = "??";
//        LANGID[30] = "??";
//        LANGID[31] = "??";

        OBJ_IMPLEMENTATION = implementationMode.valueOf(prop.getProperty("OBJ_IMPLEMENTATION", "FAST"));

        MAP_COMPRESSION = Compression.YES;


             WORD_MINLENGTH = Integer.parseInt(prop.getProperty("WORD_MINLENGTH", "3"));

        // dynamic load of TOKEN_DEFINITION
        Class token = null;
        String TOKEN_DEFINITION = prop.getProperty("TOKEN_DEFINITION", "org.olanto.mapman.mapper.TokenNative");
        try {
            token = Class.forName(TOKEN_DEFINITION);
        } catch (ClassNotFoundException ex) {
            error_fatal("try to load: " + TOKEN_DEFINITION);
        }
        try {

            WORD_DEFINITION = (ParseTokenDefinition) token.newInstance();
        } catch (InstantiationException ex) {
            error_fatal("InstantiationException - try to load: " + TOKEN_DEFINITION);
        } catch (IllegalAccessException ex) {
            error_fatal("IllegalAccessException - try to load: " + TOKEN_DEFINITION);
        }   
        
        WORD_MAXLENGTH = 40;
        IDX_DONTINDEXTHIS = prop.getProperty("IDX_DONTINDEXTHIS", "C:/SIMPLE/config/dontindexthiswords.txt");
        SOF=new ParseSetOfWords(IDX_DONTINDEXTHIS); // charge les 

        /** nbre d'object storage actif = 2^OBJ_PW2 */
        OBJ_PW2 = 0;  ///0=>1,1=>2,2=>4,3=>8,4=>16
        OBJ_NB = (int) Math.pow(2, OBJ_PW2);  ///0=>1,1=>2,2=>4,


    }

    /** initialisation des constantes de configuration (modifiable).
     * Ces constantes choisies d�finitivement pour toute la dur�e de la vie du processus.
     */
    public void InitConfiguration() {




        // les directoire
        COMLOG_FILE = prop.getProperty("COMLOG_FILE", SenseOS.getMYCAT_HOME()+"/data/MAP_ARCH/common.log");
        DETLOG_FILE = prop.getProperty("DETLOG_FILE", SenseOS.getMYCAT_HOME()+"/data/MAP_ARCH/detail.log");

        COMMON_ROOT = prop.getProperty("COMMON_ROOT");
        MAP_ROOT = COMMON_ROOT;
        String OBJ_ROOT_0 = prop.getProperty("OBJ_ROOT_0");
        SetObjectStoreRoot(OBJ_ROOT_0, 0);


    }
}
