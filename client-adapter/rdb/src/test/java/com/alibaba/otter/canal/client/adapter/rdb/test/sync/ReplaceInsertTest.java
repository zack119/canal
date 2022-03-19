package com.alibaba.otter.canal.client.adapter.rdb.test.sync;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.otter.canal.client.adapter.rdb.RdbAdapter;
import com.alibaba.otter.canal.client.adapter.support.Dml;

/**
 * @author liubin
 * @date 2022/03/16 15:24
 */

public class ReplaceInsertTest {

    private RdbAdapter rdbAdapter;

    @Before
    public void init() {
        rdbAdapter = Common.initMySQL();
    }


    @Test
    public void test01() {
        Dml dml = new Dml();
        dml.setDestination("example");
        dml.setTs(new Date().getTime());
        dml.setType("INSERT");
        dml.setDatabase("mytest2");
        dml.setTable("user");
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> data = new LinkedHashMap<>();
        dataList.add(data);
        data.put("id", 2L);
        data.put("name", "Eric");
        data.put("role_id", 1L);
        data.put("c_time", new Date());
        data.put("test1", "sdfasdfawe中国asfwef");
        dml.setData(dataList);

        rdbAdapter.sync(Collections.singletonList(dml));
    }

    @Test
    public void test02() {
        Dml dml = new Dml();
        dml.setDestination("example");
        dml.setTs(new Date().getTime());
        dml.setType("UPDATE");
        dml.setDatabase("mytest2");
        dml.setTable("user");
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> data = new LinkedHashMap<>();
        dataList.add(data);
        data.put("id", 1L);
        data.put("name", "Eric5");
        data.put("role_id", 1L);
        data.put("c_time", new Date());
        data.put("test1", "sdfasdfawe中国asfwef");
        dml.setData(dataList);
        List<Map<String, Object>> oldList = new ArrayList<>();
        Map<String, Object> old = new LinkedHashMap<>();
        oldList.add(old);
        old.put("name", "Eric");
        dml.setOld(oldList);

        rdbAdapter.sync(Collections.singletonList(dml));
    }

    @Test
    public void test03() {
        Dml dml = new Dml();
        dml.setDestination("example");
        dml.setTs(new Date().getTime());
        dml.setType("UPDATE");
        dml.setDatabase("mytest2");
        dml.setTable("sku");
        List<Map<String, Object>> dataList = new ArrayList<>();
        Map<String, Object> data = new LinkedHashMap<>();
        dataList.add(data);
        data.put("sku_id", 3614390788645L);
        data.put("product_id", 1L);
        data.put("sku_no", "Eric5");
        dml.setData(dataList);
        List<Map<String, Object>> oldList = new ArrayList<>();
        Map<String, Object> old = new LinkedHashMap<>();
        oldList.add(old);
        old.put("sku_no", "K78-黑色-XL");
        dml.setOld(oldList);

        rdbAdapter.sync(Collections.singletonList(dml));
    }







}
