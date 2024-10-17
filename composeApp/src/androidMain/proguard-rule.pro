# Keep all Kotlin serialization annotations
-keepattributes *Annotation*

# Keep all classes that are annotated with @Serializable
-keep @kotlinx.serialization.Serializable class *

# Keep generated serialization companions
-keepclassmembers class **$Companion {
    kotlinx.serialization.KSerializer serializer(...);
}