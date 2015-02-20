/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Modelo.Empleado;
import Modelo.Empresa;
import java.util.List;
import Transversal.Util;

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
                + "<td colspan=\"4\">"
                + "<br>" + empresa.Nombre + "<br>"
                + "<br>" + empresa.Nit + "<br>"
                + "<br>COMPROBANTE DE PAGO<br>"
                + "<br>LIQUIDACION DE NÓMINA<br>"
                + "<br>" + fecha + "<br>"
                + "<br></td>\n"
                + "<td colspan=\"2\" align=\"center\"><img src=\"C:/BDNomina/logo_open.jpg\" width=\"100\" height=\"100\"/></td>"
                + "<tr>\n"
                + "<td colspan=\"4\">" + empleado.Nombre + " " + empleado.Apellido + "</td>\n"
                + "<td colspan=\"2\">C.C. " + empleado.Identificacion + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"2\">" + periodoPago + "</td>\n"
                + "<td colspan=\"2\">Salud: " + empleado.Pension + "</td>\n"
                + "<td colspan=\"2\">Pensión: " + empleado.Salud + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"2\">Banco: " + empleado.Banco + "</td>\n"
                + "<td colspan=\"1\">Cuenta: " + empleado.Cuenta + "</td>\n"
                + "<td colspan=\"1\">Básico: $ " + empleado.SalarioBasico + "</td>\n"
                + "<td colspan=\"2\">Cargo: " + empleado.Cargo + "</td>\n"
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

    public String getResumen(String totalDevengado, String totalDeducido, double total) {
        String resumen = "<tfoot>\n"
                + "<tr>\n"
                + "<td colspan=\"1\"></td>\n"
                + "<td colspan=\"1\">SUBTOTAL</td>\n"
                + "<td colspan=\"1\">$ " + totalDevengado + "</td>\n"
                + "<td colspan=\"1\"></td>\n"
                + "<td colspan=\"1\">SUBTOTAL</td>\n"
                + "<td colspan=\"1\">$ " + totalDeducido + "</td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"1\"></td>\n"
                + "<td colspan=\"1\">TOTAL</td>\n"
                + "<td colspan=\"1\">$ " + total + "</td>\n"
                + "<td colspan=\"1\"></td>\n"
                + "<td colspan=\"1\"></td>\n"
                + "<td colspan=\"1\"></td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"6\" align=\"center\"><br>" + Util.convertNumberToLetter(total) + " M/CTE<br><br></td>\n"
                + "</tr>\n"
                + "<tr>\n"
                + "<td colspan=\"6\"><br><br>__________________<br>FIRMA EMPLEADO<br>C.C.<br><br></td>\n"
                + "</tr>\n"
                + "</tfoot>";
        return resumen;
    }

    public String getComprobante(String encabezado, String detalle, String resumen) {
        String reporte = ""
                + "<table class=\"EstiloTabla\" summary=\"Nomina\" border=\"1\" width=\"90%\" cellspacing=\"0\" bordercolor=\"black\" cellpadding=\"5\" align=\"center\">\n"
                + encabezado
                + resumen
                + detalle
                + "</table>";
        return reporte;
    }

    public String getReporte(List<String> lstComprobantes) {
        String reporte
                = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<title>Reporte Nomina</title>\n"
                + "<meta charset=\"utf-8\">\n"
                + "<style type=\"text/css\">\n"
                + ".EstiloTabla {\n"
                + "font-size: 10px;\n"
                + "}\n"
                + "</style>"
                + "</head>\n"
                + "<body>";

        for (String comprobante : lstComprobantes) {
            reporte = reporte + comprobante + "<br>";
        }

        reporte = reporte + "</body></html>";

        return reporte;
    }
}
