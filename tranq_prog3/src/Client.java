/**
This class is the class of Client Form
@author Quang Tran
*/
import java.awt.Color;
import javax.swing.filechooser.*; 
import java.net.*;
import java.io.*;
public class Client extends javax.swing.JFrame {
    /**
     * Creates new form Client
     */
    public Client() {
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

        textAddress = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        fileChooser = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        outPut = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Connect and Upload");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("Server Address");

        fileChooser.setFileFilter(new FileNameExtensionFilter( "MSWord", "doc", "docx"));

        outPut.setEditable(false);
        outPut.setColumns(20);
        outPut.setRows(5);
        outPut.setSelectedTextColor(new java.awt.Color(255, 0, 51));
        jScrollPane1.setViewportView(outPut);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(fileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(35, 35, 35)
                        .addComponent(textAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(fileChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private Socket sock ;
    private OutputStream out;
    private InputStream in;
    private int CHUNK_SIZE = 1024;
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       try
       {
          byte buff[] = new byte[1024]; 
          outPut.setForeground(Color.red);
          int portNum = 5976;
          String hostAddress = textAddress.getText();
          outPut.append("Making socket connection to the Server" + "\n");
          sock = new Socket(hostAddress, portNum);
          out = sock.getOutputStream();
          in = sock.getInputStream();
          outPut.append ("Connected" + "\n");
          outPut.append("Send file name: " 
                         + fileChooser.getSelectedFile().getName() + "\n");
          sendNullTerminatedString(fileChooser.getSelectedFile().getName());
          outPut.append("Send file length: " 
                        + Long.toString(fileChooser.getSelectedFile().length())
                        + "\n");
          sendNullTerminatedString(Long.toString
                                  (fileChooser.getSelectedFile().length()));
          outPut.append("Sending file..." + "\n");
          sendFile(fileChooser.getSelectedFile().getPath());
          outPut.append("File sent Waiting for the Server..." + "\n");
          in.read(buff,0,1);
          if( buff[0] == '@')
          {
             outPut.append("Upload OK" + "\n");
          }
          else
          {
             outPut.append("Error" + "\n");
          }
          sock.close();
          out.close();
          in.close();
          outPut.append("Disconnected" + "\n");
       }
       catch (Exception ex)
       {
          outPut.append(ex.toString() + "\n");
       }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client().setVisible(true);
            }
        });
    }
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea outPut;
    private javax.swing.JTextField textAddress;
    // End of variables declaration//GEN-END:variables
   /**
   * This method takes a String s (either a file name or a file size,) as a
   * parameter, turns String s into a sequence of bytes ( byte[] ) by calling
   * getBytes() method, and sends the sequence of bytes to the Server. A null
   * character ‘\0’ is sent to the Server right after the byte sequence.
   */
   private void sendNullTerminatedString(String s)
   {
      try
      {
         out.write(s.getBytes());
         out.write('\0');
      }
      catch (Exception ex)
      {
         outPut.append(ex.toString() + '\n');
      }
   } 

   /**
   * This method takes a full-path file name, decomposes the file into smaller
   * chunks (each with 1024 bytes), and sends the chunks one by one to the
   * Server (loop until all bytes are sent.) A null character ‘\0’ is sent to
   * the Server right after the whole file is sent.
   */
   private void sendFile(String fullPathFileName)
   {
   //use the FileInputStream class to read a binary file
   //set up a loop to repeatedly read and write chunks
      try
      {
         int count;
         byte[] buffer = new byte[CHUNK_SIZE];
         File file = new File(fullPathFileName);
         FileInputStream in = new FileInputStream(file);
         while(( count = in.read(buffer)) > -1)
         {
            try
            {
               out.write(buffer, 0, count);
            }
            catch(Exception ex)
            {
               outPut.append(ex.toString() + "\n");
            }
        }
      }
      catch ( Exception ex)
      {
         outPut.append(ex.toString() + "\n");
      }
   } 


}