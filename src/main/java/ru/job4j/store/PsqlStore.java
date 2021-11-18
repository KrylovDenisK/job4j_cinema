package ru.job4j.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.model.Place;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (InputStream io = PsqlStore.class.getClassLoader().getResourceAsStream("db.properties")) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public List<Place> findAllPlaces() {
        List<Place> places = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT row, place from halls where status = false")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    places.add(
                            new Place(
                                    resultSet.getInt("row"),
                                    resultSet.getInt("place")
                            )
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return places;
    }

    @Override
    public List<Place> findAllPlacesAll() {
        List<Place> places = new ArrayList<>();
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT row, place, price, status from halls")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    places.add(
                            new Place(
                                    resultSet.getInt("row"),
                                    resultSet.getInt("place"),
                                    resultSet.getInt("price"),
                                    resultSet.getBoolean("status")
                            )
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return places;
    }

    @Override
    public int findPrice(String place) {
        int result = 0;
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT price from halls where row = ? and place = ?")) {
             ps.setInt(1, Character.digit(place.charAt(0), 10));
             ps.setInt(2, Character.digit(place.charAt(1), 10));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    result = resultSet.getInt("price");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void saveTicket(String fio, String phone, String place) {
        int id = findPlaceID(place);
        try (Connection connection = pool.getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement updateHall = connection.prepareStatement("update halls set status = false where id = ?;")) {
                updateHall.setInt(1, id);
                updateHall.executeUpdate();
            }
            try (PreparedStatement updateAcc = connection.prepareStatement("insert into accounts (fio, phone, idhall) values (?, ?, ?);")) {
                updateAcc.setString(1, fio);
                updateAcc.setString(2, phone);
                updateAcc.setInt(3, id);
                updateAcc.execute();
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int findPlaceID(String place) {
        int result = 0;
        try (Connection connection = pool.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT id from halls where row = ? and place = ?")) {
            ps.setInt(1, Character.digit(place.charAt(0), 10));
            ps.setInt(2, Character.digit(place.charAt(1), 10));
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    result = resultSet.getInt("id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
