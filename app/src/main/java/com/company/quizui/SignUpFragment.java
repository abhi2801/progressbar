package com.company.quizui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.company.quizui.ViewModel.SignInSignUpViewModel;
import com.company.quizui.databinding.FragmentSignUpBinding;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {
    FragmentSignUpBinding signUpBinding;
    private SignInSignUpViewModel viewModel;
    String name,email,mobile,password;
    boolean isAllChecked=false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        signUpBinding=FragmentSignUpBinding.inflate(inflater);
        viewModel=new ViewModelProvider(this).get(SignInSignUpViewModel.class);
        viewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser!=null){
                    Navigation.findNavController(requireView()).navigate(R.id.action_signUpFragment_to_dashboardFragment);
                }
            }
        });
        return signUpBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar pb=new ProgressBar(getContext());
        signUpBinding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pb.setVisibility(View.VISIBLE);
                name=signUpBinding.signupUsername.getText().toString();
                mobile=signUpBinding.signupMobile.getText().toString();
                email=signUpBinding.signupEmail.getText().toString();
                password=signUpBinding.signupPassword.getText().toString();
                isAllChecked=Validation();
                if (Validation()){
                    viewModel.Signup(name,mobile,email,password);
                    pb.setVisibility(View.GONE);
                }
                else {
                    pb.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Some thing went wrong", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public boolean Validation(){
        if (name.length()==0){
            signUpBinding.signupUsername.setError("Username cant be blank");
            signUpBinding.signupUsername.requestFocus();
            return false;
        }
        if (mobile.length()!=10){
            signUpBinding.signupMobile.setError("Enter a valid 10 digit mobile number");
            signUpBinding.signupMobile.requestFocus();
            return false;
        }
        if (email.length()==0){
            signUpBinding.signupEmail.setError("Email cant be blank");
            signUpBinding.signupEmail.requestFocus();
            return false;
        }
        if (password.length()==0){
            signUpBinding.signupPassword.setError("Password cant be blank");
            signUpBinding.signupPassword.requestFocus();
            return false;
        }
        if (password.length()<6){
            signUpBinding.signupPassword.setError("Enter atleast 6 digit password");
            signUpBinding.signupPassword.requestFocus();
            return false;
        }
        return true;
    }
}