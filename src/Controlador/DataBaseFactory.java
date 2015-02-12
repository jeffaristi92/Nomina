/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import BD.DataBase;

/**
 *
 * @author ARISTIZABAL
 */
public class DataBaseFactory {

    DataBase baseDatos;

    public DataBaseFactory() {
        baseDatos = new DataBase("C:\\BDNomina");
        baseDatos.cargarDaTaBase();
    }

    public DataBase getDataBase(){
        return baseDatos;
    }
}
