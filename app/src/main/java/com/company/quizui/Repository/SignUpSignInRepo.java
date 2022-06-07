package com.company.quizui.Repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpSignInRepo {
    private final Application application;
    private final MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private final FirebaseAuth auth;
    private final MutableLiveData<Boolean> loginstatus;
    private final CollectionReference reference;

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getLoginstatus() {
        return loginstatus;
    }

    public SignUpSignInRepo(Application application) {
        this.application = application;
        firebaseUserMutableLiveData=new MutableLiveData<>();
        auth=FirebaseAuth.getInstance();
        loginstatus=new MutableLiveData<>();
        reference= FirebaseFirestore.getInstance().collection("peoples");
    }
    public void signUp(String name,String mobile,String email,String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if (task.isSuccessful()){
               HashMap<String,Object> map=new HashMap<>();
               map.put("name",name);
               map.put("email",email);
               map.put("mobile",mobile);
               reference.add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                   @Override
                   public void onComplete(@NonNull Task<DocumentReference> task) {
                  if (task.isSuccessful()){
                      Toast.makeText(application.getApplicationContext(), "Dekho ebe Progress Bar show karibani", Toast.LENGTH_LONG).show();
                      firebaseUserMutableLiveData.setValue(auth.getCurrentUser());
                  }else {
                      Toast.makeText(application.getApplicationContext(), "Failed to add user"+task.getResult(), Toast.LENGTH_SHORT).show();
                  }
                   }
               });
           }else {
               Toast.makeText(application.getApplicationContext(), "Error "+task.getResult(), Toast.LENGTH_SHORT).show();
           }
            }
        });
    }
    public void signin(String email,String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    firebaseUserMutableLiveData.setValue(auth.getCurrentUser());
                    Toast.makeText(application.getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(application.getApplicationContext(), ""+task.getResult(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void signout(){
        auth.signOut();
        loginstatus.setValue(true);
    }
}
