package com.planradar.assessment.utils.views;

import android.content.Context;
import android.graphics.Typeface;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.planradar.assessment.R;


public class progressLoading {
    static KProgressHUD kProgressHUD;

    public static void CreateProgress(Context context) {


        if (kProgressHUD!=null&& kProgressHUD.isShowing()){

            kProgressHUD
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }else {
            kProgressHUD = new KProgressHUD(context);
            kProgressHUD
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setCancellable(true)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();
        }

    }

    public static void HideProgress() {
        if (kProgressHUD==null) {

        }else {
            kProgressHUD.dismiss();
        }


    }


//    public static void CreatePoPup(Context context, String Title, String Message) {
//        final Typeface face = Typeface.createFromAsset(context.getAssets(),"ptsanswebbold.ttf");
//        final Typeface face1 = Typeface.createFromAsset(context.getAssets(),"ptsanswebregular.ttf");
//        new SmartDialogBuilder(context)
//                .setTitle(Title)
//                .setCancalable(true)
//                .setTitleFont(face1)
//                .setTitleFont(face)
//                .setNegativeButtonHide(true) //hide cancel button
//                .setPositiveButton(context.getResources().getString(R.string.Ok), new SmartDialogClickListener() {
//                    @Override
//                    public void onClick(SmartDialog smartDialog) {
//                        smartDialog.dismiss();
//                    }
//                }).build().show();
//    }
//


}
