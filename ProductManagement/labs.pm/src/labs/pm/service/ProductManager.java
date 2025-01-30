/*
 * Copyright (C) 2021 oracle
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
package labs.pm.service;

import labs.pm.data.Product;
import labs.pm.data.Rating;
import labs.pm.data.Review;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @version 15.2
 * @author oracle
 */
public interface ProductManager {  
    Product createProduct(int id, String name, BigDecimal price, Rating rating) throws ProductManagerException;
    Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) throws ProductManagerException;
    Product reviewProduct(int id, Rating rating, String comments) throws ProductManagerException;
    Product findProduct(int id) throws ProductManagerException;
    List<Product> findProducts(Predicate<Product> filter) throws ProductManagerException;
    List<Review> findReviews(int id) throws ProductManagerException;
    Map<Rating, BigDecimal> getDiscounts() throws ProductManagerException;
}
