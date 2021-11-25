package com.with.us;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.with.us.models.PostDetail;
import com.with.us.models.UserInfo;
import com.with.us.services.auxiliary.RequestHelper;
import com.with.us.utils.FirebaseHelper;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostFormActivity extends AppCompatActivity {

    private static final String TAG = "PostFormActivity";
    Button activity_post_form_btn;
    ImageButton activity_post_form_image_btn;
    EditText activity_post_form_title, activity_post_form_content;
    String displayName, category;
    FirebaseUser user;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_form);

        activity_post_form_title = findViewById(R.id.activity_post_form_title);
        activity_post_form_content = findViewById(R.id.activity_post_form_content);
        activity_post_form_btn = findViewById(R.id.activity_post_form_btn);
        activity_post_form_image_btn = findViewById(R.id.activity_post_form_image_btn);
        category = getIntent().getStringExtra("category");
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            getUserData();
        }

        ActivityResultLauncher<String> launcher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();

                        final StorageReference ref = storageRef.child("test.jpg");
                        ref.putFile(uri).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                double progress = (100.0 * snapshot.getBytesTransferred())
                                        / snapshot.getTotalByteCount();
                                ProgressBar progressBar = findViewById(R.id.progressbar);
                                progressBar.setVisibility(View.VISIBLE);
                                progressBar.setProgress((int) progress);
                                Log.d(TAG, "Upload is " + progress + "% done");
                            }
                        }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful()) {
                                    throw task.getException();
                                }
                                return ref.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downloadUri = task.getResult();
                                    imageUri = downloadUri;
                                    ProgressBar progressBar = findViewById(R.id.progressbar);
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(PostFormActivity.this, "사진을 업로드하였습니다.", Toast.LENGTH_SHORT).show();
                                    // TODO: Make Thumbnail
                                } else {
                                    imageUri = Uri.parse("None");
                                }
                            }
                        });

                    }
                });

        activity_post_form_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });

        activity_post_form_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RequestHelper.getPostAPI()
                        .createPostDetail("Bearer " + FirebaseHelper.getAccessToken(PostFormActivity.this),
                                new PostDetail(user.getUid(), category, displayName,
                                        activity_post_form_title.getText().toString(),
                                        activity_post_form_content.getText().toString(), 0, 0, imageUri.toString()))
                        .enqueue(new Callback<List<PostDetail>>() {
                            @Override
                            public void onResponse(Call<List<PostDetail>> call, Response<List<PostDetail>> response) {
                                if (response.isSuccessful()) {

                                }
                            }

                            @Override
                            public void onFailure(Call<List<PostDetail>> call, Throwable t) {

                            }
                        });
                Toast.makeText(PostFormActivity.this, "게시글을 작성하였습니다.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void getUserData() {
        RequestHelper.getUserAPI().getUserInfo("Bearer " + FirebaseHelper.getAccessToken(this))
                .enqueue(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if (response.isSuccessful()) {
                            displayName = response.body().displayName;
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        Log.e(TAG, t.getMessage());
                    }
                });
    }
}
