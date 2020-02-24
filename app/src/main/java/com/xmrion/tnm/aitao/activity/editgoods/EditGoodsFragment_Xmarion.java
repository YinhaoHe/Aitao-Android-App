package com.xmrion.tnm.aitao.activity.editgoods;

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
import com.xmrion.tnm.aitao.R;
import com.xmrion.tnm.aitao.activity.index.MainActivity_Xmarion;
import com.xmrion.tnm.aitao.application.MyApplication_Xmarion;
import com.xmrion.tnm.aitao.bmob.Goods_Xmarion;
import com.xmrion.tnm.aitao.utils.FileUtils_Xmarion;
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
 * Created by 郑艳达 on 2017/7/12.
 * 编辑货物、创建货物信息
 */

public class EditGoodsFragment_Xmarion extends Fragment {

    private String url;

    private Bitmap mBitmap;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;

    private Goods_Xmarion goods;

    private int type;

    private String goodsId;

    private MyApplication_Xmarion app;

    @BindView(R.id.id_load_editgoods)
    public LoadingView loadingView;

    @OnClick(R.id.id_btn_editgoods_back)
    public void back() {
        getActivity().finish();
    }

    @BindView(R.id.id_tv_editgoods_title)
    public TextView titleText;

    @BindView(R.id.id_btn_editgoods_confirm)
    public TextView confirmText;

    @OnClick(R.id.id_btn_editgoods_confirm)
    public void confirm() {
        app = (MyApplication_Xmarion) getActivity().getApplication();
        String name = nameEt.getText().toString();
        String intro = introEt.getText().toString();
        String priceS = priceEt.getText().toString();
        String numberS = numberEt.getText().toString();

        if (name.equals("")) {
            ToastUtils_Xmarion.showToast("请输入商品名称！", getActivity());
            return;
        } else if (intro.equals("")) {
            ToastUtils_Xmarion.showToast("请输入商品描述！", getActivity());
            return;
        } else if (priceS.equals("")) {
            ToastUtils_Xmarion.showToast("请输入商品价格！", getActivity());
            return;
        } else if (numberS.equals("")) {
            ToastUtils_Xmarion.showToast("请输入商品库存数量！", getActivity());
            return;
        }

        goods = new Goods_Xmarion();
        goods.setIntroduction(intro);
        goods.setName(name);
        goods.setPrice(Double.parseDouble(priceS));
        goods.setNumber(Integer.parseInt(numberS));

        loadingView.setVisibility(View.VISIBLE);
        try {
            dealWithGoods(type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BindView(R.id.id_et_editgoods_name)
    public EditText nameEt;

    @BindView(R.id.id_et_editgoods_intro)
    public EditText introEt;

    @BindView(R.id.id_img_editgoods_picture)
    public ImageView pictureImg;

    @OnClick(R.id.id_img_editgoods_picture)
    public void editImg2() {
        showChoosePicDialog();
    }

    @BindView(R.id.id_img_editgoods_picture_static)
    public ImageView pictureBtn;

    @OnClick(R.id.id_img_editgoods_picture_static)
    public void editImg() {
        showChoosePicDialog();
    }

    @BindView(R.id.id_et_editgoods_price)
    public EditText priceEt;

    @BindView(R.id.id_et_editgoods_number)
    public EditText numberEt;

    @BindView(R.id.id_btn_editgoods_del)
    public TextView delText;

    @OnClick(R.id.id_btn_editgoods_del)
    public void del() {
        loadingView.setVisibility(View.VISIBLE);
        Goods_Xmarion goods = new Goods_Xmarion();
        goods.delete(goodsId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingView.setVisibility(View.GONE);
                            ToastUtils_Xmarion.showToast("删除成功！", getActivity());
                            startActivity(new Intent(getActivity(), MainActivity_Xmarion.class));
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingView.setVisibility(View.GONE);
                            ToastUtils_Xmarion.showToast("删除失败！", getActivity());
                            startActivity(new Intent(getActivity(), MainActivity_Xmarion.class));
                        }
                    });
                    Log.e("删除失败？", e.toString());
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_editgoods, container, false);
        ButterKnife.bind(this, v);

        initialView();
        return v;
    }

    private void initialView() {
        loadingView.setVisibility(View.GONE);
        Intent i = getActivity().getIntent();
        type = i.getIntExtra("type", 0);
        //1.新建商品 2.修改商品信息
        if (type == 1) {
            titleText.setText("新建商品");
            confirmText.setText("新建");
            delText.setVisibility(View.GONE);
        } else if (type == 2) {
            url = i.getStringExtra("headImgUrl");
            pictureBtn.setVisibility(View.GONE);
            if (url != null) {
                if (!url.equals("")) {
                    Glide.with(this)
                            .load(url)
                            .placeholder(R.drawable.img_defaultavatar)
                            .crossFade()
                            .into(pictureImg);
                }
            }

            titleText.setText("编辑商品");
            confirmText.setText("确定");
            delText.setVisibility(View.VISIBLE);
            String name = i.getStringExtra("name");
            String intor = i.getStringExtra("introduction");
            double price = i.getDoubleExtra("price", (double) 0);
            String pS = price + "";
            int number = i.getIntExtra("number", 0);
            String nS = number + "";
            goodsId = i.getStringExtra("goodsId");

            nameEt.setText(name);
            introEt.setText(intor);
            priceEt.setText(pS);
            numberEt.setText(nS);
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
            pictureImg.setImageBitmap(mBitmap);//显示图片
            pictureBtn.setVisibility(View.GONE);
        } else {
            Log.e("删除原来成功", "失败");
        }
    }

    private void createNewGoods() {
        goods.setSellNum(0);
        goods.setStoreId(app.getUser().getStoreId());
        goods.save(new SaveListener<String>() {
            @Override
            public void done(String id, BmobException e) {
                if (e == null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils_Xmarion.showToast("新建商品成功！", getActivity());
                            back();
                        }
                    });
                    Log.e("新建商品:", "成功");
                } else {
                    Log.e("新建商品:", e.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils_Xmarion.showToast("新建商品失败！", getActivity());
                            back();
                        }
                    });
                }
            }
        });
    }

    private void updateGoods() {
        goods.update(goodsId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils_Xmarion.showToast("修改成功！", getActivity());
                            startActivity(new Intent(getActivity(), MainActivity_Xmarion.class));
                        }
                    });
                    Log.e("新建商品:", "成功");
                } else {
                    Log.e("新建商品:", e.toString());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtils_Xmarion.showToast("修改失败！", getActivity());
                            startActivity(new Intent(getActivity(), MainActivity_Xmarion.class));

                        }
                    });
                }
            }
        });
    }

    private void dealWithGoods(final int t) throws IOException {
        if (mBitmap == null) {
            if (t == 1) {
                goods.setGoodsImgUrl(null);
                createNewGoods();
            } else if (t == 2) {
                updateGoods();
            }
            return;
        }
        //创建新的文件
        File f = FileUtils_Xmarion.saveFile(mBitmap, Environment.getExternalStorageDirectory().getPath()
                , nameEt.getText().toString() + "_head.img");
        final BmobFile bf = new BmobFile(f);
        //上传图片到服务器
        bf.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    url = bf.getFileUrl();
                    goods.setGoodsImgUrl(url);
                    Log.e("头像上传", "成功");
                    if (t == 1) {
                        createNewGoods();
                    } else if (t == 2) {
                        updateGoods();
                    }
                }
            }
        });
    }
}
