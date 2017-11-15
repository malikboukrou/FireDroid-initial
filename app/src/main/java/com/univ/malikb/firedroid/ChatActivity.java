package com.univ.malikb.firedroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.univ.malikb.firedroid.Adapters.ChatAdapter;
import com.univ.malikb.firedroid.Modele.Message;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    private EditText mess;
    private ImageButton send;
    private RecyclerView recycler;

    private FirebaseAuth fAuth;
    private DatabaseReference ref;
    private SharedPreferences prefs;

    //Permet d'écouter à chaque ajout de message dans la table
    private FirebaseAuth.AuthStateListener authStateListener;
    private ChildEventListener childEventListener;

    private String username, uid;
    private static final String TAG = "CHAT";
    private ChatAdapter chatAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Initialisation des composants de la vue
        mess = findViewById(R.id.message);
        send = findViewById(R.id.send);
        recycler = findViewById(R.id.recycler);

        //Le linearLayoutManager est relié au recyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true); //afficher le dernier message en bas
        recycler.setLayoutManager(linearLayoutManager);

        //Liste de tous les messages du chat
        ArrayList<Message> listeMessages = new ArrayList<>();


        /* ETAPE 7 : Initialisation de l'adapter
        chatAdapter = new ChatAdapter(listeMessages);
        recycler.setAdapter(chatAdapter);
        */

        //Initialisation du module Firebase
        fAuth = FirebaseAuth.getInstance();
        FirebaseDatabase fBase = FirebaseDatabase.getInstance();
        ref = fBase.getReference();

        //Les préférences vont nous servir à enregistrer les usernames
        prefs = getSharedPreferences(TAG, MODE_PRIVATE);

        //On ajoute un listerner au bouton d'envoie de message
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMess();
            }
        });

        //Méthode appelée quand il y a une intéraction avec le système d'authentification
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
                else {
                    attachChild();
                    username = prefs.getString("PSEUDO", null);
                    uid = user.getUid();
                    chatAdapter.setUser(user);
                }
            }
        };
    }


    private void sendMess(){
        /* ETAPE 6 : Envoie de messages
        String message = mess.getText().toString();
        if (!TextUtils.isEmpty(message)){
            Message m = new Message(username, uid, message, null);
            ref.child("messages").push().setValue(m);
            mess.setText("");
        }
        */
    }


    //Méthode permettant d'attacher le listener à la base
    private void attachChild() {
        if (childEventListener == null){
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    /* ETAPE 6
                    Log.w("ChatActivity", "onChildAdded");
                    */

                    /* ETAPE 7 : Affichage des messages
                    Message message = dataSnapshot.getValue(Message.class); //recupération du message envoyé
                    message.setUid(dataSnapshot.getKey()); //on récupère l'id du messsage
                    chatAdapter.addMessage(message);
                    recycler.scrollToPosition(chatAdapter.getItemCount()-1); //auto-scroll au dernier message
                    */
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {}
                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                @Override
                public void onCancelled(DatabaseError databaseError) {}
            };
            ref.child("messages").limitToLast(100).addChildEventListener(childEventListener);
        }
    }

    //Méthode permettant de dettacher le listener de la base
    private void dettachChild(){
        if (childEventListener != null){
            ref.child("messages").removeEventListener(childEventListener);
            childEventListener = null;
        }
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (authStateListener != null){
            fAuth.removeAuthStateListener(authStateListener);
        }
        dettachChild();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fAuth.addAuthStateListener(authStateListener);
    }

    //Gestion du menu pour se déconnecter
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout){
            //PLUS TARD
        }
        return true;
    }
}
