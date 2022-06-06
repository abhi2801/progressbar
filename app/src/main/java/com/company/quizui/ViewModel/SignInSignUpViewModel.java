package com.company.quizui.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.company.quizui.Repository.SignUpSignInRepo;
import com.google.firebase.auth.FirebaseUser;

public class SignInSignUpViewModel extends AndroidViewModel {
    private SignUpSignInRepo repo;
    private final MutableLiveData<FirebaseUser> liveData;

    public MutableLiveData<FirebaseUser> getLiveData() {
        return liveData;
    }

    public SignInSignUpViewModel(@NonNull Application application) {
        super(application);
        repo=new SignUpSignInRepo(application);
        liveData = repo.getFirebaseUserMutableLiveData();
    }
    public void Signup(String name,String mobile,String email,String password){
        repo.signUp(name, mobile, email, password);
    }
    public void Signin(String email,String password){
        repo.signin(email, password);
    }
    public void signout(){
        repo.signout();
    }

}
