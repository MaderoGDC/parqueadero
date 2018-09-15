/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parqueadero;

/**
 *
 * @author CARITO
 */
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Parqueadero parqueadero = new Parqueadero();
        int u=0;
        int operacion = 0;
        Scanner teclado = new Scanner(System.in);      
        while(operacion != 5){
            //imprimir menu opciones
            System.out.println("Escoja una opción:  ");
            System.out.println("1:Ingresar un nuevo vehiculo");
            System.out.println("2:Sacar vehiculo");
            System.out.println("3:Mostrar vehiculos de una seccion");
            System.out.println("4:Mostrar ingresos");
             System.out.println("5:fin de la operacion");
            operacion = teclado.nextInt();
            switch(operacion){
                case 1:
                    parqueadero.ingresar_vehiculo();
                break;
                case 2: 
                    parqueadero.sacar_vehiculo();
                break;
                default:
                break;    
                    
            }
        }
    }
}
