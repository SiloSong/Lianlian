package application.cartoon.com.lianlian;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Right_frag extends Fragment implements AdapterView.OnItemClickListener{

public List<JavaBean.DatalistBean> list;
    private ListView lv;
private MyRightAdapter adapter;
    public Right_frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_frag, container, false);
        lv = (ListView) view.findViewById(R.id.list);
        adapter=new MyRightAdapter(list, getActivity(),0);
        lv.setAdapter(adapter);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        adapter.setPosi(2*position);
        adapter.notifyDataSetChanged();
    }
}
