/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author ARISTIZABAL
 */
public class DataBase {

    String Directorio;
    public ArrayList<String[]> Empresa;
    public ArrayList<String[]> Empleado;
    public ArrayList<String[]> Detalle;
    public ArrayList<String[]> DetalleFijo;
    public ArrayList<String[]> Periodo;
    public ArrayList<String[]> DetallePeriodo;

    public DataBase(String directorio) {
        Directorio = directorio;
        Empresa = new ArrayList<>();
        Empleado = new ArrayList<>();
        Detalle = new ArrayList<>();
        DetalleFijo = new ArrayList<>();
        Periodo = new ArrayList<>();
        DetallePeriodo = new ArrayList<>();
    }

    public void cargarDaTaBase() {
        File directorio = new File(Directorio);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        cargarEmpresa();
        cargarEmpleado();
        cargarDetalle();
        cargarDetalleFijo();
        cargarPeriodo();
        cargarDetallePeriodo();
    }

    boolean cargarPeriodo() {
        ArrayList<String> contenidoArchivo = leerArchivo(Directorio + "\\Periodo.txt");
        for (String linea : contenidoArchivo) {
            if (!linea.isEmpty()) {
                Periodo.add(linea.split("\\|"));
            }
        }
        return true;
    }
    
    boolean cargarDetallePeriodo() {
        ArrayList<String> contenidoArchivo = leerArchivo(Directorio + "\\DetallePeriodo.txt");
        for (String linea : contenidoArchivo) {
            if (!linea.isEmpty()) {
                DetallePeriodo.add(linea.split("\\|"));
            }
        }
        return true;
    }

    boolean cargarEmpresa() {
        ArrayList<String> contenidoArchivo = leerArchivo(Directorio + "\\Empresa.txt");
        for (String linea : contenidoArchivo) {
            if (!linea.isEmpty()) {
                Empresa.add(linea.split("\\|"));
            }
        }
        return true;
    }

    boolean cargarEmpleado() {
        ArrayList<String> contenidoArchivo = leerArchivo(Directorio + "\\Empleado.txt");
        for (String linea : contenidoArchivo) {
            if (!linea.isEmpty()) {
                Empleado.add(linea.split("\\|"));
            }
        }
        return true;
    }

    boolean cargarDetalle() {
        ArrayList<String> contenidoArchivo = leerArchivo(Directorio + "\\Detalle.txt");
        for (String linea : contenidoArchivo) {
            if (!linea.isEmpty()) {
                Detalle.add(linea.split("\\|"));
            }
        }
        return true;
    }

    boolean cargarDetalleFijo() {
        ArrayList<String> contenidoArchivo = leerArchivo(Directorio + "\\DetalleFijo.txt");
        for (String linea : contenidoArchivo) {
            if (!linea.isEmpty()) {
                DetalleFijo.add(linea.split("\\|"));
            }
        }
        return true;
    }

    public boolean actualizarPeriodo() {
        escribirArchivo(Directorio + "\\Periodo.txt", Periodo);
        return true;
    }

    public boolean actualizarDetallePeriodo() {
        escribirArchivo(Directorio + "\\DetallePeriodo.txt", DetallePeriodo);
        return true;
    }
    
    public boolean actualizarEmpleado() {
        escribirArchivo(Directorio + "\\Empleado.txt", Empleado);
        return true;
    }

    public boolean actualizarEmpresa() {
        escribirArchivo(Directorio + "\\Empresa.txt", Empresa);
        return true;
    }

    public boolean actualizarDetalle() {
        escribirArchivo(Directorio + "\\Detalle.txt", Detalle);
        return true;
    }

    public boolean actualizarDetalleFijo() {
        escribirArchivo(Directorio + "\\DetalleFijo.txt", DetalleFijo);
        return true;
    }

    String concatenarFila(String[] fila) {
        String linea = "";
        for (int i = 0; i < fila.length; i++) {
            if (i == 0) {
                linea = fila[i];
            } else {
                linea += "|" + fila[i];
            }
        }
        return linea;
    }

    boolean escribirArchivo(String rutaArchivo, ArrayList<String[]> contenidoArchivo) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(rutaArchivo);
            pw = new PrintWriter(fichero);

            for (String[] linea : contenidoArchivo) {
                pw.println(concatenarFila(linea));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return true;
    }

    ArrayList<String> leerArchivo(String rutaArchivo) {
        ArrayList<String> contenidoArchivo = new ArrayList<>();

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(rutaArchivo);

            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                contenidoArchivo.add(linea);
            }
        } catch (Exception e) {
            contenidoArchivo.clear();
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return contenidoArchivo;
    }
}
