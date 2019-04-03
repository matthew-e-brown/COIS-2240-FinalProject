package kiosk.backend;
import kiosk.Main;

import java.util.ArrayList;
import java.sql.*;

public class Menu {
    /* Gets a list of 'types' (Breakfast, Burgers, etc.) from the Menu Table in the database */
    public static ArrayList<String> generateTypes() {
        ArrayList<String> types = new ArrayList<>();
        try {
            Statement statement = Main.DB.makeStatement();
            ResultSet rs = statement.executeQuery("SELECT DISTINCT type FROM menu");

            while (rs.next()) { types.add(rs.getString("type")); }

            statement.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return types;
    }

    /* Gets a list of 'items' (Vanilla Cone etc.) from the Menu table in the database, by the type (Snacks and Treats etc.) */
    public static ArrayList<String> getItemsByType(String type) {
        ArrayList<String> items = new ArrayList<>();
        try {
            Statement statement = Main.DB.makeStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT name FROM menu WHERE type = '%s'", type));
            while (rs.next()) {
                items.add(rs.getString("name"));
            }
            statement.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return items;
    }

    /* Gets the filepath of the image from the Menu Table in the database, for the food item named 'name' */
    public static String getFilepath(String name) {
        String filepath = null;
        try {
            Statement statement = Main.DB.makeStatement();
            String sql = "SELECT filename FROM menu WHERE name = '%s'";
            ResultSet rs = statement.executeQuery(String.format(sql, name.replaceAll("'", "''")));
            filepath = rs.getString("filename");
            statement.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return filepath;
    }

    /* Gets the price from the Menu Table in the database, for the food item named 'name' */
    public static float getPrice(String name) {
        float price = 0;
        try {
            Statement statement = Main.DB.makeStatement();
            ResultSet rs = statement.executeQuery(String.format("SELECT price FROM menu WHERE name = '%s'", name));
            price = rs.getFloat("price");
            statement.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return price;
    }
}