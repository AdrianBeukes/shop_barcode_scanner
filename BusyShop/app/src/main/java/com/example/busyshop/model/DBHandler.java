package com.example.busyshop.model;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHandler
{
    private AccountDB accountDB;
    private static DBHandler instance = null;

    String TAG = "DBHandler";

    public DBHandler(Context context) {
        accountDB = new AccountDB(context);
        accountDB.getWritableDatabase();
    }

    public static DBHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DBHandler(context);
        }
        return instance;
    }

    public AccountDB getdatabase() {
        Log.d(TAG, "getdatabase: ");
        return accountDB;
    }

    // Insert Function

    public int insertItemDetails(ItemDetail itemDetail) {
        accountDB.getAccountREDao().create(itemDetail);
        Log.d(TAG, "after creation: "+itemDetail.getAPL883().getId());
        return itemDetail.getAPL883().getId();
    }


    public List<ItemDetail> getItemList() {

        List<ItemDetail> itemDetailList = new ArrayList<>();
        try {
            RuntimeExceptionDao<ItemDetail, Integer> accountREDao = accountDB.getAccountREDao();
            QueryBuilder<ItemDetail, Integer> qb = accountREDao.queryBuilder();
            qb.orderBy("id",false);
            PreparedQuery<ItemDetail> pq = qb.prepare();

            itemDetailList = accountREDao.query(pq);

            if (!itemDetailList.isEmpty())
                return itemDetailList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemDetailList;
    }

    // Update details in the Account table

    public void updateItemDetails(int accountID, ItemDetail accountDetails) {
        RuntimeExceptionDao<ItemDetail, Integer> accountREDao = accountDB.getAccountREDao();
        UpdateBuilder<ItemDetail, Integer> updateBuilder = accountREDao.updateBuilder();
        try {
            updateBuilder.where().eq("id", accountID);
            updateBuilder.updateColumnValue("name", accountDetails.getAPL883().getDescription());
            updateBuilder.updateColumnValue("price", accountDetails.getAPL883().getPrice());
            updateBuilder.update();
            Log.d(TAG, "updateAccountDetails: ");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete account details from the records if exists

    public void deleteItemDetails(int accountID)
    {
        RuntimeExceptionDao<ItemDetail, Integer> accountREDao = accountDB.getAccountREDao();
        accountREDao.deleteById(accountID);
    }

    public boolean itemAlreadyExist(String name, String price)
    {
        List<ItemDetail> itemDetailList;
        try {
            RuntimeExceptionDao<ItemDetail, Integer> accountREDao = accountDB.getAccountREDao();
            QueryBuilder<ItemDetail, Integer> qb = accountREDao.queryBuilder();

            Where<ItemDetail, Integer> where = qb.where();
            where.eq("name", name);
            where.and();
            where.eq("price", price);

            PreparedQuery<ItemDetail> pq = qb.prepare();

            itemDetailList = accountREDao.query(pq);

            if (!itemDetailList.isEmpty())
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
