package dev.carter.financetracker.controllers;

import com.mysql.cj.xdevapi.Table;
import dev.carter.financetracker.Expense;
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
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private Statement stmt;
    private Connection conn;
    private String sql;
    private ResultSet rs;

    private ObservableList<Expense> expensesList = FXCollections.observableArrayList();

    @FXML
    private ChoiceBox<String> categorySelection;

    @FXML
    private TableView<Expense> expenseTable;

    @FXML
    private TableColumn<Expense, Integer> expId;

    @FXML
    private TableColumn<Expense, Double> expAm;

    @FXML
    private TableColumn<Expense, String> category;

    @FXML
    private TableColumn<Expense, Date> date;

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
            populateExpenses();
            //populate categories
            populateCategories();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateExpenses() throws SQLException {
        rs = stmt.executeQuery("SELECT * from expenses");
        while(rs.next()){
            expensesList.add(new Expense(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getDate(4)));
        }
        rs.close();
        if(expensesList.isEmpty()){
            sql = "ALTER TABLE expenses AUTO_INCREMENT = 1";
            stmt.executeUpdate(sql);
        }
        expId.setCellValueFactory(new PropertyValueFactory<Expense, Integer>("ExpenseId"));
        expAm.setCellValueFactory(new PropertyValueFactory<Expense, Double>("ExpenseAmount"));
        category.setCellValueFactory(new PropertyValueFactory<Expense, String>("category"));
        date.setCellValueFactory(new PropertyValueFactory<Expense, Date>("date"));
        expenseTable.setItems(expensesList);
    }

    private void populateCategories(){
        String[] categories = {"Food & Drink", "Entertainment", "Equipment", "Transport", "Investment", "Rent"};
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
        sql = "SELECT * FROM expenses ORDER BY expenseId DESC LIMIT 1";
        rs = stmt.executeQuery(sql);
        if(rs.next()){
            expensesList.add(new Expense(rs.getInt("expenseId"), rs.getDouble("expenseAmount"), rs.getString("category"), rs.getDate(4)));
        }
        expId.setCellValueFactory(new PropertyValueFactory<Expense, Integer>("ExpenseId"));
        expAm.setCellValueFactory(new PropertyValueFactory<Expense, Double>("ExpenseAmount"));
        category.setCellValueFactory(new PropertyValueFactory<Expense, String>("category"));
        date.setCellValueFactory(new PropertyValueFactory<Expense, Date>("date"));
        expenseTable.setItems(expensesList);
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
             java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
             sql = "INSERT INTO expenses (expenseAmount, category, date) VALUES (" + amount + ", \'" + cat +"\', \'"+ date +"')";
             stmt.executeUpdate(sql);
        }
        balanceLabel.setText(updated_balance);
    };

}
