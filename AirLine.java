/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 *
 * @author Danny
 */
public class AirLine extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Automobile Mechanic Application");
        
        //formatting the grid
        
        
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        
        Label serviceLabel = new Label("Select Service:");
        
        ToggleGroup serviceGroup = new ToggleGroup();
        
        RadioButton oilButton = new RadioButton("Oil Change");
        RadioButton brakeButton = new RadioButton("Brake Repair");
        
        Label partsLabel = new Label("Select Parts");
        
        CheckBox tiresBox = new CheckBox("Tires");
        CheckBox batteryBox = new CheckBox("Battery");
        CheckBox airFilterBox = new CheckBox("Air Filter");
        
        Label paymentLabel = new Label("Payment Method");
                
        ComboBox<String>paymentBox = new ComboBox<>();
        paymentBox.getItems().addAll("Credit Card", "Debit Card");
        
        Label totalLabel = new Label("Total:");
        TextField totalField = new TextField();
        
        Button submitButton = new Button("Submit");
        
        oilButton.setToggleGroup(serviceGroup);
        brakeButton.setToggleGroup(serviceGroup);
        
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(20, 20, 20, 20));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(nameField, 1, 0);
        
        gridPane.add(serviceLabel, 0, 2);
        gridPane.add(oilButton, 1, 2);
        gridPane.add(brakeButton, 2, 2);
        
        gridPane.add(partsLabel, 0, 3);
        gridPane.add(tiresBox, 1, 3);
        gridPane.add(batteryBox, 2, 3);
        gridPane.add(airFilterBox, 3, 3);
        
        gridPane.add(paymentLabel, 0, 4);
        gridPane.add(paymentBox, 1, 4);
        
        gridPane.add(totalLabel, 0, 5);
        gridPane.add(totalField, 1, 5);
        
        gridPane.add(submitButton, 1, 6);
        
        submitButton.setOnAction(e ->
        {
            double cost = 0.0;
            
            if(oilButton.isSelected())
            {
                cost += 30.00;
            }
            
            if(brakeButton.isSelected())
            {
                cost += 120.00;
            }
            
            if(tiresBox.isSelected())
            {
                cost += 50.00;
            }
            
            if(batteryBox.isSelected())
            {
                cost += 150.00;
            }
            
            if(airFilterBox.isSelected())
            {
                cost += 35.00;
            }
            
            String stringCost = String.valueOf(cost);
            
            totalField.setText(stringCost);
            
            String name = nameField.getText();
            
            try {
                insertData(name, stringCost);
            } catch (SQLException ex) {
                Logger.getLogger(AirLine.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        );
        
        Scene scene = new Scene(gridPane, 300, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    private Connection connect()
    {
        String url = "jdbc:sqlite:C:/Users/Danny/OneDrive - Rancho Santiago Community College District/Documents/NetBeansProjects/Presentations/mechanic.db";
        
        Connection conn = null;
        
        try
        {
            conn = DriverManager.getConnection(url);
        } catch(SQLException e)
        {
            System.out.println(e.getMessage());
        }
        
        return conn;
    }
    
    private void insertData(String name, String total) throws SQLException
    {
        String sql = "INSERT INTO mechanic (name, total) VALUES (?, ?)";
        
        try(Connection conn = this.connect();
                
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, name);
            pstmt.setString(2, total);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Info Sent To DataBase Mechanic");
                    
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
