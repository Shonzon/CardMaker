package com.example.cardmaker.ImageEdit;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cardmaker.MyCardEdit.McEdit2;
import com.example.cardmaker.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class EditLogo2 extends AppCompatActivity {

    private static final String TAG = "EditImage";

    private static int PReqCode = 1;
    private static int REQUESTCODE = 1;
    private Uri pickedLogoUri;
    String photoStringLink = "";
    String tempID = "";

    private ImageView logo;
    private Button editLogo,updateLogo;
    private ProgressBar mProgressBar;

    Context mContext;

    private UploadTask uploadTask;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_logo);

        mContext = EditLogo2.this;

        logo = findViewById(R.id.logoImage);
        editLogo = findViewById(R.id.editLogo);
        updateLogo = findViewById(R.id.updateLogo);
        mProgressBar = findViewById(R.id.progress_bar);

        mStorageRef = FirebaseStorage.getInstance().getReference("templatelogo");


        String getLogo = getIntent().getExtras().getString("logo");
        Glide.with(this).load(getLogo).into(logo);
        //String ImageID = getIntent().getExtras().getString("temp_id");
        //tempID = ImageID;
        //Log.d(TAG, "onCreate: print" + tempID);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 22){
                    RequestForPermission();
                }
                else {
                    openGallery();
                }
            }
        });

        editLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (uploadTask != null && uploadTask.isInProgress()){
                    Toast.makeText(mContext,"Upload in progress",Toast.LENGTH_SHORT).show();
                }else {
                    uploadFile();
                }
            }
        });

        updateLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference tempRef = FirebaseDatabase.getInstance().getReference("usertemplate")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("three");
                Log.d(TAG, "onClick: " + photoStringLink);
                if (photoStringLink != null){
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("logo", photoStringLink);
                    tempRef.updateChildren(result);
                    Log.d(TAG, "onClick: Temp.....LOGO " + result);
                }
                Intent intent = new Intent(mContext, McEdit2.class);
                intent.putExtra("logoString",photoStringLink);
                startActivity(intent);

                Toast.makeText(mContext,"Logo Updated Successfully",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (pickedLogoUri != null){
            final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(pickedLogoUri));

            uploadTask = fileReference.putFile(pickedLogoUri);


            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(0);
                            }
                        },5000);

                        Uri downloadUri = task.getResult();
                        Toast.makeText(mContext, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                        if (downloadUri != null) {
                            photoStringLink = downloadUri.toString(); //YOU WILL GET THE DOWNLOAD URL HERE !!!!
                            Log.d(TAG, "onComplete: LLLLLLLLLL" + photoStringLink);

                        }
                    } else {
                        // Handle failures
                        // ...
                        Toast.makeText(mContext, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100*taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                    mProgressBar.setProgress((int) progress);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(mContext, e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(mContext,"Upload is paused",Toast.LENGTH_SHORT).show();
                }
            });


        }else {
            Toast.makeText(mContext,"No file selected",Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        //gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(gallery,REQUESTCODE);
    }

    private void RequestForPermission() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(EditLogo2.this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                Toast.makeText(mContext,"Accept Permissions for further Proceed",Toast.LENGTH_SHORT).show();
            }
            else {
                ActivityCompat.requestPermissions(EditLogo2.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PReqCode);
            }
        }else {
            openGallery();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUESTCODE && data != null){
            pickedLogoUri = data.getData();
            Glide.with(mContext).load(pickedLogoUri).into(logo);
            //logo.setImageURI(pickedLogoUri);
        }
    }
}
