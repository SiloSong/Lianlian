package application.cartoon.com.lianlian;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 作    者： shangzemin
 * 类的用途：
 * 日    期： 2017-04-22.
 */

public class MyRightAdapter extends BaseAdapter {
    private List<JavaBean.DatalistBean> list;
    private Context context;
private int posi;
    public MyRightAdapter(List<JavaBean.DatalistBean> list, Context context,int posi) {
        this.list = list;
        this.context = context;
        this.posi=posi;
    }

    public int getPosi() {
        return posi;
    }

    public void setPosi(int posi) {
        this.posi = posi;
    }

    @Override
    public int getCount() {
        return list!=null?2:0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView=View.inflate(context,R.layout.right_item,null);
        }
        TextView tv1= (TextView) convertView.findViewById(R.id.textView);
        tv1.setText(list.get(posi).getCourse_tname());
        TextView tv2= (TextView) convertView.findViewById(R.id.textView2);
        tv2.setText(list.get(posi).getCourse_name());
        MyImageLoader.displayCircle(context,list.get(posi).getCourse_pic(), (ImageView) convertView.findViewById(R.id.imageView));
        Log.e("posi"+posi,"---------------------"+list.get(posi).getCourse_pic());
       if (posi<list.size()-1)
        posi++;
        return convertView;
    }
}
