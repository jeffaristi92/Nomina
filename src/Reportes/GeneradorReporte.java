/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Modelo.Empleado;
import Modelo.Empresa;
import java.util.List;

/**
 *
 * @author yefguaar
 */
public class GeneradorReporte {

    public String getFila(List<String> campos) {
        String fila = "<tr>";

        for (String campo : campos) {
            fila = fila + "<td colspan=\"1\">" + campo + "</td>";
        }
        fila = fila + "</tr>";

        return fila;
    }

    public String getEncabezado(Empresa empresa, Empleado empleado, String fecha, String periodoPago) {
        String encabezado
                = "<thead>\n"
                + "<tr>\n"
                + "<td colspan=\"4\">" + empresa.Nombre + "</td>\n"
                + "<td rowspan=\"5\" colspan=\"2\">Logo</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"4\">" + empresa.Nit + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"4\">COMPROBANTE DE PAGO</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"4\">LIQUIDACION DE NOMINA</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"4\">" + fecha + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"4\">" + empleado.Nombre + " " + empleado.Apellido + "</td>\n"
                + "<td colspan=\"2\">" + empleado.Identificacion + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"2\">" + periodoPago + "</td>\n"
                + "<td colspan=\"2\">" + empleado.Pension + "</td>\n"
                + "<td colspan=\"2\">" + empleado.Salud + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"2\">" + empleado.Banco + "</td>\n"
                + "<td colspan=\"1\">" + empleado.Cuenta + "</td>\n"
                + "<td colspan=\"1\">" + empleado.SalarioBasico + "</td>\n"
                + "<td colspan=\"2\">" + empleado.Cargo + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"1\">DEVENGADOS</td>\n"
                + "<td colspan=\"1\">CANTIDAD</td>\n"
                + "<td colspan=\"1\">VALOR</td>\n"
                + "<td colspan=\"1\">DEDUCCIONES</td>\n"
                + "<td colspan=\"1\">CANTIDAD</td>\n"
                + "<td colspan=\"1\">VALOR</td>\n"
                + "</tr>\n"
                + "</thead>\n";

        return encabezado;
    }

    public String getDetalle(List<String> lstDetalles) {
        String detalle = "<tbody>\n";
        for (String aux : lstDetalles) {
            detalle = detalle + aux;
        }
        detalle = detalle + "</tbody>\n";
        return detalle;
    }

    public String getComprobante(String encabezado, String detalle) {
        String reporte = ""
                + "<table summary=\"Nomina\" border=\"1\" width=\"90%\" cellspacing=\"0\" bordercolor=\"black\" cellpadding=\"5\" align=\"center\">\n"
                + encabezado
                + detalle
                + "</table>";
        return reporte;
    }

    public String getReporte(List<String> lstComprobantes) {
        String reporte = 
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                    "<head>\n" +
                        "<title>Reporte Nomina</title>\n" +
                        "<meta charset=\"utf-8\">\n" +
                    "</head>\n" +
                    "<body>";
        
        for(String comprobante :lstComprobantes){
            reporte = reporte + comprobante;
        }
        
        reporte = reporte + "</body></html>";
        
        return reporte;
    }
}
