package io.sample.service.impl;

import java.io.IOException;

import io.sample.dao.HBaseDAO;
import io.sample.mapreduce.SampleMapReduce;
import io.sample.service.MapReduceService;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.FirstKeyOnlyFilter;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.hbase.mapreduce.TableMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapReduceServiceImpl implements MapReduceService {

	final Logger logger = LoggerFactory.getLogger(MapReduceServiceImpl.class);

	@Autowired
	private HBaseDAO hBaseDAO;

	@Autowired
	private Configuration defaultHBaseConfig;

	
	@Override
	public void testMapReduce() throws Exception {
//
//    	Configuration config = HBaseConfiguration.create();
//        config.clear();
//        config.set("hbase.master","dev-hadooppat-ap02:6000");
//        config.set("hbase.zookeeper.quorum", "dev-hadooppat-ap02");
//        config.set("hbase.zookeeper.property.clientPort","2181");

        Job job = new Job(defaultHBaseConfig, "Hbase_FreqCounter1");
        job.setJarByClass(SampleMapReduce.class);
        Scan scan = new Scan();
        scan.setCaching(500);
        scan.setCacheBlocks(false);
        scan.addColumn(Bytes.toBytes("details"), Bytes.toBytes("page"));
        // scan.addFamily(Bytes.toBytes("details"));

        scan.setFilter(new FirstKeyOnlyFilter());
        TableMapReduceUtil.initTableMapperJob("access_logs", scan, Mapper1.class, ImmutableBytesWritable.class,
                IntWritable.class, job);
        TableMapReduceUtil.initTableReducerJob("summary_user", Reducer1.class, job);

        if (job.waitForCompletion(true)) {
            logger.info("this is true.");
        } else {
        	logger.info("this is false.");
        }


	}

	public static class Mapper1 extends TableMapper<ImmutableBytesWritable, IntWritable> {

        private int numRecords = 0;
        private static final IntWritable one = new IntWritable(1);

        @Override
        public void map(ImmutableBytesWritable row, Result values, Context context) throws IOException {
            // extract userKey from the compositeKey (userId + counter)
            ImmutableBytesWritable userKey = new ImmutableBytesWritable(row.get(), 0, Bytes.SIZEOF_INT);

            System.out.println(">>>>>userKey>>>>>" + Bytes.toString(userKey.get()));

            try {
                context.write(userKey, one);
            } catch (InterruptedException e) {
                throw new IOException(e);
            }
            numRecords++;
            if ((numRecords % 1000) == 0) {
                context.setStatus("mapper processed " + numRecords + " records so far");
            }
        }
    }

    public static class Reducer1 extends TableReducer<ImmutableBytesWritable, IntWritable, ImmutableBytesWritable> {

        public void reduce(ImmutableBytesWritable key, Iterable<IntWritable> values, Context context)
                throws IOException, InterruptedException {

        	int sum = 0;
            for (IntWritable val : values) {
                sum += val.get();
            }

            Put put = new Put(key.get());
            put.add(Bytes.toBytes("details"), Bytes.toBytes("total"), Bytes.toBytes(sum));
            System.out.println(String.format("stats :   key : %d,  count : %d", Bytes.toInt(key.get()), sum));
            context.write(key, put);
        }

    }
    
}