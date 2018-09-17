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
            System.out.println(" ");
            System.out.println("cobramos 5 pesos por minuto o fraccion");
            System.out.println("bienvenido/a");
            operacion = teclado.nextInt();
            switch(operacion){
                case 1:
                    parqueadero.ingresar_vehiculo();
                break;
                case 2: 
                    parqueadero.sacar_vehiculo();
                break;
                case 3:
                    parqueadero.mostrar_zona();
                break;
                case 4:
                    parqueadero.reporte_ingresos();
                break;
                case 5:
                    System.out.println("Gracias por utilizar nuestro software");
                break;
                default:
                    System.out.println("no se que intentas hacer, prueba con otra opcion");
                break;    
                    
            }
        }
        System.out.println("Fin de la operacion");
    }
}
