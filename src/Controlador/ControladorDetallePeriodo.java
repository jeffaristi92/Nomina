/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import BD.DataBase;
import Modelo.DetallePeriodo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yefguaar
 */
public class ControladorDetallePeriodo {

    DataBase baseDatos;

    public ControladorDetallePeriodo(DataBase baseDatos) {
        this.baseDatos = baseDatos;
    }

    public List<DetallePeriodo> listarDetallePeriodos() {
        List<DetallePeriodo> lstDetallePeriodos = new ArrayList<>();

        for (String[] fila : baseDatos.DetallePeriodo) {
            DetallePeriodo dp = new DetallePeriodo();
            dp.IdDetallePeriodo = fila[0];
            dp.Periodo = fila[1];
            dp.Empleado = fila[2];
            dp.Detalle = fila[3];
            dp.ValorUnitario = Double.parseDouble(fila[4]);
            dp.Cantidad = Integer.parseInt(fila[5]);
            lstDetallePeriodos.add(dp);
        }

        return lstDetallePeriodos;
    }

    public boolean insertarDetallePeriodo(DetallePeriodo dp) {
        baseDatos.DetallePeriodo.add(new String[]{(baseDatos.DetallePeriodo.size()+1)+"",dp.Periodo,dp.Empleado,dp.Detalle,dp.ValorUnitario+"",dp.Cantidad+""});
        baseDatos.actualizarDetallePeriodo();
        return true;
    }

}
