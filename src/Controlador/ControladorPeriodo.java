/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import BD.DataBase;
import Modelo.Periodo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yefguaar
 */
public class ControladorPeriodo {

    DataBase baseDatos;

    public ControladorPeriodo(DataBase baseDatos) {
        this.baseDatos = baseDatos;
    }

    public List<Periodo> listarPeriodos() {
        List<Periodo> lstPeriodos = new ArrayList<>();

        for (String[] fila : baseDatos.Periodo) {
            Periodo p = new Periodo();
            p.IdPeriodo = fila[0];
            p.FechaInicio = fila[1];
            p.FechaFin = fila[2];
            p.Dias = Integer.parseInt(fila[3]);
            lstPeriodos.add(p);
        }

        return lstPeriodos;
    }

    public boolean insertarPeriodo(Periodo p) {
        baseDatos.Periodo.add(new String[]{(baseDatos.Periodo.size()+1)+"",p.FechaInicio,p.FechaFin,p.Dias+""});
        baseDatos.actualizarPeriodo();
        return true;
    }

}
