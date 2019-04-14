/*
 * Copyright 2019 José A. Pacheco Ondoño - joanpaon@gmail.com.
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

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.japo.java.components.BackgroundPanel;
import org.japo.java.events.AEM;
import org.japo.java.events.KEM;
import org.japo.java.events.MEM;
import org.japo.java.events.MMEM;
import org.japo.java.libraries.UtilesSwing;

/**
 *
 * @author José A. Pacheco Ondoño - joanpaon@gmail.com
 */
public final class GUI extends JFrame {

    // Propiedades App
    public static final String PRP_FAVICON_RESOURCE = "favicon_resource";
    public static final String PRP_FONT_RESOURCE = "font_resource";
    public static final String PRP_FORM_HEIGHT = "form_height";
    public static final String PRP_FORM_WIDTH = "form_width";
    public static final String PRP_FORM_TITLE = "form_title";
    public static final String PRP_IMAGE_RESOURCE = "image_resource";
    public static final String PRP_LOOK_AND_FEEL_PROFILE = "look_and_feel_profile";
    public static final String PRP_USER = "user";
    public static final String PRP_PASS = "pass";

    // Valores por Defecto
    public static final String DEF_FAVICON_RESOURCE = "img/favicon.png";
    public static final String DEF_FONT_FALLBACK_NAME = Font.SERIF;
    public static final String DEF_FONT_SYSTEM_NAME = "Kaufmann BT";
    public static final int DEF_FORM_HEIGHT = 300;
    public static final int DEF_FORM_WIDTH = 500;
    public static final String DEF_FORM_TITLE = "Swing Manual App";
    public static final String DEF_LOOK_AND_FEEL_PROFILE = UtilesSwing.LNF_WINDOWS_PROFILE;
    public static final String DEF_USER = "admin";
    public static final String DEF_PASS = "123456";

    // Referencias
    private final Properties prp;

    // Componentes
    private JLabel lblUser;
    private JLabel lblPass;
    private JTextField txfUser;
    private JPasswordField psfPass;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private JPanel pnlPpal;

    // Fuentes
    private Font fntSample;

    // Imágenes
    private Image imgPapiro;

    // Posición ventana
    private int xIni;
    private int yIni;

    // Constructor
    public GUI(Properties prp) {
        // Conectar Referencia
        this.prp = prp;

        // Inicialización Anterior
        initBefore();

        // Creación Interfaz
        initComponents();

        // Inicializacion Posterior
        initAfter();
    }

