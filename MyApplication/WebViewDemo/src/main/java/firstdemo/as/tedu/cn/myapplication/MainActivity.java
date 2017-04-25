package firstdemo.as.tedu.cn.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static firstdemo.as.tedu.cn.myapplication.R.id.wv_web;

public class MainActivity extends Activity {

    private WebView mWb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWb = (WebView) findViewById(wv_web);
        //wb.setWebViewClient(new MyWebViewClient());
        mWb.setWebChromeClient(new MyWebChromeClient());
        mWb.getSettings().setJavaScriptEnabled(true);
        mWb.getSettings().setSaveFormData(false);
        mWb.getSettings().setSavePassword(false);
        mWb.getSettings().setSupportZoom(false);
        mWb.addJavascriptInterface(new SharpJS(), "sharp");
        mWb.loadUrl("file:///android_asset/demo3.html");


        //wb.addJavascriptInterface(new MyObject(this),"myObj");
    }

    private class MyWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            new AlertDialog.Builder(MainActivity.this).setTitle("Alert对话框")
                    .setMessage(message).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }
            }).setCancelable(false).show();
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            new AlertDialog.Builder(MainActivity.this).setTitle("Confirm对话框")
                    .setMessage(message).setPositiveButton("确定",(dialog,which)->{
                result.confirm();
            }).setNegativeButton("取消",(dialog,which)->
                result.cancel()
            ).setCancelable(false).show();
            return true;
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            final LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            final View myView = inflater.inflate(R.layout.prompt_view,null);
            TextView textView = (TextView) myView.findViewById(R.id.text);
            textView.setText(message);
            EditText eText = (EditText) myView.findViewById(R.id.edit);
            eText.setHint(defaultValue);
            new AlertDialog.Builder(MainActivity.this).setTitle("Prompt对话框").setView(myView)
                    .setPositiveButton("确定", (dialog, which) -> {
                        //单击确定后取得输入的值,传给网页处理
                        String value = ((EditText) myView.findViewById(R.id.edit)).getText().toString();
                        result.confirm(value);
                    })
                    .setNegativeButton("取消", (dialog, which) -> result.cancel()).show();
            return true;
        }
    }


    public class SharpJS {
        @android.webkit.JavascriptInterface
        public void contactlist() {
            try {
                System.out.println("contactlist()方法执行了！");
                String json = buildJson(getContacts());
                runOnUiThread(()->{
                    mWb.loadUrl("javascript:show('" + json + "')");
                });

            } catch (Exception e) {
                System.out.println("设置数据失败" + e);
            }
        }
        @android.webkit.JavascriptInterface
        public void call(String phone) {
            System.out.println("call()方法执行了！");
            Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(it);
        }
    }

    //将获取到的联系人集合写入到JsonObject对象中,再添加到JsonArray数组中
    public String buildJson(List<Contact> contacts)throws Exception
    {
        JSONArray array = new JSONArray();
        for(Contact contact:contacts)
        {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", contact.getId());
            jsonObject.put("name", contact.getName());
            jsonObject.put("phone", contact.getPhone());
            array.put(jsonObject);
        }
        return array.toString();
    }

    //定义一个获取联系人的方法,返回的是List<Contact>的数据
    public List<Contact> getContacts()
    {
        List<Contact> Contacts = new ArrayList<Contact>();
        //①查询raw_contacts表获得联系人的id
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //查询联系人数据
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while(cursor.moveToNext())
        {
            Contact contact = new Contact();
            //获取联系人姓名,手机号码
            contact.setId(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
            contact.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contact.setPhone(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            Contacts.add(contact);
        }
        cursor.close();
        return Contacts;
    }
}
