package com.th3dilli.se2einzelbeispiel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;

class NetworkClient {

    NetworkClient(Observer observer){
        this.observer = observer;
    }

    private final String hostname = "se2-isys.aau.at";
    private final int port = 53212;

    private Observer observer;


    void getData(String matNum) {
        doNetworkCall(matNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private Observable<String> doNetworkCall(String matNum) {
        ObservableOnSubscribe<String> handler = emitter -> {
            String matNr = String.valueOf(matNum);
            try {
                Socket socket = new Socket(hostname, port);
                PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
                pr.println(matNr);

                BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String line = inStream.readLine();

                pr.close();
                inStream.close();
                socket.close();
                emitter.onNext(line);
            } catch (IOException e) {
                emitter.onError(e);
            }
        };
        return Observable.create(handler);
    }
}
