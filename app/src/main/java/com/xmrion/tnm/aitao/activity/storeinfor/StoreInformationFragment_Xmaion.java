package com.xmrion.tnm.aitao.activity.storeinfor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mingle.widget.LoadingView;
import com.xmrion.tnm.aitao.bmob.Store_Xmarion;
import com.xmrion.tnm.aitao.bmob.User_Xmarion;
import com.xmrion.tnm.aitao.utils.FileUtils_Xmarion;
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.editname.EditNameActivity_Xmarion;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;
import com.xmrion.tnm.aitao.utils.ToastUtils_Xmarion;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by 郑艳达 on 2017/7/10.
 * StoreInformationFragment
 */

public class StoreInformationFragment_Xmaion extends Fragment {

    private Bitmap mBitmap;
    private String url;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;

    public static String storeName;

    public static boolean isCreateStore = false;

    private int type;

    private MyApplication_Xmarion app;

    @BindView(R.id.id_img_storeinfor_head)
    public ImageView headImg;

    @BindView(R.id.id_load_storeinfor)
    public LoadingView loadingView;

    @OnClick(R.id.id_btn_storeinfor_back)
    public void back() {
        getActivity().finish();
    }

    @BindView(R.id.id_tv_storeinfor_title)
    public TextView titleText;

    @BindView(R.id.id_tv_storeinfor_name)
    public TextView nameText;

    @BindView(R.id.id_tv_storeinfor_information)
    public TextView inforText;

    @BindView(R.id.id_tv_storeinfor_introduction)
    public TextView introText;

    @BindView(R.id.id_et_storeinfor_information)
    public EditText inforEt;

    @BindView(R.id.id_et_storeinfor_introduction)
    public EditText introEt;

    @BindView(R.id.id_btn_storeinfor_confirm)
    public TextView confirmBtn;

    @OnClick(R.id.id_btn_storeinfor_confirm)
    public void confirm() {
        String infor = inforEt.getText().toString();
        String intro = introEt.getText().toString();
        if (infor.equals("")) {
            ToastUtils_Xmarion.showToast("需要输入店铺的有效信息！", getActivity());
            return;
        } else if (intro.equals("")) {
            ToastUtils_Xmarion.showToast("需要输入店铺的简介！", getActivity());
            return;
        }
        loadingView.setVisibility(View.VISIBLE);
        Store_Xmarion store_xmarion = new Store_Xmarion();
        store_xmarion.setName(storeName);
        store_xmarion.setInformation(infor);
        store_xmarion.setIntroduction(intro);

        //1.创建店铺 3.修改店铺信息
        if (type == 1) {
            isCreateStore = true;
            store_xmarion.setIncome((double) 0);

            store_xmarion.setUserId(app.getUser().getObjectId());
            store_xmarion.save(new SaveListener<String>() {
                @Override
                public void done(String id, BmobException e) {
                    final String s = e == null ? "创建成功" : "创建失败";
                    if (e == null) {
                        app.getUser().setStoreId(id);
                        User_Xmarion u = new User_Xmarion();
                        u.setStoreId(id);
                        u.update(app.getUser().getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            getActivity().finish();
                                            ToastUtils_Xmarion.showToast(s, getActivity());
                                        }
                                    });
                                } else {
                                    Log.e("storeId绑定user:", "失败");
                                }
                            }
                        });
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils_Xmarion.showToast(s, getActivity());
                            }
                        });
                    }
                }
            });
        } else if (type == 3) {
            try {
                upLoadImg();
            } catch (IOException e) {
                e.printStackTrace();
            }
            store_xmarion.update(app.getUser().getStoreId(), new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                StoreInformationFragment_Xmaion.isCreateStore = true;
                                ToastUtils_Xmarion.showToast("修改成功！", getActivity());
                                back();
                            }
                        });
                    } else {
                        Log.e("修改店铺失败", e.toString());
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtils_Xmarion.showToast("修改失败！", getActivity());
                                back();
                            }
                        });
                    }
                }
            });
        }

    }

    @BindView(R.id.id_btn_storeinfor_edithead)
    public ImageView editheadBtn;

    @OnClick(R.id.id_btn_storeinfor_edithead)
    public void editHead() {
        showChoosePicDialog();
    }

    @BindView(R.id.id_btn_storeinfor_editname)
    public ImageView editnameBtn;

    @OnClick((R.id.id_btn_storeinfor_editname))
    public void editName() {
        Intent i = new Intent(getActivity(), EditNameActivity_Xmarion.class);
        startActivity(i);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_storeinfor, container, false);

        ButterKnife.bind(this, v);
        initialView();
        return v;
    }

    @SuppressLint("SetTextI18n")
    private void initialView() {
        app = (MyApplication_Xmarion) getActivity().getApplication();
        loadingView.setVisibility(View.GONE);
        Intent intent = getActivity().getIntent();

        //1 卖家创建 2 买家 3 卖家修改
        type = intent.getIntExtra("type", 1);
        if (type == 1) {
            inforText.setVisibility(View.GONE);
            introText.setVisibility(View.GONE);

            confirmBtn.setVisibility(View.VISIBLE);
            titleText.setText("编辑店铺资料");

            storeName = app.getUser().getUsername() + "的店铺";
            nameText.setText(storeName);
        } else if (type == 2) {
            storeName = intent.getStringExtra("name");
            String infor = intent.getStringExtra("information");
            String intro = intent.getStringExtra("introduction");
            url = intent.getStringExtra("headImgUrl");
            nameText.setText(storeName);
            inforText.setText(infor);
            introText.setText(intro);

            inforEt.setVisibility(View.GONE);
            introEt.setVisibility(View.GONE);
            confirmBtn.setVisibility(View.GONE);
            editheadBtn.setVisibility(View.GONE);
            editnameBtn.setVisibility(View.GONE);
            setHeadImg();
        } else if (type == 3) {
            url = intent.getStringExtra("headImgUrl");
            inforText.setVisibility(View.GONE);
            introText.setVisibility(View.GONE);
            confirmBtn.setVisibility(View.VISIBLE);
            titleText.setText("编辑店铺资料");
            storeName = intent.getStringExtra("name");
            nameText.setText(storeName);
            String infor = intent.getStringExtra("information");
            String intro = intent.getStringExtra("introduction");
            inforEt.setText(infor);
            introEt.setText(intro);
            setHeadImg();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        nameText.setText(storeName);
        setHeadImg();
    }


    private void setHeadImg() {
        //设置头像
        if (url != null) {
            if (!url.equals("")) {
                Glide.with(this).
                        load(url).
                        placeholder(R.drawable.img_defaultavatar).
                        dontAnimate().
                        into(headImg);
            }
        }
    }

    protected void showChoosePicDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("添加图片");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "temp_image.jpg"));
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            return;
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            mBitmap = extras.getParcelable("data");
            headImg.setImageBitmap(mBitmap);//显示图片
        } else {
            Log.e("删除原来成功", "失败");
        }
    }

    private void upLoadImg() throws IOException {
        if (mBitmap == null) {
            return;
        }
        //创建新的文件
        File f = FileUtils_Xmarion.saveFile(mBitmap, Environment.getExternalStorageDirectory().getPath()
                , storeName + "_head.img");
        final BmobFile bf = new BmobFile(f);
        //上传图片到服务器
        bf.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    url = bf.getFileUrl();
                    Log.e("头像上传", "成功");
                    Store_Xmarion store = new Store_Xmarion();
                    store.setHeadImgURL(bf.getFileUrl());
                    store.update(app.getUser().getStoreId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Log.e("图片绑定", "成功");
                            }
                        }
                    });
                }
            }
        });
    }
}
