package com.univ.malikb.firedroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.univ.malikb.firedroid.Modele.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText pseudo;
    private ProgressBar loader;
    private FirebaseAuth fAuth;
    private DatabaseReference ref;
    private SharedPreferences prefs;

    private static final String TAG = "CHAT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialisation des éléments de notre vue
        pseudo =  findViewById(R.id.pseudo);
        loader =  findViewById(R.id.loader);
        Button btn = findViewById(R.id.btnlog);

        /* ETAPE 2 : Initialisation du module Firebase
        fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase fBase = FirebaseDatabase.getInstance();
        ref = fBase.getReference();
        */

        //Les préférences vont nous servir à enregistrer les usernames
        prefs = getSharedPreferences(TAG, MODE_PRIVATE);

        //Si utilisateur déjà logué, on l'envoie vers la chat activity directement
        if(fAuth.getCurrentUser() != null && prefs.getString("PSEUDO", null) != null ){
            startActivity(new Intent(getApplicationContext(), ChatActivity.class));
            finish();
        }

        //On définit le listener sur le bouton de log
        btn.setOnClickListener(this);

    }


    //Méthode appelée lors du clic bouton
    @Override
    public void onClick(View view) {
        loader.setVisibility(View.VISIBLE);
        String username = pseudo.getText().toString();
        if(!TextUtils.isEmpty(username)){
            login(username);
        }

    }


    private void login(final String username){

        fAuth.signInAnonymously().addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                /* ETAPE 3 : Connexion à la base
                //Si la connexion échoue on informe l'utilisateur à l'aide d'un pop-up
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Impossible de se connecter, veuillez réessayer...", Toast.LENGTH_SHORT).show();
                }
                */



                /* ETAPE 5 : Identification des utilisateurs
                final String uid = task.getResult().getUser().getUid();

                checkUsername(username, new CheckUsernameCallback() {
                    @Override
                    public void isValid(final String username) {
                        //On crée un utilisateur avec son pseudo et l'id généré
                        User us = new User(username, uid);

                        //On écrit les informations de l'utilisateur dans la base
                        ref.child("users").child(uid).setValue(us).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    ref.child("usernames").child(username).setValue(uid);
                                    prefs.edit().putString("PSEUDO", username).apply();
                                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                                    finish();
                                }
                            }
                        });

                    }

                    //Dans le cas où le pseudo est déjà pris
                    @Override
                    public void isTaken() {
                        Toast.makeText(LoginActivity.this, "Pseudo déjà pris, en choisir un autre...", Toast.LENGTH_SHORT).show();
                        loader.setVisibility(View.INVISIBLE);
                    }
                });
                */
            }
        });

    }


    /* ETAPE 5 : Méthode pour vérifier que le pseudo n'est pas déjà pris
    private void checkUsername(final String username, final CheckUsernameCallback callback){
        ref.child("usernames").child(username).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    callback.isTaken();
                }else{
                    callback.isValid(username);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled: " +databaseError.getMessage() );
                loader.setVisibility(View.INVISIBLE);
            }
        });
    }
    */


    //Interface permettant de vérifier que le pseudo soit unique
    interface CheckUsernameCallback{
        void isValid(String username);
        void isTaken();
    }
    //FIN

}
