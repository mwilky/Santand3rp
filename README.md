[![GitHub release](https://img.shields.io/github/v/release/mwilky/Santand3rp?style=for-the-badge)](https://github.com/mwilky/Santand3rp/releases)
[![Downloads](https://img.shields.io/github/downloads/mwilky/Santand3rp/total?style=for-the-badge)](https://github.com/mwilky/Santand3rp/releases)

# Santander zygisk detection bypass

**A Xposed module to bypass Zygisk detection in the Santander UK Android app.**

---

## ✨ Features

- ✅ Bypasses Zygisk implementation detection
- ✅ Prevents crash loop caused by stack overflow

---

## 🛠️ How It Works

Two main hooks are applied:

  - detectionHooks = `aat.\u1AD3\u0865.\u1AD0` (5.17.0) & 
      `rrj.\u0862\u1AC6.\u086A` (5.18.0)

  - crashHooks = `aat.\u1AD5\u1AC1.\u1AD7` (5.17.0) & 
      `rrj.\u1AC1\u086C.\u1ADE` (5.18.0)

1. **Blocks Zygisk Detection Method**
   - Intercepts and nullifies `detectionHooks`, which is responsible for detecting Zygisk via string scanning and file checks (e.g., `/proc/self/smaps`, `/debug_ramdisk/zygisk`  etc.).

2. **Prevents Crash Loop**
   - Skips the execution of `crashHooks`, a method that causes a deliberate infinite recursion resulting in `StackOverflowError`.

These hooks prevent the app from crashing or refusing to run when Zygisk implementations are present.

---

## 🧪 Requirements

- ✅ LSPosed implementation
- ✅ Root solution with a Zygisk implementation enabled
- ❗ Tested with the Santander UK Android app (Version: 5.17.0/5.18.0)

---

## 🚀 Installation

1. Clone or download this module.
2. Build with Android Studio or your preferred IDE.
3. Push the compiled module to your device.
4. Enable the module in **LSPosed** for the Santander UK app only.
5. Reboot your device.
6. Open the Santander UK app — it should now launch without crashing.

---

## 🧠 Notes

- The Santander app uses heavy obfuscation; this module may need updates if class/method names change in future versions.
- This module does **not** hide root or Zygisk system-wide — it only bypasses detection for this specific app.
- This module does **not** hide root from Santander UK. If your app is detecting root, you have a setup issue elsewhere.

---
