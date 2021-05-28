
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author user
 */
public class Server extends javax.swing.JFrame {

    ArrayList clientOutputStreams = new ArrayList<>();//client outputs
    ArrayList<String> users = new ArrayList<>();//Users that connected server

    public class ClientHandler implements Runnable {

        Socket socket;
        BufferedReader reader;
        PrintWriter client;
        int id_privateChat;

        public ClientHandler(Socket clientSocket, PrintWriter user) {
            client = user;
            try {
                socket = clientSocket;
                InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
                reader = new BufferedReader(isReader);
            } catch (Exception ex) {
                msgBox.append("Unexpected error... \n");
            }

        }

        public void run() {
            // String message, connect = "Connect", disconnect = "Disconnect", chat = "Chat" , privatemsg = "private" ;
            String[] data;
            String message;

            try {
                while ((message = reader.readLine()) != null) {

                    msgBox.append("\nReceived: " + message + "\n");
                    data = message.split(":");
                    int counter2steps = 0;
                    for (String token : data) {
                        if (counter2steps != 2) {
                            msgBox.append(token + " ");
                        }
                        counter2steps++;
                    }

                    if (data[2].equals("Connect")) {
                        System.out.println("girdi");
                        tellEveryone((data[0] + ":" + data[1] + ":" + "Chat"));
                        userAdd(data[0]);
                    } else if (data[2].equals("Chat")) {
                        tellEveryone(message);
                    } else if (data[2].equals("request")) {

                        int recievedID;
                        StringBuilder stringBuilder = new StringBuilder();

                        for (String current_user : users) {
                          //  recievedID = users.indexOf(current_user);
                            stringBuilder.append(current_user).append(",");
        
                        }
                        recievedID = users.indexOf(data[0]);
                        String finalString = stringBuilder.toString();
                        finalString = data[0] + ":" + finalString + ":" + "request";
                        tellthispersononly(finalString, recievedID, data[0]); // d[0] here is the reciever person which is the in this case the sender itself 

                    } else {
                        msgBox.append("No Conditions were met. \n");
                    }
                }
            } catch (Exception ex) {
                msgBox.append("Lost the connection. \n");
                ex.printStackTrace();
                clientOutputStreams.remove(client);
            }
        }

    }

    /**
     * Creates new form Server
     */
    public Server() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msgBox = new javax.swing.JTextArea();
        btn_start = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 255, 255));

        msgBox.setColumns(20);
        msgBox.setRows(5);
        jScrollPane1.setViewportView(msgBox);

        btn_start.setText("Start");
        btn_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_startActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_start)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btn_start)
                .addContainerGap(65, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_startActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startActionPerformed
        // TODO add your handling code here:
        Thread starter = new Thread(new ServerStart());
        msgBox.append("Sever Started.");
        starter.start();
    }//GEN-LAST:event_btn_startActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Server().setVisible(true);
            }
        });
    }

    public class ServerStart implements Runnable {

        @Override
        public void run() {

            try {
                ServerSocket serverSocket = new ServerSocket(5000);
                while (true) {
                    Socket clientSock = serverSocket.accept();
                    PrintWriter writer = new PrintWriter(clientSock.getOutputStream());
                    clientOutputStreams.add(writer);

                    Thread listener = new Thread(new ClientHandler(clientSock, writer));
                    listener.start();
                }
            } catch (Exception ex) {
                msgBox.append("Connection Error! \n");
            }
        }
    }

    public void userAdd(String data) {
        String message;
        users.add(data);
        for (String user : users) {
            message = (user + ": :Connect");
            tellEveryone(message);
        }

    }

    public void userRemove(String data) {
        String message;
        users.remove(data);
        for (String UserName : users) {
            message = (UserName + ": :Disconnect");
            tellEveryone(message);
        }
    }

    public void tellEveryone(String message) {
        Iterator it = clientOutputStreams.iterator();

        while (it.hasNext()) {
            try {
                PrintWriter writer = (PrintWriter) it.next();
                writer.println(message);
                writer.flush();

            } catch (Exception ex) {
                msgBox.append("Error telling everyone. \n");
            }
        }
    }

    public void tellthispersononly(String msg, int personid, String recievername) {

            if (clientOutputStreams.get(personid) != null) {

                try {
                    PrintWriter writer = (PrintWriter) clientOutputStreams.get(personid); 
                    writer.println(msg);
                    writer.flush();
                    msgBox.append("Sending to {" + recievername + "} only this msg :  " + msg + "\n");
                    msgBox.setCaretPosition(msgBox.getDocument().getLength());
                } catch (Exception ex) {
                    msgBox.append("Error in telling this " + recievername + "." + "\n");
                }
            } else {
                msgBox.append("Error in telling this ... his ID not found OR His outputstream is null " + recievername + "." + "\n");
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_start;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea msgBox;
    // End of variables declaration//GEN-END:variables
}
