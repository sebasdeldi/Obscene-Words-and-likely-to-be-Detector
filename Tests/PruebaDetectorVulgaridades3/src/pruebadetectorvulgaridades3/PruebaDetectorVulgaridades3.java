/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebadetectorvulgaridades3;

import detectorvulgaridades.DetectorVulgaridades;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author CARITO
 */
public class PruebaDetectorVulgaridades3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        DetectorVulgaridades dv = new DetectorVulgaridades();
        String[]palabrasTexto;
        palabrasTexto = dv.LecturaTexto();
        
        if(dv.compareUsingLevenshtain(palabrasTexto)){
            System.out.println("El texto tiene vulgaridades");
        }
        else{
            System.out.println("El texto NO tiene vulgaridades");
        }
        
        
        // TODO code application logic here
    }
    
    
    
    
}
