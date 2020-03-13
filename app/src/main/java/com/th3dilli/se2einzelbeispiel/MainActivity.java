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
    private TextView calcData;

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
        calcData = findViewById(R.id.calcText);
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

    public void calc(View view){
        char[] arr = String.valueOf(matNum.getText()).toCharArray();

        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            if(i % 2 == 0) {
                sum += (int)arr[i] - 48;
            } else {
                sum -= (int)arr[i] - 48;
            }
        }

        calcData.setText(String.valueOf(sum));
    }

}    