    // Construcción - GUI
    private void initComponents() {
        // Fuentes
        fntSample = UtilesSwing.generarFuenteRecurso(
                prp.getProperty(PRP_FONT_RESOURCE),
                DEF_FONT_SYSTEM_NAME,
                DEF_FONT_FALLBACK_NAME);

        // Imágenes
        imgPapiro = UtilesSwing.importarImagenRecurso(
                prp.getProperty(PRP_IMAGE_RESOURCE));

        // Etiqueta Usuario
        lblUser = new JLabel("Usuario");
        lblUser.setFont(fntSample.deriveFont(Font.BOLD + Font.ITALIC, 30f));
        lblUser.setSize(160, 40);
        lblUser.setLocation(35, 60);
        lblUser.setHorizontalAlignment(JLabel.RIGHT);

        // Etiqueta Password
        lblPass = new JLabel("Contraseña");
        lblPass.setFont(fntSample.deriveFont(Font.BOLD + Font.ITALIC, 30f));
        lblPass.setSize(160, 40);
        lblPass.setLocation(35, 120);
        lblPass.setHorizontalAlignment(JLabel.RIGHT);

        // Campo de texto de usuario
        txfUser = new JTextField();
        txfUser.setFont(fntSample.deriveFont(Font.BOLD + Font.ITALIC, 30f));
        txfUser.setSize(200, 40);
        txfUser.setLocation(220, 60);

        // Campo de texto de password
        psfPass = new JPasswordField();
        psfPass.setFont(new Font("SansSerif", Font.BOLD + Font.ITALIC, 16));
        psfPass.setSize(200, 40);
        psfPass.setLocation(220, 120);

        // Botón Aceptar
        btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(fntSample.deriveFont(Font.BOLD + Font.ITALIC, 30f));
        btnAceptar.setSize(140, 40);
        btnAceptar.setLocation(80, 200);

        // Botón Cancelar
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(fntSample.deriveFont(Font.BOLD + Font.ITALIC, 30f));
        btnCancelar.setSize(140, 40);
        btnCancelar.setLocation(250, 200);

        // Panel Principal
        pnlPpal = new BackgroundPanel(imgPapiro);
        pnlPpal.setLayout(null);
        pnlPpal.add(lblUser);
        pnlPpal.add(lblPass);
        pnlPpal.add(txfUser);
        pnlPpal.add(psfPass);
        pnlPpal.add(btnAceptar);
        pnlPpal.add(btnCancelar);

        // Ventana Principal
        setContentPane(pnlPpal);
        setTitle(prp.getProperty(PRP_FORM_TITLE, DEF_FORM_TITLE));
        try {
            int height = Integer.parseInt(prp.getProperty(PRP_FORM_HEIGHT));
            int width = Integer.parseInt(prp.getProperty(PRP_FORM_WIDTH));
            setSize(width, height);
        } catch (NumberFormatException e) {
            setSize(DEF_FORM_WIDTH, DEF_FORM_HEIGHT);
        }
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    // Inicialización Anterior    
    private void initBefore() {
        // Establecer LnF
        UtilesSwing.establecerLnFProfile(prp.getProperty(
                PRP_LOOK_AND_FEEL_PROFILE, DEF_LOOK_AND_FEEL_PROFILE));
    }

    // Inicialización Posterior
    private void initAfter() {
        // Establecer Favicon
        UtilesSwing.establecerFavicon(this, prp.getProperty(
                PRP_FAVICON_RESOURCE, DEF_FAVICON_RESOURCE));

        // Registra los Gestores de Eventos
        txfUser.addActionListener(new AEM(this));
        txfUser.addKeyListener(new KEM(this));
        psfPass.addActionListener(new AEM(this));
        psfPass.addKeyListener(new KEM(this));
        btnAceptar.addActionListener(new AEM(this));
        btnAceptar.addKeyListener(new KEM(this));
        btnCancelar.addActionListener(new AEM(this));
        btnCancelar.addKeyListener(new KEM(this));
        addKeyListener(new KEM(this));
        addMouseListener(new MEM(this));
        addMouseMotionListener(new MMEM(this));
    }

    // Evento de Acción - Respuesta
    public void procesarAccion(ActionEvent e) {
        if (e.getSource().equals(txfUser)) {
            psfPass.requestFocus();
        } else if (e.getSource().equals(psfPass)) {
//            procesarCredencial();
            btnAceptar.requestFocus();
//            txfUser.requestFocus();
        } else if (e.getSource().equals(btnAceptar)) {
            procesarCredencial();
            txfUser.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "Proceso CANCELADO");
            UtilesSwing.terminarPrograma(this);
        }
    }

    // Evento de Teclado - Respuesta
    public final void procesarTecla(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                JOptionPane.showMessageDialog(this, "Proceso CANCELADO");
                UtilesSwing.terminarPrograma(this);
                break;
            case KeyEvent.VK_SPACE:
                if (e.getSource() instanceof JButton) {
                    e.consume();
                }
                break;
            case KeyEvent.VK_ENTER:
                if (e.getSource().equals(btnAceptar)) {
                    procesarCredencial();
                    txfUser.requestFocus();
                } else if (e.getSource().equals(btnCancelar)) {
                    JOptionPane.showMessageDialog(this, "Proceso CANCELADO");
                    UtilesSwing.terminarPrograma(this);
                }
        }
    }

    // Valida la credencial
    private void procesarCredencial() {
        try {
            // Credencial Introducida
            String userAct = txfUser.getText();
            char[] passAct = psfPass.getPassword();

            // Credencial Referencia
            String userRef = prp.getProperty(PRP_USER, DEF_USER);
            char[] passRef = prp.getProperty(PRP_PASS, DEF_PASS).toCharArray();

            // Procesar Credencial
            if (userAct.equals(userRef) && Arrays.equals(passAct, passRef)) {
                // Iniciar Sesión Trabajo
                JOptionPane.showMessageDialog(this, "Acceso PERMITIDO");

                // Finalizar Sesión Trabajo
                UtilesSwing.terminarPrograma(this);
            } else {
                // Acceso Denegado
                throw new Exception();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Acceso DENEGADO");
        }
    }

    // Inicio de Arrastre de Ventana
    public final void iniciarArrastre(MouseEvent e) {
        // Posición inicio arrastre
        xIni = e.getXOnScreen();
        yIni = e.getYOnScreen();
    }

    // Arrastre de Ventana
    public final void gestionarArrastre(MouseEvent e) {
        // Coordenada X
        int xFin = e.getXOnScreen();
        int xOff = xFin - xIni;
        xIni = xFin;

        // Coordenada Y
        int yFin = e.getYOnScreen();
        int yOff = yFin - yIni;
        yIni = yFin;

        // Posición de la ventana
        int xWin = getLocation().x;
        int yWin = getLocation().y;

        // Posiciona la ventana
        setLocation(xWin + xOff, yWin + yOff);
    }
}
