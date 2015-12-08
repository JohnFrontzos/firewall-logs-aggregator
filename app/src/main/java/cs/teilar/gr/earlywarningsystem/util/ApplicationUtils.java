package cs.teilar.gr.earlywarningsystem.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 20/11/2015
 */
public class ApplicationUtils {

    public static boolean isPackageInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static List<ApplicationInfo> getApplicationList(Context context){
        PackageManager pkgmanager = context.getPackageManager();
        return pkgmanager.getInstalledApplications(PackageManager.GET_META_DATA);
    }

    public static Intent openGPlay(String packageName) {
        try {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
        } catch (android.content.ActivityNotFoundException anfe) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName));
        }
    }
}
