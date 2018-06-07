package com.edu360.day01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.util.Set;

public class TestMain1 {

	public static void main(String[] args) {
		
		ComboPooledDataSource pool = new ComboPooledDataSource();
		QueryRunner  run = new QueryRunner(pool);
		String sql = "insert into uidCount values(?,?)";
		
		Map<String, Integer> map = getMap();
		
		List<UidCountBean> list = new ArrayList<>();
		Set<Entry<String, Integer>> entrySet = map.entrySet();
		for(Entry<String, Integer> entry:entrySet){
			String uid = entry.getKey();
			Integer count = entry.getValue();
			list.add(new UidCountBean(uid,count));
		}
		
		//System.out.println(list);
		Collections.sort(list, new Comparator<UidCountBean>(){

			@Override
			public int compare(UidCountBean o1, UidCountBean o2) {
				return o2.getCount()-o1.getCount();
			}});
		/*for(UidCountBean u :list){
			System.out.println(u);
		}*/
		
		try(BufferedWriter bw =new BufferedWriter(new FileWriter("d:/data/fcount.txt"));
			) {
			for(UidCountBean u :list){	
				bw.write(u.toString());
				bw.newLine();
				run.update(sql,u.getUid(),u.getCount().toString());
			}
			bw.flush();
			bw.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
		
	public static Map<String,Integer> getMap() {
		Map<String,Integer> map = new HashMap<>();
		try(BufferedReader br = new BufferedReader(new FileReader("d:/data/Friends.txt"));
			) {
			String line =null;
			while((line=br.readLine())!=null){
				//System.out.println(line);
				String[] split = line.split(":");
				String key = split[0];
				Integer count = split[1].split(",").length;
				map.put(key, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
