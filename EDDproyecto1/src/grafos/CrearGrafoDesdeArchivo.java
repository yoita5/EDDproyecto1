
package grafos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

class Nodo {
    String nombre;
    Nodo siguiente;

    public Nodo(String nombre) {
        this.nombre = nombre;
        siguiente = null;
    }
}

class Grafo {
    Nodo[] nodos;
    int cantidadNodos;

    public Grafo(int cantidadNodos) {
        this.cantidadNodos = cantidadNodos;
        nodos = new Nodo[cantidadNodos];

        for (int i = 0; i < cantidadNodos; i++) {
            nodos[i] = null;
        }
    }

    public void agregarArista(int nodo1, int nodo2) {
        Nodo nuevoNodo = new Nodo(String.valueOf(nodo2));

        if (nodos[nodo1] == null) {
            nodos[nodo1] = nuevoNodo;
        } else {
            Nodo actual = nodos[nodo1];
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevoNodo;
        }
    }

    public void mostrarGrafo() {
        for (int i = 0; i < cantidadNodos; i++) {
            Nodo actual = nodos[i];
            System.out.print("Nodo " + i + ": ");
            while (actual != null) {
                System.out.print(actual.nombre + " ");
                actual = actual.siguiente;
            }
            System.out.println();
        }
    }
}

public class CrearGrafoDesdeArchivo {

    public static void main(String[] args) {
        JFrame frame = new JFrame(" Grafo ");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Seleccionar archivo");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos de texto", "txt");
                fileChooser.setFileFilter(filter);

                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    String rutaArchivo = fileChooser.getSelectedFile().getPath();
                    Grafo grafo = crearGrafoDesdeArchivo(rutaArchivo);

                    if (grafo != null) {
                        grafo.mostrarGrafo();
                    } else {
                        System.out.println("No se pudo crear el grafo.");
                    }
                }
            }
        });

        frame.getContentPane().add(button, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public static Grafo crearGrafoDesdeArchivo(String rutaArchivo) {
        Grafo grafo = null;

        try {
            java.io.File archivo = new java.io.File(rutaArchivo);
            java.util.Scanner scanner = new java.util.Scanner(archivo);

            int cantidadNodos = Integer.parseInt(scanner.nextLine());
            grafo = new Grafo(cantidadNodos);

            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] partes = linea.split(" ");
                int nodo1 = Integer.parseInt(partes[0]);
                int nodo2 = Integer.parseInt(partes[1]);
                grafo.agregarArista(nodo1, nodo2);
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return grafo;
    }
}