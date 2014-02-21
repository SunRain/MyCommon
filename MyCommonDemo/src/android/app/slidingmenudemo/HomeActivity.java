//package android.app.slidingmenudemo;
//
//import java.util.Map;
//
//import wd.android.framework.ui.BaseFragment;
//import wd.android.framework.util.IntentUtil;
//import android.nbtstatx.mydemos.R;
//import android.os.Bundle;
//import android.support.v4.app.FragmentTransaction;
//import android.view.Display;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.WindowManager;
//
//public class HomeActivity extends MyBaseActivity {
//
//    private MenuFragment menuFragment;
//    private SlidingMenu menu;
//
//    /**
//     * Menu菜单
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    /**
//     * Menu菜单点击事件
//     */
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // toggle就是程序自动判断是打开还是关闭
//                toggleLeftMenu();
//                // getSlidingMenu().showMenu();// show menu
//                // getSlidingMenu().showContent();//show content
//                return true;
//            case R.id.menu_about:
//                IntentUtil.startActivity(HomeActivity.this,
//                        LeftSliderMainActivity.class, null);
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void initView(View rootView, Bundle bundle) {
//        initSlidingMenu();
//        initMenu();
//    }
//
//    /**
//     * 初始化SlidingMenu
//     */
//    void initSlidingMenu() {
//        // configure the SlidingMenu
//        menu = new SlidingMenu(this);
//        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//        // customize the SlidingMenu
//        // SlidingMenu menu = getSlidingMenu();
//        // 设置阴影宽度
//        menu.setShadowWidthRes(wd.android.framework.R.dimen.slidingmenu_shadow_width);
//        // 设置滑动时拖拽效果
//        menu.setBehindScrollScale(0.35f);
//        menu.setFadeDegree(0.5f);
//        // 设置菜单占屏幕的比例
//        WindowManager manage = getWindowManager();
//        Display display = manage.getDefaultDisplay();
//        int width = (int) (display.getWidth() / 6.5 * 1);
//        menu.setBehindOffset(width);
//
//        // 设置slding menu的几种手势模式
//        // TOUCHMODE_FULLSCREEN 全屏模式，在content页面中，滑动，可以打开sliding menu
//        // TOUCHMODE_MARGIN 边缘模式，在content页面中，如果想打开slding ,你需要在屏幕边缘滑动才可以打开slding
//        // menu
//        // TOUCHMODE_NONE 自然是不能通过手势打开啦
//        // 设置要使菜单滑动，触碰屏幕的范围
//        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//
//        // 使用左上方icon可点，这样在onOptionsItemSelected里面才可以监听到R.id.home
//        // getActionBar().setDisplayHomeAsUpEnabled(true);
//        // getActionBar().hide();
//    }
//
//    /**
//     * 初始化自定义菜单
//     */
//    private void initMenu() {
//        menu.setMenu(R.layout.slidingmenudemo_frame_menu);
//        // 设置左菜单阴影图片
//        menu.setShadowDrawable(wd.android.framework.R.drawable.shadow);
//        // set the Behind View
//        // setBehindContentView(R.layout.slidingmenu_frame_menu);
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager()
//                .beginTransaction();
//        menuFragment = new MenuFragment();
//        // mFragmentHelper.replace(null, R.id.menu, menuFragment);
//        // mFragmentHelper.replace(null, R.id.content, menuFragment);
//        fragmentTransaction.replace(R.id.menu, menuFragment);
//        // fragmentTransaction.replace(R.id.content,
//        // new ContentFragment("Welcome"), "Welcome");
//        fragmentTransaction.commitAllowingStateLoss();
//
//        // 设置是左滑还是右滑，还是左右都可以滑
//        // menu.setMode(SlidingMenu.LEFT_RIGHT);
//        // menu.setSecondaryMenu(R.layout.slidingmenu_frame_menu_right);
//        // menu.setSecondaryShadowDrawable(wd.android.framework.R.drawable.shadowright);
//        // getSupportFragmentManager().beginTransaction()
//        // .replace(R.id.menuRight, new MenuRightFragment())
//        // .commitAllowingStateLoss();
//    }
//
//    @Override
//    public void initData(Bundle bundle) {
//        show(HomeFragment.class, null, -1);
//    }
//
//    @Override
//    public int getRootViewId() {
//        return R.layout.slidingmenudemo_frame_content;
//    }
//
//    /**
//     * 左侧menu开关
//     */
//    public void toggleLeftMenu() {
//        if (menu.isMenuShowing()) {
//            menu.showContent();
//        } else {
//            menu.showMenu();
//        }
//    }
//
//    /**
//     * 右侧menu开关
//     */
//    public void toggleRightMenu() {
//        if (menu.isMenuShowing()) {
//            menu.showContent();
//        } else {
//            menu.showSecondaryMenu();
//        }
//    }
//
//    /**
//     * 设置是否滑动弹出菜单
//     *
//     * @param canFlip
//     */
//    public void setMenuFlip(boolean canFlip) {
//        if (canFlip) {
//            menu.setSlidingEnabled(canFlip);
//            // menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//            // menu.setTouchModeBehind(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        } else {
//            menu.setSlidingEnabled(canFlip);
//            // menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
//            // menu.setTouchModeBehind(SlidingMenu.TOUCHMODE_NONE);
//        }
//    }
//
//    public SlidingMenu getSlidingMenu() {
//        return menu;
//    }
//
//    private void showHome(android.support.v4.app.Fragment fragment) {
//        if (fragment.isAdded()) {
//            if (mFragmentHelper.hasBackStackEntry()) {
//                mFragmentHelper.popBackStack();
//            }
//        } else {
//            mFragmentHelper.replace(null, R.id.content, fragment);
//        }
//    }
//
//    private void showOther(android.support.v4.app.Fragment fragment,
//                           Map<String, Object> map) {
//        Bundle mbundle = IntentUtil.setData(new Bundle(), map);
//        if (!fragment.isVisible()) {
//            if (mFragmentHelper.hasBackStackEntry()) {
//                mFragmentHelper.popBackStack();
//            }
//            mFragmentHelper.add(mbundle, R.id.content, fragment);
//        } else {
//            // 刷新
//            // ((BaseFragment) fragment).initData(mbundle);
//            // if (fragment instanceof HotFragment) {
//            // ((HotFragment) fragment).sendRequest(map);
//            // }
//        }
//    }
//
//    /**
//     * @param fragmentClazz
//     */
//    public void show(Class<? extends BaseFragment> fragmentClazz,
//                     Map<String, Object> map, int selection) {
//        if (selection != -1) {
//            // setMenuSelection(selection);
//        }
//        menu.showContent();
//        android.support.v4.app.Fragment fragment = mFragmentHelper
//                .findFragment(fragmentClazz);
//        // if (fragment instanceof HomeFragment) {
//        showHome(fragment);
//        // } else {
//        // showOther(fragment, map);
//        // }
//    }
//}
