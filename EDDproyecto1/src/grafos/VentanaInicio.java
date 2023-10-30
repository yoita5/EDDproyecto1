
package grafos;
import java.util.*;
import grafos.AddRelationsWindow;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
//Aqui creamos la interfaz que va a ver el usuario, con los botones 
public class VentanaInicio extends javax.swing.JFrame {
    Object usersList;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    
    public VentanaInicio() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("App de Analisis de Redes Sociales" );

        jLabel2.setText("Para empezar con el analisis, primero debe cargar el archivo con la lista de usuarios");
            
            //Estar pendiente cuando se vaya a presionar el boton( Se ejecuta una funcion y esa ejecuta a la vez )
        jButton1.setText("Cargar Archivo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(243, 243, 243)
                .addComponent(jButton1)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(199, 199, 199))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(58, 58, 58))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Csv csv = new Csv();
        csv.FileSelection("users");
            // cuando llamamos a este metodo llamamos a los usuarios 
        if(csv.status == "wrong") {
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, csv.message, "Warning",JOptionPane.WARNING_MESSAGE);
            return; //Verificamos si hubo error, si lo hay manda mensaje
        }
        if(csv.status == "success") {
            AddRelationsWindow window2 = new AddRelationsWindow(); //si no hay error ejecutaamos una nueva ventana y le pasamos la data con el array de usuario csv.
            window2.usersCsv = csv;
            setVisible(false);// hacemos que la ventana no sea visible 
            window2.setVisible(true);// hacemos que esta si sea visible
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) { //Genero netbeans 
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaInicio().setVisible(true);
            }
        }); // Hace visible
    }


    
}
