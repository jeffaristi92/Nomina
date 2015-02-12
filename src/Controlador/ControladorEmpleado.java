/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import BD.DataBase;
import Modelo.Empleado;
import java.util.ArrayList;

/**
 *
 * @author ARISTIZABAL
 */
public class ControladorEmpleado {

    DataBase baseDatos;

    public ControladorEmpleado(DataBase baseDatos) {
        this.baseDatos = baseDatos;
    }

    public boolean insertarEmpleado(Empleado e) {
        baseDatos.Empleado.add(new String[]{e.Empresa, e.Identificacion, e.Nombre, e.Apellido, e.Cargo, "" + e.SalarioBasico, e.Pension, e.Salud, e.Banco, e.Cuenta});
        baseDatos.actualizarEmpleado();
        return true;
    }

    public ArrayList<Empleado> listarEmpleados() {
        ArrayList<Empleado> listaEmpleados = new ArrayList<>();

        for (String[] fila : baseDatos.Empleado) {
            Empleado e = new Empleado();
            e.Empresa = fila[0];
            e.Identificacion = fila[1];
            e.Nombre = fila[2];
            e.Apellido = fila[3];
            e.Cargo = fila[4];
            e.SalarioBasico = Double.parseDouble(fila[5]);
            e.Pension = fila[6];
            e.Salud = fila[7];
            e.Banco = fila[8];
            e.Cuenta = fila[9];
            listaEmpleados.add(e);
        }

        return listaEmpleados;
    }

    public boolean actualizarEmpleado(Empleado e) {
        for (int i = 0; i < baseDatos.Empleado.size(); i++) {
            if (baseDatos.Empleado.get(i)[1].equals(e.Identificacion)) {
                baseDatos.Empleado.get(i)[0] = e.Empresa;
                baseDatos.Empleado.get(i)[1] = e.Identificacion;
                baseDatos.Empleado.get(i)[2] = e.Nombre;
                baseDatos.Empleado.get(i)[3] = e.Apellido;
                baseDatos.Empleado.get(i)[4] = e.Cargo;
                baseDatos.Empleado.get(i)[5] = "" + e.SalarioBasico;
                baseDatos.Empleado.get(i)[6] = e.Pension;
                baseDatos.Empleado.get(i)[7] = e.Salud;
                baseDatos.Empleado.get(i)[8] = e.Banco;
                baseDatos.Empleado.get(i)[9] = e.Cuenta;
                baseDatos.actualizarEmpleado();
                break;
            }
        }
        return true;
    }
}
