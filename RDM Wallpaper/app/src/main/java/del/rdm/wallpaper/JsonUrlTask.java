package del.rdm.wallpaper;

import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;
import java.net.HttpURLConnection;
import java.io.InputStream;
import java.io.BufferedReader;
import java.net.URL;
import java.io.InputStreamReader;
import org.json.JSONArray;
import java.net.MalformedURLException;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import com.google.gson.Gson;
import java.util.ArrayList;
import android.util.Log;

public class JsonUrlTask extends AsyncTask<String, Integer, String> {
	
	private ProgressDialog prog;
	private Context context;
	public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	public AsyncRespone ar = null;
	private static final String TAG = "Pesan";
	
	public JsonUrlTask(Context context, AsyncRespone ar){
		this.context = context;
		this.ar = ar;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Log.i(TAG, "onPreExecute Bekerja");
			prog = new ProgressDialog(context);
			prog.setTitle("Loading data from server");
			prog.setMessage("Please Wait...");
			prog.setCancelable(false);
			prog.setIndeterminate(false);
			prog.setMax(100);
			prog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			prog.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		Log.i(TAG, "doInBackground");
		HttpURLConnection connection=null;
		InputStream inputStream=null;
		BufferedReader reader=null;
		ArrayList<WallModel> listImage = new ArrayList<>();
		
		try {
			URL url=new URL(params[0]);
			connection=(HttpURLConnection)url.openConnection();
			connection.connect();
			int lenghtOfFile = connection.getContentLength();
			inputStream=connection.getInputStream();
			reader=new BufferedReader(new InputStreamReader(inputStream));
			StringBuffer buffer=new StringBuffer();
			String line="";
			long total = 0;
			
			while((line=reader.readLine())!=null)
			{
				total += line.length();
				publishProgress((int)((total*100)/lenghtOfFile));
				buffer.append(line);
				Log.d(TAG, buffer.toString());
			}
			String json=buffer.toString();
			
			JSONArray Arr = new JSONArray(json);
			for(int i=0; i<Arr.length(); i++) {
				JSONObject Obj = Arr.getJSONObject(i);
				String id = Obj.getString("Id");
				String name = Obj.getString("image_name");
				String path = Obj.getString("image_path");
				
				WallModel wm = new WallModel();
				wm.setId(id);
				wm.setName(name);
				wm.setUrl(path);
				listImage.add(wm);
				
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.d(TAG, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			Log.d(TAG, e.getMessage());
		} catch (JSONException e) {
			e.printStackTrace();
			Log.d(TAG, e.getMessage());
		} finally {
			if(connection!=null)
			{
				connection.disconnect();
			}
			if(reader!=null)
			{
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
					Log.d(TAG, e.getMessage());
				}
			}
		}
		return listImage.toString();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		super.onProgressUpdate(values);
		prog.setProgress(values[0]);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		ar.finisProses(result);
		if(prog != null && prog.isShowing()){
			prog.dismiss();
		}
	}
	
}
