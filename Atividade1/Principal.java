/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


/**
 *
 * @author letic
 */
public class Principal extends javax.swing.JFrame {
    private DatagramSocket clientSocket;
    private InetAddress IPAddress;
    private int port;

    /**
     * Creates new form Principal
     */
    public Principal() {
        initComponents();
        iniciaCliente();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        resultado = new javax.swing.JTextField();
        entrada = new javax.swing.JTextField();
        calcular = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Calculadora");
        jLabel1.setName("Calculadora"); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 6, 388, 25));

        resultado.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        resultado.setToolTipText("");
        resultado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultadoActionPerformed(evt);
            }
        });
        getContentPane().add(resultado, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 380, 40));

        entrada.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        entrada.setToolTipText("");
        entrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entradaActionPerformed(evt);
            }
        });
        getContentPane().add(entrada, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 50, 290, 40));

        calcular.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        calcular.setText("Calcular");
        calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularActionPerformed(evt);
            }
        });
        getContentPane().add(calcular, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 100, 30));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Entrada:");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, 40));

        setSize(new java.awt.Dimension(413, 262));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

   
    ////////////////////// Chama objeto enviamensagem
    private void calcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularActionPerformed
       String equation = entrada.getText();
       enviamensagem(equation);                                           
    
    }//GEN-LAST:event_calcularActionPerformed

    private void entradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_entradaActionPerformed

    private void resultadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resultadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resultadoActionPerformed

    
    public static void main(String args[]) {
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    private void iniciaCliente(){
        try {
            clientSocket = new DatagramSocket();
            IPAddress = InetAddress.getLocalHost();
            port = 9876;
            System.out.println("Conectado ao servidor!");
        } catch (IOException ex) {
            System.out.println("Erro ao conectar ao servidor!");
        }
    }
    
    private void enviamensagem (String equation){
        try {
            //criação de matriz que contém os bytes da variáel equation utilizada para enviar dados por conexeão de rede (servidor)
            byte[] sendData = equation.getBytes();
            // cria o objeto sendPacket que envia a matriz sendData para o endereço IP e porta especificado
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            //envia o objeto 'sendPacket' por meio de uma conexão de soquete (clientSocket) que já foi aberto para facilitar a comunicação com um servidor remoto
            clientSocket.send(sendPacket);

            // criação de array de byte com comprimento fixo em 1024 bytes, usado para receber dados do servidor
            byte[] receiveData = new byte[1024];
            
            //cria o objeto chamado receivePacket, que é usado para receber dados do servidor
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            //recebe dados do servidor usando o clientSocketobjeto e os armazena no objeto 'receivePacket'
            clientSocket.receive(receivePacket);
            
            //conversão dos dados recebidos do servidor (armazenados no objeto 'receivePacket'), em uma string chamada result.
            String result = new String(receivePacket.getData());
            // remove os espaços em branco no inicio e/ou no fim
            resultado.setText(result.trim());
            
            //encerrando a conexão de rede
            //clientSocket.close();
        } catch (IOException ex) {
            System.out.println("Erro ao enviar mensagem!");
        }
    }

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calcular;
    private javax.swing.JTextField entrada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField resultado;
    // End of variables declaration//GEN-END:variables
}
