package com.argentina.salarycalculator.calculadoradesueldo;

import android.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.TextView;

import com.argentina.salarycalculator.calculadoradesueldo.activity.MainActivity;
import com.argentina.salarycalculator.calculadoradesueldo.fragment.PlaceholderFragment;

/**
 * Created by Matias on 23/03/2015.
 */
public class MainToolbarFragmentTest extends
        ActivityInstrumentationTestCase2<MainActivity> {


    private MainActivity mActivity;
    private TextView salario_bruto_textfield;
    private TextView salario_neto_textview;
    private Button calcular_button;
    private FragmentManager fragmentManager;



    public MainToolbarFragmentTest() {
        super("com.argentina.salarycalculator.calculadoradesueldo.activity",MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivity = getActivity();
        fragmentManager = mActivity.getFragmentManager();
    }

        /*

    public void testFragment() {
        PlaceholderFragment fragment = startFragment();

        assertNotNull(fragment.mFilterRecyclerView);
        assertEquals(View.GONE, fragment.mFilterRecyclerView.getVisibility());

        assertNotNull(fragment.mShowFilterToggle);
        assertEquals(View.VISIBLE, fragment.mShowFilterToggle.getVisibility());

    }



    public void testToggleFilterToggle() {
        final MainToolbarFragment fragment = startFragment();

        Runnable toggleRunnable = new Runnable() {
            @Override
            public void run() {
                fragment.mShowFilterToggle.toggle();
            }
        };

        assertFalse(fragment.mShowFilterToggle.isChecked());
        getActivity().runOnUiThread(toggleRunnable);

        try {
            Thread.sleep(10000);
        }
        catch (Exception e){};
        assertTrue(fragment.mShowFilterToggle.isChecked());
        assertEquals(View.VISIBLE, fragment.mFilterRecyclerView.getVisibility());

        getActivity().runOnUiThread(toggleRunnable);
        try {
            Thread.sleep(10000);
        }
        catch (Exception e){};
        assertFalse(fragment.mShowFilterToggle.isChecked());
        assertEquals(View.GONE, fragment.mFilterRecyclerView.getVisibility());
    }

    private PlaceholderFragment startFragment() {

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.fragment_container, new PlaceholderFragment(), TAG);
        transaction.commit();
        getInstrumentation().waitForIdleSync();

        MainToolbarFragment returnValue =
                (MainToolbarFragment) mFragmentManager.findFragmentByTag(TAG);
        assertNotNull(mFragmentManager.findFragmentByTag(TAG));

        return returnValue;
    }
*/
}
