/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.server;

/**
 *
 * @author letic
 */
import java.net.*;

public class Server {

    public static void main(String[] args) throws Exception { //recebe um array de strings como argumento e pode lançar uma exceção
        DatagramSocket serverSocket = new DatagramSocket(9876); //cria-se um DatagramSocket na porta 9876 para receber os pacotes do cliente
        
        //criação dos arrays de bytes receiveData e sendData, 
        //que serão usados para armazenar os dados recebidos do cliente e 
        //para enviar a resposta ao cliente, respectivamente
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        //loop infinito que recebe os dados do cliente e enviará o resultado de volta
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); // criação do DatagramPacket para receber dados do cliente
            serverSocket.receive(receivePacket); //o servidor aguarda até receber um pacote do cliente;

            String equation = new String(receivePacket.getData()); // converte o array de bytes recebido em uma string
            String[] parts = equation.split(" "); //a string é dividida em um array de strings usando o espaço como separador

            float numero1 = Float.parseFloat(parts[0]); //conversão da primeira string em um float que representa o primeiro número da operação
            float numero2 = Float.parseFloat(parts[2]); //conversão da terceira string em um float que representa o segundo número da operação.
            char operador = parts[1].charAt(0); //extrai o operador da segunda string e armazena-se em um char
            float result = 0;

            // criação de uma estrutura para selecionar uma das operações ('+', '-', '*' e '/') e 
            // realizar as opreções matemátiacas de acordo com o num1 e num2 declarado pelo cliente
            switch (operador) {
                case '+':
                    result = numero1 + numero2;
                    break;
                case '-':
                    result = numero1 - numero2;
                    break;
                case '*':
                    result = numero1 * numero2;
                    break;
                case '/':
                    result = numero1 / numero2;
                    break;
            }
            // recebe o endereço de IP e a porta do datagrama recebido pelo servidor, 
            // sendo usada para enviar as resposta para o cliente
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            
            // armazena o resutado da operação em uma string
            String resultString = "O resultado da operação " + equation.trim() + " = " + result;
            sendData = resultString.getBytes(); // converte a string para um array de bytes para voltar para o cliente
            
            // criação de um  novo datagrama(com IP e a porta do cliente, e o array de bytes com a resposta ) 
            // para enviar de volta para cliente com a resposta
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
            
            // imprime no console do servidor
            System.out.println(resultString);
            
            //redefine o array de bytes para receber novos calculos.
            receiveData = new byte[1024];
        }
    }
}
