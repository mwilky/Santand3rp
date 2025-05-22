package com.santand3rp

import dalvik.system.DexFile
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.IXposedHookZygoteInit
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedBridge.log
import de.robv.android.xposed.XposedHelpers.findAndHookConstructor
import de.robv.android.xposed.XposedHelpers.findAndHookMethod
import de.robv.android.xposed.XposedHelpers.findClass
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.io.BufferedReader
import java.io.File
import java.lang.reflect.Modifier

class XposedInit : IXposedHookLoadPackage, IXposedHookZygoteInit {

    companion object {

        val keywords = listOf("zygisk","smap", "zygote")
        const val SANTANDER_PACKAGE = "uk.co.santander.santanderUK"
        private var MODULE_PATH: String? = null


    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam?) {
        when (lpparam?.packageName) {
            SANTANDER_PACKAGE -> {

                /*
                String decoder that returns the following in a String array. The array is checked
                for the length of each string. If the length is different than expected
                it throws an error and the app crashes. Not sure which one is detected

                - /debug_ramdisk/zygisk//monitor.sock
                - /debug_ramdisk/zygisk//zygisk.sock
                - /debug_ramdisk/zygisk/init_monitor
                - /debug_ramdisk/zygisk//cp64.sock
                - /debug_ramdisk/init_monitor
                - /debug_ramdisk//cp64.sock
                - /debug_ramdisk//cp32.sock
                - /debug_ramdisk/zygisksu/init_monitor
                - /debug_ramdisk/zygisksu/cp64.sock
                - /dev/zygisk/cp32.sock
                - /dev/zygisk/cp64.sock
                - /dev/zygisk/cp.sock
                - /debug_ramdisk/.magisk/socket

                findAndHookMethod(
                    "aaw.\u1AD6ࡪ",
                    lpparam.classLoader,
                    "\u1AD1᫋",
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val arr = param.result as? Array<String>
                            if (arr != null) {
                                arr.forEachIndexed { idx, str ->
                                    log("String array element [$idx]: $str")
                                }
                                //log("stack = ${Throwable().stackTraceToString()}")
                            } else {
                                log("Method returned null or not a String[]!")
                            }
                        }
                    }
                )

                This looks to be the root zygisk/root check function, from here it calls a host
                of functions that throw an exception if things are detected. We
                got here by tracing back the above string decoder

                renamed from: ࡰ, reason: not valid java name and contains not printable characters
                    public static void m4227() {
                        int i10 = (1661821151 | (-1661809162)) & ((~1661821151) | (~(-1661809162)));
                        int m4088 = C0686.m4088();
                        int i11 = (m4088 | 1331117868) & ((~m4088) | (~1331117868));
                        int m3976 = C0650.m3976();
                        Object[] objArr = new Object[0];
                        int i12 = (68271938 | (-68282135)) & ((~68271938) | (~(-68282135)));
                        int i13 = ((~(-1763193499)) & 1763202138) | ((~1763202138) & (-1763193499));
                        short m39762 = (short) (C0650.m3976() ^ i12);
                        int m39763 = C0650.m3976();
                        Method declaredMethod = Class.forName(C0732.m4220("BCZ\u0012뵟ꬎ", (short) ((m3976 | i10) & ((~m3976) | (~i10))), (short) (C0650.m3976() ^ i11))).getDeclaredMethod(C0760.m4331("暯", m39762, (short) ((m39763 | i13) & ((~m39763) | (~i13)))), new Class[0]);
                        try {
                            declaredMethod.setAccessible(true);
                            declaredMethod.invoke(null, objArr);
                            C0643.m3959();
                            C0625.m3929();
                            C0670.m4046();
                            C0772.m4360();
                            C0777.m4389();
                            C0600.m3870();
                            C0758.m4320();
                            C0614.m3899();
                            C0680.m4064();
                        } catch (InvocationTargetException e10) {
                            throw e10.getCause();
                        }
                    }
                 */

                findAndHookMethod(
                    "aaw.᫋ࡨ",
                    lpparam.classLoader,
                    "ࡰ",
                    object : XC_MethodHook() {
                        override fun beforeHookedMethod(param: MethodHookParam) {
                            //log("stack = ${Throwable().stackTraceToString()}")
                            param.result = null
                        }
                    }
                )


                /*
                This is a secondary function that is called in multiple places. It searches the below files using bufferedReader.readLine()
                and returns a list of strings. Certain results in this list will trigger a detection.

                - /proc/self/status
                - /proc/833/status
                - /proc/self/maps

                If you uncomment the log line out you will see the things that are being searched, i am unsure what is triggering the detection
                 */
                findAndHookMethod(
                    "aaw.ࡤࡰ",
                    lpparam.classLoader,
                    "\u1ADD",
                    object : XC_MethodHook() {
                        override fun afterHookedMethod(param: MethodHookParam) {
                            val result = param.result
                            if (result is List<*>) {
                                // Collect all non-blank f557 values in a set (for uniqueness)
                                val unique = mutableSetOf<String>()
                                result.forEach { obj ->
                                    if (obj != null) {
                                        try {
                                            val clazz = obj.javaClass
                                            val f557 = clazz.getDeclaredField("᫉").apply { isAccessible = true }.get(obj) as? String
                                            if (!f557.isNullOrBlank()
                                                && !f557.startsWith("/system/")
                                                && !f557.startsWith("/system_ext/")
                                                && !f557.startsWith("/product/")
                                                && !f557.startsWith("[anon:stack_and_tls")
                                                && !f557.startsWith("/apex")
                                                && !f557.startsWith("/vendor")
                                                && !f557.startsWith("/data/app")
                                                && !f557.startsWith("/data/data")
                                                && unique.add(f557)) {
                                                //log(f557)
                                            }
                                        } catch (_: Throwable) {}
                                    }
                                }
                            }
                            param.result = ArrayList<Any>()
                        }
                    }
                )

                // Uncomment this log all decoded strings (there is lots!)
                //outputDecodedStrings(lpparam)

            }
        }
    }

