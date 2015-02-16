/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.ControladorDetallePeriodo;
import Controlador.ControladorEmpleado;
import Controlador.ControladorEmpresa;
import Controlador.ControladorPeriodo;
import Controlador.DataBaseFactory;
import Modelo.DetallePeriodo;
import Modelo.Empleado;
import Modelo.Periodo;
import Reportes.GeneradorReporte;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import nomina.Util;

/**
 *
 * @author yefguaar
 */
public class GestionReporteComprobante extends javax.swing.JPanel {

    /**
     * Creates new form GestionReporteComprobante
     */
    ControladorPeriodo controladorPeriodo;
    ControladorDetallePeriodo controladorDetallePeriodo;
    ControladorEmpleado controladorEmpleado;
    ControladorEmpresa controladorEmpresa;
    List<Periodo> lstPeriodo;
    List<Empleado> lstEmpleado;
    GeneradorReporte generadorReporte;

    public GestionReporteComprobante(DataBaseFactory f) {
        generadorReporte = new GeneradorReporte();
        controladorPeriodo = new ControladorPeriodo(f.getDataBase());
        controladorDetallePeriodo = new ControladorDetallePeriodo(f.getDataBase());
        controladorEmpleado = new ControladorEmpleado(f.getDataBase());
        controladorEmpresa = new ControladorEmpresa(f.getDataBase());
        initComponents();
        inicializarListaPeriodos();
        inicializarEmpleados();
    }

    void inicializarListaPeriodos() {
        lstPeriodo = controladorPeriodo.listarPeriodos();

        for (Periodo p : lstPeriodo) {
            jcbPeriodo.addItem(p.FechaInicio + " - " + p.FechaFin);
        }
    }

    void inicializarEmpleados() {
        lstEmpleado = controladorEmpleado.listarEmpleados();
        for (Empleado e : lstEmpleado) {
            jcbEmpleado.addItem(e.Nombre + " " + e.Apellido);
        }
    }

    String getComprobante(Empleado e, Periodo p) {
        Date fecha = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
        String periodo = "Nomina: " + p.FechaInicio + " - " + p.FechaFin;
        double totalDevengado = 0;
        double totalDeducido = 0;
        double total = 0;

        List<DetallePeriodo> lstDetallesDevengados = controladorDetallePeriodo.listarDetallePeriodosTipoXEmpleado(p, e, "Devengado");
        List<DetallePeriodo> lstDetallesDeduccion = controladorDetallePeriodo.listarDetallePeriodosTipoXEmpleado(p, e, "Deduccion");
        List<List<String>> lstDetalles = getDetalles(lstDetallesDevengados, lstDetallesDeduccion);
        List<String> lstFilas = new ArrayList<>();

        for (List<String> detalle : lstDetalles) {
            lstFilas.add(generadorReporte.getFila(detalle));
        }

        for (DetallePeriodo devengado : lstDetallesDevengados) {
            totalDevengado += devengado.ValorUnitario * devengado.Cantidad;
        }
        for (DetallePeriodo deducido : lstDetallesDeduccion) {
            totalDeducido += deducido.ValorUnitario * deducido.Cantidad;
        }
        
        total = totalDevengado - totalDeducido;

        String encabezado = generadorReporte.getEncabezado(controladorEmpresa.getEmpresa(e.Empresa), e, formateador.format(fecha), periodo);
        String detalle = generadorReporte.getDetalle(lstFilas);
        String resumen = generadorReporte.getResumen(totalDevengado + "", totalDeducido + "", total);
        String comprobante = generadorReporte.getComprobante(encabezado, detalle, resumen);
        return comprobante;
    }

    List<List<String>> getDetalles(List<DetallePeriodo> lstDetallesDevengados, List<DetallePeriodo> lstDetallesDeduccion) {
        List<List<String>> lstDetalles = new ArrayList<>();

        int nroDetalles = (lstDetallesDeduccion.size() > lstDetallesDevengados.size()) ? lstDetallesDeduccion.size() : lstDetallesDevengados.size();

        for (int i = 0; i < nroDetalles; i++) {
            List<String> detalle = new ArrayList<>();
            if (i < lstDetallesDevengados.size()) {
                detalle.add(lstDetallesDevengados.get(i).Detalle);
                detalle.add(lstDetallesDevengados.get(i).Cantidad + "");
                detalle.add("$ "+(lstDetallesDevengados.get(i).ValorUnitario * lstDetallesDevengados.get(i).Cantidad) + "");
            } else {
                detalle.add("");
                detalle.add("");
                detalle.add("");
            }
            if (i < lstDetallesDeduccion.size()) {
                detalle.add(lstDetallesDeduccion.get(i).Detalle);
                detalle.add(lstDetallesDeduccion.get(i).Cantidad + "");
                detalle.add("$ "+(lstDetallesDeduccion.get(i).ValorUnitario * lstDetallesDeduccion.get(i).Cantidad) + "");
            } else {
                detalle.add("");
                detalle.add("");
                detalle.add("");
            }
            lstDetalles.add(detalle);
        }

        return lstDetalles;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcbPeriodo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jcbEmpleado = new javax.swing.JComboBox();
        jbtGenerarIndividual = new javax.swing.JButton();
        jbtGenerarTotal = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Gestion Reporte Comprobante de Pago Nomina");

        jLabel2.setText("Periodo");

        jLabel3.setText("Empleado");

        jbtGenerarIndividual.setText("Generar Comprobante Individual");
        jbtGenerarIndividual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGenerarIndividualActionPerformed(evt);
            }
        });

        jbtGenerarTotal.setText("Generar Comprobante Total");
        jbtGenerarTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtGenerarTotalActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jbtGenerarTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jbtGenerarIndividual, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(449, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jcbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jcbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jbtGenerarIndividual)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jbtGenerarTotal)
                .addContainerGap(300, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jbtGenerarIndividualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGenerarIndividualActionPerformed
        // TODO add your handling code here:
        Empleado e = lstEmpleado.get(jcbEmpleado.getSelectedIndex());
        Periodo p = lstPeriodo.get(jcbPeriodo.getSelectedIndex());
        String comprobante = getComprobante(e, p);
        List<String> lstComprobantes = new ArrayList<>();
        lstComprobantes.add(comprobante);
        lstComprobantes.add(comprobante);
        String reporte = generadorReporte.getReporte(lstComprobantes);
        Util.save(reporte);
    }//GEN-LAST:event_jbtGenerarIndividualActionPerformed

    private void jbtGenerarTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtGenerarTotalActionPerformed
        // TODO add your handling code here:
        Periodo p = lstPeriodo.get(jcbPeriodo.getSelectedIndex());
        List<String> lstComprobantes = new ArrayList<>();
        for (Empleado e : lstEmpleado) {
            String comprobante = getComprobante(e, p);
            lstComprobantes.add(comprobante);
            lstComprobantes.add(comprobante);
        }
        String reporte = generadorReporte.getReporte(lstComprobantes);
        Util.save(reporte);
    }//GEN-LAST:event_jbtGenerarTotalActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JButton jbtGenerarIndividual;
    private javax.swing.JButton jbtGenerarTotal;
    private javax.swing.JComboBox jcbEmpleado;
    private javax.swing.JComboBox jcbPeriodo;
    // End of variables declaration//GEN-END:variables
}
