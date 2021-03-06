package com.omega13.codecademy.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
    This class handles the table "Certificaten" inside the database
 */
public class CertificateData {
    DatabaseConnection connection = new DatabaseConnection();

    Connection conn = connection.makeConnection();

    //Gets all the certificates from the database
    public List<String> getCertificates(int id){
        ArrayList<String> certificates = new ArrayList<>();
        String certificateName;
        ResultSet rs;

        try{
            String query = "" +
                    "SELECT Cursus.Naam FROM Certificaat " +
                    "INNER JOIN Inschrijving ON Certificaat.ID = Inschrijving.CertificaatID " +
                    "INNER JOIN Cursus ON Inschrijving.CursusID = Cursus.ID " +
                    "AND Inschrijving.CursistID = " + id;

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            rs = preparedStmt.executeQuery();

            while(rs.next()){
                certificateName = rs.getString(1);
                certificates.add(certificateName);
            }

            rs.close();
            preparedStmt.close();
            return certificates;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Gets the amount of certificates inside the database
    public int getAmountCertificate(int id) {
        int amount = 0;
        ResultSet rs;

        try{
            String query = "SELECT COUNT(Certificaat.ID) FROM Certificaat INNER JOIN Inschrijving ON Certificaat.ID = Inschrijving.CertificaatID INNER JOIN Cursus ON Inschrijving.CursusID = Cursus.ID WHERE Cursus.ID = " + id;

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            rs = preparedStmt.executeQuery();

            while(rs.next()){
                amount = Integer.parseInt(rs.getString(1));
            }

            rs.close();
            preparedStmt.close();
            return amount;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
