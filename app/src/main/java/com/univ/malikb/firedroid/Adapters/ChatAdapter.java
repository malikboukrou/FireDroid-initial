package com.univ.malikb.firedroid.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.univ.malikb.firedroid.Modele.Message;
import com.univ.malikb.firedroid.R;

import java.util.ArrayList;


public class ChatAdapter {


    ArrayList<Message> listeMessages;
    private static final int SELF_MESSAGE = 0;
    private static final int ALL_MESSAGE = 1;
    private FirebaseUser user;

    /* ETAPE 7 : Adapter pour la gestion du RecycleView

    //Adapter pour les 2 views de messages (les miens et ceux des autres)
    public ChatAdapter(ArrayList<Message> listeMessages) {
        this.listeMessages = listeMessages;
    }


    public void addMessage(Message m){
        listeMessages.add(m);
        notifyDataSetChanged();
    }

    //Permet d'afficher les bulles de conversations
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        //On charge le layout en fonction du message
        switch (viewType){
            case SELF_MESSAGE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_self_message, parent, false);
                return new SelfMessageVH(v);
            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_message, parent, false);
                return new AllMessagesVH(v);
        }
    }

    //Méthode pour binder l'affichage des messages en fonction de l'emetteur
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message message = listeMessages.get(position);

        if (holder.getItemViewType() == ALL_MESSAGE){
            ((AllMessagesVH) holder).bind(message);
        }
        else {((SelfMessageVH) holder).bind(message);}
    }

    //Retourne le nombre de messages
    @Override
    public int getItemCount() {
        return listeMessages.size();
    }


    //Permet d'afficher les messages à gauche ou à droite en fonction de si c'est l'utilisateur
    //courant qui a envoyé le message ou non
    @Override
    public int getItemViewType(int position) {
        if (listeMessages.size() > 0 ){
            if (listeMessages.get(position).getUid().equals(user.getUid())){
                return SELF_MESSAGE;//Notre message
            }
            else {return ALL_MESSAGE;}//Message d'un autre utilisateur
        }

        return super.getItemViewType(position);
    }


    //Classe permettant de gérer l'affichage de nos messages envoyés
    class SelfMessageVH extends RecyclerView.ViewHolder {

        private TextView selfMess;

        public SelfMessageVH(View itemView) {
            super(itemView);
            selfMess = itemView.findViewById(R.id.selfMessage);
        }

        void bind(Message m){
            selfMess.setText(m.getContenu());
        }
    }

    //Classe permettant de gérer l'affichage de tous les messages du chat
    class AllMessagesVH extends RecyclerView.ViewHolder{

        private TextView username, contenu;

        public AllMessagesVH(View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            contenu = itemView.findViewById(R.id.message);
        }

        void bind(Message m){
            username.setText(m.getUsername());
            contenu.setText(m.getContenu());
        }
    }
    */

    //Méthode pour assigner l'utilisateur lorsqu'il est connecté
    public void setUser(FirebaseUser user){
        this.user = user;
    }
}
