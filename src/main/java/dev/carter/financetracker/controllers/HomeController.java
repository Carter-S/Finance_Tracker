package dev.carter.financetracker.controllers;

import dev.carter.financetracker.Crypto;
import dev.carter.financetracker.Transaction;
import dev.carter.financetracker.MainApplication;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private Statement stmt;
    private Connection conn;
    private String sql;
    private ResultSet rs;

    private ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
    private ObservableList<Crypto> cryptoList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> categorySelection;

    @FXML
    private TableView<Crypto> cryptoTable;

    @FXML
    private TableColumn<Crypto, String> cryptoId;

    @FXML
    private TableColumn<Crypto, Float> cryptoAmount;

    @FXML
    private TableColumn<Crypto, Float> cryptoPrice;

    @FXML
    private TableColumn<Crypto, Double> cryptoTotal;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, Integer> tranId;

    @FXML
    private TableColumn<Transaction, Double> tranAm;

    @FXML
    private TableColumn<Transaction, String> category;

    @FXML
    private TableColumn<Transaction, Date> date;

    @FXML
    private Label overallLabel;

    @FXML
    private Label cryptoLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        conn = MainApplication.db.conn;
        try {
            stmt = conn.createStatement();
            sql = "SELECT balance FROM user_data";
            rs = stmt.executeQuery(sql);
            double balance = 0;
            if(rs.next()){
                balance = rs.getDouble(1);
            }
            rs.close();
            balanceLabel.setText(String.valueOf(balance));
            //populate table
            populateTransactions();
            //populate categories
            populateCategories();
            //populate cryptos
            populateCryptos();
            double cryptoT= calculateCryptoTotal();
            cryptoLabel.setText(String.valueOf(cryptoT));
            double ov = cryptoT + balance;
            DecimalFormat df = new DecimalFormat("0.00");
            overallLabel.setText(String.valueOf(df.format(ov)));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTransactions() throws SQLException {
        rs = stmt.executeQuery("SELECT * from transactions");
        while(rs.next()){
            transactionsList.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getDate(4)));
        }
        rs.close();
        if(transactionsList.isEmpty()){
            sql = "ALTER TABLE transactions AUTO_INCREMENT = 1";
            stmt.executeUpdate(sql);
        }
        tranId.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("transactionId"));
        tranAm.setCellValueFactory(new PropertyValueFactory<Transaction, Double>("transactionAmount"));
        category.setCellValueFactory(new PropertyValueFactory<Transaction, String>("category"));
        date.setCellValueFactory(new PropertyValueFactory<Transaction, Date>("date"));
        transactionTable.setItems(transactionsList);
    }

    private void populateCryptos() throws SQLException {
        rs = stmt.executeQuery("SELECT * from crypto");
        while(rs.next()){
            cryptoList.add(new Crypto(rs.getString(1), rs.getFloat(2), rs.getFloat(3), rs.getDouble(4)));
        }
        rs.close();
        if(cryptoList.isEmpty()){
            sql = "ALTER TABLE crypto AUTO_INCREMENT = 1";
            stmt.executeUpdate(sql);
        }
        cryptoId.setCellValueFactory(new PropertyValueFactory<Crypto, String>("cryptoId"));
        cryptoAmount.setCellValueFactory(new PropertyValueFactory<Crypto, Float>("cryptoAmount"));
        cryptoPrice.setCellValueFactory(new PropertyValueFactory<Crypto, Float>("cryptoPrice"));
        cryptoTotal.setCellValueFactory(new PropertyValueFactory<Crypto, Double>("cryptoTotal"));
        cryptoTable.setItems(cryptoList);
    }

    private double calculateCryptoTotal(){
        double total = 0;
        for(Crypto c: cryptoList){
            total += c.getCryptoTotal();
        }
        return total;
    }

    private void populateCategories(){
        String[] categories = {"Food & Drink", "Entertainment", "Equipment", "Transport", "Investment", "Rent", "Income", "Student finance"};
        categorySelection.getItems().addAll(categories);
        categorySelection.setValue(categories[0]);
    }
    @FXML
    private Label balanceLabel;

    @FXML
    private TextField numberBar;

    @FXML
    private void plusClick(ActionEvent actionEvent) throws SQLException {
        adjustBalance(1);
    }

    @FXML
    private void minusClick(ActionEvent actionEvent) throws IOException, SQLException {
        adjustBalance(0);
    }

    private void adjustBalance(int addsub) throws SQLException {
        double amount = Double.parseDouble(numberBar.getText());
        double balance = Double.parseDouble(balanceLabel.getText());
        String cat = categorySelection.getValue().toString();
        String updated_balance;
        if(addsub == 1) {
             updated_balance = String.valueOf(balance + amount);
        }else{
             updated_balance = String.valueOf(balance - amount);
             amount = 0 - amount;
        }
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        sql = "INSERT INTO transactions (transactionAmount, category, date) VALUES (" + amount + ", \'" + cat +"\', \'"+ date +"')";
        stmt.executeUpdate(sql);
        balanceLabel.setText(updated_balance);
        sql = "SELECT * FROM transactions ORDER BY transactionId DESC LIMIT 1";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            transactionsList.add(new Transaction(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getDate(4)));
        }
        rs.close();
        transactionTable.setItems(transactionsList);
        numberBar.clear();
    };

}
