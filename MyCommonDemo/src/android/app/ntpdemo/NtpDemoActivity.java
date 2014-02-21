package android.app.ntpdemo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.nbtstatx.mydemos.R;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.NoRouteToHostException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NtpDemoActivity extends Activity {
    private static final int UPDATE_TIME = 0;
    private static final int REMOVE_PROGRESS = 1;
    private static final int RETRY_CONNECT = 2;
    private static final int SHOW_TOAST = 3;

    // private Button mButton;
    private TextView mTextView;
    private ProgressDialog mProgress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ntpdemo_main);
        initLayout();
    }

    private void initLayout() {
        mTextView = (TextView) findViewById(R.id.timeNtpDemo);

        Spinner spinner = (Spinner) findViewById(R.id.spinnerNtpDemo);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.servers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                String ip = (String) parent.getAdapter().getItem(position);
                System.out.println("ip: " + ip);
                startUpdateThread(ip);
                showProgressDialog(ip);
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void startUpdateThread(String ip) {
        // start update thread
        Thread thread = new Thread(new UDPThread(ip));
        thread.start();
    }

    private String showProgressDialog(String ipAddr) {
        mProgress = new ProgressDialog(this);
        mProgress.setTitle(R.string.progress_title);
        mProgress.setMessage(ipAddr);
        mProgress.setCancelable(true);
        mProgress.show();

        return null;
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private class UDPThread implements Runnable {
        int retry = 2;
        int port = 123;
        int timeout = 30000;

        InetAddress ipv4Addr = null;
        String ipAddr = "210.72.145.44";

        // 中国大概能用的NTP时间服务器
        // server 133.100.11.8 prefer
        // server 210.72.145.44 //(国家授时中心服务器IP地址)
        // server 203.117.180.36 //程序中所用的
        // server 131.107.1.10
        // server time.asia.apple.com
        // server 64.236.96.53
        // server 130.149.17.21
        // server 66.92.68.246
        // server www.freebsd.org
        // server 18.145.0.30
        // server clock.via.net
        // server 137.92.140.80
        // server 133.100.9.2
        // server 128.118.46.3
        // server ntp.nasa.gov
        // server 129.7.1.66
        // server ntp-sop.inria.frserver
        // ntpdate 131.107.1.10
        // ntpdate -s time.asia.apple.com

        public UDPThread(String ip) {
            this.ipAddr = ip;
        }

        @Override
        public void run() {
            System.out.println("UDP thread run......  ");
            try {
                ipv4Addr = InetAddress.getByName(ipAddr);
            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            }

            int serviceStatus = -1;
            DatagramSocket socket = null;
            long responseTime = -1;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(timeout); // will force the
                // InterruptedIOException

                // Send NTP request
                //
                byte[] data = new NtpMessage().toByteArray();
                DatagramPacket outgoing = new DatagramPacket(data, data.length,
                        ipv4Addr, port);
                long sentTime = System.currentTimeMillis();

                // print outgoing NtpMessage
                NtpMessage outMsg = new NtpMessage(outgoing.getData());
                System.out.println("--------Send NtpMessage--------\n"
                        + outMsg.toString());
                socket.send(outgoing);

                // Get NTP Response
                //
                DatagramPacket incoming = new DatagramPacket(data, data.length);
                socket.receive(incoming);
                responseTime = System.currentTimeMillis() - sentTime;
                double destinationTimestamp = (System.currentTimeMillis());
                // 这里要加2208988800，是因为获得到的时间是格林尼治时间，
                // 所以要变成东八区的时间，否则会与与北京时间有8小时的时差

                // Validate NTP Response
                // IOException thrown if packet does not decode as
                // expected.
                NtpMessage msg = new NtpMessage(incoming.getData());
                double localClockOffset = ((msg.receiveTimestamp - msg.originateTimestamp) + (msg.transmitTimestamp - destinationTimestamp)) / 2;

                System.out
                        .println("poll: valid NTP request received the local clock offset is "
                                + localClockOffset
                                + ", responseTime= "
                                + responseTime + "ms");
                System.out.println("poll: NTP message : \n" + msg.toString());
                serviceStatus = 1;

                mHandler.obtainMessage(SHOW_TOAST, "更新成功...").sendToTarget();
                mHandler.obtainMessage(UPDATE_TIME, msg).sendToTarget();

            } catch (InterruptedIOException ex) {
                // Ignore, no response received.
                System.out
                        .println("InterruptedIOException, no response received");
                mHandler.obtainMessage(SHOW_TOAST,
                        "InterruptedIOException, no response received")
                        .sendToTarget();
            } catch (NoRouteToHostException e) {
                System.out.println("NoRouteToHostException for address: "
                        + ipv4Addr);
                mHandler.obtainMessage(SHOW_TOAST,
                        "NoRouteToHostException for address: " + ipv4Addr)
                        .sendToTarget();
            } catch (ConnectException e) {
                // Connection refused. Continue to retry.
                e.fillInStackTrace();
                System.out.println("ConnectException for address: " + ipv4Addr);
                mHandler.obtainMessage(SHOW_TOAST,
                        "ConnectException for address: " + ipv4Addr)
                        .sendToTarget();
                mHandler.sendEmptyMessage(RETRY_CONNECT);
            } catch (IOException ex) {
                ex.fillInStackTrace();
                System.out.println("IOException while polling address: "
                        + ipv4Addr);
                mHandler.obtainMessage(SHOW_TOAST,
                        "IOException while polling address: " + ipv4Addr)
                        .sendToTarget();
            } catch (Exception ex) {
                ex.fillInStackTrace();
            } finally {
                if (socket != null) {
                    socket.close();
                }
                mHandler.sendEmptyMessage(REMOVE_PROGRESS);
            }

            // Store response time if available
            if (serviceStatus == 1) {
                System.out.println("responsetime = " + responseTime + "ms");
            }

        }

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIME:
                    NtpMessage ntpMessage = (NtpMessage) msg.obj;
                    long ms = NtpMessage
                            .timestampToLong(ntpMessage.transmitTimestamp);
                    String str = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss")
                            .format(new Date(ms));
                    mTextView.setText(":" + str);
                    break;
                case REMOVE_PROGRESS:
                    mProgress.dismiss();
                    break;
                case RETRY_CONNECT:
                    mProgress.setMessage("Connection refused, retry");
                    break;
                case SHOW_TOAST:
                    showToast(msg.obj.toString());
                    break;
                default:
                    break;
            }
        }
    };
}
