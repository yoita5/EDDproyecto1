
package grafos;
import grafos.Csv;
import grafos.GraficaGrafos;
import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AddRelationsWindow extends javax.swing.JFrame {
    Csv usersCsv;

    public AddRelationsWindow() {
        initComponents();
    }

    private void initComponents() { 

        jLabel1 = new javax.swing.JLabel(); // Creamos el boton 
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Ahora debe añadir el archivo con la data de las relaciones entre los usuarios");

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
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(39, 39, 39))
            .addGroup(layout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed  // Creamos el boton y cuando le demos click corre lo de abajo
        Csv usersRelationsCsv = new Csv(); //creamos objeto de la clase
        usersRelationsCsv.usersArr = this.usersCsv.usersArr; // a;adimos el array que hacemos 
        usersRelationsCsv.FileSelection("users_relations");//  convertimos a texto en java y 
            
        if(usersRelationsCsv.status.equals("wrong")) { ///Estatus verifica que todo este correcto
            final JPanel panel = new JPanel();
            JOptionPane.showMessageDialog(panel, usersRelationsCsv.message, "Warning",JOptionPane.WARNING_MESSAGE);
            return;
        }
        if(usersRelationsCsv.status.equals("success")) {
            GraficaGrafos grafica = new GraficaGrafos(this.usersCsv, usersRelationsCsv);// creamos un nuevo Graficagrafos grafica , le pasamos parametros usuario,relaciones,id
            grafica.getContentPane().setBackground(Color.decode("#202020"));
            setVisible(false);
            grafica.setVisible(true); // GRAFICAMOS CON LA DATA USERSRelationsCsv
        }
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AddRelationsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddRelationsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddRelationsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddRelationsWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddRelationsWindow().setVisible(true);
            }
        });
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
}
