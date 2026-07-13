package com.athletico.eshop;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class MainViewController {

    @FXML
    private TextField SearchBar;

    @FXML
    private Button SearchButton;

    @FXML
    private Button mainviewShowCartButton;

    @FXML
    private GridPane productGrid;

    @FXML
    private Button logoutButton;

    private final ProductDAO productDAO = new ProductDAO();
    private static final int COLUMNS = 4;

    @FXML
    private void initialize() {
        mainviewShowCartButton.setOnAction(event -> SceneManager.switchTo("cart_view.fxml"));
        SearchButton.setOnAction(event -> handleSearch());
        logoutButton.setOnAction(event -> handleLogout());

        loadProducts(productDAO.getAllProducts());
        updateCartButtonLabel();
    }

    private void loadProducts(List<Product> products) {
        productGrid.getChildren().clear();

        int row = 0;
        int col = 0;

        for (Product product : products) {
            VBox card = buildProductCard(product);
            productGrid.add(card, col, row);

            col++;
            if (col == COLUMNS) {
                col = 0;
                row++;
            }
        }
    }

    private VBox buildProductCard(Product product) {
        ImageView imageView = new ImageView();
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);

        try {
            Image image = new Image(getClass().getResourceAsStream(
                    "images/products/" + product.getImagePath()));
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Could not load image for: " + product.getName());
        }

        Label nameLabel = new Label(product.getName());
        nameLabel.setWrapText(true);
        nameLabel.setMaxWidth(150);

        Label priceLabel = new Label(String.format("$%.2f", product.getPrice()));

        Button addToCartButton = new Button("Add to Cart");
        addToCartButton.setOnAction(event -> {
            CartManager.getInstance().addProduct(product);
            updateCartButtonLabel();
        });

        VBox card = new VBox(5, imageView, nameLabel, priceLabel, addToCartButton);
        card.setPadding(new Insets(10));
        card.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-alignment: center;");
        card.setPrefWidth(180);

        return card;
    }

    // Updates the little cart icon/button so the item count stays visible
    // even though the button is now an icon, not text - we use its tooltip
    // and accessible text so it's not silently invisible.
    private void updateCartButtonLabel() {
        int count = CartManager.getInstance().getTotalItemCount();
        mainviewShowCartButton.setText(count > 0 ? String.valueOf(count) : "");
    }

    private void handleLogout() {
        Session.getInstance().logout();
        CartManager.getInstance().clear();
        SceneManager.switchTo("login_view.fxml");
    }

    private void handleSearch() {
        String query = SearchBar.getText();
        if (query == null || query.isBlank()) {
            loadProducts(productDAO.getAllProducts());
            return;
        }

        List<Product> filtered = productDAO.getAllProducts().stream()
                .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
                .toList();

        loadProducts(filtered);
    }
}