// ColorDialogFragment.java
// Allows user to set the drawing color on the DoodleView
package com.deitel.doodlz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

// class for the Select Color dialog
public class BackgroundColorFragment extends DialogFragment
{
    private SeekBar bgAlphaSeekBar;
    private SeekBar bgRedSeekBar;
    private SeekBar bgGreenSeekBar;
    private SeekBar bgBlueSeekBar;
    private View bgColorView;
    private int backgroundColor;

    // create an AlertDialog and return it
    @Override
    public Dialog onCreateDialog(Bundle bundle)
    {
        // create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View colorDialogView = getActivity().getLayoutInflater().inflate(R.layout.fragment_background, null);
        builder.setView(colorDialogView); // add GUI to dialog

        // set the AlertDialog's message
        builder.setTitle(R.string.title_background_color_dialog);

        // get the color SeekBars and set their onChange listeners
        bgAlphaSeekBar = (SeekBar) colorDialogView.findViewById(R.id.bgalphaSeekBar);
        bgRedSeekBar = (SeekBar) colorDialogView.findViewById(R.id.bgredSeekBar);
        bgGreenSeekBar = (SeekBar) colorDialogView.findViewById(R.id.bggreenSeekBar);
        bgBlueSeekBar = (SeekBar) colorDialogView.findViewById(R.id.bgblueSeekBar);
        bgColorView = colorDialogView.findViewById(R.id.colorView);

        // register SeekBar event listeners
        bgAlphaSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        bgRedSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        bgGreenSeekBar.setOnSeekBarChangeListener(colorChangedListener);
        bgBlueSeekBar.setOnSeekBarChangeListener(colorChangedListener);

        // use current drawing color to set SeekBar values
        final DoodleView doodleView = getDoodleFragment().getDoodleView();
        backgroundColor = doodleView.getBackgroundColor();
        bgAlphaSeekBar.setProgress(Color.alpha(backgroundColor));
        bgRedSeekBar.setProgress(Color.red(backgroundColor));
        bgGreenSeekBar.setProgress(Color.green(backgroundColor));
        bgBlueSeekBar.setProgress(Color.blue(backgroundColor));

        // add Set Color Button
        builder.setPositiveButton(R.string.button_set_color, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                doodleView.setBackgroundColor(backgroundColor);
            }
        });

        return builder.create(); // return dialog
    }

    // gets a reference to the MainActivityFragment
    private MainActivityFragment getDoodleFragment()
    {
        return (MainActivityFragment) getFragmentManager().findFragmentById(R.id.doodleFragment);
    }

    // tell MainActivityFragment that dialog is now displayed
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(true);
    }

    // tell MainActivityFragment that dialog is no longer displayed
    @Override
    public void onDetach()
    {
        super.onDetach();
        MainActivityFragment fragment = getDoodleFragment();

        if (fragment != null)
            fragment.setDialogOnScreen(false);
    }

    // OnSeekBarChangeListener for the SeekBars in the color dialog
    private final OnSeekBarChangeListener colorChangedListener = new OnSeekBarChangeListener()
    {
        // display the updated color
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            if (fromUser) // user, not program, changed SeekBar progress
                backgroundColor = Color.argb(bgAlphaSeekBar.getProgress(), bgRedSeekBar.getProgress(), bgGreenSeekBar.getProgress(), bgBlueSeekBar.getProgress());
            bgColorView.setBackgroundColor(backgroundColor);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {} // required

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {} // required
    };
}
