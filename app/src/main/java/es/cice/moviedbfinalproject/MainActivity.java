package es.cice.moviedbfinalproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import es.cice.moviedbfinalproject.adapters.FilmsAdapter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG=MainActivity.class.getCanonicalName();
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx=this;

        Spinner spinner = (Spinner) findViewById(R.id.spGenero);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.generos, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter); 



    }
    public void getMovies(View v){
        TheMovieDBAsyncTask at=new TheMovieDBAsyncTask();
        at.execute("https://api.themoviedb.org/3/movie/popular?api_key=857ef84cbaec1f89f981c0ac344c4630");

    }
    public class TheMovieDBAsyncTask extends AsyncTask<String, Void, List<String>> {
        //parámetro de entrada la URL
        @Override
        protected List<String> doInBackground(String... urls) {

            //devuelve la lista de títulos
            BufferedReader in = null;

            List<String> movieList = new ArrayList<>();
            //Retrofit evita tener que gestionar la conexion http
            //Retrofit no está disponible en android, hay que añadirla
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer data = new StringBuffer();
                //Insertar los datos obtenidos con in en el StringBuffer
                String line = null;
                while ((line = in.readLine()) != null) {
                    data.append(line);

                }
                JSONObject jsonObj = new JSONObject(data.toString());
                JSONArray results = jsonObj.getJSONArray("results");
                for (int i = 0; i < results.length(); i++) {
                    JSONObject jsonMovie = results.getJSONObject(i);
                    String title = jsonMovie.getString("original_title");
                    Log.d(TAG, title);
                    movieList.add(title);
                }
                return movieList;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<String> movieList) {
            //Rellena el listView

            FilmsAdapter adapter=new FilmsAdapter(ctx,R.layout.film_row,movieList);
            ListView listFilmsLV= (ListView) findViewById(R.id.listFilmsLV);
            listFilmsLV.setAdapter(adapter);
            /*listFilmsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(TAG,"click en item "  + i + "...");
                    Toast.makeText(MainActivity.this,"click en item "  + i + "...",Toast.LENGTH_LONG).show();
                }
            });
*/

            /*TextView moviesTV = (TextView) findViewById(R.id.movies);
            for (String movie : movieList) {
                moviesTV.append(movie + "\n");
            }*/
        }
    }

}
