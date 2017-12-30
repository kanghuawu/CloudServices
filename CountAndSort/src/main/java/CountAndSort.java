import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.File;
import java.io.IOException;

public class KangHuaWu368 {
    public static class CountMapper extends Mapper<Object, Text, Text, LongWritable> {
        private final static LongWritable one = new LongWritable(1);
        @Override
        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            String url = value.toString().split(" ")[6];
            context.write(new Text(url), one);
        }
    }

    public static class IntSumReducer extends Reducer<Text, LongWritable, Text, LongWritable> {
        private LongWritable result = new LongWritable();
        @Override
        public void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
            long sum = 0;
            for (LongWritable val : values) {
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    }

    public static class SortMapper extends Mapper<Object, Text, Text, LongWritable> {
        @Override
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
            String[] strArr = value.toString().split("\t");
            String url = strArr[0];
            String num = String.format("%06d", Long.parseLong(strArr[1]));
            context.write(new Text(num + "\t"+ url), new LongWritable());
        }
    }

    public static class DummyReducer extends Reducer<Text, IntWritable, Text, LongWritable> {
        @Override
        public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
            String[] arrStr = key.toString().split("\t");
            String num = arrStr[0];
            String url = arrStr[1];
            context.write(new Text(url), new LongWritable(Long.parseLong(num)));
        }
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        if (args.length != 3) {
            throw new IllegalArgumentException("Input has to be two arguments");
        }
        File intermediate = new File(args[1]);
        if (intermediate.exists()){
            FileUtils.deleteDirectory(intermediate);
            System.out.println("Intermediate folder deleted!");
        }
        Configuration conf = new Configuration();
        Job count = Job.getInstance(conf, "count");
        count.setJarByClass(KangHuaWu368.class);
        count.setMapperClass(CountMapper.class);
        count.setCombinerClass(IntSumReducer.class);
        count.setReducerClass(IntSumReducer.class);
        count.setOutputKeyClass(Text.class);
        count.setOutputValueClass(LongWritable.class);
        FileInputFormat.addInputPath(count, new Path(args[0]));
        FileOutputFormat.setOutputPath(count, new Path(args[1]));
        count.waitForCompletion(true);

        File output = new File(args[2]);
        if (output.exists()){
            FileUtils.deleteDirectory(output);
            System.out.println("Output folder deleted!");
        }
        Job sort = Job.getInstance(conf, "sort");
        sort.setJarByClass(KangHuaWu368.class);
        sort.setMapperClass(SortMapper.class);
        sort.setReducerClass(DummyReducer.class);
        sort.setOutputKeyClass(Text.class);
        sort.setOutputValueClass(LongWritable.class);
        FileInputFormat.addInputPath(sort, new Path(args[1]));
        FileOutputFormat.setOutputPath(sort, new Path(args[2]));
        System.exit(sort.waitForCompletion(true) ? 0 : 1);
    }
}
