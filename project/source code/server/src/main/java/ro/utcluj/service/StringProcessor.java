package ro.utcluj.service;


import org.springframework.stereotype.Component;
import ro.utcluj.api.StringProcesorInterface;

import java.sql.Date;
import java.util.StringTokenizer;


@Component
public class StringProcessor implements StringProcesorInterface {
    /**
     * Transforma string-urile introduse in interfata in tipul int
     * @param s string introdus in interfata
     * @return intregul transormat, sau 0 in caz de esec
     */
    public int toInt(String s){
        int rez = 0;
            if(s != null)
                try {
                    rez = Integer.parseInt(s);
                }catch (Exception e){
                    return 0;
                }

        return rez;
    }

    /**
     * Transforma string-urile introduse in interfata in tipul double
     * @param s stringul dat in interfata
     * @return numarul double transformat sau 0.00 in caz de nereusita
     */
    public double toDouble(String s){
        double rez = 0.00;
        try{
            if(s != null)
                rez = Double.parseDouble(s);
        }catch (Exception e){
            return 0.00;
        }
        return rez;
    }

    public Date toDate(String str){

            if(!str.equals ("")) {
                try {
                    StringTokenizer s = new StringTokenizer (str, "- ");

                    Date rez = new Date ((Integer.parseInt ((String) s.nextElement ())),
                            (Integer.parseInt ((String) s.nextElement ())),
                            (Integer.parseInt ((String) s.nextElement ())));
                    return rez;
                }catch (Exception e){
                    return null;
                }
            }


        return null;
    }
}
