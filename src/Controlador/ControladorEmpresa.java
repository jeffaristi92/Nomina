/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import BD.DataBase;
import Modelo.Empresa;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yefguaar
 */
public class ControladorEmpresa {

    DataBase baseDatos;

    public ControladorEmpresa(DataBase baseDatos) {
        this.baseDatos = baseDatos;
    }

    public List<Empresa> listarEmpresas() {
        List<Empresa> lstEmpresas = new ArrayList<>();
        for (String[] fila : baseDatos.Empresa) {
            Empresa e = new Empresa();
            e.Nit = fila[0];
            e.Nombre = fila[1];
            lstEmpresas.add(e);
        }
        return lstEmpresas;
    }

    public Empresa getEmpresa(String nit) {
        List<Empresa> lstEmpresas = listarEmpresas();
        for (Empresa e : lstEmpresas) {
            if (e.Nit.equals(nit)) {
                return e;
            }
        }
        return null;
    }
}
