package application.cartoon.com.lianlian;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private JavaBean javabean;
Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what==HttpUtils.SUCCESS){
            String json= (String) msg.obj;
            initData(json);
writeJson(json);

        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

                        ConnectivityManager mConnectivityManager = (ConnectivityManager)
                                getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                         if (mNetworkInfo != null&&mNetworkInfo.isAvailable()) {


                                 new HttpUtils(handler,"http://www.meirixue.com/api.php?c=list&a=index",HttpUtils.FLAG_GET).start();



                            }else {
                             Toast.makeText(this, "网络不可用", Toast.LENGTH_SHORT).show();
                             String json=read();
                             if (json!=null)
                             initData(json);
                         }

    }

    private void initView() {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Right_frag rf=new Right_frag();
        rf.list=javabean.getDatalist();

        Left_frag left=new Left_frag();
        left.rf=rf;
        transaction.add(R.id.right_frag,rf);
        transaction.add(R.id.left_frag,left);
        transaction.commit();
    }

    private void initData(String json) {
        javabean=new Gson().fromJson(json,JavaBean.class);
        initView();

    }
    private String read(){
        String json=null;
        try{
        File file=new File(getFilesDir(),"json");
        if (file.exists()){
            StringBuffer sb=new StringBuffer();
            byte[] bytes=new byte[1024];
            int hasRead=-1;
           InputStream is=new FileInputStream(file);
            while((hasRead=is.read(bytes))!=-1){
                sb.append(new String(bytes,0,hasRead));
            }
            return sb.toString();
        }}catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }
    private void writeJson(String json){
        try{
        File file=new File(getFilesDir(),"json");
            FileWriter fw=new FileWriter(file);
           fw.write(json);
            fw.flush();
            fw.close();
        }catch(Exception e){
        e.printStackTrace();
        }

    }
}
