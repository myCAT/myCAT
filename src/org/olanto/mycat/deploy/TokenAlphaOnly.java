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

import org.olanto.idxvli.*;
import org.olanto.idxvli.extra.*;
import static org.olanto.idxvli.IdxConstant.*;

/**
 * Une classe pour définir les token (une définition alpha numérique).
 *
 */
public class TokenAlphaOnly implements TokenDefinition {

    static final int EOF = -1;

    /** Cr�e une attache
     */
    public TokenAlphaOnly() {
    }

    /**
     * Cherche le symbole suivant.
     * d�finition symbole= commence avec une lettre (au sens unicode)
     * et n'est pas dans un tag <> (peut occasioner une erreur d'indexation dans les mauvais html)
     * s'arr�te quand on rencontre autre chose qu'une lettre (au sens unicode)
     */
    public final void next(DoParse a) {
        int c = 0;
        char r;
        try {
            while (!(Character.isLetter((char) c)) && (c != EOF)) {  // skip non letter
                if ((char) c == '<') { // skip tag
                    while ((c != EOF) && ((char) c != '>')) {
                        c = a.in.read();
                        a.poschar++;
                    }
                }
                c = a.in.read();
                a.poschar++;
            }
            a.cw.setLength(0);
            r = (char) c;
            while ((Character.isLetter(r) // ||Character.isDigit(r)
                    //     ||(char)c=='-'
                    ) && (c != EOF)) {  // get word
                a.cw.append(r);
                c = a.in.read();
                a.poschar++;
                r = (char) c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        a.EOFflag = (c == EOF);
    }

    /** normalise le mot. actuellement<br>
     * - mis en minuscule<br>
     * - tronquer � la longueur fix�e.<br>
     * - lemmatiser si le stemming est actif.<br>
     * @param id l'indexeur de r�f�rence
     * @param w le mot � normaliser
     * @return un mot normalis�
     */
    public final String normaliseWord(IdxStructure id, String w) {
        //   System.out.print(w+"-->");
        w = w.toLowerCase();
        if (w.length() > WORD_MAXLENGTH/2) {
            w = w.substring(0, WORD_MAXLENGTH/2);
        }
        if (WORD_USE_STEMMER) {
            w = Stemmer.stemmingOfW(w);
        }
        //   System.out.println(w);
        return w;
    }
}
