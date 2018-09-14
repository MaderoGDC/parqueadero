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
import becker.robots.*;
import java.awt.Color;
import java.util.Scanner;

public class Parqueadero {

    private City ciudad;
    private Robot chofer;
    private Scanner teclado;
    private Thing[][] matriz;
    private int zonas;
    private long[][] tiempos;

    public Parqueadero() {
        teclado = new Scanner(System.in);
        this.ciudad = new City();
        //todas la paredes del parqueadero
        //zonas parqueo 
        for (int i = 0; i <= 5; i++) {
            Wall lateral = new Wall(ciudad, i, 0, Direction.WEST);
            if (i < 5) {
                Wall lateralb = new Wall(ciudad, i, 0, Direction.EAST);
                Wall lateral1 = new Wall(ciudad, i, 1, Direction.EAST);
                Wall lateral1b = new Wall(ciudad, i, 1, Direction.WEST);
                Wall lateral2 = new Wall(ciudad, i, 2, Direction.WEST);
                Wall lateral2b = new Wall(ciudad, i, 2, Direction.EAST);
            }
        }
        for (int i = 0; i < 3; i++) {
            Wall arriba = new Wall(ciudad, 0, i, Direction.NORTH);
        }
        //camino y zona espera
        for (int i = 0; i < 9; i++) {
            Wall arriba = new Wall(ciudad, 5, i, Direction.SOUTH);
            if (i <= 7 && i > 2) {
                Wall arriba1 = new Wall(ciudad, 4, i, Direction.NORTH);
                Wall espacio = new Wall(ciudad, 4, i, Direction.EAST);
            }
        }
        Wall tope_inicio = new Wall(ciudad, 5, 8, Direction.EAST);
        //identificadores de zona
        Thing zona1 = new Thing(this.ciudad, -1, 0);
        zona1.getIcon().setColor(Color.cyan);
        zona1.getIcon().setLabel("1");

        Thing zona2 = new Thing(this.ciudad, -1, 1);
        zona2.getIcon().setColor(Color.cyan);
        zona2.getIcon().setLabel("2");

        Thing zona3 = new Thing(this.ciudad, -1, 2);
        zona3.getIcon().setColor(Color.cyan);
        zona3.getIcon().setLabel("3");
        //entrada
        Thing entrada = new Thing(this.ciudad, 4, 8);
        entrada.getIcon().setColor(Color.RED);
        entrada.getIcon().setLabel("Entrada");

        this.chofer = new Robot(ciudad, 5, 8, Direction.WEST);
        this.chofer.setColor(Color.ORANGE);
        this.chofer.setLabel("Chofer");
        matriz = new Thing[5][3];
        tiempos = new long[5][3];
//lleno de null el parqueadero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println("y:" + i + " " + "x:" + j);
                matriz[j][i] = null;
            }
        }
    }
//mover x pasos    

    public void mover(int pasos) {
        for (int i = pasos; i > 0; i--) {
            chofer.move();
        }
    }
//dar x giros

    public void girar(int giros) {
        for (int i = giros; i > 0; i--) {
            chofer.turnLeft();
        }
    }

 //giros
    public void giroDerecha() {
        girar(3);
    }

    public void giroIzquierda() {
        girar(1);
    }

    public void delante() {
        mover(1);
    }

 //identificador de zona libre  
    public int zona_libre() {
        int zona = -1;
        int espacios[] = {0, 0, 0};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (matriz[j][i] == null) {
                    espacios[i]++;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (espacios[i] > zona) {
                zona = i;
                return zona;
            }
        }
        return zona;
    }
//identificador de espacio libre dentro de la zona
    public int espacio_libre(int zona) {
        System.out.println("zona:" + zona);
        for (int i = 0; i < 5; i++) {
            if (matriz[i][zona] == null) {
                return i;
            }
        }
        return -1;
    }
//busca si la placa ya existe
    public boolean buscar_placa(String placa) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (matriz[j][i] != null) {
                    if (matriz[j][i].getIcon().getLabel().equals(placa)) {
                        return true;    
                    }
                }

            }

        }
        return false;
    }
//ingresar vehiculo
    public boolean ingresar_vehiculo() {
        System.out.println("ingrese placa del vehiculo (letra y numero): ");
        String placa = teclado.nextLine();
        Thing carro = new Thing(ciudad, 5, 8);
        carro.getIcon().setLabel(placa);
        chofer.pickThing();
        int zona = zona_libre();
        if (buscar_placa(placa)==true){
            System.out.println("ya hay un vehiculo registrado con esa placa");
            return false;
        }           
        int parqueadero = espacio_libre(zona);
        while (chofer.getAvenue() != zona) {
            chofer.move();
        }
        giroDerecha();
        while (chofer.getStreet() != parqueadero) {
            chofer.move();
        }
        chofer.putThing();
        girar(2);
        while (chofer.frontIsClear() == true) {
            chofer.move();
        }
        girar(1);
        while (chofer.frontIsClear() == true) {
            chofer.move();
        }
        girar(2);
        matriz[zona][parqueadero] = carro;
        tiempos [zona][parqueadero]= System.currentTimeMillis();
        return true;
    }
    public boolean sacar_vehiculo(){
        int zona;
        String placa;
        System.out.println("ingrese la zona de parqueo de su vehiculo: ");
        zona= teclado.nextInt();
        System.out.println("ingrese placa del vehiculo (letra y numero): ");
        placa = teclado.nextLine();
        
        

        
        return true;
    }
}
