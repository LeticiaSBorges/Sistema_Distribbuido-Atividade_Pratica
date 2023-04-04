package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.sound.sampled.Line;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static String username;
    private static BufferedReader in;
    private static PrintWriter out;
    private static JFrame frame = new JFrame("Chat Room");
    private static JTextArea messageArea = new JTextArea(10, 40);
    private static JTextField textField = new JTextField(40);

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket(SERVER_ADDRESS, 1234);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String line = in.readLine();
            if (line.startsWith("Connected")) {
                username = getUsername();
                out.println(username);
             
            } else if (line.startsWith("Welcome")) {
                break;
            }
        }

        messageArea.setEditable(false);
        messageArea.append("Connected to the chat room.\n");
        JScrollPane scrollPane = new JScrollPane(messageArea);
        frame.add(scrollPane, BorderLayout.CENTER);
        
        // Botão para enviar mensagem
        /*JButton sendButton = new JButton("Enviar");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(textField.getText());   
                messageArea.append("Você: " + textField.getText() + "\n");
                textField.setText("");
            }
        });*/

     // Campo de entrada de texto para a mensagem
        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = textField.getText();
                if (message.equals("#QUIT")) {
                    out.println(message);
                    try {
                        out.close();
                        in.close();
                        socket.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    frame.dispose();
                    System.exit(0);
                    
                } else {
                    out.println(message);
                    messageArea.append("Você: " + message + "\n");
                    textField.setText("");
                }
            }
        });
        
        // Listener de fechamento da janela
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                try {
                    out.close();
                    in.close();
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });


        // Adicionar componentes à janela
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.add(textField);
        //panel.add(sendButton);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);

        // Definir tamanho e visibilidade da janela
        frame.pack();
        frame.setVisible(true);
        
        // Loop principal do cliente recebe msg do servidor
        while (true) {
            String line = in.readLine();
            if (line == null) {
                // Encerrar o loop caso a linha seja null
                break;
            }
            messageArea.append(line + "\n");
        }
        
        // Fechar o socket e a janela
        out.close();
        in.close();
        socket.close();
        frame.dispose();
    }

    private static String getUsername() {
        return JOptionPane.showInputDialog(
            frame,
            "Escolha um nome de usuário:",
            "Nome de usuário",
            JOptionPane.PLAIN_MESSAGE);
    }
}


