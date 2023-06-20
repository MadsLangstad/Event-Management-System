package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Event {
    //# Fields
    private int eventId;
    private String eventName;

    private int studentId;
    private Connection con;

    public Scanner sc = new Scanner(System.in);

    public Event(Connection con){
        this.con = con;
    };

    //# Getters & Setters
    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    //# Methods
    public void registerEvent() {

        try {
            String use = "USE eventDB;";
            PreparedStatement useStmt = con.prepareStatement(use);
            int res = useStmt.executeUpdate();

            String sqlGetGuests = "SELECT COUNT(*) AS num_guests FROM guests WHERE studentId = ?;";
            PreparedStatement stmt = con.prepareStatement(sqlGetGuests);
            stmt.setInt(1, this.studentId);
            ResultSet rs = stmt.executeQuery();

            int numGuests = 4;

            while(rs.next()) {
                numGuests = Integer.parseInt(rs.getString("num_guests"));
            }

            int maxGuests = (4 - numGuests);
            System.out.println("Excellent! How Many Guests Do You Wish To Invite? Max " + maxGuests + " Guests.");

            int choice = Integer.parseInt(sc.nextLine()); // https://www.geeksforgeeks.org/why-is-scanner-skipping-nextline-after-use-of-other-next-functions/

            if(choice > maxGuests) {
                System.out.println("Max " + maxGuests + " guests!");
                return;
            }

            for(int i = 1; i <= choice; i++) {

                System.out.println("Type A Name: " + i);

                String name = sc.nextLine();

                String sqlAddGuest = "INSERT INTO guests (name, studentId) VALUES(?,?);";
                stmt = con.prepareStatement(sqlAddGuest);
                stmt.setString(1, name);
                stmt.setInt(2, this.studentId);
                int rsAddGuest = stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}

