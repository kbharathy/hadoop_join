package joinOut;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.io.Text;

import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class JoinOut {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		Configuration conff = new Configuration();

		conff.set("mapred.job.tracker", "localhost:54311"); 
		conff.set("fs.default.name", "hdfs://localhost:54310");
		
		Job job = new Job(conff, "JoinOut");
		job.setJarByClass(JoinOut.class);
		
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		MultipleInputs.addInputPath(job, new Path("/home/hduser/join_data/userdetails.txt"), TextInputFormat.class, UserMapper.class);
		MultipleInputs.addInputPath(job, new Path("/home/hduser/join_data/deliver.txt"), TextInputFormat.class, DeliverMapper.class);
		FileOutputFormat.setOutputPath(job, new Path("/home/hduser/gutenberg-output"));
		
	
		job.setReducerClass(JoinReducer.class);

		job.waitForCompletion(true);
	}

}
