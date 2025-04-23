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

The module applies two core method hooks depending on the app version:

### 🔍 Detection Hook
This hook blocks the Zygisk detection logic which scans strings and filesystem paths like `/proc/self/smaps` and `/debug_ramdisk/zygisk`.

| App Version | Method Signature                      |
|-------------|----------------------------------------|
| 5.17.0      | `aat.᫓ࡥ.᫐()`                         |
| 5.18.0      | `rrj.ࡢ᫆.ࡪ()`                         |

### 💥 Crash Loop Hook
This hook skips execution of the crash loop method that triggers a `StackOverflowError` through infinite recursion.

| App Version | Method Signature                      |
|-------------|----------------------------------------|
| 5.17.0      | `aat.᫕᫁.᫗()`                         |
| 5.18.0      | `rrj.᫁࡬.᫞()`                         |

---

## 🧪 Requirements

- ✅ LSPosed or compatible Xposed framework
- ✅ Device with root access and Zygisk enabled
- ❗ Tested with Santander UK Android app versions **5.17.0** and **5.18.0**

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
