package com.th3dilli.se2einzelbeispiel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;


public class MainActivity extends AppCompatActivity {

    private EditText matNum;
    private TextView responseData;

    private Observer observer = new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {

        }

        @Override
        public void onNext(String s) {
            responseData.setText(s);
        }

        @Override
        public void onError(@NonNull Throwable e) {
            responseData.setText(e.getMessage());
        }

        @Override
        public void onComplete() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matNum = findViewById(R.id.editMatNr);
        responseData = findViewById(R.id.responseData);

        // TODO: remove test string
        matNum.setText("11735897");

    }

    public void sendData(View view) {
        NetworkClient nwc = new NetworkClient(observer);
        String matNr = String.valueOf(matNum.getText());
        if (matNr.equals("")) {
            responseData.setText(R.string.noMatNr);
        } else {
            nwc.getData(matNr);
        }
    }




}    