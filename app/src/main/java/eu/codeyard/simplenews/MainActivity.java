package eu.codeyard.simplenews;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import eu.codeyard.simplenews.framework.presentation.titlebar.TitleBarFragment;
import eu.codeyard.simplenews.framework.presentation.titlebar.TitleBarFragment_;
import eu.codeyard.simplenews.framework.presentation.titlebar.TitleBarViewModel;
import eu.codeyard.simplenews.framework.presentation.titlebar.TitleBarVisibilityState;
import eu.codeyard.simplenews.framework.presentation.util.AnimationUtils;

@EActivity
public class MainActivity extends AppCompatActivity {

    @ViewById
    protected FrameLayout titleBarContainer;

    private TitleBarViewModel titleBarViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        setTitleBar();
        subscribeObservers();
    }

    private void setTitleBar() {
        TitleBarFragment titleBarFragment = TitleBarFragment_.builder().build();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.titleBarContainer, titleBarFragment)
                .commitAllowingStateLoss();

        titleBarViewModel = ViewModelProviders.of(this).get(TitleBarViewModel.class);
    }

    private void subscribeObservers() {
        titleBarViewModel.getTitleBarVisibilityState().observe(this, this::handleTitleBarVisibilityState);
    }

    private void handleTitleBarVisibilityState(TitleBarVisibilityState titleBarVisibilityState) {
        switch (titleBarVisibilityState) {
            case GONE:
                AnimationUtils.animateContainerHeight(titleBarContainer, titleBarContainer.getHeight(), 0, () -> {
                    if (titleBarContainer != null) {
                        titleBarContainer.setVisibility(View.GONE);
                    }
                });
                break;
            case VISIBLE:
                titleBarContainer.setVisibility(View.VISIBLE);
                AnimationUtils.animateContainerHeight(titleBarContainer, titleBarContainer.getHeight(), Math.round(getResources().getDimension(R.dimen.title_bar_height)), null);
                break;
            case SEARCH:
                AnimationUtils.animateContainerHeight(titleBarContainer, titleBarContainer.getHeight(), Math.round(getResources().getDimension(R.dimen.title_bar_search_height)), null);
                break;
        }
    }
}