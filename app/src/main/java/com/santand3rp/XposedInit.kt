package com.santand3rp

import android.app.AndroidAppHelper
import android.util.Log
import dalvik.system.DexFile
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge.log
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.XposedHelpers.findClass
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.lang.reflect.Modifier

class XposedInit : IXposedHookLoadPackage, IXposedHookZygoteInit {

    companion object {

        const val SANTANDER_PACKAGE = "uk.co.santander.santanderUK"

        private var MODULE_PATH: String? = null

        val crashHooks = listOf(
            "aat.\u1AD5\u1AC1" to "\u1AD7",   // 5.17.0
            "rrj.\u1AC1\u086C" to "\u1ADE"    // 5.18.0
        )

        val detectionHooks = listOf(
            "aat.\u1AD3\u0865" to "\u1AD0", // 5.17.0
            "rrj.\u0862\u1AC6" to "\u086A", // 5.18.0
        )
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {

        when(lpparam?.packageName) {
            SANTANDER_PACKAGE -> {
                // Prevent the StackOverflow decoy
                for ((className, methodName) in crashHooks) {
                    try {
                        findAndHookMethod(
                            className,
                            lpparam.classLoader,
                            methodName,
                            object : XC_MethodHook() {
                                override fun beforeHookedMethod(param: MethodHookParam) {
                                    log("🛑 Preventing StackOverflow decoy: $className.$methodName")
                                    param.result = null
                                }
                            }
                        )
                    } catch (e: Throwable) {
                    }
                }
                // Prevent the zygisk detection
                for ((className, methodName) in detectionHooks) {
                    try {
                        findAndHookMethod(
                            className,
                            lpparam.classLoader,
                            methodName,
                            object : XC_MethodHook() {
                                override fun beforeHookedMethod(param: MethodHookParam) {
                                    log("🛑 Preventing Zygisk detection: $className.$methodName")
                                    param.result = null
                                }
                            }
                        )
                    } catch (e: Throwable) {
                    }
                }


            }
        }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        MODULE_PATH = startupParam!!.modulePath
    }
}