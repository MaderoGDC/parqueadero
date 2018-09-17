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
    private Thing[] espera;
    private int zonas;
    private long[][] tiempos;
    private long[] tiempos_espera;
    private double ingresos_totales;

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
        Thing zona1 = new Thing(this.ciudad, 6, 0);
        zona1.getIcon().setColor(Color.BLUE);
        zona1.getIcon().setLabel("zona 1");

        Thing zona2 = new Thing(this.ciudad, 6, 1);
        zona2.getIcon().setColor(Color.cyan);
        zona2.getIcon().setLabel("zona 2");

        Thing zona3 = new Thing(this.ciudad, 6, 2);
        zona3.getIcon().setColor(Color.BLUE);
        zona3.getIcon().setLabel("zona 3");
        //entrada
        Thing entrada = new Thing(this.ciudad, 4, 8);
        entrada.getIcon().setColor(Color.MAGENTA);
        entrada.getIcon().setLabel("Entrada");

        //zona de espera
        Thing espera1 = new Thing(this.ciudad, 3, 3);
        espera1.getIcon().setColor(Color.cyan);
        espera1.getIcon().setLabel("zona");

        Thing espera2 = new Thing(this.ciudad, 3, 4);
        espera2.getIcon().setColor(Color.cyan);
        espera2.getIcon().setLabel("de");

        Thing espera3 = new Thing(this.ciudad, 3, 5);
        espera3.getIcon().setColor(Color.cyan);
        espera3.getIcon().setLabel("espera");

        Thing espera4 = new Thing(this.ciudad, 3, 6);
        espera4.getIcon().setColor(Color.cyan);
        espera4.getIcon().setLabel("de");

        Thing espera5 = new Thing(this.ciudad, 3, 7);
        espera5.getIcon().setColor(Color.cyan);
        espera5.getIcon().setLabel("vehiculos");

        //crear chofer
        this.chofer = new Robot(ciudad, 5, 8, Direction.WEST, 0);
        this.chofer.setColor(Color.darkGray);
        this.chofer.setLabel("Chofer");

        matriz = new Thing[5][3];
        tiempos = new long[5][3];
        espera = new Thing[4];
        tiempos_espera = new long[4];
