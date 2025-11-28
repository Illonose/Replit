package aplicacion;

import dominio.Alumno;
import excepcion.ErrorFicheroNotasException;
import excepcion.NotaInvalidaRuntimeException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestorNotas {

    public void guardarAlumnos(List<Alumno> alumnos, String nombreFichero) throws ErrorFicheroNotasException {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nombreFichero))) {
            for (Alumno a : alumnos) {
                pw.println(a.getNombre() + "," + a.getNota());
            }
        } catch (IOException e) {
            throw new ErrorFicheroNotasException("Error de I/O al guardar el fichero: " + nombreFichero, e);
        }
    }

    public List<Alumno> cargarAlumnos(String nombreFichero) throws ErrorFicheroNotasException {
        List<Alumno> alumnos = new ArrayList<>();
        String linea = null;
        boolean archivoVacio = true;

        try (BufferedReader br = new BufferedReader(new FileReader(nombreFichero))) {
            while ((linea = br.readLine()) != null) {
                archivoVacio = false;
                try {
                    String[] partes = linea.split(",");
                    if (partes.length < 2) {
                        throw new IllegalArgumentException("Línea sin separador de datos (',').");
                    }

                    String nombre = partes[0].trim();
                    double nota = Double.parseDouble(partes[1].trim());

                    if (nota < 0.0 || nota > 10.0) {
                        throw new NotaInvalidaRuntimeException("Nota fuera de rango (0-10): " + nota);
                    }

                    alumnos.add(new Alumno(nombre, nota));

                } catch (IllegalArgumentException | NotaInvalidaRuntimeException e) {
                    System.err.println("ERROR en línea: " + linea + ". Se ignora. Causa: " + e.getMessage());
                }
            }

            if (archivoVacio) {
                throw new ErrorFicheroNotasException("El fichero " + nombreFichero + " existe pero está vacío.");
            }

            return alumnos;

        } catch (IOException e) {
            throw new ErrorFicheroNotasException("Error de I/O al cargar el fichero: " + nombreFichero, e);
        }
    }

    public void eliminarFichero(String nombreFichero) throws ErrorFicheroNotasException {
        File fichero = new File(nombreFichero);
        if (fichero.exists() && !fichero.delete()) {
            throw new ErrorFicheroNotasException("No se pudo eliminar el fichero: " + nombreFichero);
        }
    }
}