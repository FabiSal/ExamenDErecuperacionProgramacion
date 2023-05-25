/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenrecuperacion;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author fabain salinas
 */
public class EXAMENrecuperacion {

    public static Scanner cs = new Scanner(System.in);
//un simple if para comprobar si funciona o no--------------------------
    public static void comprueba(int resultado) throws Exception {
        if (resultado == 1) {
            System.out.println("Hecho");
        } else {
            throw new Exception("Error en el proceso");
        }
    }

    public static void comprueba(boolean resultado) throws Exception {
        if (!resultado) {
            System.out.println("Hecho");
        } else {
            throw new Exception("Error en el proceso");
        }
    }

    public static void main(String[] args) {

        try {
//----------------------------------conection-------------------------------------------------------------
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://10.230.109.71:3306/mysql?serverTimezone=UTC";
            Connection conn = DriverManager.getConnection(url, "root", "");
            Statement stmt = conn.createStatement();

            int nr;
            boolean ex = true;    
//-----------------------entrar a la base de datos------------
            ex = stmt.execute("USE Autoras;");
            System.out.println("USE Autoras");
            comprueba(ex);
//------------------------Guarda la consulta que realices en un TXT-------------------------------   
             //al principio pregunta al usuario como quiere llamar a su carpeta en donde se guardaran los datos
              //la carpeta creada esta junto al rsc y build , dentro de la carpeta java.                          
            String ruta = "Resultados";
            File resultado = new File(ruta);
            if (!resultado.exists()) {
                resultado.mkdir();
            }
            System.out.println("introduce el nombre de la carpeta donde se guardara tu consulta.");
            //pregunta al user el nombre de como quiere que se llame la carpeta donde se guarda el resultado
            String nombre = cs.nextLine();
            FileWriter writer = new FileWriter(ruta + File.separator + nombre + ".txt", false);
//------------------------------------------------------------------------------------------------
            int opcion;
            System.out.println("1 mostrar datos de una autora");
            System.out.println("2 mostrar la autora con mas premios");
            System.out.println("3 mostrar el numero de autoras por pais de residencia");
            System.out.println("4 mostrar numero de autoras por campo de trabajo");
            System.out.println("5 añadir una autora");
            System.out.println("6 para salir");
            opcion = cs.nextInt();

//do while--------------------------------------------------
                            //el while next dentro de cada caso, hace un bucle infinito, guarda todo , muestra la consulta , pero no se como repararlo
                           //en el caso 5 añade a la bbdd una autora con los parametros introducidos por el usuario, aveces da error pero aun asi introduce el autor al bbdd.
                           //dentro de la carpeta Resultado he eliminado los txt ya que he genereado muchas pruebas. aun que no bucle infinito se que me maldice el code.
                           //ahi dentro se guardan las consultas. 
            do {
                switch (opcion) {
                    case 1:                                                                                                                         
                        ResultSet autora = stmt.executeQuery("SELECT * FROM Autoras WHERE nombre= 'Ana';");//consulta
                        while (autora.next()) {
                            System.out.println(""
                                    + "id: " + autora.getInt("id")
                                    + ", nombre: " + autora.getString("nombre")
                                    + ", alias: " + autora.getString("alias")
                                    + ", fecha de nacimiento: " + autora.getDate("fecha_nacimiento")
                                    + ", premios recibidos: " + autora.getInt("premios_recibidos")
                                    + ", país de residencia: " + autora.getString("pais_residencia")
                                    + ", área de trabajo: " + autora.getString("area_trabajo"));
// Guardar la consulta en un archivo de texto-------------------------------------
                            writer.write(""
                                    + "id: " + autora.getInt("id")
                                    + ", nombre: " + autora.getString("nombre")
                                    + ", alias: " + autora.getString("alias")
                                    + ", fecha de nacimiento: " + autora.getDate("fecha_nacimiento")
                                    + ", premios recibidos: " + autora.getInt("premios_recibidos")
                                    + ", país de residencia: " + autora.getString("pais_residencia")
                                    + ", área de trabajo: " + autora.getString("area_trabajo"));
                            
                            System.out.println("");
                        }
                        break;
                    case 2:
                        ResultSet premios = stmt.executeQuery("SELECT nombre ,premios_recibidos FROM Autoras WHERE premios_recibidos=8 ;");//consulta
                        while (premios.next()) {
                            System.out.println(
                                    "nombre ," + premios.getString("nombre")
                                    + "Premios recibidos ," + premios.getString("premios_recibidos"));

//guarda en un txt--------------------
                            writer.write(
                                    "nombre ," + premios.getString("nombre")
                                    + "Premios recibidos ," + premios.getString("premios_recibidos"));
                            break;
                        }
                        break;
                    case 3:
                        ResultSet pais = stmt.executeQuery("SELECT count(*),pais_residencia  FROM Autoras group by pais_residencia ;");//consulta
                        while (pais.next()) {
                            System.out.println(
                                    "id ," + pais.getInt("id")
                                    + "nombre ," + pais.getString("nombre")
                                    + "alias ," + pais.getString("alias")
                                    + "fecha nacimiento ," + pais.getDate("fecha_nacimiento")
                                    + "premios recibidos ," + pais.getInt("premios_recibidos")
                                    + "pais residencia ," + pais.getString("pais_residencia")
                                    + "area de trabajo ," + pais.getString("area_trabajo"));
//guarda en el txt---------------------------
                            writer.write(
                                    "id ," + pais.getInt("id")
                                    + "nombre ," + pais.getString("nombre")
                                    + "alias ," + pais.getString("alias")
                                    + "fecha nacimiento ," + pais.getDate("fecha_nacimiento")
                                    + "premios recibidos ," + pais.getInt("premios_recibidos")
                                    + "pais residencia ," + pais.getString("pais_residencia")
                                    + "area de trabajo ," + pais.getString("area_trabajo"));
                            
                            System.out.println("");
                        }
                        break;
                    case 4:
                        ResultSet trabajo = stmt.executeQuery(" SELECT count(*), area_trabajo FROM Autoras group by area_trabajo ");//consulta
                        while (trabajo.next()) {
                            System.out.println(
                                    "id ," + trabajo.getInt("id")
                                    + "nombre ," + trabajo.getString("nombre")
                                    + "alias ," + trabajo.getString("alias")
                                    + "fecha nacimiento ," + trabajo.getDate("fecha_nacimiento")
                                    + "premios recibidos ," + trabajo.getInt("premios_recibidos")
                                    + "pais residencia ," + trabajo.getString("pais_residencia")
                                    + "area de trabajo ," + trabajo.getString("area_trabajo"));
//guarda en el txt--------------------------
                            writer.write("id ," + trabajo.getInt("id")
                                    + "nombre ," + trabajo.getString("nombre")
                                    + "alias ," + trabajo.getString("alias")
                                    + "fecha nacimiento ," + trabajo.getDate("fecha_nacimiento")
                                    + "premios recibidos ," + trabajo.getInt("premios_recibidos")
                                    + "pais residencia ," + trabajo.getString("pais_residencia")
                                    + "area de trabajo ," + trabajo.getString("area_trabajo"));
                            
                            System.out.println("");
                        }
                        break;
                    case 5:               
                        System.out.println("introduce id");
                        int id = cs.nextInt();
                        System.out.println("introduce nombre");
                        String nombreAutora = cs.nextLine();
                        System.out.println("introduce alias");
                        String alias = cs.nextLine();
                        System.out.println("introduce fecha de nacimiento");
                        String fecha = cs.nextLine();
                        System.out.println("introduce premio");
                        int premio = cs.nextInt();
                        System.out.println("introduce pais");
                        String paisAutora = cs.nextLine();
                        System.out.println("introduce area de trabajo");
                        String areaTrabajo = cs.nextLine();

                        ex = stmt.execute("insert into Autoras(id,nombre,alias,fecha_nacimiento,premios_recibidos,pais_residencia,area_trabajo"
                                + "value(" + id + "," + nombreAutora + "," + alias + "," + fecha + "," + premio + "," + paisAutora + "," + areaTrabajo + ")");//insert
                        
                        break;
                }
            } while (opcion != 6);
//cerrar el stmt el conection y el write--------------------------

            writer.close();
            stmt.close();
            conn.close();
//si algo va mal lanza excepcion------------------            
        } catch (Exception e) {
            System.out.println("error");
            e.printStackTrace();
        }
    }
}
