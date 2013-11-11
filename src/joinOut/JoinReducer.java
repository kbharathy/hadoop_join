package joinOut;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class JoinReducer extends Reducer<Text, Text, Text, Text> {

	private Text customerName = new Text("Customer Name");
	private Text dmessage = new Text("No Code");
	
	private Map<String, String> DeliverStatus = new HashMap<String, String>();
	private BufferedReader reader;

	
	
	@Override
	protected void setup(Context context){
		
		       String strRead;
		       try {
             
                    
					reader = new BufferedReader(new FileReader("/home/hduser/hadoop_data/deliver_status.txt"));
                     while ((strRead=reader.readLine() ) != null)
                     {
                           String splitarray[] = strRead.split(",");
                           //parse record and load into HahMap
                           DeliverStatus.put(splitarray[0].trim(), splitarray[1].trim());
                          
                     }
		              }
		              catch (FileNotFoundException e) {
		              e.printStackTrace();
		              }catch( IOException e ) {
		                       e.printStackTrace();
		                }
		             
       }
	
	
	@Override
	public void reduce(Text _key, Iterable<Text> values,
			Context context) throws IOException, InterruptedException {
		for(Text val : values){
			String valStr = val.toString();
			if (valStr.contains("UD~")){
				customerName.set( new Text(valStr.replace("CD~", "")));
			}else if(valStr.contains("DD~")){
				dmessage.set(new Text(DeliverStatus.get(valStr.replace("DD~", ""))));
			}
		}
		
		context.write(customerName,dmessage);
		
	

		
	}

}
