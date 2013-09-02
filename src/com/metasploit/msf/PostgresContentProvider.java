package com.metasploit.msf;

/**
 * Created by tim on 19/10/13.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PostgresContentProvider extends ContentProvider {

    protected Connection db;

    @Override
    public boolean onCreate() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost/msf3?user=msf3&password=Ml054G8mz7UOhjD0Kr3R7KGA8wD3ghz5";
            db = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return (db != null);
    }

    public ResultSet postgresQuery(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) throws SQLException {
        String sql = "";
        PreparedStatement statement = db.prepareStatement(sql);
        return statement.executeQuery();
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        try {
            final ResultSet resultSet = postgresQuery(uri, projection, selection, selectionArgs, sortOrder);
            final int columnCount = resultSet.getMetaData().getColumnCount();
            String[] columnNames = new String[columnCount];
            for (int column = 0; column < columnCount; column++) {
                columnNames[column] = resultSet.getMetaData().getColumnName(column);
                System.out.println("" + columnNames[column]);
            }

            final MatrixCursor cursor = new MatrixCursor(columnNames);
            while (resultSet.next()) {
                Object[] data = new Object[columnCount];
                for (int column = 0; column < columnCount; column++) {
                    data[column] = resultSet.getObject(column);
                }
                cursor.addRow(data);
            }
            return cursor;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
