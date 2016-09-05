package fragment;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.administrator.tiku.R;


public class Fragment_34 extends Fragment {

    TextView qus_title;
    CheckBox cb1,cb2,cb3,cb4;
    boolean c1,c2,c3,c4;
    String ct1,ct2,ct3,ct4;
    String title;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_34, null);
        qus_title = (TextView) view.findViewById(R.id.question_title2);
        cb1 = (CheckBox) view.findViewById(R.id.checkBox);
        cb2 = (CheckBox) view.findViewById(R.id.checkBox2);
        cb3 = (CheckBox) view.findViewById(R.id.checkBox3);
        cb4 = (CheckBox) view.findViewById(R.id.checkBox4);
        cb1.setChecked(c1);
        cb2.setChecked(c2);
        cb3.setChecked(c3);
        cb4.setChecked(c4);
        cb1.setText(ct1);
        cb2.setText(ct2);
        cb3.setText(ct3);
        cb4.setText(ct4);
        qus_title.setText(title);




        return view;
    }
public void changeTitle(String s){
    qus_title.setText(s);
}
    public void changCheckBox1(boolean b,String s){
        cb1.setChecked(b);
        cb1.setText(s);
    }
    public void changCheckBox2(boolean b,String s){
        cb2.setChecked(b);
        cb2.setText(s);
    }
    public void changCheckBox3(boolean b,String s){
        cb3.setChecked(b);
        cb3.setText(s);
    }public void changCheckBox4(boolean b,String s){
        cb4.setChecked(b);
        cb4.setText(s);
    }



    public void SetTitle(String s){
        title = s;
    }
    public void SetCheckBox1(boolean b,String s){
         c1 = b;
         ct1 = s;
    }
    public void SetCheckBox2(boolean b,String s){
         c2 = b;
         ct2 = s;
    }
    public void SetCheckBox3(boolean b,String s){
         c3 = b;
         ct3 = s;
    }public void SetCheckBox4(boolean b,String s){
         c4 = b;
         ct4 = s;
    }



}
