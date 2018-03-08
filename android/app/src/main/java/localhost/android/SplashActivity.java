package localhost.android;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import localhost.android.Presenter.LoginActivityPresenter;
import localhost.android.Presenter.SplashActivityPresenter;
import localhost.android.network.NetworkService;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SplashActivity ins=this;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                final SplashActivityPresenter presenter = new SplashActivityPresenter(ins);
                final boolean init=presenter.init_Login();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if(init){
                            //SID保存済み
                            Intent intent = new Intent(getApplication(), MainActivity.class);
                            startActivity(intent);//Main表示
                            finish();
                        }else{
                            //未ログイン
                            Intent intent = new Intent(getApplication(), LoginActivity.class);
                            startActivity(intent);//Login表示
                            finish();
                        }
                    }

                });
            }
        };

        Timer t = new Timer();
        t.schedule(task, 10);
    }
}
