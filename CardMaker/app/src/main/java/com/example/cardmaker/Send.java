package com.example.cardmaker;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cardmaker.adapter.ShowUserAdapter;
import com.example.cardmaker.models.MySingleton;
import com.example.cardmaker.models.PersonInfo;
import com.example.cardmaker.models.SendModel;
import com.example.cardmaker.models.UploadModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Send extends AppCompatActivity implements SearchView.OnQueryTextListener, ShowUserAdapter.OnShowAdapterItemClickListener {
    private static final String TAG = "Send";

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AIzaSyBek1czZPHB_EsXteK_ZdjVbYCghWx-Gy4";
    final private String contentType = "application/json";

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    SearchView search;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    Context mContext;
    List<SendModel> nameList;
    List<PersonInfo> infoList;
    ShowUserAdapter adapter;
    String usename;
    StorageReference mStorageRef;
    ArrayList<SendModel> myUserList;
    ShowUserAdapter.OnShowAdapterItemClickListener showAdapterItemClickListener;
    ProgressDialog  progressDialog;
    Bitmap screenshot;
    private Uri screenshotUri;
    DatabaseReference uploadsreference;
    StorageReference sReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        if(getIntent().hasExtra("byteArray")) {
            ImageView previewThumbnail = new ImageView(this);
            screenshot= BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"),0,getIntent()
                            .getByteArrayExtra("byteArray").length);
            previewThumbnail.setImageBitmap(screenshot);
        }
        screenshotUri=getImageUri(getApplicationContext(),screenshot);
        System.out.println(screenshotUri);
        sReference=FirebaseStorage.getInstance().getReference("receivecard");
        uploadsreference=FirebaseDatabase.getInstance().getReference("receivecard");

        progressDialog = new ProgressDialog(Send.this);
        progressDialog.setMessage("Loading..."); // Setting Message
        progressDialog.setTitle("ProgressDialog"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        Log.d(TAG, "onCreate: Send Activity");

        myUserList=new ArrayList<>();

        mContext = Send.this;
        search = (SearchView) findViewById(R.id.searchEmail);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        recyclerView = findViewById(R.id.recSend);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        adapter=new ShowUserAdapter();

        if (databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("user");
        }

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myUserList.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    SendModel sendModel=ds.getValue(SendModel.class);
                    myUserList.add(sendModel);
                    if (sendModel.getEmail().equalsIgnoreCase(firebaseUser.getEmail())){
                        usename=sendModel.getName();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
                adapter.setSendlist(myUserList);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter.setMlistener(this);
        search.setOnQueryTextListener(this);
    }
    @Override
    public boolean onQueryTextSubmit(String s) {
        if (!search.isIconified()){
            search.setIconified(true);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (TextUtils.isEmpty(s)){
            adapter.resetList(myUserList);
        }else {
            final List<SendModel> filterModellist=filter(myUserList,s);
            adapter.updateList(filterModellist);
        }
        return false;
    }
    private List<SendModel> filter(List<SendModel> sendModels,String query){
        query=query.toLowerCase();
        final List<SendModel> filtersendList=new ArrayList<>();
        for (SendModel model:sendModels){
            final String no=model.getEmail().toLowerCase();
            final String name=model.getName().toLowerCase();
            if (no.contains(query) || name.contains(query) ){
                filtersendList.add(model);
            }
        }
        return filtersendList;
    }

    @Override
    public void OnItemClick(View v, int position) {
        progressDialog.show();
        uploadFile(usename,firebaseUser.getEmail(),adapter.getSendlist().get(position).getUser_id());
    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }
    private String getFileExtention(Uri uri){
        ContentResolver cs=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cs.getType(uri));
    }

    private void uploadFile(String name, final String email, final String key){
        final String memail=email;
        final String mkey=key;
        final String mName=name;
        if (screenshotUri != null)
        {
            final StorageReference fileref=sReference.child(System.currentTimeMillis()+"."+getFileExtention(screenshotUri));
            fileref.putFile(screenshotUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
            {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>()
            {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        Uri downloadUri = task.getResult();
                        Log.e(TAG, "then: " + downloadUri.toString());
                        UploadModel uploadModel=new UploadModel(mName,memail,mkey,downloadUri.toString());
                        uploadsreference.push().setValue(uploadModel);
                        progressDialog.dismiss();
                        try {
                            sendRequest(key,memail);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(Send.this,MainActivity.class);
                        startActivity(intent);
                    } else
                    {
                        Toast.makeText(Send.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendRequest(String token,String emai) throws JSONException {
        TOPIC = "/topics/"+token; //topic must match with what the receiver subscribed to
        NOTIFICATION_TITLE = "A NEW CARD RECEIVED FROM";
        NOTIFICATION_MESSAGE = emai;
        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        sendNotification(notification);
    }

    private void sendNotification(JSONObject notification) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i(TAG, "onResponse: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Send.this, "Request error", Toast.LENGTH_LONG).show();
                            Log.i(TAG, "onErrorResponse: Didn't work");
                        }
                    }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Authorization", serverKey);
                    params.put("Content-Type", contentType);
                    return params;
                }
            };
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }
}
