package aplicacion;

import dominio.Alumno;
import excepcion.ErrorFicheroNotasException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String FICHERO_PRUEBA = "notas_test.txt";
        GestorNotas gestor = new GestorNotas();

        List<Alumno> listaInicial = Arrays.asList(
            new Alumno("Juan", 7.5),
            new Alumno("Ana", 9.25),
            new Alumno("Roberto", 3.0),
            new Alumno("Carlos", 12.0) 
        );

        try {
            gestor.guardarAlumnos(listaInicial, FICHERO_PRUEBA);
            System.out.println("--- Datos iniciales escritos ---");

            try (PrintWriter pw = new PrintWriter(new FileWriter(FICHERO_PRUEBA, true))) {
                pw.println("Maria Lopez, nota_mala_aqui");
            }
        } catch (ErrorFicheroNotasException | IOException e) {
            System.err.println("Error crítico inicializando datos: " + e.getMessage());
            return;
        }

        try {
            System.out.println("\n--- Intentando cargar los alumnos ---");
            List<Alumno> listaCargada = gestor.cargarAlumnos(FICHERO_PRUEBA);
            
            System.out.println("\n--- Alumnos cargados correctamente: ---");
            listaCargada.forEach(System.out::println);
            
        } catch (ErrorFicheroNotasException e) {
            System.err.println("\n*** ERROR EN EL PROCESO DE CARGA ***");
            System.err.println("Causa principal: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("Detalles originales: " + e.getCause().getMessage());
            }
        }

        try {
            System.out.println("\n--- Intentando eliminar el fichero ---");
            gestor.eliminarFichero(FICHERO_PRUEBA);
            System.out.println("Fichero eliminado correctamente.");
        } catch (ErrorFicheroNotasException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
        }
    }
}
