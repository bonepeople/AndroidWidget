package com.bonepeople.kotlin.maven.converter;

import java.io.File;

public class MavenGroupIdConvertHelper {
    public static void main(String[] args) {
        File projectPath = new File(System.getProperty("user.dir"));
        File publishPath = new File(projectPath, "gradle/publish");
        String oldGroupId = "com.bonepeople.android.lib";
        String newGroupId = "com.github.bonepeople";

        System.out.println("Project path: " + projectPath.getAbsolutePath());
        System.out.println("Publish path: " + publishPath.getAbsolutePath());
        System.out.println("Old GroupId: " + oldGroupId);
        System.out.println("New GroupId: " + newGroupId);

        if (!publishPath.exists() || !publishPath.isDirectory()) {
            System.err.println("Publish path does not exist or is not a directory: " + publishPath.getAbsolutePath());
            System.exit(1);
        }

        MavenGroupIdConverter converter = new MavenGroupIdConverter(publishPath, oldGroupId, newGroupId);
        converter.convert();
    }
}