# FiskalPRO Kit Public

This repository contains artefacts and example usages of the FiskalPRO Kit public modules.

## Implementation

### Enable desugaring (for min API 25 and lower only!)

* https://developer.android.com/studio/write/java8-support#library-desugaring

```
android {
    …
    compileOptions {
        …
        isCoreLibraryDesugaringEnabled = true
    }
}

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:[VERSION]")
}
```

