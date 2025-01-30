/*
 * Copyright (C) 2019 oracle
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package labs.db.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import labs.pm.data.Product;
import labs.pm.data.Rating;
import labs.pm.data.Review;
import labs.pm.service.ProductManager;
import labs.pm.service.ProductManagerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import labs.pm.data.Drink;
import labs.pm.data.Food;
import labs.pm.data.Rateable;

/**
 *
 * @author oracle
 */
public class ProductDBManager implements ProductManager {

    private static final Logger logger = Logger.getLogger(ProductDBManager.class.getName());
    private static final ResourceBundle config = ResourceBundle.getBundle("labs.db.service.config");

//    public ProductDBManager() {
//        System.setProperty("derby.system.home", "/home/oracle/labs/derby");
//    }

    @Override
    public Product createProduct(int id, String name, BigDecimal price, Rating rating) throws ProductManagerException {
        Product product = new Drink(id, name, price, rating);
        insertProduct(product);
        return product;
    }

    @Override
    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) throws ProductManagerException {
        Product product = new Food(id, name, price, rating, bestBefore);
        insertProduct(product);
        return product;
    }

    @Override
    public Product reviewProduct(int id, Rating rating, String comments) throws ProductManagerException {
        insertReview(id, new Review(rating, comments));
        return findProduct(id);
    }

    @Override
    public Product findProduct(int id) throws ProductManagerException {
        Product product = null;
        try (Connection connection = DriverManager.getConnection(config.getString("jdbc.url"), config.getString("jdbc.user"), config.getString("jdbc.password"));
                PreparedStatement productQuery = connection.prepareStatement(config.getString("find.product.query"))) {
            productQuery.setInt(1, id);
            try (ResultSet resultSet = productQuery.executeQuery()) {
                if (resultSet.next()) {
                    switch (resultSet.getString(2)) {
                        case "F":
                            product = new Food(id, resultSet.getString(3), resultSet.getBigDecimal(4), Rateable.convert(resultSet.getInt(5)), resultSet.getDate(6).toLocalDate());
                            break;
                        case "D":
                            product = new Drink(id, resultSet.getString(3), resultSet.getBigDecimal(4), Rateable.convert(resultSet.getInt(5)));
                    }
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ProductManagerException("Error finding product with id " + id, ex);
        }
        return product;
    }

    @Override
    public List<Product> findProducts(Predicate<Product> filter) throws ProductManagerException {
        try (Connection connection = DriverManager.getConnection(config.getString("jdbc.url"), config.getString("jdbc.user"), config.getString("jdbc.password"));
                PreparedStatement productQuery = connection.prepareStatement(config.getString("find.products.query"));
                ResultSet resultSet = productQuery.executeQuery()) {
            Stream.Builder<Product> streamBuilder = Stream.builder();
            while (resultSet.next()) {
                Product product = null;
                switch (resultSet.getString(2)) {
                    case "F":
                        product = new Food(resultSet.getInt(1), resultSet.getString(3), resultSet.getBigDecimal(4), Rateable.convert(resultSet.getInt(5)), resultSet.getDate(6).toLocalDate());
                        break;
                    case "D":
                        product = new Drink(resultSet.getInt(1), resultSet.getString(3), resultSet.getBigDecimal(4), Rateable.convert(resultSet.getInt(5)));
                }
                streamBuilder.add(product);
            }
            return streamBuilder.build().filter(filter).collect(Collectors.toList());
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ProductManagerException("Error finding products", ex);
        }
    }

    @Override
    public List<Review> findReviews(int id) throws ProductManagerException {
        List<Review> reviews = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(config.getString("jdbc.url"), config.getString("jdbc.user"), config.getString("jdbc.password"));
                PreparedStatement reviewQuery = connection.prepareStatement(config.getString("find.reviews.query"))) {
            reviewQuery.setInt(1, id);
            try (ResultSet resultSet = reviewQuery.executeQuery()) {
                while (resultSet.next()) {
                    reviews.add(new Review(Rateable.convert(resultSet.getInt(3)), resultSet.getString(4)));
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ProductManagerException("Error finding reviews for product with id " + id, ex);
        }
        return reviews;
    }

    @Override
    public Map<Rating, BigDecimal> getDiscounts() throws ProductManagerException {
        Map<Rating, BigDecimal> result = new HashMap<>();
        try (Connection connection = DriverManager.getConnection(config.getString("jdbc.url"), config.getString("jdbc.user"), config.getString("jdbc.password"));
                PreparedStatement discountQuery = connection.prepareStatement(config.getString("find.discounts.query"));
                ResultSet resultSet = discountQuery.executeQuery()) {
            while (resultSet.next()) {
                result.put(Rateable.convert(resultSet.getInt(1)), resultSet.getBigDecimal(2));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ProductManagerException("Error calculating product discounts ", ex);
        }
        return result;
    }

    private void insertProduct(Product product) throws ProductManagerException {
        try (Connection connection = DriverManager.getConnection(config.getString("jdbc.url"), config.getString("jdbc.user"), config.getString("jdbc.password"));
                PreparedStatement productInsert = connection.prepareStatement(config.getString("create.product"))) {
            String type = (product instanceof Food) ? "F" : "D";
            productInsert.setInt(1, product.getId());
            productInsert.setString(2, type);
            productInsert.setString(3, product.getName());
            productInsert.setBigDecimal(4, product.getPrice());
            productInsert.setInt(5, product.getRating().ordinal());
            productInsert.setDate(6, Date.valueOf(product.getBestBefore()));
            productInsert.executeUpdate();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ProductManagerException("Error creating product " + product, ex);
        }
    }

    private void insertReview(int id, Review review) throws ProductManagerException {
        try (Connection connection = DriverManager.getConnection(config.getString("jdbc.url"), config.getString("jdbc.user"), config.getString("jdbc.password"));
                PreparedStatement reviewInsert = connection.prepareStatement(config.getString("create.review"));
                PreparedStatement ratingQuery = connection.prepareStatement(config.getString("find.rating.query"))) {
            reviewInsert.setInt(1, id);
            reviewInsert.setInt(2, review.rating().ordinal());
            reviewInsert.setString(3, review.comments());
            reviewInsert.executeUpdate();
            ratingQuery.setInt(1, id);
            try (ResultSet resultSet = ratingQuery.executeQuery()) {
                if (resultSet.next()) {
                    updateProductRating(id, resultSet.getInt(1));
                }
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ProductManagerException("Error creating review " + id + " " + review, ex);
        }
    }

    private void updateProductRating(int id, int rating) throws ProductManagerException {
        try (Connection connection = DriverManager.getConnection(config.getString("jdbc.url"), config.getString("jdbc.user"), config.getString("jdbc.password"));
                PreparedStatement productUpdate = connection.prepareStatement(config.getString("update.product.rating"))) {
            productUpdate.setInt(1, id);
            productUpdate.setInt(2, rating);
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
            throw new ProductManagerException("Error updating product rating for product with id" + id + " " + rating, ex);
        }
    }
}
