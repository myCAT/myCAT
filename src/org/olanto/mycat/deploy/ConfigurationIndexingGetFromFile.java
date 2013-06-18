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

import org.olanto.idxvli.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.olanto.idxvli.IdxEnum.*;
import static org.olanto.idxvli.IdxConstant.*;
import org.olanto.idxvli.server.OriginalService;
import org.olanto.idxvli.server.PreProcessingService;
import static org.olanto.util.Messages.*;

/**
 * Une classe pour initialiser les constantes.
 */
public class ConfigurationIndexingGetFromFile implements IdxInit {

    String fileName = "to be initialised";
    Properties prop;

    /**
     * cr�e l'attache de cette classe.
     */
    public ConfigurationIndexingGetFromFile() {
    }

    /**
     * charge la configuration depuis un fichier de properties
     *
     * @param fileName nom du fichier
     */
    public ConfigurationIndexingGetFromFile(String fileName) {
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

    /**
     * initialisation permanante des constantes. Ces constantes choisies
     * d�finitivement pour toute la dur�e de la vie de l'index.
     */
    public void InitPermanent() {

        DOC_MAXBIT = Integer.parseInt(prop.getProperty("DOC_MAXBIT", "20"));
        WORD_MAXBIT = Integer.parseInt(prop.getProperty("WORD_MAXBIT", "21"));


        DOC_MAX = (int) Math.pow(2, DOC_MAXBIT);  // recalcule
        WORD_MAX = (int) Math.pow(2, WORD_MAXBIT); // recalcule

        WORD_IMPLEMENTATION = implementationMode.valueOf(prop.getProperty("WORD_IMPLEMENTATION", "FAST"));
        DOC_IMPLEMENTATION = implementationMode.valueOf(prop.getProperty("DOC_IMPLEMENTATION", "FAST"));
        OBJ_IMPLEMENTATION = implementationMode.valueOf(prop.getProperty("OBJ_IMPLEMENTATION", "FAST"));


        IDX_WITHDOCBAG = Boolean.parseBoolean(prop.getProperty("IDX_WITHDOCBAG", "false"));
        IDX_MORE_INFO = Boolean.parseBoolean(prop.getProperty("IDX_MORE_INFO", "true"));
        IDX_SAVE_POSITION = Boolean.parseBoolean(prop.getProperty("IDX_SAVE_POSITION", "true"));
        IDX_ZIP_CACHE = Boolean.parseBoolean(prop.getProperty("IDX_ZIP_CACHE", "false"));


        DOC_SIZE_NAME = Integer.parseInt(prop.getProperty("DOC_SIZE_NAME", "256"));
        WORD_MAXLENGTH = Integer.parseInt(prop.getProperty("WORD_MAXLENGTH", "20"));



        OBJ_PW2 = Integer.parseInt(prop.getProperty("OBJ_PW2", "0"));

        OBJ_NB = (int) Math.pow(2, OBJ_PW2);  ///0=>1,1=>2,2=>4,
        OBJ_STORE_ASYNC = false;
        /*
         * désactive les options qui ne servent pas à myCat
         */
        IdxConstant.MODE_RANKING = RankingMode.BM25;
        ORTOGRAFIC = false;
        IDX_MARKER = false;


    }

    /**
     * initialisation des constantes de configuration (modifiable). Ces
     * constantes choisies d�finitivement pour toute la dur�e de la vie du
     * processus.
     */
    public void InitConfiguration() {

        DOC_ENCODING = prop.getProperty("DOC_ENCODING", "UTF-8");
        IDX_MFLF_ENCODING = prop.getProperty("IDX_MFLF_ENCODING", "UTF-8");

        WORD_MINLENGTH = Integer.parseInt(prop.getProperty("WORD_MINLENGTH", "3"));

        // dynamic load of TOKEN_DEFINITION
        Class token = null;
        String TOKEN_DEFINITION = prop.getProperty("TOKEN_DEFINITION", "isi.jg.deploy.indexing.TokenIndexing");
        try {
            token = Class.forName(TOKEN_DEFINITION);
        } catch (ClassNotFoundException ex) {
            error_fatal("try to load: " + TOKEN_DEFINITION);
        }
        try {

            //WORD_DEFINITION = new TokenIndexing();
            WORD_DEFINITION = (TokenDefinition) token.newInstance();
        } catch (InstantiationException ex) {
            error_fatal("InstantiationException - try to load: " + TOKEN_DEFINITION);
        } catch (IllegalAccessException ex) {
            error_fatal("IllegalAccessException - try to load: " + TOKEN_DEFINITION);
        }

        // dynamic load of ORIGINAL_DEFINITION
        Class original = null;
        String ORIGINAL_CODE = prop.getProperty("ORIGINAL_CODE", "org.olanto.idxvli.server.OriginalService_Default");
        try {
            original = Class.forName(ORIGINAL_CODE);
        } catch (ClassNotFoundException ex) {
            error_fatal("try to load: " + ORIGINAL_CODE);
        }
        try {
            ORIGINAL_DEFINITION = (OriginalService) original.newInstance();
        } catch (InstantiationException ex) {
            error_fatal("InstantiationException - try to load: " + ORIGINAL_CODE);
        } catch (IllegalAccessException ex) {
            error_fatal("IllegalAccessException - try to load: " + ORIGINAL_CODE);
        }

        // dynamic load of PRE_PROCESSING
        Class preproc = null;
        String PRE_PROCESSING_CODE = prop.getProperty("PRE_PROCESSING_CODE", "org.olanto.idxvli.server.PreProcessingService_Default");
        try {
            preproc = Class.forName(PRE_PROCESSING_CODE);
        } catch (ClassNotFoundException ex) {
            error_fatal("try to load: " + ORIGINAL_CODE);
        }
        try {
            PRE_PROCESSING = (PreProcessingService) preproc.newInstance();
        } catch (InstantiationException ex) {
            error_fatal("InstantiationException - try to load: " + PRE_PROCESSING_CODE);
        } catch (IllegalAccessException ex) {
            error_fatal("IllegalAccessException - try to load: " + PRE_PROCESSING_CODE);
        }

        PRE_PROCESSING_INTERVAL = Integer.parseInt(prop.getProperty("PRE_PROCESSING_INTERVAL", "3600"));

        WORD_USE_STEMMER = Boolean.parseBoolean(prop.getProperty("WORD_USE_STEMMER", "false"));

        WORD_STEMMING_LANG = prop.getProperty("WORD_STEMMING_LANG", "french"); // only for initialisation

        MODE_RANKING = RankingMode.valueOf(prop.getProperty("MODE_RANKING", "NO"));

        MAX_RESPONSE = Integer.parseInt(prop.getProperty("MAX_RESPONSE", "200"));

        DOCNAME_MAX_BROWSE = Integer.parseInt(prop.getProperty("DOCNAME_MAX_BROWSE", "5000"));

        DOCNAME_BROWSE_INSENSITIVE = Boolean.parseBoolean(prop.getProperty("DOCNAME_BROWSE_INSENSITIVE", "true"));      
        
        NEAR_DISTANCE = Integer.parseInt(prop.getProperty("NEAR_DISTANCE", "8"));


        IDX_DONTINDEXTHIS = prop.getProperty("IDX_DONTINDEXTHIS", "C:/SIMPLE/config/dontindexthiswords.txt");

        SERVER_MESSAGE_PATH = prop.getProperty("SERVER_MESSAGE_PATH", "C:/MYCAT/config/messages/interface/initserver");
        SERVER_MESSAGE_LANG = prop.getProperty("SERVER_MESSAGE_LANG", "");
        try {  // charge le fichier des messages
            if (SERVER_MESSAGE_LANG.isEmpty()) {
                MSG = new ConstStringManager(SERVER_MESSAGE_PATH + ".properties");
            } else {
                MSG = new ConstStringManager(SERVER_MESSAGE_PATH + "_" + SERVER_MESSAGE_LANG + ".properties");
            }
        } catch (IOException ex) {
            Logger.getLogger(ConfigurationIndexingGetFromFile.class.getName()).log(Level.SEVERE, null, ex);
        }

        DICT_FILE = prop.getProperty("DICT_FILE", "C:/SIMPLE/dict/en.dic");
        ORG_FILE = prop.getProperty("ORG_FILE", "C:/SIMPLE/dict/organisation.dic");
        ROOT_CORPUS_SOURCE = prop.getProperty("ROOT_CORPUS_SOURCE", "C:/SIMPLE/corpus/source");
        ROOT_CORPUS_TXT = prop.getProperty("ROOT_CORPUS_TXT", "C:/SIMPLE/corpus/txt");
        TEMP_FOLDER = prop.getProperty("TEMP_FOLDER", "C:/MYCAT/TEMP");
        ROOT_CORPUS_LANG = prop.getProperty("ROOT_CORPUS_LANG", "XX YY");

        PHONET_FILE = prop.getProperty("PHONET_FILE", "C:/SIMPLE/dict/phonet.en");

        ORIGINAL_FULL_URL = prop.getProperty("ORIGINAL_FULL_URL", "HTTP");
        ORIGINAL_HOST = prop.getProperty("ORIGINAL_HOST", "localhost");
        ORIGINAL_PORT = prop.getProperty("ORIGINAL_PORT", "80");

SKIP_LINE_QUOTE_DECTECTOR=Boolean.parseBoolean(prop.getProperty("SKIP_LINE_QUOTE_DECTECTOR", "false"));
        OPEN_REF_BEG = prop.getProperty("OPEN_REF_BEG", "[R");
        OPEN_REF_END = prop.getProperty("OPEN_REF_END", "]");
        CLOSE_REF_BEG = prop.getProperty("CLOSE_REF_BEG", "[E");
        CLOSE_REF_END = prop.getProperty("CLOSE_REF_END", "]");


        WORD_EXPANSION = Boolean.parseBoolean(prop.getProperty("WORD_EXPANSION", "true"));
        WORD_EXPANSION_RELOAD = Boolean.parseBoolean(prop.getProperty("WORD_EXPANSION_RELOAD", "true"));
        WORD_MAX_EXPANSION = Integer.parseInt(prop.getProperty("WORD_MAX_EXPANSION", "100"));

        DOCNAME_EXPANSION = Boolean.parseBoolean(prop.getProperty("DOCNAME_EXPANSION", "true"));
        DOCNAME_EXPANSION_RELOAD = Boolean.parseBoolean(prop.getProperty("DOCNAME_EXPANSION_RELOAD", "true"));
        DOCNAME_MAX_EXPANSION = Integer.parseInt(prop.getProperty("DOCNAME_MAX_EXPANSION", "1000"));



        LANGUAGE_TRAINING = prop.getProperty("LANGUAGE_TRAINING", "C:/SIMPLE/dtk/");
        COLLECTION_DOMAIN = prop.getProperty("COLLECTION_DOMAIN", "C:/SIMPLE/url/urlcollection.txt");
        COMLOG_FILE = prop.getProperty("COMLOG_FILE", "C:/SIMPLE/data/defaultcommon.log");
        DETLOG_FILE = prop.getProperty("DETLOG_FILE", "C:/SIMPLE/data/defaultdetail.log");


        // param�tre de fonctionnement

        CACHE_IMPLEMENTATION_INDEXING = implementationMode.valueOf(prop.getProperty("CACHE_IMPLEMENTATION_INDEXING", "FAST"));
        IDX_CACHE_COUNT = Integer.parseInt(prop.getProperty("NEAR_DISTANCE", "1")) * (int) MEGA;
        IDX_RESERVE = Integer.parseInt(prop.getProperty("IDX_RESERVE", "2")) * KILO;
        INDEXING_CACHE_SIZE = Integer.parseInt(prop.getProperty("INDEXING_CACHE_SIZE", "128")) * MEGA;
        KEEP_IN_CACHE = Integer.parseInt(prop.getProperty("KEEP_IN_CACHE", "90"));


        CACHE_IMPLEMENTATION_READ = implementationMode.valueOf(prop.getProperty("CACHE_IMPLEMENTATION_READ", "FAST"));
        QUERY_CACHE_SIZE = Integer.parseInt(prop.getProperty("QUERY_CACHE_SIZE", "128")) * MEGA;
        QUERY_CACHE_COUNT = Integer.parseInt(prop.getProperty("QUERY_CACHE_COUNT", "256")) * KILO;


        // les directoire

        COMMON_ROOT = prop.getProperty("COMMON_ROOT");
        if (prop.getProperty("DOC_ROOT") == null) {
            DOC_ROOT = COMMON_ROOT;
        } else {
            DOC_ROOT = prop.getProperty("DOC_ROOT");
        }
        if (prop.getProperty("WORD_ROOT") == null) {
            WORD_ROOT = COMMON_ROOT;
        } else {
            DOC_ROOT = prop.getProperty("WORD_ROOT");
        }
        String OBJ_ROOT_0 = prop.getProperty("OBJ_ROOT_0");
        String OBJ_ROOT_1 = prop.getProperty("OBJ_ROOT_1");
        String OBJ_ROOT_2 = prop.getProperty("OBJ_ROOT_2");
        String OBJ_ROOT_3 = prop.getProperty("OBJ_ROOT_3");
        String OBJ_ROOT_4 = prop.getProperty("OBJ_ROOT_4");
        String OBJ_ROOT_5 = prop.getProperty("OBJ_ROOT_5");
        String OBJ_ROOT_6 = prop.getProperty("OBJ_ROOT_6");
        String OBJ_ROOT_7 = prop.getProperty("OBJ_ROOT_7");

        if (OBJ_NB > 0) {
            if (OBJ_ROOT_0 != null) {
                SetObjectStoreRoot(OBJ_ROOT_0, 0);
            } else {
                error_fatal("You must defined a directory for OBJ_ROOT_0");
            }
        }
        if (OBJ_NB > 1) {
            if (OBJ_ROOT_1 != null) {
                SetObjectStoreRoot(OBJ_ROOT_1, 1);
            } else {
                error_fatal("You must defined a directory for OBJ_ROOT_1");
            }
        }
        if (OBJ_NB > 2) {
            if (OBJ_ROOT_2 != null) {
                SetObjectStoreRoot(OBJ_ROOT_2, 2);
            } else {
                error_fatal("You must defined a directory for OBJ_ROOT_2");
            }
        }
        if (OBJ_NB > 3) {
            if (OBJ_ROOT_3 != null) {
                SetObjectStoreRoot(OBJ_ROOT_3, 3);
            } else {
                error_fatal("You must defined a directory for OBJ_ROOT_3");
            }
        }
        if (OBJ_NB > 4) {
            if (OBJ_ROOT_4 != null) {
                SetObjectStoreRoot(OBJ_ROOT_4, 4);
            } else {
                error_fatal("You must defined a directory for OBJ_ROOT_4");
            }
        }
        if (OBJ_NB > 5) {
            if (OBJ_ROOT_5 != null) {
                SetObjectStoreRoot(OBJ_ROOT_5, 5);
            } else {
                error_fatal("You must defined a directory for OBJ_ROOT_5");
            }
        }
        if (OBJ_NB > 6) {
            if (OBJ_ROOT_6 != null) {
                SetObjectStoreRoot(OBJ_ROOT_6, 6);
            } else {
                error_fatal("You must defined a directory for OBJ_ROOT_6");
            }
        }
        if (OBJ_NB > 7) {
            if (OBJ_ROOT_7 != null) {
                SetObjectStoreRoot(OBJ_ROOT_7, 7);
            } else {
                error_fatal("You must defined a directory for OBJ_ROOT_7");
                // param�tre de fonctionnement
            }
        }

    }
}
