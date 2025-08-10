package com.bonepeople.kotlin.maven.converter

import java.io.File

class MavenGroupIdConverter(
    private val publishDir: File,
    private val oldGroupId: String,
    private val newGroupId: String,
) {
    fun convert() {
        // 把 oldGroupId 按路径分割，定位旧目录
        val oldGroupPath = oldGroupId.replace('.', File.separatorChar)
        val newGroupPath = newGroupId.replace('.', File.separatorChar)

        val oldGroupDir = File(publishDir, oldGroupPath)
        if (!oldGroupDir.exists()) {
            println("Old groupId directory not found: $oldGroupDir")
            return
        }

        // 找到旧仓库下所有模块目录，比如 AndroidWidget
        val modules = oldGroupDir.listFiles { file -> file.isDirectory } ?: emptyArray()
        modules.forEach { moduleDir ->
            // 复制所有版本目录下的文件
            val versions = moduleDir.listFiles { file -> file.isDirectory } ?: emptyArray()
            versions.forEach { versionDir ->
                val relativePathFromOldGroup = versionDir.relativeTo(oldGroupDir).path
                val targetDir = File(publishDir, newGroupPath + File.separator + relativePathFromOldGroup)
                targetDir.mkdirs()

                versionDir.listFiles()?.forEach { file ->
                    val targetFile = File(targetDir, file.name)
                    if (file.extension in setOf("pom", "module")) {
                        // 修改pom和module文件内容中 groupId 字符串
                        val content = file.readText()
                        val newContent = content.replace(oldGroupId, newGroupId)
                        targetFile.writeText(newContent)
                    } else if (file.extension !in listOf("md5", "sha1", "sha256", "sha512")) {
                        // 其他非校验文件直接复制二进制
                        file.copyTo(targetFile, overwrite = true)
                    }
                }
            }

            // 处理 maven-metadata.xml 文件（可能在模块目录下）
            val metadataFile = File(moduleDir, "maven-metadata.xml")
            if (metadataFile.exists()) {
                val newMetadataDir = File(publishDir, newGroupPath + File.separator + moduleDir.name)
                newMetadataDir.mkdirs()
                val newMetadataFile = File(newMetadataDir, "maven-metadata.xml")
                val content = metadataFile.readText()
                // maven-metadata.xml 里也有 groupId 字段，替换
                val newContent = content.replace(oldGroupId, newGroupId)
                newMetadataFile.writeText(newContent)
            }
        }
        println("GroupId conversion finished.")
    }
}