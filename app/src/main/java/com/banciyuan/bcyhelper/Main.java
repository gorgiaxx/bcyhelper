package com.banciyuan.bcyhelper;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Main implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (!loadPackageParam.packageName.equals("com.banciyuan.bcywebview"))
            return;
        findAndHookMethod("de.greenrobot.daoexample.model.Multi", loadPackageParam.classLoader, "getPath", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    String path = (String) XposedHelpers.getObjectField(param.thisObject, "path");
                    int imageMogrIndex = path.indexOf("?");
                    if (imageMogrIndex > 1) {
                        path = path.substring(0, imageMogrIndex);
                    }
                    return path;
                } catch (Throwable t) {
                    XposedBridge.log(t);
                    return "";
                }
            }
        });

        findAndHookMethod("de.greenrobot.daoexample.model.PostItem", loadPackageParam.classLoader, "getPath", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                try {
                    XposedBridge.log("PostItem");
                    String path = (String) XposedHelpers.getObjectField(param.thisObject, "path");
                    int imageMogrIndex = path.indexOf("?");
                    if (imageMogrIndex > 1) {
                        path = path.substring(0, imageMogrIndex);
                    }
                    return path;
                } catch (Throwable t) {
                    XposedBridge.log(t);
                    return "";
                }
            }
        });

        findAndHookMethod("de.greenrobot.daoexample.model.OrignPic", loadPackageParam.classLoader, "isDownload", new XC_MethodReplacement() {
            @Override
            protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                return true;
            }
        });
    }
}