    fun outputDecodedStrings(lpparam: XC_LoadPackage.LoadPackageParam) {
        val dex = DexFile(lpparam.appInfo.sourceDir)
        dex.entries().asSequence()
            // This needs to be changed per app release as the names change due to obfuscation
            .filter { it.startsWith("aaw.") || it.startsWith("aaw") }
            .forEach { className ->
                try {
                    val clazz = Class.forName(className, false, lpparam.classLoader)
                    clazz.declaredMethods.forEach { method ->
                        if (
                            Modifier.isStatic(method.modifiers) &&
                            method.returnType == String::class.java || method.returnType == Array<String>::class.java
                        ) {
                            try {
                                // Dynamically hook, regardless of parameters
                                findAndHookMethod(
                                    className,
                                    lpparam.classLoader,
                                    method.name,
                                    *method.parameterTypes,
                                    object : XC_MethodHook() {
                                        override fun afterHookedMethod(param: MethodHookParam) {
                                            val result = param.result
                                            // Handle plain String result
                                            if (result is String) {
                                                if (keywords.any { result.contains(it, ignoreCase = true) }) {
                                                    log("String decoder (generic): $className(${className.escapeNonAscii()})." +
                                                            "${method.name}(${method.name.escapeNonAscii()}) (${param.args?.joinToString()}) = $result" +
                                                            "\nStack:\n" + Throwable().stackTraceToString()
                                                    )
                                                }
                                            }
                                            // Handle String[] result
                                            else if (result is Array<*>) {
                                                // Only look at arrays of String
                                                if (result.isArrayOf<String>()) {
                                                    val matches = result.filterIsInstance<String>().filter { str ->
                                                        keywords.any { keyword -> str.contains(keyword, ignoreCase = true) }
                                                    }
                                                    if (matches.isNotEmpty()) {
                                                        log("String[] decoder (generic): $className(${className.escapeNonAscii()})." +
                                                                "${method.name}(${method.name.escapeNonAscii()}) (${param.args?.joinToString()}) = ${matches.joinToString()}" +
                                                                "\nStack:\n" + Throwable().stackTraceToString()
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                )
                            } catch (_: Throwable) {}
                        }
                    }
                } catch (e: Throwable) {
                    log("⚠️ Failed to load $className: ${e.message}")
                }
            }
    }

    override fun initZygote(startupParam: IXposedHookZygoteInit.StartupParam?) {
        MODULE_PATH = startupParam!!.modulePath
    }

    fun String.escapeNonAscii(): String {
        return buildString {
            for (c in this@escapeNonAscii) {
                if (c in '\u0020'..'\u007E') {
                    append(c)
                } else {
                    append("\\u%04X".format(c.code))
                }
            }
        }
    }
}