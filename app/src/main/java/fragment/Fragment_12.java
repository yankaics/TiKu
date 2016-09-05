package fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.tiku.R;


public class Fragment_12 extends Fragment {

    TextView qus_title,qus_ask;
    String tt,aa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_12, null);
        qus_title = (TextView) view.findViewById(R.id.question_title);
        qus_ask = (TextView) view.findViewById(R.id.question_ask);
        System.out.println("以获取id");
        qus_title.setText(tt);
        qus_ask.setText(aa);
        // Inflate the layout for this fragment
        return view;

    }
public void changeTitle(String s){

    tt =s;
}
    public void changeAsk(String s){

        aa = s;
    }
    public void SetTitle(String s){
        qus_title.setText(s);
    }
    public void SetAsk(String s){
        qus_ask.setText(s);
    }



}
