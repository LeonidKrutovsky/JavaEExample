/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leon
 */
public class ORMLiteUtils {

    private static String databaseUrl = "jdbc:mysql://localhost:3306/test";
    private static String user = "root";
    private static String password = "1";

    private static final String TAG = ORMLiteUtils.class.getName();

    public static <T> void createTable(Class<T> type) {
        try (ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, user, password)) {
            TableUtils.createTableIfNotExists(connectionSource, type);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
        }
    }

    public static <T> void create(Class<T> type, T object) {
        Dao<T, String> dao = null;
        try (ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, user, password)) {
            dao = DaoManager.createDao(connectionSource, type);
            dao.create(object);            
        } catch (SQLException | IOException ex) {
            Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
        }
    }

    public static <T> List<T> getAll(Class<T> type) {
        List<T> list = new ArrayList<>();
        Dao<T, String> dao = null;

        try (ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrl, user, password)) {
            dao = DaoManager.createDao(connectionSource, type);
            list = dao.queryForAll();            
        } catch (SQLException | IOException ex) {
            Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
