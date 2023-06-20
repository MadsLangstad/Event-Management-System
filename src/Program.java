import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {

    //# Fields
    public static Connection con;


    //# Getters & Setters
    public static Connection getCon() {
        return con;
    }

    public static void setCon(Connection con) {
        Program.con = con;
    }


    public static void main(String[] args) {

        Initializer initializer = new Initializer();

        try {
            FileReader fr = new FileReader("sql//CreateUniversityDB.sql");
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();

            String sql = "";

            while(line != null) {
                if(line.length() > 0) {
                    sql += line + "\n";
                    if(line.contains(";")) {
                        Statement stmt = con.createStatement();
                        int result = stmt.executeUpdate(sql);

                        sql = "";
                    }
                }
                line = br.readLine();
            }

        } catch(FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            FileReader fr = new FileReader("sql//CreateEventDB.sql");
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();

            String sql = "";

            while(line != null) {
                if(line.length() > 0) {
                    sql += line + "\n";
                    if(line.contains(";")) {
                        Statement stmt = con.createStatement();
                        int result = stmt.executeUpdate(sql);

                        sql = "";
                    }
                }
                line = br.readLine();
            }

        } catch(FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
