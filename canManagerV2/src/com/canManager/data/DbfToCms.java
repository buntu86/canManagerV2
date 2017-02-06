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
        //http://www.fileformat.info/format/corion-dbase-iii.htm
        //http://www.dbase.com/Knowledgebase/INT/db7_file_fmt.htm        
        
        FileInputStream in = null;
        DataInputStream d = null;
        
        try {
            in = new FileInputStream(dbfFile.toFile());
            d = new DataInputStream(new FileInputStream(dbfFile.toFile()));
            //int c, tmp=0, i, j;
            
            /*DataInputStream d = new DataInputStream(new FileInputStream(dbfFile.toFile()));
            d.skip(10);*/

            //d.skip(10);
            in.skip(10);
            //byte arr1[] = {d.readByte(), d.readByte()};
            //byte arr[] = {(byte)in.read(),(byte)in.read()};
            
            System.out.println(d.readByte());
            System.out.println(d.readByte());
            System.out.println(d.readByte());
            System.out.println(d.readByte());
            
            System.out.println(d.readInt());
            System.out.println(d.readShort());
            System.out.println(d.readShort());
            

            
            //System.out.println("Length : " + arr1[0].intValue());
            
            /*DataInputStream in1 = new DataInputStream(in);
            byte aByte = in1.readByte();
            int anInt = in1.readInt();
            int anotherInt = in1.readInt();
            short andAShort = in1.readShort(); // 11 bytes read :-)
            byte[] lotOfBytes = new byte[anInt];
            in1.readFully(lotOfBytes);            
            */
            /*
            DataInputStream d = new DataInputStream(new FileInputStream(dbfFile.toFile()));
            d.readInt();
            */

            //int x = java.nio.ByteBuffer.wrap(bytes).getInt();
            
            //http://www.dbase.com/Knowledgebase/INT/db7_file_fmt.htm
            /*
            //0
            System.out.println("Type de DBase " + in.read());
            //1-3
            System.out.println("Date : " + (1900+in.read()) + "." + in.read() + "." + in.read());
            //4-7
            for (i=0; i<4; i++) {j=in.read(); tmp+=j; System.out.print(j + " ");};
            System.out.println("Nombre d'enregistrements : " + tmp);
           
            //8-9
            tmp=0;
            for (i=0; i<2; i++) {j=in.read(); tmp+=j; System.out.print(j + " ");};
            System.out.println("Nombre de byte de l'entete : " + tmp);            
            //10-11
            tmp=0;
            for (i=0; i<2; i++) {tmp+=in.read();};
            System.out.println("Nombre de byte des enregistrements : " + tmp);                        
            //12+13
            System.out.println(in.read());
            System.out.println(in.read());
            //14 flag
            System.out.println(in.read());
            //15
            System.out.println(in.read());
            //16-27
            tmp=0;
            for (i=0; i<12; i++) {j=in.read(); tmp+=j; System.out.print(j + " ");};
            System.out.println("Reserved : " + tmp);   
            //28
            System.out.println("Mdx file : " + tmp); 
            //29
            System.out.println("Language driver : " + tmp); 
            //30+31
            System.out.println(in.read());
            System.out.println(in.read());            
            //32-63
            tmp=0;
            for (i=0; i<32; i++) {j=in.read(); tmp+=j; System.out.print(j + " ");};
            System.out.println("Language driver name : " + tmp);     
            //64-67
            tmp=0;
            for (i=0; i<4; i++) {j=in.read(); tmp+=j; System.out.print(j + " ");};
            System.out.println(tmp); 

            tmp=0;
            for (i=0; i<48; i++) {j=in.read(); tmp+=j; System.out.print(j + " ");};
            System.out.println(tmp); 

            /*
            while((c = in.read()) != -1){
                System.out.println(c);
            }*/
            
        }catch(Exception e){
        }
        
        
        /*
        public class CopyBytes {
    public static void main(String[] args) throws IOException {

        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream("xanadu.txt");
            out = new FileOutputStream("outagain.txt");
            int c;

            while ((c = in.read()) != -1) {
                out.write(c);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
        */
        
        return true;
    }

    public static int byteArrayToInt(byte[] b) {
      if (b.length == 4)
        return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8
            | (b[3] & 0xff);
      else if (b.length == 2)
        return 0x00 << 24 | 0x00 << 16 | (b[0] & 0xff) << 8 | (b[1] & 0xff);

      return 0;
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
