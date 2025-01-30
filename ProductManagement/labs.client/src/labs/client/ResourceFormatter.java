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
package labs.client;

import labs.pm.data.Drink;
import labs.pm.data.Food;
import labs.pm.data.Product;
import labs.pm.data.Review;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @version 15.2
 * @author oracle
 */
public class ResourceFormatter {

    private static final Map<String, ResourceFormatter> formatters =
            Map.of("en-GB", new ResourceFormatter(Locale.UK),
                    "en-US", new ResourceFormatter(Locale.US),
                    "ru-RU", new ResourceFormatter(Locale.of("ru", "RU")),
                    "fr-FR", new ResourceFormatter(Locale.FRANCE),
                    "zh-CN", new ResourceFormatter(Locale.CHINA));

    private Locale locale;
    private ResourceBundle resources;
    private DateTimeFormatter dateFormat;
    private NumberFormat moneyFormat;

    public static ResourceFormatter getResourceFormatter(String languageTag) {
        return formatters.getOrDefault(languageTag, formatters.get("en-GB"));
    }

    public static Set<String> getSupportedLocales() {
        return formatters.keySet();
    }

    private ResourceFormatter(Locale locale) {
        this.locale = locale;
        resources = ResourceBundle.getBundle("labs.client.resources", locale);
        dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
        moneyFormat = NumberFormat.getCurrencyInstance(locale);
    }

    /**
     * Formats a message picked up from the resource bundle using designated key
     * and substitutes zero or more values. The value could be any object. If
     * the values is a BigDecimal object is is formatted as currency and if it
     * is a LocalDate value it is formatted as date.
     *
     * @param messageKey
     * @param values
     * @return
     */
    public String formatData(String messageKey, Object... values) {
        Object[] formattedValues = Arrays.stream(values).map(value -> {
            if (value instanceof LocalDate) {
                return dateFormat.format((LocalDate) value);
            }
            if (value instanceof BigDecimal) {
                return moneyFormat.format((Number) value);
            }
            return value.toString();
        }).toArray();
        return MessageFormat.format(resources.getString(messageKey), formattedValues);
    }

    public String formatProduct(Product product) {
        String type = switch (product) {
            case Food food -> resources.getString("food");
            case Drink drink -> resources.getString("drink");
        };
        return this.formatData("product", product.getName(), product.getPrice(), product.getRating().getStars(), product.getBestBefore(), type);
    }
    public String formatReview(Review review) {
        return this.formatData("review", review.rating().getStars(), review.comments());
    }
    public String formatProductReport(Product product, List<Review> reviews) {
        Collections.sort(reviews);
        StringBuilder txt = new StringBuilder();
        txt.append(formatProduct(product) + System.lineSeparator());
        if (reviews.isEmpty()) {
            txt.append(formatData("no.reviews") + System.lineSeparator());
        } else {
            txt.append(reviews.stream().map(review -> formatReview(review) + System.lineSeparator()).collect(Collectors.joining()));
        }
        return txt.toString();
    }

}
