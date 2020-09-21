package com.t.familyapp.View.main;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.t.familyapp.R;

import java.util.Objects;

/**
 * 起動時の認証画面の出力・処理を担当するクラス
 *
 */
public class LoginActivity extends AppCompatActivity {

    EditText emailFormEditText, passwordFormEditText;
    public Intent data;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        emailFormEditText = (EditText) findViewById(R.id.email_log_in_edit_text);
        passwordFormEditText = (EditText) findViewById(R.id.password_log_in_edit_text);
        mAuth = FirebaseAuth.getInstance();
    }

    /**
     * 「ログイン」押下時の処理
     * @param v
     */
    public void loginMailButton(View v) {
        signIn(emailFormEditText.getText().toString(), passwordFormEditText.getText().toString());
        setResult(RESULT_OK, data);
    }

    /**
     * 「新規登録」押下時の処理
     * @param v
     */
    public void addMailButton(View v) {
        createAccount(emailFormEditText.getText().toString(), passwordFormEditText.getText().toString());
        setResult(RESULT_OK, data);
    }

    /**
     * ログインの処理
     *
     * @param email
     * @param password
     */
    private void signIn(String email, String password) {
        if (checkEmpty()) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "ログインに成功しました", Toast.LENGTH_SHORT).show();
                            changeActivity();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(Objects.requireNonNull(task.getException()).getMessage())
                                    .setTitle("ログインに失敗しました")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }

    /**
     * アカウントを新規作成する処理
     *
     * @param email
     * @param password
     */
    private void createAccount(String email, String password) {
        if (checkEmpty()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "新規アカウントが作成されました", Toast.LENGTH_SHORT).show();
                            changeActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "新規アカウント作成に失敗しました", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public boolean checkEmpty() {
        return (TextUtils.isEmpty(emailFormEditText.getText())) || (TextUtils.isEmpty(passwordFormEditText.getText()));
    }

    /**
     * 認証処理成功時にListActivityに遷移させる処理
     */
    private void changeActivity() {
        Intent intent = new Intent(this, ListActivity.class);
        startActivity(intent);
        finish();
    }
}