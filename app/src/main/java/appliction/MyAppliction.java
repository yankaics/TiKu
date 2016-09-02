package appliction;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/8/30.
 */
public class MyAppliction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);

    }
}
