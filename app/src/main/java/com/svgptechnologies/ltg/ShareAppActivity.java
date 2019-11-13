package com.svgptechnologies.ltg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ShareAppActivity extends AppCompatActivity {

    String ReferCode;
    String code, c;
    public static String FACEBOOK_URL = "https://www.facebook.com/YourPageName";
    public static String FACEBOOK_PAGE_ID = "YourPageName";
    ImageView refer_whatsapp, refer_messenger, refer_message, refer_gmail, refer_twiter, refer_facebook, refer_share;
    Toolbar refer_toolbar;
    TextView refer_code_textView;
    TextView copy_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_app);


        refer_whatsapp = (ImageView) findViewById(R.id.refer_whatsap);
        refer_messenger = (ImageView) findViewById(R.id.refer_messenger);
        refer_message = (ImageView) findViewById(R.id.refer_message);
        refer_gmail = (ImageView) findViewById(R.id.refer_gmail);
        refer_twiter = (ImageView) findViewById(R.id.refer_twiter);
        refer_facebook = (ImageView) findViewById(R.id.refer_facebook);
        refer_share = (ImageView) findViewById(R.id.refer_share);

        refer_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, ReferCode);
                sendIntent.setType("text/plain");
                sendIntent.setPackage("com.whatsapp");
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(sendIntent);
                }
            }
        });

        refer_messenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, ReferCode);
                intent.setType("text/plain");
                intent.setPackage("com.facebook.orca");

                try {
                    startActivity(intent);
                } catch (ActivityNotFoundException ex) {
                    Toast.makeText(ShareAppActivity.this, "Oups!Can't open Facebook messenger right now. Please try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });

        refer_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "phoneNumber");
                smsIntent.putExtra("sms_body", ReferCode);
                startActivity(smsIntent);
            }
        });

        refer_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "your_emailid@gmail.com"));
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Refferal Code");
                    intent.putExtra(Intent.EXTRA_TEXT, ReferCode);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(ShareAppActivity.this, "Sorry...You don't have any mail app", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        refer_twiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tweetIntent = new Intent(Intent.ACTION_SEND);
                tweetIntent.putExtra(Intent.EXTRA_TEXT, ReferCode);
                tweetIntent.setType("text/plain");

                PackageManager packManager = getPackageManager();
                List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

                boolean resolved = false;
                for (ResolveInfo resolveInfo : resolvedInfoList) {
                    if (resolveInfo.activityInfo.packageName.startsWith("com.twitter.android")) {
                        tweetIntent.setClassName(
                                resolveInfo.activityInfo.packageName,
                                resolveInfo.activityInfo.name);
                        resolved = true;
                        break;
                    }
                }
                if (resolved) {
                    startActivity(tweetIntent);
                } else {
                    Toast.makeText(ShareAppActivity.this, "Twitter app isn't found", Toast.LENGTH_LONG).show();
                }
            }
        });

        refer_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(ShareAppActivity.this);
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });

        refer_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, ReferCode);
                startActivity(sendIntent);
            }
        });

    }

    //To open The Facebook when user click facebook icon
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;

            boolean activated = packageManager.getApplicationInfo("com.facebook.katana", 0).enabled;
            if (activated) {
                if ((versionCode >= 3002850)) {
                    return "fb://facewebmodal/f?href=" + FACEBOOK_URL;
                } else {
                    return "fb://page/" + FACEBOOK_PAGE_ID;
                }
            } else {
                return FACEBOOK_URL;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return FACEBOOK_URL;
        }

    }


}
