package com.edu360.day01;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class TestC3p0 {

	public static void main(String[] args) throws Exception {
		
		ComboPooledDataSource pool = new ComboPooledDataSource();
		
		QueryRunner  run = new QueryRunner(pool);
		//String sql = "create table uidcount2(uid varchar(20),count varchar(20))";
		String sql = "insert into uidCount values(?,?)";
		run.update(sql,"3","ww");
	}
}
