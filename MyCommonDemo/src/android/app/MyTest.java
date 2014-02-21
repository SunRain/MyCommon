package android.app;

import android.os.Bundle;
import android.util.Log;

import de.greenrobot.event.EventBus;

public class MyTest extends Activity {
    private EventBus controlBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        controlBus = new EventBus();
        controlBus.register(this);
        controlBus.post(new MyTestEvent("xxxx"));
    }

    public void onDestroy() {
        controlBus.unregister(this);
        super.onDestroy();
    }

    public void onEvent(MyTestEvent event) {
        Log.e("ggggg", "ggggg");
    }
}

class MyTestEvent {
    private String name;

    public MyTestEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}