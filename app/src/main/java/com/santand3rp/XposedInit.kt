package com.santand3rp

import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge.log
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.callbacks.XC_LoadPackage

class XposedInit : IXposedHookLoadPackage, IXposedHookZygoteInit {

    companion object {

        const val SANTANDER_PACKAGE = "uk.co.santander.santanderUK"

        private var MODULE_PATH: String? = null
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {

        when(lpparam?.packageName) {
            SANTANDER_PACKAGE -> {
                // Prevent the StackOverflow decoy
                findAndHookMethod(
                    "aat.᫕᫁",
                    lpparam.classLoader,
                    "᫗",
                    object : XC_MethodHook() {
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            log("🛑 Preventing StackOverflow decoy")
                            param.result = null
                        }
                    }
                )

                // Prevent the detection
                findAndHookMethod(
                    "aat.᫓ࡥ",
                    lpparam.classLoader,
                    "᫐",
                    object : XC_MethodHook() {
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            log("🛑 Preventing Zygisk check")
                            param.result = null
                        }
                    }
                )
            }
        }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        MODULE_PATH = startupParam!!.modulePath
    }

}