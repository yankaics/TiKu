package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.tiku.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pojo.Question;

/**
 * Created by Administrator on 2016/9/1.
 */
public class QuestionAdapter extends BaseAdapter {
    List<Question> list = new ArrayList<Question>();
    Context context;

    public QuestionAdapter(List<Question> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.question_list_item,null);
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.leibie = (TextView) convertView.findViewById(R.id.tv_type);
            holder.time = (TextView) convertView.findViewById(R.id.tv_pubTime);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(list.get(position).getContent().toString());
        System.out.println(list.get(position).getContent().toString()+"==========================================");
        switch (list.get(position).getTypeid()){
            case 1:{
                holder.leibie.setText("单选");
            }
            break;
            case 2:{
                holder.leibie.setText("多选");
            }
            break;
            case 3:{
                holder.leibie.setText("判断");
            }
            break;
            case 4:{
                holder.leibie.setText("简答");
            }
            break;
        }
        System.out.println(list.get(position).getTypeid()+"========================================");
        long data = list.get(position).getPubTime();
        DateUtils utils = new DateUtils();
        String d = utils.getDateToString(data);
        holder.time.setText(d);
        System.out.println(d+"=====================================");
        return convertView;
    }
    public class ViewHolder{
        TextView title;
        TextView leibie;
        TextView time;

    }
    public class DateUtils {

        private SimpleDateFormat sf = null;

        public String getCurrentDate() {
            Date d = new Date();
            sf = new SimpleDateFormat("yyyy年MM月dd日");
            return sf.format(d);
            }




        public String getDateToString(long time) {
            Date d = new Date(time);
            sf = new SimpleDateFormat("yyyy年MM月dd日");
            return sf.format(d);
            }
    }
}