//lleno de null el parqueadero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                matriz[j][i] = null;
            }
        }
        //leno de ceros el tiempo espera
        for (int i = 0; i < 4; i++) {
            tiempos_espera[i] = 0;
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

    //identificador de zona con mas espacio libres
    public int zona_libre() {
        int zona = 6;
        int mas_espacio = -1;
        int espacios[] = {0, 0, 0};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (matriz[j][i] == null) {
                    espacios[i]++;
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            if (espacios[i] >= mas_espacio) {
                mas_espacio = espacios[i];
            }
        }
        if (mas_espacio == 0) {
            return zona;
        }
        for (int i = 0; i < 3; i++) {
            if (espacios[i] == mas_espacio) {
                zona = i;
                return zona;
            }

        }
        return zona;
    }

    //identificador de espacio libre dentro de la zona
    public int espacio_libre(int zona) {
        for (int i = 0; i < 5; i++) {
            if (matriz[i][zona] == null) {
                return i;
            }
        }
        return -1;
    }

    //busca si la placa ya ha sido ingresada en el parqueadero
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

    //buscar placa en alguna zona y devuelve su posicion
    public int buscar_placa_zona(int zona, String placa) {
        for (int i = 0; i < 5; i++) {
            if (matriz[i][zona] != null && matriz[i][zona].getIcon().getLabel().equals(placa)) {
                return i;
            }

        }
        return -1;
    }

    //hacer factura devuelve el valor a cobrar
    public double factura(double tiempo_minutos) {
        double valor_pesos;
        if (tiempo_minutos == 0) {
            return 5;
        }
        valor_pesos = tiempo_minutos * 5;
        return valor_pesos;
    }

    //tiempo en segs que lleva en el parqueadero
    public long tiempo_dentro(long tiempo_de_entrada) {
        long tiempo_dentro;
        long tiempo_actual = System.currentTimeMillis();
        tiempo_dentro = (tiempo_actual - tiempo_de_entrada) / 60000;
        return tiempo_dentro;
    }

    //ingresar vehiculo
    public boolean ingresar_vehiculo() {
        int zona = zona_libre();
        if (zona == 6) {
            System.out.println("lo sentimos el parqueadero esta lleno, vuelva mas tarde");
            return false;
        }
        System.out.println("ingrese placa del vehiculo (letra y numero): ");
        String placa = teclado.nextLine();
        if (buscar_placa(placa) == true) {
            System.out.println("ya hay un vehiculo registrado con esa placa");
            return false;
        } else {
            int espacio = espacio_libre(zona);
            Thing carro = new Thing(ciudad, 5, 8);
            carro.getIcon().setColor(Color.LIGHT_GRAY);
            carro.getIcon().setLabel(placa);
            chofer.pickThing();

            while (chofer.getAvenue() != zona) {
                chofer.move();
            }
            giroDerecha();
            while (chofer.getStreet() != espacio) {
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
            //se llena la matriz de control de vehiculos y la del tiempo con los datos
            matriz[espacio][zona] = carro;
            tiempos[espacio][zona] = System.currentTimeMillis();
            return true;
        }
    }

    //sacar vehiculo
    public boolean sacar_vehiculo() {
        int zona, espacio;
        String placa;
        double valor_a_pagar;
        double tiempo_aqui;
        System.out.println("ingrese la zona de parqueo de su vehiculo: ");
        zona = teclado.nextInt() - 1;
        System.out.println("ingrese placa del vehiculo (letra y numero): ");
        placa = teclado.next();
        teclado.nextLine();
        espacio = buscar_placa_zona(zona, placa);
        //buscar si el auto si esta en el parqueadero
        if (buscar_placa(placa) == false) {
            System.out.println("el vehiculo no se encuentra en el parqueadero");
            return false;
        }
        //buscar si la zona ingresada corresponde a la zona donde esta el vehiculo
        if (espacio == -1) {
            System.out.println("el vehiculo no se encuentra en esa zona de parqueo");
            return false;
        }

        //si el vehiculo se puede sacar directamente
        if (espacio == 4) {
            while (chofer.getAvenue() != zona) {
                chofer.move();
            }
            giroDerecha();
            while (chofer.getStreet() != espacio) {
                chofer.move();
            }
            //recoge vehiculo
            chofer.pickThing();
            girar(2);
            while (chofer.frontIsClear()) {
                chofer.move();
            }
            girar(1);
            while (chofer.frontIsClear()) {
                chofer.move();
            }
            girar(2);
            //llega a la salida
            matriz[espacio][zona] = null;
            tiempos[espacio][zona] = (System.currentTimeMillis() - tiempos[espacio][zona]) / 60000;
            valor_a_pagar = factura(tiempos[espacio][zona]);
            tiempo_aqui = tiempo_dentro(tiempos[espacio][zona]);
            System.out.println("lleva " + tiempo_aqui + " minutos en el parqueadero");
            System.out.println("el valor a pagar es:" + valor_a_pagar + " pesos.");
            ingresos_totales += valor_a_pagar;
            tiempos[espacio][zona] = 0;
            System.out.println("¡Gracias!,vuelva pronto");
            return true;
        }
        //si no se puede sacar directamente
        while (chofer.getAvenue() != zona) {
            chofer.move();
        }
        giroDerecha();
        mover(1);
        int contador_y = 4;
        int contador_espera = 0;
        boolean flag = true;
        while (flag) {
            if (!chofer.canPickThing()) {
                mover(1);
                contador_y--;
            } else {
                if (matriz[contador_y][zona].getIcon().getLabel().equals(placa)) {
                    System.out.println("Encontrado");
                    flag = false;
                    chofer.pickThing();
                    girar(2);
                    while (chofer.frontIsClear()) {
                        mover(1);
                    }
                    girar(1);
                    while (chofer.frontIsClear()) {
                        mover(1);
                    }
                    girar(2);
                    matriz[contador_y][zona] = null;
                    tiempos[contador_y][zona] = (System.currentTimeMillis() - tiempos[espacio][zona]) / 60000;
                    valor_a_pagar = factura(tiempos[espacio][zona]);
                    tiempo_aqui = tiempo_dentro(tiempos[espacio][zona]);
                    System.out.println("lleva " + tiempo_aqui + " minutos en el parqueadero");
                    System.out.println("el valor a pagar es:" + valor_a_pagar + " pesos.");
                    ingresos_totales += valor_a_pagar;
                    tiempos[contador_y][zona] = 0;
                    System.out.println("¡Gracias!,vuelva pronto");
                    //System.out.println("ingresos hasta ahora" + ingresos_totales );
                    mover(5);
                    giroDerecha();
                    //devolver carros en espera al parqueadero
                    int indice_espera = 0;
                    int movimiento = 0;
                    while (contador_espera > 0) {
                        mover(1);
                        chofer.pickThing();
                        girar(2);
                        mover(1);
                        girar(3);
                        int lugar = espacio_libre(zona);
                        //
                        while (chofer.getAvenue() != zona) {
                            chofer.move();
                            movimiento++;
                        }
                        giroDerecha();
                        while (chofer.getStreet() != lugar) {
                            chofer.move();
                        }
                        matriz[chofer.getStreet()][chofer.getAvenue()] = espera[indice_espera];
                        tiempos[chofer.getStreet()][chofer.getAvenue()] = tiempos_espera[indice_espera];
                        espera[indice_espera] = null;
                        tiempos_espera[indice_espera] = 0;
                        chofer.putThing();
                        girar(2);
                        while (chofer.frontIsClear()) {
                            chofer.move();
                        }
                        girar(1);
                        mover(movimiento + 1);
                        girar(1);
                        movimiento = 0;
                        contador_espera--;
                        indice_espera++;
                    }
                    girar(3);
                    while (chofer.frontIsClear()) {
                        mover(1);
                    }
                    girar(2);
                    return true;
                } else {
                    //lleva vahiculos a zona de espera
                    chofer.pickThing();
                    espera[contador_espera] = matriz[chofer.getStreet()][chofer.getAvenue()];
                    tiempos_espera[contador_espera] = tiempos[chofer.getStreet()][chofer.getAvenue()];
                    matriz[chofer.getStreet()][chofer.getAvenue()] = null;
                    tiempos[chofer.getStreet()][chofer.getAvenue()] = 0;
                    girar(2);
                    mover(5 - contador_y);
                    girar(1);
                    mover((3 - zona) + contador_espera);
                    girar(1);
                    mover(1);
                    chofer.putThing();
                    girar(2);
                    mover(1);
                    giroDerecha();
                    mover((3 - zona) + contador_espera);
                    contador_espera++;
                    giroDerecha();
                    contador_y--;
                    mover(4 - contador_y + 1);
                }

            }
        }

        return true;
    }

    //mostrar vehiculos en una seccion
    public boolean mostrar_zona() {
        System.out.println("ingrese la zona que desee conocer: ");
        int zona = teclado.nextInt() - 1;
        System.out.println("los vehiculos en la zona son: ");
        for (int i = 0; i < 5; i++) {
            if (matriz[i][zona] == null) {
                System.out.println("espacio: " + i + " no hay carro ");
            } else {
                System.out.println("espacio: " + i + " placa: " + matriz[i][zona].getIcon().getLabel() + " y lleva: " + tiempo_dentro(tiempos[i][zona]) + " minutos dentro del parqueadero");
            }
        }
        return true;
    }

    //imprime los ingresos totales de la jornada
    public boolean reporte_ingresos() {
        System.out.println("los ingresos en esta jornada de trabajo son: ");
        System.out.println(ingresos_totales);
        return true;
    }

}
