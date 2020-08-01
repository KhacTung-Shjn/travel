package com.example.mytravel.ui.auth.updateprofile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.user.UserInformation;
import com.example.mytravel.utils.CommonUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.mytravel.utils.ConstApp.KEY_USER_INFORMATION;
import static com.example.mytravel.utils.ConstApp.REQUEST_CODE_CHOOSE_PHOTO_GALLERY;
import static com.example.mytravel.utils.ConstApp.REQUEST_CODE_TAKE_PHOTO;
import static com.example.mytravel.utils.ConstApp.REQUEST_PERMISSION_CODE;
import static com.example.mytravel.utils.ConstApp.REQUEST_PERMISSION_CODE_CAMERA;

public class UpdateUpdateProfileFragment extends BaseFragment implements UpdateProfileFrMvpView, View.OnClickListener {
    public static final String TAG = UpdateUpdateProfileFragment.class.getSimpleName();

    private UpdateProfileFrMvpPresenter presenter;

    @BindView(R.id.ivAvatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tvNameUser)
    TextView tvNameUser;
    @BindView(R.id.tvEmailUser)
    TextView tvEmailUser;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.rbMale)
    RadioButton rbMale;
    @BindView(R.id.rbFeMale)
    RadioButton rbFeMale;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.etAddress)
    EditText etAddress;
    @BindView(R.id.tvChooseBirth)
    TextView tvChooseBirth;
    @BindView(R.id.btnUpdate)
    Button btnUpdate;

    private UserInformation userInformation;
    private String birth;
    private Calendar calendar;
    private int mDay, mMonth, mYear;
    private String urlImage;

    public static UpdateUpdateProfileFragment newInstance(UserInformation userInformation) {
        UpdateUpdateProfileFragment updateProfileFragment = new UpdateUpdateProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_USER_INFORMATION, userInformation);
        updateProfileFragment.setArguments(bundle);
        return updateProfileFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UpdateProfileFrPresenter(this);
        calendar = Calendar.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_update_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            ImageView ivBack = getActivity().findViewById(R.id.ivBack);
            ivBack.setOnClickListener(this);
        }
        if (getArguments() != null) {
            userInformation = getArguments().getParcelable(KEY_USER_INFORMATION);
        }
        if (calendar != null) {
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
            mMonth = calendar.get(Calendar.MONTH);
            mYear = calendar.get(Calendar.YEAR);
        }

        if (userInformation != null) {
            if (!TextUtils.isEmpty(userInformation.getEmail())) {
                etEmail.setText(userInformation.getEmail());
                tvEmailUser.setText(userInformation.getEmail());
            }
            if (!TextUtils.isEmpty(userInformation.getName())) {
                tvNameUser.setText(userInformation.getName());
                etName.setText(userInformation.getName());
            }
            if (!TextUtils.isEmpty(userInformation.getPhone()) && userInformation.getPhone() != null) {
                etPhone.setText(userInformation.getPhone());
            }
            if (!TextUtils.isEmpty(userInformation.getAddress()) && userInformation.getAddress() != null) {
                etAddress.setText(userInformation.getAddress());
            }
            if (!TextUtils.isEmpty(userInformation.getBirth()) && userInformation.getBirth() != null) {
                tvChooseBirth.setText(userInformation.getBirth());
            }
            if (!TextUtils.isEmpty(userInformation.getAvatar()) && userInformation.getAvatar() != null) {
                try {
                    Bitmap bitmap = CommonUtils.StringToBitMap(userInformation.getAvatar());
                    ivAvatar.setImageBitmap(bitmap);
                } catch (Exception e) {
                    Toast.makeText(getContext(), getString(R.string.msg_error_unknown), Toast.LENGTH_SHORT).show();
                }
            }
            if (userInformation.getGender() == 0) {
                rbFeMale.setChecked(true);
            } else {
                rbMale.setChecked(true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null) {
            if (getActivity().getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }

    @OnClick(R.id.btnUpdate)
    public void onClickUpdate() {
        if (TextUtils.isEmpty(urlImage) || urlImage == null) {
            urlImage = getAppDataManager().getUserInformation().getAvatar();
        }
        if (birth == null || TextUtils.isEmpty(birth)) {
            birth = getAppDataManager().getUserInformation().getBirth();
        }
        presenter.onClickUpdateProfile(etName.getText().toString(), etEmail.getText().toString(),
                rbMale.isChecked(), rbFeMale.isChecked(), etPhone.getText().toString(),
                etAddress.getText().toString(), birth, userInformation, urlImage);
    }

    @SuppressLint("DefaultLocale")

    @OnClick(R.id.tvChooseBirth)
    public void onClickChooseBirth() {
        if (getContext() != null) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (view, year, month, dayOfMonth) -> {
                birth = String.format("%d/%d/%d", dayOfMonth, (month + 1), year);
                tvChooseBirth.setText(birth);
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
    }

    @Override
    public void focusEdit(int i) {
        if (getContext() != null) {
            switch (i) {
                case 0: {
                    CommonUtils.showSoftInput(etName, getContext());
                    break;
                }
                case 1: {
                    CommonUtils.showSoftInput(etEmail, getContext());
                    break;
                }
            }
        }
    }

    @Override
    public void updateProfileSuccess(UserInformation userInformation) {
        if (getActivity() != null) {
            Intent intent = new Intent();
            intent.putExtra(KEY_USER_INFORMATION, userInformation);
            getActivity().setResult(RESULT_OK, intent);
            getActivity().finish();
        }
    }

    @OnClick(R.id.ivAvatar)
    public void onClickSetAvatar(View view) {
        final CharSequence[] options = {getString(R.string.text_take_photo), getString(R.string.text_choose_from_gallery), getString(R.string.text_cancel)};
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(getString(R.string.text_choose_your_profile_picture));
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals(getString(R.string.text_take_photo)) && getContext() != null) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CODE_CAMERA);
                } else {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, REQUEST_CODE_TAKE_PHOTO);
                }
            } else if (options[item].equals(getString(R.string.text_choose_from_gallery))) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK);
                pickPhoto.setType("image/*");
                startActivityForResult(pickPhoto, REQUEST_CODE_CHOOSE_PHOTO_GALLERY);
            } else if (options[item].equals(getString(R.string.text_cancel))) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PHOTO: {
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap selectedImage = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                    if (selectedImage != null) {
                        ivAvatar.setImageBitmap(selectedImage);
                        selectedImage = CommonUtils.getResizedBitmap(selectedImage, 200);
                        urlImage = CommonUtils.BitMapToString(selectedImage);
                    }
                }
                break;
            }
            case REQUEST_CODE_CHOOSE_PHOTO_GALLERY: {
                if (resultCode == RESULT_OK && data != null && getActivity() != null) {
                    try {
                        final Uri imageUri = data.getData();
                        final InputStream imageStream;
                        if (imageUri != null) {
                            imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                            Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            ivAvatar.setImageBitmap(selectedImage);
                            selectedImage = CommonUtils.getResizedBitmap(selectedImage, 200);
                            urlImage = CommonUtils.BitMapToString(selectedImage);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), getString(R.string.msg_error_catch_choose_image), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.msg_error_choose_image_gallery), Toast.LENGTH_LONG).show();
                }
                break;
            }
            case REQUEST_PERMISSION_CODE_CAMERA: {
                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, REQUEST_CODE_TAKE_PHOTO);
                break;
            }
        }
    }
}
