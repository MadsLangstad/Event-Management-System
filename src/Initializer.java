import models.Event;
import models.StudyProgram;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Initializer {
    private static final Database db = new Database("root", "Vestby04121994");
    private static final Connection con = db.getConnection();
    private static final Event e = new Event(con);
    private static final Scanner scanner = new Scanner(System.in);


    //# Constructor
    public Initializer() {}


    //# Methods
    public static void main(String[] args) throws SQLException {
        Program program = new Program();
        program.con = con;
        program.main(args);

        boolean running = true;

        while (running) {
            System.out.println("Welcome! Here are your options:");
            System.out.println("1. Sign in");
            System.out.println("2. See overall program");
            System.out.println("3. Exit");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    login();
                    break;
                case "2":
                    seeOverallProgram();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please select a number from 1 to 3.");
            }
        }
    }


    //# Need Fix
    public static List<StudyProgram> seeOverallProgram() throws SQLException {
        List<StudyProgram> programs = new ArrayList<>();
        if (doesProgramExist()) {

            String sqlUseUniversity = "USE universityDB;";
            PreparedStatement useStmt = con.prepareStatement(sqlUseUniversity);
            useStmt.executeUpdate();

            String sqlGetProgramInfo = "SELECT * FROM studyPrograms;";
            PreparedStatement stmt = con.prepareStatement(sqlGetProgramInfo);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                StudyProgram studyProgram = new StudyProgram();
                studyProgram.setName(rs.getString("name"));
                studyProgram.setProgramResponsible(rs.getString("programResponsible"));
                studyProgram.setDescription(rs.getString("description"));
                programs.add(studyProgram); // Adding the created studyProgram to the list
            }

            return programs; // Return the populated programs list after the loop

        } else {
            System.out.println("Program does not exist in the database.");
        }
        return programs;
    }


    public static boolean doesProgramExist() {
        try {

            String sqlUse = "USE universityDB;";
            PreparedStatement useStmt = con.prepareStatement(sqlUse);
            int res = useStmt.executeUpdate();

            String sqlGetStudyPrograms = "SELECT * FROM studyPrograms;";
            PreparedStatement stmt = con.prepareStatement(sqlGetStudyPrograms);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Program exists

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // Program does not exist
    }


    public static boolean doesUserExist(String userName) {
        try {
            String use = "USE universityDB;";
            String sql = "SELECT id FROM students WHERE name = ? LIMIT 1;";
            PreparedStatement useStmt = con.prepareStatement(use);
            int res = useStmt.executeUpdate();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, userName);
            ResultSet rs = stmt.executeQuery();

            boolean userFound = false;

            while(rs.next()) {
                int studentId = Integer.parseInt(rs.getString("id")); // https://www.geeksforgeeks.org/why-is-scanner-skipping-nextline-after-use-of-other-next-functions/
                e.setStudentId(studentId);
                userFound = true;
            }
            return userFound; // User exists

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false; // User does not exist
    }


    public static void seeAllParticipants() {
        try {
            String sqlUseUniversityStmt = "USE universityDB;";
            PreparedStatement useStmt = con.prepareStatement(sqlUseUniversityStmt);
            useStmt.executeUpdate();

            String sqlGetStudents = "SELECT * FROM students;";
            PreparedStatement stmt = con.prepareStatement(sqlGetStudents);
            ResultSet rs = stmt.executeQuery();

            String sqlGetTeachers = "SELECT * FROM teachers;";
            PreparedStatement stmt2 = con.prepareStatement(sqlGetTeachers);
            ResultSet rs1 = stmt2.executeQuery();

            String sqlUseEventStmt = "USE eventDB;";
            PreparedStatement stmt3 = con.prepareStatement(sqlUseEventStmt);
            stmt3.executeUpdate();

            String sqlGetGuests = "SELECT * FROM guests;";
            PreparedStatement stmt4 = con.prepareStatement(sqlGetGuests);
            ResultSet rs2 = stmt4.executeQuery();

            System.out.println("Name Of Participants: " + "\n");

            System.out.println("\n" + "Students: " + "\n");
            while (rs.next()) {
                String resultName = rs.getString("name");
                System.out.println(resultName);
            }

            System.out.println("\n" + "Teachers: " + "\n");
            while (rs1.next()) {
                String resultName = rs1.getString("name");
                System.out.println(resultName);
            }

            System.out.println("\n" + "Guests: " + "\n");
            while (rs2.next()) {
                String resultName = rs2.getString("name");
                System.out.println(resultName);
            }

            rs.close();
            rs1.close();
            rs2.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public static void seeParticipantsFromProgram() {
        System.out.println("Which Program Do You Wish To See Participants From? \n");
        System.out.println("1. Programming");
        System.out.println("2. Interaction Design");
        System.out.println("3. Cyber Security");
        System.out.println("4. Frontend & Mobile Development");

        String sql = "";

        int whichProgram = Integer.parseInt(scanner.nextLine());

        switch (whichProgram) {
            case 1:
                sql = "SELECT name FROM students WHERE studyProgramId LIKE 1;";
                break;
            case 2:
                sql = "SELECT name FROM students WHERE studyProgramId LIKE 2;";
                break;
            case 3:
                sql = "SELECT name FROM students WHERE studyProgramId LIKE 3;";
                break;
            case 4:
                sql = "SELECT name FROM students WHERE studyProgramId LIKE 4;";
                break;
            default:
                System.out.println("Invalid choice. Please select a number from 1 to 4.");
        }

        try {

            String sqlUseStmt = "USE universityDB;";
            PreparedStatement useStmt = con.prepareStatement(sqlUseStmt);
            int res = useStmt.executeUpdate();

            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                String resultName = rs.getString("name");
                System.out.println(resultName);
            }
            rs.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }


    public static void searchForParticipants() {
        try {
            System.out.println("What Type Of Participant (student/teacher/guest): ");
            String choice = scanner.nextLine();

            String use = "USE universityDB;";
            String sql = "";
            String name = "";

            switch (choice) {
                case "student":
                    System.out.println("What Is The Name Of The Student? ");
                    name = scanner.nextLine();
                    sql = "SELECT COUNT(*) AS num_rows FROM students where name = ? LIMIT 1;";
                    break;
                case "teacher":
                    System.out.println("What Is The Name Of The Teacher? ");
                    name = scanner.nextLine();
                    sql = "SELECT COUNT(*) AS num_rows FROM teachers where name = ? LIMIT 1;";
                    break;
                case "guest":
                    System.out.println("What Is The Name Of The Guest? ");
                    name = scanner.nextLine();
                    use = "USE eventDB;";
                    sql = "SELECT COUNT(*) AS num_rows FROM guests where name = ? LIMIT 1;";
                    break;
                default:
                    System.out.println("Invalid choice. Please select group students, teachers or guests.");
            }

            PreparedStatement useStmt = con.prepareStatement(use);
            int res = useStmt.executeUpdate();

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int resultNumRows = rs.getInt("num_rows");
                if(resultNumRows == 0){
                    System.out.println("Found no participant with that name.");
                } else {
                    System.out.println("We Found: " + name);
                }
            }

            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void login() throws SQLException {
        boolean running = true;

        System.out.println("Please type your username: ");
        String userName = scanner.nextLine();

        if (doesUserExist(userName)) {
            while(running) {
                System.out.println("\n" + "Welcome " + userName + "! Here Are your Options!" + "\n");

                System.out.println("1. Register For An Event");
                System.out.println("2. See All Participants");
                System.out.println("3. See Participants From Your Program");
                System.out.println("4. Search For Participant");
                System.out.println("5. See Overall Program");
                System.out.println("6. Exit");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        e.registerEvent();
                        break;
                    case "2":
                        seeAllParticipants();
                        break;
                    case "3":
                        seeParticipantsFromProgram();
                        break;
                    case "4":
                        searchForParticipants();
                        break;
                    case "5":
                        seeOverallProgram();
                        break;
                    case "6":
                        running = false;
                    default:
                        System.out.println("Invalid choice. Please select a number from 1 to 6.");
                }
            }

        } else {
            System.out.println("User " + userName + " does not exist in the database.");
        }
    }
}