/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import BD.DataBase;
import Modelo.DetalleFijo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yefguaar
 */
public class ControladorDetalleFijo {

    DataBase baseDatos;

    public ControladorDetalleFijo(DataBase baseDatos) {
        this.baseDatos = baseDatos;
    }

    public List<DetalleFijo> listarDetallesFijos() {
        List<DetalleFijo> listarDetallesFijos = new ArrayList<>();

        for (String[] fila : baseDatos.DetalleFijo) {
            DetalleFijo df = new DetalleFijo();
            df.IdDetalleFijo = fila[0];
            df.Empleado = fila[1];
            df.Nombre = fila[2];
            df.ValorUnitario = Double.parseDouble(fila[3]);
            listarDetallesFijos.add(df);
        }

        return listarDetallesFijos;
    }

    public boolean insertarDetalleFijo(DetalleFijo df) {
        baseDatos.DetalleFijo.add(new String[]{(baseDatos.DetalleFijo.size()+1)+"", df.Empleado, df.Nombre, df.ValorUnitario + ""});
        baseDatos.actualizarDetalleFijo();
        return true;
    }
}
