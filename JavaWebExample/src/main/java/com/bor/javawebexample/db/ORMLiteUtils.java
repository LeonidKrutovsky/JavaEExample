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
    private static String databaseUrl;
    private static String user;
    private static String password;
    private static final Logger LOGGER = Logger.getLogger(ORMLiteUtils.class.getName());
    
    
    public static void  init (DbConfig config) {
        databaseUrl = config.getDatabaseUrl();
        user = config.getUser();
        password = config.getPassword();
    }


    public static <T> void createTable(Class<T> type) {
        try (ConnectionSource connectionSource = new JdbcPooledConnectionSource(databaseUrl, user, password)) {
            TableUtils.createTableIfNotExists(connectionSource, type);
        } catch (SQLException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
    }

    public static <T> boolean create(Class<T> type, T object) {
        boolean retVal;
        
        try (ConnectionSource connectionSource = new JdbcPooledConnectionSource(databaseUrl, user, password)) {            
            DaoManager.createDao(connectionSource, type).create(object);
            retVal = true;
        } catch (SQLException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
            retVal = false;
        }
        return retVal;
    }

    public static <T> List<T> getAll(Class<T> type) {
        List<T> list = new ArrayList<>();
        
        try (ConnectionSource connectionSource = new JdbcPooledConnectionSource(databaseUrl, user, password)) {
            Dao<T, String> dao = DaoManager.createDao(connectionSource, type);
            list = dao.queryForAll();
        } catch (SQLException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public static <T> List<T> find(Class<T> type, T matchObj) {
        List<T> list = new ArrayList<>();
        
        try (ConnectionSource connectionSource = new JdbcPooledConnectionSource(databaseUrl, user, password)) {
            Dao<T, String> dao = DaoManager.createDao(connectionSource, type);
            list = dao.queryForMatchingArgs(matchObj);
        } catch (SQLException | IOException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }

        return list;
    }
}
