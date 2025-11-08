plugins {
    alias(libs.plugins.dependencies.build.logic)
    alias(libs.plugins.api.build.logic)
    alias(libs.plugins.proto.library)
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                create("java") {
                    option("lite")
                }
            }
        }
    }
}