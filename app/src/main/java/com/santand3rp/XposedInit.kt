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

                /*
                 The below hooks are hooking the string decoders in the app. This makes the string readable so we can
                 determine what the app is searching for in regards to root/zygisk. You can filter out results here
                 for things like "su" "root" "zygisk" "zygote" etc if required. Only uncomment these to debug.
                */

                /*
                findAndHookMethod(
                    "aat.\u0864\u0868",
                    lpparam.classLoader,
                    "\u1ad6",
                    String::class.java, Short::class.javaPrimitiveType, Short::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val result = param.result?.toString() ?: return
                            log("🔍 Decoded: $result")
                        }
                    }
                )

                findAndHookMethod(
                    "aat.\u0865\u0868",
                    lpparam.classLoader,
                    "\u086a",
                    String::class.java, Short::class.javaPrimitiveType, Short::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val result = param.result?.toString() ?: return
                            log("🔍 Decoded: $result")
                        }
                    }
                )

                findAndHookMethod(
                    "aat.\u086b\u0868",
                    lpparam.classLoader,
                    "\u1ada",
                    String::class.java, Short::class.javaPrimitiveType, Short::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val result = param.result?.toString() ?: return
                            log("🔍 Decoded: $result")
                        }
                    }
                )

                findAndHookMethod(
                    "aat.\u1ac5\u0868",
                    lpparam.classLoader,
                    "\u1ac9",
                    String::class.java, Short::class.javaPrimitiveType, Short::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val result = param.result?.toString() ?: return
                            log("🔍 Decoded: $result")
                        }
                    }
                )

                findAndHookMethod(
                    "aat.\u1acb\u0868",
                    lpparam.classLoader,
                    "\u086f",
                    String::class.java, Short::class.javaPrimitiveType, Short::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val result = param.result?.toString() ?: return
                            log("🔍 Decoded: $result")
                        }
                    }
                )

                findAndHookMethod(
                    "aat.\u1ad0\u0868",
                    lpparam.classLoader,
                    "\u086b",
                    String::class.java, Short::class.javaPrimitiveType, Short::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val result = param.result?.toString() ?: return
                            log("🔍 Decoded: $result")
                        }
                    }
                )

                findAndHookMethod(
                    "aat.\u1ad0\u0868",
                    lpparam.classLoader,
                    "\u086b",
                    String::class.java, Short::class.javaPrimitiveType, Short::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val result = param.result?.toString() ?: return
                            log("🔍 Decoded: $result")
                        }
                    }
                )

                findAndHookMethod(
                    "aat.\u1ad2\u0868",
                    lpparam.classLoader,
                    "\u086c",
                    String::class.java, Short::class.javaPrimitiveType, Short::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val result = param.result?.toString() ?: return
                            log("🔍 Decoded: $result")
                        }
                    }
                )

                findAndHookMethod(
                    "aat.\u1ad5\u0868",
                    lpparam.classLoader,
                    "\u0863",
                    String::class.java, Short::class.javaPrimitiveType, Short::class.javaPrimitiveType,
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val result = param.result?.toString() ?: return
                            log("🔍 Decoded: $result")
                        }
                    }
                )
                */
            }
        }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        MODULE_PATH = startupParam!!.modulePath
    }

}