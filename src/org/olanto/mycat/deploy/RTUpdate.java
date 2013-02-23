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

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.olanto.converter.SimpleConverterApplicationTest;
import org.olanto.idxvli.IdxConstant;
import org.olanto.idxvli.server.IndexDirectory;
import org.olanto.idxvli.server.IndexService_MyCat;
import org.olanto.mapman.mapper.MapAll;
import org.olanto.mapman.server.GetMapService;
import org.olanto.mapman.server.MapService;
import org.olanto.mycat.lineseg.RUNSegmentation;
import org.olanto.senseos.SenseOS;
import org.olanto.util.Timer;

/**
 * envoi un stop au service d'indexation
 */
public class RTUpdate extends Thread {

    int waiting;  // en seconde
    static int step = 300;  // en seconde
    String convParams; // paramètres de conversion
    int count;
    IndexService_MyCat ismain;
    MapService msmain;

    public RTUpdate(int waiting, String convParams) {
        this.waiting = waiting;
        this.convParams = convParams;
        try {

            System.out.println("connect to serveur");
            Remote r = Naming.lookup("rmi://localhost/VLI");

            System.out.println("access to serveur");

            if (r instanceof IndexService_MyCat) {
                ismain = ((IndexService_MyCat) r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            msmain = GetMapService.getServiceMAP("rmi://localhost/MAP");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String home=SenseOS.getMYCAT_HOME();
        Thread thread = new RTUpdate(IdxConstant.PRE_PROCESSING_INTERVAL, "-f txt -b "+home+"/corpus/bad "+home+"/corpus/docs "+home+"/corpus/source");
        thread.run();
    }

    public void run() {
        while (true) {
            try {
                // Récupère la température de Bruxelles (Belgique) 
                count++;
                Timer t1 = new Timer("Start new Update #" + count);
                update();
                t1.stop();
                System.out.println("End of Update #" + count);
                // Thread.sleep(waiting*1000); // Attend  
                int countwait = 0;
                while (countwait < waiting) {
                    try {
                        // pour être "conscient" que les rmi sont arrêtés
                        System.out.println(ismain.getInformation());
                    } catch (RemoteException ex) {
                        Logger.getLogger(RTUpdate.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    countwait += step;
                    Thread.sleep(step * 1000);
                }
            } catch (InterruptedException exception) {
            }
        }
    }

    private void update() {
        System.out.println("Start pre-processing Update #" + count);
        try {
            ismain.executePreprocessing(null);
        } catch (RemoteException ex) {
            Logger.getLogger(RTUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Start conversion Update #" + count);
        try {
            String[] args = (new String(convParams)).split("\\s");
            SimpleConverterApplicationTest.main(args);
        } catch (Exception ex) {
            Logger.getLogger(RTUpdate.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Start lineSegmentation Update #" + count);
        RUNSegmentation.main(null);
        System.out.println("Start Index Update #" + count);
        IndexDirectory.updateIndex(ismain);
        System.out.println("Start Map Update #" + count);
        MapAll.updateMap(msmain);
    }
}
