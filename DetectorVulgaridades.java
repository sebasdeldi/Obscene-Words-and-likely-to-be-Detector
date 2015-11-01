/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detectorvulgaridades;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;



/**
 *
 * @author Sebastián Delado Díaz
 */
public class DetectorVulgaridades {

    /**
     * @param args the command line arguments
     * Este es el main de la clase, en caso de que se quiera trabajar sobre el proyecto mismo en vez de descargar
     * la libreria (.jar)
     */
    
    /*
    public static void main(String[] args) throws IOException {
        

        DetectorVulgaridades dv = new DetectorVulgaridades();
        
        String recivida = "Sebastian hola amigos  amarillo fucdfsqer";
        System.out.println("El String es:" + recivida);
        
        System.out.println("Es vulgar?:");
        //dv.compareIfObscene(recivida);
        if (dv.compareUsingLevenshtain(wordDivider(recivida))){
            System.out.println("Si");
        }
        else {
            
            System.out.println("No");

        }
        //dv.LecturaDiccionario();
    }
    */
    
    /**
     * 
     * @return obsceneWords (Arreglo de Strings)
     * @throws FileNotFoundException
     * @throws IOException 
     * 
     * 
     * Este metodo, lee un archivo de texto (.txt) que debe estar ubicado en el fichero raiz de el producto, 
     * para que no toque modificar el funcionamiento de este metodo, es necesario que el formato de el archivo de texto
     * sea una palabra separada de otra por un espacio, es decir (String \n String \n String ... etc)
     */
    
     
    private static String[] LecturaDiccionario() throws FileNotFoundException, IOException{
    
        String [] obsceneWords = new String[371];

        BufferedReader br = new BufferedReader(new FileReader("DiccionarioVulgaridades.txt"));
        
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            
            for (int i = 0; i < 370; i++){
            
                obsceneWords = everything.split("\n");
            
            }
            //System.out.println (obsceneWords);
            return obsceneWords;
            
        } finally {
            br.close();
        }
        
       
             
    }
    
    /**
     * 
     * @param texto (Un String cualquiera)
     * @return palabras (Arreglo de Strings)
     * 
     * 
     * para que quien use esta libreria no tenga que convertir el texto que desea analizar a un arreglo de Strings,
     * para pasarcelo despues como parametro a el metodo compareUsingLevenshtain, este metodo tomará cualquier texto,
     * y lo convertirá a un arreglo donde en cada posición se guarda una única palabra del texto.
     */
    
    public static String[] wordDivider (String texto){
    
        String []palabras = texto.split(" +");      
        return palabras;
    
    }
    
    /**
     * 
     * @param texto (Toma como parametro un arreglo de enteros), ya sea obtenido por el metodo wordDivider o
     * se le ingrese manualmente)
     * @return Boolean vulgar, tras aplicar el algoritmo de Levenshtain, el analisís arrojará si contiene alguna 
     * vulgaridad o no.
     * @throws IOException 
     * 
     * Para una mejor comprensión de como funciona el algoritmo, leer los comentarios hechos dentro de el metodo
     * 
     */
    
    
    
    public boolean compareUsingLevenshtain (String[] texto) throws IOException{
        
        
        String keyword;
        int pesoVulgaridad = 0;
        int pesoVulgaridadRespuestas[] = null;
        List<Integer> listaPesos = new LinkedList<Integer>();
        boolean vulgar = false;
        String palabraDelTexto;

        //HACER LEVENSHTAIN
                
        //convertir todo (palabras de el diccionario de vulgaridades y palabras que se van a analizar a matriz de caracteres
        
        
        //USAR ESTO PARA QUE CORRA LEVENSHTAIN EN CADA PALABRA DE EL TEXTO!
        for (int z = 0; z < texto.length; z++){
            
            palabraDelTexto = texto[z];
            //String [] obsceneWords = LecturaDiccionario();
        
        
                  //por ahora solo va a funcionar si el input es 1 sola palabra
                char[] charArray_input = palabraDelTexto.toCharArray();
                String [] obsceneWords = LecturaDiccionario();
                
                for (int i = 0 ; i < obsceneWords.length; i++){
                    
                    char[] charArray_diccionario = obsceneWords[i].toCharArray();
                    
                    int [][] matriz = new int[charArray_input.length+1][charArray_diccionario.length+1];
                    
                    //voy a llevar la primera fila y la primera columna con valores de 0 hasta el tamaño del String
                    
                    for (int x = 0; x<charArray_input.length+1; x++){
                        
                        matriz[x][0] = x;
                    }
                    
                    for (int y = 0; y<charArray_diccionario.length+1; y++){
                        
                        matriz[0][y] = y;
                    }
                    
                    //comienzo a llenar la matriz
                    
                    for (int j = 1; j < charArray_input.length; j++){
                        
                        for (int k = 1 ; k < charArray_diccionario.length; k++){
                            
                            //algoritmo base, si son iguales, inserta el anterior en la diagonal
                            if (charArray_input[j] == charArray_diccionario[k]){
                                
                                
                                matriz[j][k] = matriz[j-1][k-1];
                            }
                            
                            //si no son iguales inserta el menor que le rodea y más uno
                            else {
                                matriz [j][k] = min(matriz[j-1][k], matriz[j-1][k-1], matriz[j][k-1])+1;
                            }
                            
                            
                        }
                         
                    }
                        //al final el ultimo elemento de la matriz es el peso de dicha palabra con respecto a los elementos del diciionario
                         pesoVulgaridad = matriz[charArray_input.length-1][charArray_diccionario.length-1];
                    
                         
                    //si la palabra es igual (0) o con un solo cambio (1) la concidero vulgar, y la inserto en una lista
                    if (pesoVulgaridad <= 1){
                            listaPesos.add(pesoVulgaridad);
                        }
                    
 
                }
       
        //si dicha lista donde inserto las palabras concideradas culgares está vacía, entonces no hay vulgaridades
          if (listaPesos.isEmpty()){
              
              vulgar = false;
          }
          
          //si contiene así sea un elemento, entonces si contiene vulgaridades.
          else{
              vulgar = true;
          }
        
        
        
        }
        return vulgar;
    }

    
    /**
     * 
     * @param a
     * @param b
     * @param c
     * @return smalles, que es el mínimo de tres enteros cualquieras
     * 
     * la única función de este metodo es hallar el mínimo de los elementos que rodean la posición que se va a llenar
     * a la hora de llenar la matriz de levenshtain.
     */
    
    private int min(int a, int b, int c) {
        
        int smallest;
        
        if(a<b && a<c){
            smallest = a;
            return smallest;
        }
        
        else if(b<c && b<a){
            smallest = b;
            return smallest;
        }
        
        else{
            smallest = c;
            return smallest;
        }
            
    }
    
    
     /**
     * 

     * @return String [] palabrasTexto, un arreglo donde cada una de las posiciones es una palabra de el arreglo
     * 
     * 
     * La idea de este metodo es que se use cuando se quieren comparar un texto más extenso con el diccionario
     * dicho texto tendrá que ser dado en un archivo de texto llamado Text.txt y ubicado en el fichero raiz de 
     * el proyecto 
     */
    
    
    
    public String[] LecturaTexto() throws FileNotFoundException, IOException{
    
        String [] palabrasTexto = new String[10000];

        BufferedReader br = new BufferedReader(new FileReader("Text.txt"));
        
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            
            for (int i = 0; i < 10000; i++){
            
                palabrasTexto = everything.split(" +");
            
            }
            return palabrasTexto;
            
        } finally {
            br.close();
        }
    }
    
    
    
}

   
        
        
       
    
    
    
    
   

    
    
    

