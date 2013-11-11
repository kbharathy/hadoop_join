package joinOut;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class UserMapper extends Mapper<LongWritable , Text , Text, Text > {
	private Text cellnumber = new Text();
	private String identifier = "UD~";
	
	@Override
	public void map(LongWritable key, Text value,
			Context context) throws IOException, InterruptedException {
			String line_split[] = value.toString().split(",");
			
			cellnumber.set(line_split[0].trim());
			System.out.println(cellnumber);
			
			context.write(cellnumber, new Text(line_split[1].trim().concat(identifier)) );
	}

}
