package com.hbase.connection;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class CustomerRepositoryNew {

	public static void save(final String id, final String name, final String zipcode, final String city,
			final String isInternal) throws IOException {

		Configuration config = HBaseConfiguration.create();
		
		config.setInt("timeout", 120000);
		config.set("hbase.rootdir", "hdfs://centosbase:8020/hbase");
		config.set("hbase.zookeeper.quorum", "centosbase");
		config.set("hbase.master", "centosbase:60000");
		config.set("hbase.zookeeper.property.clientPort", "2181");

		Connection connection = ConnectionFactory.createConnection(config);

		Table table = connection.getTable(TableName.valueOf("customer"));

		try {

			Put put = new Put(Bytes.toBytes(id));
			put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("id"), Bytes.toBytes(id));
			put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("name"), Bytes.toBytes(name));
			put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("zipcode"), Bytes.toBytes(zipcode));
			put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("city"), Bytes.toBytes(city));
			put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("isInternal"), Bytes.toBytes(isInternal));			
			table.put(put);
			System.out.println("Values inserted : ");
			table.close();
			connection.close();
			
		} finally {
			
		}
	}
}
