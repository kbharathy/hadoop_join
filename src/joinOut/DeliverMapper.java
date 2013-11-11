package joinOut;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DeliverMapper extends Mapper<LongWritable, Text, Text, Text> {
	private String identifier = "DD~";
	
	@Override
	public void map(LongWritable key, Text values,
			Context context) throws IOException, InterruptedException {
		String line_split[] = values.toString().split(",");
		
		context.write(new Text(line_split[0]), new Text(line_split[1].trim().concat(identifier)) );
		
	}

}
