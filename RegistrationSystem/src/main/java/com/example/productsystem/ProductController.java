package com.example.productsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductController {
    @FXML
    private TableView<Product> tableView;
    @FXML
    private TableColumn<Product, Integer> idColumn;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, String> imageColumn;

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3307/regdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<>("imageUrl"));

        // Set up image rendering
        imageColumn.setCellFactory(param -> new TableCell<Product, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String imageUrl, boolean empty) {
                super.updateItem(imageUrl, empty);

                if (empty || imageUrl == null) {
                    setGraphic(null);
                } else {
                    // Load the image
                    Image image = new Image(imageUrl);
                    imageView.setImage(image);
                    imageView.setFitWidth(100); // Set width of the image
                    imageView.setFitHeight(100); // Set height of the image
                    setGraphic(imageView);
                }
            }
        });

        tableView.setItems(getProducts());
    }

    private ObservableList<Product> getProducts() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        String query = "SELECT id, name, price, quantity, image_url FROM products"; // Adjust the query to include image_url

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");
                String imageUrl = resultSet.getString("image_url"); // Fetch imageUrl from ResultSet
                products.add(new Product(id, name, price, quantity, imageUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

}
