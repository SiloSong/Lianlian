package application.cartoon.com.lianlian;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 作    者： shangzemin
 * 类的用途：
 * 日    期： 2017-04-22.
 */

public class LeftBaseAdapter extends BaseAdapter {
    private Context context;

    public LeftBaseAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return 5;
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
            convertView=View.inflate(context,R.layout.list_tem,null);

        }
        LinearLayout lin= (LinearLayout) convertView.findViewById(R.id.line1);
        TextView rb= (TextView) lin.getChildAt(0);
        rb.setId(View.generateViewId());
        rb.setText("条目"+2*position+"、"+(2*position+1));
        return convertView;
    }
}
