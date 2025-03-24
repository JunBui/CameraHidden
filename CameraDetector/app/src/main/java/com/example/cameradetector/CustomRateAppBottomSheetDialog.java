package com.example.cameradetector;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.example.cameradetector.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CustomRateAppBottomSheetDialog extends BottomSheetDialogFragment {
    private boolean canSubmit =false;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialogInterface -> {
            View bottomSheet = dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                bottomSheet.setBackgroundResource(R.drawable.bottom_sheet_background);
            }
        });
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_rate_dialog, container, false);

        // Correct way to find RatingBar
        RatingBar ratingBar = view.findViewById(R.id.ratingBar);
        CardView btnSubmit = view.findViewById(R.id.btnSubmit);
        View btnCancel = view.findViewById(R.id.btnCancel);
        ImageView tutorialArrow = view.findViewById(R.id.tutorialArrow);
        PlayTutorialAnim(tutorialArrow);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                // This is called when the rating changes
                if (fromUser) {
                    btnSubmit.setCardBackgroundColor(getResources().getColor(R.color.blue));
                    canSubmit = true;
                }
            }
        });

        setCancelable(false);
        btnCancel.setOnClickListener(v -> dismiss());
        // Handle Submit Button Click
        btnSubmit.setOnClickListener(v -> {
            if(canSubmit == false)
                return;
            float rating = ratingBar.getRating();
            if (rating >= 5) {
                // Redirect to Google Play Store
                openPlayStore();
            } else {
                Toast.makeText(requireContext(), "Thank you for your rating!", Toast.LENGTH_SHORT).show();
            }
            dismiss();
        });
        return view;
    }
    private void openPlayStore() {
//        String appPackageName = getPackageName();
//        try {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
//        } catch (android.content.ActivityNotFoundException e) {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
//        }
    }
    public static void createRateDialog(FragmentManager fragmentManager)
    {
        CustomRateAppBottomSheetDialog bottomSheetDialog = new CustomRateAppBottomSheetDialog();
        // Show Bottom Sheet
        bottomSheetDialog.show(fragmentManager, "CustomBottomSheet");
    }
    private void PlayTutorialAnim(View view)
    {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1f, 1.5f); // Scale horizontally
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1f, 1.5f); // Scale vertically
        ObjectAnimator moveRight = ObjectAnimator.ofFloat(view, "translationX", 0f, 30f); // Move right
        ObjectAnimator moveTop = ObjectAnimator.ofFloat(view, "translationY", 0f, -30f); // Move top (negative Y for upwards movement)
        ObjectAnimator rotate = ObjectAnimator.ofFloat(view, "rotation", 0f, -15f); // Rotate slightly to the left

        // Set duration and repeat infinitely with reverse effect (scale up and down)
        long duration = 700;
        scaleX.setDuration(duration);  // Duration for scaling
        scaleY.setDuration(duration);  // Duration for scaling
        moveRight.setDuration(duration);
        moveTop.setDuration(duration);
        rotate.setDuration(duration);
        scaleX.setRepeatCount(ObjectAnimator.INFINITE);
        scaleY.setRepeatCount(ObjectAnimator.INFINITE);
        moveRight.setRepeatCount(ObjectAnimator.INFINITE);
        moveTop.setRepeatCount(ObjectAnimator.INFINITE);
        rotate.setRepeatCount(ObjectAnimator.INFINITE);
        scaleX.setRepeatMode(ObjectAnimator.REVERSE);
        scaleY.setRepeatMode(ObjectAnimator.REVERSE);
        moveRight.setRepeatMode(ObjectAnimator.REVERSE);
        moveTop.setRepeatMode(ObjectAnimator.REVERSE);
        rotate.setRepeatMode(ObjectAnimator.REVERSE);

        // Use LinearInterpolator for smooth continuous animation
        scaleX.setInterpolator(new LinearInterpolator());
        scaleY.setInterpolator(new LinearInterpolator());
        moveRight.setInterpolator(new LinearInterpolator());
        moveTop.setInterpolator(new LinearInterpolator());
        rotate.setInterpolator(new LinearInterpolator());

        // Start the animations
        scaleX.start();
        scaleY.start();
        moveRight.start();
        moveTop.start();
        rotate.start();
    }
}
