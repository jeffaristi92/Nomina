/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import BD.DataBase;
import Modelo.Detalle;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yefguaar
 */
public class ControladorDetalle {

    DataBase baseDatos;

    public ControladorDetalle(DataBase baseDatos) {
        this.baseDatos = baseDatos;
    }

    public List<Detalle> listarDetalles() {
        List<Detalle> lstDetalles = new ArrayList<>();

        for (String[] fila : baseDatos.Detalle) {
            Detalle d = new Detalle();
            d.Nombre = fila[0];
            d.Descripcion = fila[1];
            d.Tipo = fila[2];
            lstDetalles.add(d);
        }

        return lstDetalles;
    }

    public boolean insertarDetalle(Detalle d) {
        baseDatos.Detalle.add(new String[]{d.Nombre,d.Descripcion,d.Tipo});
        baseDatos.actualizarDetalle();
        return true;
    }

    public boolean actualizarDetalle(Detalle d) {
        for (int i = 0; i < baseDatos.Detalle.size(); i++) {
            if (baseDatos.Detalle.get(i)[1].equals(d.Nombre)) {
                baseDatos.Detalle.get(i)[0] = d.Nombre;
                baseDatos.Detalle.get(i)[1] = d.Descripcion;
                baseDatos.Detalle.get(i)[2] = d.Tipo;

                baseDatos.actualizarDetalle();
                break;
            }
        }
        return true;
    }

}
