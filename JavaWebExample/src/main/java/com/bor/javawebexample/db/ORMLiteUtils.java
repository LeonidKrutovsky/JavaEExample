/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bor.javawebexample.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
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
///TODO(leon): move to config
    private static String databaseUrl;
    private static String user;
    private static String password;
    private static final Logger logger = Logger.getLogger(ORMLiteUtils.class.getName());
    
    
    public static void  init (DbConfig config) {
        databaseUrl = config.getDatabaseUrl();
        user = config.getUser();
        password = config.getPassword();
    }


    public static <T> void createTable(Class<T> type) {
        try (ConnectionSource connectionSource = new JdbcPooledConnectionSource(databaseUrl, user, password)) {
            TableUtils.createTableIfNotExists(connectionSource, type);
        } catch (SQLException | IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public static <T> void create(Class<T> type, T object) {
        Dao<T, String> dao = null;
        try (ConnectionSource connectionSource = new JdbcPooledConnectionSource(databaseUrl, user, password)) {
            dao = DaoManager.createDao(connectionSource, type);
            dao.create(object);
        } catch (SQLException | IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    public static <T> List<T> getAll(Class<T> type) {
        List<T> list = new ArrayList<>();
        Dao<T, String> dao = null;

        try (ConnectionSource connectionSource = new JdbcPooledConnectionSource(databaseUrl, user, password)) {
            dao = DaoManager.createDao(connectionSource, type);
            list = dao.queryForAll();
        } catch (SQLException | IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public static <T> List<T> find(Class<T> type, T matchObj) {
        List<T> list = new ArrayList<>();
        Dao<T, String> dao = null;

        try (ConnectionSource connectionSource = new JdbcPooledConnectionSource(databaseUrl, user, password)) {
            dao = DaoManager.createDao(connectionSource, type);
            list = dao.queryForMatchingArgs(matchObj);
        } catch (SQLException | IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
