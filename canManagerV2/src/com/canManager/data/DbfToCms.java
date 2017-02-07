package com.canManager.data;

import com.canManager.utils.Log;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Alert;

public class DbfToCms {
    
    private static Path dbfFile, cmsFile;
    private static Connection connection = null; 

    public static boolean convert(String str){
        dbfFile = Paths.get(str);
        cmsFile = Paths.get(dbfFile.toString().substring(0, dbfFile.toString().lastIndexOf('.')) + ".cms");

        checkFiles();
        addArticles();
        
        /*if(checkFiles())
            if(connectSql())
                if(createTable())
                    return true;*/
        return false;
    }   

    private static boolean checkFiles() {
        if(Files.exists(dbfFile))
        {
            Log.msg(0, "Le fichier dbf existe.");

            if(!Files.exists(cmsFile))
            {
                Log.msg(0, "Le fichier cms n'existe pas.");

                return true;
            }
            else
            {
                Log.msg(1, "Le fichier cms existe déjà.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Convertir");
                alert.setHeaderText(null);
                alert.setContentText("Le fichier \"" + cmsFile.toString() + "\" existe déjà.");
                alert.showAndWait();
                return false;
            }
        }
        else
        {
            Log.msg(1, "Le fichier dbf n'existe pas.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Convertir");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier \"" + dbfFile.toString() + "\" n'existe pas.");
            alert.showAndWait();
            return false;
        }
    }

    private static boolean connectSql() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + cmsFile);
            Log.msg(0, "Création et connection au fichier : " + cmsFile.toString());
            return true;
        } catch (SQLException e) {
            Log.msg(1, "Erreur sql : " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Convertir");
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage() + "\nErreur de type SQL.\nVeuillez choisir un autre nom de fichier et réessayer.");
            alert.showAndWait();
            return false;
        }
    }
    
    private static boolean createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS 'CAN' (\n"
            + "`ID` INTEGER PRIMARY KEY AUTOINCREMENT,\n"
            + "`position`	INTEGER,\n"
            + "`sousPosition`	INTEGER,\n"
            + "`variable`	INTEGER,\n"
            + "`ligne`	INTEGER,\n"
            + "`alt`	TEXT,\n"
            + "`unite`	TEXT,\n"
            + "`publication`	INTEGER,\n"
            + "`debut`	INTEGER,\n"
            + "`text`	TEXT\n"
            + ");";
        try (Statement stmt  = connection.createStatement();) {
            stmt.execute(sql);
            Log.msg(0, "Création de la table CAN.");
            return true;

            } catch (SQLException e) {
                Log.msg(1, "Erreur sql : " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Convertir");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage() + "\nErreur de type SQL.\nVeuillez choisir un autre nom de fichier et réessayer.");
                alert.showAndWait();
                return false;
        }   
    }
    
    private static boolean addArticles(){
        //http://www.dbase.com/Knowledgebase/INT/db7_file_fmt.htm        
        //http://www.oocities.org/geoff_wass/dBASE/GaryWhite/dBASE/FAQ/qformt.htm#Ac
        // http://sebastienguillon.com/test/javascript/convertisseur.html
        //lsb

        FileInputStream in = null;
        DataInputStream d = null;
        
        try {
            
            byte[] data = Files.readAllBytes(dbfFile);
            
            System.out.println(data[8]);
            System.out.println(data[9]);

            byte firstByte = data[8];
            byte secondByte = data[9];
            int result = (secondByte << 8) + firstByte;
            
            System.out.println("" + result);
            
            
            /*System.out.println(data[6]);
            System.out.println(data[7]);
            
            byte firstByte = array[0];
            byte secondByte = array[1];
            int result = (secondByte << 8) + firstByte;
            37 et 15 (base 10) = 0010 0101 et 0000 1111 (binary) = 9488 (base 10)
            0010 0101 0000 1111 -> 9487
            */
            

            /*for(int w=318; w<350; w++)
                System.out.println(w + " | " + (char)data[w]);
            

            //7351
            
*/

            
        }catch(Exception e){
        }
        
        return true;
    }


    
/*
    private void addArticlesOnTable() {
        Charset stringCharset = Charset.forName("IBM437");

        try {
            BufferedReader in = Files.newBufferedReader(crbFilePath, stringCharset);
            String line1=null;
            String line2=null;                       
            int nbrChar=78;

            line1=in.readLine();
            line2=in.readLine();

            List<String> liste = java.util.Arrays.asList(line2.split("(?<=\\G.{"+nbrChar+"})"));    

            Iterator<String> iterator = liste.iterator(); 

            try {
                Statement stat = conn.createStatement();
                stat.executeUpdate("BEGIN;");

                while (iterator.hasNext()) {
                    String nodes = iterator.next();
                    if(nodes.length()==78)
                    {
                        System.out.println(nodes.toString());
                        String sqlInsertInto = ("INSERT INTO CAN (position,subPosition,variable,line,alt,unit,publication,begin,text) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
                        PreparedStatement stmtPrepared = conn.prepareStatement(sqlInsertInto);                
                        String position = new String(nodes.toCharArray(), 1, 3);
                        String subPosition = new String(nodes.toCharArray(), 4, 3);
                        String variable = new String(nodes.toCharArray(), 7, 2);
                        String line = new String(nodes.toCharArray(), 9, 2);
                        String alt = new String(nodes.toCharArray(), 11, 1);
                        String unit = new String(nodes.toCharArray(), 12, 2);
                        String publication = new String(nodes.toCharArray(), 14, 2);
                        String begin = new String(nodes.toCharArray(), 16, 2);
                        String text = new String(nodes.toCharArray(), 18, 60);                        

                        stmtPrepared.setString(1, position);
                        stmtPrepared.setString(2, subPosition);
                        stmtPrepared.setString(3, variable);
                        stmtPrepared.setString(4, line);
                        stmtPrepared.setString(5, alt);
                        stmtPrepared.setString(6, unit);
                        stmtPrepared.setString(7, publication);
                        stmtPrepared.setString(8, begin);
                        stmtPrepared.setString(9, text.trim());

                        stmtPrepared.executeUpdate();                
                    }
                }
                stat.executeUpdate("COMMIT;");
                System.out.println("[ V ] Table CAN is populated");
       
                dialogStage.close();
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Convertir");
                alert.setHeaderText(null);
                alert.setContentText("Le fichier a été converti avec succès.");
                alert.showAndWait();                
            } catch(SQLException e){
                System.out.println("[ X ] " + e.getMessage());
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Convertir");
                alert.setHeaderText(null);
                alert.setContentText(e.getMessage() + "\nErreur de type SQL.\nVeuillez choisir un autre nom de fichier et réessayer.");
                alert.showAndWait();
            }
        } catch(IOException e){
            System.out.println("[ X ] " + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Convertir");
            alert.setHeaderText(null);
            alert.setContentText(crbFilePath.toString() + "\nErreur lors de l'ouverture du fichier\nVeuillez choisir un autre nom de fichier et réessayer.\nErreur : " + e.getMessage());
            alert.showAndWait();
        }
    }    
    */
}
