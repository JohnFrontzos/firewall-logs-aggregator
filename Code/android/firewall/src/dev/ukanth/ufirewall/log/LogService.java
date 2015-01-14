/**
 * Background service to spool /proc/kmesg command output using klogripper
 * 
 * Copyright (C) 2014 Umakanthan Chandran
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @author Umakanthan Chandran
 * @version 1.0
 */

package dev.ukanth.ufirewall.log;

import java.util.List;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import dev.ukanth.ufirewall.Api;
import dev.ukanth.ufirewall.G;
import dev.ukanth.ufirewall.Log;
import dev.ukanth.ufirewall.R;
import dev.ukanth.ufirewall.RootShell.RootCommand;
import eu.chainfire.libsuperuser.Shell;
import eu.chainfire.libsuperuser.StreamGobbler;

public class LogService extends Service {

	public static final String TAG = "AFWall";

	public static String klogPath;
	private final IBinder mBinder = new Binder();
	private Shell.Interactive rootSession;

	private Handler handler;
	
	public static Toast toast;
	public static TextView toastTextView;
	public static CharSequence toastText;
	public static boolean toastEnabled;
	public static int toastDuration;
	public static int toastPosition;
	public static int toastDefaultYOffset;
	public static int toastYOffset;
	public static int toastOpacity;
	public static boolean toastShowAddress;
	
	
	private static Runnable showOnlyToastRunnable;
	private static CancelableRunnable showToastRunnable;
	private static View toastLayout;
	
	private static abstract class CancelableRunnable implements Runnable {
	    public boolean cancel;
	}
	

	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	
	public static void showToast(final Context context, final Handler handler, final CharSequence text, boolean cancel) {
	    if(showToastRunnable == null) {
	      showToastRunnable = new CancelableRunnable() {
	        public void run() {
	          if(cancel && toast != null) {
	            toast.cancel();
	          }

	          if(cancel || toast == null) {
	            toastLayout = ((LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_toast, null);
	            toastTextView = (TextView) toastLayout.findViewById(R.id.toasttext);
	            if(android.os.Build.VERSION.SDK_INT > 10 || toast == null) {
	              toast = new Toast(context);
	            }
	            toastDefaultYOffset = toast.getYOffset();
	            toast.setView(toastLayout);
	          }

	          switch(toastDuration) {
	            case 3500:
	              toast.setDuration(Toast.LENGTH_LONG);
	              break;
	            case 7000:
	              toast.setDuration(Toast.LENGTH_LONG);

	              if(showOnlyToastRunnable == null) {
	                showOnlyToastRunnable  = new Runnable() {
	                  public void run() {
	                    toast.show();
	                  }
	                };
	              }

	              handler.postDelayed(showOnlyToastRunnable, 3250);
	              break;
	            default:
	              toast.setDuration(Toast.LENGTH_SHORT);
	          }

	          switch(toastPosition) {
	            case 1:
	              toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, toastYOffset);
	              break;
	            case 2:
	              toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, toastYOffset);
	              break;
	            default:
	              toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, toastDefaultYOffset);
	              break;
	          }

	          toastTextView.setText(android.text.Html.fromHtml(toastText.toString()));
	          toast.show();
	        }
	      };
	    }

	    showToastRunnable.cancel = cancel;
	    toastText = text;
	    handler.post(showToastRunnable);
	  }

	public void onCreate() {
		klogPath = Api.getKLogPath(getApplicationContext());
		Log.i(TAG, "Starting " + klogPath);
		handler = new Handler();
		rootSession = new Shell.Builder()
				.useSU()
				.setMinimalLogging(true)
				.setOnSTDOUTLineListener(new StreamGobbler.OnLineListener() {
					@Override
					public void onLine(String line) {
						if(G.enableLogService()) {
							if(line.trim().length() > 0)
							{
								if (line.contains("AFL")) {
                                    Log.d("Warning System", "Found a log! Need to write it to a file");

									final String logData = LogInfo.parseLogs(line,getApplicationContext());
									if(logData != null && logData.length() > 0 ) {
										showToast(getApplicationContext(), handler,logData, false);
									}	
								}
							}
						} 
					}
				})

				.open(new Shell.OnCommandResultListener() {
					public void onCommandResult(int commandCode, int exitCode, List<String> output) {
						if (exitCode != 0) {
							Log.e(TAG, "Can't start klog shell: exitCode " + exitCode);
							stopSelf();
						} else {
							Log.i(TAG, "logservice shell started");
							rootSession.addCommand(klogPath);
						}
					}
				});
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Received request to kill logservice");
		//Api.killLogProcess(getApplicationContext());
	}
}
