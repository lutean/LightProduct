package com.prepod.lightproduct.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.prepod.lightproduct.LightProduct;
import com.prepod.lightproduct.ProductApiInterface;
import com.prepod.lightproduct.R;
import com.prepod.lightproduct.Utils;
import com.prepod.lightproduct.containers.Review;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoteDialogFragment extends DialogFragment {



    private RatingBar ratingBar;
    private TextView textView;
    private NoticeDialogListener mListener;


    public interface NoticeDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String message);
        public void onDialogNegativeClick(DialogFragment dialog);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NoticeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NoticeDialogListener");
        }

    }

        @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.dialog_vote, null);
        builder.setView(view)
                .setTitle(getString(R.string.rating))
                .setPositiveButton(getString(R.string.send), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = (EditText) view.findViewById(R.id.review);
                        RatingBar ratingBar = (RatingBar) view.findViewById(R.id.ratingBar2);
                        int rate = (int) ratingBar.getRating();
                        String str = editText.getText().toString();

                        ProductApiInterface service = LightProduct.getInstance().getRetrofitClient().create(ProductApiInterface.class);

                        int productId = getActivity().getIntent().getExtras().getInt("product_id", 0);
                        Review review = new Review();
                        review.setText(str);
                        review.setRate(rate);
                        String token = Utils.getToken(getActivity());
                        Call<Review> call = service.sendProductReview("Token " + token, "" + productId, review);
                        call.enqueue(new Callback<Review>() {
                            @Override
                            public void onResponse(Call<Review> call, Response<Review> response) {
                                Log.v("", "");
                                String str = "";
                                if (response.body()==null){
                                    str = response.message();
                                    Log.e("My", str);
                                } else str = "Posted";
                                mListener.onDialogPositiveClick(VoteDialogFragment.this, str);
                            }
                            @Override
                            public void onFailure(Call<Review> call, Throwable t) {
                                Log.v("", "");
                            }
                        });
                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        VoteDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();

    }
}