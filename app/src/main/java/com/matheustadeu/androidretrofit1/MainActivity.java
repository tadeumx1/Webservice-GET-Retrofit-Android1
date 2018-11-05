package com.matheustadeu.androidretrofit1;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.matheustadeu.androidretrofit1.controller.iRetrofitGitHub;
import com.matheustadeu.androidretrofit1.model.Usuario;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sincrono = (Button) findViewById(R.id.btnSincrono);

        sincrono.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                iRetrofitGitHub githubUser = iRetrofitGitHub.retrofit.create(iRetrofitGitHub.class);

                final Call<Usuario> call = githubUser.getUsuario("tadeumx1");
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Carregando...");
                dialog.setCancelable(false);
                dialog.show();
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {

                        if (dialog.isShowing())
                            dialog.dismiss();

                        int code = response.code();

                        if (code == 200) {

                            Usuario usuario = response.body(); Toast.makeText(getBaseContext(), "Nome do usuário: " +
                                    usuario.name, Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Falha: " + String.valueOf(code), Toast.LENGTH_LONG).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {

                        if (dialog.isShowing())
                            dialog.dismiss();

                        Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

            }

        });

        Button seguidores = (Button) findViewById(R.id.btnSeguidores);

        seguidores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iRetrofitGitHub githubUser = iRetrofitGitHub.retrofit.create(iRetrofitGitHub.class);

                final Call<List<Usuario>> call = githubUser.getSeguidores("tadeumx1");
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setMessage("Carregando...");
                dialog.setCancelable(false);
                dialog.show();

                call.enqueue(new Callback<List<Usuario>>() {
                    @Override
                    public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {

                        if (dialog.isShowing())
                            dialog.dismiss();

                        List<Usuario> lista = response.body();
                        for (Usuario usuario : lista) {

                            Toast.makeText(MainActivity.this, "Usuário : " + usuario.login, Toast.LENGTH_SHORT).show();

                            Log.d("MainActivity", usuario.login);

                        }

                    }

                    @Override
                    public void onFailure(Call<List<Usuario>> call, Throwable t) {

                        if (dialog.isShowing())
                            dialog.dismiss();

                    }
                });

            }
        });

    }
}
