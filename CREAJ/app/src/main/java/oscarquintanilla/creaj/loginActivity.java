    package oscarquintanilla.creaj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

    public class loginActivity extends AppCompatActivity {

        public void otroInicio(View view){
            Intent OtroIS = new Intent(getApplicationContext(), oscarquintanilla.creaj.uve.class);
            startActivity((OtroIS));
        }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

        public void iniciarSesion(View view){
            TextView usuarioTV, password;
            String user, contrasena;
            password = (TextView) findViewById(R.id.txtContra);
            usuarioTV = (TextView) findViewById(R.id.txtUsuario); // Guarda el correo del usuario
            user = new String(usuarioTV.getText().toString());
            contrasena = new String(password.getText().toString());
            AsyncHttpClient client = new AsyncHttpClient();
            //String url = "http://creaj.000webhostapp.com/GetData.php";
            String url = "http://192.168.1.6/servicios%20web/Login/login.php?'";
            //String url = "http://10.10.5.16/servicios%20web/Login/login.php?usuario='$login'&clave='$clave'";
            RequestParams parametros = new RequestParams();
            parametros.put("usuario","login");
            parametros.put("clave","clave");
            try{
                client.post(url, parametros, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        if (statusCode == 200){
                            //cargaLista(obtenerDatosJSON(new String(responseBody)));
                            Intent juradoActivity = new Intent(getApplicationContext(), oscarquintanilla.creaj.juradoActivity.class);
                            startActivity(juradoActivity);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(loginActivity.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
            }catch (Exception e){
                Log.i("=====Error=======", e.toString());
                Toast.makeText(this, "Error: " + e.toString(), Toast.LENGTH_SHORT).show();
            }


        }


        public ArrayList<String> obtenerDatosJSON(String response){
            ArrayList<String> datosUsuario = new ArrayList<String>();
            try{
                JSONArray jsonArray = new JSONArray(response);
                String texto;
                for (int i = 0; i < jsonArray.length(); i++){
                    texto = jsonArray.getJSONObject(i).getString("nombres") + " " +
                            jsonArray.getJSONObject(i).getString("apellidos") + " " +
                            jsonArray.getJSONObject(i).getString("login") + " " +
                            jsonArray.getJSONObject(i).getString("clave") + " " +
                            jsonArray.getJSONObject(i).getString("tipo") + " " +
                            jsonArray.getJSONObject(i).getString("estado") + " ";
                    datosUsuario.add(texto);
                }

            }catch (Exception e){
                Toast.makeText(loginActivity.this, "Error: " + e, Toast.LENGTH_SHORT).show();
                String error = e.toString();
                Log.i("Error",error);
            }
            Log.d("--------Conexxion", " -" + response);
            return datosUsuario;
        }

}
