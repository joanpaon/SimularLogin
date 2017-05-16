/*
 * Copyright 2017 José A. Pacheco Ondoño - joanpaon@gmail.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.japo.java.forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.japo.java.events.AEM;
import org.japo.java.events.KEM;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public class GUI extends JFrame {

    // Tamaño de la ventana
    public static final int VENTANA_ANC = 290;
    public static final int VENTANA_ALT = 240;

    // Nombre del fichero de datos
    private static final String FICHERO = "password.txt";

    // Referencias a los componentes
    JTextField txfUser;
    JPasswordField psfPass;
    JButton btnAceptar;

    // Credenciales de acceso
    Properties prpAcceso;

    public GUI() {
        // Inicialización PREVIA
        beforeInit();

        // Creación del interfaz
        initComponents();

        // Inicialización POSTERIOR
        afterInit();
    }

    // Construcción del IGU
    private void initComponents() {
        // Fuente para las etiquetas
        Font f = new Font("Calibri", Font.BOLD + Font.ITALIC, 16);

        // Gestor de Eventos de Accion
        AEM aem = new AEM(this);

        // Gestor de Eventos de Teclado
        KEM kem = new KEM(this);

        // Tamaños de los controles
        Dimension dimLabel = new Dimension(80, 30);
        Dimension dimField = new Dimension(120, 30);
        Dimension dimBoton = new Dimension(100, 30);

        // Etiqueta de usuario
        JLabel lblUser = new JLabel("Usuario");
        lblUser.setFont(f);
        lblUser.setSize(dimLabel);
        lblUser.setLocation(35, 35);
        lblUser.setHorizontalAlignment(JLabel.RIGHT);

        // Etiqueta de password
        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(f);
        lblPass.setSize(dimLabel);
        lblPass.setLocation(35, 85);
        lblPass.setHorizontalAlignment(JLabel.RIGHT);

        // Campo de texto de usuario
        txfUser = new JTextField();
        txfUser.setFont(f);
        txfUser.setSize(dimField);
        txfUser.setLocation(130, 30);
        txfUser.addActionListener(aem);

        // Campo de texto de password
        psfPass = new JPasswordField();
        psfPass.setFont(f);
        psfPass.setSize(dimField);
        psfPass.setLocation(130, 85);
        psfPass.addActionListener(aem);

        // Botón de aceptar
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(f);
        btnAceptar.setSize(dimBoton);
        btnAceptar.setLocation(30, 145);
        btnAceptar.addActionListener(aem);
        btnAceptar.addKeyListener(kem);

        // Botón de cancelar
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(f);
        btnCancelar.setSize(dimBoton);
        btnCancelar.setLocation(150, 145);
        btnCancelar.addActionListener(aem);
        btnCancelar.addKeyListener(kem);

        // Panel Principal
        JPanel pnlPpal = new JPanel();
        pnlPpal.setLayout(null);
        pnlPpal.add(lblUser);
        pnlPpal.add(lblPass);
        pnlPpal.add(txfUser);
        pnlPpal.add(psfPass);
        pnlPpal.add(btnAceptar);
        pnlPpal.add(btnCancelar);

        // Ventana principal
        setTitle("Simular Login");
        setContentPane(pnlPpal);
        setResizable(false);
        setSize(VENTANA_ANC, VENTANA_ALT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // Inicialización antes del IGU
    private void beforeInit() {

    }

    // Inicialización después del IGU
    private void afterInit() {
        try (FileReader fr = new FileReader(FICHERO)) {
            // Intanciar propiedades
            prpAcceso = new Properties();
            
            // Cargar las propiedades
            prpAcceso.load(fr);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos");
            terminarPrograma();
        }
    }

    public void procesarAccion(ActionEvent e) {
        if (e.getSource().equals(txfUser)) {
            psfPass.requestFocus();
        } else if (e.getSource().equals(psfPass)) {
            procesarCredencial();
            txfUser.requestFocus();
        } else if (e.getSource().equals(btnAceptar)) {
            procesarCredencial();
            txfUser.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "Proceso CANCELADO");
            terminarPrograma();
        }
    }

    public void procesarTecla(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            e.consume();
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (e.getSource().equals(btnAceptar)) {
                procesarCredencial();
                txfUser.requestFocus();
            } else {
                JOptionPane.showMessageDialog(this, "Proceso CANCELADO");
                terminarPrograma();
            }
        }
    }

    private void procesarCredencial() {
        // Datos del formulario
        String userAct = txfUser.getText();
        char[] passAct = psfPass.getPassword();

        // Datos de acceso
        String userRef = prpAcceso.getProperty("user", "");
        char[] passRef = prpAcceso.getProperty("pass", "").toCharArray();

        if (userAct.equals(userRef) && Arrays.equals(passAct, passRef)) {
            JOptionPane.showMessageDialog(this, "Acceso PERMITIDO");
            terminarPrograma();
        } else {
            JOptionPane.showMessageDialog(this, "Acceso DENEGADO");
        }
    }

    private void terminarPrograma() {
        // Ocultar la ventana
        setVisible(false);

        // Devolver
        dispose();

        // Cerrar programa
        System.exit(0);
    }

}
