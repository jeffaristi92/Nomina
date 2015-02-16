/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Controlador.ControladorDetalle;
import Controlador.ControladorDetalleFijo;
import Controlador.ControladorDetallePeriodo;
import Controlador.ControladorEmpleado;
import Controlador.ControladorPeriodo;
import Controlador.DataBaseFactory;
import Modelo.Detalle;
import Modelo.DetalleFijo;
import Modelo.DetallePeriodo;
import Modelo.Empleado;
import Modelo.Periodo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import nomina.Util;

/**
 *
 * @author yefguaar
 */
public class GestionDetallePeriodo extends javax.swing.JPanel {

    /**
     * Creates new form GestionDetallePeriodo
     */
    ControladorPeriodo controladorPeriodo;
    ControladorDetallePeriodo controladorDetallePeriodo;
    ControladorEmpleado controladorEmpleado;
    ControladorDetalle controladorDetalle;
    ControladorDetalleFijo controladorDetalleFijo;

    List<Periodo> lstPeriodo;
    List<Empleado> lstEmpleado;
    List<Detalle> lstDetalle;
    List<DetalleFijo> lstDetalleFijo;

    public GestionDetallePeriodo(DataBaseFactory f) {
        controladorPeriodo = new ControladorPeriodo(f.getDataBase());
        controladorDetallePeriodo = new ControladorDetallePeriodo(f.getDataBase());
        controladorEmpleado = new ControladorEmpleado(f.getDataBase());
        controladorDetalle = new ControladorDetalle(f.getDataBase());
        controladorDetalleFijo = new ControladorDetalleFijo(f.getDataBase());
        initComponents();
        inicializarListaPeriodos();
        inicializarEmpleados();
        inicializarDetalles();
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

    void inicializarDetalles() {
        lstDetalle = controladorDetalle.listarDetalles();
        for (Detalle d : lstDetalle) {
            jcbDetalle.addItem(d.Nombre + " - " + d.Tipo);
        }
    }

    void inicializarDetallesFijos() {
        List<DetalleFijo> lstAuxDetalleFijo = controladorDetalleFijo.listarDetallesFijos();
        lstDetalleFijo = new ArrayList<>();
        jcbDetalleFijo.removeAllItems();
        jtfValorUnitarioDetalleFijo.setText("");
        Empleado e = lstEmpleado.get(jcbEmpleado.getSelectedIndex());

        for (DetalleFijo d : lstAuxDetalleFijo) {
            if (d.Empleado.equals(e.Identificacion)) {
                lstDetalleFijo.add(d);
                jcbDetalleFijo.addItem(d.Nombre);
            }
        }
    }

    DetallePeriodo getDatosDetalle() {
        DetallePeriodo dp = new DetallePeriodo();
        dp.Periodo = lstPeriodo.get(jcbPeriodo.getSelectedIndex()).IdPeriodo;
        dp.Empleado = lstEmpleado.get(jcbEmpleado.getSelectedIndex()).Identificacion;
        dp.Detalle = lstDetalle.get(jcbDetalle.getSelectedIndex()).Nombre;
        dp.ValorUnitario = Double.parseDouble(jtfValorUnitarioDetalle.getText());
        dp.Cantidad = Integer.parseInt(jtfCantidadDetalleFijo.getText());
        return dp;
    }

    DetallePeriodo getDatosDetalleFijo() {
        DetallePeriodo dp = new DetallePeriodo();
        dp.Periodo = lstPeriodo.get(jcbPeriodo.getSelectedIndex()).IdPeriodo;
        dp.Empleado = lstEmpleado.get(jcbEmpleado.getSelectedIndex()).Identificacion;
        dp.Detalle = lstDetalleFijo.get(jcbDetalleFijo.getSelectedIndex()).Nombre;
        dp.ValorUnitario = Double.parseDouble(jtfValorUnitarioDetalleFijo.getText());
        dp.Cantidad = Integer.parseInt(jtfCantidadDetalleFijo.getText());
        return dp;
    }

    void actualizarTabla() {
        if (lstPeriodo.size() > 0 && lstEmpleado != null) {
            List<DetallePeriodo> lstAuxDetallesPeriodo = controladorDetallePeriodo.listarDetallePeriodos();
            DefaultTableModel modelo = (DefaultTableModel) jtbDetallePeriodo.getModel();

            int nroFIlas = modelo.getRowCount();
            for (int i = 0; i < nroFIlas; i++) {
                modelo.removeRow(0);
            }
            Periodo p = lstPeriodo.get(jcbPeriodo.getSelectedIndex());
            Empleado e = lstEmpleado.get(jcbEmpleado.getSelectedIndex());

            if (lstAuxDetallesPeriodo.size() > 0) {
                for (DetallePeriodo dp : lstAuxDetallesPeriodo) {
                    if (dp.Empleado.equals(e.Identificacion) && dp.Periodo.equals(p.IdPeriodo)) {
                        Object[] fila;
                        fila = new Object[]{
                            dp.Detalle,
                            dp.ValorUnitario,
                            dp.Cantidad,
                            dp.ValorUnitario * dp.Cantidad
                        };
                        modelo.addRow(fila);
                    }
                }
            }
            jtbDetallePeriodo.setModel(modelo);
        }
    }
    
    void actualizarValorUnitarioDetalle(){
        double valorTotal = 0;
        if(!jtfValorTotalDetalle.getText().isEmpty()){
            valorTotal = Double.parseDouble(jtfValorTotalDetalle.getText());
        }
        int cantidad = 1;
        
        if(!jtfCantidadDetalle.getText().isEmpty()){
            cantidad = Integer.parseInt(jtfCantidadDetalle.getText());
        }
        
        double valorUnitario = Math.floor(valorTotal/cantidad);
        
        jtfValorUnitarioDetalle.setText(valorUnitario+"");
    }
    
    void actualizarValorUnitarioDetalleFijo(){
        double valorTotal = 0;
        if(!jtfValorTotalDetalleFijo.getText().isEmpty()){
            valorTotal = Double.parseDouble(jtfValorTotalDetalleFijo.getText());
        }
        int cantidad = 1;
        
        if(!jtfCantidadDetalleFijo.getText().isEmpty()){
            cantidad = Integer.parseInt(jtfCantidadDetalleFijo.getText());
        }
        
        double valorUnitario = Math.floor(valorTotal/cantidad);
        
        jtfValorUnitarioDetalleFijo.setText(valorUnitario+"");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbDetallePeriodo = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jcbDetalleFijo = new javax.swing.JComboBox();
        jcbDetalle = new javax.swing.JComboBox();
        jcbEmpleado = new javax.swing.JComboBox();
        jcbPeriodo = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jtfValorUnitarioDetalle = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jtfCantidadDetalle = new javax.swing.JTextField();
        jbtAgregarDetalle = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jtfValorUnitarioDetalleFijo = new javax.swing.JTextField();
        jtfCantidadDetalleFijo = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jbtAgregarDetalleFijo = new javax.swing.JButton();
        jtfValorTotalDetalle = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jtfValorTotalDetalleFijo = new javax.swing.JTextField();

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("Gestion Detalle Periodo");

        jtbDetallePeriodo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Detalle", "Valor Unitario", "Cantidad", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jtbDetallePeriodo);

        jLabel2.setText("Periodo");

        jLabel3.setText("Empleado");

        jLabel4.setText("Detalle");

        jLabel5.setText("Detalle Fijo");

        jcbDetalleFijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbDetalleFijoActionPerformed(evt);
            }
        });

        jcbEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbEmpleadoActionPerformed(evt);
            }
        });

        jcbPeriodo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbPeriodoActionPerformed(evt);
            }
        });

        jLabel6.setText("Valor Unitario");

        jLabel7.setText("Cantidad");

        jtfCantidadDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfCantidadDetalleKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfCantidadDetalleKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfCantidadDetalleKeyTyped(evt);
            }
        });

        jbtAgregarDetalle.setText("Registrar");
        jbtAgregarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAgregarDetalleActionPerformed(evt);
            }
        });

        jLabel8.setText("Valor Unitario");

        jtfCantidadDetalleFijo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfCantidadDetalleFijoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfCantidadDetalleFijoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfCantidadDetalleFijoKeyTyped(evt);
            }
        });

        jLabel9.setText("Cantidad");

        jbtAgregarDetalleFijo.setText("Registrar");
        jbtAgregarDetalleFijo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtAgregarDetalleFijoActionPerformed(evt);
            }
        });

        jtfValorTotalDetalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfValorTotalDetalleKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfValorTotalDetalleKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfValorTotalDetalleKeyTyped(evt);
            }
        });

        jLabel10.setText("Valor Total");

        jLabel11.setText("Valor Total");

        jtfValorTotalDetalleFijo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jtfValorTotalDetalleFijoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jtfValorTotalDetalleFijoKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jtfValorTotalDetalleFijoKeyTyped(evt);
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
                            .addComponent(jLabel5)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jcbDetalleFijo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbEmpleado, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jcbPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfValorTotalDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jtfValorTotalDetalleFijo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfValorUnitarioDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfCantidadDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                                .addComponent(jbtAgregarDetalle))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfValorUnitarioDetalleFijo, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jtfCantidadDetalleFijo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbtAgregarDetalleFijo))))
                    .addComponent(jScrollPane1))
                .addContainerGap())
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jcbDetalleFijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtfValorTotalDetalleFijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jtfValorUnitarioDetalleFijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jtfCantidadDetalleFijo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtAgregarDetalleFijo)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jcbDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel10)
                        .addComponent(jtfValorTotalDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jtfValorUnitarioDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(jtfCantidadDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtAgregarDetalle)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jcbEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbEmpleadoActionPerformed
        // TODO add your handling code here:
        inicializarDetallesFijos();
        actualizarTabla();
    }//GEN-LAST:event_jcbEmpleadoActionPerformed

    private void jcbDetalleFijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbDetalleFijoActionPerformed
        // TODO add your handling code here:
        if (lstDetalleFijo.size() > 0) {
            DetalleFijo df = lstDetalleFijo.get(jcbDetalleFijo.getSelectedIndex());
            jtfValorUnitarioDetalleFijo.setText(df.ValorUnitario + "");
        }
    }//GEN-LAST:event_jcbDetalleFijoActionPerformed

    private void jcbPeriodoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbPeriodoActionPerformed
        // TODO add your handling code here:
        Periodo p = lstPeriodo.get(jcbPeriodo.getSelectedIndex());
        jtfCantidadDetalle.setText(p.Dias + "");
        jtfCantidadDetalleFijo.setText(p.Dias + "");
        actualizarTabla();
    }//GEN-LAST:event_jcbPeriodoActionPerformed

    private void jbtAgregarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAgregarDetalleActionPerformed
        // TODO add your handling code here:
        if (controladorDetallePeriodo.insertarDetallePeriodo(getDatosDetalle())) {
            JOptionPane.showMessageDialog(this, "Detalle Periodo registrado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            //limpiarFormulario();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar Detalle Periodo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbtAgregarDetalleActionPerformed

    private void jbtAgregarDetalleFijoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtAgregarDetalleFijoActionPerformed
        // TODO add your handling code here:
        if (controladorDetallePeriodo.insertarDetallePeriodo(getDatosDetalleFijo())) {
            JOptionPane.showMessageDialog(this, "Detalle Periodo registrado", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            //limpiarFormulario();
            actualizarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo registrar Detalle Periodo", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jbtAgregarDetalleFijoActionPerformed

    private void jtfValorTotalDetalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfValorTotalDetalleKeyTyped
        // TODO add your handling code here:
        if (!Util.validarCampoNumerico(evt, false, jtfValorTotalDetalle.getText())) {
            evt.consume();
        }
        actualizarValorUnitarioDetalle();
    }//GEN-LAST:event_jtfValorTotalDetalleKeyTyped

    private void jtfValorTotalDetalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfValorTotalDetalleKeyPressed
        // TODO add your handling code here:
        actualizarValorUnitarioDetalle();
    }//GEN-LAST:event_jtfValorTotalDetalleKeyPressed

    private void jtfValorTotalDetalleFijoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfValorTotalDetalleFijoKeyPressed
        // TODO add your handling code here:
        actualizarValorUnitarioDetalleFijo();
    }//GEN-LAST:event_jtfValorTotalDetalleFijoKeyPressed

    private void jtfCantidadDetalleKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCantidadDetalleKeyPressed
        // TODO add your handling code here:
        actualizarValorUnitarioDetalle();
    }//GEN-LAST:event_jtfCantidadDetalleKeyPressed

    private void jtfCantidadDetalleFijoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCantidadDetalleFijoKeyPressed
        // TODO add your handling code here:
        actualizarValorUnitarioDetalleFijo();
    }//GEN-LAST:event_jtfCantidadDetalleFijoKeyPressed

    private void jtfCantidadDetalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCantidadDetalleKeyTyped
        // TODO add your handling code here:
        if (!Util.validarCampoNumerico(evt, false, jtfCantidadDetalle.getText())) {
            evt.consume();
        }
        actualizarValorUnitarioDetalle();
    }//GEN-LAST:event_jtfCantidadDetalleKeyTyped

    private void jtfValorTotalDetalleFijoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfValorTotalDetalleFijoKeyTyped
        // TODO add your handling code here:
        if (!Util.validarCampoNumerico(evt, false, jtfValorTotalDetalleFijo.getText())) {
            evt.consume();
        }
        actualizarValorUnitarioDetalleFijo();
    }//GEN-LAST:event_jtfValorTotalDetalleFijoKeyTyped

    private void jtfCantidadDetalleFijoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCantidadDetalleFijoKeyTyped
        // TODO add your handling code here:
        if (!Util.validarCampoNumerico(evt, false, jtfCantidadDetalleFijo.getText())) {
            evt.consume();
        }
        actualizarValorUnitarioDetalleFijo();
    }//GEN-LAST:event_jtfCantidadDetalleFijoKeyTyped

    private void jtfValorTotalDetalleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfValorTotalDetalleKeyReleased
        // TODO add your handling code here:
        actualizarValorUnitarioDetalle();
    }//GEN-LAST:event_jtfValorTotalDetalleKeyReleased

    private void jtfCantidadDetalleKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCantidadDetalleKeyReleased
        // TODO add your handling code here:
        actualizarValorUnitarioDetalle();
    }//GEN-LAST:event_jtfCantidadDetalleKeyReleased

    private void jtfValorTotalDetalleFijoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfValorTotalDetalleFijoKeyReleased
        // TODO add your handling code here:
        actualizarValorUnitarioDetalleFijo();
    }//GEN-LAST:event_jtfValorTotalDetalleFijoKeyReleased

    private void jtfCantidadDetalleFijoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfCantidadDetalleFijoKeyReleased
        // TODO add your handling code here:
        actualizarValorUnitarioDetalleFijo();
    }//GEN-LAST:event_jtfCantidadDetalleFijoKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbtAgregarDetalle;
    private javax.swing.JButton jbtAgregarDetalleFijo;
    private javax.swing.JComboBox jcbDetalle;
    private javax.swing.JComboBox jcbDetalleFijo;
    private javax.swing.JComboBox jcbEmpleado;
    private javax.swing.JComboBox jcbPeriodo;
    private javax.swing.JTable jtbDetallePeriodo;
    private javax.swing.JTextField jtfCantidadDetalle;
    private javax.swing.JTextField jtfCantidadDetalleFijo;
    private javax.swing.JTextField jtfValorTotalDetalle;
    private javax.swing.JTextField jtfValorTotalDetalleFijo;
    private javax.swing.JTextField jtfValorUnitarioDetalle;
    private javax.swing.JTextField jtfValorUnitarioDetalleFijo;
    // End of variables declaration//GEN-END:variables
}
