package application.cartoon.com.lianlian;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Left_frag extends Fragment {


    private ListView lv;
public Right_frag rf;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_left_frag, container, false);
        lv = (ListView) view.findViewById(R.id.list);
        lv.setAdapter(new LeftBaseAdapter(getActivity()));
        if (rf!=null)
        lv.setOnItemClickListener(rf);
      lv.setItemsCanFocus(true);// 让ListView的item获得焦点
               lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);// 单选模式
             // 默认第一个item被选中
        lv.setItemChecked(0, true);

        return view;

    }

}
